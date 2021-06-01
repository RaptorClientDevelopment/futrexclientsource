package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import java.text.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;

public final class NetherCords extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    static Setting Shadow;
    static Setting Decimal;
    static Setting Frame;
    static Setting FontSize;
    
    public NetherCords() {
        super("NetherCords", 0, Category.ONSCREEN, "NetherCords");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(NetherCords.TextColor = new Setting("TextColor", this, 1.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(NetherCords.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(NetherCords.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(NetherCords.background = new Setting("background", this, false));
        Main.setmgr.add(NetherCords.xpos = new Setting("xpos", this, 200.0, -20.0, NetherCords.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(NetherCords.ypos = new Setting("ypos", this, 120.0, -20.0, NetherCords.mc.field_71440_d / 2 + 40, true));
        Main.setmgr.add(NetherCords.Decimal = new Setting("Decimal", this, 2.0, 0.0, 5.0, true));
        Main.setmgr.add(NetherCords.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(NetherCords.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("NetherCordsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("NetherCordsSET", false);
    }
    
    public static class NetherCordsRUN extends PinableFrame
    {
        DecimalFormat decimalFormat;
        
        public NetherCordsRUN() {
            super("NetherCordsSET", new String[0], (int)NetherCords.ypos.getValDouble(), (int)NetherCords.xpos.getValDouble());
            this.decimalFormat = new DecimalFormat("0.00");
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, NetherCords.xpos, NetherCords.ypos, NetherCords.Frame, NetherCords.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, NetherCords.xpos, NetherCords.ypos, NetherCords.Frame, NetherCords.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            this.fontSelect(NetherCords.Frame, this.getCoords(), this.getX(), this.getY() + 10, NetherCords.TextColor.getcolor(), NetherCords.Shadow.getValBoolean());
            if (NetherCords.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(NetherCords.Frame, this.getCoords()), this.y + 20, NetherCords.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
        
        private String getCoords() {
            this.decimalFormat = this.getDecimalFormat((int)NetherCords.Decimal.getValDouble());
            final String x = this.decimalFormat.format(this.mc.field_71439_g.field_70165_t / 8.0);
            final String y = this.decimalFormat.format(this.mc.field_71439_g.field_70163_u);
            final String z = this.decimalFormat.format(this.mc.field_71439_g.field_70161_v / 8.0);
            final String coords = x + ", " + y + ", " + z + ChatFormatting.GRAY;
            return coords;
        }
    }
}
