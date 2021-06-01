package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderZombie extends RenderZombie
{
    public ModRenderZombie(final RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.field_177097_h.remove(3);
        final ModLayerBipedArmor layerbipedarmor = new ModLayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = (T)new ModelZombie(0.5f, true);
                this.modelArmor = (T)new ModelZombie(1.0f, true);
            }
        };
        this.func_177094_a((LayerRenderer)layerbipedarmor);
    }
}
