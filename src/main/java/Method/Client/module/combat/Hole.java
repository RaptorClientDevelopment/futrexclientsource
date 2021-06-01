package Method.Client.module.combat;

import net.minecraft.util.math.*;

class Hole extends Vec3i
{
    private final boolean tall;
    private HoleTypes HoleType;
    
    public Hole(final int x, final int y, final int z, final BlockPos pos, final HoleTypes p_Type, final boolean tall) {
        super(x, y, z);
        this.tall = tall;
        this.SetHoleType(p_Type);
    }
    
    public boolean isTall() {
        return this.tall;
    }
    
    public HoleTypes GetHoleType() {
        return this.HoleType;
    }
    
    public void SetHoleType(final HoleTypes holeType) {
        this.HoleType = holeType;
    }
    
    public enum HoleTypes
    {
        None, 
        Obsidian, 
        Bedrock, 
        Void, 
        Burrow;
    }
}
