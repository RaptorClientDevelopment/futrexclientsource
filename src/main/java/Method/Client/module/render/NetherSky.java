package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.event.world.*;
import net.minecraft.world.*;
import net.minecraftforge.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import Method.Client.utils.visual.*;
import net.minecraft.client.renderer.*;

public class NetherSky extends Module
{
    Setting mode;
    Setting OverlayColor;
    private static ISpaceRenderer skyboxSpaceRenderer;
    private boolean wasChanged;
    
    @Override
    public void setup() {
        NetherSky.skyboxSpaceRenderer = new SkyboxSpaceRenderer();
    }
    
    public NetherSky() {
        super("NetherSky", 0, Category.RENDER, "NetherSky");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Glint", new String[] { "Glint", "Method" }));
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.62));
    }
    
    @Override
    public void onEnable() {
        this.wasChanged = false;
    }
    
    @Override
    public void onDisable() {
        this.disableBackgroundRenderer(NetherSky.mc.field_71439_g.field_70170_p);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!this.wasChanged) {
            this.enableBackgroundRenderer(NetherSky.mc.field_71439_g.field_70170_p);
            this.wasChanged = true;
        }
    }
    
    @Override
    public void onWorldLoad(final WorldEvent.Load event) {
        this.wasChanged = false;
    }
    
    @Override
    public void onWorldUnload(final WorldEvent.Unload event) {
        this.wasChanged = false;
    }
    
    private void enableBackgroundRenderer(final World world) {
        if (world.field_73011_w.func_186058_p() == DimensionType.NETHER) {
            world.field_73011_w.setSkyRenderer((IRenderHandler)new IRenderHandler() {
                public void render(final float partialTicks, final WorldClient world, final Minecraft mc) {
                    NetherSky.skyboxSpaceRenderer.render(NetherSky.this.mode);
                }
            });
        }
    }
    
    private void disableBackgroundRenderer(final World world) {
        if (world.field_73011_w.func_186058_p() == DimensionType.NETHER) {
            world.field_73011_w.setSkyRenderer((IRenderHandler)new IRenderHandler() {
                public void render(final float partialTicks, final WorldClient world, final Minecraft mc) {
                }
            });
        }
    }
    
    public class SkyboxSpaceRenderer implements ISpaceRenderer
    {
        @Override
        public void render(final Setting mode) {
            GlStateManager.func_179106_n();
            GlStateManager.func_179118_c();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.func_74518_a();
            GlStateManager.func_179132_a(false);
            final Tessellator tessellator = Tessellator.func_178181_a();
            final BufferBuilder bufferbuilder = tessellator.func_178180_c();
            for (int i = 0; i < 6; ++i) {
                if (mode.getValString().equalsIgnoreCase("Glint")) {
                    NetherSky.mc.func_110434_K().func_110577_a(new ResourceLocation("futurex", "N.png"));
                }
                if (mode.getValString().equalsIgnoreCase("Method")) {
                    NetherSky.mc.func_110434_K().func_110577_a(new ResourceLocation("futurex", "method.png"));
                }
                GlStateManager.func_179094_E();
                if (i == 1) {
                    GlStateManager.func_179114_b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == 2) {
                    GlStateManager.func_179114_b(-90.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (i == 3) {
                    GlStateManager.func_179114_b(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == 4) {
                    GlStateManager.func_179114_b(90.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.func_179114_b(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (i == 5) {
                    GlStateManager.func_179114_b(-90.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.func_179114_b(90.0f, 0.0f, 1.0f, 0.0f);
                }
                bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                final double size = 100.0;
                final float a = ColorUtils.colorcalc(NetherSky.this.OverlayColor.getcolor(), 24);
                final float r = ColorUtils.colorcalc(NetherSky.this.OverlayColor.getcolor(), 16);
                final float g = ColorUtils.colorcalc(NetherSky.this.OverlayColor.getcolor(), 8);
                final float b = ColorUtils.colorcalc(NetherSky.this.OverlayColor.getcolor(), 0);
                bufferbuilder.func_181662_b(-size, -size, -size).func_187315_a(0.0, 0.0).func_181666_a(r, g, b, a).func_181675_d();
                bufferbuilder.func_181662_b(-size, -size, size).func_187315_a(0.0, 1.0).func_181666_a(r, g, b, a).func_181675_d();
                bufferbuilder.func_181662_b(size, -size, size).func_187315_a(1.0, 1.0).func_181666_a(r, g, b, a).func_181675_d();
                bufferbuilder.func_181662_b(size, -size, -size).func_187315_a(1.0, 0.0).func_181666_a(r, g, b, a).func_181675_d();
                tessellator.func_78381_a();
                GlStateManager.func_179121_F();
            }
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179098_w();
            GlStateManager.func_179141_d();
            GlStateManager.func_179141_d();
        }
    }
    
    public interface ISpaceRenderer
    {
        void render(final Setting p0);
    }
}
