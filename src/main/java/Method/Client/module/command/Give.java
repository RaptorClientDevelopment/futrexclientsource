package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Give extends Command
{
    public Give() {
        super("Give");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (!Give.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.error("You must be in creative mode.");
            }
            Item item = null;
            int amount = 1;
            int metadata = 0;
            StringBuilder nbt = null;
            item = Item.func_111206_d(args[0]);
            if (item == null) {
                ChatUtils.error("There's no such item with name §7" + args[0] + "§c.");
                return;
            }
            if (args.length > 1) {
                try {
                    amount = Integer.parseInt(args[1]);
                }
                catch (NullPointerException | NumberFormatException ex3) {
                    final RuntimeException ex;
                    final RuntimeException e = ex;
                    ChatUtils.error("§7" + args[1] + "§c is not a valid number.");
                    return;
                }
                if (args.length > 2) {
                    try {
                        metadata = Integer.parseInt(args[2]);
                    }
                    catch (NullPointerException | NumberFormatException ex4) {
                        final RuntimeException ex2;
                        final RuntimeException e = ex2;
                        ChatUtils.error("§7" + args[2] + "§c is not a valid number.");
                        return;
                    }
                    if (args.length > 3) {
                        nbt = new StringBuilder(args[3]);
                        for (int i = 4; i < args.length; ++i) {
                            nbt.append(" ").append(args[i]);
                        }
                        nbt = new StringBuilder(nbt.toString().replace('&', '§').replace("§§", "&"));
                    }
                }
            }
            final ItemStack stack = new ItemStack(item, amount, metadata);
            if (nbt != null) {
                try {
                    stack.func_77982_d(JsonToNBT.func_180713_a(nbt.toString()));
                }
                catch (NBTException e2) {
                    ChatUtils.error("Data tag parsing failed: " + e2.getMessage());
                    return;
                }
            }
            updateFirstEmptySlot(stack);
        }
        catch (Exception e3) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Gives items";
    }
    
    public static void updateFirstEmptySlot(final ItemStack stack) {
        int slot = 0;
        boolean slotFound = false;
        for (int i = 0; i < 36; ++i) {
            if (Give.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) {
                slot = i;
                slotFound = true;
                break;
            }
        }
        if (!slotFound) {
            ChatUtils.warning("Could not find empty slot. Operation has been aborted.");
            return;
        }
        int convertedSlot;
        if ((convertedSlot = slot) < 9) {
            convertedSlot += 36;
        }
        if (stack.func_190916_E() > 64) {
            final ItemStack passStack = stack.func_77946_l();
            stack.func_190920_e(64);
            passStack.func_190920_e(passStack.func_190916_E() - 64);
            Give.mc.field_71439_g.field_71071_by.func_70299_a(slot, stack);
            Objects.requireNonNull(Give.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(convertedSlot, stack));
            updateFirstEmptySlot(passStack);
            return;
        }
        Objects.requireNonNull(Give.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(convertedSlot, stack));
    }
    
    @Override
    public String getSyntax() {
        return "Give <Id> <MetaData>";
    }
}
