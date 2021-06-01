package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.nbt.*;
import java.util.*;
import java.awt.*;
import net.minecraft.item.*;
import java.awt.datatransfer.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Nbt extends Command
{
    public Nbt() {
        super("Nbt");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
                return;
            }
            final ItemStack stack = Nbt.mc.field_71439_g.field_71071_by.func_70448_g();
            if (stack.func_190926_b()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            final String s2 = args[0];
            switch (s2) {
                case "add": {
                    if (!Nbt.mc.field_71439_g.func_184812_l_()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT data provided.");
                        return;
                    }
                    StringBuilder nbt = new StringBuilder(args[1]);
                    for (int i = 2; i < args.length; ++i) {
                        nbt.append(" ").append(args[i]);
                    }
                    nbt = new StringBuilder(nbt.toString().replace('&', '§').replace("§§", "&"));
                    try {
                        if (!stack.func_77942_o()) {
                            stack.func_77982_d(JsonToNBT.func_180713_a(nbt.toString()));
                        }
                        else {
                            assert stack.func_77978_p() != null;
                            stack.func_77978_p().func_179237_a(JsonToNBT.func_180713_a(nbt.toString()));
                        }
                        updateSlot(36 + Nbt.mc.field_71439_g.field_71071_by.field_70461_c, stack);
                        ChatUtils.message("Item modified.");
                    }
                    catch (NBTException e) {
                        ChatUtils.error("Data tag parsing failed: " + e.getMessage());
                    }
                    break;
                }
                case "set": {
                    if (!Nbt.mc.field_71439_g.func_184812_l_()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT data provided.");
                        return;
                    }
                    StringBuilder nbt = new StringBuilder(args[1]);
                    for (int i = 2; i < args.length; ++i) {
                        nbt.append(" ").append(args[i]);
                    }
                    nbt = new StringBuilder(nbt.toString().replace('&', '§').replace("§§", "&"));
                    try {
                        stack.func_77982_d(JsonToNBT.func_180713_a(nbt.toString()));
                    }
                    catch (NBTException e) {
                        ChatUtils.error("Data tag parsing failed: " + e.getMessage());
                        return;
                    }
                    updateSlot(36 + Nbt.mc.field_71439_g.field_71071_by.field_70461_c, stack);
                    ChatUtils.message("Item modified.");
                    break;
                }
                case "remove": {
                    if (!Nbt.mc.field_71439_g.func_184812_l_()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length < 2) {
                        ChatUtils.error("No NBT tag specified.");
                        return;
                    }
                    if (args.length > 2) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    final String tag = args[1];
                    if (!stack.func_77942_o() || !Objects.requireNonNull(stack.func_77978_p()).func_74764_b(tag)) {
                        ChatUtils.error("Item has no NBT tag with name §7" + args[1] + "§c.");
                        return;
                    }
                    stack.func_77978_p().func_82580_o(tag);
                    if (stack.func_77978_p().func_82582_d()) {
                        stack.func_77982_d((NBTTagCompound)null);
                    }
                    updateSlot(36 + Nbt.mc.field_71439_g.field_71071_by.field_70461_c, stack);
                    ChatUtils.message("Item modified.");
                    break;
                }
                case "clear": {
                    if (!Nbt.mc.field_71439_g.func_184812_l_()) {
                        ChatUtils.warning("You must be in creative mode.");
                    }
                    if (args.length > 1) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    if (!stack.func_77942_o()) {
                        ChatUtils.error("Item has no NBT data.");
                        return;
                    }
                    stack.func_77982_d((NBTTagCompound)null);
                    updateSlot(36 + Nbt.mc.field_71439_g.field_71071_by.field_70461_c, stack);
                    ChatUtils.message("Cleared item's NBT data.");
                    break;
                }
                case "copy": {
                    if (args.length > 1) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    if (!stack.func_77942_o()) {
                        ChatUtils.error("Item has no NBT data.");
                        return;
                    }
                    assert stack.func_77978_p() != null;
                    final StringSelection selection = new StringSelection(stack.func_77978_p().toString());
                    final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                    ChatUtils.message("Copied item's NBT data to clipboard.");
                    break;
                }
            }
        }
        catch (Exception e2) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Nbt.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    @Override
    public String getDescription() {
        return "Modifies held item's NBT data.";
    }
    
    @Override
    public String getSyntax() {
        return "nbt <add <dataTag>|set <dataTag>|remove <tagName>|clear|copy>";
    }
}
