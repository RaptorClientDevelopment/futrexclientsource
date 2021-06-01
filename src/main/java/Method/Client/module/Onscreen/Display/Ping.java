package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;

public final class Ping extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting Shadow;
    static Setting Frame;
    static Setting ypos;
    static Setting FontSize;
    
    public Ping() {
        super("Ping", 0, Category.ONSCREEN, "Ping");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Ping.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Ping.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Ping.background = new Setting("background", this, false));
        Main.setmgr.add(Ping.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Ping.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Ping.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Ping.xpos = new Setting("xpos", this, 200.0, -20.0, Ping.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Ping.ypos = new Setting("ypos", this, 130.0, -20.0, Ping.mc.field_71440_d / 2 + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("PingSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("PingSET", false);
    }
    
    public static class PingRUN extends PinableFrame
    {
        public PingRUN() {
            super("PingSET", new String[0], (int)Ping.ypos.getValDouble(), (int)Ping.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Ping.xpos, Ping.ypos, Ping.Frame, Ping.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Ping.xpos, Ping.ypos, Ping.Frame, Ping.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
                return;
            }
            final NetworkPlayerInfo playerInfo = Objects.requireNonNull(this.mc.func_147114_u()).func_175102_a(this.mc.field_71439_g.func_110124_au());
            final String ping = "MS: " + playerInfo.func_178853_c();
            this.fontSelect(Ping.Frame, ping, this.getX(), this.getY() + 10, Ping.TextColor.getcolor(), Ping.Shadow.getValBoolean());
            if (Ping.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Ping.Frame, ping), this.y + 20, Ping.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
