package Method.Client.utils.proxy.renderers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class ModRenderHusk extends ModRenderZombie
{
    private static final ResourceLocation HUSK_ZOMBIE_TEXTURES;
    
    public ModRenderHusk(final RenderManager p_i47204_1_) {
        super(p_i47204_1_);
    }
    
    protected void preRenderCallback(final EntityZombie entitylivingbaseIn, final float partialTickTime) {
        GlStateManager.func_179152_a(1.0625f, 1.0625f, 1.0625f);
        super.func_77041_b((EntityLivingBase)entitylivingbaseIn, partialTickTime);
    }
    
    protected ResourceLocation func_110775_a(final EntityZombie entity) {
        return ModRenderHusk.HUSK_ZOMBIE_TEXTURES;
    }
    
    static {
        HUSK_ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/husk.png");
    }
}
