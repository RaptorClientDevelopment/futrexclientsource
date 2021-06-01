package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Hat extends Command
{
    public Hat() {
        super("Hat");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!Hat.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.warning("You must be in creative mode.");
            }
            if (args.length > 0) {
                ChatUtils.warning("Too many arguments.");
            }
            final ItemStack stack = Hat.mc.field_71439_g.field_71071_by.func_70448_g();
            if (stack.func_190926_b()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            final ItemStack head = Hat.mc.field_71439_g.field_71071_by.func_70440_f(3);
            Hat.mc.field_71439_g.field_71071_by.field_70460_b.set(3, (Object)stack);
            updateSlot(5, stack);
            updateSlot(36 + Hat.mc.field_71439_g.field_71071_by.field_70461_c, head);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Hat.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    @Override
    public String getDescription() {
        return "Hand to head";
    }
    
    @Override
    public String getSyntax() {
        return "Hat";
    }
}
