package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;

public final class GetAmbientOcclusionLightValueEvent extends Event
{
    private final IBlockState state;
    private float lightValue;
    private final float defaultLightValue;
    
    public GetAmbientOcclusionLightValueEvent(final IBlockState state, final float lightValue) {
        this.state = state;
        this.lightValue = lightValue;
        this.defaultLightValue = lightValue;
    }
    
    public IBlockState getState() {
        return this.state;
    }
    
    public float getLightValue() {
        return this.lightValue;
    }
    
    public void setLightValue(final float lightValue) {
        this.lightValue = lightValue;
    }
    
    public float getDefaultLightValue() {
        return this.defaultLightValue;
    }
}
