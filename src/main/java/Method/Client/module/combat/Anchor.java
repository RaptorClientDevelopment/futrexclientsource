package Method.Client.module.combat;

import net.minecraft.util.math.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;

public class Anchor extends Module
{
    Setting maxheight;
    Setting JumpOut;
    Setting Onerun;
    BlockPos playerPos;
    private final TimerUtils timer;
    private boolean WasJump;
    
    public Anchor() {
        super("Anchor", 0, Category.COMBAT, "Anchor to Holes");
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("max height", this, 15.0, 0.0, 255.0, false);
        this.maxheight = setting;
        this.maxheight = setmgr.add(setting);
        final SettingsManager setmgr2 = Main.setmgr;
        final Setting setting2 = new Setting("JumpOut", this, true);
        this.JumpOut = setting2;
        this.JumpOut = setmgr2.add(setting2);
        final SettingsManager setmgr3 = Main.setmgr;
        final Setting setting3 = new Setting("Run Once", this, true);
        this.Onerun = setting3;
        this.Onerun = setmgr3.add(setting3);
        this.timer = new TimerUtils();
        this.WasJump = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.WasJump) {
            if (this.timer.isDelay(1800L)) {
                this.timer.setLastMS();
                this.WasJump = false;
            }
            return;
        }
        if (Anchor.mc.field_71439_g.field_70163_u < 0.0) {
            return;
        }
        if (Anchor.mc.field_71439_g.field_70163_u > this.maxheight.getValDouble()) {
            return;
        }
        double newX = Anchor.mc.field_71439_g.field_70165_t;
        double newZ = Anchor.mc.field_71439_g.field_70161_v;
        newX = ((Anchor.mc.field_71439_g.field_70165_t > Math.round(Anchor.mc.field_71439_g.field_70165_t)) ? (Math.round(Anchor.mc.field_71439_g.field_70165_t) + 0.5) : newX);
        newX = ((Anchor.mc.field_71439_g.field_70165_t < Math.round(Anchor.mc.field_71439_g.field_70165_t)) ? (Math.round(Anchor.mc.field_71439_g.field_70165_t) - 0.5) : newX);
        newZ = ((Anchor.mc.field_71439_g.field_70161_v > Math.round(Anchor.mc.field_71439_g.field_70161_v)) ? (Math.round(Anchor.mc.field_71439_g.field_70161_v) + 0.5) : newZ);
        newZ = ((Anchor.mc.field_71439_g.field_70161_v < Math.round(Anchor.mc.field_71439_g.field_70161_v)) ? (Math.round(Anchor.mc.field_71439_g.field_70161_v) - 0.5) : newZ);
        this.playerPos = new BlockPos(newX, Anchor.mc.field_71439_g.field_70163_u, newZ);
        if (Anchor.mc.field_71441_e.func_180495_p(this.playerPos).func_177230_c() != Blocks.field_150350_a) {
            return;
        }
        if (this.JumpOut.getValBoolean() && Anchor.mc.field_71474_y.field_74314_A.func_151468_f() && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177974_f()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177976_e()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177978_c()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177968_d()).func_177230_c() != Blocks.field_150350_a) {
            this.WasJump = true;
            return;
        }
        if (Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177974_f()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177976_e()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177978_c()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b().func_177968_d()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2)).func_177230_c() != Blocks.field_150350_a) {
            final double lMotionX = Math.floor(Anchor.mc.field_71439_g.field_70165_t) + 0.5 - Anchor.mc.field_71439_g.field_70165_t;
            final double lMotionZ = Math.floor(Anchor.mc.field_71439_g.field_70161_v) + 0.5 - Anchor.mc.field_71439_g.field_70161_v;
            Anchor.mc.field_71439_g.field_70159_w = lMotionX / 2.0;
            Anchor.mc.field_71439_g.field_70179_y = lMotionZ / 2.0;
        }
        else if (Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2)).func_177230_c() == Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2).func_177974_f()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2).func_177976_e()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2).func_177978_c()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(2).func_177968_d()).func_177230_c() != Blocks.field_150350_a && Anchor.mc.field_71441_e.func_180495_p(this.playerPos.func_177979_c(3)).func_177230_c() != Blocks.field_150350_a) {
            final double lMotionX = Math.floor(Anchor.mc.field_71439_g.field_70165_t) + 0.5 - Anchor.mc.field_71439_g.field_70165_t;
            final double lMotionZ = Math.floor(Anchor.mc.field_71439_g.field_70161_v) + 0.5 - Anchor.mc.field_71439_g.field_70161_v;
            Anchor.mc.field_71439_g.field_70159_w = lMotionX / 2.0;
            Anchor.mc.field_71439_g.field_70179_y = lMotionZ / 2.0;
        }
        if (this.Onerun.getValBoolean()) {
            this.toggle();
        }
        super.onClientTick(event);
    }
}
