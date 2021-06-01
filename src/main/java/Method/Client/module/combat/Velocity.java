package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;

public class Velocity extends Module
{
    Setting mode;
    Setting XMult;
    Setting YMult;
    Setting ZMult;
    Setting onPacket;
    Setting CancelPacket;
    Setting Super;
    Setting Pushspeed;
    Setting Pushstart;
    private double motionX;
    private double motionZ;
    private final TimerUtils timer;
    
    public Velocity() {
        super("Velocity", 0, Category.COMBAT, "Velocity");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Simple", new String[] { "Simple", "AAC", "Fast", "YPort", "AAC4Flag", "Pull", "Airmove", "HurtPacket" }));
        this.XMult = Main.setmgr.add(new Setting("XMultipl", this, 0.0, 0.0, 10.0, false, this.mode, "Simple", 1));
        this.YMult = Main.setmgr.add(new Setting("YMultipl", this, 0.0, 0.0, 10.0, false, this.mode, "Simple", 2));
        this.ZMult = Main.setmgr.add(new Setting("ZMultipl", this, 0.0, 0.0, 10.0, false, this.mode, "Simple", 3));
        this.onPacket = Main.setmgr.add(new Setting("Only Packet", this, true, this.mode, "Simple", 4));
        this.CancelPacket = Main.setmgr.add(new Setting("CancelPacket", this, true, this.mode, "Simple", 5));
        this.Super = Main.setmgr.add(new Setting("Super", this, true, this.mode, "Pull", 1));
        this.Pushspeed = Main.setmgr.add(new Setting("Pushspeed", this, 0.25, 1.0E-4, 0.4, false, this.mode, "Airmove", 2));
        this.Pushstart = Main.setmgr.add(new Setting("Pushstart", this, 8.0, 2.0, 9.0, false, this.mode, "Airmove", 3));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("AAC")) {
            if (Velocity.mc.field_71439_g.field_70737_aN > 0 && Velocity.mc.field_71439_g.field_70737_aN <= 7) {
                final EntityPlayerSP field_71439_g = Velocity.mc.field_71439_g;
                field_71439_g.field_70159_w *= 0.5;
                final EntityPlayerSP field_71439_g2 = Velocity.mc.field_71439_g;
                field_71439_g2.field_70179_y *= 0.5;
            }
            if (Velocity.mc.field_71439_g.field_70737_aN > 0 && Velocity.mc.field_71439_g.field_70737_aN < 6) {
                Velocity.mc.field_71439_g.field_70159_w = 0.0;
                Velocity.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Fast") && Velocity.mc.field_71439_g.field_70737_aN < 9 && !Velocity.mc.field_71439_g.field_70122_E) {
            double yaw = Velocity.mc.field_71439_g.field_70759_as;
            yaw = Math.toRadians(yaw);
            final double dX = -Math.sin(yaw) * 0.08;
            final double dZ = Math.cos(yaw) * 0.08;
            if (Velocity.mc.field_71439_g.func_110143_aJ() >= 6.0f) {
                Velocity.mc.field_71439_g.field_70159_w = dX;
                Velocity.mc.field_71439_g.field_70179_y = dZ;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Simple") && !this.onPacket.getValBoolean() && Velocity.mc.field_71439_g.field_70737_aN > 0 && Velocity.mc.field_71439_g.field_70143_R < 3.0f && this.timer.isDelay(100L)) {
            if (Utils.isMovinginput()) {
                final EntityPlayerSP field_71439_g3 = Velocity.mc.field_71439_g;
                field_71439_g3.field_70159_w *= this.XMult.getValDouble();
                final EntityPlayerSP field_71439_g4 = Velocity.mc.field_71439_g;
                field_71439_g4.field_70179_y *= this.ZMult.getValDouble();
            }
            else {
                final EntityPlayerSP field_71439_g5 = Velocity.mc.field_71439_g;
                field_71439_g5.field_70159_w *= this.XMult.getValDouble() + 0.2;
                final EntityPlayerSP field_71439_g6 = Velocity.mc.field_71439_g;
                field_71439_g6.field_70179_y *= this.ZMult.getValDouble() + 0.2;
            }
            final EntityPlayerSP field_71439_g7 = Velocity.mc.field_71439_g;
            field_71439_g7.field_70181_x -= this.YMult.getValDouble();
            final EntityPlayerSP field_71439_g8 = Velocity.mc.field_71439_g;
            field_71439_g8.field_70181_x += this.YMult.getValDouble();
            this.timer.setLastMS();
        }
        if (this.mode.getValString().equalsIgnoreCase("AAC4Flag") && (Velocity.mc.field_71439_g.field_70737_aN == 3 || Velocity.mc.field_71439_g.field_70737_aN == 4)) {
            final double[] directionSpeedVanilla = Utils.directionSpeed(0.05);
            Velocity.mc.field_71439_g.field_70159_w = directionSpeedVanilla[0];
            Velocity.mc.field_71439_g.field_70179_y = directionSpeedVanilla[1];
        }
        if (this.mode.getValString().equalsIgnoreCase("Pull")) {
            if (Velocity.mc.field_71439_g.field_70737_aN == 9) {
                this.motionX = Velocity.mc.field_71439_g.field_70159_w;
                this.motionZ = Velocity.mc.field_71439_g.field_70179_y;
            }
            if (this.Super.getValBoolean()) {
                if (Velocity.mc.field_71439_g.field_70737_aN == 8) {
                    Velocity.mc.field_71439_g.field_70159_w = -this.motionX * 0.45;
                    Velocity.mc.field_71439_g.field_70179_y = -this.motionZ * 0.45;
                }
            }
            else if (Velocity.mc.field_71439_g.field_70737_aN == 4) {
                Velocity.mc.field_71439_g.field_70159_w = -this.motionX * 0.6;
                Velocity.mc.field_71439_g.field_70179_y = -this.motionZ * 0.6;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Airmove")) {
            if (Velocity.mc.field_71439_g.field_70737_aN == 9) {
                this.motionX = Velocity.mc.field_71439_g.field_70159_w;
                this.motionZ = Velocity.mc.field_71439_g.field_70179_y;
            }
            else if (Velocity.mc.field_71439_g.field_70737_aN == this.Pushstart.getValDouble() - 1.0) {
                final EntityPlayerSP field_71439_g9 = Velocity.mc.field_71439_g;
                field_71439_g9.field_70159_w *= -this.Pushspeed.getValDouble();
                final EntityPlayerSP field_71439_g10 = Velocity.mc.field_71439_g;
                field_71439_g10.field_70179_y *= -this.Pushspeed.getValDouble();
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("HurtPacket") && Velocity.mc.field_71439_g.field_70172_ad > 18) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Velocity.mc.field_71439_g.field_70165_t, Velocity.mc.field_71439_g.field_70163_u - 12.0, Velocity.mc.field_71439_g.field_70161_v, false));
        }
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("Simple") && this.onPacket.getValBoolean()) {
            if (this.CancelPacket.getValBoolean()) {
                if (packet instanceof SPacketEntityVelocity) {
                    final SPacketEntityVelocity packet2 = (SPacketEntityVelocity)packet;
                    return packet2.func_149412_c() != Velocity.mc.field_71439_g.func_145782_y();
                }
                return !(packet instanceof SPacketExplosion) || this.YMult.getValDouble() != 0.0 || this.XMult.getValDouble() != 0.0 || this.ZMult.getValDouble() != 0.0;
            }
            else if (this.timer.isDelay(100L)) {
                if (packet instanceof SPacketEntityVelocity) {
                    final SPacketEntityVelocity sPacketEntityVelocity;
                    final SPacketEntityVelocity packet2 = sPacketEntityVelocity = (SPacketEntityVelocity)packet;
                    sPacketEntityVelocity.field_149416_c *= (int)this.YMult.getValDouble();
                    final SPacketEntityVelocity sPacketEntityVelocity2 = packet2;
                    sPacketEntityVelocity2.field_149415_b *= (int)this.XMult.getValDouble();
                    final SPacketEntityVelocity sPacketEntityVelocity3 = packet2;
                    sPacketEntityVelocity3.field_149414_d *= (int)this.ZMult.getValDouble();
                }
                if (packet instanceof SPacketExplosion) {
                    final SPacketExplosion sPacketExplosion;
                    final SPacketExplosion packet3 = sPacketExplosion = (SPacketExplosion)packet;
                    sPacketExplosion.field_149153_g *= (float)this.YMult.getValDouble();
                    final SPacketExplosion sPacketExplosion2 = packet3;
                    sPacketExplosion2.field_149152_f *= (float)this.XMult.getValDouble();
                    final SPacketExplosion sPacketExplosion3 = packet3;
                    sPacketExplosion3.field_149159_h *= (float)this.ZMult.getValDouble();
                }
                this.timer.setLastMS();
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("YPort") && Velocity.mc.field_71439_g.field_70737_aN >= 8) {
            Velocity.mc.field_71439_g.func_70107_b(Velocity.mc.field_71439_g.field_70142_S, Velocity.mc.field_71439_g.field_70137_T + 2.0, Velocity.mc.field_71439_g.field_70136_U);
            final EntityPlayerSP field_71439_g = Velocity.mc.field_71439_g;
            field_71439_g.field_70181_x -= 0.3;
            final EntityPlayerSP field_71439_g2 = Velocity.mc.field_71439_g;
            field_71439_g2.field_70159_w *= 0.8;
            final EntityPlayerSP field_71439_g3 = Velocity.mc.field_71439_g;
            field_71439_g3.field_70179_y *= 0.8;
        }
        return true;
    }
}
