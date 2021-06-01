package Method.Client.module.command;

import Method.Client.utils.visual.*;
import Method.Client.module.Profiles.*;
import Method.Client.module.*;
import Method.Client.clickgui.*;
import Method.Client.clickgui.component.*;
import java.util.*;

public class Profile extends Command
{
    public Profile() {
        super("Profile");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0].equalsIgnoreCase("Remove")) {
                ModuleManager.toggledModules.remove(ModuleManager.getModuleByName(args[1]));
                ModuleManager.modules.remove(ModuleManager.getModuleByName(args[1]));
                ChatUtils.message("Removed Profile" + args[1]);
            }
            if (args[0].equalsIgnoreCase("Add")) {
                ModuleManager.addModule(new Profiletem(args[1]));
                ChatUtils.message("Added Profile" + args[1]);
            }
            for (final Frame frame : ClickGui.frames) {
                if (frame.getName().equalsIgnoreCase("PROFILES")) {
                    frame.updateRefresh();
                }
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Modify Profile Add/Remove";
    }
    
    @Override
    public String getSyntax() {
        return "profile <Add/Remove> [Name]";
    }
}
