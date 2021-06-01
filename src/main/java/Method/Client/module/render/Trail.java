package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;

public class Trail extends Module
{
    Setting Self;
    Setting Player;
    Setting Mob;
    Setting Hostile;
    Setting Tickrate;
    Setting Yoffset;
    Setting Trail;
    
    public Trail() {
        super("Trail", 0, Category.RENDER, "Trail");
        this.Self = Main.setmgr.add(new Setting("Self", this, true));
        this.Player = Main.setmgr.add(new Setting("Player", this, true));
        this.Mob = Main.setmgr.add(new Setting("Mob", this, false));
        this.Hostile = Main.setmgr.add(new Setting("Hostile", this, false));
        this.Tickrate = Main.setmgr.add(new Setting("Per Sec", this, 10.0, 2.0, 20.0, true));
        this.Yoffset = Main.setmgr.add(new Setting("Y Offset", this, 0.0, 0.0, 2.0, false));
        this.Trail = Main.setmgr.add(new Setting("Mode", this, "SMOKE", new String[] { "HEART", "FIREWORK", "FLAME", "CLOUD", "WATER", "LAVA", "SLIME", "EXPLOSION", "MAGIC", "REDSTONE", "SWORD" }));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (Method.Client.module.render.Trail.mc.field_71439_g.field_70173_aa % this.Tickrate.getValDouble() == 0.0) {
            for (final Entity object : Method.Client.module.render.Trail.mc.field_71441_e.field_72996_f) {
                if (object instanceof EntityLivingBase) {
                    final EntityLivingBase entity = (EntityLivingBase)object;
                    if (entity instanceof IAnimals && this.Mob.getValBoolean()) {
                        Renderparticle(entity, this.Trail.getValString(), this.Yoffset.getValDouble());
                    }
                    if (entity instanceof IMob && this.Hostile.getValBoolean()) {
                        Renderparticle(entity, this.Trail.getValString(), this.Yoffset.getValDouble());
                    }
                    if (entity instanceof EntityPlayer && this.Player.getValBoolean() && entity != Method.Client.module.render.Trail.mc.field_71439_g) {
                        Renderparticle(entity, this.Trail.getValString(), this.Yoffset.getValDouble());
                    }
                    if (entity != Method.Client.module.render.Trail.mc.field_71439_g || !this.Self.getValBoolean()) {
                        continue;
                    }
                    Renderparticle(entity, this.Trail.getValString(), this.Yoffset.getValDouble());
                }
            }
        }
    }
    
    public static void Renderparticle(final EntityLivingBase entity, final String s, final double yoffset) {
        try {
            switch (s) {
                case "HEART": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.HEART, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "SWORD": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.CRIT, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.CRIT_MAGIC, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "REDSTONE": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.REDSTONE, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "MAGIC": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "LAVA": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.LAVA, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.DRIP_LAVA, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "SMOKE": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "CLOUD": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.CLOUD, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "FLAME": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.FLAME, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "SLIME": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.SLIME, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "EXPLOSION": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "WATER": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.WATER_BUBBLE, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.WATER_SPLASH, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                }
                case "FIREWORK": {
                    Trail.mc.field_71441_e.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, entity.field_70165_t, entity.field_70163_u + 0.01 + yoffset, entity.field_70161_v, entity.field_70159_w * 0.4, entity.field_70181_x * 0.4, entity.field_70179_y * 0.4, new int[0]);
                    break;
                }
            }
        }
        catch (Exception ex) {}
    }
}
