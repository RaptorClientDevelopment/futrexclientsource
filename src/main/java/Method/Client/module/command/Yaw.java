package Method.Client.module.command;

import java.math.*;
import Method.Client.utils.visual.*;

public class Yaw extends Command
{
    public Yaw() {
        super("Yaw");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final long Yaw = new BigInteger(args[0]).longValue();
            Method.Client.module.command.Yaw.mc.field_71439_g.field_70177_z = Yaw;
            ChatUtils.message("Yaw =" + Yaw);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Set Yaw";
    }
    
    @Override
    public String getSyntax() {
        return "Yaw <Num>";
    }
}
