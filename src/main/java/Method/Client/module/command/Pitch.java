package Method.Client.module.command;

import java.math.*;
import Method.Client.utils.visual.*;

public class Pitch extends Command
{
    public Pitch() {
        super("Pitch");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final long Pitch = new BigInteger(args[0]).longValue();
            Method.Client.module.command.Pitch.mc.field_71439_g.field_70125_A = Pitch;
            ChatUtils.message("Pitch =" + Pitch);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Set Pitch";
    }
    
    @Override
    public String getSyntax() {
        return "Pitch <Num>";
    }
}
