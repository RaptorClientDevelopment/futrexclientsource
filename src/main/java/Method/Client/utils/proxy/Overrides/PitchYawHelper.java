package Method.Client.utils.proxy.Overrides;

import net.minecraft.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;

public class PitchYawHelper extends MouseHelper
{
    public static boolean Pitch;
    public static boolean Yaw;
    
    public void func_74374_c() {
        this.field_74377_a = Mouse.getDX();
        this.field_74375_b = Mouse.getDY();
        if (PitchYawHelper.Pitch) {
            Minecraft.func_71410_x().field_71417_B.field_74375_b = 0;
        }
        if (PitchYawHelper.Yaw) {
            Minecraft.func_71410_x().field_71417_B.field_74377_a = 0;
        }
    }
    
    static {
        PitchYawHelper.Pitch = false;
        PitchYawHelper.Yaw = false;
    }
}
