package Method.Client.utils;

import java.lang.reflect.*;
import net.minecraft.block.*;
import Method.Client.utils.system.*;
import net.minecraft.entity.item.*;
import Method.Client.managers.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Utils
{
    public static Field pressed;
    private static Field modifiersField;
    public static float[] rotationsToBlock;
    private static final Random RANDOM;
    public static List<Block> emptyBlocks;
    public static List<Block> rightclickableBlocks;
    
    public static double[] directionSpeed(final double speed) {
        float forward = Wrapper.INSTANCE.mc().field_71439_g.field_71158_b.field_192832_b;
        float side = Wrapper.INSTANCE.mc().field_71439_g.field_71158_b.field_78902_a;
        float yaw = Wrapper.INSTANCE.mc().field_71439_g.field_70126_B + (Wrapper.INSTANCE.mc().field_71439_g.field_70177_z - Wrapper.INSTANCE.mc().field_71439_g.field_70126_B) * Wrapper.INSTANCE.mc().func_184121_ak();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static float[] getNeededRotations(final Vec3d vec, final float yaw, final float pitch) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        final double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        final double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float rotationYaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float rotationPitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { updateRotation(Wrapper.INSTANCE.player().field_70177_z, rotationYaw, yaw / 4.0f), updateRotation(Wrapper.INSTANCE.player().field_70125_A, rotationPitch, pitch / 4.0f) };
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.field_72450_a - from.field_72450_a;
        final double difY = (to.field_72448_b - from.field_72448_b) * -1.0;
        final double difZ = to.field_72449_c - from.field_72449_c;
        final double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float updateRotation(final float PlayerRotation, final float Modified, final float MaxValueAccepted) {
        float degrees = MathHelper.func_76142_g(Modified - PlayerRotation);
        if (MaxValueAccepted != 0.0f) {
            if (degrees > MaxValueAccepted) {
                degrees = MaxValueAccepted;
            }
            if (degrees < -MaxValueAccepted) {
                degrees = -MaxValueAccepted;
            }
        }
        return PlayerRotation + degrees;
    }
    
    public static boolean isBlockEmpty(final BlockPos pos) {
        try {
            if (Utils.emptyBlocks.contains(Wrapper.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
                final AxisAlignedBB box = new AxisAlignedBB(pos);
                for (final Entity entity : Wrapper.mc.field_71441_e.field_72996_f) {
                    if (entity instanceof EntityLivingBase || box.func_72326_a(entity.func_174813_aQ())) {
                        return true;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static boolean trytoplace(final BlockPos target_pos) {
        boolean should_try_place = true;
        if (!Wrapper.mc.field_71441_e.func_180495_p(target_pos).func_185904_a().func_76222_j()) {
            should_try_place = false;
        }
        for (final Entity entity : Wrapper.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(target_pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                should_try_place = false;
                break;
            }
        }
        return should_try_place;
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time);
    }
    
    public static boolean placeBlock(final BlockPos pos, final boolean rotate, final Setting s) {
        if (isBlockEmpty(pos)) {
            final EnumFacing[] values;
            final EnumFacing[] facings = values = EnumFacing.values();
            for (final EnumFacing f : values) {
                final Block neighborBlock = Wrapper.mc.field_71441_e.func_180495_p(pos.func_177972_a(f)).func_177230_c();
                final Vec3d vec = new Vec3d(pos.func_177958_n() + 0.5 + f.func_82601_c() * 0.5, pos.func_177956_o() + 0.5 + f.func_96559_d() * 0.5, pos.func_177952_p() + 0.5 + f.func_82599_e() * 0.5);
                if (!Utils.emptyBlocks.contains(neighborBlock) && Wrapper.mc.field_71439_g.func_174824_e(Wrapper.mc.func_184121_ak()).func_72438_d(vec) <= 4.25) {
                    final float[] rot = { Wrapper.mc.field_71439_g.field_70177_z, Wrapper.mc.field_71439_g.field_70125_A };
                    if (rotate) {
                        final float[] array = getNeededRotations(vec, 0.0f, 0.0f);
                        Wrapper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(array[0], array[1], Wrapper.mc.field_71439_g.field_70122_E));
                    }
                    if (Utils.rightclickableBlocks.contains(neighborBlock)) {
                        Wrapper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Wrapper.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    Wrapper.mc.field_71442_b.func_187099_a(Wrapper.mc.field_71439_g, Wrapper.mc.field_71441_e, pos.func_177972_a(f), f.func_176734_d(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                    if (Utils.rightclickableBlocks.contains(neighborBlock)) {
                        Wrapper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Wrapper.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    if (rotate) {
                        Wrapper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], Wrapper.mc.field_71439_g.field_70122_E));
                    }
                    if (s.getValString().equalsIgnoreCase("Mainhand") || s.getValString().equalsIgnoreCase("Both")) {
                        Wrapper.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                    }
                    if (s.getValString().equalsIgnoreCase("Offhand") || s.getValString().equalsIgnoreCase("Both")) {
                        Wrapper.mc.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static ValidResult valid(final BlockPos pos) {
        if (!Wrapper.mc.field_71441_e.func_72855_b(new AxisAlignedBB(pos))) {
            return ValidResult.NoEntityCollision;
        }
        if (!checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        final IBlockState blockState = Wrapper.mc.field_71441_e.func_180495_p(pos);
        if (blockState.func_177230_c() == Blocks.field_150350_a) {
            final BlockPos[] array;
            final BlockPos[] l_Blocks = array = new BlockPos[] { pos.func_177978_c(), pos.func_177968_d(), pos.func_177974_f(), pos.func_177976_e(), pos.func_177984_a(), pos.func_177977_b() };
            for (final BlockPos l_Pos : array) {
                final IBlockState l_State2 = Wrapper.mc.field_71441_e.func_180495_p(l_Pos);
                if (l_State2.func_177230_c() != Blocks.field_150350_a) {
                    for (final EnumFacing side : EnumFacing.values()) {
                        final BlockPos neighbor = pos.func_177972_a(side);
                        if (Wrapper.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(Wrapper.mc.field_71441_e.func_180495_p(neighbor), false)) {
                            return ValidResult.Ok;
                        }
                    }
                }
            }
            return ValidResult.NoNeighbors;
        }
        return ValidResult.AlreadyBlockThere;
    }
    
    public static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.func_177972_a(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.func_177972_a(side);
            if (!Wrapper.mc.field_71441_e.func_180495_p(neighbour).func_185904_a().func_76222_j()) {
                return true;
            }
        }
        return false;
    }
    
    public static int random(final int min, final int max) {
        return Utils.RANDOM.nextInt(max - min) + min;
    }
    
    public static void faceVectorPacket(final Vec3d vec) {
        final float[] rotations = getNeededRotations(vec, 0.0f, 0.0f);
        final EntityPlayerSP pl = Minecraft.func_71410_x().field_71439_g;
        final float preYaw = pl.field_70177_z;
        final float prePitch = pl.field_70125_A;
        pl.field_70177_z = rotations[0];
        pl.field_70125_A = rotations[1];
        pl.func_175161_p();
        pl.field_70177_z = preYaw;
        pl.field_70125_A = prePitch;
    }
    
    public static boolean isMoving(final Entity e) {
        return e.field_70159_w != 0.0 && e.field_70179_y != 0.0 && e.field_70181_x != 0.0;
    }
    
    public static boolean isMovinginput() {
        return Wrapper.INSTANCE.mc().field_71439_g.field_71158_b.field_192832_b != 0.0f || Wrapper.INSTANCE.mc().field_71439_g.field_71158_b.field_78902_a != 0.0f;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return Wrapper.INSTANCE.world().func_180495_p(pos).func_177230_c().func_176209_a(Wrapper.INSTANCE.world().func_180495_p(pos), false);
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).func_70919_bu()) && (e instanceof EntityAgeable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).func_70643_av() == null));
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.INSTANCE.player().field_70165_t, Wrapper.INSTANCE.player().field_70163_u + Wrapper.INSTANCE.player().func_70047_e(), Wrapper.INSTANCE.player().field_70161_v);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        Utils.rotationsToBlock = getNeededRotations(vec, 0.0f, 0.0f);
    }
    
    public static void teleportToPosition(final double[] startPosition, final double[] endPosition, final double setOffset, final double slack, final boolean extendOffset, final boolean onGround) {
        boolean wasSneaking = false;
        if (Wrapper.INSTANCE.player().func_70093_af()) {
            wasSneaking = true;
        }
        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];
        final double endX = endPosition[0];
        final double endY = endPosition[1];
        final double endZ = endPosition[2];
        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
            if (count > 120) {
                break;
            }
            final double offset = (extendOffset && (count & 0x1) == 0x0) ? (setOffset + 0.15) : setOffset;
            final double diffX = startX - endX;
            final double diffY = startY - endY;
            final double diffZ = startZ - endZ;
            final double min = Math.min(Math.abs(diffX), offset);
            if (diffX < 0.0) {
                startX += min;
            }
            if (diffX > 0.0) {
                startX -= min;
            }
            final double min2 = Math.min(Math.abs(diffY), offset);
            if (diffY < 0.0) {
                startY += min2;
            }
            if (diffY > 0.0) {
                startY -= min2;
            }
            final double min3 = Math.min(Math.abs(diffZ), offset);
            if (diffZ < 0.0) {
                startZ += min3;
            }
            if (diffZ > 0.0) {
                startZ -= min3;
            }
            if (wasSneaking) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SNEAKING));
            }
            Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_147298_b().func_179290_a((Packet)new CPacketPlayer.Position(startX, startY, startZ, onGround));
            ++count;
        }
        if (wasSneaking) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SNEAKING));
        }
    }
    
    public static boolean isBlockMaterial(final BlockPos blockPos, final Block block) {
        return Wrapper.INSTANCE.world().func_180495_p(blockPos).func_177230_c() == Blocks.field_150350_a;
    }
    
    public static String getPlayerName(final EntityPlayer player) {
        player.func_146103_bH();
        return player.func_146103_bH().getName();
    }
    
    public static String getEntityNameColor(final EntityLivingBase entity) {
        final String name = entity.func_145748_c_().func_150254_d();
        if (name.contains("§")) {
            if (name.contains("§1")) {
                return "§1";
            }
            if (name.contains("§2")) {
                return "§2";
            }
            if (name.contains("§3")) {
                return "§3";
            }
            if (name.contains("§4")) {
                return "§4";
            }
            if (name.contains("§5")) {
                return "§5";
            }
            if (name.contains("§6")) {
                return "§6";
            }
            if (name.contains("§7")) {
                return "§7";
            }
            if (name.contains("§8")) {
                return "§8";
            }
            if (name.contains("§9")) {
                return "§9";
            }
            if (name.contains("§0")) {
                return "§0";
            }
            if (name.contains("§e")) {
                return "§e";
            }
            if (name.contains("§d")) {
                return "§d";
            }
            if (name.contains("§a")) {
                return "§a";
            }
            if (name.contains("§b")) {
                return "§b";
            }
            if (name.contains("§c")) {
                return "§c";
            }
            if (name.contains("§f")) {
                return "§f";
            }
        }
        return "null";
    }
    
    public static boolean checkScreen() {
        return !(Wrapper.INSTANCE.mc().field_71462_r instanceof GuiContainer) && !(Wrapper.INSTANCE.mc().field_71462_r instanceof GuiChat) && Wrapper.INSTANCE.mc().field_71462_r == null;
    }
    
    public static float getPitch(final Entity entity) {
        double y = entity.field_70163_u - Wrapper.INSTANCE.player().field_70163_u;
        y /= Wrapper.INSTANCE.player().func_70032_d(entity);
        double pitch = Math.asin(y) * 57.29577951308232;
        pitch = -pitch;
        return (float)pitch;
    }
    
    public static float getYaw(final Entity entity) {
        final double x = entity.field_70165_t - Wrapper.INSTANCE.player().field_70165_t;
        final double z = entity.field_70161_v - Wrapper.INSTANCE.player().field_70161_v;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        return (float)yaw;
    }
    
    public static float getDirection() {
        float var1 = Wrapper.INSTANCE.player().field_70177_z;
        if (Wrapper.INSTANCE.player().field_191988_bg < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Wrapper.INSTANCE.player().field_191988_bg < 0.0f) {
            forward = -0.5f;
        }
        else if (Wrapper.INSTANCE.player().field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Wrapper.INSTANCE.player().field_70702_br > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Wrapper.INSTANCE.player().field_70702_br < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
    
    public static int getDistanceFromMouse(final EntityLivingBase entity) {
        final float[] neededRotations = getNeededRotations(entity.func_174791_d(), 0.0f, 0.0f);
        final float neededYaw = Wrapper.INSTANCE.player().field_70177_z - neededRotations[0];
        final float neededPitch = Wrapper.INSTANCE.player().field_70125_A - neededRotations[1];
        final float distanceFromMouse = MathHelper.func_76129_c(neededYaw * neededYaw + neededPitch * neededPitch * 2.0f);
        return (int)distanceFromMouse;
    }
    
    static {
        Utils.rotationsToBlock = null;
        RANDOM = new Random();
        Utils.emptyBlocks = Arrays.asList(Blocks.field_150350_a, Blocks.field_150356_k, Blocks.field_150353_l, Blocks.field_150358_i, Blocks.field_150355_j, Blocks.field_150395_bd, Blocks.field_150431_aC, Blocks.field_150329_H, Blocks.field_150480_ab);
        Utils.rightclickableBlocks = Arrays.asList(Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150477_bB, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA, Blocks.field_150467_bQ, Blocks.field_150471_bO, Blocks.field_150430_aB, Blocks.field_150441_bU, Blocks.field_150413_aR, Blocks.field_150416_aS, Blocks.field_150455_bV, Blocks.field_180390_bo, Blocks.field_180391_bp, Blocks.field_180392_bq, Blocks.field_180386_br, Blocks.field_180385_bs, Blocks.field_180387_bt, Blocks.field_150382_bo, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150421_aI, Blocks.field_150461_bJ, Blocks.field_150324_C, Blocks.field_150460_al, Blocks.field_180413_ao, Blocks.field_180414_ap, Blocks.field_180412_aq, Blocks.field_180411_ar, Blocks.field_180410_as, Blocks.field_180409_at, Blocks.field_150414_aQ, Blocks.field_150381_bn, Blocks.field_150380_bt, Blocks.field_150438_bZ, Blocks.field_185776_dc, Blocks.field_150483_bI, Blocks.field_185777_dd, Blocks.field_150462_ai);
        try {
            (Utils.modifiersField = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (Exception ex) {}
    }
    
    public enum ValidResult
    {
        NoEntityCollision, 
        AlreadyBlockThere, 
        NoNeighbors, 
        Ok;
    }
}
