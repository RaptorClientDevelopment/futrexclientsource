package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.util.*;

public class CreativeTabEggpot extends CreativeTabs
{
    ArrayList<Enchantment> Enchants;
    ArrayList<Integer> Levels;
    ItemStack Blankspot;
    
    public CreativeTabEggpot() {
        super("Items");
        this.Enchants = new ArrayList<Enchantment>();
        this.Levels = new ArrayList<Integer>();
        this.Blankspot = new ItemStack(Items.field_151118_aC);
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        this.Blankspot.func_190920_e(-1);
        try {
            for (final ResourceLocation dan : LootTableList.func_186374_a()) {
                final ItemStack Entitydrop = new ItemStack(Items.field_151063_bx);
                Entitydrop.func_77982_d(JsonToNBT.func_180713_a("{EntityTag:{DeathLootTable:\"" + dan.func_110624_b() + "\",id:\"minecraft:bat\",ActiveEffects:[{Duration:2147483647,Id:7,Amplifier:0}]}}"));
                itemList.add((Object)Entitydrop);
            }
        }
        catch (NBTException e) {
            e.printStackTrace();
        }
        super.func_78018_a((NonNullList)itemList);
    }
    
    private void clearvar() {
        this.Enchants.clear();
        this.Levels.clear();
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack(Items.field_151110_aK);
    }
}
