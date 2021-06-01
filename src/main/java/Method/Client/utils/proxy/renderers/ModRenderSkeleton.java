package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderSkeleton extends RenderSkeleton
{
    public ModRenderSkeleton(final RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.field_177097_h.remove(4);
        this.func_177094_a((LayerRenderer)new ModLayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = (T)new ModelSkeleton(0.5f, true);
                this.modelArmor = (T)new ModelSkeleton(1.0f, true);
            }
        });
    }
}
