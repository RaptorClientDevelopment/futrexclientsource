package Method.Client.utils.proxy.renderers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class ModRenderWitherSkeleton extends ModRenderSkeleton
{
    private static final ResourceLocation WITHER_SKELETON_TEXTURES;
    
    public ModRenderWitherSkeleton(final RenderManager manager) {
        super(manager);
    }
    
    protected ResourceLocation func_110775_a(final AbstractSkeleton entity) {
        return ModRenderWitherSkeleton.WITHER_SKELETON_TEXTURES;
    }
    
    protected void preRenderCallback(final AbstractSkeleton entitylivingbaseIn, final float partialTickTime) {
        GlStateManager.func_179152_a(1.2f, 1.2f, 1.2f);
    }
    
    static {
        WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }
}
