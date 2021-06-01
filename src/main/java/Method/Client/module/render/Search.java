package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.utils.Screens.Custom.Search.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.client.gui.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraftforge.event.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.state.*;
import net.minecraftforge.client.event.*;
import it.unimi.dsi.fastutil.longs.*;
import net.minecraft.client.*;
import java.util.*;
import Method.Client.utils.visual.*;

public class Search extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting LineWidth;
    Setting Gui;
    private final SearchGuiSettings blocks;
    public static ArrayList<String> blockNames;
    private final Long2ObjectArrayMap<MyChunk> chunks;
    private final Pool<MyBlock> blockPool;
    private final LongList toRemove;
    private final BlockPos.MutableBlockPos blockPos;
    
    public Search() {
        super("Search", 0, Category.RENDER, "Search");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.42));
        this.Mode = Main.setmgr.add(new Setting("block Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.8, 0.0, 3.0, false));
        this.Gui = Main.setmgr.add(new Setting("Gui", this, Main.Search));
        this.blocks = new SearchGuiSettings(new Block[] { Blocks.field_150477_bB, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
        this.chunks = (Long2ObjectArrayMap<MyChunk>)new Long2ObjectArrayMap();
        this.blockPool = new Pool<MyBlock>(() -> new MyBlock());
        this.toRemove = (LongList)new LongArrayList();
        this.blockPos = new BlockPos.MutableBlockPos();
    }
    
    @Override
    public void setup() {
        Executer.init();
    }
    
    @Override
    public void onEnable() {
        Search.blockNames = new ArrayList<String>(SearchGuiSettings.getBlockNames());
        Executer.init();
        this.searchViewDistance();
    }
    
    @Override
    public void onDisable() {
        for (final MyChunk chunk : this.chunks.values()) {
            chunk.dispose();
        }
        this.chunks.clear();
    }
    
    private void searchViewDistance() {
        for (int viewDist = Search.mc.field_71474_y.field_151451_c, x = Search.mc.field_71439_g.field_70176_ah - viewDist; x <= Search.mc.field_71439_g.field_70176_ah + viewDist; ++x) {
            for (int z = Search.mc.field_71439_g.field_70164_aj - viewDist; z <= Search.mc.field_71439_g.field_70164_aj + viewDist; ++z) {
                if (Search.mc.field_71441_e.func_190526_b(x, z)) {
                    this.searchChunk(Search.mc.field_71441_e.func_72964_e(x, z));
                }
            }
        }
    }
    
    @Override
    public void ChunkeventLOAD(final ChunkEvent.Load event) {
        this.searchChunk(event.getChunk());
    }
    
    private void searchChunk(final Chunk chunk) {
        final MyChunk myChunk;
        int x;
        int z;
        int y;
        Executer.execute(() -> {
            myChunk = new MyChunk(chunk.func_76632_l().field_77276_a, chunk.func_76632_l().field_77275_b);
            for (x = chunk.func_76632_l().func_180334_c(); x <= chunk.func_76632_l().func_180332_e(); ++x) {
                for (z = chunk.func_76632_l().func_180333_d(); z <= chunk.func_76632_l().func_180330_f(); ++z) {
                    for (y = 0; y < 256; ++y) {
                        this.blockPos.func_181079_c(x, y, z);
                        if (this.isVisible(chunk.func_177435_g((BlockPos)this.blockPos).func_177230_c())) {
                            myChunk.add((BlockPos)this.blockPos, false);
                        }
                    }
                }
            }
            synchronized (this.chunks) {
                if (myChunk.blocks.size() > 0) {
                    this.chunks.put(ChunkPos.func_77272_a(chunk.func_76632_l().field_77276_a, chunk.func_76632_l().field_77275_b), (Object)myChunk);
                }
            }
        });
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketBlockChange) {
            final SPacketBlockChange packet2 = (SPacketBlockChange)packet;
            this.onBlockUpdate(packet2.func_179827_b(), packet2.field_148883_d);
        }
        if (packet instanceof SPacketBlockAction) {
            final SPacketBlockAction packet3 = (SPacketBlockAction)packet;
            this.onBlockUpdate(packet3.func_179825_a(), packet3.func_148868_c().func_176223_P());
        }
        if (packet instanceof SPacketMultiBlockChange) {
            final SPacketMultiBlockChange packet4 = (SPacketMultiBlockChange)packet;
            for (final SPacketMultiBlockChange.BlockUpdateData changedBlock : packet4.func_179844_a()) {
                this.onBlockUpdate(changedBlock.func_180090_a(), changedBlock.func_180088_c());
            }
        }
        return true;
    }
    
    public void onBlockUpdate(final BlockPos blockPos, final IBlockState blockState) {
        final int chunkX;
        final int chunkZ;
        final long key;
        final MyChunk chunk;
        Executer.execute(() -> {
            chunkX = blockPos.func_177958_n() >> 4;
            chunkZ = blockPos.func_177952_p() >> 4;
            key = ChunkPos.func_77272_a(chunkX, chunkZ);
            synchronized (this.chunks) {
                if (this.isVisible(blockState.func_177230_c())) {
                    ((MyChunk)this.chunks.computeIfAbsent((Object)key, aLong -> new MyChunk(chunkX, chunkZ))).add(blockPos, true);
                }
                else {
                    chunk = (MyChunk)this.chunks.get(key);
                    if (chunk != null) {
                        chunk.remove(blockPos);
                    }
                }
            }
        });
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        synchronized (this.chunks) {
            this.toRemove.clear();
            for (final long key : this.chunks.keySet()) {
                final MyChunk chunk = (MyChunk)this.chunks.get(key);
                if (chunk.shouldBeDeleted()) {
                    this.toRemove.add(key);
                }
                else {
                    chunk.render();
                }
            }
            for (final long key : this.toRemove) {
                this.chunks.remove(key);
            }
        }
    }
    
    private boolean isVisible(final Block block) {
        final String name = getName(block);
        final int index = Collections.binarySearch(Search.blockNames, name);
        return index >= 0;
    }
    
    public static String getName(final Block block) {
        return "" + Block.field_149771_c.func_177774_c((Object)block);
    }
    
    private class MyChunk
    {
        private final int x;
        private final int z;
        private final List<MyBlock> blocks;
        
        public MyChunk(final int x, final int z) {
            this.blocks = new ArrayList<MyBlock>();
            this.x = x;
            this.z = z;
        }
        
        public void add(final BlockPos blockPos, final boolean checkForDuplicates) {
            if (checkForDuplicates) {
                for (final MyBlock block : this.blocks) {
                    if (block.equals(blockPos)) {
                        return;
                    }
                }
            }
            final MyBlock block2 = Search.this.blockPool.get();
            block2.set(blockPos);
            this.blocks.add(block2);
        }
        
        public void remove(final BlockPos blockPos) {
            for (int i = 0; i < this.blocks.size(); ++i) {
                final MyBlock block = this.blocks.get(i);
                if (block.equals(blockPos)) {
                    this.blocks.remove(i);
                    return;
                }
            }
        }
        
        public boolean shouldBeDeleted() {
            final int viewDist = Search.mc.field_71474_y.field_151451_c + 1;
            return this.x > Search.mc.field_71439_g.field_70176_ah + viewDist || this.x < Search.mc.field_71439_g.field_70176_ah - viewDist || this.z > Search.mc.field_71439_g.field_70164_aj + viewDist || this.z < Search.mc.field_71439_g.field_70164_aj - viewDist;
        }
        
        public void render() {
            for (final MyBlock block : this.blocks) {
                block.render();
            }
        }
        
        public void dispose() {
            for (final MyBlock block : this.blocks) {
                Search.this.blockPool.free(block);
            }
            this.blocks.clear();
        }
    }
    
    private class MyBlock
    {
        private int x;
        private int y;
        private int z;
        
        public void set(final BlockPos blockPos) {
            this.x = blockPos.func_177958_n();
            this.y = blockPos.func_177956_o();
            this.z = blockPos.func_177952_p();
        }
        
        public void render() {
            RenderUtils.RenderBlock(Search.this.Mode.getValString(), RenderUtils.Standardbb(new BlockPos(this.x, this.y, this.z)), Search.this.OverlayColor.getcolor(), Search.this.LineWidth.getValDouble());
        }
        
        public boolean equals(final BlockPos blockPos) {
            return this.x == blockPos.func_177958_n() && this.y == blockPos.func_177956_o() && this.z == blockPos.func_177952_p();
        }
    }
    
    public static class Pool<T>
    {
        private final List<T> items;
        private final Producer<T> producer;
        
        public Pool(final Producer<T> producer) {
            this.items = new ArrayList<T>();
            this.producer = producer;
        }
        
        public T get() {
            if (this.items.size() > 0) {
                return this.items.remove(this.items.size() - 1);
            }
            return this.producer.create();
        }
        
        public void free(final T obj) {
            this.items.add(obj);
        }
    }
    
    public interface Producer<T>
    {
        T create();
    }
}
