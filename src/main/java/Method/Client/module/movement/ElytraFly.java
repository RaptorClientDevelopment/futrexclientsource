package Method.Client.module.movement;

import Method.Client.managers.*;
import net.minecraft.entity.item.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import Method.Client.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import java.util.*;
import Method.Client.utils.system.*;

public class ElytraFly extends Module
{
    Setting mode;
    Setting speed;
    Setting autoStart;
    Setting disableInLiquid;
    Setting infiniteDurability;
    Setting Flatfly;
    Setting BuildupTicks;
    Setting noKick;
    Setting RandomFlap;
    Setting Yboost;
    Setting Flatboost;
    Setting Fallspeedboost;
    Setting Fallspeed;
    Setting StopPitch;
    Setting Speedme;
    Setting upspeed;
    Setting SpeedMulti;
    Setting AutoRocket;
    Setting RocketTicks;
    Setting Lockmove;
    Setting AfterBurner;
    Setting TicksAfter;
    double yposperson;
    double Lazyticks;
    EntityFireworkRocket wasBoosted;
    private final TimerUtils timer;
    private final TimerUtils Fireworktimer1;
    private final TimerUtils Fireworktimer2;
    
    public ElytraFly() {
        super("ElytraFly", 0, Category.MOVEMENT, "Elytra Fly");
        this.mode = Main.setmgr.add(new Setting("mode", this, "Control", new String[] { "BYPASS", "Control", "Boost", "Try1", "Legit+", "Mouse", "Rocket" }));
        this.speed = Main.setmgr.add(new Setting("speed", this, 1.0, 0.0, 5.0, false));
        this.autoStart = Main.setmgr.add(new Setting("autoStart", this, false));
        this.disableInLiquid = Main.setmgr.add(new Setting("disableInLiquid", this, false));
        this.infiniteDurability = Main.setmgr.add(new Setting("Packet Durability", this, false));
        this.Flatfly = Main.setmgr.add(new Setting("Flatfly", this, false, this.mode, "Control", 6));
        this.BuildupTicks = Main.setmgr.add(new Setting("BuildupTicks", this, 0.0, 0.0, 200.0, false, this.mode, "Control", 7));
        this.noKick = Main.setmgr.add(new Setting("noKick", this, false, this.mode, "Control", 8));
        this.RandomFlap = Main.setmgr.add(new Setting("RandomFlap", this, false));
        this.Yboost = Main.setmgr.add(new Setting("Yboost", this, false, this.mode, "Boost", 8));
        this.Flatboost = Main.setmgr.add(new Setting("Flatboost", this, false, this.mode, "Boost", 8));
        this.Fallspeedboost = Main.setmgr.add(new Setting("FlatFall% ", this, 99.95, 10.0, 99.95, false, this.mode, "Boost", 7));
        this.Fallspeed = Main.setmgr.add(new Setting("Fall multiplier", this, 99.95, 10.0, 99.95, false, this.mode, "Control", 7));
        this.StopPitch = Main.setmgr.add(new Setting("StopPitch", this, 0.0, 0.0, 90.0, false, this.mode, "Control", 7));
        this.Speedme = Main.setmgr.add(new Setting("Speed Weird", this, 1.0, 0.01, 3.0, false, this.mode, "Try1", 6));
        this.upspeed = Main.setmgr.add(new Setting("upspeed", this, 1.0, 0.0, 3.0, false, this.mode, "Mouse", 6));
        this.SpeedMulti = Main.setmgr.add(new Setting("Burner Speed", this, 2.0, 0.0, 10.0, false, this.mode, "Rocket", 7));
        this.AutoRocket = Main.setmgr.add(new Setting("AutoRocket", this, false, this.mode, "Rocket", 6));
        this.RocketTicks = Main.setmgr.add(new Setting("RocketTicks ", this, 22.0, 0.0, 100.0, false, this.mode, "Rocket", 7));
        this.Lockmove = Main.setmgr.add(new Setting("Lockmove", this, false, this.mode, "Rocket", 6));
        this.AfterBurner = Main.setmgr.add(new Setting("After Burner", this, false, this.mode, "Rocket", 6));
        this.TicksAfter = Main.setmgr.add(new Setting("Burner Ticks", this, 22.0, 0.0, 60.0, false, this.mode, "Rocket", 7));
        this.Lazyticks = 0.0;
        this.wasBoosted = null;
        this.timer = new TimerUtils();
        this.Fireworktimer1 = new TimerUtils();
        this.Fireworktimer2 = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (ElytraFly.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() != Items.field_185160_cR) {
            return;
        }
        if (this.disableInLiquid.getValBoolean() && (ElytraFly.mc.field_71439_g.func_70090_H() || ElytraFly.mc.field_71439_g.func_180799_ab())) {
            if (ElytraFly.mc.field_71439_g.func_184613_cA()) {
                Objects.requireNonNull(ElytraFly.mc.func_147114_u()).func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFly.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            return;
        }
        if (this.autoStart.getValBoolean() && !ElytraFly.mc.field_71439_g.func_184613_cA()) {
            if (ElytraFly.mc.field_71439_g.field_70163_u + 0.02 < ElytraFly.mc.field_71439_g.field_70137_T && !ElytraFly.mc.field_71439_g.field_70122_E) {
                ElytraFly.mc.field_71439_g.field_70163_u = this.yposperson;
                Objects.requireNonNull(ElytraFly.mc.func_147114_u()).func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFly.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ElytraFly.mc.field_71439_g.field_70165_t, ElytraFly.mc.field_71439_g.field_70163_u, ElytraFly.mc.field_71439_g.field_70161_v, false));
            }
            else {
                this.yposperson = ElytraFly.mc.field_71439_g.field_70163_u;
            }
        }
        if (ElytraFly.mc.field_71439_g.func_184613_cA()) {
            if (this.RandomFlap.getValBoolean()) {
                ElytraFly.mc.field_71439_g.field_184835_a = (float)Math.random();
                ElytraFly.mc.field_71439_g.field_184836_b = (float)Math.random();
                ElytraFly.mc.field_71439_g.field_184837_c = (float)Math.random();
            }
            if (this.mode.getValString().equalsIgnoreCase("Legit+")) {
                final EntityPlayerSP field_71439_g = ElytraFly.mc.field_71439_g;
                field_71439_g.field_70747_aH *= 1.01222f;
                ElytraFly.mc.field_71439_g.field_70143_R = 0.0f;
                final EntityPlayerSP field_71439_g2 = ElytraFly.mc.field_71439_g;
                field_71439_g2.field_70702_br += 0.1f;
                ElytraFly.mc.field_71439_g.field_70133_I = true;
                if (ElytraFly.mc.field_71439_g.field_70726_aT > 88.0f) {
                    ElytraFly.mc.field_71439_g.func_70024_g(0.1, 0.0, 0.1);
                }
                final double[] directionSpeedVanilla = Utils.directionSpeed(0.02);
                if (ElytraFly.mc.field_71439_g.field_70726_aT < 0.0f) {
                    ElytraFly.mc.field_71439_g.func_70024_g(directionSpeedVanilla[0], -0.001, directionSpeedVanilla[1]);
                }
            }
            if (this.mode.getValString().equalsIgnoreCase("Rocket")) {
                ElytraFly.mc.field_71439_g.func_70097_a(DamageSource.field_191552_t, 10.0f);
                if (this.wasBoosted != null) {
                    float speedScaled = (float)(this.speed.getValDouble() * 0.05000000074505806) * 10.0f;
                    if (this.Fireworktimer1.isDelay((long)(this.TicksAfter.getValDouble() * 100.0)) && this.AfterBurner.getValBoolean()) {
                        this.wasBoosted = null;
                        speedScaled *= (float)this.SpeedMulti.getValDouble();
                        this.Fireworktimer1.setLastMS();
                    }
                    else if (this.Lockmove.getValBoolean()) {
                        this.freezePlayer((EntityPlayer)ElytraFly.mc.field_71439_g);
                    }
                    final double[] directionSpeedVanilla2 = Utils.directionSpeed(speedScaled);
                    if (ElytraFly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || ElytraFly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                        final EntityPlayerSP field_71439_g3 = ElytraFly.mc.field_71439_g;
                        field_71439_g3.field_70159_w += directionSpeedVanilla2[0];
                        final EntityPlayerSP field_71439_g4 = ElytraFly.mc.field_71439_g;
                        field_71439_g4.field_70179_y += directionSpeedVanilla2[1];
                    }
                }
                else {
                    if (this.AutoRocket.getValBoolean() && this.Fireworktimer2.isDelay((long)this.RocketTicks.getValDouble() * 100L)) {
                        if (ElytraFly.mc.field_71439_g.func_184614_ca().field_151002_e == Items.field_151152_bP || ElytraFly.mc.field_71439_g.func_184592_cb().field_151002_e == Items.field_151152_bP) {
                            ElytraFly.mc.func_147121_ag();
                        }
                        this.Fireworktimer2.setLastMS();
                    }
                    if (this.AfterBurner.getValBoolean()) {
                        for (final Entity entity : ElytraFly.mc.field_71441_e.field_72996_f) {
                            if (entity instanceof EntityFireworkRocket) {
                                final EntityFireworkRocket e = (EntityFireworkRocket)entity;
                                if (e.field_191513_e == ElytraFly.mc.field_71439_g && !e.func_184202_aL()) {
                                    e.func_184195_f(true);
                                    this.wasBoosted = e;
                                    this.Fireworktimer1.setLastMS();
                                    break;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
            if (this.mode.getValString().equalsIgnoreCase("Boost")) {
                final float pitch = ElytraFly.mc.field_71439_g.field_70125_A;
                final float speedScaled2 = (float)(this.speed.getValDouble() * 0.05000000074505806);
                final double[] directionSpeedVanilla3 = Utils.directionSpeed(speedScaled2);
                if (ElytraFly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || ElytraFly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                    final EntityPlayerSP field_71439_g5 = ElytraFly.mc.field_71439_g;
                    field_71439_g5.field_70159_w += directionSpeedVanilla3[0];
                    final EntityPlayerSP field_71439_g6 = ElytraFly.mc.field_71439_g;
                    field_71439_g6.field_70179_y += directionSpeedVanilla3[1];
                }
                final EntityPlayerSP field_71439_g7 = ElytraFly.mc.field_71439_g;
                field_71439_g7.field_70181_x += (this.Yboost.getValBoolean() ? (Math.sin(Math.toRadians(pitch)) * 0.05) : 0.0);
                if (this.Flatboost.getValBoolean()) {
                    ElytraFly.mc.field_71439_g.field_70181_x = 0.03 * Math.cos(3.141592653589793 * Math.abs(ElytraFly.mc.field_71439_g.field_70125_A) / 90.0 + 3.141592653589793) + 0.05 * (this.Fallspeedboost.getValDouble() / 100.0);
                }
                if (ElytraFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    final EntityPlayerSP field_71439_g8 = ElytraFly.mc.field_71439_g;
                    field_71439_g8.field_70181_x += 0.05;
                }
                if (ElytraFly.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    final EntityPlayerSP field_71439_g9 = ElytraFly.mc.field_71439_g;
                    field_71439_g9.field_70181_x -= 0.05;
                }
            }
            if (this.mode.getValString().equalsIgnoreCase("Try1") && ((ElytraFly.mc.field_71439_g.field_70181_x < 0.0 && ElytraFly.mc.field_71439_g.field_70160_al) || ElytraFly.mc.field_71439_g.field_70122_E)) {
                ElytraFly.mc.field_71439_g.field_70181_x = -0.125;
                final EntityPlayerSP field_71439_g10 = ElytraFly.mc.field_71439_g;
                field_71439_g10.field_70747_aH *= (float)(1.0122699737548828 + this.Speedme.getValDouble());
                ElytraFly.mc.field_71439_g.field_70145_X = true;
                ElytraFly.mc.field_71439_g.field_70143_R = 0.0f;
                ElytraFly.mc.field_71439_g.field_70122_E = true;
                final EntityPlayerSP field_71439_g11 = ElytraFly.mc.field_71439_g;
                field_71439_g11.field_70702_br += (float)(0.800000011920929 + this.Speedme.getValDouble());
                final EntityPlayerSP field_71439_g12 = ElytraFly.mc.field_71439_g;
                field_71439_g12.field_70747_aH += (float)(0.20000000298023224 + this.Speedme.getValDouble());
                ElytraFly.mc.field_71439_g.field_70133_I = true;
                if (ElytraFly.mc.field_71439_g.field_70173_aa % 8 == 0 && ElytraFly.mc.field_71439_g.field_70163_u <= 240.0) {
                    ElytraFly.mc.field_71439_g.field_70181_x = 0.019999999552965164;
                }
            }
            if (this.mode.getValString().equalsIgnoreCase("Control")) {
                this.runNoKick();
                final double[] directionSpeedPacket = Utils.directionSpeed(this.speed.getValDouble() / Math.max(this.Lazyticks, 1.0));
                if (ElytraFly.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f && ElytraFly.mc.field_71439_g.field_71158_b.field_192832_b == 0.0f) {
                    this.Lazyticks = 0.0;
                    this.freezePlayer((EntityPlayer)ElytraFly.mc.field_71439_g);
                    ElytraFly.mc.field_71439_g.field_70181_x = 0.03 * Math.cos(3.141592653589793 * Math.abs(ElytraFly.mc.field_71439_g.field_70125_A) / 90.0 + 3.141592653589793) + 0.05 * (this.Fallspeed.getValDouble() / 100.0);
                    if (ElytraFly.mc.field_71439_g.field_71158_b.field_78899_d) {
                        ElytraFly.mc.field_71439_g.field_70181_x = -this.speed.getValDouble();
                    }
                }
                else if (ElytraFly.mc.field_71439_g.field_70125_A > this.StopPitch.getValDouble() - 0.01) {
                    if (this.BuildupTicks.getValDouble() > 0.0) {
                        if (this.Lazyticks == 0.0) {
                            this.Lazyticks = this.BuildupTicks.getValDouble();
                        }
                        if (this.Lazyticks > 1.0) {
                            this.Lazyticks /= 1.03;
                        }
                    }
                    this.freezePlayer((EntityPlayer)ElytraFly.mc.field_71439_g);
                    ElytraFly.mc.field_71439_g.field_70181_x = 0.03 * Math.cos(3.141592653589793 * Math.abs(ElytraFly.mc.field_71439_g.field_70125_A) / 90.0 + 3.141592653589793) + 0.05 * (this.Fallspeed.getValDouble() / 100.0);
                    if (ElytraFly.mc.field_71439_g.field_71158_b.field_78899_d) {
                        ElytraFly.mc.field_71439_g.field_70181_x = -this.speed.getValDouble();
                    }
                    if (ElytraFly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || ElytraFly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                        ElytraFly.mc.field_71439_g.field_70159_w = directionSpeedPacket[0];
                        ElytraFly.mc.field_71439_g.field_70179_y = directionSpeedPacket[1];
                    }
                }
                else if (ElytraFly.mc.field_71439_g.field_70163_u < ElytraFly.mc.field_71439_g.field_70137_T && this.timer.isDelay(100L)) {
                    ElytraFly.mc.field_71439_g.field_70125_A = 0.0f;
                    this.timer.setLastMS();
                }
            }
            if (this.mode.getValString().equalsIgnoreCase("Mouse")) {
                final double[] dir = Utils.directionSpeed(this.speed.getValDouble());
                if (Module.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f && Module.mc.field_71439_g.field_71158_b.field_192832_b == 0.0f) {
                    Module.mc.field_71439_g.field_70159_w = 0.0;
                    Module.mc.field_71439_g.field_70179_y = 0.0;
                }
                else {
                    Module.mc.field_71439_g.field_70159_w = dir[0];
                    Module.mc.field_71439_g.field_70179_y = dir[1];
                    final EntityPlayerSP field_71439_g13 = ElytraFly.mc.field_71439_g;
                    field_71439_g13.field_70159_w -= Module.mc.field_71439_g.field_70159_w * (Math.abs(Module.mc.field_71439_g.field_70125_A) + 90.0f) / 90.0 - Module.mc.field_71439_g.field_70159_w;
                    final EntityPlayerSP field_71439_g14 = ElytraFly.mc.field_71439_g;
                    field_71439_g14.field_70179_y -= Module.mc.field_71439_g.field_70179_y * (Math.abs(Module.mc.field_71439_g.field_70125_A) + 90.0f) / 90.0 - Module.mc.field_71439_g.field_70179_y;
                }
                float y = 0.0f;
                if (ElytraFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    y = (float)this.upspeed.getValDouble();
                }
                double Ymove = y + -this.degToRad(Module.mc.field_71439_g.field_70125_A) * Module.mc.field_71439_g.field_71158_b.field_192832_b;
                if (this.upspeed.getValDouble() == 0.0 && Ymove > 0.0) {
                    Ymove = 0.0;
                }
                Module.mc.field_71439_g.field_70181_x = Ymove;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("BYPASS")) {
            if (ElytraFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                ElytraFly.mc.field_71439_g.field_70181_x = 0.019999999552965164;
            }
            if (ElytraFly.mc.field_71474_y.field_74311_E.func_151470_d()) {
                ElytraFly.mc.field_71439_g.field_70181_x = -0.20000000298023224;
            }
            if (ElytraFly.mc.field_71439_g.field_70173_aa % 8 == 0 && ElytraFly.mc.field_71439_g.field_70163_u <= 240.0) {
                ElytraFly.mc.field_71439_g.field_70181_x = 0.019999999552965164;
            }
            ElytraFly.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            ElytraFly.mc.field_71439_g.field_71075_bZ.func_75092_a(0.025f);
            final double[] directionSpeedBypass = Utils.directionSpeed(0.5199999809265137);
            if (ElytraFly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || ElytraFly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                ElytraFly.mc.field_71439_g.field_70159_w = directionSpeedBypass[0];
                ElytraFly.mc.field_71439_g.field_70179_y = directionSpeedBypass[1];
            }
            else {
                ElytraFly.mc.field_71439_g.field_70159_w = 0.0;
                ElytraFly.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        if (this.infiniteDurability.getValBoolean()) {
            ElytraFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFly.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
            ElytraFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFly.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    public double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    private void runNoKick() {
        if (this.noKick.getValBoolean() && !ElytraFly.mc.field_71439_g.func_184613_cA() && ElytraFly.mc.field_71439_g.field_70173_aa % 4 == 0) {
            ElytraFly.mc.field_71439_g.field_70181_x = -0.03999999910593033;
        }
    }
    
    private void freezePlayer(final EntityPlayer player) {
        player.field_70159_w = 0.0;
        player.field_70181_x = 0.0;
        player.field_70179_y = 0.0;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketPlayer.PositionRotation && ElytraFly.mc.field_71439_g.field_70125_A > -this.StopPitch.getValDouble() && this.mode.getValString().equalsIgnoreCase("Control") && ElytraFly.mc.field_71439_g.func_184613_cA() && this.Flatfly.getValBoolean()) {
            final CPacketPlayer.PositionRotation rotation = (CPacketPlayer.PositionRotation)packet;
            Objects.requireNonNull(ElytraFly.mc.func_147114_u()).func_147297_a((Packet)new CPacketPlayer.Position(rotation.field_149479_a, rotation.field_149477_b, rotation.field_149478_c, rotation.field_149474_g));
            return false;
        }
        return true;
    }
    
    @Override
    public void onDisable() {
        if (ElytraFly.mc.field_71439_g != null) {
            ElytraFly.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        }
    }
}
