package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import java.text.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;

public final class Angles extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Decimal;
    static Setting Shadow;
    static Setting FontSize;
    static Setting TrueYAW;
    static Setting Names;
    
    public Angles() {
        super("Angles", 0, Category.ONSCREEN, "Angles");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Angles.TrueYAW = new Setting("TrueYAW", this, false));
        Main.setmgr.add(Angles.Names = new Setting("Names", this, true));
        Main.setmgr.add(Angles.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Angles.BgColor = new Setting("BgColor", this, 0.22, 0.88, 0.22, 0.22));
        Main.setmgr.add(Angles.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Angles.background = new Setting("background", this, false));
        Main.setmgr.add(Angles.xpos = new Setting("xpos", this, 200.0, -20.0, Angles.mc.field_71443_c + 40, true));
        Main.setmgr.add(Angles.ypos = new Setting("ypos", this, 220.0, -20.0, Angles.mc.field_71440_d + 40, true));
        Main.setmgr.add(Angles.Decimal = new Setting("Decimal", this, 2.0, 0.0, 5.0, true));
        Main.setmgr.add(Angles.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Angles.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("AnglesSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("AnglesSET", false);
    }
    
    public static class AnglesRUN extends PinableFrame
    {
        DecimalFormat decimalFormat;
        
        public AnglesRUN() {
            super("AnglesSET", new String[0], (int)Angles.ypos.getValDouble(), (int)Angles.xpos.getValDouble());
            this.decimalFormat = new DecimalFormat("0.00");
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Angles.xpos, Angles.ypos, Angles.Frame, Angles.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Angles.xpos, Angles.ypos, Angles.Frame, Angles.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (Angles.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Angles.Frame, this.getCoords()), this.y + 20, Angles.background.getcolor());
            }
            this.fontSelect(Angles.Frame, this.getCoords(), this.getX(), this.getY() + 10, Angles.TextColor.getcolor(), Angles.Shadow.getValBoolean());
            if (Angles.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Angles.Frame, this.getCoords()), this.y + 22, Angles.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
        
        private String getCoords() {
            this.decimalFormat = this.getDecimalFormat((int)Angles.Decimal.getValDouble());
            final String Pitch = this.decimalFormat.format(this.mc.field_71439_g.field_70125_A);
            String Yaw;
            if (!Angles.TrueYAW.getValBoolean()) {
                Yaw = this.decimalFormat.format(this.mc.field_71439_g.field_70177_z % 360.0f);
            }
            else {
                Yaw = this.decimalFormat.format(this.mc.field_71439_g.field_70177_z);
            }
            if (Angles.Names.getValBoolean()) {
                return "Pitch " + Pitch + ", Yaw " + Yaw;
            }
            return Pitch + ", " + Yaw;
        }
    }
}
