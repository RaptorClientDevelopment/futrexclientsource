package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderArmorStand extends RenderArmorStand
{
    public ModRenderArmorStand(final RenderManager manager) {
        super(manager);
        final ModLayerBipedArmor layerbipedarmor = new ModLayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = (T)new ModelArmorStandArmor(0.5f);
                this.modelArmor = (T)new ModelArmorStandArmor(1.0f);
            }
        };
        this.func_177094_a((LayerRenderer)layerbipedarmor);
    }
}
