package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class CreativeTabBlocks extends CreativeTabs
{
    public CreativeTabBlocks() {
        super("Blocks");
    }
    
    public String func_78013_b() {
        return "Blocks";
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        itemList.add((Object)new ItemStack(Items.field_151095_cc));
        itemList.add((Object)new ItemStack(Items.field_151164_bB));
        itemList.add((Object)new ItemStack((Item)Items.field_151098_aY));
        itemList.add((Object)new ItemStack(Blocks.field_150483_bI));
        itemList.add((Object)new ItemStack(Blocks.field_185777_dd));
        itemList.add((Object)new ItemStack(Blocks.field_185776_dc));
        itemList.add((Object)new ItemStack(Blocks.field_185779_df));
        itemList.add((Object)new ItemStack(Blocks.field_189881_dj));
        itemList.add((Object)new ItemStack(Blocks.field_150380_bt));
        itemList.add((Object)new ItemStack(Blocks.field_180401_cv));
        itemList.add((Object)new ItemStack(Blocks.field_185774_da));
        itemList.add((Object)new ItemStack(Blocks.field_150420_aW));
        itemList.add((Object)new ItemStack(Blocks.field_150419_aX));
        itemList.add((Object)new ItemStack(Blocks.field_150458_ak));
        itemList.add((Object)new ItemStack(Blocks.field_150474_ac));
        itemList.add((Object)new ItemStack((Block)Blocks.field_150329_H));
        final ItemStack Furnace = new ItemStack(Blocks.field_150460_al);
        try {
            Furnace.func_77982_d(JsonToNBT.func_180713_a("{BlockStateTag:{lit:\"true\"}}"));
        }
        catch (NBTException e) {
            e.printStackTrace();
        }
        itemList.add((Object)Furnace);
        final ItemStack Water = new ItemStack(Items.field_151131_as);
        Water.func_190920_e(64);
        itemList.add((Object)Water);
        final ItemStack Lava = new ItemStack(Items.field_151129_at);
        Lava.func_190920_e(64);
        itemList.add((Object)Lava);
        final ItemStack Bucket = new ItemStack(Items.field_151133_ar);
        Bucket.func_190920_e(64);
        itemList.add((Object)Bucket);
        final ItemStack Epearl = new ItemStack(Items.field_151079_bi);
        Epearl.func_190920_e(64);
        itemList.add((Object)Epearl);
        final ItemStack egg = new ItemStack(Items.field_151110_aK);
        egg.func_190920_e(64);
        itemList.add((Object)egg);
        final ItemStack Sign = new ItemStack(Items.field_151155_ap);
        Sign.func_190920_e(64);
        itemList.add((Object)Sign);
        final ItemStack Banner = new ItemStack(Items.field_179564_cE);
        Banner.func_190920_e(64);
        itemList.add((Object)Banner);
        final ItemStack Snowball = new ItemStack(Items.field_151126_ay);
        Snowball.func_190920_e(64);
        itemList.add((Object)Snowball);
        final ItemStack Bed = new ItemStack(Items.field_151104_aV);
        Bed.func_190920_e(64);
        itemList.add((Object)Bed);
        final ItemStack Boat = new ItemStack(Items.field_151124_az);
        Boat.func_190920_e(64);
        itemList.add((Object)Boat);
        final ItemStack Cake = new ItemStack(Items.field_151105_aU);
        Cake.func_190920_e(64);
        itemList.add((Object)Cake);
        final ItemStack Totm = new ItemStack(Items.field_190929_cY);
        Totm.func_190920_e(64);
        itemList.add((Object)Totm);
        final ItemStack Shul = new ItemStack(Item.func_150899_d(229));
        Shul.func_190920_e(64);
        itemList.add((Object)Shul);
        final ItemStack Mush = new ItemStack(Items.field_151009_A);
        Mush.func_190920_e(64);
        itemList.add((Object)Mush);
        final ItemStack Saddle = new ItemStack(Items.field_151141_av);
        Saddle.func_190920_e(64);
        itemList.add((Object)Saddle);
        final ItemStack Tntmc = new ItemStack(Items.field_151142_bV);
        Tntmc.func_190920_e(64);
        itemList.add((Object)Tntmc);
        final ItemStack Minecart = new ItemStack(Items.field_151143_au);
        Minecart.func_190920_e(64);
        itemList.add((Object)Minecart);
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack(Items.field_151069_bo);
    }
}
