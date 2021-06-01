package Method.Client.module.command;

import Method.Client.utils.visual.*;

public class BedCoords extends Command
{
    public BedCoords() {
        super("BedCoords");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            ChatUtils.message(BedCoords.mc.field_71439_g.func_180470_cg().toString());
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "BedCoords";
    }
    
    @Override
    public String getSyntax() {
        return "BedCoords ";
    }
}
