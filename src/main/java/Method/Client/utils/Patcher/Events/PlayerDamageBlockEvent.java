package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public final class PlayerDamageBlockEvent extends Event
{
    private final BlockPos pos;
    private final EnumFacing facing;
    
    public PlayerDamageBlockEvent(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
}
