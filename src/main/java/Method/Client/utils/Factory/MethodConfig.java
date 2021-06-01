package Method.Client.utils.Factory;

import net.minecraftforge.fml.common.event.*;
import java.util.*;
import net.minecraftforge.fml.client.config.*;
import net.minecraftforge.common.config.*;

public class MethodConfig
{
    static Property Opengui;
    public static boolean Guicheck;
    static Configuration config;
    
    public void preInit(final FMLPreInitializationEvent event) {
        (MethodConfig.config = new Configuration(event.getSuggestedConfigurationFile())).load();
        MethodConfig.Opengui = MethodConfig.config.get("managers", "Toggle Gui", false, "Opens gui if Right control is bound to something else");
        this.syncConfig();
    }
    
    public void syncConfig() {
        MethodConfig.Guicheck = MethodConfig.Opengui.getBoolean();
        if (MethodConfig.config.hasChanged()) {
            MethodConfig.config.save();
        }
    }
    
    public static String getString() {
        return MethodConfig.config.toString();
    }
    
    public static List<IConfigElement> getConfigElements() {
        return (List<IConfigElement>)new ConfigElement(MethodConfig.config.getCategory("managers")).getChildElements();
    }
}
