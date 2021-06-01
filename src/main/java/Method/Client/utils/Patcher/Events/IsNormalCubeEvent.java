package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;

@Cancelable
public final class IsNormalCubeEvent extends Event
{
    private final IBlockState state;
    
    public IsNormalCubeEvent(final IBlockState state) {
        this.state = state;
    }
    
    public IBlockState getBlockState() {
        return this.state;
    }
}
