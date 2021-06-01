package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public final class Fps extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Shadow;
    static Setting Frame;
    static Setting Background;
    static Setting FontSize;
    
    public Fps() {
        super("Fps", 0, Category.ONSCREEN, "Fps");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(Fps.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Fps.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Fps.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Fps.Background = new Setting("Background", this, false));
        Main.setmgr.add(Fps.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Fps.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Fps.xpos = new Setting("xpos", this, 200.0, -20.0, Fps.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Fps.ypos = new Setting("ypos", this, 70.0, -20.0, Fps.mc.field_71440_d / 2 + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("FpsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("FpsSET", false);
    }
    
    public static class FpsRUN extends PinableFrame
    {
        public FpsRUN() {
            super("FpsSET", new String[0], (int)Fps.ypos.getValDouble(), (int)Fps.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Fps.xpos, Fps.ypos, Fps.Frame, Fps.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Fps.xpos, Fps.ypos, Fps.Frame, Fps.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final String framerate = "FPS: " + Minecraft.func_175610_ah();
            if (Fps.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Fps.Frame, framerate), this.y + 20, Fps.BgColor.getcolor());
            }
            this.fontSelect(Fps.Frame, framerate, this.getX(), this.getY() + 10, Fps.TextColor.getcolor(), Fps.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
    }
}
