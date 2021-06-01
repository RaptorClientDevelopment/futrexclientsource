package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;

public class Sprint extends Module
{
    Setting Backwards;
    Setting Foodcheck;
    Setting ObstacleCheck;
    Setting InstarunTimer;
    Setting Fastspeedup;
    double quicktimerrun;
    boolean startedquickrun;
    
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT, "Always be running");
        this.Backwards = Main.setmgr.add(new Setting("Backwards", this, false));
        this.Foodcheck = Main.setmgr.add(new Setting("Food check", this, true));
        this.ObstacleCheck = Main.setmgr.add(new Setting("Obstical Check", this, true));
        this.InstarunTimer = Main.setmgr.add(new Setting("InstarunTimer", this, false, this.ObstacleCheck, 3));
        this.Fastspeedup = Main.setmgr.add(new Setting("Fastspeedup", this, 10.0, 2.0, 40.0, true, this.ObstacleCheck, 4));
        this.quicktimerrun = 10.0;
        this.startedquickrun = false;
    }
    
    private void setTickLength(final float tickLength) {
        Sprint.mc.field_71428_T.field_194149_e = 1.0f * tickLength;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Sprint.mc.field_71439_g.func_71024_bL().func_75116_a() > 6 || !this.Foodcheck.getValBoolean()) {
            if (this.ObstacleCheck.getValBoolean()) {
                if (this.canSprint()) {
                    Sprint.mc.field_71439_g.func_70031_b(true);
                }
            }
            else {
                Sprint.mc.field_71439_g.func_70031_b(true);
            }
            if (Sprint.mc.field_71439_g.func_70051_ag()) {
                if (this.InstarunTimer.getValBoolean() && this.quicktimerrun > 0.0) {
                    this.startedquickrun = true;
                    this.setTickLength(33.333f);
                    --this.quicktimerrun;
                    if (this.quicktimerrun < 1.0) {
                        this.setTickLength(50.0f);
                    }
                }
            }
            else {
                if (this.startedquickrun) {
                    this.startedquickrun = false;
                    this.setTickLength(50.0f);
                }
                this.quicktimerrun = this.Fastspeedup.getValDouble();
            }
            if (this.Backwards.getValBoolean() && !Sprint.mc.field_71439_g.func_184613_cA() && Wrapper.mc.field_71474_y.field_74368_y.func_151470_d()) {
                if (Sprint.mc.field_71439_g.field_191988_bg > 0.0f && !Sprint.mc.field_71439_g.field_70123_F) {
                    Sprint.mc.field_71439_g.func_70031_b(true);
                }
                if (Sprint.mc.field_71439_g.field_70122_E) {
                    final EntityPlayerSP field_71439_g = Sprint.mc.field_71439_g;
                    field_71439_g.field_70159_w *= 1.092;
                    final EntityPlayerSP field_71439_g2 = Sprint.mc.field_71439_g;
                    field_71439_g2.field_70179_y *= 1.092;
                }
                final double sqrt = Math.sqrt(Math.pow(Sprint.mc.field_71439_g.field_70159_w, 2.0) + Math.pow(Sprint.mc.field_71439_g.field_70179_y, 2.0));
                final double n = 0.6500000262260437;
                if (sqrt > 0.6500000262260437) {
                    Sprint.mc.field_71439_g.field_70159_w = Sprint.mc.field_71439_g.field_70159_w / sqrt * 0.6500000262260437;
                    Sprint.mc.field_71439_g.field_70179_y = Sprint.mc.field_71439_g.field_70179_y / sqrt * 0.6500000262260437;
                }
            }
        }
        super.onClientTick(event);
    }
    
    boolean canSprint() {
        return Sprint.mc.field_71439_g.field_70122_E && !Sprint.mc.field_71439_g.func_70051_ag() && !Sprint.mc.field_71439_g.func_70617_f_() && !Sprint.mc.field_71439_g.func_70090_H() && !Sprint.mc.field_71439_g.func_180799_ab() && !Sprint.mc.field_71439_g.field_70123_F && Sprint.mc.field_71439_g.field_191988_bg >= 0.1f && !Sprint.mc.field_71439_g.func_70093_af() && Sprint.mc.field_71439_g.func_71024_bL().func_75116_a() >= 6 && !Sprint.mc.field_71439_g.func_184218_aH() && !Sprint.mc.field_71439_g.func_70644_a(MobEffects.field_76440_q);
    }
}
