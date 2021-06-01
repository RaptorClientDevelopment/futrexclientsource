package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

public class AimBot extends Module
{
    Setting priority;
    Setting walls;
    Setting yaw;
    Setting pitch;
    Setting range;
    Setting FOV;
    Setting Silent;
    Setting Mobs;
    Setting Animals;
    public EntityLivingBase target;
    
    public AimBot() {
        super("AimBot", 0, Category.COMBAT, "Aims to enemys");
        this.priority = Main.setmgr.add(new Setting("priority", this, "Closest", new String[] { "Closest", "Health" }));
        this.walls = Main.setmgr.add(new Setting("walls", this, false));
        this.yaw = Main.setmgr.add(new Setting("yaw", this, 15.0, 0.0, 50.0, false));
        this.pitch = Main.setmgr.add(new Setting("pitch", this, 15.0, 0.0, 50.0, false));
        this.range = Main.setmgr.add(new Setting("range", this, 4.7, 0.1, 10.0, false));
        this.FOV = Main.setmgr.add(new Setting("FOV", this, 90.0, 1.0, 180.0, false));
        this.Silent = Main.setmgr.add(new Setting("Silent", this, false));
        this.Mobs = Main.setmgr.add(new Setting("Mobs", this, true));
        this.Animals = Main.setmgr.add(new Setting("Animals", this, false));
    }
    
    @Override
    public void onDisable() {
        this.target = null;
        super.onDisable();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.Silent.getValBoolean()) {
            if (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation) {
                this.updateTarget();
                final CPacketPlayer packet2 = (CPacketPlayer)packet;
                final float[] rot = Utils.getNeededRotations(this.target.func_174791_d().func_72441_c(0.0, (double)(this.target.func_70047_e() / 2.0f), 0.0), (float)this.yaw.getValDouble(), (float)this.pitch.getValDouble());
                packet2.field_149476_e = rot[0];
                packet2.field_149473_f = rot[1];
            }
            this.target = null;
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.updateTarget();
        if (!this.Silent.getValBoolean()) {
            final float[] rot = Utils.getNeededRotations(this.target.func_174791_d().func_72441_c(0.0, (double)(this.target.func_70047_e() / 2.0f), 0.0), (float)this.yaw.getValDouble(), (float)this.pitch.getValDouble());
            AimBot.mc.field_71439_g.field_70177_z = rot[0];
            AimBot.mc.field_71439_g.field_70125_A = rot[1];
        }
        this.target = null;
        super.onClientTick(event);
    }
    
    void updateTarget() {
        for (final Object object : AimBot.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                if (!this.check(entity)) {
                    continue;
                }
                this.target = entity;
            }
        }
    }
    
    public boolean check(final EntityLivingBase entity) {
        return !this.Checks(entity, this.FOV) && ValidUtils.isInAttackRange(entity, (float)this.range.getValDouble()) && ValidUtils.pingCheck(entity) && this.isPriority(entity) && (this.walls.getValBoolean() || AimBot.mc.field_71439_g.func_70685_l((Entity)entity));
    }
    
    boolean Checks(final EntityLivingBase entity, final Setting fov) {
        return entity instanceof EntityArmorStand || !ValidUtils.isNoScreen() || entity == AimBot.mc.field_71439_g || entity.field_70128_L || ValidUtils.isBot(entity) || ValidUtils.isFriendEnemy(entity) || !ValidUtils.isInAttackFOV(entity, (int)fov.getValDouble()) || ((!this.Animals.getValBoolean() || !(entity instanceof IAnimals)) && (!this.Mobs.getValBoolean() || !(entity instanceof IMob)) && !(entity instanceof EntityPlayer));
    }
    
    boolean isPriority(final EntityLivingBase entity) {
        return (this.priority.getValString().equalsIgnoreCase("Closest") && ValidUtils.isClosest(entity, this.target)) || (this.priority.getValString().equalsIgnoreCase("Health") && ValidUtils.isLowHealth(entity, this.target));
    }
}
