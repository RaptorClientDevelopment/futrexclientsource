package Method.Client.utils.Screens.Custom.Esp;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import Method.Client.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;

public final class EspGui extends GuiScreen
{
    private static ListGui listGui;
    private ListGui AllMobs;
    private GuiTextField MobFieldName;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton doneButton;
    private String MobToAdd;
    private String MobToRemove;
    public static ArrayList<String> mobs;
    public static ArrayList<String> allmobs;
    
    public boolean func_73868_f() {
        return false;
    }
    
    public static List<String> RemoveMobs(final List<String> input) {
        EspGui.allmobs.clear();
        for (final ResourceLocation resourceLocation : EntityList.func_180124_b()) {
            if (!input.contains(resourceLocation.toString())) {
                EspGui.allmobs.add(resourceLocation.toString());
            }
        }
        return EspGui.allmobs;
    }
    
    public static List<String> MobSearch(final String text) {
        final ArrayList<String> Temp = new ArrayList<String>();
        for (final ResourceLocation object : EntityList.func_180124_b()) {
            final String s = object.toString();
            if (object.toString().contains(text)) {
                Temp.add(s);
            }
        }
        return Temp;
    }
    
    public void func_73866_w_() {
        EspGui.listGui = new ListGui(this.field_146297_k, this, EspGui.mobs, this.field_146294_l - this.field_146294_l / 3, 0);
        this.AllMobs = new ListGui(this.field_146297_k, this, RemoveMobs(EspGui.mobs), 0, 0);
        this.MobFieldName = new GuiTextField(1, this.field_146297_k.field_71466_p, 64, this.field_146295_m - 55, 150, 18);
        this.field_146292_n.add(this.addButton = new GuiButton(0, 214, this.field_146295_m - 56, 30, 20, "Add"));
        this.field_146292_n.add(this.removeButton = new GuiButton(1, this.field_146294_l - 300, this.field_146295_m - 56, 100, 20, "Remove Selected"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 108, 8, 100, 20, "Remove All"));
        this.field_146292_n.add(new GuiButton(20, this.field_146294_l - 308, 8, 100, 20, "Add Passive"));
        this.field_146292_n.add(new GuiButton(40, this.field_146294_l - 208, 8, 100, 20, "Add Hostile"));
        this.field_146292_n.add(this.doneButton = new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m - 28, "Done"));
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0: {
                if (this.AllMobs.selected >= 0 && this.AllMobs.selected <= this.AllMobs.list.size() && !this.MobToAdd.isEmpty()) {
                    EspGui.mobs.add(this.MobToAdd);
                    EspGui.allmobs.remove(this.MobToAdd);
                    this.MobToAdd = "";
                    break;
                }
                break;
            }
            case 1: {
                EspGui.mobs.remove(EspGui.listGui.selected);
                EspGui.allmobs.add(this.MobToRemove);
                break;
            }
            case 2: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, "Reset to Defaults", "Are you sure?", 0));
                break;
            }
            case 20: {
                if (this.field_146297_k.field_71441_e != null) {
                    for (final ResourceLocation resourceLocation : EntityList.func_180124_b()) {
                        if (EntityList.func_188429_b(resourceLocation, (World)this.field_146297_k.field_71441_e) instanceof IAnimals && !(EntityList.func_188429_b(resourceLocation, (World)this.field_146297_k.field_71441_e) instanceof IMob) && !EspGui.listGui.list.contains(resourceLocation.toString())) {
                            EspGui.listGui.list.add(resourceLocation.toString());
                        }
                    }
                    break;
                }
                break;
            }
            case 3: {
                this.field_146297_k.func_147108_a((GuiScreen)Main.ClickGui);
                break;
            }
            case 40: {
                if (this.field_146297_k.field_71441_e != null) {
                    for (final ResourceLocation resourceLocation : EntityList.func_180124_b()) {
                        if (EntityList.func_188429_b(resourceLocation, (World)this.field_146297_k.field_71441_e) instanceof IMob && !EspGui.listGui.list.contains(resourceLocation.toString())) {
                            EspGui.listGui.list.add(resourceLocation.toString());
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public void func_73878_a(final boolean result, final int id) {
        if (id == 0 && result) {
            EspGui.mobs.clear();
        }
        super.func_73878_a(result, id);
        this.field_146297_k.func_147108_a((GuiScreen)this);
    }
    
    public void func_146274_d() throws IOException {
        super.func_146274_d();
        final int mouseX = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
        final int mouseY = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
        EspGui.listGui.handleMouseInput(mouseX, mouseY);
        this.AllMobs.handleMouseInput(mouseX, mouseY);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.MobFieldName.func_146192_a(mouseX, mouseY, mouseButton);
        if (mouseX < 550 || mouseX > this.field_146294_l - 50 || mouseY < 32 || mouseY > this.field_146295_m - 64) {
            EspGui.listGui.selected = -1;
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        this.MobFieldName.func_146201_a(typedChar, keyCode);
        if (keyCode == 28) {
            this.func_146284_a(this.addButton);
        }
        else if (keyCode == 211) {
            this.func_146284_a(this.removeButton);
        }
        else if (keyCode == 1) {
            this.func_146284_a(this.doneButton);
        }
        if (!this.MobFieldName.func_146179_b().isEmpty()) {
            this.AllMobs.list = MobSearch(this.MobFieldName.func_146179_b());
        }
        else {
            this.AllMobs.list = RemoveMobs(EspGui.mobs);
        }
    }
    
    public static ArrayList<String> Getmobs() {
        return new ArrayList<String>(EspGui.listGui.list);
    }
    
    public void func_73876_c() {
        this.MobFieldName.func_146178_a();
        if (EspGui.listGui.selected >= 0 && EspGui.listGui.selected <= EspGui.listGui.list.size()) {
            this.MobToRemove = EspGui.listGui.list.get(EspGui.listGui.selected);
        }
        if (this.AllMobs.selected >= 0 && this.AllMobs.selected < this.AllMobs.list.size()) {
            this.MobToAdd = this.AllMobs.list.get(this.AllMobs.selected);
        }
        this.addButton.field_146124_l = (this.MobToAdd != null);
        this.removeButton.field_146124_l = (EspGui.listGui.selected >= 0 && EspGui.listGui.selected < EspGui.listGui.list.size());
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146297_k.field_71466_p, "Mob (" + EspGui.listGui.getSize() + ")", this.field_146294_l / 2, 12, 16777215);
        EspGui.listGui.drawScreen(mouseX, mouseY, partialTicks);
        this.AllMobs.drawScreen(mouseX, mouseY, partialTicks);
        this.MobFieldName.func_146194_f();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (this.MobFieldName.func_146179_b().isEmpty() && !this.MobFieldName.func_146206_l()) {
            this.func_73731_b(this.field_146297_k.field_71466_p, "Mob name", 68, this.field_146295_m - 50, 8421504);
        }
        func_73734_a(48, this.field_146295_m - 56, 64, this.field_146295_m - 36, -6250336);
        func_73734_a(49, this.field_146295_m - 55, 64, this.field_146295_m - 37, -16777216);
        func_73734_a(214, this.field_146295_m - 56, 244, this.field_146295_m - 55, -6250336);
        func_73734_a(214, this.field_146295_m - 37, 244, this.field_146295_m - 36, -6250336);
        func_73734_a(244, this.field_146295_m - 56, 246, this.field_146295_m - 36, -6250336);
        func_73734_a(214, this.field_146295_m - 55, 243, this.field_146295_m - 52, -16777216);
        func_73734_a(214, this.field_146295_m - 40, 243, this.field_146295_m - 37, -16777216);
        func_73734_a(215, this.field_146295_m - 55, 216, this.field_146295_m - 37, -16777216);
        func_73734_a(242, this.field_146295_m - 55, 245, this.field_146295_m - 37, -16777216);
    }
    
    static {
        EspGui.mobs = new ArrayList<String>();
        EspGui.allmobs = new ArrayList<String>();
    }
    
    private static class ListGui extends GuiScrollingList
    {
        private final Minecraft mc;
        private List<String> list;
        private int selected;
        private int offsetx;
        
        public ListGui(final Minecraft mc, final EspGui screen, final List<String> list, final int offsetx, final int offsety) {
            super(mc, screen.field_146294_l / 4, screen.field_146295_m, 32 + offsety, screen.field_146295_m - 64, 50 + offsetx, 16, screen.field_146294_l, screen.field_146295_m);
            this.selected = -1;
            this.offsetx = offsetx;
            this.mc = mc;
            this.list = list;
        }
        
        protected int getSize() {
            return this.list.size();
        }
        
        protected void elementClicked(final int index, final boolean doubleClick) {
            if (index >= 0 && index < this.list.size()) {
                this.selected = index;
            }
        }
        
        protected boolean isSelected(final int index) {
            return index == this.selected;
        }
        
        protected void drawBackground() {
            Gui.func_73734_a(50 + this.offsetx, this.top, 66 + this.offsetx, this.bottom, -1);
        }
        
        protected void drawSlot(final int slotIdx, final int entryRight, final int slotTop, final int slotBuffer, final Tessellator tess) {
            final String name = this.list.get(slotIdx);
            final FontRenderer fr = this.mc.field_71466_p;
            GlStateManager.func_179094_E();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            try {
                int scale = 13;
                if (name.contains("giant") || name.contains("ghast") || name.contains("ender_dragon")) {
                    scale = 5;
                }
                GuiInventory.func_147046_a(58 + this.offsetx, slotTop + 13, scale, 0.0f, 0.0f, (EntityLivingBase)Objects.requireNonNull((EntityLivingBase)EntityList.func_188429_b(new ResourceLocation(name), (World)this.mc.field_71441_e)));
            }
            catch (Exception ex) {}
            GlStateManager.func_179121_F();
            fr.func_78276_b(" (" + name + ")", 68 + this.offsetx, slotTop + 2, 15790320);
        }
    }
}
