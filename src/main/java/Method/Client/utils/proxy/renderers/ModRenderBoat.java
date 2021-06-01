package Method.Client.utils.proxy.renderers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import Method.Client.module.movement.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import net.minecraft.entity.*;

public class ModRenderBoat extends RenderBoat
{
    public ModRenderBoat(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void func_76986_a(final EntityBoat entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (BoatFly.Boatblend.getValBoolean()) {
            GlStateManager.func_179147_l();
        }
        if (BoatFly.BoatRender.getValString().equalsIgnoreCase("Vanish")) {
            return;
        }
        if (BoatFly.BoatRender.getValString().equalsIgnoreCase("Rainbow")) {
            ColorUtils.glColor(ColorUtils.rainbow().getRGB());
        }
        super.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
        if (BoatFly.Boatblend.getValBoolean()) {
            GlStateManager.func_179084_k();
        }
    }
}
