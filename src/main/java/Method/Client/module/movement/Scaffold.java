package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;
import net.minecraft.block.*;
import Method.Client.utils.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;

public class Scaffold extends Module
{
    BlockPos blockDown1;
    BlockPos blockDown2;
    BlockPos blockDown3;
    Setting Towermode;
    Setting Placecolor;
    Setting TimerVal;
    Setting radius;
    Setting Sprint;
    Setting Towerspeed;
    Setting TowerDelay;
    Setting TowerFeet;
    Setting SneakPlace;
    Setting DrawMode;
    Setting LineWidth;
    private final TimerUtils timer;
    
    public Scaffold() {
        super("Scaffold", 0, Category.MOVEMENT, "Scaffolds");
        this.blockDown1 = null;
        this.blockDown2 = null;
        this.blockDown3 = null;
        this.Towermode = Main.setmgr.add(new Setting("Towermode", this, "Tower", new String[] { "Tower", "Onjump", "Timer", "ACC", "NCP", "Spartan", "TP", "Long", "None" }));
        this.Placecolor = Main.setmgr.add(new Setting("Placecolor", this, 0.0, 1.0, 1.0, 0.22));
        this.TimerVal = Main.setmgr.add(new Setting("TimerVal", this, 1.0, 0.0, 3.0, false, this.Towermode, "Timer", 8));
        this.radius = Main.setmgr.add(new Setting("Radius", this, 0.0, 0.0, 5.0, true));
        this.Sprint = Main.setmgr.add(new Setting("Sprint place", this, false));
        this.Towerspeed = Main.setmgr.add(new Setting("Towerspeed", this, 1.0, 0.0, 1.0, false, this.Towermode, "Tower", 8));
        this.TowerDelay = Main.setmgr.add(new Setting("TowerDelay", this, 100.0, 0.0, 1000.0, true, this.Towermode, "Tower", 9));
        this.TowerFeet = Main.setmgr.add(new Setting("Tower Feet Look", this, true));
        this.SneakPlace = Main.setmgr.add(new Setting("SneakPlace", this, true));
        this.DrawMode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onDisable() {
        Scaffold.mc.field_71428_T.field_194149_e = 50.0f;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        Scaffold.mc.field_71428_T.field_194149_e = 50.0f;
        super.onEnable();
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final int newSlot = this.findSlotWithBlock();
        if (newSlot != -1) {
            this.Custom(newSlot);
        }
        else {
            ChatUtils.error("No blocks found in hotbar!");
            this.toggle();
        }
        if (this.TowerFeet.getValBoolean() && !this.Towermode.getValString().equalsIgnoreCase("None")) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Rotation(Scaffold.mc.field_71439_g.field_70177_z, 90.0f, Scaffold.mc.field_71439_g.field_70122_E));
        }
        if (this.Towermode.getValString().equalsIgnoreCase("Tower") && this.timer.isDelay((long)this.TowerDelay.getValDouble()) && Scaffold.mc.field_71474_y.field_74314_A.func_151470_d()) {
            Scaffold.mc.field_71439_g.field_70181_x = -0.2800000011920929;
            final float towerMotion = 0.42f;
            Scaffold.mc.field_71439_g.func_70016_h(0.0, 0.41999998688697815 * this.Towerspeed.getValDouble(), 0.0);
            this.timer.setLastMS();
        }
        if (this.Towermode.getValString().equalsIgnoreCase("Onjump")) {
            Scaffold.mc.field_71467_ac = 0;
            if (Scaffold.mc.field_71474_y.field_74314_A.func_151470_d() && Scaffold.mc.field_71439_g.field_70181_x < 0.0) {
                final EntityPlayerSP field_71439_g = Scaffold.mc.field_71439_g;
                field_71439_g.field_70181_x *= 1.48;
                if (Scaffold.mc.field_71439_g.field_70122_E) {
                    Scaffold.mc.field_71439_g.func_70637_d(false);
                    Scaffold.mc.field_71439_g.func_70664_aZ();
                }
            }
        }
        if (this.Towermode.getValString().equalsIgnoreCase("NCP")) {
            final double blockBelow = -2.0;
            if (Scaffold.mc.field_71439_g.field_70122_E && Scaffold.mc.field_71474_y.field_74314_A.field_74513_e) {
                Scaffold.mc.field_71439_g.field_70181_x = 0.41999998688697815;
            }
            if (Scaffold.mc.field_71439_g.field_70181_x < 0.1 && !(Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u, Scaffold.mc.field_71439_g.field_70161_v).func_177963_a(0.0, blockBelow, 0.0)).func_177230_c() instanceof BlockAir)) {
                Scaffold.mc.field_71439_g.field_70181_x = -10.0;
            }
        }
        if (this.Towermode.getValString().equalsIgnoreCase("TP") && Scaffold.mc.field_71474_y.field_74314_A.func_151470_d() && Scaffold.mc.field_71439_g.field_70122_E) {
            final EntityPlayerSP field_71439_g2 = Scaffold.mc.field_71439_g;
            field_71439_g2.field_70181_x -= 0.2300000051036477;
            Scaffold.mc.field_71439_g.func_70107_b(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u + 1.1, Scaffold.mc.field_71439_g.field_70161_v);
        }
        if (this.Towermode.getValString().equalsIgnoreCase("Spartan")) {
            final double blockBelow = -2.0;
            if (Scaffold.mc.field_71439_g.field_70122_E && Scaffold.mc.field_71474_y.field_74314_A.field_74513_e) {
                Scaffold.mc.field_71439_g.field_70181_x = 0.41999998688697815;
            }
            if (Scaffold.mc.field_71439_g.field_70181_x < 0.0 && !(Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u, Scaffold.mc.field_71439_g.field_70161_v).func_177963_a(0.0, blockBelow, 0.0)).func_177230_c() instanceof BlockAir)) {
                Scaffold.mc.field_71439_g.field_70181_x = -10.0;
            }
        }
        if (this.Towermode.getValString().equalsIgnoreCase("ACC")) {
            if (Scaffold.mc.field_71439_g.field_70122_E && Scaffold.mc.field_71474_y.field_74314_A.field_74513_e) {
                Scaffold.mc.field_71439_g.field_70181_x = 0.395;
            }
            final EntityPlayerSP field_71439_g3 = Scaffold.mc.field_71439_g;
            field_71439_g3.field_70181_x -= 0.002300000051036477;
        }
        if (this.Towermode.getValString().equalsIgnoreCase("Long") && Scaffold.mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (Utils.isMoving((Entity)Scaffold.mc.field_71439_g)) {
                if (isOnGround(0.76) && !isOnGround(0.75) && Scaffold.mc.field_71439_g.field_70181_x > 0.23 && Scaffold.mc.field_71439_g.field_70181_x < 0.25) {
                    final double round = Math.round(Scaffold.mc.field_71439_g.field_70163_u);
                    Scaffold.mc.field_71439_g.field_70181_x = round - Scaffold.mc.field_71439_g.field_70163_u;
                }
                if (isOnGround(1.0E-4)) {
                    Scaffold.mc.field_71439_g.field_70181_x = 0.42;
                    final EntityPlayerSP field_71439_g4 = Scaffold.mc.field_71439_g;
                    field_71439_g4.field_70159_w *= 0.9;
                    final EntityPlayerSP field_71439_g5 = Scaffold.mc.field_71439_g;
                    field_71439_g5.field_70179_y *= 0.9;
                }
                else if (Scaffold.mc.field_71439_g.field_70163_u >= Math.round(Scaffold.mc.field_71439_g.field_70163_u) - 1.0E-4 && Scaffold.mc.field_71439_g.field_70163_u <= Math.round(Scaffold.mc.field_71439_g.field_70163_u) + 1.0E-4) {
                    Scaffold.mc.field_71439_g.field_70181_x = 0.0;
                }
            }
            else {
                Scaffold.mc.field_71439_g.field_70159_w = 0.0;
                Scaffold.mc.field_71439_g.field_70179_y = 0.0;
                Scaffold.mc.field_71439_g.field_70747_aH = 0.0f;
                final double x = Scaffold.mc.field_71439_g.field_70165_t;
                final double y = Scaffold.mc.field_71439_g.field_70163_u - 1.0;
                final double z = Scaffold.mc.field_71439_g.field_70161_v;
                final BlockPos blockBelow2 = new BlockPos(x, y, z);
                if (Scaffold.mc.field_71441_e.func_180495_p(blockBelow2).func_177230_c() == Blocks.field_150350_a) {
                    Scaffold.mc.field_71439_g.field_70181_x = 0.4196;
                    final EntityPlayerSP field_71439_g6 = Scaffold.mc.field_71439_g;
                    field_71439_g6.field_70159_w *= 0.75;
                    final EntityPlayerSP field_71439_g7 = Scaffold.mc.field_71439_g;
                    field_71439_g7.field_70179_y *= 0.75;
                }
            }
        }
        if (this.Towermode.getValString().equalsIgnoreCase("Timer")) {
            if (!Scaffold.mc.field_71439_g.field_70122_E) {
                Scaffold.mc.field_71428_T.field_194149_e = (float)(50.0 / this.TimerVal.getValDouble());
            }
            Scaffold.mc.field_71467_ac = 0;
            if (Scaffold.mc.field_71439_g.field_70122_E) {
                Scaffold.mc.field_71439_g.field_70181_x = 0.3932;
                Scaffold.mc.field_71428_T.field_194149_e = 50.0f;
            }
        }
    }
    
    public static boolean isOnGround(final double height) {
        return !Scaffold.mc.field_71441_e.func_184144_a((Entity)Scaffold.mc.field_71439_g, Scaffold.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -height, 0.0)).isEmpty();
    }
    
    public void XZmodify(final double x, final double z) {
        Scaffold.mc.field_71439_g.field_70159_w = x;
        Scaffold.mc.field_71439_g.field_70179_y = z;
    }
    
    private void Custom(final int NewSlot) {
        final int StartingItem = Scaffold.mc.field_71439_g.field_71071_by.field_70461_c;
        Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = NewSlot;
        if (Scaffold.mc.field_71474_y.field_151444_V.func_151470_d() && this.Sprint.getValBoolean()) {
            final float X = MathHelper.func_76126_a((float)Math.toRadians(Scaffold.mc.field_71439_g.field_70177_z)) * 0.03f;
            final float Z = MathHelper.func_76134_b((float)Math.toRadians(Scaffold.mc.field_71439_g.field_70177_z)) * 0.03f;
            if (Scaffold.mc.field_71474_y.field_74351_w.func_151470_d()) {
                this.XZmodify(-X, Z);
            }
            if (Scaffold.mc.field_71474_y.field_74368_y.func_151470_d()) {
                this.XZmodify(X, -Z);
            }
            if (Scaffold.mc.field_71474_y.field_74370_x.func_151470_d()) {
                this.XZmodify(X, Z);
            }
            if (Scaffold.mc.field_71474_y.field_74366_z.func_151470_d()) {
                this.XZmodify(-X, -Z);
            }
            this.blockDown1 = new BlockPos((Entity)Scaffold.mc.field_71439_g).func_177979_c(2);
            if (Scaffold.mc.field_71441_e.func_180495_p(this.blockDown1).func_185904_a().func_76222_j()) {
                this.Blockplace(EnumHand.MAIN_HAND, this.blockDown1);
            }
            if (Math.abs(Scaffold.mc.field_71439_g.field_70159_w) > 0.03 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(this.blockDown1.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)(this.blockDown1.func_177956_o() - 1), (double)this.blockDown1.func_177952_p())).func_185904_a().func_76222_j()) {
                this.Blockplace(EnumHand.MAIN_HAND, new BlockPos(this.blockDown1.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)(this.blockDown1.func_177956_o() - 1), (double)this.blockDown1.func_177952_p()));
            }
            else if (Math.abs(Scaffold.mc.field_71439_g.field_70179_y) > 0.03 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos((double)this.blockDown1.func_177958_n(), (double)(this.blockDown1.func_177956_o() - 1), this.blockDown1.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y))).func_185904_a().func_76222_j()) {
                this.Blockplace(EnumHand.MAIN_HAND, new BlockPos((double)this.blockDown1.func_177958_n(), (double)(this.blockDown1.func_177956_o() - 1), this.blockDown1.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y)));
            }
            return;
        }
        if (this.radius.getValDouble() == 0.0) {
            this.blockDown2 = new BlockPos((Entity)Scaffold.mc.field_71439_g).func_177977_b();
            if (Scaffold.mc.field_71441_e.func_180495_p(this.blockDown2).func_185904_a().func_76222_j() && !Scaffold.mc.field_71439_g.func_174813_aQ().func_72326_a(new AxisAlignedBB(this.blockDown2).func_72321_a(0.05, 0.05, 0.05))) {
                this.Blockplace(EnumHand.MAIN_HAND, this.blockDown2);
            }
            if (Math.abs(Scaffold.mc.field_71439_g.field_70159_w) > 0.033 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(this.blockDown2.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)this.blockDown2.func_177956_o(), (double)this.blockDown2.func_177952_p())).func_185904_a().func_76222_j()) {
                this.Blockplace(EnumHand.MAIN_HAND, new BlockPos(this.blockDown2.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)this.blockDown2.func_177956_o(), (double)this.blockDown2.func_177952_p()));
            }
            else if (Math.abs(Scaffold.mc.field_71439_g.field_70179_y) > 0.033 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos((double)this.blockDown2.func_177958_n(), (double)this.blockDown2.func_177956_o(), this.blockDown2.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y))).func_185904_a().func_76222_j()) {
                this.Blockplace(EnumHand.MAIN_HAND, new BlockPos((double)this.blockDown2.func_177958_n(), (double)this.blockDown2.func_177956_o(), this.blockDown2.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y)));
            }
            return;
        }
        final ArrayList<BlockPos> WidePlace = new ArrayList<BlockPos>();
        for (int i = (int)(-this.radius.getValDouble()); i <= this.radius.getValDouble(); ++i) {
            for (int j = (int)(-this.radius.getValDouble()); j <= this.radius.getValDouble(); ++j) {
                WidePlace.add(new BlockPos(Scaffold.mc.field_71439_g.field_70165_t + i, Scaffold.mc.field_71439_g.field_70163_u - 1.0, Scaffold.mc.field_71439_g.field_70161_v + j));
            }
        }
        for (final BlockPos blockPos3 : WidePlace) {
            if (Scaffold.mc.field_71441_e.func_180495_p(blockPos3).func_185904_a().func_76222_j()) {
                this.blockDown3 = blockPos3;
                this.Blockplace(EnumHand.MAIN_HAND, blockPos3);
            }
        }
        Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = StartingItem;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        try {
            if (this.blockDown1 != null) {
                RenderUtils.RenderBlock(this.DrawMode.getValString(), RenderUtils.Standardbb(this.blockDown1), this.Placecolor.getcolor(), this.LineWidth.getValDouble());
            }
            if (this.blockDown2 != null && this.radius.getValDouble() == 0.0) {
                RenderUtils.RenderBlock(this.DrawMode.getValString(), RenderUtils.Standardbb(this.blockDown2), this.Placecolor.getcolor(), this.LineWidth.getValDouble());
            }
            if (this.blockDown3 != null) {
                RenderUtils.RenderBlock(this.DrawMode.getValString(), RenderUtils.Standardbb(this.blockDown3), this.Placecolor.getcolor(), this.LineWidth.getValDouble());
            }
        }
        catch (Exception ex) {}
    }
    
    public int findSlotWithBlock() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() instanceof ItemBlock) {
                final Block block = Block.func_149634_a(stack.func_77973_b()).func_176223_P().func_177230_c();
                if (block.func_149730_j(BlockUtils.getBlock(new BlockPos((Entity)Scaffold.mc.field_71439_g).func_177977_b()).func_176223_P()) && block != Blocks.field_150354_m && block != Blocks.field_150351_n) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void Blockplace(final EnumHand enumHand, final BlockPos blockPos) {
        final Vec3d vec3d = new Vec3d(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u + Scaffold.mc.field_71439_g.func_70047_e(), Scaffold.mc.field_71439_g.field_70161_v);
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos offset = blockPos.func_177972_a(enumFacing);
            final EnumFacing opposite = enumFacing.func_176734_d();
            if (Utils.canBeClicked(offset)) {
                final Vec3d Vec3d = new Vec3d((Vec3i)offset).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
                if (vec3d.func_72436_e(Vec3d) <= 18.0625) {
                    final float[] array = Utils.getNeededRotations(Vec3d, 0.0f, 0.0f);
                    Scaffold.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(array[0], array[1], Scaffold.mc.field_71439_g.field_70122_E));
                    if (this.SneakPlace.getValBoolean()) {
                        Scaffold.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Scaffold.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    Scaffold.mc.field_71442_b.func_187099_a(Scaffold.mc.field_71439_g, Scaffold.mc.field_71441_e, offset, opposite, Vec3d, enumHand);
                    Scaffold.mc.field_71439_g.func_184609_a(enumHand);
                    if (this.SneakPlace.getValBoolean()) {
                        Scaffold.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Scaffold.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return;
                }
            }
        }
    }
}
