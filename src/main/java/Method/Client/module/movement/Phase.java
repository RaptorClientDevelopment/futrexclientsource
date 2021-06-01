package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Method.Client.utils.system.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.item.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class Phase extends Module
{
    Setting mode;
    Setting speed;
    Setting packets;
    private int resetNext;
    public static boolean Finalsep;
    public int vanillastage;
    
    public Phase() {
        super("Phase", 0, Category.MOVEMENT, "Phase through blocks");
        this.mode = Main.setmgr.add(new Setting("Phase Mode", this, "Noclip", new String[] { "Noclip", "Simple", "Destroy", "Glitch", "VClip", "NCPDEV", "HFC", "WinterLithe", "Sand", "Packet", "Skip", "Sneak", "Dpacket" }));
        this.speed = Main.setmgr.add(new Setting("speed", this, 10.0, 0.1, 2.0, false));
        this.packets = Main.setmgr.add(new Setting("packets", this, 5.0, 1.0, 10.0, true));
    }
    
    @Override
    public void onEnable() {
        Phase.Finalsep = false;
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70122_E));
        Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
        Phase.mc.field_71439_g.field_70145_X = true;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("Dpacket") && side == Connection.Side.OUT && Phase.mc.field_71439_g.field_70123_F && packet instanceof CPacketPlayer.Position) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u - 0.08, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70122_E));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u + 0.08, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70122_E));
            final double var2 = -Math.sin(Math.toRadians(Phase.mc.field_71439_g.field_70177_z));
            final double var3 = Math.cos(Math.toRadians(Phase.mc.field_71439_g.field_70177_z));
            final double var4 = Phase.mc.field_71439_g.field_71158_b.field_192832_b * var2 + Phase.mc.field_71439_g.field_71158_b.field_78902_a * var3;
            final double var5 = Phase.mc.field_71439_g.field_71158_b.field_192832_b * var3 - Phase.mc.field_71439_g.field_71158_b.field_78902_a * var2;
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + var4, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + var5, false));
            return false;
        }
        return !(packet instanceof CPacketPlayer) || packet instanceof CPacketPlayer.Position || !this.mode.getValString().equalsIgnoreCase("noclip");
    }
    
    @Override
    public void onDisable() {
        this.vanillastage = 0;
        Phase.Finalsep = false;
        Phase.mc.field_71439_g.field_70181_x = 0.0;
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70122_E));
        Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
        Phase.mc.field_71439_g.field_70145_X = false;
        Phase.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Simple")) {
            Phase.MC.field_71439_g.field_70145_X = true;
            Phase.MC.field_71439_g.field_70143_R = 0.0f;
            Phase.MC.field_71439_g.field_70122_E = false;
            Phase.MC.field_71439_g.field_70747_aH = 0.32f;
            if (Phase.MC.field_71474_y.field_74314_A.func_151470_d()) {
                final EntityPlayerSP field_71439_g = Phase.MC.field_71439_g;
                field_71439_g.field_70181_x += 0.3199999928474426;
            }
            if (Phase.MC.field_71474_y.field_74311_E.func_151470_d()) {
                final EntityPlayerSP field_71439_g2 = Phase.MC.field_71439_g;
                field_71439_g2.field_70181_x -= 0.3199999928474426;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Sneak")) {
            Phase.mc.field_71439_g.field_70143_R = 0.0f;
            Phase.mc.field_71439_g.field_70122_E = true;
            if (Phase.mc.field_71474_y.field_74314_A.func_151468_f()) {
                Phase.mc.field_71439_g.func_70016_h(Phase.mc.field_71439_g.field_70159_w, 0.1, Phase.mc.field_71439_g.field_70179_y);
            }
            else if (Phase.mc.field_71474_y.field_74311_E.func_151468_f()) {
                Phase.mc.field_71439_g.func_70024_g(0.0, -0.1, 0.0);
            }
            else {
                Phase.mc.field_71439_g.func_70016_h(Phase.mc.field_71439_g.field_70159_w, 0.0, Phase.mc.field_71439_g.field_70179_y);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("noclip")) {
            if (Phase.mc.field_71439_g != null) {
                Phase.mc.field_71439_g.field_70145_X = false;
            }
            if (Phase.mc.field_71441_e != null && event.getEntity() == Phase.mc.field_71439_g) {
                Phase.mc.field_71439_g.field_70145_X = true;
                Phase.mc.field_71439_g.field_70122_E = false;
                Phase.mc.field_71439_g.field_70143_R = 0.0f;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("SAND") && Phase.mc.field_71474_y.field_74314_A.func_151470_d() && Phase.mc.field_71439_g.func_184187_bx() != null && Phase.mc.field_71439_g.func_184187_bx() instanceof EntityBoat) {
            final EntityBoat boat = (EntityBoat)Phase.mc.field_71439_g.func_184187_bx();
            if (boat.field_70122_E) {
                boat.field_70181_x = 0.41999998688697815;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("PACKET")) {
            final Vec3d dir = direction(Phase.mc.field_71439_g.field_70177_z);
            if (Phase.mc.field_71439_g.field_70122_E && Phase.mc.field_71439_g.field_70123_F) {
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + dir.field_72450_a * 9.999999747378752E-6, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + dir.field_72449_c * 9.999999747378752E-5, Phase.mc.field_71439_g.field_70122_E));
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + dir.field_72450_a * 2.0, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + dir.field_72449_c * 2.0, Phase.mc.field_71439_g.field_70122_E));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("SKIP")) {
            final Vec3d dir = direction(Phase.mc.field_71439_g.field_70177_z);
            if (Phase.mc.field_71439_g.field_70122_E && Phase.mc.field_71439_g.field_70123_F) {
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70122_E));
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + dir.field_72450_a * 0.0010000000474974513, Phase.mc.field_71439_g.field_70163_u + 0.10000000149011612, Phase.mc.field_71439_g.field_70161_v + dir.field_72449_c * 0.0010000000474974513, Phase.mc.field_71439_g.field_70122_E));
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + dir.field_72450_a * 0.029999999329447746, 0.0, Phase.mc.field_71439_g.field_70161_v + dir.field_72449_c * 0.029999999329447746, Phase.mc.field_71439_g.field_70122_E));
                Phase.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + dir.field_72450_a * 0.05999999865889549, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + dir.field_72449_c * 0.05999999865889549, Phase.mc.field_71439_g.field_70122_E));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("NCPDEV")) {
            Phase.mc.field_71439_g.field_70145_X = true;
            Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u - 1.0E-5, Phase.mc.field_71439_g.field_70161_v);
            Phase.mc.field_71439_g.field_70181_x = -10.0;
        }
        if (this.mode.getValString().equalsIgnoreCase("Glitch")) {
            Phase.mc.field_71439_g.field_70145_X = true;
            final EnumFacing var4 = Phase.mc.field_71439_g.func_174811_aO();
            Phase.mc.field_71439_g.field_70181_x = 0.0;
            if (Phase.mc.field_71439_g.field_71158_b.field_78901_c) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u + 2.0, Phase.mc.field_71439_g.field_70161_v);
            }
            if (Phase.mc.field_71439_g.field_71158_b.field_78899_d) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u - 1.0E-5, Phase.mc.field_71439_g.field_70161_v);
                Phase.mc.field_71439_g.field_70181_x = -1000.0;
            }
            if (Utils.isMoving((Entity)Phase.mc.field_71439_g)) {
                if (var4.func_176610_l().equals(EnumFacing.NORTH.func_176610_l())) {
                    Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v - 0.001);
                    if (isInsideBlock() && Phase.mc.field_71439_g.field_70173_aa % 10 == 0) {
                        Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v - 0.2);
                        final double[] directionSpeedVanilla = Utils.directionSpeed(1.5);
                        Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla[0];
                        Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla[1];
                    }
                }
                if (var4.func_176610_l().equals(EnumFacing.SOUTH.func_176610_l())) {
                    Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + 0.001);
                    if (isInsideBlock() && Phase.mc.field_71439_g.field_70173_aa % 10 == 0) {
                        Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + 0.2);
                        final double[] directionSpeedVanilla = Utils.directionSpeed(1.5);
                        Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla[0];
                        Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla[1];
                    }
                }
                if (var4.func_176610_l().equals(EnumFacing.WEST.func_176610_l())) {
                    Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t - 0.001, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
                    if (isInsideBlock() && Phase.mc.field_71439_g.field_70173_aa % 10 == 0) {
                        Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t - 0.2, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
                        final double[] directionSpeedVanilla = Utils.directionSpeed(1.5);
                        Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla[0];
                        Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla[1];
                    }
                }
                if (var4.func_176610_l().equals(EnumFacing.EAST.func_176610_l())) {
                    Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t + 0.001, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
                    if (isInsideBlock() && Phase.mc.field_71439_g.field_70173_aa % 10 == 0) {
                        Phase.mc.field_71439_g.func_70634_a(Phase.mc.field_71439_g.field_70165_t + 0.2, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
                        final double[] directionSpeedVanilla = Utils.directionSpeed(1.5);
                        Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla[0];
                        Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla[1];
                    }
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Sand")) {
            final double var5 = Phase.mc.field_71439_g.field_70165_t;
            final double y = Phase.mc.field_71439_g.field_70163_u - 3.0;
            final double z = Phase.mc.field_71439_g.field_70161_v;
            if (this.vanillastage == 0) {
                for (int i = 0; i < 100; ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(var5, Phase.mc.field_71439_g.field_70163_u - 1.0001, z, Phase.mc.field_71439_g.field_70177_z, Phase.mc.field_71439_g.field_70125_A, Phase.mc.field_71439_g.field_70122_E));
                    Phase.mc.field_71439_g.func_70107_b(var5, Phase.mc.field_71439_g.field_70163_u - 1.0001, z);
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70177_z, Phase.mc.field_71439_g.field_70125_A, Phase.mc.field_71439_g.field_70122_E));
                }
                for (int i = 0; i < 10; ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(var5, y, z, Phase.mc.field_71439_g.field_70177_z, Phase.mc.field_71439_g.field_70125_A, Phase.mc.field_71439_g.field_70122_E));
                    Phase.mc.field_71439_g.func_70107_b(var5, y, z);
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, Phase.mc.field_71439_g.field_70177_z, Phase.mc.field_71439_g.field_70125_A, Phase.mc.field_71439_g.field_70122_E));
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(var5, y, z, Phase.mc.field_71439_g.field_70177_z, Phase.mc.field_71439_g.field_70125_A, Phase.mc.field_71439_g.field_70122_E));
                    Phase.mc.field_71439_g.func_70107_b(var5, y, z);
                }
                ++this.vanillastage;
            }
            if (this.vanillastage > 0) {
                Phase.mc.field_71439_g.field_70145_X = true;
                Phase.Finalsep = true;
            }
        }
        final double var5 = -this.speed.getValDouble();
        if (this.mode.getValString().equalsIgnoreCase("HFC")) {
            final double[] directionSpeedVanilla2 = Utils.directionSpeed(this.speed.getValDouble());
            Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla2[0];
            Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla2[1];
            final EnumFacing var4 = Phase.mc.field_71439_g.func_174811_aO();
            if (Utils.isMoving((Entity)Phase.mc.field_71439_g)) {
                if (!Phase.mc.field_71439_g.field_70122_E && !isInsideBlock() && Phase.mc.field_71439_g.func_70093_af()) {
                    Phase.mc.field_71439_g.field_70181_x = 0.0;
                }
                else {
                    Phase.mc.field_71439_g.field_70181_x = 100000.0;
                }
                if (Phase.mc.field_71439_g.field_70173_aa % 2 == 0) {
                    if (var4.func_176610_l().equals(EnumFacing.NORTH.func_176610_l())) {
                        Phase.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, 0.0, -3.0);
                    }
                    if (var4.func_176610_l().equals(EnumFacing.SOUTH.func_176610_l())) {
                        Phase.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, 0.0, 3.0);
                    }
                    if (var4.func_176610_l().equals(EnumFacing.WEST.func_176610_l())) {
                        Phase.mc.field_71439_g.func_174813_aQ().func_72317_d(-3.0, 0.0, 0.0);
                    }
                    if (var4.func_176610_l().equals(EnumFacing.EAST.func_176610_l())) {
                        Phase.mc.field_71439_g.func_174813_aQ().func_72317_d(3.0, 0.0, 0.0);
                    }
                    for (int j = 0; j < (int)this.packets.getValDouble(); ++j) {
                        if (var4.func_176610_l().equals(EnumFacing.NORTH.func_176610_l())) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v - 3.0, true));
                        }
                        if (var4.func_176610_l().equals(EnumFacing.SOUTH.func_176610_l())) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + 3.0, true));
                        }
                        if (var4.func_176610_l().equals(EnumFacing.WEST.func_176610_l())) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t - 3.0, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, true));
                        }
                        if (var4.func_176610_l().equals(EnumFacing.EAST.func_176610_l())) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.field_71439_g.field_70165_t + 3.0, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v, true));
                        }
                    }
                }
                else {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(5000.0, Phase.mc.field_71439_g.field_70163_u, 5000.0, true));
                }
            }
            Phase.mc.field_71439_g.field_70145_X = true;
        }
        else if (this.mode.getValString().equalsIgnoreCase("WinterLithe")) {
            if ((isInsideBlock() && Phase.mc.field_71474_y.field_74314_A.field_74513_e) || (!isInsideBlock() && Phase.mc.field_71439_g.func_70046_E() != null && Phase.mc.field_71439_g.func_70046_E().field_72337_e > Phase.mc.field_71439_g.func_70046_E().field_72338_b && Phase.mc.field_71439_g.func_70093_af() && Phase.mc.field_71439_g.field_70123_F)) {
                --this.resetNext;
                final double mx = Math.cos(Math.toRadians(Phase.mc.field_71439_g.field_70177_z + 90.0f));
                final double mz = Math.sin(Math.toRadians(Phase.mc.field_71439_g.field_70177_z + 90.0f));
                final double xOff = Phase.mc.field_71439_g.field_191988_bg * 1.2 * mx + Phase.mc.field_71439_g.field_191988_bg * 1.2 * mz;
                final double zOff = Phase.mc.field_71439_g.field_191988_bg * 1.2 * mz - Phase.mc.field_71439_g.field_191988_bg * 1.2 * mx;
                if (isInsideBlock()) {
                    this.resetNext = 1;
                }
                if (this.resetNext > 0) {
                    Phase.mc.field_71439_g.func_174813_aQ().func_72317_d(xOff, 0.0, zOff);
                }
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Vclip")) {
            if (Phase.mc.field_71474_y.field_74314_A.field_74513_e) {
                Phase.mc.field_71439_g.field_70145_X = true;
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u + this.speed.getValDouble(), Phase.mc.field_71439_g.field_70161_v);
                Phase.mc.field_71439_g.field_70181_x = this.speed.getValDouble();
            }
            if (Phase.mc.field_71474_y.field_74311_E.field_74513_e) {
                Phase.mc.field_71439_g.field_70145_X = true;
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u + var5, Phase.mc.field_71439_g.field_70161_v);
                Phase.mc.field_71439_g.field_70181_x = var5;
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("MotionY") && Utils.isMoving((Entity)Phase.mc.field_71439_g)) {
            final EnumFacing var4 = Phase.mc.field_71439_g.func_174811_aO();
            if (var4.func_176610_l().equals(EnumFacing.NORTH.func_176610_l())) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v - 3.0);
            }
            if (var4.func_176610_l().equals(EnumFacing.SOUTH.func_176610_l())) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + 3.0);
            }
            if (var4.func_176610_l().equals(EnumFacing.WEST.func_176610_l())) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t - 3.0, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
            }
            if (var4.func_176610_l().equals(EnumFacing.EAST.func_176610_l())) {
                Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t + 3.0, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
            }
            Phase.mc.field_71439_g.func_70107_b(Phase.mc.field_71439_g.field_70165_t, Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v);
            Phase.mc.field_71439_g.field_70181_x = -1000.0;
            Phase.mc.field_71439_g.field_70145_X = true;
            final double[] directionSpeedVanilla2 = Utils.directionSpeed(this.speed.getValDouble());
            Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla2[0];
            Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla2[1];
        }
        if (this.mode.getValString().equalsIgnoreCase("destroy")) {
            final double[] dir2 = Utils.directionSpeed(1.0);
            if (Phase.mc.field_71439_g.field_70123_F) {
                Phase.mc.field_71441_e.func_175655_b(new BlockPos(Phase.mc.field_71439_g.field_70165_t + dir2[0], Phase.mc.field_71439_g.field_70163_u, Phase.mc.field_71439_g.field_70161_v + dir2[1]), false);
                Phase.mc.field_71441_e.func_175655_b(new BlockPos(Phase.mc.field_71439_g.field_70165_t + dir2[0], Phase.mc.field_71439_g.field_70163_u + 1.0, Phase.mc.field_71439_g.field_70161_v + dir2[1]), false);
            }
            if (Utils.isMoving((Entity)Phase.mc.field_71439_g) && Phase.mc.field_71439_g.field_70122_E) {
                final double[] directionSpeedVanilla3 = Utils.directionSpeed(0.23000000298023224);
                Phase.mc.field_71439_g.field_70159_w = directionSpeedVanilla3[0];
                Phase.mc.field_71439_g.field_70179_y = directionSpeedVanilla3[1];
            }
        }
    }
    
    public boolean canPhase() {
        return !Phase.mc.field_71439_g.func_70617_f_() && !Phase.mc.field_71439_g.func_70090_H() && !Phase.mc.field_71439_g.func_180799_ab();
    }
    
    public static boolean isInsideBlock() {
        for (int x = (int)Objects.requireNonNull(Phase.mc.field_71439_g.func_70046_E()).field_72340_a; x < Phase.mc.field_71439_g.func_70046_E().field_72336_d + 1.0; ++x) {
            for (int y = (int)Phase.mc.field_71439_g.func_70046_E().field_72338_b; y < Phase.mc.field_71439_g.func_70046_E().field_72337_e + 1.0; ++y) {
                for (int z = (int)Phase.mc.field_71439_g.func_70046_E().field_72339_c; z < Phase.mc.field_71439_g.func_70046_E().field_72334_f + 1.0; ++z) {
                    final Block block = Phase.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                    final AxisAlignedBB boundingBox;
                    if (!(block instanceof BlockAir) && (boundingBox = block.func_180646_a(Phase.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)), (IBlockAccess)Phase.mc.field_71441_e, new BlockPos(x, y, z))) != null && Phase.mc.field_71439_g.func_70046_E().func_72326_a(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    static {
        Phase.Finalsep = false;
    }
}
