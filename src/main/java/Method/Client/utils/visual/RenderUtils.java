package Method.Client.utils.visual;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import Method.Client.module.misc.*;
import net.minecraft.entity.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.renderer.culling.*;

public class RenderUtils
{
    protected static Minecraft mc;
    private static final ICamera camera;
    
    public static void OpenGl() {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179132_a(false);
        GL11.glHint(3154, 4354);
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GL11.glEnable(2848);
        GL11.glEnable(34383);
    }
    
    public static void ReleaseGl() {
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179089_o();
        GlStateManager.func_179145_e();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
        GlStateManager.func_179132_a(true);
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        GL11.glDisable(34383);
        GlStateManager.func_179103_j(7424);
    }
    
    public static void RenderBlock(final String s, final AxisAlignedBB bb, final int c, final Double width) {
        if (!s.equalsIgnoreCase("Tracer") && !ModSettings.Rendernonsee.getValBoolean()) {
            RenderUtils.camera.func_78547_a(Objects.requireNonNull(RenderUtils.mc.func_175606_aa()).field_70165_t, RenderUtils.mc.func_175606_aa().field_70163_u, RenderUtils.mc.func_175606_aa().field_70161_v);
            if (!RenderUtils.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + RenderUtils.mc.func_175598_ae().field_78730_l, -10.0, bb.field_72339_c + RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d + RenderUtils.mc.func_175598_ae().field_78730_l, 2500.0, bb.field_72334_f + RenderUtils.mc.func_175598_ae().field_78728_n))) {
                return;
            }
        }
        OpenGl();
        GlStateManager.func_187441_d((float)(1.5 * (width + 1.0E-4)));
        final float a = ColorUtils.colorcalc(c, 24);
        final float r = ColorUtils.colorcalc(c, 16);
        final float g = ColorUtils.colorcalc(c, 8);
        final float b = ColorUtils.colorcalc(c, 0);
        switch (s) {
            case "Shape": {
                final Sphere sph = new Sphere();
                sph.setDrawStyle(100013);
                GlStateManager.func_179131_c(r, g, b, a);
                GlStateManager.func_179137_b((bb.field_72336_d + bb.field_72340_a) / 2.0, (bb.field_72337_e + bb.field_72338_b) / 2.0, (bb.field_72334_f + bb.field_72339_c) / 2.0);
                sph.draw(1.0f, (int)ModSettings.Spherelines.getValDouble(), (int)ModSettings.SphereDist.getValDouble());
                break;
            }
            case "Flat": {
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, r, g, b, a);
                break;
            }
            case "FlatOutline": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, r, g, b, a);
                break;
            }
            case "Full": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, r, g, b, a);
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, r, g, b, a);
                break;
            }
            case "Outline": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, r, g, b, a);
                break;
            }
            case "Beacon": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, r, g, b, a);
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, 0.0 - bb.field_72337_e + 255.0, bb.field_72334_f, r, g, b, a / 2.0f);
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, 0.0 - bb.field_72337_e + 255.0, bb.field_72334_f, r, g, b, a / 4.0f);
                break;
            }
            case "Tracer": {
                GlStateManager.func_187447_r(1);
                GlStateManager.func_179131_c(r, g, b, a);
                final Vec3d eyes = ActiveRenderInfo.getCameraPosition();
                GL11.glVertex3d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c);
                GL11.glVertex3d(bb.func_189972_c().field_72450_a, bb.func_189972_c().field_72448_b, bb.func_189972_c().field_72449_c);
                GlStateManager.func_187437_J();
                break;
            }
            case "Xspot": {
                final BufferBuilder BBuild2 = Tessellator.func_178181_a().func_178180_c();
                BBuild2.func_181668_a(3, DefaultVertexFormats.field_181706_f);
                BBuild2.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
                BBuild2.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
                BBuild2.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(r, g, b, 0.0f).func_181675_d();
                BBuild2.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
                Tessellator.func_178181_a().func_78381_a();
                break;
            }
        }
        ReleaseGl();
    }
    
    public static AxisAlignedBB Standardbb(final BlockPos pos) {
        final double renderPosX = pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l;
        final double renderPosY = pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m;
        final double renderPosZ = pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n;
        return new AxisAlignedBB(renderPosX, renderPosY, renderPosZ, renderPosX + 1.0, renderPosY + 1.0, renderPosZ + 1.0);
    }
    
    public static AxisAlignedBB Boundingbb(final Entity entity, final double x, final double y, final double z, final double x1, final double y1, final double z1) {
        final double renderPosX = entity.func_174813_aQ().field_72340_a - RenderUtils.mc.func_175598_ae().field_78730_l;
        final double renderPosY = entity.func_174813_aQ().field_72338_b - RenderUtils.mc.func_175598_ae().field_78731_m;
        final double renderPosZ = entity.func_174813_aQ().field_72339_c - RenderUtils.mc.func_175598_ae().field_78728_n;
        final double renderPosX2 = entity.func_174813_aQ().field_72336_d - RenderUtils.mc.func_175598_ae().field_78730_l;
        final double renderPosY2 = entity.func_174813_aQ().field_72337_e - RenderUtils.mc.func_175598_ae().field_78731_m;
        final double renderPosZ2 = entity.func_174813_aQ().field_72334_f - RenderUtils.mc.func_175598_ae().field_78728_n;
        return new AxisAlignedBB(renderPosX + x, renderPosY + y, renderPosZ + z, renderPosX2 + x1, renderPosY2 + y1, renderPosZ2 + z1);
    }
    
    public static AxisAlignedBB Modifiedbb(final BlockPos pos, final int x, final int y, final int z) {
        final double renderPosX = pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l;
        final double renderPosY = pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m;
        final double renderPosZ = pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n;
        return new AxisAlignedBB(renderPosX + x, renderPosY + y, renderPosZ + z, renderPosX + 1.0 + x, renderPosY + 1.0 + y, renderPosZ + 1.0 + z);
    }
    
    public static void RenderLine(final List<Vec3d> List, final int Color, final double width, final boolean valBoolean) {
        OpenGl();
        GL11.glEnable(32925);
        GL11.glLineWidth((float)width);
        ColorUtils.glColor(Color);
        GL11.glBegin(3);
        final RenderManager renderManager = RenderUtils.mc.func_175598_ae();
        for (final Vec3d blockPos : List) {
            if (valBoolean) {
                final BlockPos snap = new BlockPos(blockPos);
                GL11.glVertex3d(snap.field_177962_a - renderManager.field_78730_l, snap.field_177960_b - renderManager.field_78731_m, snap.field_177961_c - renderManager.field_78728_n);
            }
            else {
                GL11.glVertex3d(blockPos.field_72450_a - renderManager.field_78730_l, blockPos.field_72448_b - renderManager.field_78731_m, blockPos.field_72449_c - renderManager.field_78728_n);
            }
        }
        GL11.glEnd();
        ReleaseGl();
        GL11.glDisable(32925);
    }
    
    public static Vec3d getInterpolatedRenderPos(final Vec3d pos) {
        return new Vec3d(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_178786_a(RenderUtils.mc.func_175598_ae().field_78725_b, RenderUtils.mc.func_175598_ae().field_78726_c, RenderUtils.mc.func_175598_ae().field_78723_d);
    }
    
    public static void SimpleNametag(final Vec3d pos, final String str) {
        OpenGl();
        final boolean isThirdPersonFrontal = RenderUtils.mc.func_175598_ae().field_78733_k.field_74320_O == 2;
        final Vec3d interp = getInterpolatedRenderPos(pos);
        GlStateManager.func_179137_b(interp.field_72450_a + 0.5, interp.field_72448_b + 0.75, interp.field_72449_c + 0.5);
        GlStateManager.func_179114_b(-RenderUtils.mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((isThirdPersonFrontal ? -1 : 1) * RenderUtils.mc.func_175598_ae().field_78732_j, 1.0f, 0.0f, 0.0f);
        final float m = (float)Math.pow(1.04, RenderUtils.mc.field_71439_g.func_70011_f(pos.field_72450_a, pos.field_72448_b + 0.5, pos.field_72449_c));
        GlStateManager.func_179152_a(m, m, m);
        GlStateManager.func_179152_a(-0.025f, -0.025f, 0.025f);
        final int i = RenderUtils.mc.field_71466_p.func_78256_a(str) / 2;
        GlStateManager.func_187432_a(0.0f, 1.0f, 0.0f);
        GlStateManager.func_179098_w();
        RenderUtils.mc.field_71466_p.func_175063_a(str, (float)(-i), 9.0f, Color.WHITE.getRGB());
        GlStateManager.func_179090_x();
        GlStateManager.func_187432_a(0.0f, 0.0f, 0.0f);
        ReleaseGl();
    }
    
    public static void drawRectOutline(final double left, final double top, final double right, final double bottom, final double width, final int color) {
        final double l = left - width;
        final double t = top - width;
        final double r = right + width;
        final double b = bottom + width;
        drawRectDouble(l, t, r, top, color);
        drawRectDouble(right, top, r, b, color);
        drawRectDouble(l, bottom, right, b, color);
        drawRectDouble(l, top, left, bottom, color);
    }
    
    public static void drawRectDouble(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ColorUtils.glColor(color);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b(left, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, top, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    static {
        RenderUtils.mc = Minecraft.func_71410_x();
        camera = (ICamera)new Frustum();
    }
}
