package Method.Client.module.command;

import Method.Client.managers.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class Help extends Command
{
    public Help() {
        super("help");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        for (final Command cmd : CommandManager.commands) {
            if (cmd != this) {
                ChatUtils.message(cmd.getSyntax().replace("<", "<§9").replace(">", "§7>") + " - " + cmd.getDescription());
            }
        }
    }
    
    @Override
    public String getDescription() {
        return "Lists all commands.";
    }
    
    @Override
    public String getSyntax() {
        return "help";
    }
}
