package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import Method.Client.utils.system.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import Method.Client.utils.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.network.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import Method.Client.utils.visual.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class Teleport extends Module
{
    Setting mode;
    Setting math;
    Setting Path;
    Setting Land;
    Setting TpMode;
    Setting LineWidth;
    public boolean passPacket;
    private BlockPos teleportPosition;
    private boolean canDraw;
    private int delay;
    float reach;
    
    public Teleport() {
        super("Teleport", 0, Category.MOVEMENT, "Teleport around");
        this.mode = Main.setmgr.add(new Setting("Tp Mode", this, "Reach", new String[] { "Reach", "Flight" }));
        this.math = Main.setmgr.add(new Setting("Speed", this, false));
        this.Path = Main.setmgr.add(new Setting("Path", this, 0.0, 1.0, 1.0, 0.22));
        this.Land = Main.setmgr.add(new Setting("Land", this, 0.22, 1.0, 1.0, 0.22));
        this.TpMode = Main.setmgr.add(new Setting("Tp Draw", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.passPacket = false;
        this.teleportPosition = null;
        this.reach = 0.0f;
    }
    
    @Override
    public void onEnable() {
        if (this.mode.getValString().equalsIgnoreCase("Reach")) {
            this.reach = (float)Teleport.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111126_e();
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        if (this.mode.getValString().equalsIgnoreCase("Flight")) {
            Teleport.mc.field_71439_g.field_70145_X = false;
            this.passPacket = false;
            this.teleportPosition = null;
            return;
        }
        this.canDraw = false;
        Teleport.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111128_a(500.0);
        super.onDisable();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return side != Connection.Side.OUT || !this.mode.getValString().equalsIgnoreCase("Flight") || !(packet instanceof CPacketPlayer) || this.passPacket;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!this.mode.getValString().equalsIgnoreCase("Flight")) {
            if (((!Mouse.isButtonDown(0) && Wrapper.INSTANCE.mc().field_71415_G) || !Wrapper.INSTANCE.mc().field_71415_G) && Teleport.mc.field_71439_g.func_184605_cv() == 0) {
                Teleport.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111128_a(100.0);
                this.canDraw = true;
            }
            else {
                this.canDraw = false;
                Teleport.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111128_a((double)this.reach);
            }
            if (this.teleportPosition != null && this.delay == 0 && Mouse.isButtonDown(1)) {
                this.Mathteleport();
                this.delay = 5;
            }
            if (this.delay > 0) {
                --this.delay;
            }
            super.onClientTick(event);
            return;
        }
        final RayTraceResult object = Wrapper.INSTANCE.mc().field_71476_x;
        if (object == null) {
            return;
        }
        final EntityPlayerSP player = Teleport.mc.field_71439_g;
        final GameSettings settings = Wrapper.INSTANCE.mcSettings();
        if (!this.passPacket) {
            if (settings.field_74312_F.func_151470_d() && object.field_72313_a == RayTraceResult.Type.BLOCK) {
                if (Utils.isBlockMaterial(object.func_178782_a(), Blocks.field_150350_a)) {
                    return;
                }
                this.teleportPosition = object.func_178782_a();
                this.passPacket = true;
            }
            return;
        }
        player.field_70145_X = false;
        if (settings.field_74311_E.func_151470_d() && player.field_70122_E) {
            this.Mathteleport();
        }
    }
    
    private void Mathteleport() {
        if (this.math.getValBoolean()) {
            final double[] playerPosition = { Teleport.mc.field_71439_g.field_70165_t, Teleport.mc.field_71439_g.field_70163_u, Teleport.mc.field_71439_g.field_70161_v };
            final double[] blockPosition = { this.teleportPosition.func_177958_n() + 0.5f, this.teleportPosition.func_177956_o() + this.getOffset(Teleport.mc.field_71441_e.func_180495_p(this.teleportPosition).func_177230_c(), this.teleportPosition) + 1.0, this.teleportPosition.func_177952_p() + 0.5f };
            Utils.teleportToPosition(playerPosition, blockPosition, 0.25, 0.0, true, true);
            Teleport.mc.field_71439_g.func_70107_b(blockPosition[0], blockPosition[1], blockPosition[2]);
            this.teleportPosition = null;
        }
        else {
            final double x = this.teleportPosition.func_177958_n();
            final double y = this.teleportPosition.func_177956_o() + 1;
            final double z = this.teleportPosition.func_177952_p();
            Teleport.mc.field_71439_g.func_70107_b(x, y, z);
            for (int i = 0; i < 1; ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, Teleport.mc.field_71439_g.field_70122_E));
            }
        }
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!this.mode.getValString().equalsIgnoreCase("Flight")) {
            return;
        }
        final EntityPlayerSP player = Teleport.mc.field_71439_g;
        final GameSettings settings = Wrapper.INSTANCE.mcSettings();
        if (!this.passPacket) {
            player.field_70145_X = true;
            player.field_70143_R = 0.0f;
            player.field_70122_E = true;
            player.field_71075_bZ.field_75100_b = false;
            player.field_70159_w = 0.0;
            player.field_70181_x = 0.0;
            player.field_70179_y = 0.0;
            final float speed = 0.5f;
            if (settings.field_74314_A.func_151470_d()) {
                final EntityPlayerSP entityPlayerSP = player;
                entityPlayerSP.field_70181_x += speed;
            }
            if (settings.field_74311_E.func_151470_d()) {
                final EntityPlayerSP entityPlayerSP2 = player;
                entityPlayerSP2.field_70181_x -= speed;
            }
            double d7 = player.field_70177_z + 90.0f;
            final boolean flag4 = settings.field_74351_w.func_151470_d();
            final boolean flag5 = settings.field_74368_y.func_151470_d();
            final boolean flag6 = settings.field_74370_x.func_151470_d();
            final boolean flag7 = settings.field_74366_z.func_151470_d();
            if (flag4) {
                if (flag6) {
                    d7 -= 45.0;
                }
                else if (flag7) {
                    d7 += 45.0;
                }
            }
            else if (flag5) {
                d7 += 180.0;
                if (flag6) {
                    d7 += 45.0;
                }
                else if (flag7) {
                    d7 -= 45.0;
                }
            }
            else if (flag6) {
                d7 -= 90.0;
            }
            else if (flag7) {
                d7 += 90.0;
            }
            if (flag4 || flag6 || flag5 || flag7) {
                player.field_70159_w = Math.cos(Math.toRadians(d7));
                player.field_70179_y = Math.sin(Math.toRadians(d7));
            }
        }
        super.onLivingUpdate(event);
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Flight")) {
            if (this.teleportPosition == null) {
                return;
            }
            if (this.teleportPosition.func_177956_o() == new BlockPos((Entity)Teleport.mc.field_71439_g).func_177977_b().func_177956_o()) {
                RenderUtils.RenderBlock(this.TpMode.getValString(), RenderUtils.Standardbb(this.teleportPosition), this.Path.getcolor(), this.LineWidth.getValDouble());
                return;
            }
            RenderUtils.RenderBlock(this.TpMode.getValString(), RenderUtils.Standardbb(this.teleportPosition), this.Land.getcolor(), this.LineWidth.getValDouble());
        }
        else {
            final RayTraceResult object = Wrapper.INSTANCE.mc().field_71476_x;
            if (object == null) {
                return;
            }
            object.func_178782_a();
            if (this.canDraw) {
                for (float offset = -2.0f; offset < 18.0f; ++offset) {
                    final double[] mouseOverPos = { object.func_178782_a().func_177958_n(), object.func_178782_a().func_177956_o() + offset, object.func_178782_a().func_177952_p() };
                    if (this.BlockTeleport(mouseOverPos)) {
                        break;
                    }
                }
            }
            else if (object.field_72308_g != null) {
                for (float offset = -2.0f; offset < 18.0f; ++offset) {
                    final double[] mouseOverPos = { object.field_72308_g.field_70165_t, object.field_72308_g.field_70163_u + offset, object.field_72308_g.field_70161_v };
                    if (this.BlockTeleport(mouseOverPos)) {
                        break;
                    }
                }
            }
            else {
                this.teleportPosition = null;
            }
            super.onRenderWorldLast(event);
        }
    }
    
    private boolean BlockTeleport(final double[] mouseOverPos) {
        final BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
        if (this.canRenderBox(mouseOverPos)) {
            RenderUtils.RenderBlock(this.TpMode.getValString(), RenderUtils.Standardbb(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2])), this.Path.getcolor(), this.LineWidth.getValDouble());
            if (Wrapper.INSTANCE.mc().field_71415_G) {
                this.teleportPosition = blockBelowPos;
                return true;
            }
            this.teleportPosition = null;
        }
        return false;
    }
    
    public boolean canRenderBox(final double[] mouseOverPos) {
        boolean canTeleport = false;
        final Block blockBelowPos = Teleport.mc.field_71441_e.func_180495_p(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])).func_177230_c();
        final Block blockPos = Teleport.mc.field_71441_e.func_180495_p(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2])).func_177230_c();
        final Block blockAbovePos = Teleport.mc.field_71441_e.func_180495_p(new BlockPos(mouseOverPos[0], mouseOverPos[1] + 1.0, mouseOverPos[2])).func_177230_c();
        final boolean validBlockBelow = blockBelowPos.func_180646_a(Teleport.mc.field_71441_e.func_180495_p(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])), (IBlockAccess)Teleport.mc.field_71441_e, new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])) != null;
        final boolean validBlock = this.isValidBlock(blockPos);
        final boolean validBlockAbove = this.isValidBlock(blockAbovePos);
        if (validBlockBelow && validBlock && validBlockAbove) {
            canTeleport = true;
        }
        return canTeleport;
    }
    
    public double getOffset(final Block block, final BlockPos pos) {
        final IBlockState state = Teleport.mc.field_71441_e.func_180495_p(pos);
        double offset = 0.0;
        if (block instanceof BlockSlab && !((BlockSlab)block).func_176552_j()) {
            offset -= 0.5;
        }
        else if (block instanceof BlockEndPortalFrame) {
            offset -= 0.20000000298023224;
        }
        else if (block instanceof BlockBed) {
            offset -= 0.4399999976158142;
        }
        else if (block instanceof BlockCake) {
            offset -= 0.5;
        }
        else if (block instanceof BlockDaylightDetector) {
            offset -= 0.625;
        }
        else if (block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater) {
            offset -= 0.875;
        }
        else if (block instanceof BlockChest || block == Blocks.field_150477_bB) {
            offset -= 0.125;
        }
        else if (block instanceof BlockLilyPad) {
            offset -= 0.949999988079071;
        }
        else if (block == Blocks.field_150431_aC) {
            offset -= 0.875;
            offset += 0.125f * ((int)state.func_177229_b((IProperty)BlockSnow.field_176315_a) - 1);
        }
        else if (this.isValidBlock(block)) {
            --offset;
        }
        return offset;
    }
    
    public boolean isValidBlock(final Block block) {
        return block == Blocks.field_150427_aO || block == Blocks.field_150431_aC || block instanceof BlockTripWireHook || block instanceof BlockTripWire || block instanceof BlockDaylightDetector || block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater || block instanceof BlockSign || block instanceof BlockAir || block instanceof BlockPressurePlate || block instanceof BlockTallGrass || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockDoublePlant || block instanceof BlockReed || block instanceof BlockSapling || block == Blocks.field_150459_bM || block == Blocks.field_150464_aj || block == Blocks.field_150388_bm || block == Blocks.field_150469_bN || block == Blocks.field_150393_bb || block == Blocks.field_150394_bc || block == Blocks.field_150443_bT || block == Blocks.field_150445_bS || block == Blocks.field_150488_af || block instanceof BlockTorch || block == Blocks.field_150442_at || block instanceof BlockButton;
    }
}
