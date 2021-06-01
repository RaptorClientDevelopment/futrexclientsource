package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.item.*;

public class Rename extends Command
{
    public Rename() {
        super("Rename");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
                return;
            }
            final ItemStack stack = Rename.mc.field_71439_g.field_71071_by.func_70448_g();
            if (stack.func_190926_b()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            StringBuilder name = new StringBuilder(args[0]);
            for (int i = 1; i < args.length; ++i) {
                name.append(" ").append(args[i]);
            }
            name = new StringBuilder(name.toString().replace('&', '§').replace("§§", "&"));
            if (!Rename.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.warning("You must be in creative mode!");
            }
            stack.func_151001_c(name.toString());
            Nbt.updateSlot(36 + Rename.mc.field_71439_g.field_71071_by.field_70461_c, stack);
            ChatUtils.message("Item's name changed to §7" + (Object)name + "§e.");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Rename item!";
    }
    
    @Override
    public String getSyntax() {
        return "Rename <Name>";
    }
}
