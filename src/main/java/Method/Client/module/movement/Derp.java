package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.visual.*;
import net.minecraftforge.event.entity.living.*;
import java.util.concurrent.*;
import Method.Client.utils.Patcher.Events.*;
import Method.Client.utils.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;

public class Derp extends Module
{
    Setting Silent;
    Setting Yaw;
    Setting Pitch;
    Setting illegal;
    
    public Derp() {
        super("Derp", 0, Category.MOVEMENT, "Derp");
        this.Silent = Main.setmgr.add(new Setting("Silent", this, true));
        this.Yaw = Main.setmgr.add(new Setting("Yaw", this, true));
        this.Pitch = Main.setmgr.add(new Setting("Pitch", this, true));
        this.illegal = Main.setmgr.add(new Setting("illegal Range?", this, false));
    }
    
    @Override
    public void onEnable() {
        if (this.illegal.getValBoolean()) {
            ChatUtils.warning("Going beyond max normally possible");
        }
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        final int doubleit = this.illegal.getValBoolean() ? 2 : 1;
        if (!this.Silent.getValBoolean()) {
            if (this.Yaw.getValBoolean()) {
                Derp.mc.field_71439_g.field_70177_z = (float)ThreadLocalRandom.current().nextDouble(-180 * doubleit, 180 * doubleit);
            }
            if (this.Pitch.getValBoolean()) {
                Derp.mc.field_71439_g.field_70125_A = (float)ThreadLocalRandom.current().nextDouble(-90 * doubleit, 90 * doubleit);
            }
        }
    }
    
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        Derp.mc.field_71439_g.field_70759_as = Utils.random(-180, 180);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.Silent.getValBoolean()) {
            final int doubleit = this.illegal.getValBoolean() ? 2 : 1;
            if ((packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation) && side == Connection.Side.OUT) {
                final CPacketPlayer packet2 = (CPacketPlayer)packet;
                if (this.Pitch.getValBoolean()) {
                    packet2.field_149473_f = Utils.random(-180 * doubleit, 180 * doubleit);
                }
                if (this.Yaw.getValBoolean()) {
                    packet2.field_149476_e = Utils.random(-90 * doubleit, 90 * doubleit);
                }
            }
        }
        return true;
    }
}
