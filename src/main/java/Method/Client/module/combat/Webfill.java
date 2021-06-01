package Method.Client.module.combat;

import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import Method.Client.managers.*;
import Method.Client.utils.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class Webfill extends Module
{
    Setting always_on;
    Setting rotate;
    Setting range;
    int new_slot;
    boolean sneak;
    
    public Webfill() {
        super("Webfill", 0, Category.COMBAT, "Webfill");
        this.always_on = Main.setmgr.add(new Setting("hole toggle", this, true));
        this.rotate = Main.setmgr.add(new Setting("hole rotate", this, true));
        this.range = Main.setmgr.add(new Setting("range", this, 4.0, 1.0, 6.0, true));
        this.new_slot = -1;
        this.sneak = false;
    }
    
    @Override
    public void onEnable() {
        if (Webfill.mc.field_71439_g != null) {
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                this.toggle();
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (Webfill.mc.field_71439_g != null && this.sneak) {
            Webfill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Webfill.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneak = false;
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Webfill.mc.field_71439_g == null) {
            return;
        }
        if (this.always_on.getValBoolean()) {
            final EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (Webfill.mc.field_71439_g.func_70032_d((Entity)target) < this.range.getValDouble() && this.is_surround()) {
                final int last_slot = Webfill.mc.field_71439_g.field_71071_by.field_70461_c;
                Webfill.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
                Webfill.mc.field_71442_b.func_78765_e();
                this.place_blocks(HoleFill.GetLocalPlayerPosFloored());
                Webfill.mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            }
        }
        else {
            final int last_slot2 = Webfill.mc.field_71439_g.field_71071_by.field_70461_c;
            Webfill.mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
            Webfill.mc.field_71442_b.func_78765_e();
            this.place_blocks(HoleFill.GetLocalPlayerPosFloored());
            Webfill.mc.field_71439_g.field_71071_by.field_70461_c = last_slot2;
            this.toggle();
        }
    }
    
    public EntityPlayer find_closest_target() {
        if (Webfill.mc.field_71441_e.field_73010_i.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : Webfill.mc.field_71441_e.field_73010_i) {
            if (target == Webfill.mc.field_71439_g) {
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
            if (closestTarget != null && Webfill.mc.field_71439_g.func_70032_d((Entity)target) > Webfill.mc.field_71439_g.func_70032_d((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Webfill.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == Item.func_150899_d(30)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = HoleFill.GetLocalPlayerPosFloored();
        return Webfill.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && Webfill.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && Webfill.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && Webfill.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a && Webfill.mc.field_71441_e.func_180495_p(player_block).func_177230_c() == Blocks.field_150350_a;
    }
    
    private void place_blocks(final BlockPos pos) {
        if (!Webfill.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (!Utils.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (Utils.canBeClicked(neighbor)) {
                if (Surrond.blackList.contains(Webfill.mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                    Webfill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Webfill.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (this.rotate.getValBoolean()) {
                    Utils.faceVectorPacketInstant(hitVec);
                }
                Webfill.mc.field_71442_b.func_187099_a(Webfill.mc.field_71439_g, Webfill.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                Webfill.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                return;
            }
        }
    }
}
