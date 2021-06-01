package Method.Client.utils.proxy.renderers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ModRenderStray extends ModRenderSkeleton
{
    private static final ResourceLocation STRAY_SKELETON_TEXTURES;
    
    public ModRenderStray(final RenderManager manager) {
        super(manager);
        this.func_177094_a((LayerRenderer)new LayerStrayClothing((RenderLivingBase)this));
    }
    
    protected ResourceLocation func_110775_a(final AbstractSkeleton entity) {
        return ModRenderStray.STRAY_SKELETON_TEXTURES;
    }
    
    static {
        STRAY_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray.png");
    }
}
