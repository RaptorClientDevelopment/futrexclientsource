package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import java.lang.reflect.*;
import net.minecraftforge.client.event.*;
import java.text.*;
import net.minecraft.client.gui.*;

public final class Hunger extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Background;
    static Setting Shadow;
    static Setting FontSize;
    
    public Hunger() {
        super("Hunger", 0, Category.ONSCREEN, "Hunger");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Hunger.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Hunger.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Hunger.Background = new Setting("Background", this, false));
        Main.setmgr.add(Hunger.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Hunger.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Hunger.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Hunger.xpos = new Setting("xpos", this, 200.0, -20.0, Hunger.mc.field_71443_c + 40, true));
        Main.setmgr.add(Hunger.ypos = new Setting("ypos", this, 100.0, -20.0, Hunger.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("HungerSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("HungerSET", false);
    }
    
    public static class HungerRUN extends PinableFrame
    {
        public static Field foodExhaustionLevel;
        
        public HungerRUN() {
            super("HungerSET", new String[0], (int)Hunger.ypos.getValDouble(), (int)Hunger.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Hunger.xpos, Hunger.ypos, Hunger.Frame, Hunger.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Hunger.xpos, Hunger.ypos, Hunger.Frame, Hunger.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final DecimalFormat dc = new DecimalFormat("#.##");
            final String cow = "Hunger: " + this.mc.field_71439_g.func_71024_bL().func_75116_a() + " Saturation: " + dc.format(this.mc.field_71439_g.func_71024_bL().func_75115_e());
            this.fontSelect(Hunger.Frame, cow, this.getX(), this.getY() + 10, Hunger.TextColor.getcolor(), Hunger.Shadow.getValBoolean());
            if (Hunger.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Hunger.Frame, cow), this.y + 20, Hunger.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
