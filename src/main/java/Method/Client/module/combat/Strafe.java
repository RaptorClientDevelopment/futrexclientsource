package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

public class Strafe extends Module
{
    Setting mode;
    Setting jump;
    Setting extraYBoost;
    Setting jumpDetect;
    Setting speedDetect;
    Setting multiplier;
    Setting ground;
    int waitCounter;
    int forward;
    private double motionSpeed;
    private int currentState;
    private double prevDist;
    
    public Strafe() {
        super("Strafe", 0, Category.COMBAT, "Strafe");
        this.mode = Main.setmgr.add(new Setting("Strafe Mode", this, "Vanilla", new String[] { "Vanilla", "Fast", "Bypass" }));
        this.jump = Main.setmgr.add(new Setting("FastJump", this, true, this.mode, "Bypass", 1));
        this.extraYBoost = Main.setmgr.add(new Setting("extraYBoost", this, 0.0, 0.0, 1.0, false, this.mode, "Fast", 1));
        this.jumpDetect = Main.setmgr.add(new Setting("jumpDetect", this, false, this.mode, "Fast", 1));
        this.speedDetect = Main.setmgr.add(new Setting("speedDetect", this, false, this.mode, "Fast", 1));
        this.multiplier = Main.setmgr.add(new Setting("multiplier", this, 1.0, 0.0, 1.0, false, this.mode, "Fast", 1));
        this.ground = Main.setmgr.add(new Setting("ground", this, true, this.mode, "Vanilla", 1));
        this.waitCounter = 0;
        this.forward = 1;
    }
    
    @Override
    public void onDisable() {
        Strafe.mc.field_71428_T.field_194149_e = 50.0f;
        super.onEnable();
    }
    
