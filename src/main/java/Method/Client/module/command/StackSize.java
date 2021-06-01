package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.item.*;

public class StackSize extends Command
{
    public StackSize() {
        super("StackSize");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!StackSize.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.error("Creative mode is required to use this command.");
                return;
            }
            final ItemStack itemStack = StackSize.mc.field_71439_g.func_184614_ca();
            if (itemStack.func_190926_b()) {
                ChatUtils.error("Please hold an item in your main hand to enchant.");
                return;
            }
            final String id = args[0];
            final int num = Integer.parseInt(id);
            itemStack.func_190920_e(num);
            assert itemStack.func_77978_p() != null;
            itemStack.func_77973_b().func_179215_a(itemStack.func_77978_p());
            ChatUtils.error("Set your stack size to " + num);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "StackSize";
    }
    
    @Override
    public String getSyntax() {
        return "StackSize ";
    }
}
