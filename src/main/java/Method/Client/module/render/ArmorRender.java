package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;

public class ArmorRender extends Module
{
    public static Setting enableColoredGlint;
    public static Setting useRuneTexture;
    public static Setting CustomColor;
    public static Setting Color;
    public static Setting RenderArmor;
    public static int BANE_OF_ARTHROPODS;
    public static int FIRE_ASPECT;
    public static int KNOCKBACK;
    public static int LOOTING;
    public static int SHARPNESS;
    public static int SMITE;
    public static int SWEEPING;
    public static int UNBREAKING;
    public static int FLAME;
    public static int INFINITY;
    public static int POWER;
    public static int PUNCH;
    public static int EFFICIENCY;
    public static int FORTUNE;
    public static int SILK_TOUCH;
    public static int LUCK_OF_THE_SEA;
    public static int LURE;
    public static int AQUA_AFFINITY;
    public static int BLAST_PROTECTION;
    public static int DEPTH_STRIDER;
    public static int FEATHER_FALLING;
    public static int FIRE_PROTECTION;
    public static int FROST_WALKER;
    public static int MENDING;
    public static int PROJECTILE_PROTECTION;
    public static int PROTECTION;
    public static int RESPIRATION;
    public static int THORNS;
    public static int VANISHING_CURSE;
    public static int BINDING_CURSE;
    public static int DEFAULT;
    
    public ArmorRender() {
        super("ArmorRender", 0, Category.RENDER, "ArmorRender");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(ArmorRender.enableColoredGlint = new Setting("enableColoredGlint", this, false));
        Main.setmgr.add(ArmorRender.CustomColor = new Setting("CustomColor", this, false));
        Main.setmgr.add(ArmorRender.Color = new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.56, ArmorRender.CustomColor, 2));
        Main.setmgr.add(ArmorRender.useRuneTexture = new Setting("useRuneTexture", this, false));
        Main.setmgr.add(ArmorRender.RenderArmor = new Setting("RenderArmor", this, true));
    }
    
    static {
        ArmorRender.BANE_OF_ARTHROPODS = 13369599;
        ArmorRender.FIRE_ASPECT = 16728064;
        ArmorRender.KNOCKBACK = 6684927;
        ArmorRender.LOOTING = 16769126;
        ArmorRender.SHARPNESS = 16750899;
        ArmorRender.SMITE = 52479;
        ArmorRender.SWEEPING = 13434675;
        ArmorRender.UNBREAKING = 52326;
        ArmorRender.FLAME = 16728064;
        ArmorRender.INFINITY = 13369599;
        ArmorRender.POWER = 16750899;
        ArmorRender.PUNCH = 6684927;
        ArmorRender.EFFICIENCY = 3394815;
        ArmorRender.FORTUNE = 16769126;
        ArmorRender.SILK_TOUCH = 13434777;
        ArmorRender.LUCK_OF_THE_SEA = 16769126;
        ArmorRender.LURE = 3394815;
        ArmorRender.AQUA_AFFINITY = 3368703;
        ArmorRender.BLAST_PROTECTION = 13395609;
        ArmorRender.DEPTH_STRIDER = 6711039;
        ArmorRender.FEATHER_FALLING = 13434777;
        ArmorRender.FIRE_PROTECTION = 16728064;
        ArmorRender.FROST_WALKER = 13434879;
        ArmorRender.MENDING = 16769126;
        ArmorRender.PROJECTILE_PROTECTION = 13408767;
        ArmorRender.PROTECTION = 52377;
        ArmorRender.RESPIRATION = 3368703;
        ArmorRender.THORNS = 16750899;
        ArmorRender.VANISHING_CURSE = 6684876;
        ArmorRender.BINDING_CURSE = 16777215;
        ArmorRender.DEFAULT = -8372020;
    }
}
