package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import Method.Client.module.combat.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import Method.Client.utils.system.*;
import Method.Client.utils.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;

public class FoodMod extends Module
{
    Setting Souphunger;
    Setting Soup;
    Setting AntiHunger;
    Setting AutoEat;
    Setting SetFoodLevelMax;
    int slotBefore;
    int bestSlot;
    int eating;
    private int oldSlot;
    
    public FoodMod() {
        super("FoodMod", 0, Category.PLAYER, "FoodMod");
        this.Souphunger = Main.setmgr.add(new Setting("Hunger", this, 10.0, 0.0, 20.0, true));
        this.Soup = Main.setmgr.add(new Setting("Soup", this, false));
        this.AntiHunger = Main.setmgr.add(new Setting("AntiHunger", this, false));
        this.AutoEat = Main.setmgr.add(new Setting("AutoEat", this, false));
        this.SetFoodLevelMax = Main.setmgr.add(new Setting("SetFoodLevelMax", this, false));
        this.slotBefore = -1;
        this.bestSlot = -1;
        this.eating = 40;
        this.oldSlot = -1;
    }
    
    @Override
    public void onEnable() {
        this.oldSlot = -1;
        this.bestSlot = -1;
        super.onEnable();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.AntiHunger.getValBoolean() && packet instanceof CPacketPlayer) {
            final CPacketPlayer packet2 = (CPacketPlayer)packet;
            packet2.field_149474_g = (FoodMod.mc.field_71439_g.field_70143_R >= 0.0f || FoodMod.mc.field_71442_b.func_181040_m());
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.SetFoodLevelMax.getValBoolean()) {
            FoodMod.mc.field_71439_g.func_71024_bL().func_75114_a(20);
        }
        if (this.AutoEat.getValBoolean()) {
            if (this.oldSlot == -1) {
                if (!this.canEat()) {
                    return;
                }
                float bestSaturation = 0.0f;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(i);
                    if (this.isFood(stack)) {
                        final ItemFood food = (ItemFood)stack.func_77973_b();
                        final float saturation = food.func_150906_h(stack);
                        if (saturation > bestSaturation) {
                            bestSaturation = saturation;
                            this.bestSlot = i;
                        }
                    }
                }
                if (this.bestSlot != -1) {
                    this.oldSlot = FoodMod.mc.field_71439_g.field_71071_by.field_70461_c;
                }
            }
            else {
                if (!this.canEat()) {
                    this.stop();
                    return;
                }
                if (!this.isFood(FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(this.bestSlot))) {
                    this.stop();
                    return;
                }
                FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = this.bestSlot;
                FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = true;
            }
        }
        if (this.AntiHunger.getValBoolean()) {
            FoodMod.mc.field_71439_g.field_70122_E = false;
        }
        if (this.Soup.getValBoolean()) {
            for (int j = 0; j < 36; ++j) {
                final ItemStack stack2 = FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(j);
                if (stack2.func_77973_b() == Items.field_151054_z) {
                    if (j != 9) {
                        final ItemStack emptyBowlStack = FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(9);
                        final boolean swap = !AutoArmor.isNullOrEmpty(emptyBowlStack) && emptyBowlStack.func_77973_b() != Items.field_151054_z;
                        windowClick_PICKUP((j < 9) ? (36 + j) : j);
                        windowClick_PICKUP(9);
                        if (swap) {
                            windowClick_PICKUP((j < 9) ? (36 + j) : j);
                        }
                    }
                }
            }
            final int soupInHotbar = this.findSoup(0, 9);
            if (soupInHotbar != -1) {
                if (!this.shouldEatSoup()) {
                    this.stopIfEating();
                    return;
                }
                if (this.oldSlot == -1) {
                    this.oldSlot = FoodMod.mc.field_71439_g.field_71071_by.field_70461_c;
                }
                FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = soupInHotbar;
                FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = true;
                processRightClick();
                return;
            }
            else {
                this.stopIfEating();
                final int soupInInventory = this.findSoup(9, 36);
                if (soupInInventory != -1) {
                    windowClick_QUICK_MOVE(soupInInventory);
                }
            }
        }
        if (!this.Soup.getValBoolean()) {
            if (this.eating < 41) {
                ++this.eating;
                if (this.eating <= 1) {
                    FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = this.bestSlot;
                }
                FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = true;
                if (this.eating >= 38) {
                    FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = true;
                    if (this.slotBefore != -1) {
                        FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = this.slotBefore;
                    }
                    this.slotBefore = -1;
                }
                return;
            }
            float bestRestoration = 0.0f;
            this.bestSlot = -1;
            final int PrevSlot = FoodMod.mc.field_71439_g.field_71071_by.field_70461_c;
            for (int k = 0; k < 9; ++k) {
                final ItemStack item = FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(k);
                float restoration = 0.0f;
                if (item.func_77973_b() instanceof ItemFood) {
                    restoration = ((ItemFood)item.func_77973_b()).func_150906_h(item);
                }
                if (restoration > bestRestoration) {
                    bestRestoration = restoration;
                    this.bestSlot = k;
                }
            }
            if (this.bestSlot == -1) {
                return;
            }
            if (FoodMod.mc.field_71439_g.func_71024_bL().func_75116_a() >= this.Souphunger.getValDouble()) {
                return;
            }
            this.slotBefore = FoodMod.mc.field_71439_g.field_71071_by.field_70461_c;
            if (this.slotBefore == -1) {
                return;
            }
            FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = PrevSlot;
            FoodMod.mc.field_71439_g.func_184597_cx();
            FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = PrevSlot;
            this.eating = 0;
            super.onClientTick(event);
        }
    }
    
    private int findSoup(final int startSlot, final int endSlot) {
        for (int i = startSlot; i < endSlot; ++i) {
            final ItemStack stack = FoodMod.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() instanceof ItemSoup) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean canEat() {
        if (!FoodMod.mc.field_71439_g.func_71043_e(false)) {
            return false;
        }
        if (FoodMod.mc.field_71476_x != null) {
            final Entity entity = FoodMod.mc.field_71476_x.field_72308_g;
            if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
                return false;
            }
            final BlockPos pos = FoodMod.mc.field_71476_x.func_178782_a();
            if (pos != null) {
                final Block block = FoodMod.mc.field_71441_e.func_180495_p(pos).func_177230_c();
                if (block instanceof BlockContainer || block instanceof BlockWorkbench) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isFood(final ItemStack stack) {
        return stack.func_77973_b() instanceof ItemFood;
    }
    
    private void stop() {
        FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = false;
        FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot;
        this.oldSlot = -1;
    }
    
    private boolean shouldEatSoup() {
        if (FoodMod.mc.field_71439_g.func_110143_aJ() > 13.0) {
            return false;
        }
        if (Wrapper.mc.field_71462_r != null) {
            return false;
        }
        if (Wrapper.mc.field_71476_x == null) {
            return true;
        }
        final Entity entity = Wrapper.mc.field_71476_x.field_72308_g;
        if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
            return false;
        }
        Wrapper.mc.field_71476_x.func_178782_a();
        return !(getBlock(Wrapper.mc.field_71476_x.func_178782_a()) instanceof BlockContainer);
    }
    
    private void stopIfEating() {
        if (this.oldSlot == -1) {
            return;
        }
        FoodMod.mc.field_71474_y.field_74313_G.field_74513_e = true;
        FoodMod.mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot;
        this.oldSlot = -1;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return BlockUtils.getState(pos).func_177230_c();
    }
    
    public static ItemStack windowClick_PICKUP(final int slot) {
        return getPlayerController().func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)FoodMod.mc.field_71439_g);
    }
    
    private static PlayerControllerMP getPlayerController() {
        return FoodMod.mc.field_71442_b;
    }
    
    public static void processRightClick() {
        getPlayerController().func_187101_a((EntityPlayer)FoodMod.mc.field_71439_g, FoodMod.mc.field_71439_g.field_70170_p, EnumHand.MAIN_HAND);
    }
    
    public static ItemStack windowClick_QUICK_MOVE(final int slot) {
        return getPlayerController().func_187098_a(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)FoodMod.mc.field_71439_g);
    }
}
