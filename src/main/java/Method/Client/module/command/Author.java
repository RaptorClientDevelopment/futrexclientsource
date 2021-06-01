package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class Author extends Command
{
    public Author() {
        super("Author");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!Author.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                ChatUtils.error("Creative mode only.");
            }
            final ItemStack heldItem = Author.mc.field_71439_g.field_71071_by.func_70448_g();
            final int heldItemID = Item.func_150891_b(heldItem.func_77973_b());
            final int writtenBookID = Item.func_150891_b(Items.field_151164_bB);
            if (heldItemID != writtenBookID) {
                ChatUtils.error("You must hold a written book in your main hand.");
            }
            else {
                heldItem.func_77983_a("author", (NBTBase)new NBTTagString(args[0]));
                ChatUtils.message("Author Changed! Open Inventory.");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Change BookSign Author Creative Only";
    }
    
    @Override
    public String getSyntax() {
        return "Author <author> ";
    }
}
