package Method.Client.module.movement;

import Method.Client.module.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import Method.Client.utils.proxy.Overrides.*;

public class InvMove extends Module
{
    private static boolean Toggled;
    
    public InvMove() {
        super("Inv Move", 0, Category.MOVEMENT, "Inventory Move");
    }
    
    public static boolean runthething() {
        if (InvMove.Toggled) {
            InvMove.mc.field_71439_g.field_71158_b.field_78902_a = 0.0f;
            InvMove.mc.field_71439_g.field_71158_b.field_192832_b = 0.0f;
            if (Keyboard.isKeyDown(203)) {
                InvMove.mc.field_71439_g.field_70177_z -= 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                InvMove.mc.field_71439_g.field_70177_z += 5.0f;
            }
            if (Keyboard.isKeyDown(200)) {
                InvMove.mc.field_71439_g.field_70125_A = Math.max(InvMove.mc.field_71439_g.field_70125_A - 5.0f, -90.0f);
            }
            if (Keyboard.isKeyDown(208)) {
                InvMove.mc.field_71439_g.field_70125_A = Math.min(InvMove.mc.field_71439_g.field_70125_A + 5.0f, 90.0f);
            }
            KeyBinding.func_74510_a(InvMove.mc.field_71474_y.field_74351_w.func_151463_i(), Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74351_w.func_151463_i()));
            if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74351_w.func_151463_i())) {
                final MovementInput field_71158_b = InvMove.mc.field_71439_g.field_71158_b;
                ++field_71158_b.field_192832_b;
                InvMove.mc.field_71439_g.field_71158_b.field_187255_c = true;
            }
            else {
                InvMove.mc.field_71439_g.field_71158_b.field_187255_c = false;
            }
            KeyBinding.func_74510_a(InvMove.mc.field_71474_y.field_74368_y.func_151463_i(), Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74368_y.func_151463_i()));
            if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74368_y.func_151463_i())) {
                final MovementInput field_71158_b2 = InvMove.mc.field_71439_g.field_71158_b;
                --field_71158_b2.field_192832_b;
                InvMove.mc.field_71439_g.field_71158_b.field_187256_d = true;
            }
            else {
                InvMove.mc.field_71439_g.field_71158_b.field_187256_d = false;
            }
            KeyBinding.func_74510_a(InvMove.mc.field_71474_y.field_74370_x.func_151463_i(), Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74370_x.func_151463_i()));
            if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74370_x.func_151463_i())) {
                final MovementInput field_71158_b3 = InvMove.mc.field_71439_g.field_71158_b;
                ++field_71158_b3.field_78902_a;
                InvMove.mc.field_71439_g.field_71158_b.field_187257_e = true;
            }
            else {
                InvMove.mc.field_71439_g.field_71158_b.field_187257_e = false;
            }
            KeyBinding.func_74510_a(InvMove.mc.field_71474_y.field_74366_z.func_151463_i(), Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74366_z.func_151463_i()));
            if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74366_z.func_151463_i())) {
                final MovementInput field_71158_b4 = InvMove.mc.field_71439_g.field_71158_b;
                --field_71158_b4.field_78902_a;
                InvMove.mc.field_71439_g.field_71158_b.field_187258_f = true;
            }
            else {
                InvMove.mc.field_71439_g.field_71158_b.field_187258_f = false;
            }
            KeyBinding.func_74510_a(InvMove.mc.field_71474_y.field_74314_A.func_151463_i(), Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74314_A.func_151463_i()));
            InvMove.mc.field_71439_g.field_71158_b.field_78901_c = Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74314_A.func_151463_i());
            if (Mouse.isButtonDown(2)) {
                Mouse.setGrabbed(true);
                InvMove.mc.field_71415_G = true;
            }
            else if (InvMove.mc.field_71462_r != null) {
                Mouse.setGrabbed(false);
                InvMove.mc.field_71415_G = false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onEnable() {
        InvMove.Toggled = true;
        MoveOverride.toggle();
    }
    
    @Override
    public void onDisable() {
        InvMove.Toggled = false;
    }
}
