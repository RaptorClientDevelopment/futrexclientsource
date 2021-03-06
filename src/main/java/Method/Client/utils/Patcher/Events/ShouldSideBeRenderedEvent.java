package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;

public final class ShouldSideBeRenderedEvent extends Event
{
    private final IBlockState state;
    private boolean rendered;
    private final boolean normallyRendered;
    
    public ShouldSideBeRenderedEvent(final IBlockState state, final boolean rendered) {
        this.state = state;
        this.rendered = rendered;
        this.normallyRendered = rendered;
    }
    
    public IBlockState getState() {
        return this.state;
    }
    
    public boolean isRendered() {
        return this.rendered;
    }
    
    public void setRendered(final boolean rendered) {
        this.rendered = rendered;
    }
    
    public boolean isNormallyRendered() {
        return this.normallyRendered;
    }
}
