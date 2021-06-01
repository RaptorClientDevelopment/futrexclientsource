package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

@SideOnly(Side.CLIENT)
public class ModRenderZombieVillager extends RenderZombieVillager
{
    public ModRenderZombieVillager(final RenderManager manager) {
        super(manager);
        final ModLayerBipedArmor layerbipedarmor = new ModLayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = (T)new ModelZombieVillager(0.5f, 0.0f, true);
                this.modelArmor = (T)new ModelZombieVillager(1.0f, 0.0f, true);
            }
        };
        this.field_177097_h.remove(3);
        this.func_177094_a((LayerRenderer)layerbipedarmor);
    }
}
