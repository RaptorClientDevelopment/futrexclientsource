package Method.Client.module.player;

import Method.Client.module.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class AutoFish extends Module
{
    public AutoFish() {
        super("AutoFish", 0, Category.PLAYER, "AutoFish");
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect)packet;
            if (packet2.func_186978_a().equals(SoundEvents.field_187609_F)) {
                new Thread(() -> {
                    try {
                        AutoFish.mc.field_71442_b.func_187101_a((EntityPlayer)AutoFish.mc.field_71439_g, AutoFish.mc.field_71439_g.field_70170_p, EnumHand.MAIN_HAND);
                        Thread.sleep(300L);
                        AutoFish.mc.field_71442_b.func_187101_a((EntityPlayer)AutoFish.mc.field_71439_g, AutoFish.mc.field_71439_g.field_70170_p, EnumHand.MAIN_HAND);
                    }
                    catch (Exception ex) {}
                    return;
                }).start();
            }
        }
        return true;
    }
}
