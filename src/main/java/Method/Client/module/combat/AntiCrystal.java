package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import Method.Client.utils.visual.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import Method.Client.utils.*;
import net.minecraft.network.play.client.*;

public class AntiCrystal extends Module
{
    public Setting Hand;
    public Setting rotate;
    
    public AntiCrystal() {
        super("AntiCrystal", 0, Category.COMBAT, "String");
        this.Hand = Main.setmgr.add(new Setting("Hand", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.rotate = Main.setmgr.add(new Setting("rotate", this, true));
    }
    
    private int find_in_hotbar(final Item item) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AntiCrystal.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b().equals(item)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void onEnable() {
        if (this.find_in_hotbar(Items.field_151007_F) == -1) {
            ChatUtils.warning("Must have string in hotbar!");
            this.toggle();
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (AntiCrystal.mc.field_71439_g.field_70173_aa % 2 == 0 && this.find_in_hotbar(Items.field_151007_F) != -1 && !(AntiCrystal.mc.field_71441_e.func_180495_p(AntiCrystal.mc.field_71439_g.func_180425_c()).func_177230_c() instanceof BlockTripWire)) {
            int old_slot = -1;
            old_slot = AntiCrystal.mc.field_71439_g.field_71071_by.field_70461_c;
            AntiCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.find_in_hotbar(Items.field_151007_F)));
            AntiCrystal.mc.field_71439_g.field_71071_by.field_70461_c = this.find_in_hotbar(Items.field_151007_F);
            this.Blockplace(EnumHand.MAIN_HAND, new BlockPos(AntiCrystal.mc.field_71439_g.field_70165_t, Math.ceil(AntiCrystal.mc.field_71439_g.field_70163_u), AntiCrystal.mc.field_71439_g.field_70161_v));
            AntiCrystal.mc.field_71439_g.field_71071_by.field_70461_c = old_slot;
        }
        super.onClientTick(event);
    }
    
    public void Blockplace(final EnumHand enumHand, final BlockPos blockPos) {
        final Vec3d vec3d = new Vec3d(AntiCrystal.mc.field_71439_g.field_70165_t, AntiCrystal.mc.field_71439_g.field_70163_u + AntiCrystal.mc.field_71439_g.func_70047_e(), AntiCrystal.mc.field_71439_g.field_70161_v);
        final BlockPos offset = blockPos.func_177972_a(EnumFacing.UP);
        final EnumFacing opposite = EnumFacing.UP.func_176734_d();
        final Vec3d Vec3d = new Vec3d((Vec3i)offset).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        if (vec3d.func_72436_e(Vec3d) <= 18.0625) {
            final float[] array = Utils.getNeededRotations(Vec3d, 0.0f, 0.0f);
            AntiCrystal.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(array[0], array[1], AntiCrystal.mc.field_71439_g.field_70122_E));
            AntiCrystal.mc.field_71442_b.func_187099_a(AntiCrystal.mc.field_71439_g, AntiCrystal.mc.field_71441_e, offset, opposite, Vec3d, enumHand);
            AntiCrystal.mc.field_71439_g.func_184609_a(enumHand);
        }
    }
}
