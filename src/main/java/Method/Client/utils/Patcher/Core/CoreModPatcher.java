package Method.Client.utils.Patcher.Core;

import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({ "Method.Client.utils.Patcher.Core" })
public final class CoreModPatcher implements IFMLLoadingPlugin
{
    public static boolean IN_MCP;
    
    public String[] getASMTransformerClass() {
        return new String[] { ClassTransformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map data) {
        CoreModPatcher.IN_MCP = !data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return ModAccessTransformer.class.getName();
    }
    
    static {
        CoreModPatcher.IN_MCP = false;
    }
}
