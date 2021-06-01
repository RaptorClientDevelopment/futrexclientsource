package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Repair extends Command
{
    public Repair() {
        super("Repair");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!Repair.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.warning("You must be in creative mode.");
            }
            if (args.length > 0) {
                ChatUtils.warning("Too many arguments.");
            }
            final ItemStack stack = Repair.mc.field_71439_g.field_71071_by.func_70448_g();
            if (stack.func_190926_b()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            if (!stack.func_77984_f()) {
                ChatUtils.error("This item cannot take any damage.");
                return;
            }
            if (!stack.func_77951_h()) {
                ChatUtils.error("This item is not damaged.");
                return;
            }
            stack.func_77964_b(0);
            updateSlot(36 + Repair.mc.field_71439_g.field_71071_by.field_70461_c, stack);
            ChatUtils.message("Item §7" + stack.func_82833_r() + " §ehas been repaired.");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Repair.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    @Override
    public String getDescription() {
        return "Repair Item In hand";
    }
    
    @Override
    public String getSyntax() {
        return "Repair";
    }
}
