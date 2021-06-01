package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class SafeWalk extends Module
{
    Setting mode;
    Setting EdgeStop;
    Setting SlowOnEdge;
    
    public SafeWalk() {
        super("SafeWalk", 0, Category.MOVEMENT, "SafeWalk, Safe ledge");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Sneak", new String[] { "Sneak", "Normal" }));
        this.EdgeStop = Main.setmgr.add(new Setting("Edge Stop", this, true, this.mode, "Normal", 2));
        this.SlowOnEdge = Main.setmgr.add(new Setting("Slow on Edge", this, false));
    }
    
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Normal") && SafeWalk.mc.field_71439_g.field_70122_E && !SafeWalk.mc.field_71474_y.field_74314_A.func_151468_f() && !isCollidable(SafeWalk.mc.field_71441_e.func_180495_p(new BlockPos(SafeWalk.mc.field_71439_g.func_174791_d().func_178787_e(new Vec3d(0.0, -0.5, 0.0)))).func_177230_c())) {
            if (this.SlowOnEdge.getValBoolean()) {
                final EntityPlayerSP field_71439_g = SafeWalk.mc.field_71439_g;
                field_71439_g.field_70159_w -= SafeWalk.mc.field_71439_g.field_70159_w;
                final EntityPlayerSP field_71439_g2 = SafeWalk.mc.field_71439_g;
                field_71439_g2.field_70179_y -= SafeWalk.mc.field_71439_g.field_70179_y;
            }
            if (this.EdgeStop.getValBoolean()) {
                SafeWalk.mc.field_71439_g.func_70107_b(SafeWalk.mc.field_71439_g.field_70169_q, SafeWalk.mc.field_71439_g.field_70163_u, SafeWalk.mc.field_71439_g.field_70166_s);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Sneak")) {
            if (SafeWalk.mc.field_71439_g.field_70122_E && !SafeWalk.mc.field_71474_y.field_74314_A.func_151468_f() && !isCollidable(SafeWalk.mc.field_71441_e.func_180495_p(new BlockPos(SafeWalk.mc.field_71439_g.func_174791_d().func_178787_e(new Vec3d(0.0, -0.5, 0.0)))).func_177230_c())) {
                KeyBinding.func_74510_a(SafeWalk.mc.field_71474_y.field_74311_E.func_151463_i(), true);
                if (this.SlowOnEdge.getValBoolean()) {
                    final EntityPlayerSP field_71439_g3 = SafeWalk.mc.field_71439_g;
                    field_71439_g3.field_70159_w -= SafeWalk.mc.field_71439_g.field_70159_w;
                    final EntityPlayerSP field_71439_g4 = SafeWalk.mc.field_71439_g;
                    field_71439_g4.field_70179_y -= SafeWalk.mc.field_71439_g.field_70179_y;
                }
            }
            else if (!Keyboard.isKeyDown(SafeWalk.mc.field_71474_y.field_74311_E.func_151463_i())) {
                KeyBinding.func_74510_a(SafeWalk.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            }
        }
    }
    
    public static boolean isCollidable(final Block block) {
        return block != Blocks.field_150350_a && block != Blocks.field_185773_cZ && block != Blocks.field_150459_bM && block != Blocks.field_150330_I && block != Blocks.field_150398_cm && block != Blocks.field_150356_k && block != Blocks.field_150358_i && block != Blocks.field_150353_l && block != Blocks.field_150394_bc && block != Blocks.field_150388_bm && block != Blocks.field_150469_bN && block != Blocks.field_150393_bb && block != Blocks.field_150328_O && block != Blocks.field_150337_Q && block != Blocks.field_150429_aA && block != Blocks.field_150329_H && block != Blocks.field_150478_aa && block != Blocks.field_150437_az && block != Blocks.field_150327_N && block != Blocks.field_150395_bd && block != Blocks.field_150355_j && block != Blocks.field_150321_G && block != Blocks.field_150464_aj;
    }
}
