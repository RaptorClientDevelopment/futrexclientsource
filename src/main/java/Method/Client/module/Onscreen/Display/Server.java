package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;

public final class Server extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Shadow;
    static Setting background;
    static Setting FontSize;
    
    public Server() {
        super("Server", 0, Category.ONSCREEN, "Server");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Server.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Server.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Server.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Server.background = new Setting("background", this, false));
        Main.setmgr.add(Server.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Server.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Server.xpos = new Setting("xpos", this, 200.0, -20.0, Server.mc.field_71443_c + 40, true));
        Main.setmgr.add(Server.ypos = new Setting("ypos", this, 170.0, -20.0, Server.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("ServerSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("ServerSET", false);
    }
    
    public static class ServerRUN extends PinableFrame
    {
        public ServerRUN() {
            super("ServerSET", new String[0], (int)Server.ypos.getValDouble(), (int)Server.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Server.xpos, Server.ypos, Server.Frame, Server.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Server.xpos, Server.ypos, Server.Frame, Server.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final String brand = (this.mc.func_147104_D() == null) ? "Vanilla" : this.mc.func_147104_D().field_82822_g;
            this.fontSelect(Server.Frame, brand, this.getX(), this.getY() + 10, Server.TextColor.getcolor(), Server.Shadow.getValBoolean());
            if (Server.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Server.Frame, brand), this.y + 20, Server.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
