package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import java.util.*;
import Method.Client.module.Onscreen.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;

public final class Tps extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    static Setting Shadow;
    static Setting Frame;
    static Setting FontSize;
    private static final float[] tickRates;
    private int nextIndex;
    private long timeLastTimeUpdate;
    
    public Tps() {
        super("Tps", 0, Category.ONSCREEN, "Tps");
        this.nextIndex = 0;
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Tps.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Tps.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Tps.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(Tps.tickRates, 0.0f);
        Main.setmgr.add(Tps.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Tps.background = new Setting("background", this, false));
        Main.setmgr.add(Tps.xpos = new Setting("xpos", this, 200.0, -20.0, Tps.mc.field_71443_c + 40, true));
        Main.setmgr.add(Tps.ypos = new Setting("ypos", this, 210.0, -20.0, Tps.mc.field_71440_d + 40, true));
        Main.setmgr.add(Tps.Frame = new Setting("Font", this, "Times", this.fontoptions()));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("TpsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("TpsSET", false);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketTimeUpdate) {
            this.onTimeUpdate();
        }
        return true;
    }
    
    private void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            final float timeElapsed = (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            Tps.tickRates[this.nextIndex % Tps.tickRates.length] = MathHelper.func_76131_a(20.0f / timeElapsed, 0.0f, 20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
    
    public static float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (final float tickRate : Tps.tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                ++numTicks;
            }
        }
        return MathHelper.func_76131_a(sumTickRates / numTicks, 0.0f, 20.0f);
    }
    
    static {
        tickRates = new float[20];
    }
    
    public static class TpsRUN extends PinableFrame
    {
        public TpsRUN() {
            super("TpsSET", new String[0], (int)Tps.ypos.getValDouble(), (int)Tps.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Tps.xpos, Tps.ypos, Tps.Frame, Tps.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Tps.xpos, Tps.ypos, Tps.Frame, Tps.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final String tickrate = String.format("TPS: %.2f", Tps.getTickRate());
            this.fontSelect(Tps.Frame, tickrate, this.getX(), this.getY() + 10, Tps.TextColor.getcolor(), Tps.Shadow.getValBoolean());
            if (Tps.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Tps.Frame, tickrate), this.y + 20, Tps.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
