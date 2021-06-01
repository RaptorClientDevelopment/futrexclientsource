package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import java.text.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;

public final class Coords extends Module
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
    
    public Coords() {
        super("Coords", 0, Category.ONSCREEN, "Coords");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Coords.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Coords.BgColor = new Setting("BgColor", this, 0.22, 0.88, 0.22, 0.22));
        Main.setmgr.add(Coords.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Coords.background = new Setting("background", this, false));
        Main.setmgr.add(Coords.xpos = new Setting("xpos", this, 200.0, -20.0, Coords.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Coords.ypos = new Setting("ypos", this, 30.0, -20.0, Coords.mc.field_71440_d / 2 + 40, true));
        Main.setmgr.add(Coords.Decimal = new Setting("Decimal", this, 2.0, 0.0, 5.0, true));
        Main.setmgr.add(Coords.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Coords.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("CoordsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("CoordsSET", false);
    }
    
    public static class CoordsRUN extends PinableFrame
    {
        DecimalFormat decimalFormat;
        
        public CoordsRUN() {
            super("CoordsSET", new String[0], (int)Coords.ypos.getValDouble(), (int)Coords.xpos.getValDouble());
            this.decimalFormat = new DecimalFormat("0.00");
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Coords.xpos, Coords.ypos, Coords.Frame, Coords.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Coords.xpos, Coords.ypos, Coords.Frame, Coords.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (Coords.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Coords.Frame, this.getCoords()), this.y + 20, Coords.background.getcolor());
            }
            this.fontSelect(Coords.Frame, this.getCoords(), this.getX(), this.getY() + 10, Coords.TextColor.getcolor(), Coords.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
        
        private String getCoords() {
            this.decimalFormat = this.getDecimalFormat((int)Coords.Decimal.getValDouble());
            final String x = this.decimalFormat.format(this.mc.field_71439_g.field_70165_t);
            final String y = this.decimalFormat.format(this.mc.field_71439_g.field_70163_u);
            final String z = this.decimalFormat.format(this.mc.field_71439_g.field_70161_v);
            final String coords = x + ", " + y + ", " + z + ChatFormatting.GRAY;
            return coords;
        }
    }
}
