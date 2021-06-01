package Method.Client.module.command;

import java.math.*;
import Method.Client.utils.visual.*;

public class VClip extends Command
{
    public VClip() {
        super("vclip");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            VClip.mc.field_71439_g.func_70107_b(VClip.mc.field_71439_g.field_70165_t, VClip.mc.field_71439_g.field_70163_u + new BigInteger(args[0]).longValue(), VClip.mc.field_71439_g.field_70161_v);
            ChatUtils.message("Height teleported to " + new BigInteger(args[0]).longValue());
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Teleports you up/down.";
    }
    
    @Override
    public String getSyntax() {
        return "vclip <height>";
    }
}
