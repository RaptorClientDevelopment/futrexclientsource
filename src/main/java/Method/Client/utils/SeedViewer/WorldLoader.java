package Method.Client.utils.SeedViewer;

import java.util.*;
import net.minecraft.world.storage.*;
import Method.Client.utils.system.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.terraingen.*;

public class WorldLoader
{
    public static IChunkGenerator ChunkGenerator;
    public static long seed;
    public static boolean GenerateStructures;
    public static AwesomeWorld fakeworld;
    public static Random rand;
    
    public static void setup() {
        final WorldSettings worldSettings = new WorldSettings(WorldLoader.seed, GameType.SURVIVAL, WorldLoader.GenerateStructures, false, WorldType.field_77137_b);
        final WorldInfo worldInfo = new WorldInfo(worldSettings, "FakeWorld");
        worldInfo.func_176128_f(true);
        WorldLoader.fakeworld = new AwesomeWorld(worldInfo);
        if (Wrapper.mc.field_71439_g.field_71093_bK == -1) {
            WorldLoader.ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell((World)WorldLoader.fakeworld, WorldLoader.fakeworld.func_72912_H().func_76089_r(), WorldLoader.seed);
        }
        else {
            WorldLoader.ChunkGenerator = WorldLoader.fakeworld.field_73011_w.func_186060_c();
        }
    }
    
    public static Chunk CreateChunk(final int x, final int z, final int dis) {
        if (dis == -1 && !(WorldLoader.ChunkGenerator instanceof ChunkGeneratorHell)) {
            WorldLoader.ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell((World)WorldLoader.fakeworld, WorldLoader.fakeworld.func_72912_H().func_76089_r(), WorldLoader.seed);
        }
        Chunk Testchunk;
        if (!WorldLoader.fakeworld.func_190526_b(x, z)) {
            Testchunk = WorldLoader.ChunkGenerator.func_185932_a(x, z);
        }
        else {
            Testchunk = WorldLoader.fakeworld.func_72964_e(x, z);
        }
        WorldLoader.fakeworld.getChunkProvider().field_73236_b.put(ChunkPos.func_77272_a(x, z), (Object)Testchunk);
        Testchunk.func_76631_c();
        populate((IChunkProvider)WorldLoader.fakeworld.getChunkProvider(), WorldLoader.ChunkGenerator, x, z);
        return Testchunk;
    }
    
    public static void populate(final IChunkProvider chunkProvider, final IChunkGenerator chunkGenrator, final int x, final int z) {
        final Chunk chunk = chunkProvider.func_186026_b(x, z - 1);
        final Chunk chunk2 = chunkProvider.func_186026_b(x + 1, z);
        final Chunk chunk3 = chunkProvider.func_186026_b(x, z + 1);
        final Chunk chunk4 = chunkProvider.func_186026_b(x - 1, z);
        if (chunk2 != null && chunk3 != null && chunkProvider.func_186026_b(x + 1, z + 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x, z);
        }
        if (chunk4 != null && chunk3 != null && chunkProvider.func_186026_b(x - 1, z + 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x - 1, z);
        }
        if (chunk != null && chunk2 != null && chunkProvider.func_186026_b(x + 1, z - 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x, z - 1);
        }
        if (chunk != null && chunk4 != null) {
            final Chunk chunk5 = chunkProvider.func_186026_b(x - 1, z - 1);
            if (chunk5 != null) {
                Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x - 1, z - 1);
            }
        }
    }
    
    private static void Awesomepopulate(final IChunkGenerator overworldChunkGen, final AwesomeWorld fakeworld, final int x, final int z) {
        final Chunk testchunk = fakeworld.func_72964_e(x, z);
        if (testchunk.func_177419_t()) {
            if (overworldChunkGen.func_185933_a(testchunk, x, z)) {
                testchunk.func_76630_e();
            }
        }
        else {
            testchunk.func_150809_p();
            overworldChunkGen.func_185931_b(x, z);
            testchunk.func_76630_e();
        }
    }
    
    public static void event(final PopulateChunkEvent.Populate event) {
        event.setResult(Event.Result.ALLOW);
    }
    
    public static void DecorateBiomeEvent(final DecorateBiomeEvent.Decorate event) {
        event.setResult(Event.Result.ALLOW);
    }
    
    static {
        WorldLoader.seed = 44776655L;
        WorldLoader.GenerateStructures = true;
    }
}
