package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;

public final class PostMotionEvent extends Event
{
    private final EntityPlayerSP player;
    
    public PostMotionEvent(final EntityPlayerSP player) {
        this.player = player;
    }
    
    public EntityPlayerSP getPlayer() {
        return this.player;
    }
}
