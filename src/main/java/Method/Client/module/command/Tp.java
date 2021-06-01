package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Tp extends Command
{
    public Tp() {
        super("Tp");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args.length < 1) {
                ChatUtils.error("Invalid syntax.");
            }
            else if (args.length < 2) {
                final EntityPlayer target = Tp.mc.field_71441_e.func_72924_a(args[0]);
                if (target == null) {
                    ChatUtils.error("Player §7" + args[0] + " §ccan not be found.");
                    return;
                }
                final double x = target.field_70165_t;
                final double y = target.field_70163_u;
                final double z = target.field_70161_v;
                final float pitch = target.field_70125_A;
                final float yaw = target.field_70177_z;
                Tp.mc.field_71439_g.func_70080_a(x, y, z, yaw, pitch);
                ChatUtils.message("Teleported §7" + Tp.mc.field_71439_g.func_70005_c_() + "§e to §9" + x + "§e, §9" + y + "§e, §9" + z + "§e.");
            }
            else {
                if (args.length >= 3) {
                    double x2 = Tp.mc.field_71439_g.field_70165_t;
                    double y2 = Tp.mc.field_71439_g.field_70163_u;
                    double z2 = Tp.mc.field_71439_g.field_70161_v;
                    float pitch2 = Tp.mc.field_71439_g.field_70125_A;
                    float yaw2 = Tp.mc.field_71439_g.field_70177_z;
                    try {
                        x2 = parseMath(args[0], x2);
                    }
                    catch (NullPointerException | NumberFormatException ex6) {
                        final RuntimeException ex;
                        final RuntimeException e = ex;
                        ChatUtils.error("§7" + args[0] + " §cis not a valid number.");
                        return;
                    }
                    try {
                        y2 = parseMath(args[1], y2);
                    }
                    catch (NullPointerException | NumberFormatException ex7) {
                        final RuntimeException ex2;
                        final RuntimeException e = ex2;
                        ChatUtils.error("§7" + args[1] + " §cis not a valid number.");
                        return;
                    }
                    try {
                        z2 = parseMath(args[2], z2);
                    }
                    catch (NullPointerException | NumberFormatException ex8) {
                        final RuntimeException ex3;
                        final RuntimeException e = ex3;
                        ChatUtils.error("§7" + args[2] + " §cis not a valid number.");
                        return;
                    }
                    if (args.length > 3) {
                        try {
                            yaw2 = (float)parseMath(args[3], yaw2);
                        }
                        catch (NullPointerException | NumberFormatException ex9) {
                            final RuntimeException ex4;
                            final RuntimeException e = ex4;
                            ChatUtils.error("§7" + args[3] + " §cis not a valid number.");
                            return;
                        }
                    }
                    if (args.length > 4) {
                        try {
                            pitch2 = (float)parseMath(args[4], pitch2);
                        }
                        catch (NullPointerException | NumberFormatException ex10) {
                            final RuntimeException ex5;
                            final RuntimeException e = ex5;
                            ChatUtils.error("§7" + args[4] + " §cis not a valid number.");
                            return;
                        }
                    }
                    if (args.length > 5) {
                        ChatUtils.warning("Too many arguments.");
                    }
                    Tp.mc.field_71439_g.func_70080_a(x2, y2, z2, yaw2, pitch2);
                    ChatUtils.message("Teleported §7" + Tp.mc.field_71439_g.func_70005_c_() + "§e to §9" + x2 + "§e, §9" + y2 + "§e, §9" + z2 + "§e.");
                    return;
                }
                ChatUtils.error("Invalid syntax.");
            }
        }
        catch (Exception e2) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    public static void updateSlot(final int slot, final ItemStack stack) {
        Objects.requireNonNull(Tp.mc.func_147114_u()).func_147297_a((Packet)new CPacketCreativeInventoryAction(slot, stack));
    }
    
    private static double parseMath(final String input, final double old) {
        if (input.length() < 1) {
            throw new NumberFormatException();
        }
        if (input.charAt(0) != '~') {
            return Double.parseDouble(input);
        }
        if (input.length() > 2 && input.charAt(1) == '+') {
            final String coord = input.substring(2);
            return old + Double.parseDouble(coord);
        }
        if (input.length() > 2 && input.charAt(1) == '-') {
            final String coord = input.substring(2);
            return old - Double.parseDouble(coord);
        }
        if (input.length() != 1) {
            throw new NumberFormatException();
        }
        return old;
    }
    
    @Override
    public String getDescription() {
        return "Tp to position or player";
    }
    
    @Override
    public String getSyntax() {
        return "tp <<x> <y> <z> [yaw] [pitch]|<player>>";
    }
}
