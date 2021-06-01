package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import Method.Client.utils.visual.*;

public class HoleEsp extends Module
{
    Setting Radius;
    Setting Void;
    Setting Bedrock;
    Setting obby;
    Setting Burrow;
    Setting OwnHole;
    Setting Timer;
    Setting Mode;
    Setting LineWidth;
    Setting BurrowDetect;
    Vec3i playerPos;
    TimerUtils timer;
    public final List<Hole> holes;
    
    public HoleEsp() {
        super("HoleEsp", 0, Category.COMBAT, "HoleEsp");
        this.Radius = Main.setmgr.add(new Setting("Radius", this, 8.0, 0.0, 32.0, true));
        this.Void = Main.setmgr.add(new Setting("Void", this, 0.85, 1.0, 1.0, 0.75));
        this.Bedrock = Main.setmgr.add(new Setting("Bedrock", this, 0.55, 1.0, 1.0, 0.75));
        this.obby = Main.setmgr.add(new Setting("obby", this, 0.22, 1.0, 1.0, 0.75));
        this.Burrow = Main.setmgr.add(new Setting("Burrow", this, 0.4, 1.0, 1.0, 0.75));
        this.OwnHole = Main.setmgr.add(new Setting("Ignore Own", this, true));
        this.Timer = Main.setmgr.add(new Setting("Timer", this, 250.0, 0.0, 500.0, true));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.BurrowDetect = Main.setmgr.add(new Setting("Burrow Detect", this, true));
        this.timer = new TimerUtils();
        this.holes = new ArrayList<Hole>();
    }
    
    @Override
    public void onEnable() {
        Executer.init();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay((long)this.Timer.getValDouble()) && !this.Mode.getValString().equalsIgnoreCase("None")) {
            this.playerPos = new Vec3i(HoleEsp.mc.field_71439_g.field_70165_t, HoleEsp.mc.field_71439_g.field_70163_u, HoleEsp.mc.field_71439_g.field_70161_v);
            this.holes.clear();
            final Iterator<Entity> iterator;
            Entity entity;
            EntityPlayer entityPlayer;
            BlockPos b;
            int x;
            int z;
            int y;
            BlockPos blockPos;
            Hole.HoleTypes l_Type;
            IBlockState downBlockState;
            BlockPos downPos;
            Hole.HoleTypes l_Type2;
            Executer.execute(() -> {
                if (this.BurrowDetect.getValBoolean()) {
                    HoleEsp.mc.field_71441_e.field_72996_f.iterator();
                    while (iterator.hasNext()) {
                        entity = iterator.next();
                        if (entity instanceof EntityPlayer) {
                            entityPlayer = (EntityPlayer)entity;
                            if (this.isInBurrow(entityPlayer)) {
                                b = new BlockPos((Entity)entityPlayer);
                                this.holes.add(new Hole(b.field_177962_a, b.field_177960_b, b.field_177961_c, b, Hole.HoleTypes.Burrow, false));
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                for (x = (int)(this.playerPos.func_177958_n() - this.Radius.getValDouble()); x < this.playerPos.func_177958_n() + this.Radius.getValDouble(); ++x) {
                    for (z = (int)(this.playerPos.func_177952_p() - this.Radius.getValDouble()); z < this.playerPos.func_177952_p() + this.Radius.getValDouble(); ++z) {
                        for (y = this.playerPos.func_177956_o() + 6; y > this.playerPos.func_177956_o() - 6; --y) {
                            blockPos = new BlockPos(x, y, z);
                            if (!this.OwnHole.getValBoolean() || HoleEsp.mc.field_71439_g.func_174818_b(blockPos) > 1.0) {
                                l_Type = isHoleValid(HoleEsp.mc.field_71441_e.func_180495_p(blockPos), blockPos);
                                if (l_Type != Hole.HoleTypes.None) {
                                    if (l_Type == Hole.HoleTypes.Void) {
                                        this.holes.add(new Hole(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), blockPos, Hole.HoleTypes.Void, true));
                                    }
                                    else {
                                        downBlockState = HoleEsp.mc.field_71441_e.func_180495_p(blockPos.func_177977_b());
                                        if (downBlockState.func_177230_c() == Blocks.field_150350_a) {
                                            downPos = blockPos.func_177977_b();
                                            l_Type2 = isHoleValid(downBlockState, blockPos);
                                            if (l_Type2 != Hole.HoleTypes.None) {
                                                this.holes.add(new Hole(downPos.func_177958_n(), downPos.func_177956_o(), downPos.func_177952_p(), downPos, l_Type2, true));
                                            }
                                        }
                                        else {
                                            this.holes.add(new Hole(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), blockPos, l_Type, false));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            });
            this.timer.setLastMS();
        }
    }
    
    private boolean isInBurrow(final EntityPlayer entityPlayer) {
        final BlockPos playerPos = new BlockPos(Math.floor(entityPlayer.field_70165_t + 0.5), entityPlayer.field_70163_u, Math.floor(entityPlayer.field_70161_v + 0.5));
        return HoleEsp.MC.field_71441_e.func_180495_p(playerPos).func_177230_c() == Blocks.field_150343_Z || HoleEsp.MC.field_71441_e.func_180495_p(playerPos).func_177230_c() == Blocks.field_150477_bB || HoleEsp.MC.field_71441_e.func_180495_p(playerPos).func_177230_c() == Blocks.field_150467_bQ;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (!this.Mode.getValString().equalsIgnoreCase("None")) {
            for (final Hole hole : this.holes) {
                final double renderPosX = hole.func_177958_n() - HoleEsp.mc.func_175598_ae().field_78730_l;
                final double renderPosY = hole.func_177956_o() - HoleEsp.mc.func_175598_ae().field_78731_m;
                final double renderPosZ = hole.func_177952_p() - HoleEsp.mc.func_175598_ae().field_78728_n;
                final AxisAlignedBB bb = new AxisAlignedBB(renderPosX, renderPosY, renderPosZ, renderPosX + 1.0, renderPosY + (hole.isTall() ? 2 : 1), renderPosZ + 1.0);
                RenderUtils.RenderBlock(this.Mode.getValString(), bb, (hole.GetHoleType() == Hole.HoleTypes.Bedrock) ? this.Bedrock.getcolor() : ((hole.GetHoleType() == Hole.HoleTypes.Obsidian) ? this.obby.getcolor() : ((hole.GetHoleType() == Hole.HoleTypes.Burrow) ? this.Burrow.getcolor() : this.Void.getcolor())), this.LineWidth.getValDouble());
            }
        }
    }
    
    public static Hole.HoleTypes isHoleValid(final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (blockState.func_177230_c() == Blocks.field_150350_a && blockPos.field_177960_b == 0) {
            return Hole.HoleTypes.Void;
        }
        if (HoleEsp.mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (HoleEsp.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2)).func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (HoleEsp.mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        boolean AllBedrock = true;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = HoleEsp.mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() == Blocks.field_150350_a || !touchingState.func_185913_b()) {
                return Hole.HoleTypes.None;
            }
            if (touchingState.func_177230_c() == Blocks.field_150343_Z) {
                AllBedrock = false;
            }
            else if (touchingState.func_177230_c() != Blocks.field_150357_h) {
                return Hole.HoleTypes.None;
            }
        }
        return AllBedrock ? Hole.HoleTypes.Bedrock : Hole.HoleTypes.Obsidian;
    }
}
