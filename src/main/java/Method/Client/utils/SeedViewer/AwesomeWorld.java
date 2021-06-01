package Method.Client.utils.SeedViewer;

import net.minecraft.client.multiplayer.*;
import net.minecraftforge.common.*;
import Method.Client.utils.system.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.storage.*;

public class AwesomeWorld extends World
{
    private ChunkProviderClient clientChunkProvider;
    
    public ChunkProviderClient getChunkProvider() {
        return (ChunkProviderClient)super.func_72863_F();
    }
    
    protected AwesomeWorld(final WorldInfo worldInfo) {
        super((ISaveHandler)new SaveHandlerMP(), worldInfo, DimensionManager.createProviderFor(0), Wrapper.mc.field_71424_I, true);
        this.func_72912_H().func_176144_a(EnumDifficulty.PEACEFUL);
        this.field_73011_w.func_76558_a((World)this);
        this.func_175652_B(new BlockPos(8, 64, 8));
        this.field_73020_y = this.func_72970_h();
        this.field_72988_C = (MapStorage)new SaveDataMemoryStorage();
        this.func_72966_v();
        this.func_72947_a();
        this.initCapabilities();
    }
    
    protected IChunkProvider func_72970_h() {
        return (IChunkProvider)(this.clientChunkProvider = new ChunkProviderClient((World)this));
    }
    
    protected boolean func_175680_a(final int x, final int z, final boolean allowEmpty) {
        return allowEmpty || !this.getChunkProvider().func_186025_d(x, z).func_76621_g();
    }
}
