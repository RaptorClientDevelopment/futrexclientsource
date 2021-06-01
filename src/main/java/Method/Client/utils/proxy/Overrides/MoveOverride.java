package Method.Client.utils.proxy.Overrides;

import net.minecraft.client.settings.*;
import Method.Client.utils.system.*;
import Method.Client.module.movement.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;

public class MoveOverride extends MovementInputFromOptions
{
    public MoveOverride(final GameSettings gameSettingsIn) {
        super(gameSettingsIn);
    }
    
    public static void toggle() {
        Wrapper.mc.field_71439_g.field_71158_b = (MovementInput)new MoveOverride(Wrapper.mc.field_71474_y);
    }
    
    public void func_78898_a() {
        if (InvMove.runthething() || AutoHold.runthething()) {
            this.field_78901_c = Keyboard.isKeyDown(Wrapper.mc.field_71474_y.field_74314_A.func_151463_i());
            this.field_78899_d = Keyboard.isKeyDown(Wrapper.mc.field_71474_y.field_74311_E.func_151463_i());
            if (this.field_78899_d) {
                this.field_78902_a *= 0.3;
                this.field_192832_b *= 0.3;
            }
        }
        else {
            super.func_78898_a();
        }
    }
}
