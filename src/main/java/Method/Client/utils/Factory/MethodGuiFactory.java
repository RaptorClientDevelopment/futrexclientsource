package Method.Client.utils.Factory;

import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class MethodGuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft minecraftInstance) {
    }
    
    public Class mainConfigGuiClass() {
        return MethodConfigGui.class;
    }
    
    public Set runtimeGuiCategories() {
        return null;
    }
    
    public boolean hasConfigGui() {
        return true;
    }
    
    public GuiScreen createConfigGui(final GuiScreen parentScreen) {
        return (GuiScreen)new MethodConfigGui(parentScreen);
    }
}
