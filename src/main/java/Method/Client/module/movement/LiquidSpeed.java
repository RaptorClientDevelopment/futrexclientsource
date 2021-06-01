package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;

public class LiquidSpeed extends Module
{
    Setting waterSpeed;
    Setting lavaSpeed;
    Setting mode;
    private final TimerUtils timer;
    
    public LiquidSpeed() {
        super("LiquidSpeed", 0, Category.MOVEMENT, "Liquid Speed");
        this.waterSpeed = Main.setmgr.add(new Setting("waterSpeed", this, 1.0, 0.9, 1.1, false));
        this.lavaSpeed = Main.setmgr.add(new Setting("lavaSpeed", this, 1.0, 0.9, 1.1, false));
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Vanilla", new String[] { "Vanilla", "Bypass" }));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Bypass") && LiquidSpeed.mc.field_71439_g.func_70090_H() && this.timer.isDelay(940L)) {
            final EntityPlayerSP field_71439_g = LiquidSpeed.mc.field_71439_g;
            field_71439_g.field_70159_w *= 1.005;
            final EntityPlayerSP field_71439_g2 = LiquidSpeed.mc.field_71439_g;
            field_71439_g2.field_70179_y *= 1.005;
            LiquidSpeed.mc.field_71439_g.field_70181_x = 0.4;
            this.timer.setLastMS();
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            final BlockPos blockPos = new BlockPos(LiquidSpeed.mc.field_71439_g.field_70165_t, LiquidSpeed.mc.field_71439_g.field_70163_u + 0.4, LiquidSpeed.mc.field_71439_g.field_70161_v);
            if (LiquidSpeed.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150353_l) {
                this.Speed(this.lavaSpeed);
                if (LiquidSpeed.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    LiquidSpeed.mc.field_71439_g.field_70181_x = 0.06;
                }
                if (LiquidSpeed.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    LiquidSpeed.mc.field_71439_g.field_70181_x = -0.14;
                }
            }
            if (LiquidSpeed.mc.field_71439_g.func_70090_H()) {
                this.Speed(this.waterSpeed);
                if (LiquidSpeed.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    final EntityPlayerSP field_71439_g3 = LiquidSpeed.mc.field_71439_g;
                    field_71439_g3.field_70181_x *= this.waterSpeed.getValDouble() / 1.2;
                }
            }
        }
    }
    
    private void Speed(final Setting waterSpeed) {
        if (LiquidSpeed.mc.field_71474_y.field_74351_w.func_151470_d() || LiquidSpeed.mc.field_71474_y.field_74366_z.func_151470_d() || LiquidSpeed.mc.field_71474_y.field_74370_x.func_151470_d() || LiquidSpeed.mc.field_71474_y.field_74368_y.func_151470_d()) {
            final EntityPlayerSP field_71439_g = LiquidSpeed.mc.field_71439_g;
            field_71439_g.field_70159_w *= waterSpeed.getValDouble();
            final EntityPlayerSP field_71439_g2 = LiquidSpeed.mc.field_71439_g;
            field_71439_g2.field_70179_y *= waterSpeed.getValDouble();
        }
    }
}
