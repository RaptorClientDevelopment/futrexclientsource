package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderGiantZombie extends RenderGiantZombie
{
    public ModRenderGiantZombie(final RenderManager manager, final float scale) {
        super(manager, scale);
        final ModLayerBipedArmor layerbipedarmor = new ModLayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = (T)new ModelZombie(0.5f, true);
                this.modelArmor = (T)new ModelZombie(1.0f, true);
            }
        };
        this.field_177097_h.remove(1);
        this.func_177094_a((LayerRenderer)layerbipedarmor);
    }
}
