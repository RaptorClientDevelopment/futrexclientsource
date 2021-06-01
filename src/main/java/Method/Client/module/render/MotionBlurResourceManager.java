package Method.Client.module.render;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;

class MotionBlurResourceManager implements IResourceManager
{
    public Set<String> func_135055_a() {
        return null;
    }
    
    public IResource func_110536_a(final ResourceLocation resourceLocation) {
        return (IResource)new MotionBlurResource();
    }
    
    public List<IResource> func_135056_b(final ResourceLocation resourceLocation) {
        return null;
    }
}
