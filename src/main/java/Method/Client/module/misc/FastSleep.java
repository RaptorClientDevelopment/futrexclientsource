package Method.Client.module.misc;

import Method.Client.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class FastSleep extends Module
{
    public FastSleep() {
        super("FastSleep", 0, Category.MISC, "Fast Sleep");
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final EntityPlayerSP player = FastSleep.mc.field_71439_g;
        if (player.func_70608_bn() && player.func_71060_bI() > 10) {
            player.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.STOP_SLEEPING));
        }
    }
}
