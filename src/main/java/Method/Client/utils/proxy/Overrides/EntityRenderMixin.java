package Method.Client.utils.proxy.Overrides;

import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class EntityRenderMixin extends EntityRenderer
{
    public static boolean Camswitch;
    
    public EntityRenderMixin(final Minecraft mcIn, final IResourceManager resourceManagerIn) {
        super(mcIn, resourceManagerIn);
    }
    
    public void func_78482_e(final float partialTicks) {
        if (EntityRenderMixin.Camswitch) {
            super.func_78482_e(partialTicks);
        }
    }
    
    static {
        EntityRenderMixin.Camswitch = true;
    }
}
