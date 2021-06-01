package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.*;
import Method.Client.utils.*;

public class Trigger extends Module
{
    public Setting autoDelay;
    public Setting advanced;
    public Setting minCPS;
    public Setting maxCPS;
    Setting Mode;
    public EntityLivingBase target;
    public TimerUtils timer;
    
    public Trigger() {
        super("Trigger", 0, Category.COMBAT, "Triggers");
        this.autoDelay = Main.setmgr.add(new Setting("AutoDelay", this, true));
        this.advanced = Main.setmgr.add(new Setting("Advanced", this, false));
        this.minCPS = Main.setmgr.add(new Setting("MinCPS", this, 4.0, 1.0, 20.0, true));
        this.maxCPS = Main.setmgr.add(new Setting("MaxCPS", this, 8.0, 1.0, 20.0, false));
        this.Mode = Main.setmgr.add(new Setting("Mode", this, "Click", new String[] { "Click", "Attack" }));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onDisable() {
        this.target = null;
        super.onDisable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.updateTarget();
        this.attackTarget(this.target);
        super.onClientTick(event);
    }
    
    void attackTarget(final EntityLivingBase target) {
        if (this.check(target)) {
            if (this.autoDelay.getValBoolean()) {
                if (Trigger.mc.field_71439_g.func_184825_o(0.0f) == 1.0f) {
                    this.processAttack(target, false);
                }
            }
            else {
                final int currentCPS = Utils.random((int)this.minCPS.getValDouble(), (int)this.maxCPS.getValDouble());
                if (this.timer.isDelay(1000 / currentCPS)) {
                    this.processAttack(target, true);
                    this.timer.setLastMS();
                }
            }
        }
    }
    
    public void processAttack(final EntityLivingBase entity, final boolean packet) {
        final float sharpLevel = EnchantmentHelper.func_152377_a(Trigger.mc.field_71439_g.func_184614_ca(), this.target.func_70668_bt());
        if (this.Mode.getValString().equalsIgnoreCase("Click")) {
            Trigger.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        }
        else {
            if (packet) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)this.target));
            }
            else {
                Wrapper.INSTANCE.attack((Entity)this.target);
            }
            Wrapper.INSTANCE.swingArm();
            if (sharpLevel > 0.0f) {
                Trigger.mc.field_71439_g.func_71047_c((Entity)this.target);
            }
        }
    }
    
    void updateTarget() {
        final RayTraceResult object = Wrapper.INSTANCE.mc().field_71476_x;
        if (object == null) {
            return;
        }
        EntityLivingBase entity = null;
        if (this.target != entity) {
            this.target = null;
        }
        if (object.field_72313_a == RayTraceResult.Type.ENTITY) {
            if (object.field_72308_g instanceof EntityLivingBase) {
                entity = (EntityLivingBase)object.field_72308_g;
                this.target = entity;
            }
        }
        else if (object.field_72313_a != RayTraceResult.Type.ENTITY && this.advanced.getValBoolean()) {
            entity = this.getClosestEntity();
        }
        if (entity != null) {
            this.target = entity;
        }
    }
    
    EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (final Object o : Trigger.mc.field_71441_e.field_72996_f) {
            if (o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
                final EntityLivingBase entity = (EntityLivingBase)o;
                if (!this.check(entity) || (closestEntity != null && Trigger.mc.field_71439_g.func_70032_d((Entity)entity) >= Trigger.mc.field_71439_g.func_70032_d((Entity)closestEntity))) {
                    continue;
                }
                closestEntity = entity;
            }
        }
        return closestEntity;
    }
    
    public boolean check(final EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (!ValidUtils.isNoScreen()) {
            return false;
        }
        if (entity == Trigger.mc.field_71439_g) {
            return false;
        }
        if (entity.field_70128_L) {
            return false;
        }
        if (ValidUtils.isBot(entity)) {
            return false;
        }
        if (ValidUtils.isFriendEnemy(entity)) {
            return false;
        }
        if (this.advanced.getValBoolean()) {
            if (!ValidUtils.isInAttackFOV(entity, 50)) {
                return false;
            }
            if (!ValidUtils.isInAttackRange(entity, 4.7f)) {
                return false;
            }
        }
        return ValidUtils.pingCheck(entity) && Trigger.mc.field_71439_g.func_70685_l((Entity)entity);
    }
}
