package Method.Client.module.combat;

import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import Method.Client.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import Method.Client.managers.*;
import net.minecraft.entity.*;
import java.util.*;

public class AutoTrap extends Module
{
    public Setting place_mode;
    public Setting blocks_per_tick;
    public Setting rotate;
    public Setting chad_mode;
    public Setting range;
    public Setting Hand;
    private String last_tick_target_name;
    private int offset_step;
    private int timeout_ticker;
    private int y_level;
    private boolean first_run;
    private final Vec3d[] offsets_default;
    private final Vec3d[] offsets_face;
    private final Vec3d[] offsets_feet;
    private final Vec3d[] offsets_extra;
    
    public AutoTrap() {
        super("AutoTrap", 0, Category.COMBAT, "AutoTrap");
        this.place_mode = Main.setmgr.add(new Setting("cage", this, "Extra", new String[] { "Extra", "Face", "Normal", "Feet" }));
        this.blocks_per_tick = Main.setmgr.add(new Setting("blocks Per Tick", this, 4.0, 0.0, 8.0, true));
        this.rotate = Main.setmgr.add(new Setting("rotate", this, false));
        this.chad_mode = Main.setmgr.add(new Setting("Modify mode", this, false));
        this.range = Main.setmgr.add(new Setting("range", this, 5.5, 3.5, 10.0, false));
        this.Hand = Main.setmgr.add(new Setting("Hand", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.last_tick_target_name = "";
        this.offset_step = 0;
        this.timeout_ticker = 0;
        this.first_run = true;
        this.offsets_default = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_face = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_feet = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_extra = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0) };
    }
    
    @Override
    public void onEnable() {
        this.timeout_ticker = 0;
        this.y_level = (int)Math.round(AutoTrap.mc.field_71439_g.field_70163_u);
        this.first_run = true;
        if (this.find_obi_in_hotbar() == -1) {
            this.toggle();
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final int timeout_ticks = 20;
        if (this.timeout_ticker > timeout_ticks && this.chad_mode.getValBoolean()) {
            this.timeout_ticker = 0;
            this.toggle();
            return;
        }
        final EntityPlayer closest_target = this.find_closest_target();
        if (closest_target == null) {
            this.toggle();
            return;
        }
        if (this.chad_mode.getValBoolean() && (int)Math.round(AutoTrap.mc.field_71439_g.field_70163_u) != this.y_level) {
            this.toggle();
            return;
        }
        if (this.first_run) {
            this.first_run = false;
            this.last_tick_target_name = closest_target.func_70005_c_();
        }
        else if (!this.last_tick_target_name.equals(closest_target.func_70005_c_())) {
            this.last_tick_target_name = closest_target.func_70005_c_();
            this.offset_step = 0;
        }
        final List<Vec3d> place_targets = new ArrayList<Vec3d>();
        if (this.place_mode.getValString().equalsIgnoreCase("Normal")) {
            Collections.addAll(place_targets, this.offsets_default);
        }
        else if (this.place_mode.getValString().equalsIgnoreCase("Extra")) {
            Collections.addAll(place_targets, this.offsets_extra);
        }
        else if (this.place_mode.getValString().equalsIgnoreCase("Feet")) {
            Collections.addAll(place_targets, this.offsets_feet);
        }
        else {
            Collections.addAll(place_targets, this.offsets_face);
        }
        int blocks_placed = 0;
        while (blocks_placed < this.blocks_per_tick.getValDouble()) {
            if (this.offset_step >= place_targets.size()) {
                this.offset_step = 0;
                break;
            }
            final BlockPos offset_pos = new BlockPos((Vec3d)place_targets.get(this.offset_step));
            final BlockPos target_pos = new BlockPos(closest_target.func_174791_d()).func_177977_b().func_177982_a(offset_pos.func_177958_n(), offset_pos.func_177956_o(), offset_pos.func_177952_p());
            int old_slot = -1;
            if (this.find_obi_in_hotbar() != AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c) {
                old_slot = AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c;
                AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = this.find_obi_in_hotbar();
            }
            if (Utils.trytoplace(target_pos) && Utils.placeBlock(target_pos, this.rotate.getValBoolean(), this.Hand)) {
                ++blocks_placed;
            }
            if (old_slot != -1) {
                AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = old_slot;
            }
            ++this.offset_step;
        }
        ++this.timeout_ticker;
    }
    
    private int find_obi_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTrap.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public EntityPlayer find_closest_target() {
        if (AutoTrap.mc.field_71441_e.field_73010_i.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : AutoTrap.mc.field_71441_e.field_73010_i) {
            if (target == AutoTrap.mc.field_71439_g) {
                continue;
            }
            if (FriendManager.isFriend(target.func_70005_c_())) {
                continue;
            }
            if (!Utils.isLiving((Entity)target)) {
                continue;
            }
            if (target.func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && AutoTrap.mc.field_71439_g.func_70032_d((Entity)target) > AutoTrap.mc.field_71439_g.func_70032_d((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
}
