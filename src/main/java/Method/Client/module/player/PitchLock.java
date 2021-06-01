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

public class PitchLock extends Module
{
    float PitchOLD;
    boolean overshot;
    Setting auto;
    Setting slice;
    Setting pitch;
    Setting Gradual;
    Setting Gradualamnt;
    Setting Wiggle;
    Setting Wiggleamnt;
    Setting Silent;
    float NewPitch;
    
    public PitchLock() {
        super("PitchLock", 0, Category.PLAYER, "PitchLock");
        this.PitchOLD = 0.0f;
        this.auto = Main.setmgr.add(new Setting("Auto Snap", this, true));
        this.slice = Main.setmgr.add(new Setting("Auto Slice", this, 45.0, 0.0, 360.0, true, this.auto, 1));
        this.pitch = Main.setmgr.add(new Setting("pitch", this, 0.0, -180.0, 180.0, true));
        this.Gradual = Main.setmgr.add(new Setting("Gradual", this, true));
        this.Gradualamnt = Main.setmgr.add(new Setting("Gradualamnt", this, 0.1, 0.0, 1.0, false));
        this.Wiggle = Main.setmgr.add(new Setting("Wiggle", this, true));
        this.Wiggleamnt = Main.setmgr.add(new Setting("Wiggleamnt", this, 0.1, 0.0, 1.0, false, this.Wiggle, 5));
        this.Silent = Main.setmgr.add(new Setting("Silent", this, true));
    }
    
    @Override
    public void onEnable() {
        PitchLock.mc.field_71417_B = new PitchYawHelper();
        PitchYawHelper.Pitch = true;
        this.PitchOLD = PitchLock.mc.field_71439_g.field_70125_A;
        this.overshot = false;
        if (this.pitch.getValDouble() > 90.0 || this.pitch.getValDouble() < -90.0) {
            ChatUtils.warning("Out of normal Range! Use Silent?");
        }
    }
    
    @Override
    public void onDisable() {
        PitchYawHelper.Pitch = false;
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        PitchYawHelper.Pitch = !this.Silent.getValBoolean();
        this.NewPitch = this.PitchOLD;
        if (this.Gradual.getValBoolean() && !this.overshot) {
            this.PitchOLD += (float)((this.PitchOLD < this.pitch.getValDouble()) ? this.Gradualamnt.getValDouble() : (-this.Gradualamnt.getValDouble()));
            if ((this.NewPitch > this.pitch.getValDouble() && this.PitchOLD < this.pitch.getValDouble()) || (this.NewPitch < this.pitch.getValDouble() && this.PitchOLD > this.pitch.getValDouble())) {
                this.PitchOLD = (float)this.pitch.getValDouble();
                this.overshot = true;
            }
        }
        if (this.overshot && this.pitch.getValDouble() != this.PitchOLD) {
            this.overshot = false;
        }
        if (!this.Gradual.getValBoolean()) {
            this.NewPitch = (float)this.pitch.getValDouble();
        }
        if (this.Wiggle.getValBoolean()) {
            this.NewPitch += (float)(this.Wiggleamnt.getValDouble() * ((Math.random() > 0.5) ? Math.random() : (-Math.random())));
        }
        if (!this.Silent.getValBoolean()) {
            if (this.slice.getValDouble() == 0.0) {
                return;
            }
            if (this.auto.getValBoolean()) {
                final int angle = (int)(360.0 / this.slice.getValDouble());
                float yaw = PitchLock.mc.field_71439_g.field_70125_A;
                yaw = Math.round(yaw / angle) * angle;
                PitchLock.mc.field_71439_g.field_70125_A = yaw;
                if (PitchLock.mc.field_71439_g.func_184218_aH()) {
                    Objects.requireNonNull(PitchLock.mc.field_71439_g.func_184187_bx()).field_70125_A = yaw;
                }
            }
            else {
                PitchLock.mc.field_71439_g.field_70125_A = this.NewPitch;
            }
        }
        PitchLock.mc.field_71439_g.field_70761_aq = this.NewPitch;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.Silent.getValBoolean()) {
            if (packet instanceof CPacketPlayer.Rotation) {
                final CPacketPlayer.Rotation p = (CPacketPlayer.Rotation)packet;
                p.field_149473_f = this.NewPitch;
            }
            if (packet instanceof CPacketPlayer.PositionRotation) {
                final CPacketPlayer.PositionRotation p2 = (CPacketPlayer.PositionRotation)packet;
                p2.field_149473_f = this.NewPitch;
            }
        }
        return true;
    }
}
