package Method.Client.module.command;

import java.math.*;
import Method.Client.utils.visual.*;

public class Hclip extends Command
{
    public Hclip() {
        super("Hclip");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final long dir = new BigInteger(args[0]).longValue();
            final long dir2 = new BigInteger(args[1]).longValue();
            final double y = Hclip.mc.field_71439_g.field_70163_u;
            final float yaw = Hclip.mc.field_71439_g.field_70177_z;
            final double newX = -Math.sin(Math.toRadians(yaw)) * dir + Hclip.mc.field_71439_g.field_70165_t;
            final double newZ = Math.cos(Math.toRadians(yaw)) * dir2 + Hclip.mc.field_71439_g.field_70161_v;
            Hclip.mc.field_71439_g.func_70107_b(newX, y, newZ);
            ChatUtils.message("Zoomed " + dir + " blocks.");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Teleports you In the H.";
    }
    
    @Override
    public String getSyntax() {
        return "Hclip <X> <Z>";
    }
}
