package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class Jesus extends Module
{
    Setting mode;
    Setting offset;
    Setting Blockdist;
    Setting NoDrown;
    public TimerUtils Delayer;
    int noDown;
    int start;
    int cooldownSpeed;
    
    public Jesus() {
        super("Jesus", 0, Category.MOVEMENT, "Jesus, Walk on water");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Solid", new String[] { "Solid", "BOUNCE", "FrostWalker", "BunnyHop", "Aac" }));
        this.offset = Main.setmgr.add(new Setting("offset", this, 0.05, 0.0, 0.9, false));
        this.Blockdist = Main.setmgr.add(new Setting("Top Water", this, false, this.mode, "FrostWalker", 2));
        this.NoDrown = Main.setmgr.add(new Setting("NoDrown", this, false));
        this.Delayer = new TimerUtils();
    }
    
    @Override
    public void onEnable() {
        this.noDown = 0;
    }
    
    @Override
    public void onDisable() {
        this.noDown = 0;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return !this.NoDrown.getValBoolean() || !(packet instanceof CPacketPlayerAbilities) || !this.canSave();
    }
    
    private boolean canSave() {
        final boolean swinging = Jesus.mc.field_71439_g.field_82175_bq;
        final Vec3d prevmotion = new Vec3d(Jesus.mc.field_71439_g.field_70159_w, Jesus.mc.field_71439_g.field_70181_x, Jesus.mc.field_71439_g.field_70179_y);
        final boolean moving = prevmotion.field_72450_a != 0.0 || !Jesus.mc.field_71439_g.field_70124_G || Jesus.mc.field_71474_y.field_74314_A.func_151468_f() || prevmotion.field_72449_c != 0.0;
        Jesus.mc.field_71439_g.field_70171_ac = false;
        return Jesus.mc.field_71439_g.func_70090_H() && !swinging && !moving;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Aac") && Jesus.mc.field_71439_g.func_70090_H()) {
            ++this.start;
            if (this.start < 4) {
                return;
            }
            ++this.noDown;
            ++this.cooldownSpeed;
            Jesus.mc.field_71474_y.field_74314_A.field_74513_e = (this.noDown < 2);
            Jesus.mc.field_71474_y.field_74314_A.field_74513_e = true;
            if (this.noDown >= 3.5f) {
                this.noDown = 0;
            }
            if (this.cooldownSpeed >= 3) {
                final EntityPlayerSP field_71439_g = Jesus.mc.field_71439_g;
                field_71439_g.field_70159_w *= 1.1699999570846558;
                final EntityPlayerSP field_71439_g2 = Jesus.mc.field_71439_g;
                field_71439_g2.field_70179_y *= 1.1699999570846558;
                this.cooldownSpeed = 0;
                Jesus.mc.field_71474_y.field_74314_A.field_74513_e = false;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("BunnyHop") && Jesus.mc.field_71439_g.func_70090_H()) {
            Jesus.mc.field_71439_g.field_70747_aH = 0.1f;
            Jesus.mc.field_71439_g.field_70181_x = 0.42;
        }
        if (this.mode.getValString().equalsIgnoreCase("FrostWalker") && this.Delayer.isDelay(200L)) {
            for (final BlockPos b : BlockPos.func_191532_a((int)Jesus.mc.field_71439_g.field_70165_t - 3, (int)Jesus.mc.field_71439_g.field_70163_u - 2, (int)Jesus.mc.field_71439_g.field_70161_v - 3, (int)Jesus.mc.field_71439_g.field_70165_t + 3, (int)Jesus.mc.field_71439_g.field_70163_u + 2, (int)Jesus.mc.field_71439_g.field_70161_v + 3)) {
                if (Jesus.mc.field_71441_e.func_180495_p(b).func_177230_c() == Blocks.field_150355_j || Jesus.mc.field_71441_e.func_180495_p(b).func_177230_c() == Blocks.field_150358_i || Jesus.mc.field_71441_e.func_180495_p(b).func_177230_c() == Blocks.field_150353_l || Jesus.mc.field_71441_e.func_180495_p(b).func_177230_c() == Blocks.field_150356_k) {
                    if (this.Blockdist.getValBoolean() && Jesus.mc.field_71441_e.func_180495_p(b.func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
                        continue;
                    }
                    Jesus.mc.field_71441_e.func_175656_a(b, Blocks.field_185778_de.func_176223_P());
                    Jesus.mc.field_71441_e.func_175684_a(b, Blocks.field_185778_de, MathHelper.func_76136_a(Jesus.mc.field_71439_g.func_70681_au(), 6, 12));
                }
            }
            this.Delayer.setLastMS();
        }
        if (this.mode.getValString().equalsIgnoreCase("Solid")) {
            final BlockPos blockPos = new BlockPos(Jesus.mc.field_71439_g.field_70165_t, Jesus.mc.field_71439_g.field_70163_u, Jesus.mc.field_71439_g.field_70161_v);
            final Block block = Jesus.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
            if (block.func_176194_O().func_177622_c() == Blocks.field_150355_j || block.func_176194_O().func_177622_c() == Blocks.field_150358_i || block.func_176194_O().func_177622_c() == Blocks.field_150353_l || block.func_176194_O().func_177622_c() == Blocks.field_150356_k || Jesus.mc.field_71439_g.func_70090_H()) {
                Jesus.mc.field_71439_g.field_70181_x = 0.0;
                Jesus.mc.field_71439_g.field_70747_aH = 0.1f;
                if (Jesus.mc.field_71439_g.field_70173_aa % 2 == 0) {
                    Jesus.mc.field_71439_g.func_70107_b(Jesus.mc.field_71439_g.field_70165_t, Jesus.mc.field_71439_g.field_70163_u + 2.8471E-6, Jesus.mc.field_71439_g.field_70161_v);
                }
                else {
                    Jesus.mc.field_71439_g.func_70107_b(Jesus.mc.field_71439_g.field_70165_t, Jesus.mc.field_71439_g.field_70163_u - 2.8471E-6, Jesus.mc.field_71439_g.field_70161_v);
                }
                Jesus.mc.field_71439_g.field_70122_E = true;
            }
        }
        super.onClientTick(event);
        if (this.mode.getValString().equalsIgnoreCase("BOUNCE") && !Jesus.mc.field_71439_g.func_70093_af() && !Jesus.mc.field_71439_g.field_70145_X && !Jesus.mc.field_71474_y.field_74314_A.func_151470_d() && isOnLiquid(this.offset.getValDouble())) {
            Jesus.mc.field_71439_g.field_70181_x = 0.10000000149011612;
        }
    }
    
    public static boolean isOnLiquid(final double offset) {
        if (Jesus.mc.field_71439_g.field_70143_R >= 3.0f) {
            return false;
        }
        final AxisAlignedBB bb = (Jesus.mc.field_71439_g.func_184187_bx() != null) ? Jesus.mc.field_71439_g.func_184187_bx().func_174813_aQ().func_191195_a(0.0, 0.0, 0.0).func_72317_d(0.0, -offset, 0.0) : Jesus.mc.field_71439_g.func_174813_aQ().func_191195_a(0.0, 0.0, 0.0).func_72317_d(0.0, -offset, 0.0);
        boolean onLiquid = false;
        final int y = (int)bb.field_72338_b;
        for (int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d + 1.0); ++x) {
            for (int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f + 1.0); ++z) {
                final Block block = Jesus.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                if (block != Blocks.field_150350_a) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
}
