package Method.Client.utils.Screens.Override;

import net.minecraft.client.gui.*;
import Method.Client.utils.visual.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import java.io.*;

public class Authorinsert extends GuiScreen
{
    GuiTextField textbox;
    
    public void func_73866_w_() {
        (this.textbox = new GuiTextField(0, this.field_146297_k.field_71466_p, (int)(this.field_146297_k.field_71443_c / 5.4), 180, 240, this.field_146297_k.field_71443_c / 100)).func_146195_b(true);
        this.func_189646_b(new GuiButton(200, this.field_146294_l / 2 - 50, this.field_146295_m / 4 + 120, 120, 20, "Done"));
    }
    
    public void doitnow() {
        if (!this.field_146297_k.field_71439_g.field_71075_bZ.field_75098_d) {
            ChatUtils.error("Creative mode only.");
            this.field_146297_k.func_147108_a((GuiScreen)null);
            return;
        }
        final ItemStack heldItem = this.field_146297_k.field_71439_g.field_71071_by.func_70448_g();
        final int heldItemID = Item.func_150891_b(heldItem.func_77973_b());
        final int writtenBookID = Item.func_150891_b(Items.field_151164_bB);
        if (heldItemID != writtenBookID) {
            ChatUtils.error("You must hold a written book in your main hand.");
        }
        else {
            heldItem.func_77983_a("author", (NBTBase)new NBTTagString(this.textbox.func_146179_b()));
            ChatUtils.message("Author Changed! Open Inventory.");
        }
        this.field_146297_k.func_147108_a((GuiScreen)null);
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146289_q, "Change Author", this.field_146294_l / 2, 40, 16777215);
        this.textbox.func_146194_f();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.textbox.func_146192_a(mouseX, mouseY, mouseButton);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
        else {
            this.textbox.func_146201_a(typedChar, keyCode);
        }
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        super.func_146284_a(button);
        if (button.field_146127_k == 200) {
            this.doitnow();
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
}
