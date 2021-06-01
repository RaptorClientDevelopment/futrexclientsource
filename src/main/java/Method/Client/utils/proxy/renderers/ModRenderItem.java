package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import Method.Client.utils.proxy.*;
import Method.Client.utils.proxy.Overrides.*;
import net.minecraft.enchantment.*;
import Method.Client.module.render.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.client.model.pipeline.*;

@SideOnly(Side.CLIENT)
public class ModRenderItem extends RenderItem
{
    private static final ResourceLocation RES_ITEM_GLINT_RUNE;
    private static final ResourceLocation RES_ITEM_GLINT;
    private final RenderItem originalRenderItem;
    
    public ModRenderItem(final RenderItem parRenderItem, final ModelManager modelManager) {
        super(Minecraft.func_71410_x().func_110434_K(), modelManager, Minecraft.func_71410_x().getItemColors());
        this.originalRenderItem = parRenderItem;
    }
    
    public void func_180454_a(final ItemStack stack, final IBakedModel model) {
        if (!stack.func_190926_b()) {
            if (model.func_188618_c() || !ClientProxy.Gl.isToggled()) {
                this.originalRenderItem.func_180454_a(stack, model);
            }
            else {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b(-0.5f, -0.5f, -0.5f);
                this.renderModel(model, stack);
                if (stack.func_77962_s()) {
                    this.renderEffect(model, ColorMix.getColorForEnchantment(EnchantmentHelper.func_82781_a(stack)));
                }
                GlStateManager.func_179121_F();
            }
        }
    }
    
    private void renderEffect(final IBakedModel model, final int color) {
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179143_c(514);
        GlStateManager.func_179140_f();
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        if (ArmorRender.useRuneTexture.getValBoolean()) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(ModRenderItem.RES_ITEM_GLINT_RUNE);
        }
        else {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(ModRenderItem.RES_ITEM_GLINT);
        }
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(16.0f, 16.0f, 16.0f);
        final float f = Minecraft.func_71386_F() % 3000L / 3000.0f / 8.0f;
        GlStateManager.func_179109_b(f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(-50.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(model, color);
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(16.0f, 16.0f, 16.0f);
        final float f2 = Minecraft.func_71386_F() % 4873L / 4873.0f / 8.0f;
        GlStateManager.func_179109_b(-f2, 0.0f, 0.0f);
        GlStateManager.func_179114_b(10.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(model, color);
        GlStateManager.func_179121_F();
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.func_179145_e();
        GlStateManager.func_179143_c(515);
        GlStateManager.func_179132_a(true);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(TextureMap.field_110575_b);
    }
    
    private void renderModel(final IBakedModel model, final ItemStack stack) {
        this.renderModel(model, -1, stack);
    }
    
    private void renderModel(final IBakedModel model, final int color) {
        this.renderModel(model, color, ItemStack.field_190927_a);
    }
    
    private void renderModel(final IBakedModel model, final int color, final ItemStack stack) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_176599_b);
        for (final EnumFacing enumfacing : EnumFacing.values()) {
            this.func_191970_a(bufferbuilder, model.func_188616_a((IBlockState)null, enumfacing, 0L), color, stack);
        }
        this.func_191970_a(bufferbuilder, model.func_188616_a((IBlockState)null, (EnumFacing)null, 0L), color, stack);
        tessellator.func_78381_a();
    }
    
    public void func_191970_a(final BufferBuilder renderer, final List<BakedQuad> quads, final int color, final ItemStack stack) {
        final boolean flag = color == -1 && !stack.func_190926_b();
        for (int i = 0, j = quads.size(); i < j; ++i) {
            final BakedQuad bakedquad = quads.get(i);
            int k = color;
            if (flag && bakedquad.func_178212_b()) {
                k = Minecraft.func_71410_x().getItemColors().func_186728_a(stack, bakedquad.func_178211_c());
                if (EntityRenderer.field_78517_a) {
                    k = TextureUtil.func_177054_c(k);
                }
                k |= 0xFF000000;
            }
            LightUtil.renderQuadColor(renderer, bakedquad, k);
        }
    }
    
    public static ResourceLocation getResItemGlint() {
        return ModRenderItem.RES_ITEM_GLINT;
    }
    
    static {
        RES_ITEM_GLINT_RUNE = new ResourceLocation("futurex", "textures/misc/enchanted_item_glint_rune.png");
        RES_ITEM_GLINT = new ResourceLocation("futurex", "textures/misc/enchanted_item_glint.png");
    }
}
