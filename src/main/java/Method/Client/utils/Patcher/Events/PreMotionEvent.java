package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;

public final class PreMotionEvent extends Event
{
    private final EntityPlayerSP player;
    
    public PreMotionEvent(final EntityPlayerSP player) {
        this.player = player;
    }
    
    public EntityPlayerSP getPlayer() {
        return this.player;
    }
}
