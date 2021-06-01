package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class Refill extends Module
{
    Setting delay;
    Setting percentage;
    Setting offHand;
    private final TimerUtils timer;
    
    public Refill() {
        super("Refill", 0, Category.COMBAT, "Refill");
        this.delay = Main.setmgr.add(new Setting("delay", this, 5.0, 0.0, 10.0, true));
        this.percentage = Main.setmgr.add(new Setting("percentage", this, 50.0, 0.0, 100.0, false));
        this.offHand = Main.setmgr.add(new Setting("offHand", this, true));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay((long)(this.delay.getValDouble() * 1000.0)) && Refill.mc.field_71462_r instanceof GuiInventory) {
            return;
        }
        final int toRefill = this.getRefillable(Refill.mc.field_71439_g);
        if (toRefill != -1) {
            this.refillHotbarSlot(toRefill);
        }
    }
    
    private int getRefillable(final EntityPlayerSP player) {
        if (this.offHand.getValBoolean() && player.func_184592_cb().func_77973_b() != Items.field_190931_a && player.func_184592_cb().func_190916_E() < player.func_184592_cb().func_77976_d() && player.func_184592_cb().func_190916_E() / player.func_184592_cb().func_77976_d() <= this.percentage.getValDouble() / 100.0) {
            return 45;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
            if (stack.func_77973_b() != Items.field_190931_a && stack.func_190916_E() < stack.func_77976_d() && stack.func_190916_E() / stack.func_77976_d() <= this.percentage.getValDouble() / 100.0) {
                return i;
            }
        }
        return -1;
    }
    
    private int getSmallestStack(final EntityPlayerSP player, final ItemStack itemStack) {
        if (itemStack == null) {
            return -1;
        }
        int minCount = itemStack.func_77976_d() + 1;
        int minIndex = -1;
        for (int i = 9; i < player.field_71071_by.field_70462_a.size(); ++i) {
            final ItemStack stack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
            if (stack.func_77973_b() != Items.field_190931_a && stack.func_77973_b() == itemStack.func_77973_b() && stack.func_190916_E() < minCount) {
                minCount = stack.func_190916_E();
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    public void refillHotbarSlot(final int slot) {
        ItemStack stack;
        if (slot == 45) {
            stack = Refill.mc.field_71439_g.func_184592_cb();
        }
        else {
            stack = (ItemStack)Refill.mc.field_71439_g.field_71071_by.field_70462_a.get(slot);
        }
        if (stack.func_77973_b() == Items.field_190931_a) {
            return;
        }
        final int biggestStack = this.getSmallestStack(Refill.mc.field_71439_g, stack);
        if (biggestStack == -1) {
            return;
        }
        if (slot == 45) {
            Refill.mc.field_71442_b.func_187098_a(Refill.mc.field_71439_g.field_71069_bz.field_75152_c, biggestStack, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.field_71439_g);
            Refill.mc.field_71442_b.func_187098_a(Refill.mc.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.field_71439_g);
            Refill.mc.field_71442_b.func_187098_a(Refill.mc.field_71439_g.field_71069_bz.field_75152_c, biggestStack, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.field_71439_g);
            return;
        }
        int overflow = -1;
        for (int i = 0; i < 9 && overflow == -1; ++i) {
            if (((ItemStack)Refill.mc.field_71439_g.field_71071_by.field_70462_a.get(i)).func_77973_b() == Items.field_190931_a) {
                overflow = i;
            }
        }
        Refill.mc.field_71442_b.func_187098_a(Refill.mc.field_71439_g.field_71069_bz.field_75152_c, biggestStack, 0, ClickType.QUICK_MOVE, (EntityPlayer)Refill.mc.field_71439_g);
        if (overflow != -1 && ((ItemStack)Refill.mc.field_71439_g.field_71071_by.field_70462_a.get(overflow)).func_77973_b() != Items.field_190931_a) {
            Refill.mc.field_71442_b.func_187098_a(Refill.mc.field_71439_g.field_71069_bz.field_75152_c, biggestStack, overflow, ClickType.SWAP, (EntityPlayer)Refill.mc.field_71439_g);
        }
    }
}
