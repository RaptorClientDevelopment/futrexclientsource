package Method.Client.module.command;

import Method.Client.*;
import Method.Client.managers.*;
import Method.Client.utils.visual.*;
import Method.Client.module.*;
import java.util.*;

public class Reset extends Command
{
    public Reset() {
        super("Reset");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final Module m = ModuleManager.getModuleByName(args[0]);
            if (m != null) {
                if (m.isToggled()) {
                    m.toggle();
                }
                if (Main.setmgr.getSettingsByMod(m) != null) {
                    for (final Setting SET : Main.setmgr.getSettingsByMod(m)) {
                        Main.setmgr.getSettings().remove(SET);
                    }
                    m.setup();
                    FileManager.SaveMods();
                    FileManager.saveframes();
                }
                ModuleManager.addModule(m);
                ChatUtils.message(m + " Returned to Factory");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Resets a module to factory defaults";
    }
    
    @Override
    public String getSyntax() {
        return "Reset <module>";
    }
}
