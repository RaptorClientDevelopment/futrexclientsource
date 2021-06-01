package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;

public class ModSettings extends Module
{
    public static Setting Spherelines;
    public static Setting SphereDist;
    public static Setting Rendernonsee;
    public static Setting ShowErrors;
    public static Setting GuiSpeed;
    
    public ModSettings() {
        super("ModSettings", 0, Category.MISC, "Mod Settings for other modules");
        Main.setmgr.add(ModSettings.Spherelines = new Setting("Shapelines", this, 10.0, 0.0, 20.0, true));
        Main.setmgr.add(ModSettings.SphereDist = new Setting("ShapeDist", this, 10.0, 0.0, 20.0, true));
        Main.setmgr.add(ModSettings.GuiSpeed = new Setting("GuiSpeed", this, 20.0, 0.0, 50.0, true));
        Main.setmgr.add(ModSettings.Rendernonsee = new Setting("Unseen Render", this, false));
        Main.setmgr.add(ModSettings.ShowErrors = new Setting("ShowErrors", this, false));
    }
}
