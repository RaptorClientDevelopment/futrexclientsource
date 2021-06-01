package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class AntiCrash extends Module
{
    Setting slime;
    Setting offhand;
    Setting Sound;
    
    public AntiCrash() {
        super("AntiCrash", 0, Category.MISC, "Anti Crash");
        this.slime = Main.setmgr.add(new Setting("slime", this, true));
        this.offhand = Main.setmgr.add(new Setting("offhand", this, true));
        this.Sound = Main.setmgr.add(new Setting("Sound", this, true));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN && packet instanceof SPacketSoundEffect && this.offhand.getValBoolean()) {
            return ((SPacketSoundEffect)packet).func_186978_a() != SoundEvents.field_187719_p;
        }
        if (packet instanceof SPacketSoundEffect && this.Sound.getValBoolean()) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect)packet;
            return packet2.func_186977_b() != SoundCategory.PLAYERS || packet2.func_186978_a() != SoundEvents.field_187719_p;
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Objects.nonNull(AntiCrash.mc.field_71441_e) && this.slime.getValBoolean()) {
            final EntitySlime slime;
            AntiCrash.mc.field_71441_e.field_72996_f.forEach(e -> {
                if (e instanceof EntitySlime) {
                    slime = e;
                    if (slime.func_70809_q() > 4) {
                        AntiCrash.mc.field_71441_e.func_72900_e((Entity)e);
                    }
                }
            });
        }
    }
}
