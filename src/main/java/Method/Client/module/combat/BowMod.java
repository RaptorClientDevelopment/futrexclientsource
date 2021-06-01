package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import Method.Client.utils.visual.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class BowMod extends Module
{
    public Setting BowSpam;
    public Setting PacketSpam;
    public Setting AimBot;
    public Setting walls;
    public Setting yaw;
    public Setting FOV;
    public Setting KickBow;
    public EntityLivingBase target;
    public float rangeAimVelocity;
    
    public BowMod() {
        super("BowMod", 0, Category.COMBAT, "BowMod");
        this.BowSpam = Main.setmgr.add(new Setting("BowSpam", this, false));
        this.PacketSpam = Main.setmgr.add(new Setting("PacketSpam", this, false, this.BowSpam, 2));
        this.AimBot = Main.setmgr.add(new Setting("AimBot", this, false));
        this.walls = Main.setmgr.add(new Setting("walls", this, false, this.AimBot, 6));
        this.yaw = Main.setmgr.add(new Setting("Yaw", this, 22.0, 0.0, 50.0, false, this.AimBot, 4));
        this.FOV = Main.setmgr.add(new Setting("FOV", this, 90.0, 1.0, 180.0, true, this.AimBot, 5));
        this.KickBow = Main.setmgr.add(new Setting("KickBow", this, false));
        this.rangeAimVelocity = 0.0f;
    }
    
    @Override
    public void onDisable() {
        this.target = null;
        super.onDisable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        BowMod.mc.field_71439_g.field_71071_by.func_70448_g();
        if (!(BowMod.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow)) {
            return;
        }
        if (!BowMod.mc.field_71474_y.field_74313_G.func_151470_d()) {
            return;
        }
        if (this.KickBow.getValBoolean() && BowMod.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && BowMod.mc.field_71439_g.func_184587_cr() && BowMod.mc.field_71439_g.func_184612_cw() >= 25) {
            BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, BowMod.mc.field_71439_g.func_174811_aO()));
            BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(BowMod.mc.field_71439_g.func_184600_cs()));
            BowMod.mc.field_71439_g.func_184597_cx();
            if (this.findShulker() != -1) {
                this.changeItem(this.findShulker());
            }
            else {
                ChatUtils.message("No shulker found in hotbar, not switching...");
                this.toggle();
            }
        }
        if (this.AimBot.getValBoolean()) {
            this.target = this.getClosestEntity();
            if (this.target == null) {
                return;
            }
            final float rangeCharge = BowMod.mc.field_71439_g.func_184605_cv();
            this.rangeAimVelocity = rangeCharge / 20.0f;
            this.rangeAimVelocity = (this.rangeAimVelocity * this.rangeAimVelocity + this.rangeAimVelocity * 2.0f) / 3.0f;
            this.rangeAimVelocity = 1.0f;
            final double posX = this.target.field_70165_t - BowMod.mc.field_71439_g.field_70165_t;
            final double posY = this.target.field_70163_u + this.target.func_70047_e() - 0.15 - BowMod.mc.field_71439_g.field_70163_u - BowMod.mc.field_71439_g.func_70047_e();
            final double posZ = this.target.field_70161_v - BowMod.mc.field_71439_g.field_70161_v;
            final double y2 = Math.sqrt(posX * posX + posZ * posZ);
            final float g = 0.006f;
            final float tmp = (float)(this.rangeAimVelocity * this.rangeAimVelocity * this.rangeAimVelocity * this.rangeAimVelocity - g * (g * (y2 * y2) + 2.0 * posY * (this.rangeAimVelocity * this.rangeAimVelocity)));
            final float pitch = (float)(-Math.toDegrees(Math.atan((this.rangeAimVelocity * this.rangeAimVelocity - Math.sqrt(tmp)) / (g * y2))));
            final float[] rot = Utils.getNeededRotations(this.target.func_174791_d(), (float)this.yaw.getValDouble(), 0.0f);
            BowMod.mc.field_71439_g.field_70177_z = rot[0];
            BowMod.mc.field_71439_g.field_70125_A = pitch;
        }
        if (this.BowSpam.getValBoolean() && BowMod.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && BowMod.mc.field_71439_g.func_184587_cr() && BowMod.mc.field_71439_g.func_184612_cw() >= 3) {
            BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, BowMod.mc.field_71439_g.func_174811_aO()));
            if (this.PacketSpam.getValBoolean()) {
                for (int var1 = 0; var1 < 10; ++var1) {
                    BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer(true));
                }
            }
            BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(BowMod.mc.field_71439_g.func_184600_cs()));
            BowMod.mc.field_71439_g.func_184597_cx();
        }
    }
    
    public int findShulker() {
        byte b = -1;
        for (byte b2 = 0; b2 < 9; ++b2) {
            final ItemStack itemStack = (ItemStack)BowMod.mc.field_71439_g.field_71071_by.field_70462_a.get((int)b2);
            if (itemStack.func_77973_b() instanceof ItemShulkerBox) {
                b = b2;
            }
        }
        return b;
    }
    
    public void changeItem(final int paramInt) {
        BowMod.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(paramInt));
        BowMod.mc.field_71439_g.field_71071_by.field_70461_c = paramInt;
    }
    
    public boolean check(final EntityLivingBase entity) {
        return !this.Checks(entity, this.FOV) && ValidUtils.pingCheck(entity) && (this.walls.getValBoolean() || BowMod.mc.field_71439_g.func_70685_l((Entity)entity));
    }
    
    boolean Checks(final EntityLivingBase entity, final Setting fov) {
        return entity instanceof EntityArmorStand || !ValidUtils.isNoScreen() || entity == BowMod.mc.field_71439_g || entity.field_70128_L || ValidUtils.isBot(entity) || ValidUtils.isFriendEnemy(entity) || !ValidUtils.isInAttackFOV(entity, (int)fov.getValDouble()) || !(entity instanceof EntityPlayer);
    }
    
    EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (final Object o : BowMod.mc.field_71441_e.field_72996_f) {
            if (o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
                final EntityLivingBase entity = (EntityLivingBase)o;
                if (!this.check(entity) || (closestEntity != null && BowMod.mc.field_71439_g.func_70032_d((Entity)entity) >= BowMod.mc.field_71439_g.func_70032_d((Entity)closestEntity))) {
                    continue;
                }
                closestEntity = entity;
            }
        }
        return closestEntity;
    }
}
