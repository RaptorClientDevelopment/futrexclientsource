package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class Levitate extends Module
{
    Setting mode;
    private double startY;
    int counter;
    
    public Levitate() {
        super("Levitate", 0, Category.MOVEMENT, "Levitate");
        this.mode = Main.setmgr.add(new Setting("Fly Mode", this, "Normal", new String[] { "Normal", "Weird", "Old", "MoonWalk" }));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Moonwalk")) {
            if (Levitate.mc.field_71439_g.field_70122_E && Wrapper.mc.field_71474_y.field_74314_A.func_151468_f()) {
                Levitate.mc.field_71439_g.field_70181_x = 0.25;
            }
            else if (Levitate.mc.field_71439_g.field_70160_al && !Levitate.mc.field_71439_g.func_70090_H() && !Levitate.mc.field_71439_g.func_70617_f_() && !Levitate.mc.field_71439_g.func_70055_a(Material.field_151587_i)) {
                Levitate.mc.field_71439_g.field_70181_x = 1.0E-6;
                final EntityPlayerSP player = Levitate.mc.field_71439_g;
                final EntityPlayerSP field_71439_g = Levitate.mc.field_71439_g;
                field_71439_g.field_70747_aH *= 1.21337f;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Normal")) {
            Levitate.mc.field_71439_g.field_70181_x = 0.0;
            if (Levitate.mc.field_71474_y.field_74311_E.field_74513_e) {
                Levitate.mc.field_71439_g.field_70181_x = -0.1;
            }
            if (Levitate.mc.field_71474_y.field_74314_A.field_74513_e) {
                Levitate.mc.field_71439_g.field_70181_x = 0.1;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Old") && !Levitate.mc.field_71439_g.field_70122_E && Levitate.mc.field_71474_y.field_74314_A.field_74513_e) {
            Levitate.mc.field_71439_g.field_70181_x = ((Levitate.mc.field_71439_g.field_70163_u < this.startY - 1.0) ? 0.2 : -0.05);
        }
        if (this.mode.getValString().equalsIgnoreCase("Weird")) {
            ++this.counter;
            if (this.counter > 3.2) {
                Levitate.mc.field_71474_y.field_74311_E.field_74513_e = true;
                final EntityPlayerSP field_71439_g2 = Levitate.mc.field_71439_g;
                field_71439_g2.field_70159_w *= 1.2;
                Levitate.mc.field_71439_g.field_70739_aP = 1.0f;
                final EntityPlayerSP field_71439_g3 = Levitate.mc.field_71439_g;
                field_71439_g3.field_70179_y *= 1.2;
                this.counter = 0;
            }
            else {
                ++this.counter;
            }
            if (this.counter > 3.7) {
                Levitate.mc.field_71474_y.field_74311_E.field_74513_e = false;
                this.counter = 0;
            }
            Levitate.mc.field_71439_g.field_70122_E = true;
            Levitate.mc.field_71439_g.field_70181_x = 0.0;
            final EntityPlayerSP field_71439_g4 = Levitate.mc.field_71439_g;
            field_71439_g4.field_70159_w *= 0.2;
            Levitate.mc.field_71439_g.field_70739_aP = 1.0f;
            final EntityPlayerSP field_71439_g5 = Levitate.mc.field_71439_g;
            field_71439_g5.field_70179_y *= 0.2;
            Levitate.mc.field_71439_g.func_174829_m();
            Levitate.mc.field_71439_g.func_70107_b(Levitate.mc.field_71439_g.field_70165_t, Levitate.mc.field_71439_g.field_70163_u + 1.0E-9, Levitate.mc.field_71439_g.field_70161_v);
            if (Levitate.mc.field_71439_g.field_70173_aa % 3 == 0 && Levitate.mc.field_71441_e.func_180495_p(new BlockPos(Levitate.mc.field_71439_g.field_70165_t, Levitate.mc.field_71439_g.field_70163_u - 0.2, Levitate.mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockAir) {
                Levitate.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Levitate.mc.field_71439_g.field_70165_t, Levitate.mc.field_71439_g.field_70163_u - 0.0, Levitate.mc.field_71439_g.field_70161_v, Levitate.mc.field_71439_g.field_70177_z, Levitate.mc.field_71439_g.field_70125_A, true));
            }
        }
        super.onClientTick(event);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.startY = Levitate.mc.field_71439_g.field_70163_u;
        if (this.mode.getValString().equalsIgnoreCase("Weird")) {
            Levitate.mc.field_71439_g.field_70181_x = 0.42;
            for (int i2 = 1; i2 < 4; ++i2) {
                Levitate.mc.field_71439_g.field_70738_aO = 9;
                Levitate.mc.field_71439_g.func_70057_ab();
                Levitate.mc.field_71439_g.field_70143_R = 0.0f;
            }
        }
    }
}
