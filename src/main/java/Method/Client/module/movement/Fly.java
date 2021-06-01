package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Method.Client.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

public class Fly extends Module
{
    int clock;
    private boolean aac;
    private double aad;
    Setting mode;
    Setting speed;
    Setting speed2;
    Setting speed3;
    
    public Fly() {
        super("Fly", 0, Category.MOVEMENT, "Fly me to the skys");
        this.clock = 0;
        this.mode = Main.setmgr.add(new Setting("Fly Mode", this, "Vanilla", new String[] { "Vanilla", "Motion", "Tp", "Servers", "NPacket", "BPacket", "CubeCraft", "Old AAC", "Rewinside", "Clicktp", "AAC" }));
        this.speed = Main.setmgr.add(new Setting("Speed", this, 1.0, 0.5, 8.0, false, this.mode, "Vanilla", 1));
        this.speed2 = Main.setmgr.add(new Setting("NSpeed", this, 1.5, 0.5, 5.0, false, this.mode, "Tp", 1));
        this.speed3 = Main.setmgr.add(new Setting("Speed3", this, 3.0, 0.5, 5.0, false, this.mode, "Motion", 1));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("AAC")) {
            Fly.mc.field_71439_g.func_70031_b(false);
            if (Fly.mc.field_71439_g.field_70143_R >= 4.0f && !this.aac) {
                this.aac = true;
                this.aad = Fly.mc.field_71439_g.field_70163_u + 3.0;
                Fly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Fly.mc.field_71439_g.field_70165_t, Fly.mc.field_71439_g.field_70163_u, Fly.mc.field_71439_g.field_70161_v, true));
            }
            Fly.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            if (this.aac) {
                if (Fly.mc.field_71439_g.field_70122_E) {
                    this.aac = false;
                }
                if (Fly.mc.field_71439_g.field_70163_u < this.aad) {
                    Fly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Fly.mc.field_71439_g.field_70165_t, Fly.mc.field_71439_g.field_70163_u, Fly.mc.field_71439_g.field_70161_v, true));
                    if (Fly.mc.field_71474_y.field_74311_E.field_74513_e) {
                        this.aad -= 2.0;
                    }
                    else if (Fly.mc.field_71474_y.field_74311_E.field_74513_e && Fly.mc.field_71439_g.field_70163_u < this.aad + 0.8) {
                        this.aad += 2.0;
                    }
                    else {
                        Fly.mc.field_71439_g.field_70181_x = 0.7;
                        this.utils(0.8f);
                    }
                }
            }
            else {
                Fly.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Clicktp") && Fly.mc.field_71474_y.field_74312_F.field_74513_e) {
            Fly.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            final double yaw = Fly.mc.field_71439_g.field_70177_z;
            final float increment = 8.5f;
            Fly.mc.field_71439_g.func_70107_b(Fly.mc.field_71439_g.field_70165_t + Math.sin(Math.toRadians(-yaw)) * increment, Fly.mc.field_71439_g.field_70163_u, Fly.mc.field_71439_g.field_70161_v + Math.cos(Math.toRadians(-yaw)) * increment);
        }
        if (this.mode.getValString().equalsIgnoreCase("CubeCraft") && Fly.mc.field_71439_g != null && Fly.mc.field_71441_e != null) {
            final double teleportV = 1.0;
            final double x = this.getPosForSetPosX(teleportV);
            final double z = this.getPosForSetPosZ(teleportV);
            Fly.mc.field_71439_g.field_70181_x = -0.25;
            if (Fly.mc.field_71439_g.field_70143_R >= 0.8f) {
                Fly.mc.field_71439_g.func_70107_b(Fly.mc.field_71439_g.field_70165_t + x, Fly.mc.field_71439_g.field_70163_u + (Fly.mc.field_71439_g.field_70143_R - 0.15), Fly.mc.field_71439_g.field_70161_v + z);
                Fly.mc.field_71439_g.field_70143_R = 0.0f;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Old AAC") && Fly.mc.field_71441_e != null && Fly.mc.field_71439_g != null && Fly.mc.field_71439_g.field_70143_R > 0.0f) {
            Fly.mc.field_71439_g.field_70181_x = ((Fly.mc.field_71439_g.field_70173_aa % 2 == 0) ? 0.1 : 0.0);
        }
        if (this.mode.getValString().equalsIgnoreCase("Rewinside") && Fly.mc.field_71439_g != null && Fly.mc.field_71441_e != null) {
            Fly.mc.field_71474_y.field_74370_x.field_74513_e = false;
            Fly.mc.field_71474_y.field_74366_z.field_74513_e = false;
            Fly.mc.field_71474_y.field_74368_y.field_74513_e = false;
            Fly.mc.field_71474_y.field_74314_A.field_74513_e = false;
            Fly.mc.field_71474_y.field_151444_V.field_74513_e = false;
            Fly.mc.field_71439_g.func_70031_b(false);
            Fly.mc.field_71439_g.field_70181_x = 0.0;
            Fly.mc.field_71439_g.func_70107_b(Fly.mc.field_71439_g.field_70165_t, Fly.mc.field_71439_g.field_70163_u + 1.0E-10, Fly.mc.field_71439_g.field_70161_v);
            Fly.mc.field_71439_g.field_70122_E = true;
            if (Fly.mc.field_71439_g.field_70173_aa % 3 == 0) {
                Fly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Fly.mc.field_71439_g.field_70165_t + Fly.mc.field_71439_g.field_70159_w, Fly.mc.field_71439_g.field_70163_u - 1.0E-10, Fly.mc.field_71439_g.field_70161_v + Fly.mc.field_71439_g.field_70179_y, Fly.mc.field_71439_g.field_70177_z, Fly.mc.field_71439_g.field_70125_A, true));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Tp")) {
            final EntityPlayerSP player = Fly.mc.field_71439_g;
            player.field_71075_bZ.field_75100_b = false;
            final float speedScaled = (float)(this.speed2.getValDouble() * 0.5);
            final double[] directionSpeedVanilla = Utils.directionSpeed(speedScaled);
            if (Fly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || Fly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                player.func_70107_b(player.field_70165_t + directionSpeedVanilla[0], player.field_70163_u, player.field_70161_v + directionSpeedVanilla[1]);
            }
            player.field_70159_w = 0.0;
            player.field_70179_y = 0.0;
            player.func_70016_h(player.field_70181_x = 0.0, 0.0, 0.0);
            if (Fly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                player.func_70107_b(player.field_70165_t, player.field_70163_u + this.speed2.getValDouble(), player.field_70161_v);
                player.field_70181_x = 0.0;
            }
            if (Fly.mc.field_71474_y.field_74311_E.func_151470_d() && !player.field_70122_E) {
                player.func_70107_b(player.field_70165_t, player.field_70163_u - this.speed2.getValDouble(), player.field_70161_v);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Motion")) {
            final float speedScaled2 = (float)(this.speed3.getValDouble() * 0.6000000238418579);
            final double[] directionSpeedVanilla2 = Utils.directionSpeed(speedScaled2);
            if (Fly.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || Fly.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
                Fly.mc.field_71439_g.field_70159_w = directionSpeedVanilla2[0];
                Fly.mc.field_71439_g.field_70179_y = directionSpeedVanilla2[1];
            }
            Fly.mc.field_71439_g.field_70181_x = 0.0;
            if (Fly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                Fly.mc.field_71439_g.field_70181_x = this.speed3.getValDouble();
            }
            if (Fly.mc.field_71474_y.field_74311_E.func_151470_d() && !Fly.mc.field_71439_g.field_70122_E) {
                Fly.mc.field_71439_g.field_70181_x = -this.speed3.getValDouble();
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            final EntityPlayerSP player = Fly.mc.field_71439_g;
            player.field_71075_bZ.field_75100_b = false;
            player.field_70159_w = 0.0;
            player.field_70181_x = 0.0;
            player.field_70179_y = 0.0;
            player.field_70747_aH = (float)this.speed.getValDouble();
            if (Fly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                final EntityPlayerSP entityPlayerSP = player;
                entityPlayerSP.field_70181_x += this.speed.getValDouble();
            }
            if (Fly.mc.field_71474_y.field_74311_E.func_151470_d()) {
                final EntityPlayerSP entityPlayerSP2 = player;
                entityPlayerSP2.field_70181_x -= this.speed.getValDouble();
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Servers")) {
            Fly.mc.field_71439_g.field_70181_x = 0.0;
            if (Fly.mc.field_71439_g.field_70173_aa % 3 == 0) {
                final double n = Fly.mc.field_71439_g.field_70163_u - 1.0E-10;
            }
            final double y1 = Fly.mc.field_71439_g.field_70163_u + 1.0E-10;
            Fly.mc.field_71439_g.func_70107_b(Fly.mc.field_71439_g.field_70165_t, y1, Fly.mc.field_71439_g.field_70161_v);
        }
        if (this.mode.getValString().equalsIgnoreCase("NPacket")) {
            float forward = 0.0f;
            float strafe = 0.0f;
            final EntityPlayerSP player2 = Fly.mc.field_71439_g;
            final float var5 = MathHelper.func_76126_a(Fly.mc.field_71439_g.field_70177_z * 3.1415927f / 180.0f);
            final float var6 = MathHelper.func_76134_b(Fly.mc.field_71439_g.field_70177_z * 3.1415927f / 180.0f);
            if (Fly.mc.field_71474_y.field_74351_w.field_74513_e) {
                forward += 0.1f;
            }
            if (Fly.mc.field_71474_y.field_74368_y.field_74513_e) {
                forward -= 0.1f;
            }
            if (Fly.mc.field_71474_y.field_74370_x.field_74513_e) {
                strafe += 0.01f;
            }
            if (Fly.mc.field_71474_y.field_74366_z.field_74513_e) {
                strafe -= 0.01f;
            }
            if (!Fly.mc.field_71439_g.field_70128_L) {
                this.Movement(forward, strafe, player2, var5, var6);
                this.SendPacket();
            }
            Fly.mc.field_71439_g.field_70181_x = 0.0;
            ++this.clock;
            if (this.clock >= 12) {
                this.clock = 0;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("BPacket")) {
            float forward = 0.0f;
            float strafe = 0.0f;
            final double speed = 0.2;
            final EntityPlayerSP player3 = Fly.mc.field_71439_g;
            final float var7 = MathHelper.func_76126_a(Fly.mc.field_71439_g.field_70177_z * 3.1415927f / 180.0f);
            final float var8 = MathHelper.func_76134_b(Fly.mc.field_71439_g.field_70177_z * 3.1415927f / 180.0f);
            if (Fly.mc.field_71474_y.field_74351_w.field_74513_e) {
                forward += 0.1f;
            }
            if (Fly.mc.field_71474_y.field_74368_y.field_74513_e) {
                forward -= 0.1f;
            }
            if (Fly.mc.field_71474_y.field_74370_x.field_74513_e) {
                strafe += 0.01f;
            }
            if (Fly.mc.field_71474_y.field_74366_z.field_74513_e) {
                strafe -= 0.01f;
            }
            if (!Fly.mc.field_71439_g.field_70128_L) {
                this.Movement(forward, strafe, player3, var7, var8);
            }
            Fly.mc.field_71439_g.field_70181_x = 0.0;
            ++this.clock;
            if (this.clock >= 2) {
                this.SendPacket();
                this.clock = 0;
            }
        }
        super.onClientTick(event);
    }
    
    public void utils(final float speed) {
        Fly.mc.field_71439_g.field_70159_w = -(Math.sin(this.aan()) * speed);
        Fly.mc.field_71439_g.field_70179_y = Math.cos(this.aan()) * speed;
    }
    
    public float aan() {
        float var1 = Fly.mc.field_71439_g.field_70177_z;
        if (Fly.mc.field_71439_g.field_191988_bg < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Fly.mc.field_71439_g.field_191988_bg < 0.0f) {
            forward = -0.5f;
        }
        else if (Fly.mc.field_71439_g.field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Fly.mc.field_71439_g.field_70702_br > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Fly.mc.field_71439_g.field_70702_br < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
    
    private void Movement(final float forward, final float strafe, final EntityPlayerSP player, final float var5, final float var6) {
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        final double speed = 2.7999100260353087;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        this.Runmove(player);
        this.Runmove(player);
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        final double motionX = (strafe * var6 - forward * var5) * speed;
        final double motionZ = (forward * var6 + strafe * var5) * speed;
        Fly.mc.field_71439_g.field_70159_w = motionX;
        Fly.mc.field_71439_g.field_70179_y = motionZ;
        if (!Fly.mc.field_71474_y.field_74314_A.field_74513_e) {
            Fly.mc.field_71439_g.func_70107_b(Fly.mc.field_71439_g.field_70165_t, Fly.mc.field_71439_g.field_70163_u - 0.03948584, Fly.mc.field_71439_g.field_70161_v);
        }
    }
    
    private void Runmove(final EntityPlayerSP player) {
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
        Fly.mc.field_71439_g.field_70179_y = player.field_70161_v * 5.01E-8;
        Fly.mc.field_71439_g.field_70159_w = player.field_70165_t * 5.01E-8;
    }
    
    private void SendPacket() {
        Fly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Fly.mc.field_71439_g.field_70165_t + Fly.mc.field_71439_g.field_70159_w, Fly.mc.field_71439_g.field_70163_u + (Fly.mc.field_71474_y.field_74314_A.func_151470_d() ? 0.0621 : 0.0) - (Fly.mc.field_71474_y.field_74311_E.func_151470_d() ? 0.0621 : 0.0), Fly.mc.field_71439_g.field_70161_v + Fly.mc.field_71439_g.field_70179_y, Fly.mc.field_71439_g.field_70177_z, Fly.mc.field_71439_g.field_70125_A, false));
        Fly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Fly.mc.field_71439_g.field_70165_t + Fly.mc.field_71439_g.field_70159_w, Fly.mc.field_71439_g.field_70163_u - 999.0, Fly.mc.field_71439_g.field_70161_v + Fly.mc.field_71439_g.field_70179_y, Fly.mc.field_71439_g.field_70177_z, Fly.mc.field_71439_g.field_70125_A, true));
    }
    
    public double getPosForSetPosX(final double value) {
        final double yaw = Math.toRadians(Fly.mc.field_71439_g.field_70177_z);
        final double x = -Math.sin(yaw) * value;
        return x;
    }
    
    public double getPosForSetPosZ(final double value) {
        final double yaw = Math.toRadians(Fly.mc.field_71439_g.field_70177_z);
        final double z = Math.cos(yaw) * value;
        return z;
    }
    
    @Override
    public void onDisable() {
        Fly.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        super.onDisable();
    }
}