    private double getBaseMotionSpeed() {
        double v = 0.272;
        if (Strafe.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && this.speedDetect.getValBoolean()) {
            final int amplifier = Objects.requireNonNull(Strafe.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c();
            v *= 1.0 + 0.2 * amplifier;
        }
        return v;
    }
    
    private static int calcl(final float var0) {
        final float var;
        return ((var = var0 - 0.0f) == 0.0f) ? 0 : ((var < 0.0f) ? -1 : 1);
    }
    
    private static int Call(final double var0) {
        final double var;
        return ((var = var0 - 0.0) == 0.0) ? 0 : ((var < 0.0) ? -1 : 1);
    }
    
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Fast")) {
            switch (this.currentState) {
                case 0: {
                    ++this.currentState;
                    this.prevDist = 0.0;
                    break;
                }
                case 2: {
                    double v = 0.40123128 + this.extraYBoost.getValDouble();
                    if ((calcl(Strafe.mc.field_71439_g.field_191988_bg) != 0 || calcl(Strafe.mc.field_71439_g.field_70702_br) != 0) && Strafe.mc.field_71439_g.field_70122_E) {
                        if (Strafe.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && this.jumpDetect.getValBoolean()) {
                            v += (Objects.requireNonNull(Strafe.mc.field_71439_g.func_70660_b(MobEffects.field_76430_j)).func_76458_c() + 1) * 0.1f;
                        }
                        Strafe.mc.field_71439_g.field_70181_x = v;
                        this.motionSpeed *= 2.149;
                        break;
                    }
                    break;
                }
                case 3: {
                    this.motionSpeed = this.prevDist - 0.76 * (this.prevDist - this.getBaseMotionSpeed());
                    break;
                }
                default: {
                    if ((Strafe.mc.field_71441_e.func_184144_a((Entity)Strafe.mc.field_71439_g, Strafe.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, Strafe.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || Strafe.mc.field_71439_g.field_70124_G) && this.currentState > 0) {
                        if (calcl(Strafe.mc.field_71439_g.field_191988_bg) == 0 && calcl(Strafe.mc.field_71439_g.field_70702_br) == 0) {
                            this.currentState = 0;
                        }
                        else {
                            this.currentState = 1;
                        }
                    }
                    this.motionSpeed = this.prevDist - this.prevDist / 159.0;
                    break;
                }
            }
            this.motionSpeed = Math.max(this.motionSpeed, this.getBaseMotionSpeed());
            double v2 = Strafe.mc.field_71439_g.field_71158_b.field_192832_b;
            double v3 = Strafe.mc.field_71439_g.field_71158_b.field_78902_a;
            final double v4 = Strafe.mc.field_71439_g.field_70177_z;
            if (Call(v2) == 0 && Call(v3) == 0) {
                Strafe.mc.field_71439_g.field_70159_w = 0.0;
                Strafe.mc.field_71439_g.field_70179_y = 0.0;
            }
            if (Call(v2) != 0 && Call(v3) != 0) {
                v2 *= Math.sin(0.7853981633974483);
                v3 *= Math.cos(0.7853981633974483);
            }
            Strafe.mc.field_71439_g.field_70159_w = (v2 * this.motionSpeed * -Math.sin(Math.toRadians(v4)) + v3 * this.motionSpeed * Math.cos(Math.toRadians(v4))) * (this.multiplier.getValDouble() * 0.99);
            Strafe.mc.field_71439_g.field_70179_y = (v2 * this.motionSpeed * Math.cos(Math.toRadians(v4)) - v3 * this.motionSpeed * -Math.sin(Math.toRadians(v4))) * (this.multiplier.getValDouble() * 0.99);
            ++this.currentState;
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Fast")) {
            this.prevDist = Math.sqrt((Strafe.mc.field_71439_g.field_70165_t - Strafe.mc.field_71439_g.field_70169_q) * (Strafe.mc.field_71439_g.field_70165_t - Strafe.mc.field_71439_g.field_70169_q) + (Strafe.mc.field_71439_g.field_70161_v - Strafe.mc.field_71439_g.field_70166_s) * (Strafe.mc.field_71439_g.field_70161_v - Strafe.mc.field_71439_g.field_70166_s));
        }
        if (Utils.isMoving((Entity)Strafe.mc.field_71439_g) && this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            if (Strafe.mc.field_71439_g.func_70093_af() || Strafe.mc.field_71439_g.func_70617_f_() || Strafe.mc.field_71439_g.field_70134_J || Strafe.mc.field_71439_g.func_180799_ab() || Strafe.mc.field_71439_g.func_70090_H() || Strafe.mc.field_71439_g.field_71075_bZ.field_75100_b) {
                return;
            }
            if (!this.ground.getValBoolean() && Strafe.mc.field_71439_g.field_70122_E) {
                return;
            }
            float playerSpeed = 0.2873f;
            if (Strafe.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
                final int amplifier = Objects.requireNonNull(Strafe.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c();
                playerSpeed *= 1.0f + 0.2f * (amplifier + 1);
            }
            if (Strafe.mc.field_71439_g.field_71158_b.field_192832_b == 0.0f && Strafe.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) {
                Strafe.mc.field_71439_g.field_70159_w = 0.0;
                Strafe.mc.field_71439_g.field_70179_y = 0.0;
            }
            else {
                Utils.directionSpeed(playerSpeed);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Bypass")) {
            Strafe.mc.field_71428_T.field_194149_e = 45.5f;
            final boolean boost = Math.abs(Strafe.mc.field_71439_g.field_70759_as - Strafe.mc.field_71439_g.field_70177_z) < 90.0f;
            if ((this.isPlayerTryingMoveForward() || this.isPlayerTryingStrafe()) && Strafe.mc.field_71439_g.field_70122_E) {
                Strafe.mc.field_71439_g.field_70181_x = 0.4;
            }
            if (Strafe.mc.field_71439_g.field_191988_bg != 0.0f) {
                if (!Strafe.mc.field_71439_g.func_70051_ag()) {
                    Strafe.mc.field_71439_g.func_70031_b(true);
                }
                float yaw = Strafe.mc.field_71439_g.field_70177_z;
                if (Strafe.mc.field_71439_g.field_191988_bg > 0.0f) {
                    if (Strafe.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f) {
                        yaw += ((Strafe.mc.field_71439_g.field_71158_b.field_78902_a > 0.0f) ? -45.0f : 45.0f);
                    }
                    this.forward = 1;
                    Strafe.mc.field_71439_g.field_191988_bg = 1.5f;
                    Strafe.mc.field_71439_g.field_70702_br = 1.5f;
                }
                else if (Strafe.mc.field_71439_g.field_191988_bg < 0.0f) {
                    if (Strafe.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f) {
                        yaw += ((Strafe.mc.field_71439_g.field_71158_b.field_78902_a > 0.0f) ? 45.0f : -45.0f);
                    }
                    this.forward = -1;
                    Strafe.mc.field_71439_g.field_191988_bg = -1.5f;
                    Strafe.mc.field_71439_g.field_70702_br = 1.5f;
                }
                if (Strafe.mc.field_71439_g.field_70122_E) {
                    final float f = (float)Math.toRadians(yaw);
                    if (this.jump.getValBoolean() && Strafe.mc.field_71474_y.field_74314_A.func_151468_f()) {
                        this.Move(f);
                    }
                }
                else {
                    if (this.waitCounter < 1) {
                        ++this.waitCounter;
                        return;
                    }
                    this.waitCounter = 0;
                    final double currentSpeed = Math.sqrt(Strafe.mc.field_71439_g.field_70159_w * Strafe.mc.field_71439_g.field_70159_w + Strafe.mc.field_71439_g.field_70179_y * Strafe.mc.field_71439_g.field_70179_y);
                    double speed = boost ? 1.05 : 1.025;
                    if (Strafe.mc.field_71439_g.field_70181_x < 0.0) {
                        speed = 1.0;
                    }
                    final double direction = Math.toRadians(yaw);
                    Strafe.mc.field_71439_g.field_70159_w = -Math.sin(direction) * speed * currentSpeed * this.forward;
                    Strafe.mc.field_71439_g.field_70179_y = Math.cos(direction) * speed * currentSpeed * this.forward;
                }
            }
            if (!this.isPlayerTryingMoveForward() && !this.isPlayerTryingStrafe()) {
                Strafe.mc.field_71439_g.field_70159_w = 0.0;
                Strafe.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        super.onClientTick(event);
    }
    
    private void Move(final float f) {
        if (this.jump.getValBoolean()) {
            Strafe.mc.field_71439_g.field_70181_x = 0.4;
        }
        final EntityPlayerSP field_71439_g = Strafe.mc.field_71439_g;
        field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f * this.forward;
        final EntityPlayerSP field_71439_g2 = Strafe.mc.field_71439_g;
        field_71439_g2.field_70179_y += MathHelper.func_76134_b(f) * 0.2f * this.forward;
    }
    
    public boolean isPlayerTryingMoveForward() {
        return Strafe.mc.field_71474_y.field_74351_w.func_151470_d() || Strafe.mc.field_71474_y.field_74368_y.func_151470_d();
    }
    
    public boolean isPlayerTryingStrafe() {
        return Strafe.mc.field_71474_y.field_74366_z.func_151470_d() || Strafe.mc.field_71474_y.field_74370_x.func_151470_d();
    }
}
