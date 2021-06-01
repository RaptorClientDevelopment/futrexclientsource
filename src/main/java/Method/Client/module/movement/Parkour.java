package Method.Client.module.movement;

import Method.Client.utils.*;
import Method.Client.managers.*;
import net.minecraft.block.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.init.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.entity.*;

public class Parkour extends Module
{
    public TimerUtils timer;
    public TimerUtils Delayer;
    Setting Nearedge;
    Setting autodrop;
    Setting runsinglegap;
    Setting FullBlock;
    public static List<Block> pospoint5;
    public static List<Block> Replace;
    BlockPos playerPos;
    
    public Parkour() {
        super("Parkour", 0, Category.MOVEMENT, "Auto Parkour+");
        this.timer = new TimerUtils();
        this.Delayer = new TimerUtils();
        this.Nearedge = Main.setmgr.add(new Setting("Nearedge", this, 0.001, 0.0, 0.01, false));
        this.autodrop = Main.setmgr.add(new Setting("autodrop", this, false));
        this.runsinglegap = Main.setmgr.add(new Setting("Run Single Gap", this, false));
        this.FullBlock = Main.setmgr.add(new Setting("All Full Blocks", this, false));
    }
    
    @Override
    public void setup() {
        Parkour.Replace = Arrays.asList(Blocks.field_150477_bB, Blocks.field_150434_aF, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150411_aY, Blocks.field_150367_z, Blocks.field_150378_br, Blocks.field_150415_aT, Blocks.field_150381_bn, Blocks.field_185769_cV, Blocks.field_180396_cN, Blocks.field_150401_cl, Blocks.field_150400_ck, Blocks.field_150370_cb, Blocks.field_150372_bz, Blocks.field_150485_bF, Blocks.field_150487_bG, Blocks.field_150481_bH, Blocks.field_150387_bl, Blocks.field_150476_ad, Blocks.field_150446_ar, Blocks.field_150389_bf, Blocks.field_150390_bg);
        Parkour.pospoint5 = Arrays.asList(Blocks.field_180405_aT, Blocks.field_180407_aO, Blocks.field_180408_aP, Blocks.field_180404_aQ, Blocks.field_180403_aR, Blocks.field_180406_aS, Blocks.field_180405_aT, Blocks.field_180390_bo, Blocks.field_180391_bp, Blocks.field_180392_bq, Blocks.field_180386_br, Blocks.field_180385_bs, Blocks.field_180387_bt, Blocks.field_150386_bk, Blocks.field_150463_bK);
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.FullBlock.getValBoolean() && this.Delayer.isDelay(200L)) {
            for (final BlockPos b : BlockPos.func_191532_a((int)Parkour.mc.field_71439_g.field_70165_t - 5, (int)Parkour.mc.field_71439_g.field_70163_u - 5, (int)Parkour.mc.field_71439_g.field_70161_v - 5, (int)Parkour.mc.field_71439_g.field_70165_t + 5, (int)Parkour.mc.field_71439_g.field_70163_u + 5, (int)Parkour.mc.field_71439_g.field_70161_v + 5)) {
                if (Parkour.pospoint5.contains(Parkour.mc.field_71441_e.func_180495_p(b).func_177230_c())) {
                    final BlockPos pos = new BlockPos(b.field_177962_a, b.field_177960_b + 1, b.field_177961_c);
                    while (Parkour.pospoint5.contains(Parkour.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
                        final BlockPos blockPos = pos;
                        ++blockPos.field_177960_b;
                    }
                    Parkour.mc.field_71441_e.func_175656_a(pos, Blocks.field_185771_cX.func_176223_P());
                }
                if (Parkour.Replace.contains(Parkour.mc.field_71441_e.func_180495_p(b).func_177230_c())) {
                    Parkour.mc.field_71441_e.func_175656_a(b, Blocks.field_150440_ba.func_176223_P());
                }
            }
            this.Delayer.setLastMS();
        }
        double newX = Parkour.mc.field_71439_g.field_70165_t;
        double newZ = Parkour.mc.field_71439_g.field_70161_v;
        newX = ((Parkour.mc.field_71439_g.field_70165_t > Math.round(Parkour.mc.field_71439_g.field_70165_t)) ? (Math.round(Parkour.mc.field_71439_g.field_70165_t) + 0.5) : newX);
        newX = ((Parkour.mc.field_71439_g.field_70165_t < Math.round(Parkour.mc.field_71439_g.field_70165_t)) ? (Math.round(Parkour.mc.field_71439_g.field_70165_t) - 0.5) : newX);
        newZ = ((Parkour.mc.field_71439_g.field_70161_v > Math.round(Parkour.mc.field_71439_g.field_70161_v)) ? (Math.round(Parkour.mc.field_71439_g.field_70161_v) + 0.5) : newZ);
        newZ = ((Parkour.mc.field_71439_g.field_70161_v < Math.round(Parkour.mc.field_71439_g.field_70161_v)) ? (Math.round(Parkour.mc.field_71439_g.field_70161_v) - 0.5) : newZ);
        this.playerPos = new BlockPos(newX, Parkour.mc.field_71439_g.field_70163_u, newZ);
        if (this.autodrop.getValBoolean() && Parkour.mc.field_71439_g.field_70181_x < -0.01 && !Parkour.mc.field_71439_g.field_70122_E) {
            for (int i = 0; i < 4; ++i) {
                if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b()).func_177230_c() != Blocks.field_150350_a) {
                    final EntityPlayerSP field_71439_g = Parkour.mc.field_71439_g;
                    field_71439_g.field_70159_w -= Parkour.mc.field_71439_g.field_70159_w / 555.0;
                    final EntityPlayerSP field_71439_g2 = Parkour.mc.field_71439_g;
                    field_71439_g2.field_70179_y -= Parkour.mc.field_71439_g.field_70179_y / 555.0;
                    break;
                }
                this.playerPos = new BlockPos(newX, Parkour.mc.field_71439_g.field_70163_u - i, newZ);
            }
        }
        if (Parkour.mc.field_71439_g.field_70122_E && !Parkour.mc.field_71439_g.func_70093_af() && !Parkour.mc.field_71474_y.field_74311_E.field_74513_e && Parkour.mc.field_71441_e.func_184144_a((Entity)Parkour.mc.field_71439_g, Parkour.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -0.5, 0.0).func_72321_a(-this.Nearedge.getValDouble(), 0.0, -this.Nearedge.getValDouble())).isEmpty() && this.timer.isDelay(100L)) {
            if (this.runsinglegap.getValBoolean()) {
                switch (MathHelper.func_76128_c(Parkour.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
                    case 0: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177968_d()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 1: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177968_d()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177976_e()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 2: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177976_e()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 3: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177976_e()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177978_c()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 4: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177978_c()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 5: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177974_f()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177978_c()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 6: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177974_f()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                    case 7: {
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177968_d()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        if (Parkour.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177974_f()).func_177230_c() != Blocks.field_150350_a) {
                            break;
                        }
                        this.jumpme();
                        break;
                    }
                }
            }
            else {
                this.jumpme();
            }
        }
    }
    
    private void jumpme() {
        Parkour.mc.field_71439_g.func_70664_aZ();
        this.timer.setLastMS();
    }
}
