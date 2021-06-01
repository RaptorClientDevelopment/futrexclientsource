package Method.Client.module.command;

import java.math.*;
import Method.Client.utils.SeedViewer.*;
import Method.Client.utils.visual.*;

public class WorldSeed extends Command
{
    public WorldSeed() {
        super("WorldSeed");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final long Seed = WorldLoader.seed = new BigInteger(args[0]).longValue();
            ChatUtils.message("Seed = " + Seed);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "WorldSeed";
    }
    
    @Override
    public String getSyntax() {
        return "WorldSeed <seed>";
    }
}
