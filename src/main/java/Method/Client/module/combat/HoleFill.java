package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class HoleFill extends Module
{
    Setting hole_toggle;
    Setting hole_rotate;
    Setting hole_range;
    Setting swing;
    private final ArrayList<BlockPos> holes;
    
    public HoleFill() {
        super("HoleFill", 0, Category.COMBAT, "HoleFill");
        this.hole_toggle = Main.setmgr.add(new Setting("hole toggle", this, true));
        this.hole_rotate = Main.setmgr.add(new Setting("hole rotate", this, true));
        this.hole_range = Main.setmgr.add(new Setting("hole range", this, 4.0, 1.0, 6.0, true));
        this.swing = Main.setmgr.add(new Setting("swing", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.holes = new ArrayList<BlockPos>();
    }
    
    @Override
    public void onEnable() {
        if (this.find_in_hotbar() == -1) {
            this.toggle();
        }
        this.find_new_holes();
    }
    
    @Override
    public void onDisable() {
        this.holes.clear();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.find_in_hotbar() == -1) {
            this.toggle();
            return;
        }
        if (!this.hole_toggle.getValBoolean()) {
            this.toggle();
            return;
        }
        this.find_new_holes();
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) {
                continue;
            }
            final Utils.ValidResult result = Utils.valid(pos);
            if (result == Utils.ValidResult.Ok) {
                pos_to_fill = pos;
                break;
            }
            this.holes.remove(pos);
        }
        int old_slot = -1;
        if (this.find_in_hotbar() != HoleFill.mc.field_71439_g.field_71071_by.field_70461_c) {
            old_slot = HoleFill.mc.field_71439_g.field_71071_by.field_70461_c;
            HoleFill.mc.field_71439_g.field_71071_by.field_70461_c = this.find_in_hotbar();
        }
        if (pos_to_fill != null && Utils.placeBlock(pos_to_fill, this.hole_rotate.getValBoolean(), this.swing)) {
            this.holes.remove(pos_to_fill);
        }
        if (old_slot != -1) {
            HoleFill.mc.field_71439_g.field_71071_by.field_70461_c = old_slot;
        }
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.func_177958_n();
        final int cy = loc.func_177956_o();
        final int cz = loc.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = sphere ? (cy + r) : (cy + h);
                    if (y >= f) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(HoleFill.mc.field_71439_g.field_70165_t), Math.floor(HoleFill.mc.field_71439_g.field_70163_u), Math.floor(HoleFill.mc.field_71439_g.field_70161_v));
    }
    
    public void find_new_holes() {
        this.holes.clear();
        for (final BlockPos pos : getSphere(GetLocalPlayerPosFloored(), (float)this.hole_range.getValDouble(), (int)this.hole_range.getValDouble(), false, true, 0)) {
            if (!HoleFill.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            boolean possible = true;
            for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                final Block block = HoleFill.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                continue;
            }
            this.holes.add(pos);
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = HoleFill.mc.field_71439_g.field_71071_by.func_70301_a(i);
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
}
