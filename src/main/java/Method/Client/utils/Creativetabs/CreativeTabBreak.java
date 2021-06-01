package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;

public class CreativeTabBreak extends CreativeTabs
{
    ItemStack Blankspot;
    
    public CreativeTabBreak() {
        super("Break");
        this.Blankspot = new ItemStack(Items.field_151118_aC);
    }
    
    public String func_78013_b() {
        return "Break";
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        final ArrayList<Enchantment> AllEnchant = new ArrayList<Enchantment>();
        final ArrayList<Integer> AllLevel32k = new ArrayList<Integer>();
        int Simple30 = 0;
        for (final Enchantment e : Enchantment.field_185264_b) {
            if (Simple30 <= 30) {
                AllEnchant.add(e);
                AllLevel32k.add(32767);
            }
            ++Simple30;
        }
        this.Blankspot.func_190920_e(-1);
        Creativetabhelper.Attributeitems(Items.field_151048_u, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems(Items.field_151046_w, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems(Items.field_151056_x, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems(Items.field_151047_v, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems(Items.field_151012_L, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151097_aZ, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151031_f, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems(Items.field_185160_cR, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151112_aM, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151161_ac, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151163_ad, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151173_ae, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, true);
        Creativetabhelper.Attributeitems((Item)Items.field_151175_af, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, true);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        Creativetabhelper.Unbreakpack(Items.field_151048_u, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack(Items.field_151046_w, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack(Items.field_151056_x, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack(Items.field_151047_v, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack(Items.field_151012_L, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151097_aZ, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151031_f, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack(Items.field_185160_cR, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151112_aM, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151161_ac, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151163_ad, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151173_ae, AllEnchant, AllLevel32k, itemList);
        Creativetabhelper.Unbreakpack((Item)Items.field_151175_af, AllEnchant, AllLevel32k, itemList);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        Creativetabhelper.Attributeitems(Items.field_151048_u, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems(Items.field_151046_w, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems(Items.field_151056_x, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems(Items.field_151047_v, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems(Items.field_151012_L, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151097_aZ, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151031_f, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems(Items.field_185160_cR, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151112_aM, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.MAINHAND, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151161_ac, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151163_ad, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151173_ae, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, false);
        Creativetabhelper.Attributeitems((Item)Items.field_151175_af, AllEnchant, AllLevel32k, itemList, EntityEquipmentSlot.CHEST, false);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        itemList.add((Object)this.Blankspot);
        super.func_78018_a((NonNullList)itemList);
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack(Items.field_151032_g);
    }
}
