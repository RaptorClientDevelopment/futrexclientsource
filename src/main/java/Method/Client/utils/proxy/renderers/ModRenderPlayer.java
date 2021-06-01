package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderPlayer extends RenderPlayer
{
    public ModRenderPlayer(final RenderManager renderManager) {
        this(renderManager, false);
    }
    
    public ModRenderPlayer(final RenderManager renderManager, final boolean b) {
        super(renderManager, b);
        this.field_177097_h.remove(0);
        this.func_177094_a((LayerRenderer)new ModLayerBipedArmor((RenderLivingBase<?>)this));
    }
}
