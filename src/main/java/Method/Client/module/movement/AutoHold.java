package Method.Client.module.movement;

import Method.Client.managers.*;
import org.lwjgl.input.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import Method.Client.*;
import Method.Client.module.*;
import Method.Client.utils.proxy.Overrides.*;
import net.minecraftforge.fml.common.gameevent.*;

public class AutoHold extends Module
{
    private static boolean Toggled;
    private static boolean Unloaded;
    public Setting unloadedChunk;
    public static Setting w;
    public static Setting a;
    public static Setting s;
    public static Setting d;
    public static Setting lc;
    public static Setting rc;
    public static Setting Space;
    
    public static boolean runthething() {
        if (!AutoHold.Toggled) {
            return false;
        }
        if (AutoHold.Unloaded) {
            return false;
        }
        AutoHold.mc.field_71439_g.field_71158_b.field_78902_a = 0.0f;
        AutoHold.mc.field_71439_g.field_71158_b.field_192832_b = 0.0f;
        KeyBinding.func_74510_a(AutoHold.mc.field_71474_y.field_74351_w.func_151463_i(), AutoHold.w.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74351_w.func_151463_i()));
        if (AutoHold.w.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74351_w.func_151463_i())) {
            final MovementInput field_71158_b = AutoHold.mc.field_71439_g.field_71158_b;
            ++field_71158_b.field_192832_b;
            AutoHold.mc.field_71439_g.field_71158_b.field_187255_c = true;
        }
        else {
            AutoHold.mc.field_71439_g.field_71158_b.field_187255_c = false;
        }
        KeyBinding.func_74510_a(AutoHold.mc.field_71474_y.field_74368_y.func_151463_i(), AutoHold.s.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74368_y.func_151463_i()));
        if (AutoHold.s.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74368_y.func_151463_i())) {
            final MovementInput field_71158_b2 = AutoHold.mc.field_71439_g.field_71158_b;
            --field_71158_b2.field_192832_b;
            AutoHold.mc.field_71439_g.field_71158_b.field_187256_d = true;
        }
        else {
            AutoHold.mc.field_71439_g.field_71158_b.field_187256_d = false;
        }
        KeyBinding.func_74510_a(AutoHold.mc.field_71474_y.field_74370_x.func_151463_i(), AutoHold.a.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74370_x.func_151463_i()));
        if (AutoHold.a.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74370_x.func_151463_i())) {
            final MovementInput field_71158_b3 = AutoHold.mc.field_71439_g.field_71158_b;
            ++field_71158_b3.field_78902_a;
            AutoHold.mc.field_71439_g.field_71158_b.field_187257_e = true;
        }
        else {
            AutoHold.mc.field_71439_g.field_71158_b.field_187257_e = false;
        }
        KeyBinding.func_74510_a(AutoHold.mc.field_71474_y.field_74366_z.func_151463_i(), AutoHold.d.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74366_z.func_151463_i()));
        if (AutoHold.d.getValBoolean() || Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74366_z.func_151463_i())) {
            final MovementInput field_71158_b4 = AutoHold.mc.field_71439_g.field_71158_b;
            --field_71158_b4.field_78902_a;
            AutoHold.mc.field_71439_g.field_71158_b.field_187258_f = true;
        }
        else {
            AutoHold.mc.field_71439_g.field_71158_b.field_187258_f = false;
        }
        KeyBinding.func_74510_a(AutoHold.mc.field_71474_y.field_74314_A.func_151463_i(), Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74314_A.func_151463_i()));
        AutoHold.mc.field_71439_g.field_71158_b.field_78901_c = Keyboard.isKeyDown(AutoHold.mc.field_71474_y.field_74314_A.func_151463_i());
        AutoHold.mc.field_71439_g.field_71158_b.field_78901_c = AutoHold.Space.getValBoolean();
        return true;
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(this.unloadedChunk = new Setting("Stop for unloaded", this, false));
        Main.setmgr.add(AutoHold.w = new Setting("w", this, true));
        Main.setmgr.add(AutoHold.a = new Setting("a", this, false));
        Main.setmgr.add(AutoHold.s = new Setting("s", this, false));
        Main.setmgr.add(AutoHold.d = new Setting("d", this, false));
        Main.setmgr.add(AutoHold.lc = new Setting("lc", this, false));
        Main.setmgr.add(AutoHold.rc = new Setting("rc", this, false));
        Main.setmgr.add(AutoHold.Space = new Setting("Space", this, false));
    }
    
    public AutoHold() {
        super("AutoHold", 0, Category.MOVEMENT, "Auto Walk + More!");
    }
    
    @Override
    public void onEnable() {
        MoveOverride.toggle();
        AutoHold.Toggled = true;
    }
    
    @Override
    public void onDisable() {
        AutoHold.Toggled = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.unloadedChunk.getValBoolean()) {
            AutoHold.Unloaded = !AutoHold.mc.field_71441_e.func_175726_f(AutoHold.mc.field_71439_g.func_180425_c()).func_177410_o();
        }
    }
    
    static {
        AutoHold.Unloaded = false;
    }
}
