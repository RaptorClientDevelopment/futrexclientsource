package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;

public class FastFall extends Module
{
    Setting speed;
    Setting timer;
    
    public FastFall() {
        super("FastFall", 0, Category.MOVEMENT, "Fast Fall");
        this.speed = Main.setmgr.add(new Setting("Speed", this, 0.1, 0.1, 4.0, false));
        this.timer = Main.setmgr.add(new Setting("timer", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.getValBoolean() && FastFall.mc.field_71439_g.field_70122_E) {
            this.setTickLength(50.0f);
        }
        if (FastFall.mc.field_71439_g.func_184613_cA() || FastFall.mc.field_71439_g.field_71075_bZ.field_75100_b) {
            return;
        }
        final boolean b = !FastFall.mc.field_71441_e.func_175623_d(FastFall.mc.field_71439_g.func_180425_c().func_177982_a(0, -1, 0)) || !FastFall.mc.field_71441_e.func_175623_d(FastFall.mc.field_71439_g.func_180425_c().func_177982_a(0, -2, 0));
        if (!FastFall.mc.field_71439_g.field_70122_E && !b) {
            if (this.timer.getValBoolean() && !FastFall.mc.field_71439_g.field_70122_E) {
                this.setTickLength((float)(50.0 / this.speed.getValDouble()));
            }
            else {
                FastFall.mc.field_71439_g.field_70181_x = -this.speed.getValDouble();
            }
        }
    }
    
    private void setTickLength(final float tickLength) {
        FastFall.mc.field_71428_T.field_194149_e = 1.0f * tickLength;
    }
    
    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
    }
}
