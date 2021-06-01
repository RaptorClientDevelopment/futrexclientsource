package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;

@Cancelable
public final class RenderBlockModelEvent extends Event
{
    private final IBlockState state;
    
    public RenderBlockModelEvent(final IBlockState state) {
        this.state = state;
    }
    
    public IBlockState getState() {
        return this.state;
    }
}
