package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.tileentity.*;

@Cancelable
public final class RenderTileEntityEvent extends Event
{
    private final TileEntity tileEntity;
    
    public RenderTileEntityEvent(final TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }
    
    public TileEntity getTileEntity() {
        return this.tileEntity;
    }
}
