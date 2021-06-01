package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;

public final class Biome extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Background;
    static Setting Shadow;
    static Setting FontSize;
    
    public Biome() {
        super("Biome", 0, Category.ONSCREEN, "Biome");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Biome.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Biome.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Biome.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Biome.Background = new Setting("Background", this, false));
        Main.setmgr.add(Biome.xpos = new Setting("xpos", this, 200.0, -20.0, Biome.mc.field_71443_c + 40, true));
        Main.setmgr.add(Biome.ypos = new Setting("ypos", this, 20.0, -20.0, Biome.mc.field_71440_d + 40, true));
        Main.setmgr.add(Biome.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Biome.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("BiomeSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("BiomeSET", false);
    }
    
    public static class BiomeRUN extends PinableFrame
    {
        public BiomeRUN() {
            super("BiomeSET", new String[0], (int)Biome.ypos.getValDouble(), (int)Biome.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Biome.xpos, Biome.ypos, Biome.Frame, Biome.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Biome.xpos, Biome.ypos, Biome.Frame, Biome.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final BlockPos pos = this.mc.field_71439_g.func_180425_c();
            final Chunk chunk = this.mc.field_71441_e.func_175726_f(pos);
            final net.minecraft.world.biome.Biome biome = chunk.func_177411_a(pos, this.mc.field_71441_e.func_72959_q());
            if (Biome.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Biome.Frame, biome.func_185359_l()), this.y + 22, Biome.BgColor.getcolor());
            }
            this.fontSelect(Biome.Frame, biome.func_185359_l(), this.getX(), this.getY() + 10, Biome.TextColor.getcolor(), Biome.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
    }
}
