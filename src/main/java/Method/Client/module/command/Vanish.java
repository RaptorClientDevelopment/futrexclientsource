package Method.Client.module.command;

import net.minecraft.entity.*;
import Method.Client.utils.visual.*;

public class Vanish extends Command
{
    private static Entity vehicle;
    
    public Vanish() {
        super("Vanish");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (Vanish.mc.field_71439_g.func_184187_bx() != null && Vanish.vehicle == null) {
                Vanish.vehicle = Vanish.mc.field_71439_g.func_184187_bx();
                Vanish.mc.field_71439_g.func_184210_p();
                Vanish.mc.field_71441_e.func_73028_b(Vanish.vehicle.func_145782_y());
                ChatUtils.message("Vehicle " + Vanish.vehicle.func_70005_c_() + " removed.");
            }
            else if (Vanish.vehicle != null) {
                Vanish.vehicle.field_70128_L = false;
                Vanish.mc.field_71441_e.func_73027_a(Vanish.vehicle.func_145782_y(), Vanish.vehicle);
                Vanish.mc.field_71439_g.func_184205_a(Vanish.vehicle, true);
                ChatUtils.message("Vehicle " + Vanish.vehicle.func_70005_c_() + " created.");
                Vanish.vehicle = null;
            }
            else {
                ChatUtils.message("No Vehicle.");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Vanish in a entity";
    }
    
    @Override
    public String getSyntax() {
        return "Vanish  ";
    }
}
