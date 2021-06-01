package Method.Client.utils.visual;

import Method.Client.utils.system.*;
import net.minecraft.util.text.*;

public class ChatUtils
{
    public static void component(final ITextComponent component) {
        if (Wrapper.INSTANCE.player() == null) {
            return;
        }
        Wrapper.INSTANCE.mc().field_71456_v.func_146158_b();
        Wrapper.INSTANCE.mc().field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("", new Object[0]).func_150257_a(component));
    }
    
    public static void message(final String message) {
        component((ITextComponent)new TextComponentTranslation("§8FutureX§7 " + message, new Object[0]));
    }
    
    public static void warning(final String message) {
        message("§8[§eWARNING§8]§e " + message);
    }
    
    public static void error(final String message) {
        message("§8[§4ERROR§8]§c " + message);
    }
}
