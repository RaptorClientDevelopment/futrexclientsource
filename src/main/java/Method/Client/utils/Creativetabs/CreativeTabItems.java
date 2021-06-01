package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class CreativeTabItems extends CreativeTabs
{
    ArrayList<Enchantment> Enchants;
    ArrayList<Integer> Levels;
    ItemStack Blankspot;
    
    public CreativeTabItems() {
        super("Items");
        this.Enchants = new ArrayList<Enchantment>();
        this.Levels = new ArrayList<Integer>();
        this.Blankspot = new ItemStack(Items.field_151118_aC);
    }
    
    public String func_78013_b() {
        return "Items";
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        this.Blankspot.func_190920_e(-1);
        this.Enchants.add(Enchantments.field_185302_k);
        this.Levels.add(32767);
        Creativetabhelper.Packsize(Items.field_151048_u, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_185302_k);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_180313_o);
        this.Levels.add(2);
        this.Enchants.add(Enchantments.field_77334_n);
        this.Levels.add(2);
        this.Enchants.add(Enchantments.field_185304_p);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_191530_r);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_185303_l);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_180312_n);
        this.Levels.add(5);
        this.Mendundbr();
        Creativetabhelper.Packsize(Items.field_151048_u, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.PicRepeat();
        this.Enchants.add(Enchantments.field_185306_r);
        this.Levels.add(1);
        Creativetabhelper.Packsize(Items.field_151046_w, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.PicRepeat();
        itemList.add((Object)this.Blankspot);
        Creativetabhelper.Packsize(Items.field_151046_w, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.Enchants.add(Enchantments.field_180312_n);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_185305_q);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_185308_t);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_185302_k);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_185303_l);
        this.Levels.add(5);
        this.Mendundbr();
        Creativetabhelper.Packsize(Items.field_151056_x, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.PicRepeat();
        itemList.add((Object)this.Blankspot);
        Creativetabhelper.Packsize(Items.field_151047_v, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.Mendundbr();
        Creativetabhelper.Packsize(Items.field_151012_L, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_185305_q);
        this.Levels.add(5);
        this.Mendundbr();
        Creativetabhelper.Packsize((Item)Items.field_151097_aZ, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.Enchants.add(Enchantments.field_185310_v);
        this.Levels.add(2);
        this.Enchants.add(Enchantments.field_185309_u);
        this.Levels.add(5);
        this.Bowrepeat(itemList);
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_185310_v);
        this.Levels.add(32767);
        this.Enchants.add(Enchantments.field_185309_u);
        this.Levels.add(32767);
        this.Bowrepeat(itemList);
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(3);
        Creativetabhelper.Packsize(Items.field_185160_cR, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(32767);
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(32767);
        Creativetabhelper.Packsize(Items.field_185160_cR, this.Enchants, this.Levels, itemList);
        this.clearvar();
        this.Enchants.add(Enchantments.field_151370_z);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_151369_A);
        this.Levels.add(3);
        this.Mendundbr();
        Creativetabhelper.Packsize((Item)Items.field_151112_aM, this.Enchants, this.Levels, itemList);
        this.clearvar();
        itemList.add((Object)this.Blankspot);
        this.Enchants.add(Enchantments.field_151370_z);
        this.Levels.add(32767);
        this.Enchants.add(Enchantments.field_151369_A);
        this.Levels.add(32767);
        this.Mendundbr();
        Creativetabhelper.Packsize((Item)Items.field_151112_aM, this.Enchants, this.Levels, itemList);
        this.clearvar();
        super.func_78018_a((NonNullList)itemList);
    }
    
    private void PicRepeat() {
        this.Enchants.add(Enchantments.field_185305_q);
        this.Levels.add(5);
        this.Enchants.add(Enchantments.field_185308_t);
        this.Levels.add(3);
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(3);
    }
    
    private void Mendundbr() {
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(3);
    }
    
    private void Bowrepeat(final NonNullList<ItemStack> itemList) {
        this.Enchants.add(Enchantments.field_185311_w);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185312_x);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185296_A);
        this.Levels.add(1);
        this.Enchants.add(Enchantments.field_185307_s);
        this.Levels.add(3);
        Creativetabhelper.Packsize((Item)Items.field_151031_f, this.Enchants, this.Levels, itemList);
        this.clearvar();
    }
    
    private void clearvar() {
        this.Enchants.clear();
        this.Levels.clear();
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack(Items.field_151056_x);
    }
}
