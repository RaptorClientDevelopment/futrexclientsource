package Method.Client;

import net.minecraft.client.*;
import club.minnced.discord.rpc.*;

public class FutureXDiscordRPC
{
    private static final String ClientId = "842487217473323088";
    private static final Minecraft mc;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static String details;
    private static String state;
    private static String version;
    
    public static void init(final boolean stop) {
        if (stop) {
            FutureXDiscordRPC.rpc.Discord_Shutdown();
            return;
        }
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        FutureXDiscordRPC.rpc.Discord_Initialize("842487217473323088", handlers, true, "");
        FutureXDiscordRPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        FutureXDiscordRPC.presence.details = "FutureX on top";
        FutureXDiscordRPC.presence.state = "Main Menu";
        FutureXDiscordRPC.presence.largeImageKey = "futurex";
        FutureXDiscordRPC.presence.largeImageText = "v" + FutureXDiscordRPC.version;
        FutureXDiscordRPC.rpc.Discord_UpdatePresence(FutureXDiscordRPC.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    FutureXDiscordRPC.rpc.Discord_RunCallbacks();
                    FutureXDiscordRPC.details = "FutureX on top";
                    FutureXDiscordRPC.state = "";
                    if (FutureXDiscordRPC.mc.func_71387_A()) {
                        FutureXDiscordRPC.state = "Playing Singleplayer";
                    }
                    else if (FutureXDiscordRPC.mc.func_147104_D() != null) {
                        if (!FutureXDiscordRPC.mc.func_147104_D().field_78845_b.equals("")) {
                            FutureXDiscordRPC.state = "Playing " + FutureXDiscordRPC.mc.func_147104_D().field_78845_b;
                        }
                    }
                    else {
                        FutureXDiscordRPC.state = "Main Menu";
                    }
                    if (!FutureXDiscordRPC.details.equals(FutureXDiscordRPC.presence.details) || !FutureXDiscordRPC.state.equals(FutureXDiscordRPC.presence.state)) {
                        FutureXDiscordRPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    FutureXDiscordRPC.presence.details = FutureXDiscordRPC.details;
                    FutureXDiscordRPC.presence.state = FutureXDiscordRPC.state;
                    FutureXDiscordRPC.rpc.Discord_UpdatePresence(FutureXDiscordRPC.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
            FutureXDiscordRPC.rpc.Discord_Shutdown();
        }, "Discord-RPC-Callback-Handler").start();
    }
    
    static {
        mc = Minecraft.func_71410_x();
        rpc = DiscordRPC.INSTANCE;
        FutureXDiscordRPC.presence = new DiscordRichPresence();
        FutureXDiscordRPC.version = "0.0.1";
    }
}
