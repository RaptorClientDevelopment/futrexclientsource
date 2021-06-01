package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.proxy.Overrides.*;
import Method.Client.utils.visual.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import java.util.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;

public class YawLock extends Module
{
    float YawOld;
    boolean overshot;
    Setting auto;
    Setting slice;
    Setting yaw;
    Setting Silent;
    Setting Gradual;
    Setting Gradualamnt;
    Setting Wiggle;
    Setting Wiggleamnt;
    float NewYaw;
    
    public YawLock() {
        super("YawLock", 0, Category.PLAYER, "YawLock");
        this.YawOld = 0.0f;
        this.auto = Main.setmgr.add(new Setting("auto", this, true));
        this.slice = Main.setmgr.add(new Setting("slice", this, 45.0, 0.0, 360.0, true));
        this.yaw = Main.setmgr.add(new Setting("yaw", this, 0.0, -360.0, 360.0, true));
        this.Silent = Main.setmgr.add(new Setting("Silent", this, true));
        this.Gradual = Main.setmgr.add(new Setting("Gradual", this, true));
        this.Gradualamnt = Main.setmgr.add(new Setting("Gradualamnt", this, 0.1, 0.0, 1.0, false));
        this.Wiggle = Main.setmgr.add(new Setting("Wiggle", this, true));
        this.Wiggleamnt = Main.setmgr.add(new Setting("Wiggleamnt", this, 0.1, 0.0, 1.0, false));
    }
    
    @Override
    public void onEnable() {
        YawLock.mc.field_71417_B = new PitchYawHelper();
        PitchYawHelper.Yaw = true;
        this.YawOld = YawLock.mc.field_71439_g.field_70177_z;
        this.overshot = false;
        if (this.yaw.getValDouble() > 90.0 || this.yaw.getValDouble() < -90.0) {
            ChatUtils.warning("Out of normal Range! Use Silent?");
        }
    }
    
    @Override
    public void onDisable() {
        PitchYawHelper.Yaw = false;
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        PitchYawHelper.Yaw = !this.Silent.getValBoolean();
        this.NewYaw = this.YawOld;
        if (this.Gradual.getValBoolean() && !this.overshot) {
            this.YawOld += (float)((this.YawOld < this.yaw.getValDouble()) ? this.Gradualamnt.getValDouble() : (-this.Gradualamnt.getValDouble()));
            if ((this.NewYaw > this.yaw.getValDouble() && this.YawOld < this.yaw.getValDouble()) || (this.NewYaw < this.yaw.getValDouble() && this.YawOld > this.yaw.getValDouble())) {
                this.YawOld = (float)this.yaw.getValDouble();
                this.overshot = true;
            }
        }
        if (this.overshot && this.yaw.getValDouble() != this.YawOld) {
            this.overshot = false;
        }
        if (this.Wiggle.getValBoolean()) {
            this.NewYaw += (float)(this.Wiggleamnt.getValDouble() * ((Math.random() > 0.5) ? Math.random() : (-Math.random())));
        }
        if (!this.Silent.getValBoolean()) {
            if (this.slice.getValDouble() == 0.0) {
                return;
            }
            if (this.auto.getValBoolean()) {
                final int angle = (int)(360.0 / this.slice.getValDouble());
                float yaw = YawLock.mc.field_71439_g.field_70177_z;
                yaw = Math.round(yaw / angle) * angle;
                YawLock.mc.field_71439_g.field_70177_z = yaw;
                if (YawLock.mc.field_71439_g.func_184218_aH()) {
                    Objects.requireNonNull(YawLock.mc.field_71439_g.func_184187_bx()).field_70177_z = yaw;
                }
            }
            else {
                YawLock.mc.field_71439_g.field_70177_z = this.NewYaw;
            }
        }
        YawLock.mc.field_71439_g.field_70759_as = this.NewYaw;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.Silent.getValBoolean()) {
            if (packet instanceof CPacketPlayer.Rotation) {
                final CPacketPlayer.Rotation p = (CPacketPlayer.Rotation)packet;
                p.field_149476_e = this.NewYaw;
            }
            if (packet instanceof CPacketPlayer.PositionRotation) {
                final CPacketPlayer.PositionRotation p2 = (CPacketPlayer.PositionRotation)packet;
                p2.field_149476_e = this.NewYaw;
            }
        }
        return true;
    }
}
