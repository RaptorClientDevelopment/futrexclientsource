package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import Method.Client.module.*;
import net.minecraft.client.gui.*;
import Method.Client.module.misc.*;

public class DeathOverride extends Screen
{
    public static boolean isDead;
    public static boolean Override;
    
    @Override
    public void GuiOpen(final GuiOpenEvent event) {
        final boolean host = ModuleManager.getModuleByName("Ghost").isToggled();
        if (host && event.getGui() instanceof GuiGameOver) {
            event.setGui((GuiScreen)null);
            DeathOverride.isDead = true;
            DeathOverride.mc.field_71439_g.func_70606_j((float)Ghost.health.getValDouble() / 2.0f);
        }
    }
    
    static {
        DeathOverride.Override = false;
    }
}
