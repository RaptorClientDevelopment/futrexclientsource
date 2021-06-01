package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.storage.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraft.util.datafix.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.concurrent.*;
import net.minecraft.world.*;
import java.util.zip.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import java.text.*;

public final class ChunkSize extends Module
{
    static Setting Delay;
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Shadow;
    static Setting Frame;
    static Setting FontSize;
    static Setting Background;
    public static final TimerUtils timer;
    public static boolean running;
    public static long size;
    public static long previousSize;
    public static ChunkPos current;
    AnvilChunkLoader loader;
    NBTTagCompound root;
    NBTTagCompound level;
    DataOutputStream compressed;
    
    public ChunkSize() {
        super("ChunkSize", 0, Category.ONSCREEN, "ChunkSize");
        this.root = null;
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(ChunkSize.Delay = new Setting("Delay", this, 1.0, 1.0, 10.0, true));
        Main.setmgr.add(ChunkSize.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(ChunkSize.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(ChunkSize.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(ChunkSize.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(ChunkSize.Background = new Setting("Background", this, false));
        Main.setmgr.add(ChunkSize.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(ChunkSize.xpos = new Setting("xpos", this, 200.0, -20.0, ChunkSize.mc.field_71443_c + 40, true));
        Main.setmgr.add(ChunkSize.ypos = new Setting("ypos", this, 250.0, -20.0, ChunkSize.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("ChunkSizeSET", true);
        ChunkSize.timer.reset();
        this.loader = new AnvilChunkLoader((File)null, (DataFixer)null);
        ChunkSize.running = false;
        ChunkSize.size = (ChunkSize.previousSize = 0L);
        ChunkSize.current = null;
        this.root = new NBTTagCompound();
        this.level = new NBTTagCompound();
        this.root.func_74782_a("Level", (NBTBase)this.level);
        this.root.func_74768_a("DataVersion", 2021);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("ChunkSizeSET", false);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (ChunkSize.running) {
            return;
        }
        if (event.phase == TickEvent.Phase.END) {
            final Chunk chunk = ChunkSize.mc.field_71441_e.func_175726_f(ChunkSize.mc.field_71439_g.func_180425_c());
            if (chunk.func_76621_g()) {
                return;
            }
            final ChunkPos pos = chunk.func_76632_l();
            if (!pos.equals((Object)ChunkSize.current) || ChunkSize.timer.isDelay(1000L)) {
                if (ChunkSize.current != null && !pos.equals((Object)ChunkSize.current)) {
                    ChunkSize.size = (ChunkSize.previousSize = 0L);
                }
                ChunkSize.current = pos;
                ChunkSize.running = true;
                final Chunk chunk2;
                final DeflaterOutputStream deflaterOutputStream;
                final OutputStream outputStream;
                final DataOutputStream compressed;
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        try {
                            this.loader.func_75820_a(chunk2, (World)ChunkSize.mc.field_71441_e, this.level);
                        }
                        catch (Throwable t) {
                            ChunkSize.size = -1L;
                            ChunkSize.previousSize = 0L;
                            return;
                        }
                        // new(java.io.DataOutputStream.class)
                        // new(java.io.BufferedOutputStream.class)
                        new DeflaterOutputStream(new ByteArrayOutputStream(8096));
                        new BufferedOutputStream(deflaterOutputStream);
                        new DataOutputStream(outputStream);
                        this.compressed = compressed;
                        try {
                            CompressedStreamTools.func_74800_a(this.root, (DataOutput)this.compressed);
                            ChunkSize.previousSize = ChunkSize.size;
                            ChunkSize.size = this.compressed.size();
                        }
                        catch (IOException e) {
                            ChunkSize.size = -1L;
                            ChunkSize.previousSize = 0L;
                        }
                    }
                    finally {
                        ChunkSize.timer.setLastMS();
                        ChunkSize.running = false;
                    }
                });
            }
        }
    }
    
    static {
        timer = new TimerUtils();
        ChunkSize.running = false;
        ChunkSize.size = 0L;
        ChunkSize.previousSize = 0L;
        ChunkSize.current = null;
    }
    
    public static class ChunkSizeRUN extends PinableFrame
    {
        public ChunkSizeRUN() {
            super("ChunkSizeSET", new String[0], (int)ChunkSize.ypos.getValDouble(), (int)ChunkSize.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, ChunkSize.xpos, ChunkSize.ypos, ChunkSize.Frame, ChunkSize.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, ChunkSize.xpos, ChunkSize.ypos, ChunkSize.Frame, ChunkSize.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final String Size = " " + String.format("[%s | %s]", (ChunkSize.size == -1L) ? "<error>" : toFormattedBytes(ChunkSize.size), difference(ChunkSize.size - ChunkSize.previousSize));
            if (ChunkSize.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(ChunkSize.Frame, Size), this.y + 20, ChunkSize.BgColor.getcolor());
            }
            this.fontSelect(ChunkSize.Frame, Size, this.getX(), this.getY() + 10, ChunkSize.TextColor.getcolor(), ChunkSize.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
        
        private static String toFormattedBytes(final long size) {
            final NumberFormat format = NumberFormat.getInstance();
            format.setGroupingUsed(true);
            if (size < 1000L) {
                return format.format(size) + " B";
            }
            if (size < 1000000L) {
                return format.format(size / 1000.0) + " KB";
            }
            return format.format(size / 1000000.0) + " MB";
        }
        
        private static String difference(final long size) {
            if (size == 0L) {
                return "+0 B";
            }
            if (size > 0L) {
                return "+" + toFormattedBytes(size);
            }
            return "-" + toFormattedBytes(Math.abs(size));
        }
    }
}
