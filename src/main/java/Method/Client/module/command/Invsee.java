package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Invsee extends Command
{
    public Invsee() {
        super("Invsee");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (Invsee.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                ChatUtils.error("Must Be Creative");
                return;
            }
            final String id = args[0];
            for (final EntityPlayer entityPlayer : Invsee.mc.field_71441_e.field_73010_i) {
                if (entityPlayer.getDisplayNameString().equalsIgnoreCase(id)) {
                    Invsee.mc.func_147108_a((GuiScreen)new GuiInventory(entityPlayer));
                    return;
                }
            }
            ChatUtils.error("Could not find player " + id);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "See inv of other players";
    }
    
    @Override
    public String getSyntax() {
        return "Invsee <Player>";
    }
}
