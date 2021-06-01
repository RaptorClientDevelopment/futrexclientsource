package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import net.minecraft.item.*;

public final class Inventory extends Module
{
    static Setting BgColor;
    static Setting background;
    static Setting Hotbar;
    static Setting Xcarry;
    static Setting xpos;
    static Setting ypos;
    
    public Inventory() {
        super("Inventory", 0, Category.ONSCREEN, "Inventory");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Inventory.BgColor = new Setting("BgColor", this, 0.22, 0.88, 0.22, 0.22));
        Main.setmgr.add(Inventory.background = new Setting("background", this, false));
        Main.setmgr.add(Inventory.Hotbar = new Setting("Hotbar", this, false));
        Main.setmgr.add(Inventory.Xcarry = new Setting("Xcarry", this, false));
        Main.setmgr.add(Inventory.xpos = new Setting("xpos", this, 200.0, -20.0, Inventory.mc.field_71443_c + 40, true));
        Main.setmgr.add(Inventory.ypos = new Setting("ypos", this, 110.0, -20.0, Inventory.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("InventorySET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("InventorySET", false);
    }
    
    public static class InventoryRUN extends PinableFrame
    {
        public InventoryRUN() {
            super("InventorySET", new String[0], (int)Inventory.ypos.getValDouble(), (int)Inventory.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.x = (int)Inventory.xpos.getValDouble();
            this.y = (int)Inventory.ypos.getValDouble();
        }
        
        @Override
        public void Ongui() {
            if (!this.getDrag()) {
                this.x = (int)Inventory.xpos.getValDouble();
                this.y = (int)Inventory.ypos.getValDouble();
            }
            else {
                Inventory.xpos.setValDouble(this.x);
                Inventory.ypos.setValDouble(this.y);
            }
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71439_g == null) {
                return;
            }
            RenderHelper.func_74520_c();
            if (Inventory.background.getValBoolean()) {
                RenderUtils.drawRectDouble(this.getX(), this.getY(), this.getX() + this.getWidth() + 60, this.getY() + 50 + (Inventory.Hotbar.getValBoolean() ? 25 : 0), Inventory.BgColor.getcolor());
            }
            for (int i = 0; i < 27; ++i) {
                final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71071_by.field_70462_a.get(i + 9);
                final int offsetX = this.getX() + i % 9 * 16;
                final int offsetY = this.getY() + i / 9 * 16;
                this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
                this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
            }
            if (Inventory.Hotbar.getValBoolean()) {
                for (int i = 0; i < 9; ++i) {
                    final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71071_by.field_70462_a.get(i);
                    final int offsetX = this.getX() + i % 9 * 16;
                    final int offsetY = this.getY() + 48;
                    this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
                    this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
                }
            }
            if (Inventory.Xcarry.getValBoolean()) {
                for (int i = 0; i < 5; ++i) {
                    final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71069_bz.func_75138_a().get(i);
                    final int offsetX = this.getX() + i * 16;
                    final int offsetY = this.getY() + 60;
                    this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
                    this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
                }
            }
            RenderHelper.func_74518_a();
            this.mc.func_175599_af().field_77023_b = 0.0f;
            super.onRenderGameOverlay(event);
        }
    }
}
