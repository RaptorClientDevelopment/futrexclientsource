package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.player.*;
import Method.Client.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Method.Client.utils.system.*;

public class SchematicaNCP extends Module
{
    private final TimerUtils timer;
    Setting KeepRotation;
    public float[] Rots;
    
    public SchematicaNCP() {
        super("PrinterBypass", 0, Category.PLAYER, "PrinterBypass");
        this.timer = new TimerUtils();
        this.KeepRotation = Main.setmgr.add(new Setting("Keep Rotation", this, true));
    }
    
    @Override
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        this.timer.setLastMS();
        final float[] array = Utils.getNeededRotations(event.getHitVec(), 0.0f, 0.0f);
        SchematicaNCP.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(array[0], array[1], SchematicaNCP.mc.field_71439_g.field_70122_E));
        this.Rots = array;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.KeepRotation.getValBoolean() && this.Rots != null && (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation) && !this.timer.isDelay(4000L)) {
            final CPacketPlayer packet2 = (CPacketPlayer)packet;
            packet2.field_149476_e = this.Rots[0];
            packet2.field_149473_f = this.Rots[1];
        }
        return true;
    }
}
