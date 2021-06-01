package Method.Client.module.player;

import Method.Client.module.*;
import Method.Client.utils.Patcher.Events.*;

public class LiquidInteract extends Module
{
    public LiquidInteract() {
        super("LiquidInteract", 0, Category.PLAYER, "LiquidInteract");
    }
    
    @Override
    public void EventCanCollide(final EventCanCollide event) {
        event.setCanceled(true);
    }
    
    @Override
    public void GetLiquidCollisionBoxEvent(final GetLiquidCollisionBoxEvent event) {
        event.setSolidCollisionBox();
    }
}
