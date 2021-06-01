package Method.Client.module.command;

import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import Method.Client.utils.visual.*;

public class Drop extends Command
{
    public Drop() {
        super("Drop");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0].equalsIgnoreCase("all")) {
                final ClickType t = ClickType.THROW;
                for (int var2 = 9; var2 < 45; ++var2) {
                    Drop.mc.field_71442_b.func_187098_a(0, var2, 1, t, (EntityPlayer)Drop.mc.field_71439_g);
                }
            }
            else if (args[0].equalsIgnoreCase("Mob") && Drop.mc.field_71439_g.func_184187_bx() instanceof AbstractHorse && Drop.mc.field_71439_g.field_71070_bA instanceof ContainerHorseInventory) {
                for (int i = 2; i < 17; ++i) {
                    final ItemStack itemStack = (ItemStack)Drop.mc.field_71439_g.field_71070_bA.func_75138_a().get(i);
                    if (!itemStack.func_190926_b() && itemStack.func_77973_b() != Items.field_190931_a) {
                        Drop.mc.field_71442_b.func_187098_a(Drop.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.PICKUP, (EntityPlayer)Drop.mc.field_71439_g);
                        Drop.mc.field_71442_b.func_187098_a(Drop.mc.field_71439_g.field_71070_bA.field_75152_c, -999, 0, ClickType.PICKUP, (EntityPlayer)Drop.mc.field_71439_g);
                    }
                }
            }
            if (args[0].equalsIgnoreCase("hand")) {
                Drop.mc.field_71439_g.func_71040_bB(true);
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Drops items";
    }
    
    @Override
    public String getSyntax() {
        return "Drop <all/hand/Mob> ";
    }
}
