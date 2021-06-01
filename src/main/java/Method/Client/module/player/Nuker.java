package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import java.util.function.*;
import net.minecraft.block.*;
import java.util.stream.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;
import net.minecraft.util.math.*;

public class Nuker extends Module
{
    public Setting mode;
    public Setting distance;
    public Setting Drawbox;
    public Setting StopOnKick;
    public Setting allcolor;
    public Setting idcolor;
    Setting Drawmode;
    Setting LineWidth;
    public final ArrayDeque<Set<BlockPos>> prevBlocks;
    public BlockPos currentBlock;
    public float progress;
    public float prevProgress;
    public int id;
    
    public Nuker() {
        super("Nuker", 0, Category.PLAYER, "Nuker");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "All", new String[] { "ID", "All" }));
        this.distance = Main.setmgr.add(new Setting("Distance", this, 6.0, 0.1, 6.0, false));
        this.Drawbox = Main.setmgr.add(new Setting("Draw box", this, true));
        this.StopOnKick = Main.setmgr.add(new Setting("StopOnKick", this, true));
        this.allcolor = Main.setmgr.add(new Setting("allcolor", this, 0.0, 1.0, 1.0, 1.0));
        this.idcolor = Main.setmgr.add(new Setting("idcolor", this, 0.22, 1.0, 1.0, 1.0));
        this.Drawmode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.prevBlocks = new ArrayDeque<Set<BlockPos>>();
    }
    
    @Override
    public void onDisable() {
        if (this.currentBlock != null) {
            Nuker.mc.field_71442_b.field_78778_j = true;
            Wrapper.INSTANCE.controller().func_78767_c();
            this.currentBlock = null;
        }
        this.prevBlocks.clear();
        this.id = 0;
        super.onDisable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Nuker.mc.field_71441_e == null && this.StopOnKick.getValBoolean() && this.isToggled()) {
            this.toggle();
        }
        this.currentBlock = null;
        final Vec3d eyesPos = Utils.getEyesPos().func_178786_a(0.5, 0.5, 0.5);
        final BlockPos eyesBlock = new BlockPos(Utils.getEyesPos());
        final double rangeSq = Math.pow(this.distance.getValDouble(), 2.0);
        final int blockRange = (int)Math.ceil(this.distance.getValDouble());
        Stream<BlockPos> stream = StreamSupport.stream(BlockPos.func_177980_a(eyesBlock.func_177982_a(blockRange, blockRange, blockRange), eyesBlock.func_177982_a(-blockRange, -blockRange, -blockRange)).spliterator(), true);
        stream = stream.filter(pos -> eyesPos.func_72436_e(new Vec3d(pos)) <= rangeSq).filter(BlockUtils::canBeClicked).sorted(Comparator.comparingDouble(pos -> eyesPos.func_72436_e(new Vec3d(pos))));
        if (this.mode.getValString().equalsIgnoreCase("id")) {
            stream = stream.filter(pos -> Block.func_149682_b(BlockUtils.getBlock(pos)) == this.id);
        }
        final List<BlockPos> blocks = stream.collect((Collector<? super BlockPos, ?, List<BlockPos>>)Collectors.toList());
        if (Nuker.mc.field_71439_g.field_71075_bZ.field_75098_d) {
            Stream<BlockPos> stream2 = blocks.parallelStream();
            for (final Set<BlockPos> set : this.prevBlocks) {
                stream2 = stream2.filter(pos -> !set.contains(pos));
            }
            final List<BlockPos> blocks2 = stream2.collect((Collector<? super BlockPos, ?, List<BlockPos>>)Collectors.toList());
            this.prevBlocks.addLast(new HashSet<BlockPos>(blocks2));
            while (this.prevBlocks.size() > 5) {
                this.prevBlocks.removeFirst();
            }
            if (!blocks2.isEmpty()) {
                this.currentBlock = blocks2.get(0);
            }
            Wrapper.INSTANCE.controller().func_78767_c();
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
            BlockUtils.breakBlocksPacketSpam(blocks2);
            return;
        }
        for (final BlockPos pos2 : blocks) {
            if (BlockUtils.breakBlockSimple(pos2)) {
                this.currentBlock = pos2;
                break;
            }
        }
        if (this.currentBlock == null) {
            Wrapper.INSTANCE.controller().func_78767_c();
        }
        if (this.currentBlock != null && BlockUtils.getHardness(this.currentBlock) < 1.0f) {
            this.prevProgress = this.progress;
        }
        this.progress = Nuker.mc.field_71442_b.field_78770_f;
        if (this.progress < this.prevProgress) {
            this.prevProgress = this.progress;
        }
        else {
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
        }
        super.onClientTick(event);
    }
    
    @Override
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        if (this.mode.getValString().equalsIgnoreCase("id") && Nuker.mc.field_71441_e.field_72995_K) {
            final IBlockState blockState = BlockUtils.getState(event.getPos());
            this.id = Block.func_149682_b(blockState.func_177230_c());
        }
        super.onLeftClickBlock(event);
    }
    
    @Override
    public void onWorldUnload(final WorldEvent.Unload event) {
        if (this.StopOnKick.getValBoolean()) {
            this.toggle();
        }
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.currentBlock == null) {
            return;
        }
        if (this.Drawbox.getValBoolean()) {
            if (this.mode.getValString().equalsIgnoreCase("all")) {
                RenderUtils.RenderBlock(this.Drawmode.getValString(), RenderUtils.Standardbb(this.currentBlock), this.allcolor.getcolor(), this.LineWidth.getValDouble());
            }
            else if (this.mode.getValString().equalsIgnoreCase("id")) {
                RenderUtils.RenderBlock(this.Drawmode.getValString(), RenderUtils.Standardbb(this.currentBlock), this.idcolor.getcolor(), this.LineWidth.getValDouble());
            }
        }
        super.onRenderWorldLast(event);
    }
}
