package Method.Client.module.player;

import Method.Client.module.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

public class BuildHeight extends Module
{
    public BuildHeight() {
        super("BuildHeight", 0, Category.PLAYER, "Interact at Build Height");
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)packet;
            if (packet2.func_187023_a().func_177956_o() >= 255 && packet2.func_187024_b() == EnumFacing.UP) {
                packet2.field_149579_d = EnumFacing.DOWN;
            }
        }
        return true;
    }
}
