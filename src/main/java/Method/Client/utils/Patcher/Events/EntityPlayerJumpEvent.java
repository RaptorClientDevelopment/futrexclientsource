package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;

@Cancelable
public final class EntityPlayerJumpEvent extends Event
{
    private float jumpHeight;
    private final EntityPlayer player;
    
    public EntityPlayerJumpEvent(final EntityPlayer player) {
        this.player = player;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public void setJumpHeight(final float jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
}
