package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.function.*;
import Method.Client.utils.system.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class Nowall extends Module
{
    Setting Storage;
    Setting Mine;
    private boolean clicked;
    private boolean focus;
    
    public Nowall() {
        super("Nowall", 0, Category.PLAYER, "Click through walls");
        this.Storage = Main.setmgr.add(new Setting("Storage", this, true));
        this.Mine = Main.setmgr.add(new Setting("Mine", this, false));
        this.focus = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.Mine.getValBoolean()) {
            Nowall.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> Nowall.mc.field_71439_g == entity).map(entity -> entity).filter(entity -> !entity.field_70128_L).forEach(this::process);
            final RayTraceResult normal_result = Nowall.mc.field_71476_x;
            if (normal_result != null) {
                this.focus = (normal_result.field_72313_a == RayTraceResult.Type.ENTITY);
            }
        }
    }
    
    private void process(final EntityLivingBase event) {
        final RayTraceResult bypass_entity_result = event.func_174822_a(6.0, Nowall.mc.func_184121_ak());
        if (bypass_entity_result != null && this.focus && bypass_entity_result.field_72313_a == RayTraceResult.Type.BLOCK) {
            final BlockPos block_pos = bypass_entity_result.func_178782_a();
            if (Nowall.mc.field_71474_y.field_74312_F.func_151470_d()) {
                Nowall.mc.field_71442_b.func_180512_c(block_pos, EnumFacing.UP);
            }
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.Storage.getValBoolean() && packet instanceof CPacketPlayerTryUseItemOnBlock) {
            if (this.clicked) {
                this.clicked = false;
                return true;
            }
            final CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)packet;
            if (Nowall.mc.field_71462_r == null) {
                final Block block = Nowall.mc.field_71441_e.func_180495_p(packet2.func_187023_a()).func_177230_c();
                final BlockPos usable = this.findUsableBlock(packet2.func_187022_c(), packet2.func_187024_b(), packet2.func_187026_d(), packet2.func_187025_e(), packet2.func_187020_f());
                if (block.func_180639_a((World)Nowall.mc.field_71441_e, packet2.func_187023_a(), Nowall.mc.field_71441_e.func_180495_p(packet2.func_187023_a()), (EntityPlayer)Nowall.mc.field_71439_g, packet2.func_187022_c(), packet2.func_187024_b(), packet2.func_187026_d(), packet2.func_187025_e(), packet2.func_187020_f())) {
                    return true;
                }
                if (usable != null) {
                    Nowall.mc.field_71439_g.func_184609_a(packet2.func_187022_c());
                    Nowall.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(usable, packet2.func_187024_b(), packet2.func_187022_c(), packet2.func_187026_d(), packet2.func_187025_e(), packet2.func_187020_f()));
                    this.clicked = true;
                }
                else {
                    final Entity usableEntity = this.findUsableEntity();
                    if (usableEntity != null) {
                        Nowall.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(usableEntity, packet2.func_187022_c()));
                        this.clicked = true;
                    }
                }
            }
        }
        return true;
    }
    
    private Entity findUsableEntity() {
        Entity entity = null;
        for (int i = 0; i <= Nowall.mc.field_71442_b.func_78757_d(); ++i) {
            final AxisAlignedBB bb = this.traceToBlock(i, Nowall.mc.func_184121_ak());
            float maxDist = Nowall.mc.field_71442_b.func_78757_d();
            for (final Entity e : Nowall.mc.field_71441_e.func_72839_b((Entity)Nowall.mc.field_71439_g, bb)) {
                final float currentDist = Nowall.mc.field_71439_g.func_70032_d(e);
                if (currentDist <= maxDist) {
                    entity = e;
                    maxDist = currentDist;
                }
            }
        }
        return entity;
    }
    
    private BlockPos findUsableBlock(final EnumHand hand, final EnumFacing dir, final float x, final float y, final float z) {
        for (int i = 0; i <= Nowall.mc.field_71442_b.func_78757_d(); ++i) {
            final AxisAlignedBB bb = this.traceToBlock(i, Nowall.mc.func_184121_ak());
            final BlockPos pos = new BlockPos(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
            final Block block = Nowall.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (block.func_180639_a((World)Nowall.mc.field_71441_e, pos, Nowall.mc.field_71441_e.func_180495_p(pos), (EntityPlayer)Nowall.mc.field_71439_g, hand, dir, x, y, z)) {
                return new BlockPos((Vec3i)pos);
            }
        }
        return null;
    }
    
    private AxisAlignedBB traceToBlock(final double dist, final float partialTicks) {
        final Vec3d pos = Nowall.mc.field_71439_g.func_174824_e(partialTicks);
        final Vec3d angles = Nowall.mc.field_71439_g.func_70676_i(partialTicks);
        final Vec3d end = pos.func_72441_c(angles.field_72450_a * dist, angles.field_72448_b * dist, angles.field_72449_c * dist);
        return new AxisAlignedBB(end.field_72450_a, end.field_72448_b, end.field_72449_c, end.field_72450_a + 1.0, end.field_72448_b + 1.0, end.field_72449_c + 1.0);
    }
}
