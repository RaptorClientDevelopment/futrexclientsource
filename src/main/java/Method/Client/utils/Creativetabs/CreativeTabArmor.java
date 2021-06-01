package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class CreativeTabArmor extends CreativeTabs
{
    ArrayList<Enchantment> Enchants;
    ArrayList<Integer> Levels;
    ItemStack Blankspot;
    
    public CreativeTabArmor() {
        super("Armor");
        this.Enchants = new ArrayList<Enchantment>();
        this.Levels = new ArrayList<Integer>();
        this.Blankspot = new ItemStack(Items.field_151118_aC);
    }
    
    public String func_78013_b() {
        return "Armor";
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        this.Blankspot.func_190920_e(-1);
        this.MaxVanillaArmor(itemList, 0);
        this.MaxVanillaArmor(itemList, 1);
        this.MaxVanillaArmor(itemList, 2);
        this.MaxVanillaArmor(itemList, 3);
        this.MaxVanillaArmor(itemList, 4);
        super.func_78018_a((NonNullList)itemList);
    }
    
    private void MaxVanillaArmor(final NonNullList<ItemStack> itemList, final int Switch) {
        this.Enchants.add(Enchantments.field_185298_f);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_185299_g);
        this.Levels.add(1);
        this.Enchantsetup(Switch);
        Creativetabhelper.Packsize((Item)Items.field_151161_ac, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchantsetup(Switch);
        Creativetabhelper.Packsize((Item)Items.field_151163_ad, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.Enchantsetup(Switch);
        Creativetabhelper.Packsize((Item)Items.field_151173_ae, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_185300_i);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_180309_e);
        this.Levels.add(4);
        this.Enchants.add(Enchantments.field_185301_j);
        this.Levels.add(2);
        this.Enchantsetup(Switch);
        Creativetabhelper.Packsize((Item)Items.field_151175_af, this.Enchants, this.Levels, itemList);
        this.clearvar();
    }
    
    private void Enchantsetup(final int Switch) {
        if (Switch == 0 || Switch == 1) {
            this.Enchants.add(Enchantments.field_185297_d);
            this.Levels.add(4);
        }
        if (Switch == 0 || Switch == 2) {
            this.Enchants.add(Enchantments.field_77329_d);
            this.Levels.add(4);
        }
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(1);
        if (Switch == 0 || Switch == 3) {
            this.Enchants.add(Enchantments.field_180308_g);
            this.Levels.add(4);
        }
        if (Switch == 0 || Switch == 4) {
            this.Enchants.add(Enchantments.field_180310_c);
            this.Levels.add(4);
        }
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(3);
    }
    
    private void clearvar() {
        this.Enchants.clear();
        this.Levels.clear();
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack((Item)Items.field_151163_ad);
    }
}
