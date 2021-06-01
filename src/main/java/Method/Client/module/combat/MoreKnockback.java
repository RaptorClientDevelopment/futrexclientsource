package Method.Client.module.combat;

import Method.Client.module.*;
import net.minecraft.entity.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class MoreKnockback extends Module
{
    public MoreKnockback() {
        super("MoreKnockback", 0, Category.COMBAT, "More Knockback");
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (MoreKnockback.mc.field_71439_g.field_70122_E && side == Connection.Side.OUT && packet instanceof CPacketUseEntity) {
            final CPacketUseEntity attack = (CPacketUseEntity)packet;
            if (attack.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                final Entity entity = MoreKnockback.mc.field_71441_e.func_73045_a(attack.field_149567_a);
                if (entity != MoreKnockback.mc.field_71439_g && entity != null && entity.func_70032_d((Entity)MoreKnockback.mc.field_71439_g) < 4.0f) {
                    final boolean oldSprint = MoreKnockback.mc.field_71439_g.func_70051_ag();
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)MoreKnockback.mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)MoreKnockback.mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
                    MoreKnockback.mc.field_71439_g.func_70031_b(oldSprint);
                }
            }
        }
        return true;
    }
}
