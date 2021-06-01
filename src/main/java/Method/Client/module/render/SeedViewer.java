package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.utils.*;
import java.util.concurrent.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.SeedViewer.*;
import net.minecraft.util.text.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class SeedViewer extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting LineWidth;
    Setting BlockLimit;
    Setting Fallingblock;
    Setting Liquid;
    Setting Tree;
    Setting Bush;
    Setting Distance;
    Setting LavaMix;
    Setting FalsePositive;
    Setting GrassSpread;
    private static ExecutorService executor;
    private static ExecutorService executor2;
    public int currentdis;
    private ArrayList<ChunkData> chunks;
    private ArrayList<int[]> tobesearch;
    private final TimerUtils timer;
    
    @Override
    public void setup() {
        SeedViewer.executor = Executors.newSingleThreadExecutor();
        SeedViewer.executor2 = Executors.newSingleThreadExecutor();
    }
    
    public SeedViewer() {
        super("SeedViewer", 0, Category.RENDER, "SeedViewer");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 1.0));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.BlockLimit = Main.setmgr.add(new Setting("BlockLimit", this, 200.0, 0.0, 5000.0, false));
        this.Fallingblock = Main.setmgr.add(new Setting("Falling block", this, false));
        this.Liquid = Main.setmgr.add(new Setting("Liquid", this, false));
        this.Tree = Main.setmgr.add(new Setting("Tree", this, false));
        this.Bush = Main.setmgr.add(new Setting("Bush", this, false));
        this.Distance = Main.setmgr.add(new Setting("Distance", this, 6.0, 0.0, 15.0, true));
        this.LavaMix = Main.setmgr.add(new Setting("LavaMix", this, false));
        this.FalsePositive = Main.setmgr.add(new Setting("FalsePositive", this, false));
        this.GrassSpread = Main.setmgr.add(new Setting("GrassSpread", this, false));
        this.currentdis = 0;
        this.chunks = new ArrayList<ChunkData>();
        this.tobesearch = new ArrayList<int[]>();
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay(500L)) {
            if (SeedViewer.mc.field_71439_g.field_71093_bK != this.currentdis) {
                ChatUtils.warning("You must reenable on dimension change!");
                this.toggle();
            }
            this.searchViewDistance();
            this.runviewdistance();
            this.timer.setLastMS();
        }
        int[] remove = null;
        final Iterator<int[]> iterator = this.tobesearch.iterator();
        while (iterator.hasNext()) {
            final int[] vec2d = remove = iterator.next();
            final Object o;
            SeedViewer.executor.execute(() -> WorldLoader.CreateChunk(o[0], o[1], SeedViewer.mc.field_71439_g.field_71093_bK));
        }
        this.tobesearch.remove(remove);
    }
    
    @Override
    public void onEnable() {
        if (SeedViewer.mc.func_71356_B()) {
            ChatUtils.warning("Only for multiplayer");
            this.toggle();
        }
        if (WorldLoader.seed == 44776655L) {
            ChatUtils.message("Set Seed using the" + TextFormatting.GOLD + " @WorldSeed" + TextFormatting.RESET + " Command");
            this.toggle();
            return;
        }
        this.currentdis = SeedViewer.mc.field_71439_g.field_71093_bK;
        SeedViewer.executor = Executors.newSingleThreadExecutor();
        SeedViewer.executor2 = Executors.newSingleThreadExecutor();
        WorldLoader.setup();
        ChatUtils.warning("Still Working on this.");
        this.chunks = new ArrayList<ChunkData>();
        this.searchViewDistance();
    }
    
    private void searchViewDistance() {
        int x;
        int z;
        boolean found;
        final Iterator<int[]> iterator;
        int[] vec2d;
        SeedViewer.executor.execute(() -> {
            for (x = SeedViewer.mc.field_71439_g.field_70176_ah - (int)this.Distance.getValDouble(); x <= SeedViewer.mc.field_71439_g.field_70176_ah + (int)this.Distance.getValDouble(); ++x) {
                for (z = SeedViewer.mc.field_71439_g.field_70164_aj - (int)this.Distance.getValDouble(); z <= SeedViewer.mc.field_71439_g.field_70164_aj + (int)this.Distance.getValDouble(); ++z) {
                    if (this.havenotsearched(x, z) && SeedViewer.mc.field_71441_e.func_190526_b(x, z)) {
                        found = false;
                        this.tobesearch.iterator();
                        while (iterator.hasNext()) {
                            vec2d = iterator.next();
                            if (vec2d[0] == x && vec2d[1] == z) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            this.tobesearch.add(new int[] { x, z });
                        }
                    }
                }
            }
        });
    }
    
    private void runviewdistance() {
        for (int x = SeedViewer.mc.field_71439_g.field_70176_ah - (int)this.Distance.getValDouble(); x <= SeedViewer.mc.field_71439_g.field_70176_ah + (int)this.Distance.getValDouble(); ++x) {
            for (int z = SeedViewer.mc.field_71439_g.field_70164_aj - (int)this.Distance.getValDouble(); z <= SeedViewer.mc.field_71439_g.field_70164_aj + (int)this.Distance.getValDouble(); ++z) {
                if (SeedViewer.mc.field_71441_e.func_190526_b(x, z) && WorldLoader.fakeworld.func_190526_b(x, z) && WorldLoader.fakeworld.func_190526_b(x + 1, z) && WorldLoader.fakeworld.func_190526_b(x, z + 1) && WorldLoader.fakeworld.func_190526_b(x + 1, z + 1) && this.havenotsearched(x, z)) {
                    final ChunkData data = new ChunkData(new ChunkPos(x, z), false);
                    this.searchChunk(SeedViewer.mc.field_71441_e.func_72964_e(x, z), data);
                    this.chunks.add(data);
                }
            }
        }
    }
    
    private boolean havenotsearched(final int x, final int z) {
        for (final ChunkData chunk : this.chunks) {
            if (chunk.chunkPos.field_77276_a == x && chunk.chunkPos.field_77275_b == z) {
                return false;
            }
        }
        return true;
    }
    
    private void searchChunk(final Chunk chunk, final ChunkData data) {
        int x;
        int z;
        int y;
        SeedViewer.executor2.execute(() -> {
            try {
                for (x = chunk.func_76632_l().func_180334_c(); x <= chunk.func_76632_l().func_180332_e(); ++x) {
                    for (z = chunk.func_76632_l().func_180333_d(); z <= chunk.func_76632_l().func_180330_f(); ++z) {
                        for (y = 0; y < 255; ++y) {
                            if (this.BlockFast(new BlockPos(x, y, z), WorldLoader.fakeworld.func_180495_p(new BlockPos(x, y, z)).func_177230_c(), chunk.func_186032_a(x, y, z).func_177230_c())) {
                                data.blocks.add(new BlockPos(x, y, z));
                            }
                        }
                    }
                }
                data.Searched = true;
            }
            catch (Exception ex) {}
        });
    }
    
    private boolean BlockFast(final BlockPos blockPos, final Block FakeChunk, final Block RealChunk) {
        if (RealChunk instanceof BlockSnow) {
            return false;
        }
        if (FakeChunk instanceof BlockSnow) {
            return false;
        }
        if (RealChunk instanceof BlockVine) {
            return false;
        }
        if (FakeChunk instanceof BlockVine) {
            return false;
        }
        if (!this.Fallingblock.getValBoolean()) {
            if (RealChunk instanceof BlockFalling) {
                return false;
            }
            if (FakeChunk instanceof BlockFalling) {
                return false;
            }
        }
        if (!this.Liquid.getValBoolean()) {
            if (RealChunk instanceof BlockLiquid) {
                return false;
            }
            if (FakeChunk instanceof BlockLiquid) {
                return false;
            }
            if (SeedViewer.mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() instanceof BlockLiquid) {
                return false;
            }
            if (SeedViewer.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2)).func_177230_c() instanceof BlockLiquid) {
                return false;
            }
        }
        if (!this.Tree.getValBoolean()) {
            if (FakeChunk instanceof BlockGrass && this.Treeroots(blockPos)) {
                return false;
            }
            if (RealChunk instanceof BlockLog || RealChunk instanceof BlockLeaves) {
                return false;
            }
            if (FakeChunk instanceof BlockLog || FakeChunk instanceof BlockLeaves) {
                return false;
            }
        }
        if (!this.GrassSpread.getValBoolean()) {
            if (RealChunk instanceof BlockGrass && FakeChunk instanceof BlockDirt) {
                return false;
            }
            if (RealChunk instanceof BlockDirt && FakeChunk instanceof BlockGrass) {
                return false;
            }
        }
        if (!this.Bush.getValBoolean()) {
            if (RealChunk instanceof BlockBush) {
                return false;
            }
            if (RealChunk instanceof BlockReed) {
                return false;
            }
            if (FakeChunk instanceof BlockBush) {
                return false;
            }
        }
        if (!this.LavaMix.getValBoolean() && (RealChunk instanceof BlockObsidian || RealChunk.equals(Blocks.field_150347_e)) && this.Lavamix(blockPos)) {
            return false;
        }
        if (!this.FalsePositive.getValBoolean()) {
            if (FakeChunk instanceof BlockOre && (RealChunk instanceof BlockStone || RealChunk instanceof BlockMagma || RealChunk instanceof BlockNetherrack || RealChunk instanceof BlockDirt)) {
                return false;
            }
            if (RealChunk instanceof BlockOre && (FakeChunk instanceof BlockStone || FakeChunk instanceof BlockMagma || FakeChunk instanceof BlockNetherrack || FakeChunk instanceof BlockDirt)) {
                return false;
            }
            if (FakeChunk instanceof BlockRedstoneOre && (RealChunk instanceof BlockStone || RealChunk instanceof BlockDirt)) {
                return false;
            }
            if (RealChunk instanceof BlockRedstoneOre && (FakeChunk instanceof BlockStone || FakeChunk instanceof BlockDirt)) {
                return false;
            }
            if (FakeChunk instanceof BlockGlowstone && RealChunk instanceof BlockAir) {
                return false;
            }
            if (RealChunk instanceof BlockGlowstone && FakeChunk instanceof BlockAir) {
                return false;
            }
            if (FakeChunk instanceof BlockMagma && RealChunk instanceof BlockNetherrack) {
                return false;
            }
            if (RealChunk instanceof BlockMagma && FakeChunk instanceof BlockNetherrack) {
                return false;
            }
            if (RealChunk instanceof BlockFire || FakeChunk instanceof BlockFire) {
                return false;
            }
            if (RealChunk instanceof BlockOre && FakeChunk instanceof BlockOre) {
                return false;
            }
            if (RealChunk.func_149732_F().equals(Blocks.field_150418_aU.func_149732_F()) && FakeChunk instanceof BlockStone) {
                return false;
            }
            if ((FakeChunk instanceof BlockStone && RealChunk instanceof BlockDirt) || (FakeChunk instanceof BlockDirt && RealChunk instanceof BlockStone)) {
                return false;
            }
            if (!(FakeChunk instanceof BlockAir) && RealChunk instanceof BlockAir && !SeedViewer.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149732_F().equals(RealChunk.func_149732_F())) {
                return false;
            }
        }
        return !FakeChunk.func_149732_F().equals(RealChunk.func_149732_F());
    }
    
    public boolean Treeroots(final BlockPos b) {
        return SeedViewer.mc.field_71441_e.func_180495_p(b.func_177984_a()).func_177230_c() instanceof BlockLog;
    }
    
    public boolean Lavamix(final BlockPos b) {
        return SeedViewer.mc.field_71441_e.func_180495_p(b.func_177984_a()).func_177230_c() instanceof BlockLiquid || SeedViewer.mc.field_71441_e.func_180495_p(b.func_177977_b()).func_177230_c() instanceof BlockLiquid || SeedViewer.mc.field_71441_e.func_180495_p(b.func_177982_a(1, 0, 0)).func_177230_c() instanceof BlockLiquid || SeedViewer.mc.field_71441_e.func_180495_p(b.func_177982_a(0, 0, 1)).func_177230_c() instanceof BlockLiquid || SeedViewer.mc.field_71441_e.func_180495_p(b.func_177982_a(-1, 0, 0)).func_177230_c() instanceof BlockLiquid || SeedViewer.mc.field_71441_e.func_180495_p(b.func_177982_a(0, 0, -1)).func_177230_c() instanceof BlockLiquid;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        try {
            int blocklimit = 0;
            final ArrayList<ChunkData> Remove = new ArrayList<ChunkData>();
            for (final ChunkData chunk : this.chunks) {
                if (chunk.Searched) {
                    if (SeedViewer.mc.field_71439_g.func_70011_f((double)chunk.chunkPos.func_180332_e(), 100.0, (double)chunk.chunkPos.func_180330_f()) > 2000.0) {
                        Remove.add(chunk);
                    }
                    for (final BlockPos block : chunk.blocks) {
                        if (blocklimit > this.BlockLimit.getValDouble()) {
                            break;
                        }
                        RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(new BlockPos(block.field_177962_a, block.field_177960_b, block.field_177961_c)), this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
                        ++blocklimit;
                    }
                }
            }
            this.chunks.removeAll(Remove);
        }
        catch (Exception ex) {}
        super.onRenderWorldLast(event);
    }
    
    public static class ChunkData
    {
        private boolean Searched;
        public final List<BlockPos> blocks;
        private ChunkPos chunkPos;
        
        public List<BlockPos> getBlocks() {
            return this.blocks;
        }
        
        public ChunkData(final ChunkPos chunkPos, final boolean Searched) {
            this.blocks = new ArrayList<BlockPos>();
            this.chunkPos = chunkPos;
            this.Searched = Searched;
        }
    }
}
