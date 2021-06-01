package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import Method.Client.utils.system.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import java.net.*;
import java.io.*;

public final class ServerResponce extends Module
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
    private static long serverLastUpdated;
    
    public ServerResponce() {
        super("ServerResponce", 0, Category.ONSCREEN, "ServerResponce");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(ServerResponce.Delay = new Setting("Delay", this, 1.0, 1.0, 10.0, true));
        Main.setmgr.add(ServerResponce.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(ServerResponce.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(ServerResponce.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(ServerResponce.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(ServerResponce.Background = new Setting("Background", this, false));
        Main.setmgr.add(ServerResponce.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(ServerResponce.xpos = new Setting("xpos", this, 200.0, -20.0, ServerResponce.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(ServerResponce.ypos = new Setting("ypos", this, 180.0, -20.0, ServerResponce.mc.field_71440_d / 2 + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("ServerResponceSET", true);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN) {
            ServerResponce.serverLastUpdated = System.currentTimeMillis();
        }
        return true;
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("ServerResponceSET", false);
    }
    
    static {
        ServerResponce.serverLastUpdated = 0L;
    }
    
    public static class ServerResponceRUN extends PinableFrame
    {
        String text;
        private static long startTime;
        
        public ServerResponceRUN() {
            super("ServerResponceSET", new String[0], (int)ServerResponce.ypos.getValDouble(), (int)ServerResponce.xpos.getValDouble());
            this.text = "Server Not Responding! ";
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, ServerResponce.xpos, ServerResponce.ypos, ServerResponce.Frame, ServerResponce.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, ServerResponce.xpos, ServerResponce.ypos, ServerResponce.Frame, ServerResponce.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.func_71356_B()) {
                return;
            }
            if (this.mc.field_71462_r != null && !(this.mc.field_71462_r instanceof GuiChat)) {
                return;
            }
            if (ServerResponce.Delay.getValDouble() * 1000.0 > System.currentTimeMillis() - ServerResponce.serverLastUpdated) {
                return;
            }
            if (this.shouldPing()) {
                if (isDown("1.1.1.1", 80, 1111)) {
                    this.text = "Your internet is offline! ";
                }
                else {
                    this.text = "Server Not Responding! ";
                }
            }
            this.text = this.text.replaceAll("! .*", "! " + this.timeDifference() + "s");
            if (ServerResponce.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(ServerResponce.Frame, this.text), this.y + 20, ServerResponce.BgColor.getcolor());
            }
            this.fontSelect(ServerResponce.Frame, this.text, this.getX(), this.getY() + 10, ServerResponce.TextColor.getcolor(), ServerResponce.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
        
        private double timeDifference() {
            return (System.currentTimeMillis() - ServerResponce.serverLastUpdated) / 1000.0;
        }
        
        public static boolean isDown(final String host, final int port, final int timeout) {
            try (final Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), timeout);
                return false;
            }
            catch (IOException e) {
                return true;
            }
        }
        
        private boolean shouldPing() {
            if (ServerResponceRUN.startTime == 0L) {
                ServerResponceRUN.startTime = System.currentTimeMillis();
            }
            if (ServerResponceRUN.startTime + 1000L <= System.currentTimeMillis()) {
                ServerResponceRUN.startTime = System.currentTimeMillis();
                return true;
            }
            return false;
        }
        
        static {
            ServerResponceRUN.startTime = 0L;
        }
    }
}
