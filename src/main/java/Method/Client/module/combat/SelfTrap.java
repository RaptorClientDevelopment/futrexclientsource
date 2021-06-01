package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class SelfTrap extends Module
{
    private BlockPos trap_pos;
    public Setting place_mode;
    public Setting rotate;
    public Setting Hand;
    
    public SelfTrap() {
        super("SelfTrap", 0, Category.COMBAT, "SelfTrap");
        this.place_mode = Main.setmgr.add(new Setting("cage", this, "Extra", new String[] { "Extra", "Face", "Normal", "Feet" }));
        this.rotate = Main.setmgr.add(new Setting("rotate", this, false));
        this.Hand = Main.setmgr.add(new Setting("Hand", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final Vec3d pos = Utils.interpolateEntity((Entity)SelfTrap.mc.field_71439_g, SelfTrap.mc.func_184121_ak());
        this.trap_pos = new BlockPos(pos.field_72450_a, pos.field_72448_b + 2.0, pos.field_72449_c);
        if (this.is_trapped()) {
            this.toggle();
            return;
        }
        final Utils.ValidResult result = Utils.valid(this.trap_pos);
        if (result == Utils.ValidResult.AlreadyBlockThere && !SelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (result == Utils.ValidResult.NoNeighbors) {
            final BlockPos[] array;
            final BlockPos[] tests = array = new BlockPos[] { this.trap_pos.func_177978_c(), this.trap_pos.func_177968_d(), this.trap_pos.func_177974_f(), this.trap_pos.func_177976_e(), this.trap_pos.func_177984_a(), this.trap_pos.func_177977_b().func_177976_e() };
            for (final BlockPos pos_ : array) {
                final Utils.ValidResult result_ = Utils.valid(pos_);
                if (result_ != Utils.ValidResult.NoNeighbors) {
                    if (result_ != Utils.ValidResult.NoEntityCollision) {
                        if (Utils.placeBlock(pos_, this.rotate.getValBoolean(), this.Hand)) {
                            return;
                        }
                    }
                }
            }
            return;
        }
        Utils.placeBlock(this.trap_pos, this.rotate.getValBoolean(), this.Hand);
    }
    
    public boolean is_trapped() {
        if (this.trap_pos == null) {
            return false;
        }
        final IBlockState state = SelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos);
        return state.func_177230_c() != Blocks.field_150350_a && state.func_177230_c() != Blocks.field_150355_j && state.func_177230_c() != Blocks.field_150353_l;
    }
}
