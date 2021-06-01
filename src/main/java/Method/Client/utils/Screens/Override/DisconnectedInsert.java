package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.text.*;
import java.nio.channels.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class DisconnectedInsert extends Screen
{
    public static ServerData lastserver;
    public static boolean Autolog;
    public static boolean doPing;
    public static boolean Connect;
    public static int ping;
    
    @Override
    public void onWorldUnload(final WorldEvent.Unload event) {
        final ServerData data = DisconnectedInsert.mc.func_147104_D();
        if (data != null) {
            DisconnectedInsert.lastserver = data;
        }
    }
    
    @Override
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiDisconnected) {
            if (event.getButton().field_146127_k == 997) {
                DisconnectedInsert.doPing = true;
            }
            if (event.getButton().field_146127_k == 999) {
                final ServerAddress serveraddress = ServerAddress.func_78860_a(DisconnectedInsert.lastserver.field_78845_b);
                DisconnectedInsert.mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), DisconnectedInsert.mc, DisconnectedInsert.lastserver.field_78845_b, serveraddress.func_78864_b()));
            }
            if (event.getButton().field_146127_k == 998) {
                DisconnectedInsert.Autolog = !DisconnectedInsert.Autolog;
                final double Starttime = System.currentTimeMillis() + 5000L;
                final double n;
                new Thread(() -> {
                    try {
                        while (n >= System.currentTimeMillis() && DisconnectedInsert.Autolog) {
                            DisconnectedInsert.Connect = false;
                            event.getButton().field_146126_j = "Auto " + TextFormatting.GOLD + (n - System.currentTimeMillis()) + TextFormatting.RESET + "ms Relog";
                        }
                        DisconnectedInsert.Connect = true;
                    }
                    catch (Exception ex) {}
                }).start();
            }
        }
    }
    
    @Override
    public void DrawScreenEvent(final GuiScreenEvent.DrawScreenEvent event) {
        if (event.getGui() instanceof GuiDisconnected) {
            if (DisconnectedInsert.Autolog && DisconnectedInsert.Connect) {
                DisconnectedInsert.Connect = false;
                final ServerAddress serveraddress = ServerAddress.func_78860_a(DisconnectedInsert.lastserver.field_78845_b);
                DisconnectedInsert.mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), DisconnectedInsert.mc, DisconnectedInsert.lastserver.field_78845_b, serveraddress.func_78864_b()));
            }
            final ServerAddress serveraddress = ServerAddress.func_78860_a(DisconnectedInsert.lastserver.field_78845_b);
            event.getGui().func_73732_a(event.getGui().field_146289_q, DisconnectedInsert.lastserver.field_78845_b + " Port: " + serveraddress.func_78864_b(), event.getGui().field_146294_l / 2, event.getGui().field_146295_m / 2 - 50, 11184810);
            event.getGui().func_73732_a(event.getGui().field_146289_q, "Ping: " + DisconnectedInsert.ping, event.getGui().field_146294_l / 2, event.getGui().field_146295_m / 2 - 65, 11184810);
            if (DisconnectedInsert.doPing) {
                DisconnectedInsert.doPing = false;
                try {
                    final String hostAddress = DisconnectedInsert.lastserver.field_78845_b;
                    final int port = serveraddress.func_78864_b();
                    long timeToRespond = 0L;
                    final InetAddress inetAddress = InetAddress.getByName(hostAddress);
                    final InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
                    final SocketChannel sc = SocketChannel.open();
                    sc.configureBlocking(true);
                    final Date start = new Date();
                    if (sc.connect(socketAddress)) {
                        final Date stop = new Date();
                        timeToRespond = stop.getTime() - start.getTime();
                    }
                    DisconnectedInsert.ping = (int)timeToRespond;
                }
                catch (IOException ex) {}
            }
        }
    }
    
    @Override
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiConnecting) {
            DisconnectedInsert.lastserver = DisconnectedInsert.mc.func_147104_D();
        }
        if (event.getGui() instanceof GuiDisconnected) {
            event.getButtonList().add(new GuiButton(999, event.getGui().field_146294_l / 2 - 100, Math.min(event.getGui().field_146295_m / 2 + 10 + event.getGui().field_146289_q.field_78288_b, event.getGui().field_146295_m - 30) + 20, 200, 20, "Relog"));
            event.getButtonList().add(new GuiButton(998, event.getGui().field_146294_l / 2 - 100, Math.min(event.getGui().field_146295_m / 2 + 10 + event.getGui().field_146289_q.field_78288_b, event.getGui().field_146295_m - 30) + 40, 200, 20, "Auto 5s Relog"));
            event.getButtonList().add(new GuiButton(997, event.getGui().field_146294_l / 2 - 100, Math.min(event.getGui().field_146295_m / 2 + 10 + event.getGui().field_146289_q.field_78288_b, event.getGui().field_146295_m - 30) + 60, 200, 20, "Ping"));
            if (DisconnectedInsert.Autolog) {
                DisconnectedInsert.Autolog = false;
                GuiButton button = null;
                try {
                    for (final GuiButton guiButton : event.getButtonList()) {
                        if (guiButton.field_146127_k == 998) {
                            button = guiButton;
                            ((GuiDisconnected)event.getGui()).func_146284_a(guiButton);
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.ActionPerformedEvent.Post(event.getGui(), button, event.getGui().field_146292_n));
            }
        }
    }
    
    static {
        DisconnectedInsert.Autolog = false;
        DisconnectedInsert.doPing = false;
        DisconnectedInsert.Connect = false;
        DisconnectedInsert.ping = 0;
    }
}
