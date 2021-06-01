package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.*;
import Method.Client.utils.visual.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import Method.Client.utils.*;
import net.minecraft.network.play.client.*;

public class Burrow extends Module
{
    Setting mode;
    Setting glitchY;
    Setting tpHeight;
    Setting delay;
    Setting Rotate;
    Setting Instant;
    Setting Center;
    Setting CenterBypass;
    Setting OffGround;
    private final TimerUtils timer;
    
    public Burrow() {
        super("Burrow", 0, Category.COMBAT, "Burrow into hole");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "JUMP", new String[] { "JUMP", "GLITCH", "TP" }));
        this.glitchY = Main.setmgr.add(new Setting("glitchY", this, 0.5, 0.1, 1.5, false, this.mode, "GLITCH", 1));
        this.tpHeight = Main.setmgr.add(new Setting("tpHeight", this, 0.5, 0.0, 10.0, false, this.mode, "TP", 1));
        this.delay = Main.setmgr.add(new Setting("delay", this, 200.0, 1.0, 500.0, false));
        this.Rotate = Main.setmgr.add(new Setting("Rotate", this, true));
        this.Instant = Main.setmgr.add(new Setting("Instant", this, true));
        this.Center = Main.setmgr.add(new Setting("Center", this, true));
        this.CenterBypass = Main.setmgr.add(new Setting("CenterBypass", this, true, this.Center, 5));
        this.OffGround = Main.setmgr.add(new Setting("OffGround", this, true));
        this.timer = new TimerUtils();
    }
    
    private int find_obi_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Burrow.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay((long)this.delay.getValDouble()) && this.find_obi_in_hotbar() != -1) {
            final double posy = Burrow.mc.field_71439_g.field_70163_u;
            final int current = Burrow.mc.field_71439_g.field_71071_by.field_70461_c;
            Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.find_obi_in_hotbar()));
            Burrow.mc.field_71439_g.field_71071_by.field_70461_c = this.find_obi_in_hotbar();
            final BlockPos positionToPlaceAt = new BlockPos(Burrow.mc.field_71439_g.func_174791_d()).func_177977_b();
            if (this.place(positionToPlaceAt, Burrow.mc)) {
                if (this.OffGround.getValBoolean()) {
                    Burrow.mc.field_71439_g.field_70122_E = false;
                }
                final String valString = this.mode.getValString();
                switch (valString) {
                    case "JUMP": {
                        Burrow.mc.field_71439_g.func_70664_aZ();
                        break;
                    }
                    case "GLITCH": {
                        Burrow.mc.field_71439_g.field_70181_x = this.glitchY.getValDouble();
                        break;
                    }
                    case "TP": {
                        Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u - this.tpHeight.getValDouble(), Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                        break;
                    }
                }
            }
            Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(current));
            Burrow.mc.field_71439_g.field_71071_by.field_70461_c = current;
            if (this.Instant.getValBoolean()) {
                Burrow.mc.field_71439_g.field_70163_u = posy;
            }
            this.toggle();
        }
    }
    
    @Override
    public void onEnable() {
        if (Burrow.mc.field_71439_g != null) {
            if (this.find_obi_in_hotbar() != -1) {
                if (this.Center.getValBoolean()) {
                    if (this.CenterBypass.getValBoolean()) {
                        final double lMotionX = Math.floor(Burrow.mc.field_71439_g.field_70165_t) + 0.5 - Burrow.mc.field_71439_g.field_70165_t;
                        final double lMotionZ = Math.floor(Burrow.mc.field_71439_g.field_70161_v) + 0.5 - Burrow.mc.field_71439_g.field_70161_v;
                        Burrow.mc.field_71439_g.field_70159_w = lMotionX / 2.0;
                        Burrow.mc.field_71439_g.field_70179_y = lMotionZ / 2.0;
                    }
                    else {
                        final double[] newPos = { Math.floor(Burrow.mc.field_71439_g.field_70165_t) + 0.5, Burrow.mc.field_71439_g.field_70163_u, Math.floor(Burrow.mc.field_71439_g.field_70161_v) + 0.5 };
                        final CPacketPlayer.Position middleOfPos = new CPacketPlayer.Position(newPos[0], newPos[1], newPos[2], Burrow.mc.field_71439_g.field_70122_E);
                        if (!Burrow.mc.field_71441_e.func_175623_d(new BlockPos(newPos[0], newPos[1], newPos[2]).func_177977_b()) && Burrow.mc.field_71439_g.field_70165_t != middleOfPos.field_149479_a && Burrow.mc.field_71439_g.field_70161_v != middleOfPos.field_149478_c) {
                            Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)middleOfPos);
                            Burrow.mc.field_71439_g.func_70107_b(newPos[0], newPos[1], newPos[2]);
                        }
                    }
                }
                Burrow.mc.field_71439_g.func_70664_aZ();
                this.timer.setLastMS();
            }
            else {
                ChatUtils.message("You dont have obsidian to use");
                this.toggle();
            }
        }
    }
    
    private EnumFacing calcSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos sideOffset = pos.func_177972_a(side);
            final IBlockState offsetState = Burrow.mc.field_71441_e.func_180495_p(sideOffset);
            if (offsetState.func_177230_c().func_176209_a(offsetState, false)) {
                if (!offsetState.func_185904_a().func_76222_j()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    private boolean place(final BlockPos pos, final Minecraft mc) {
        final Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
        final EnumFacing direction = this.calcSide(pos);
        if (direction == null) {
            return false;
        }
        final boolean activated = block.func_180639_a((World)mc.field_71441_e, pos, mc.field_71441_e.func_180495_p(pos), (EntityPlayer)mc.field_71439_g, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
        }
        final EnumFacing otherSide = direction.func_176734_d();
        final BlockPos sideOffset = pos.func_177972_a(direction);
        if (this.Rotate.getValBoolean()) {
            final float[] angle = Utils.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d((double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f)));
            mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], mc.field_71439_g.field_70122_E));
        }
        mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(sideOffset, otherSide, EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated) {
            mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        return true;
    }
}
