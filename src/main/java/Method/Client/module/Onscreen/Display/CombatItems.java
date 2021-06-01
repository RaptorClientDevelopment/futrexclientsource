package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import java.util.*;

public final class CombatItems extends Module
{
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    
    public CombatItems() {
        super("CombatItems", 0, Category.ONSCREEN, "CombatItems");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(CombatItems.BgColor = new Setting("BgColor", this, 0.22, 0.88, 0.22, 0.22));
        Main.setmgr.add(CombatItems.background = new Setting("background", this, false));
        Main.setmgr.add(CombatItems.xpos = new Setting("xpos", this, 200.0, -20.0, CombatItems.mc.field_71443_c + 40, true));
        Main.setmgr.add(CombatItems.ypos = new Setting("ypos", this, 110.0, -20.0, CombatItems.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("CombatItemsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("CombatItemsSET", false);
    }
    
    public static class CombatItemsRUN extends PinableFrame
    {
        ArrayList<ItemStack> itemStacks;
        ArrayList<Item> Itemslist;
        
        public CombatItemsRUN() {
            super("CombatItemsSET", new String[0], (int)CombatItems.ypos.getValDouble(), (int)CombatItems.xpos.getValDouble());
            this.itemStacks = new ArrayList<ItemStack>();
            this.Itemslist = new ArrayList<Item>();
        }
        
        @Override
        public void setup() {
            this.x = (int)CombatItems.xpos.getValDouble();
            this.y = (int)CombatItems.ypos.getValDouble();
        }
        
        @Override
        public void Ongui() {
            if (!this.getDrag()) {
                this.x = (int)CombatItems.xpos.getValDouble();
                this.y = (int)CombatItems.ypos.getValDouble();
            }
            else {
                CombatItems.xpos.setValDouble(this.x);
                CombatItems.ypos.setValDouble(this.y);
            }
        }
        
        public void setupitems() {
            this.itemStacks.clear();
            this.Itemslist.clear();
            this.itemStacks.add(new ItemStack(Items.field_151032_g, 1));
            this.itemStacks.add(new ItemStack(Items.field_185158_cP, 1));
            this.itemStacks.add(new ItemStack(Items.field_151153_ao, 1, 1));
            this.itemStacks.add(new ItemStack(Items.field_190929_cY, 1));
            this.itemStacks.add(new ItemStack(Items.field_151062_by, 1));
            this.itemStacks.add(new ItemStack(Items.field_151079_bi, 1));
            this.itemStacks.add(new ItemStack(Items.field_185161_cS, 1));
            this.itemStacks.add(new ItemStack(Item.func_150899_d(49), 1));
            this.Itemslist.add(Items.field_185158_cP);
            this.Itemslist.add(Items.field_151032_g);
            this.Itemslist.add(Items.field_151153_ao);
            this.Itemslist.add(Items.field_190929_cY);
            this.Itemslist.add(Items.field_151062_by);
            this.Itemslist.add(Items.field_185161_cS);
            this.Itemslist.add(Item.func_150899_d(49));
            this.Itemslist.add(Items.field_185167_i);
            this.Itemslist.add(Items.field_185166_h);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71439_g == null) {
                return;
            }
            this.setupitems();
            for (final ItemStack itemStack : this.mc.field_71439_g.field_71071_by.field_70462_a) {
                if (this.Itemslist.contains(itemStack.func_77973_b())) {
                    for (final ItemStack stack : this.itemStacks) {
                        if (itemStack.func_77973_b().equals(Items.field_185167_i) || (itemStack.func_77973_b().equals(Items.field_185166_h) && stack.func_77973_b().equals(Items.field_151032_g))) {
                            stack.func_190920_e(stack.func_190916_E() + itemStack.func_190916_E());
                        }
                        if (Objects.equals(stack.func_77973_b().getRegistryName(), itemStack.func_77973_b().getRegistryName())) {
                            stack.func_190920_e(stack.func_190916_E() + itemStack.func_190916_E());
                        }
                    }
                }
            }
            int offset = 0;
            RenderHelper.func_74520_c();
            for (final ItemStack itemStack2 : this.itemStacks) {
                itemStack2.func_190920_e(itemStack2.func_190916_E() - 1);
                if (itemStack2.func_190916_E() >= 1) {
                    this.mc.func_175599_af().func_180450_b(itemStack2, this.getX() + offset, this.getY() - 3);
                    this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack2, this.getX() + offset, this.getY() - 3, (String)null);
                    offset += 19;
                }
            }
            if (CombatItems.background.getValBoolean()) {
                RenderUtils.drawRectDouble(this.getX(), this.getY(), this.getX() + offset + 10, this.getY() + 20, CombatItems.BgColor.getcolor());
            }
            RenderHelper.func_74518_a();
            this.mc.func_175599_af().field_77023_b = 0.0f;
            super.onRenderGameOverlay(event);
        }
    }
}
