package Method.Client.utils;

import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import Method.Client.utils.system.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.network.*;
import java.util.*;

public final class BlockUtils
{
    private static final Minecraft mc;
    public static LinkedList<BlockPos> blocks;
    
    public static Material getMaterial(final BlockPos pos) {
        return getState(pos).func_185904_a();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtils.mc.field_71441_e.func_180495_p(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).func_177230_c();
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).func_176209_a(getState(pos), false);
    }
    
    public static float getHardness(final BlockPos pos) {
        return getState(pos).func_185903_a((EntityPlayer)Wrapper.INSTANCE.player(), (World)Wrapper.INSTANCE.world(), pos);
    }
    
    public static boolean breakBlockSimple(final BlockPos pos) {
        EnumFacing side = null;
        final EnumFacing[] sides = EnumFacing.values();
        final Vec3d eyesPos = Utils.getEyesPos();
        final Vec3d relCenter = getState(pos).func_185900_c((IBlockAccess)Wrapper.INSTANCE.world(), pos).func_189972_c();
        final Vec3d center = new Vec3d((Vec3i)pos).func_178787_e(relCenter);
        final Vec3d[] hitVecs = new Vec3d[sides.length];
        for (int i = 0; i < sides.length; ++i) {
            final Vec3i dirVec = sides[i].func_176730_m();
            final Vec3d relHitVec = new Vec3d(relCenter.field_72450_a * dirVec.func_177958_n(), relCenter.field_72448_b * dirVec.func_177956_o(), relCenter.field_72449_c * dirVec.func_177952_p());
            hitVecs[i] = center.func_178787_e(relHitVec);
        }
        for (int i = 0; i < sides.length; ++i) {
            if (Wrapper.INSTANCE.world().func_147447_a(eyesPos, hitVecs[i], false, true, false) == null) {
                side = sides[i];
                break;
            }
        }
        if (side == null) {
            final double distanceSqToCenter = eyesPos.func_72436_e(center);
            for (int j = 0; j < sides.length; ++j) {
                if (eyesPos.func_72436_e(hitVecs[j]) < distanceSqToCenter) {
                    side = sides[j];
                    break;
                }
            }
        }
        if (side == null) {
            side = sides[0];
        }
        Utils.faceVectorPacket(hitVecs[side.ordinal()]);
        if (!Wrapper.INSTANCE.controller().func_180512_c(pos, side)) {
            return false;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        return true;
    }
    
    public static void breakBlocksPacketSpam(final Iterable<BlockPos> blocks) {
        final Vec3d eyesPos = Utils.getEyesPos();
        final NetHandlerPlayClient connection = Wrapper.INSTANCE.player().field_71174_a;
        for (final BlockPos pos : blocks) {
            final Vec3d posVec = new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5);
            final double distanceSqPosVec = eyesPos.func_72436_e(posVec);
            for (final EnumFacing side : EnumFacing.values()) {
                final Vec3d hitVec = posVec.func_178787_e(new Vec3d(side.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) < distanceSqPosVec) {
                    connection.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                    connection.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                    break;
                }
            }
        }
    }
    
    static {
        mc = Wrapper.INSTANCE.mc();
        BlockUtils.blocks = new LinkedList<BlockPos>();
    }
}
