package Method.Client.module.command;

import Method.Client.managers.*;
import Method.Client.utils.visual.*;

public class PrefixChange extends Command
{
    public PrefixChange() {
        super("PrefixChange");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0].length() < 2) {
                CommandManager.cmdPrefix = args[0].charAt(0);
                ChatUtils.message("Prefix Changed");
            }
            else {
                ChatUtils.error("Prefix must be 1 length");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Prefix Changer";
    }
    
    @Override
    public String getSyntax() {
        return "PrefixChange <Name>";
    }
}
