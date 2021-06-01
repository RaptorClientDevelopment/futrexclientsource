package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.inventory.*;
import Method.Client.module.misc.*;
import net.minecraft.item.*;

public class Peek extends Command
{
    public Peek() {
        super("Peek");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0] != null) {
                final String name = args[0].toLowerCase();
                if (!Shulkerspy.shulkerMap.containsKey(name.toLowerCase())) {
                    ChatUtils.error("have not seen this player hold a shulkerbox. Check your spelling.");
                    return;
                }
                final IInventory inv = (IInventory)Shulkerspy.shulkerMap.get(name.toLowerCase());
                final IInventory inventory;
                new Thread(() -> {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException ex) {}
                    Peek.mc.field_71439_g.func_71007_a(inventory);
                }).start();
            }
            else {
                if (!(Peek.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemShulkerBox)) {
                    ChatUtils.error("You Have to hold a shulker box");
                }
                final ItemStack itemStack = Peek.mc.field_71439_g.func_184614_ca();
                if (itemStack.func_77973_b() instanceof ItemShulkerBox) {
                    ChatUtils.message("Opening your shulker box.");
                    GuiPeek.Peekcode(itemStack, Peek.mc);
                }
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Peek into shukler!";
    }
    
    @Override
    public String getSyntax() {
        return "Peek <Name>";
    }
}
