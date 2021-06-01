package Method.Client.utils.Creativetabs;

import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;

public class Creativetabhelper
{
    public static ItemStack ItemWithEnchants(final Item foe, final ArrayList<Enchantment> enchants, final ArrayList<Integer> levels) {
        final ItemStack item = new ItemStack(foe);
        try {
            int forlevels = 0;
            for (final Enchantment ench : enchants) {
                if (item.field_77990_d == null) {
                    item.func_77982_d(new NBTTagCompound());
                }
                if (!item.field_77990_d.func_150297_b("ench", 9)) {
                    item.field_77990_d.func_74782_a("ench", (NBTBase)new NBTTagList());
                }
                final NBTTagList nbttaglist = item.field_77990_d.func_150295_c("ench", 10);
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.func_74777_a("id", (short)Enchantment.func_185258_b(ench));
                nbttagcompound.func_74768_a("lvl", (int)levels.get(forlevels));
                nbttaglist.func_74742_a((NBTBase)nbttagcompound);
                ++forlevels;
            }
        }
        catch (Exception ex) {}
        return item;
    }
    
    static void Unbreakpack(final Item item, final ArrayList<Enchantment> enchants, final ArrayList<Integer> levels, final NonNullList<ItemStack> itemList) {
        if (enchants == null && levels == null) {
            return;
        }
        final ItemStack D32k = ItemWithEnchants(item, enchants, levels);
        D32k.func_77983_a("Unbreakable", (NBTBase)new NBTTagInt(1));
        itemList.add((Object)D32k);
    }
    
    static void Attributeitems(final Item item, final ArrayList<Enchantment> enchants, final ArrayList<Integer> levels, final NonNullList<ItemStack> itemList, final EntityEquipmentSlot ro, boolean Enchanted) {
        if (enchants == null && levels == null) {
            Enchanted = false;
        }
        ItemStack D32k;
        if (Enchanted) {
            D32k = ItemWithEnchants(item, enchants, levels);
        }
        else {
            D32k = new ItemStack(item);
            if (D32k.field_77990_d == null) {
                D32k.func_77982_d(new NBTTagCompound());
            }
        }
        if (ro == EntityEquipmentSlot.MAINHAND) {
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), ro);
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), EntityEquipmentSlot.OFFHAND);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), ro);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), EntityEquipmentSlot.OFFHAND);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), ro);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.OFFHAND);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), ro);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), EntityEquipmentSlot.OFFHAND);
        }
        if (ro == EntityEquipmentSlot.OFFHAND) {
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.MAINHAND);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.OFFHAND);
        }
        if (ro == EntityEquipmentSlot.HEAD) {
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost", -20.0, 0), EntityEquipmentSlot.MAINHAND);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost", -20.0, 0), EntityEquipmentSlot.OFFHAND);
        }
        if (ro == EntityEquipmentSlot.CHEST) {
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), EntityEquipmentSlot.HEAD);
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), EntityEquipmentSlot.CHEST);
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), EntityEquipmentSlot.LEGS);
            D32k.func_185129_a("generic.attackDamage", new AttributeModifier("Weapon modifier", 20.0, 0), EntityEquipmentSlot.FEET);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), EntityEquipmentSlot.HEAD);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), EntityEquipmentSlot.CHEST);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), EntityEquipmentSlot.LEGS);
            D32k.func_185129_a("generic.attackSpeed", new AttributeModifier("Tool modifier", 20.0, 0), EntityEquipmentSlot.FEET);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.HEAD);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.CHEST);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.LEGS);
            D32k.func_185129_a("generic.movementSpeed", new AttributeModifier("Sprinting speed boost", 2.0, 0), EntityEquipmentSlot.FEET);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), EntityEquipmentSlot.HEAD);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), EntityEquipmentSlot.CHEST);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), EntityEquipmentSlot.LEGS);
            D32k.func_185129_a("generic.maxHealth", new AttributeModifier("effect.healthBoost 255", 200.0, 0), EntityEquipmentSlot.FEET);
            D32k.func_185129_a("generic.armorToughness", new AttributeModifier("Armor toughness", 20.0, 0), EntityEquipmentSlot.HEAD);
            D32k.func_185129_a("generic.armorToughness", new AttributeModifier("Armor toughness", 20.0, 0), EntityEquipmentSlot.CHEST);
            D32k.func_185129_a("generic.armorToughness", new AttributeModifier("Armor toughness", 20.0, 0), EntityEquipmentSlot.LEGS);
            D32k.func_185129_a("generic.armorToughness", new AttributeModifier("Armor toughness", 20.0, 0), EntityEquipmentSlot.FEET);
        }
        itemList.add((Object)D32k);
    }
    
    static void Packsize(final Item item, final ArrayList<Enchantment> enchants, final ArrayList<Integer> levels, final NonNullList<ItemStack> itemList) {
        if (enchants == null && levels == null) {
            return;
        }
        final ItemStack D32k = ItemWithEnchants(item, enchants, levels);
        itemList.add((Object)D32k);
        final ItemStack DSword64 = ItemWithEnchants(item, enchants, levels);
        DSword64.func_190920_e(64);
        itemList.add((Object)DSword64);
        final ItemStack Damaged = ItemWithEnchants(item, enchants, levels);
        Damaged.func_77964_b(Damaged.func_77958_k() + 100);
        itemList.add((Object)Damaged);
        final ItemStack Damage64 = ItemWithEnchants(item, enchants, levels);
        Damage64.func_190920_e(64);
        Damage64.func_77964_b(Damage64.func_77958_k() + 100);
        itemList.add((Object)Damage64);
    }
}
