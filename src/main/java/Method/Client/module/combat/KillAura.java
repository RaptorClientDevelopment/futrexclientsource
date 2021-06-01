package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import Method.Client.utils.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import Method.Client.utils.system.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

public class KillAura extends Module
{
    public Setting priority;
    public Setting walls;
    public Setting autoDelay;
    public Setting packetReach;
    public Setting minCPS;
    public Setting maxCPS;
    public Setting packetRange;
    public Setting range;
    public Setting FOV;
    public Setting Mobs;
    public Setting Animals;
    public Setting SpoofAngle;
    public TimerUtils timer;
    public EntityLivingBase target;
    
    public KillAura() {
        super("KillAura", 0, Category.COMBAT, "KillAura");
        this.priority = Main.setmgr.add(new Setting("priority Mode", this, "Closest", new String[] { "Closest", "Health" }));
        this.walls = Main.setmgr.add(new Setting("walls", this, true));
        this.autoDelay = Main.setmgr.add(new Setting("autoDelay", this, false));
        this.packetReach = Main.setmgr.add(new Setting("packetReach", this, false));
        this.minCPS = Main.setmgr.add(new Setting("minCPS", this, 5.0, 0.0, 30.0, false));
        this.maxCPS = Main.setmgr.add(new Setting("maxCPS", this, 8.0, 0.0, 30.0, false));
        this.packetRange = Main.setmgr.add(new Setting("packetRange", this, 5.0, 0.0, 100.0, false));
        this.range = Main.setmgr.add(new Setting("range", this, 5.0, 0.0, 10.0, false));
        this.FOV = Main.setmgr.add(new Setting("FOV", this, 180.0, 0.0, 180.0, false));
        this.Mobs = Main.setmgr.add(new Setting("Mobs", this, true));
        this.Animals = Main.setmgr.add(new Setting("Animals", this, false));
        this.SpoofAngle = Main.setmgr.add(new Setting("SpoofAngle", this, true));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.killAuraUpdate();
        this.killAuraAttack(this.target);
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        this.killAuraUpdate();
        if (side == Connection.Side.OUT && this.SpoofAngle.getValBoolean()) {
            if (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation) {
                final CPacketPlayer packet2 = (CPacketPlayer)packet;
                final float[] rot = Utils.getNeededRotations(this.target.func_174791_d().func_72441_c(0.0, (double)(this.target.func_70047_e() / 2.0f), 0.0), 0.0f, 0.0f);
                packet2.field_149476_e = rot[0];
                packet2.field_149473_f = rot[1];
            }
            this.target = null;
        }
        return true;
    }
    
    void killAuraUpdate() {
        for (final Object object : KillAura.mc.field_71441_e.func_72910_y()) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                if (!this.check(entity)) {
                    continue;
                }
                this.target = entity;
            }
        }
    }
    
    public void killAuraAttack(final EntityLivingBase entity) {
        if (entity == null) {
            return;
        }
        if (this.autoDelay.getValBoolean()) {
            if (KillAura.mc.field_71439_g.func_184825_o(0.0f) == 1.0f) {
                this.processAttack(entity);
                this.target = null;
            }
        }
        else {
            final int CPS = Utils.random((int)this.minCPS.getValDouble(), (int)this.maxCPS.getValDouble());
            final int r1 = Utils.random(1, 50);
            final int r2 = Utils.random(1, 60);
            final int r3 = Utils.random(1, 70);
            if (this.timer.isDelay((1000 + (r1 - r2 + r3)) / CPS)) {
                this.processAttack(entity);
                this.timer.setLastMS();
                this.target = null;
            }
        }
    }
    
    public void processAttack(final EntityLivingBase entity) {
        if (this.isInAttackRange(entity) || !ValidUtils.isInAttackFOV(entity, (int)this.FOV.getValDouble())) {
            return;
        }
        final float sharpLevel = EnchantmentHelper.func_152377_a(KillAura.mc.field_71439_g.func_184614_ca(), entity.func_70668_bt());
        if (this.packetReach.getValBoolean()) {
            final double posX = entity.field_70165_t - 3.5 * Math.cos(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            final double posZ = entity.field_70161_v - 3.5 * Math.sin(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, entity.field_70163_u, posZ, Utils.getYaw((Entity)entity), Utils.getPitch((Entity)entity), KillAura.mc.field_71439_g.field_70122_E));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(KillAura.mc.field_71439_g.field_70165_t, KillAura.mc.field_71439_g.field_70163_u, KillAura.mc.field_71439_g.field_70161_v, KillAura.mc.field_71439_g.field_70122_E));
        }
        else if (this.autoDelay.getValBoolean()) {
            Wrapper.INSTANCE.attack((Entity)entity);
        }
        else {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
        }
        Wrapper.INSTANCE.swingArm();
        if (sharpLevel > 0.0f) {
            KillAura.mc.field_71439_g.func_71047_c((Entity)entity);
        }
    }
    
    boolean isPriority(final EntityLivingBase entity) {
        return (this.priority.getValString().equalsIgnoreCase("Closest") && ValidUtils.isClosest(entity, this.target)) || (this.priority.getValString().equalsIgnoreCase("Health") && ValidUtils.isLowHealth(entity, this.target));
    }
    
    boolean isInAttackRange(final EntityLivingBase entity) {
        return this.packetReach.getValBoolean() ? (entity.func_70032_d((Entity)KillAura.mc.field_71439_g) > this.packetRange.getValDouble()) : (entity.func_70032_d((Entity)KillAura.mc.field_71439_g) > this.range.getValDouble());
    }
    
    public boolean check(final EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (!ValidUtils.isNoScreen()) {
            return false;
        }
        if (entity == KillAura.mc.field_71439_g) {
            return false;
        }
        if (entity.field_70128_L) {
            return false;
        }
        if (entity.field_70725_aQ > 0) {
            return false;
        }
        if (ValidUtils.isBot(entity)) {
            return false;
        }
        if (ValidUtils.isFriendEnemy(entity)) {
            return false;
        }
        if (!ValidUtils.isInAttackFOV(entity, (int)this.FOV.getValDouble())) {
            return false;
        }
        if (this.isInAttackRange(entity)) {
            return false;
        }
        if (!ValidUtils.pingCheck(entity)) {
            return false;
        }
        if (!this.walls.getValBoolean() && !KillAura.mc.field_71439_g.func_70685_l((Entity)entity)) {
            return false;
        }
        if (this.Animals.getValBoolean() && entity instanceof IAnimals) {
            return this.isPriority(entity);
        }
        if (this.Mobs.getValBoolean() && entity instanceof IMob) {
            return this.isPriority(entity);
        }
        return entity instanceof EntityPlayer && this.isPriority(entity);
    }
    
    public static boolean isInvisible(final EntityLivingBase entity) {
        return !entity.func_82150_aj();
    }
}
