package Method.Client.utils.proxy.Overrides;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import Method.Client.utils.proxy.renderers.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.enchantment.*;
import Method.Client.module.render.*;
import net.minecraft.init.*;

public class ColorMix
{
    public static ModRenderItem modRenderItem;
    
    public static void replaceRenderers() {
        final Minecraft mc = Minecraft.func_71410_x();
        try {
            ColorMix.modRenderItem = new ModRenderItem(mc.field_175621_X, mc.field_175617_aL);
            mc.field_175621_X = ColorMix.modRenderItem;
            mc.field_175620_Y.field_178112_h = ColorMix.modRenderItem;
            mc.field_175616_W.field_178637_m = new ModRenderPlayer(mc.field_175616_W);
            mc.field_175616_W.field_178636_l.put("default", new ModRenderPlayer(mc.field_175616_W));
            mc.field_175616_W.field_178636_l.put("slim", new ModRenderPlayer(mc.field_175616_W, true));
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mc.field_175616_W.field_78729_o.put(EntityBoat.class, new ModRenderBoat(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityItemFrame.class, new RenderItemFrame(mc.field_175616_W, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntitySnowball.class, new RenderSnowball(mc.field_175616_W, Items.field_151126_ay, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityEnderPearl.class, new RenderSnowball(mc.field_175616_W, Items.field_151079_bi, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityEnderEye.class, new RenderSnowball(mc.field_175616_W, Items.field_151061_bv, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityEgg.class, new RenderSnowball(mc.field_175616_W, Items.field_151110_aK, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityPotion.class, new RenderPotion(mc.field_175616_W, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityExpBottle.class, new RenderSnowball(mc.field_175616_W, Items.field_151062_by, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityFireworkRocket.class, new RenderSnowball(mc.field_175616_W, Items.field_151152_bP, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntityItem.class, new RenderEntityItem(mc.field_175616_W, (RenderItem)ColorMix.modRenderItem));
        mc.field_175616_W.field_78729_o.put(EntitySkeleton.class, new ModRenderSkeleton(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityWitherSkeleton.class, new ModRenderWitherSkeleton(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityStray.class, new ModRenderStray(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityZombie.class, new ModRenderZombie(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityHusk.class, new ModRenderHusk(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityZombieVillager.class, new ModRenderZombieVillager(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityGiantZombie.class, new ModRenderGiantZombie(mc.field_175616_W, 6.0f));
        mc.field_175616_W.field_78729_o.put(EntityPigZombie.class, new ModRenderPigZombie(mc.field_175616_W));
        mc.field_175616_W.field_78729_o.put(EntityArmorStand.class, new ModRenderArmorStand(mc.field_175616_W));
        ((IReloadableResourceManager)mc.func_110442_L()).func_110542_a((IResourceManagerReloadListener)ColorMix.modRenderItem);
    }
    
    public static int getColorForEnchantment(final Map<Enchantment, Integer> enchMap) {
        if (ArmorRender.CustomColor.getValBoolean()) {
            return ArmorRender.Color.getcolor();
        }
        if (!ArmorRender.enableColoredGlint.getValBoolean()) {
            return -8372020;
        }
        final int alpha = 1711276032;
        if (enchMap.containsKey(Enchantments.field_180312_n)) {
            return alpha | ArmorRender.BANE_OF_ARTHROPODS;
        }
        if (enchMap.containsKey(Enchantments.field_77334_n)) {
            return alpha | ArmorRender.FIRE_ASPECT;
        }
        if (enchMap.containsKey(Enchantments.field_180313_o)) {
            return alpha | ArmorRender.KNOCKBACK;
        }
        if (enchMap.containsKey(Enchantments.field_185304_p)) {
            return alpha | ArmorRender.LOOTING;
        }
        if (enchMap.containsKey(Enchantments.field_185302_k)) {
            return alpha | ArmorRender.SHARPNESS;
        }
        if (enchMap.containsKey(Enchantments.field_185303_l)) {
            return alpha | ArmorRender.SMITE;
        }
        if (enchMap.containsKey(Enchantments.field_191530_r)) {
            return alpha | ArmorRender.SWEEPING;
        }
        if (enchMap.containsKey(Enchantments.field_185307_s)) {
            return alpha | ArmorRender.UNBREAKING;
        }
        if (enchMap.containsKey(Enchantments.field_185311_w)) {
            return alpha | ArmorRender.FLAME;
        }
        if (enchMap.containsKey(Enchantments.field_185312_x)) {
            return alpha | ArmorRender.INFINITY;
        }
        if (enchMap.containsKey(Enchantments.field_185309_u)) {
            return alpha | ArmorRender.POWER;
        }
        if (enchMap.containsKey(Enchantments.field_185310_v)) {
            return alpha | ArmorRender.PUNCH;
        }
        if (enchMap.containsKey(Enchantments.field_185305_q)) {
            return alpha | ArmorRender.EFFICIENCY;
        }
        if (enchMap.containsKey(Enchantments.field_185308_t)) {
            return alpha | ArmorRender.FORTUNE;
        }
        if (enchMap.containsKey(Enchantments.field_185306_r)) {
            return alpha | ArmorRender.SILK_TOUCH;
        }
        if (enchMap.containsKey(Enchantments.field_151370_z)) {
            return alpha | ArmorRender.LUCK_OF_THE_SEA;
        }
        if (enchMap.containsKey(Enchantments.field_151369_A)) {
            return alpha | ArmorRender.LURE;
        }
        if (enchMap.containsKey(Enchantments.field_185299_g)) {
            return alpha | ArmorRender.AQUA_AFFINITY;
        }
        if (enchMap.containsKey(Enchantments.field_185297_d)) {
            return alpha | ArmorRender.BLAST_PROTECTION;
        }
        if (enchMap.containsKey(Enchantments.field_185300_i)) {
            return alpha | ArmorRender.DEPTH_STRIDER;
        }
        if (enchMap.containsKey(Enchantments.field_180309_e)) {
            return alpha | ArmorRender.FEATHER_FALLING;
        }
        if (enchMap.containsKey(Enchantments.field_77329_d)) {
            return alpha | ArmorRender.FIRE_PROTECTION;
        }
        if (enchMap.containsKey(Enchantments.field_185301_j)) {
            return alpha | ArmorRender.FROST_WALKER;
        }
        if (enchMap.containsKey(Enchantments.field_185296_A)) {
            return alpha | ArmorRender.MENDING;
        }
        if (enchMap.containsKey(Enchantments.field_180308_g)) {
            return alpha | ArmorRender.PROJECTILE_PROTECTION;
        }
        if (enchMap.containsKey(Enchantments.field_180310_c)) {
            return alpha | ArmorRender.PROTECTION;
        }
        if (enchMap.containsKey(Enchantments.field_185298_f)) {
            return alpha | ArmorRender.RESPIRATION;
        }
        if (enchMap.containsKey(Enchantments.field_92091_k)) {
            return alpha | ArmorRender.THORNS;
        }
        if (enchMap.containsKey(Enchantments.field_190940_C)) {
            return alpha | ArmorRender.VANISHING_CURSE;
        }
        if (enchMap.containsKey(Enchantments.field_190941_k)) {
            return alpha | ArmorRender.BINDING_CURSE;
        }
        return -8372020;
    }
    
    public static float alphaFromColor() {
        return 0.32f;
    }
    
    public static float redFromColor(final int parColor) {
        return (parColor >> 16 & 0xFF) / 255.0f;
    }
    
    public static float greenFromColor(final int parColor) {
        return (parColor >> 8 & 0xFF) / 255.0f;
    }
    
    public static float blueFromColor(final int parColor) {
        return (parColor & 0xFF) / 255.0f;
    }
}
