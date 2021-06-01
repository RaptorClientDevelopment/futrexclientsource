package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.visual.*;

public class DiscordRPCModule extends Module
{
    public DiscordRPCModule() {
        super("DiscordRPC", 0, Category.MISC, "Discord Rich Presence");
    }
    
    @Override
    public void onEnable() {
        FutureXDiscordRPC.init(false);
        ChatUtils.warning("If Discord RPC doesn't appear you will need to relog");
    }
    
    @Override
    public void onDisable() {
        FutureXDiscordRPC.init(true);
    }
}
