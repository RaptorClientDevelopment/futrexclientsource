package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Head extends Command
{
    public Head() {
        super("Head");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!Head.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.warning("You must be in creative mode.");
            }
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
                return;
            }
            if (args.length > 1) {
                ChatUtils.warning("Too many arguments.");
            }
            final ItemStack stack = Head.mc.field_71439_g.field_71071_by.func_70448_g();
            if (!stack.func_190926_b() && Item.func_150891_b(stack.func_77973_b()) == 397 && stack.func_77960_j() == 3) {
                stack.func_77983_a("SkullOwner", (NBTBase)new NBTTagString(args[0]));
                updateSlot(36 + Head.mc.field_71439_g.field_71071_by.field_70461_c, stack);
                ChatUtils.message("Head's owner changed to §7" + args[0] + "§e.");
                return;
            }
            final ItemStack newStack = new ItemStack(Item.func_150899_d(397), 1, 3);
            newStack.func_77983_a("SkullOwner", (NBTBase)new NBTTagString(args[0]));
            Give.updateFirstEmptySlot(newStack);
            ChatUtils.message("Given head of player §7" + args[0] + "§e to §7" + Head.mc.field_71439_g.func_70005_c_() + "§e.");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Head.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    @Override
    public String getDescription() {
        return "Head to Hand";
    }
    
    @Override
    public String getSyntax() {
        return "Head <Player>";
    }
}
