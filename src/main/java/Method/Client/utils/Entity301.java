package Method.Client.utils;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class Entity301 extends EntityOtherPlayerMP
{
    public Entity301(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    public void setMovementInput(final MovementInput movementInput) {
        if (movementInput.field_78901_c && this.field_70122_E) {
            super.func_70664_aZ();
        }
        super.func_191958_b(movementInput.field_78902_a, this.field_70701_bs, movementInput.field_192832_b, this.field_70764_aw);
    }
    
    public void func_70091_d(final MoverType type, final double x, final double y, final double z) {
        this.field_70122_E = true;
        super.func_70091_d(type, x, y, z);
        this.field_70122_E = true;
    }
    
    public boolean func_70093_af() {
        return false;
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        this.field_70145_X = true;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70145_X = false;
    }
}
