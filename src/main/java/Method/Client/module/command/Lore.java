package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Lore extends Command
{
    public Lore() {
        super("Lore");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
                return;
            }
            final ItemStack stack = Lore.mc.field_71439_g.field_71071_by.func_70448_g();
            if (stack.func_190926_b()) {
                ChatUtils.error("You must hold an item in your hand.");
                return;
            }
            StringBuilder lore = new StringBuilder(args[0]);
            for (int i = 1; i < args.length; ++i) {
                lore.append(" ").append(args[i]);
            }
            lore = new StringBuilder(lore.toString().replace('&', '§').replace("§§", "&"));
            if (!Lore.mc.field_71439_g.func_184812_l_()) {
                ChatUtils.warning("You must be in creative mode.");
            }
            if (stack.func_77942_o()) {
                assert stack.func_77978_p() != null;
                stack.func_77978_p().func_74775_l("display").func_74781_a("Lore");
                final NBTTagList lores = (NBTTagList)stack.func_77978_p().func_74775_l("display").func_74781_a("Lore");
                lores.func_74742_a((NBTBase)new NBTTagString(lore.toString()));
                final NBTTagCompound display = new NBTTagCompound();
                display.func_74782_a("Lore", (NBTBase)lores);
                stack.func_77978_p().func_74775_l("display").func_179237_a(display);
            }
            else {
                final NBTTagList lores = new NBTTagList();
                lores.func_74742_a((NBTBase)new NBTTagString(lore.toString()));
                final NBTTagCompound display = new NBTTagCompound();
                display.func_74782_a("Lore", (NBTBase)lores);
                stack.func_77983_a("display", (NBTBase)display);
            }
            updateSlot(36 + Lore.mc.field_71439_g.field_71071_by.field_70461_c, stack);
            ChatUtils.message("Added lore §7" + (Object)lore + "§e to the item.");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Lore.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    @Override
    public String getDescription() {
        return "Adds Lore to and object";
    }
    
    @Override
    public String getSyntax() {
        return "Lore <Lore>  ";
    }
}
