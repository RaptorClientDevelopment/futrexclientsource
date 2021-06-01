package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import Method.Client.module.player.*;
import net.minecraft.client.gui.*;
import Method.Client.module.*;

public class ChestGuiInsert extends Screen
{
    @Override
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiChest) {
            if (event.getButton().field_146127_k == 11209) {
                ChestStealer.Mode.setValString("Steal");
                toggle2();
            }
            if (event.getButton().field_146127_k == 11210) {
                ChestStealer.Mode.setValString("Store");
                toggle2();
            }
            if (event.getButton().field_146127_k == 11211) {
                ChestStealer.Mode.setValString("Drop");
                toggle2();
            }
        }
    }
    
    @Override
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiChest) {
            event.getButtonList().add(new GuiButton(11209, event.getGui().field_146294_l / 2 + 100, event.getGui().field_146295_m / 2 - ((GuiChest)event.getGui()).getYSize() + 110, 50, 20, "Steal"));
            event.getButtonList().add(new GuiButton(11210, event.getGui().field_146294_l / 2 + 100, event.getGui().field_146295_m / 2 - ((GuiChest)event.getGui()).getYSize() + 130, 50, 20, "Store"));
            event.getButtonList().add(new GuiButton(11211, event.getGui().field_146294_l / 2 + 100, event.getGui().field_146295_m / 2 - ((GuiChest)event.getGui()).getYSize() + 150, 50, 20, "Drop"));
        }
    }
    
    private static void toggle2() {
        final Module Chest = ModuleManager.getModuleByName("ChestStealer");
        if (Chest.isToggled()) {
            Chest.toggle();
        }
        Chest.toggle();
    }
}
