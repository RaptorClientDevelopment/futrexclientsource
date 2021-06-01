package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import Method.Client.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import Method.Client.utils.system.*;

public class Step extends Module
{
    public int ticks;
    Setting mode;
    Setting Height;
    Setting Entity;
    Setting Timer;
    
    public Step() {
        super("Step", 0, Category.MOVEMENT, "Allows you to step up.");
        this.ticks = 0;
        this.mode = Main.setmgr.add(new Setting("STEP", this, "Vanilla", new String[] { "Vanilla", "ACC", "Packet", "FastAAC", "NCP", "Hop", "SPAM", "Step" }));
        this.Height = Main.setmgr.add(new Setting("Height", this, 1.0, 0.5, 4.0, true));
        this.Entity = Main.setmgr.add(new Setting("Entity", this, true));
        this.Timer = Main.setmgr.add(new Setting("Timer", this, true, this.mode, "Packet", 3));
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        super.onEnable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.Entity.getValBoolean() && Step.mc.field_71439_g.func_184187_bx() != null && Step.mc.field_71439_g.func_184187_bx().field_70138_W != (int)this.Height.getValDouble()) {
            Step.mc.field_71439_g.func_184187_bx().field_70138_W = (int)this.Height.getValDouble();
        }
        if (this.mode.getValString().equalsIgnoreCase("Step")) {
            if (Step.mc.field_71439_g.field_70123_F && Step.mc.field_71439_g.field_70122_E) {
                Step.mc.field_71439_g.func_70664_aZ();
            }
            if (Step.mc.field_71439_g.field_70123_F && Step.mc.field_71439_g.field_70122_E && Step.mc.field_71439_g.field_70163_u + 1.065 < Step.mc.field_71439_g.field_70163_u) {
                Step.mc.field_71439_g.func_70016_h(Step.mc.field_71439_g.field_70159_w, -0.1, Step.mc.field_71439_g.field_70179_y);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("FastAAC")) {
            final BlockPos pos1 = new BlockPos(Step.mc.field_71439_g.field_70165_t + 1.0, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
            final BlockPos pos2 = new BlockPos(Step.mc.field_71439_g.field_70165_t - 1.0, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
            final BlockPos pos3 = new BlockPos(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v + 1.0);
            final BlockPos pos4 = new BlockPos(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v - 1.0);
            final Block block1 = Step.mc.field_71441_e.func_180495_p(pos1).func_177230_c();
            final Block block2 = Step.mc.field_71441_e.func_180495_p(pos2).func_177230_c();
            final Block block3 = Step.mc.field_71441_e.func_180495_p(pos3).func_177230_c();
            final Block block4 = Step.mc.field_71441_e.func_180495_p(pos4).func_177230_c();
            if (Step.mc.field_71474_y.field_74351_w.func_151470_d() || Step.mc.field_71474_y.field_74370_x.func_151470_d() || Step.mc.field_71474_y.field_74366_z.func_151470_d() || (Step.mc.field_71474_y.field_74368_y.func_151470_d() && Step.mc.field_71439_g.field_70123_F && (block1 == Blocks.field_150350_a || block2 == Blocks.field_150350_a || block3 == Blocks.field_150350_a || block4 == Blocks.field_150350_a))) {
                if (Step.mc.field_71439_g.field_70122_E) {
                    Step.mc.field_71439_g.func_70664_aZ();
                    Step.mc.field_71439_g.field_70181_x = 0.386;
                }
                else {
                    toFwd(0.26);
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Spam")) {
            this.Spam();
        }
        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
            if (Step.mc.field_71439_g.func_70090_H() || Step.mc.field_71439_g.func_180799_ab() || Step.mc.field_71439_g.func_70617_f_() || Step.mc.field_71474_y.field_74314_A.func_151470_d()) {
                return;
            }
            if (this.Timer.getValBoolean()) {
                if (this.ticks == 0) {
                    Step.mc.field_71428_T.field_194149_e = 50.0f;
                }
                else {
                    --this.ticks;
                }
            }
            if (Step.mc.field_71439_g != null && Step.mc.field_71439_g.field_70122_E && !Step.mc.field_71439_g.func_70090_H() && !Step.mc.field_71439_g.func_70617_f_()) {
                for (double y = 0.0; y < this.Height.getValDouble() + 0.5; y += 0.01) {
                    if (!Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -y, 0.0)).isEmpty()) {
                        Step.mc.field_71439_g.field_70181_x = -10.0;
                        break;
                    }
                }
            }
            final double[] dir = Utils.directionSpeed(0.1);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.4, dir[1])).isEmpty()) {
                twofive = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.9, dir[1])).isEmpty()) {
                two = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.4, dir[1])).isEmpty()) {
                onefive = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 0.6, dir[1])).isEmpty()) {
                one = true;
            }
            if (Step.mc.field_71439_g.field_70123_F && (Step.mc.field_71439_g.field_191988_bg != 0.0f || Step.mc.field_71439_g.field_70702_br != 0.0f) && Step.mc.field_71439_g.field_70122_E) {
                if (one && this.Height.getValDouble() >= 1.0) {
                    final double[] array;
                    final double[] oneOffset = array = new double[] { 0.42, 0.753 };
                    for (final double v : array) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + v, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.Timer.getValBoolean()) {
                        Step.mc.field_71428_T.field_194149_e = 83.33333f;
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 1;
                }
                if (onefive && this.Height.getValDouble() >= 1.5) {
                    final double[] array2;
                    final double[] oneFiveOffset = array2 = new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 };
                    for (final double v : array2) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + v, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.Timer.getValBoolean()) {
                        Step.mc.field_71428_T.field_194149_e = 142.85715f;
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.5, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 1;
                }
                if (two && this.Height.getValDouble() >= 2.0) {
                    final double[] array3;
                    final double[] twoOffset = array3 = new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 };
                    for (final double v : array3) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + v, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.Timer.getValBoolean()) {
                        Step.mc.field_71428_T.field_194149_e = 200.0f;
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 2.0, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 2;
                }
                if (twofive && this.Height.getValDouble() >= 2.5) {
                    final double[] array4;
                    final double[] twoFiveOffset = array4 = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
                    for (final double v : array4) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + v, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.Timer.getValBoolean()) {
                        Step.mc.field_71428_T.field_194149_e = 333.3333f;
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 2.5, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 2;
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("ACC")) {
            final EntityPlayerSP player = Step.mc.field_71439_g;
            if (player.field_70123_F) {
                switch (this.ticks) {
                    case 0: {
                        if (player.field_70122_E) {
                            player.func_70664_aZ();
                            break;
                        }
                        break;
                    }
                    case 7: {
                        player.field_70181_x = 0.0;
                        break;
                    }
                    case 8: {
                        if (!player.field_70122_E) {
                            player.func_70107_b(player.field_70165_t, player.field_70163_u + 1.0, player.field_70161_v);
                            break;
                        }
                        break;
                    }
                }
                ++this.ticks;
            }
            else {
                this.ticks = 0;
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            Step.mc.field_71439_g.field_70138_W = (float)this.Height.getValDouble();
        }
        else if (this.mode.getValString().equalsIgnoreCase("NCP")) {
            if (Step.mc.field_71439_g.field_70123_F && Step.mc.field_71439_g.field_70122_E && Step.mc.field_71439_g.field_70124_G && Step.mc.field_71439_g.field_70132_H) {
                this.StepRun();
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Hop") && Step.mc.field_71474_y.field_74314_A.field_74513_e && Step.mc.field_71439_g.field_70123_F) {
            Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70142_S, Step.mc.field_71439_g.field_70117_cu + Step.mc.field_71439_g.field_70137_T + 0.09000000357627869, Step.mc.field_71439_g.field_70116_cv + Step.mc.field_71439_g.field_70161_v);
        }
        super.onClientTick(event);
    }
    
    public double get_n_normal() {
        Step.mc.field_71439_g.field_70138_W = 0.5f;
        double max_y = -1.0;
        final AxisAlignedBB grow = Step.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, 0.05, 0.0).func_186662_g(0.05);
        if (!Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, grow.func_72317_d(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (final AxisAlignedBB aabb : Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, grow)) {
            if (aabb.field_72337_e > max_y) {
                max_y = aabb.field_72337_e;
            }
        }
        return max_y - Step.mc.field_71439_g.field_70163_u;
    }
    
    public static void toFwd(final double speed) {
        final float yaw = Step.mc.field_71439_g.field_70177_z * 0.017453292f;
        final EntityPlayerSP field_71439_g = Step.mc.field_71439_g;
        field_71439_g.field_70159_w -= MathHelper.func_76126_a(yaw) * speed;
        final EntityPlayerSP field_71439_g2 = Step.mc.field_71439_g;
        field_71439_g2.field_70179_y += MathHelper.func_76134_b(yaw) * speed;
    }
    
    private void Spam() {
        if (Step.mc.field_71439_g.field_70122_E && !Step.mc.field_71439_g.func_70617_f_() && !Step.mc.field_71439_g.func_70090_H() && !Step.mc.field_71439_g.func_180799_ab() && !Step.mc.field_71439_g.field_71158_b.field_78901_c && !Step.mc.field_71439_g.field_70145_X && (Step.mc.field_71439_g.field_191988_bg != 0.0f || Step.mc.field_71439_g.field_70702_br != 0.0f)) {
            final double n = this.get_n_normal();
            if (n < 0.0 || n > 2.0) {
                return;
            }
            if (n == 2.0) {
                this.Sendpos(0.42);
                this.Sendpos(0.78);
                this.Sendpos(0.63);
                this.Sendpos(0.51);
                this.Sendpos(0.9);
                this.Sendpos(1.21);
                this.Sendpos(1.45);
                this.Sendpos(1.43);
                Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 2.0, Step.mc.field_71439_g.field_70161_v);
            }
            if (n == 1.5) {
                this.Sendpos(0.41999998688698);
                this.Sendpos(0.7531999805212);
                this.Sendpos(1.00133597911214);
                this.Sendpos(1.16610926093821);
                this.Sendpos(1.24918707874468);
                this.Sendpos(1.1707870772188);
                Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
            }
            if (n == 1.0) {
                this.Sendpos(0.41999998688698);
                this.Sendpos(0.7531999805212);
                Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
            }
        }
    }
    
    private void Sendpos(final double pos) {
        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + pos, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
    }
    
    private void StepRun() {
        Step.mc.field_71439_g.func_70031_b(true);
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 0.42, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
        Step.mc.field_71439_g.func_70031_b(true);
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 0.753, Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
        Step.mc.field_71439_g.func_70031_b(true);
        Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
        Step.mc.field_71439_g.func_70031_b(true);
    }
    
    @Override
    public void onDisable() {
        Step.mc.field_71439_g.field_70138_W = 0.5f;
        Step.mc.field_71428_T.field_194149_e = 50.0f;
        super.onDisable();
        try {
            if (Step.mc.field_71439_g.func_184187_bx() != null) {
                Step.mc.field_71439_g.func_184187_bx().field_70138_W = 1.0f;
            }
        }
        catch (Exception ex) {}
    }
}
