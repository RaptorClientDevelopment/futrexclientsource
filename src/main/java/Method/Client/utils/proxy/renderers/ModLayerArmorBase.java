package Method.Client.utils.proxy.renderers;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.inventory.*;
import Method.Client.module.render.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.proxy.Overrides.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraftforge.client.*;
import com.google.common.collect.*;

@SideOnly(Side.CLIENT)
public abstract class ModLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{
    protected static final ResourceLocation RES_ITEM_GLINT_RUNE;
    protected static final ResourceLocation RES_ITEM_GLINT;
    protected T modelLeggings;
    protected T modelArmor;
    private final RenderLivingBase<?> renderer;
    private boolean skipRenderGlint;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP;
    
    public ModLayerArmorBase(final RenderLivingBase<?> rendererIn) {
        this.renderer = rendererIn;
        this.initArmor();
    }
    
    public void func_177141_a(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }
    
    public boolean func_177142_b() {
        return false;
    }
    
    private void renderArmorLayer(final EntityLivingBase entityLivingBaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final EntityEquipmentSlot slotIn) {
        final ItemStack itemstack = entityLivingBaseIn.func_184582_a(slotIn);
        if (ArmorRender.RenderArmor.getValBoolean() && itemstack.func_77973_b() instanceof ItemArmor) {
            final ItemArmor itemarmor = (ItemArmor)itemstack.func_77973_b();
            if (itemarmor.func_185083_B_() == slotIn) {
                T model = this.getModelFromSlot(slotIn);
                model = this.getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, model);
                model.func_178686_a(this.renderer.func_177087_b());
                model.func_78086_a(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.setModelSlotVisible(model, slotIn);
                this.renderer.func_110776_a(this.getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, null));
                final float alpha = 1.0f;
                final float colorR = 1.0f;
                final float colorG = 1.0f;
                final float colorB = 1.0f;
                if (itemarmor.hasOverlay(itemstack)) {
                    final int itemColor = itemarmor.func_82814_b(itemstack);
                    final float itemRed = (itemColor >> 16 & 0xFF) / 255.0f;
                    final float itemGreen = (itemColor >> 8 & 0xFF) / 255.0f;
                    final float itemBlue = (itemColor & 0xFF) / 255.0f;
                    GlStateManager.func_179131_c(colorR * itemRed, colorG * itemGreen, colorB * itemBlue, alpha);
                    model.func_78088_a((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    this.renderer.func_110776_a(this.getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, "overlay"));
                }
                GlStateManager.func_179131_c(colorR, colorG, colorB, alpha);
                model.func_78088_a((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (!this.skipRenderGlint && itemstack.func_77962_s()) {
                    renderEnchantedGlint(this.renderer, entityLivingBaseIn, model, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, ColorMix.getColorForEnchantment(EnchantmentHelper.func_82781_a(itemstack)));
                }
            }
        }
    }
    
    public T getModelFromSlot(final EntityEquipmentSlot slotIn) {
        return this.isLegSlot(slotIn) ? this.modelLeggings : this.modelArmor;
    }
    
    private boolean isLegSlot(final EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.LEGS;
    }
    
    public static void renderEnchantedGlint(final RenderLivingBase<?> parRenderLivingBase, final EntityLivingBase parEntityLivingBase, final ModelBase model, final float parLimbSwing, final float parLimbSwingAmount, final float parPartialTicks, final float parAgeInTicks, final float parHeadYaw, final float parHeadPitch, final float parScale, final int parColor) {
        final float f = parEntityLivingBase.field_70173_aa + parPartialTicks;
        if (ArmorRender.useRuneTexture.getValBoolean()) {
            parRenderLivingBase.func_110776_a(ModLayerArmorBase.RES_ITEM_GLINT_RUNE);
        }
        else {
            parRenderLivingBase.func_110776_a(ModLayerArmorBase.RES_ITEM_GLINT);
        }
        Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
        GlStateManager.func_179147_l();
        GlStateManager.func_179143_c(514);
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179131_c(ColorMix.redFromColor(parColor), ColorMix.greenFromColor(parColor), ColorMix.blueFromColor(parColor), ColorMix.alphaFromColor());
        for (int i = 0; i < 2; ++i) {
            GlStateManager.func_179140_f();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.func_179131_c(ColorMix.redFromColor(parColor), ColorMix.greenFromColor(parColor), ColorMix.blueFromColor(parColor), ColorMix.alphaFromColor());
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179096_D();
            GlStateManager.func_179152_a(3.0f, 3.0f, 3.0f);
            GlStateManager.func_179114_b(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.func_179109_b(0.0f, f * (0.001f + i * 0.003f) * 5.0f, 0.0f);
            GlStateManager.func_179128_n(5888);
            model.func_78088_a((Entity)parEntityLivingBase, parLimbSwing, parLimbSwingAmount, parAgeInTicks, parHeadYaw, parHeadPitch, parScale);
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179145_e();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179143_c(515);
        GlStateManager.func_179084_k();
        Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
    }
    
    protected abstract void initArmor();
    
    protected abstract void setModelSlotVisible(final T p0, final EntityEquipmentSlot p1);
    
    protected T getArmorModelHook(final EntityLivingBase entity, final ItemStack itemStack, final EntityEquipmentSlot slot, final T model) {
        return model;
    }
    
    public ResourceLocation getArmorResource(final Entity entity, final ItemStack stack, final EntityEquipmentSlot slot, final String type) {
        final ItemArmor item = (ItemArmor)stack.func_77973_b();
        String texture = item.func_82812_d().func_179242_c();
        String domain = "minecraft";
        final int idx = texture.indexOf(58);
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, this.isLegSlot(slot) ? 2 : 1, (type == null) ? "" : String.format("_%s", type));
        s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ModLayerArmorBase.ARMOR_TEXTURE_RES_MAP.get(s1);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ModLayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }
        return resourcelocation;
    }
    
    static {
        RES_ITEM_GLINT_RUNE = new ResourceLocation("futurex", "enchanted_item_glint_rune.png");
        RES_ITEM_GLINT = new ResourceLocation("futurex", "enchanted_item_glint.png");
        ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    }
}
