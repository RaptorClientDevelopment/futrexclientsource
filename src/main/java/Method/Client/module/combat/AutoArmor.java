package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;

public class AutoArmor extends Module
{
    private int timer;
    Setting useEnchantments;
    Setting swapWhileMoving;
    Setting delay;
    Setting nocurse;
    Setting Elytra;
    Setting Edam;
    boolean ElytraSwitch;
    
    public AutoArmor() {
        super("Auto Armor", 0, Category.COMBAT, "Puts on any Armor");
        this.useEnchantments = Main.setmgr.add(new Setting("Enchantments", this, true));
        this.swapWhileMoving = Main.setmgr.add(new Setting("SwapWhileMoving", this, true));
        this.delay = Main.setmgr.add(new Setting("Delay", this, 1.0, 0.0, 5.0, true));
        this.nocurse = Main.setmgr.add(new Setting("No Binding", this, true));
        this.Elytra = Main.setmgr.add(new Setting("Elytra Over Chest", this, true));
        this.Edam = Main.setmgr.add(new Setting("Elytra Damage", this, 2.0, 0.0, 320.0, true));
        this.ElytraSwitch = false;
    }
    
    @Override
    public void onEnable() {
        this.ElytraSwitch = false;
        this.timer = 0;
        super.onEnable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        if (Wrapper.INSTANCE.mc().field_71462_r instanceof GuiContainer && !(Wrapper.INSTANCE.mc().field_71462_r instanceof InventoryEffectRenderer)) {
            return;
        }
        final InventoryPlayer inventory = AutoArmor.mc.field_71439_g.field_71071_by;
        if (!this.swapWhileMoving.getValBoolean() && (AutoArmor.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f || AutoArmor.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int type = 0; type < 4; ++type) {
            bestArmorSlots[type] = -1;
            final ItemStack stack = inventory.func_70440_f(type);
            if (this.Elytra.getValBoolean() && type == 2 && stack.func_77973_b() instanceof ItemElytra) {
                if (stack.func_190926_b()) {
                    this.ElytraSwitch = false;
                }
                if (stack.func_77973_b().getDamage(stack) > stack.func_77973_b().getMaxDamage(stack) - this.Edam.getValDouble()) {
                    this.ElytraSwitch = false;
                }
            }
            else if (!isNullOrEmpty(stack)) {
                if (stack.func_77973_b() instanceof ItemArmor) {
                    final ItemArmor item = (ItemArmor)stack.func_77973_b();
                    bestArmorValues[type] = this.getArmorValue(item, stack);
                }
            }
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = inventory.func_70301_a(slot);
            if (stack.func_77973_b() instanceof ItemElytra && this.Elytra.getValBoolean() && !this.ElytraSwitch) {
                if (stack.func_77973_b().getDamage(stack) <= stack.func_77973_b().getMaxDamage(stack) - this.Edam.getValDouble()) {
                    bestArmorSlots[2] = slot;
                    this.ElytraSwitch = true;
                }
            }
            else if (!isNullOrEmpty(stack)) {
                if (stack.func_77973_b() instanceof ItemArmor) {
                    if (!this.nocurse.getValBoolean() || !EnchantmentHelper.func_190938_b(stack)) {
                        final ItemArmor item = (ItemArmor)stack.func_77973_b();
                        final int armorType = item.field_77881_a.func_188454_b();
                        final int armorValue = this.getArmorValue(item, stack);
                        if (armorValue > bestArmorValues[armorType]) {
                            bestArmorSlots[armorType] = slot;
                            bestArmorValues[armorType] = armorValue;
                        }
                    }
                }
            }
        }
        final ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(types);
        for (final int type2 : types) {
            int slot2 = bestArmorSlots[type2];
            if (slot2 == -1) {
                continue;
            }
            if (inventory.func_70440_f(type2).func_77973_b() instanceof ItemElytra && this.Elytra.getValBoolean()) {
                final ItemStack stack2 = inventory.func_70440_f(type2);
                if (stack2.func_77973_b().getDamage(stack2) <= stack2.func_77973_b().getMaxDamage(stack2) - this.Edam.getValDouble()) {
                    continue;
                }
                Wrapper.INSTANCE.mc().field_71442_b.func_187098_a(0, 8 - type2, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.field_71439_g);
                this.ElytraSwitch = false;
            }
            final ItemStack oldArmor = inventory.func_70440_f(type2);
            if (!isNullOrEmpty(oldArmor) && inventory.func_70447_i() == -1) {
                continue;
            }
            if (slot2 < 9) {
                slot2 += 36;
            }
            if (!isNullOrEmpty(oldArmor)) {
                Wrapper.INSTANCE.mc().field_71442_b.func_187098_a(0, 8 - type2, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.field_71439_g);
            }
            Wrapper.INSTANCE.mc().field_71442_b.func_187098_a(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.field_71439_g);
            break;
        }
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketClickWindow) {
            this.timer = (int)this.delay.getValDouble();
        }
        return true;
    }
    
    public static boolean isNullOrEmpty(final ItemStack stack) {
        return stack == null || stack.func_190926_b();
    }
    
    int getArmorValue(final ItemArmor item, final ItemStack stack) {
        final int armorPoints = item.field_77879_b;
        int prtPoints = 0;
        final int armorToughness = (int)item.field_189415_e;
        final int armorType = item.func_82812_d().func_78044_b(EntityEquipmentSlot.LEGS);
        if (this.useEnchantments.getValBoolean()) {
            final Enchantment protection = Enchantments.field_180310_c;
            final int prtLvl = EnchantmentHelper.func_77506_a(protection, stack);
            final DamageSource dmgSource = DamageSource.func_76365_a((EntityPlayer)AutoArmor.mc.field_71439_g);
            prtPoints = protection.func_77318_a(prtLvl, dmgSource);
        }
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }
}
