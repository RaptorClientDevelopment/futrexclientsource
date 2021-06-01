package Method.Client.module.player;

import Method.Client.utils.*;
import Method.Client.managers.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import Method.Client.module.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class ChestStealer extends Module
{
    private final TimerUtils Timer;
    Setting delay;
    Setting Entity;
    Setting Shulker;
    public static Setting Mode;
    
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER, "ChestStealer");
        this.Timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(ChestStealer.Mode = new Setting("Mode", this, "Steal", new String[] { "Steal", "Store", "Drop" }));
        Main.setmgr.add(this.delay = new Setting("Delay", this, 3.0, 0.0, 20.0, true));
        Main.setmgr.add(this.Shulker = new Setting("Take Shulker", this, false));
        Main.setmgr.add(this.Entity = new Setting("Entitys Chest", this, true));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!this.Timer.isDelay((long)(this.delay.getValDouble() * 100.0))) {
            return;
        }
        this.Timer.setLastMS();
        if (ChestStealer.mc.field_71462_r instanceof GuiChest) {
            final GuiChest guiChest = (GuiChest)ChestStealer.mc.field_71462_r;
            this.Quickhandle((GuiContainer)guiChest, guiChest.field_147015_w.func_70302_i_());
        }
        else if (ChestStealer.mc.field_71462_r instanceof GuiScreenHorseInventory && this.Entity.getValBoolean()) {
            final GuiScreenHorseInventory horseInventory = (GuiScreenHorseInventory)ChestStealer.mc.field_71462_r;
            this.Quickhandle((GuiContainer)horseInventory, horseInventory.field_147029_w.func_70302_i_());
        }
        else if (ChestStealer.mc.field_71462_r instanceof GuiShulkerBox && this.Shulker.getValBoolean()) {
            final GuiShulkerBox shulkerBox = (GuiShulkerBox)ChestStealer.mc.field_71462_r;
            this.Quickhandle((GuiContainer)ChestStealer.mc.field_71462_r, shulkerBox.field_190779_v.func_70302_i_());
        }
        else {
            ModuleManager.getModuleByName("ChestStealer").toggle();
        }
    }
    
    private void Quickhandle(final GuiContainer guiContainer, final int size) {
        for (int i = 0; i < size; ++i) {
            final ItemStack stack = (ItemStack)guiContainer.field_147002_h.func_75138_a().get(i);
            if ((stack.func_190926_b() || stack.func_77973_b() == Items.field_190931_a) && ChestStealer.Mode.getValString().equalsIgnoreCase("Store")) {
                this.HandleStoring(guiContainer.field_147002_h.field_75152_c, size - 9);
                return;
            }
            if (this.StealorDrop(guiContainer.field_147002_h.field_75152_c, i, stack)) {
                break;
            }
        }
    }
    
    private void HandleStoring(final int pWindowId, final int stack) {
        for (int i = 9; i < ChestStealer.mc.field_71439_g.field_71069_bz.field_75151_b.size() - 1; ++i) {
            final ItemStack itemStack = ChestStealer.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (!itemStack.func_190926_b()) {
                if (itemStack.func_77973_b() != Items.field_190931_a) {
                    if (!this.Shulker.getValBoolean() || itemStack.func_77973_b() instanceof ItemShulkerBox) {
                        ChestStealer.mc.field_71442_b.func_187098_a(pWindowId, i + stack, 0, ClickType.QUICK_MOVE, (EntityPlayer)ChestStealer.mc.field_71439_g);
                        return;
                    }
                }
            }
        }
    }
    
    private boolean StealorDrop(final int windowId, final int i, final ItemStack stack) {
        if (stack.func_190926_b() || (this.Shulker.getValBoolean() && !(stack.func_77973_b() instanceof ItemShulkerBox))) {
            return false;
        }
        if (ChestStealer.Mode.getValString().equalsIgnoreCase("Steal")) {
            ChestStealer.mc.field_71442_b.func_187098_a(windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)ChestStealer.mc.field_71439_g);
            return true;
        }
        if (ChestStealer.Mode.getValString().equalsIgnoreCase("Drop")) {
            ChestStealer.mc.field_71442_b.func_187098_a(windowId, i, 1, ClickType.THROW, (EntityPlayer)ChestStealer.mc.field_71439_g);
            return true;
        }
        return false;
    }
}
