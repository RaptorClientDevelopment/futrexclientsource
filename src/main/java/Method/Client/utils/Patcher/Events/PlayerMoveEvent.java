package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;

public final class PlayerMoveEvent extends Event
{
    private final EntityPlayerSP player;
    
    public PlayerMoveEvent(final EntityPlayerSP player) {
        this.player = player;
    }
    
    public EntityPlayerSP getPlayer() {
        return this.player;
    }
}
