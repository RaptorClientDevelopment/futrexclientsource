package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.utils.system.*;
import net.minecraftforge.fml.common.network.internal.*;
import net.minecraft.network.play.client.*;
import io.netty.buffer.*;
import net.minecraft.network.*;

public class AntiHandShake extends Module
{
    public AntiHandShake() {
        super("AntiHandShake", 0, Category.MISC, "No Mod List sent on login");
    }
    
    @Override
    public boolean onDisablePacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketResourcePackStatus) {
            ((CPacketResourcePackStatus)packet).field_179719_b = CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
        }
        if (side == Connection.Side.OUT) {
            if (packet instanceof FMLProxyPacket && !AntiHandShake.mc.func_71356_B()) {
                return false;
            }
            if (packet instanceof CPacketCustomPayload && !AntiHandShake.mc.func_71356_B()) {
                final CPacketCustomPayload packet2 = (CPacketCustomPayload)packet;
                if (packet2.func_149559_c().equals("MC|Brand")) {
                    packet2.field_149561_c = new PacketBuffer(Unpooled.buffer()).func_180714_a("vanilla");
                }
            }
        }
        return true;
    }
}
