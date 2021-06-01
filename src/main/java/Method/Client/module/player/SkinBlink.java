package Method.Client.module.player;

import Method.Client.managers.*;
import net.minecraft.entity.player.*;
import java.util.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;

public class SkinBlink extends Module
{
    Setting mode;
    Setting slowness;
    private static final EnumPlayerModelParts[] PARTS_HORIZONTAL;
    private static final EnumPlayerModelParts[] PARTS_VERTICAL;
    private Random r;
    private int len;
    
    public SkinBlink() {
        super("SkinBlink", 0, Category.PLAYER, "SkinBlink");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Flat", new String[] { "HORIZONTAL", "VERTICAL", "RANDOM" }));
        this.slowness = Main.setmgr.add(new Setting("slowness", this, 2.0, 1.0, 2.0, true));
    }
    
    @Override
    public void setup() {
        this.r = new Random();
        this.len = EnumPlayerModelParts.values().length;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final String valString = this.mode.getValString();
        switch (valString) {
            case "RANDOM": {
                if (SkinBlink.mc.field_71439_g.field_70173_aa % this.slowness.getValDouble() != 0.0) {
                    return;
                }
                SkinBlink.mc.field_71474_y.func_178877_a(EnumPlayerModelParts.values()[this.r.nextInt(this.len)]);
                break;
            }
            case "VERTICAL":
            case "HORIZONTAL": {
                int i = (int)(SkinBlink.mc.field_71439_g.field_70173_aa / this.slowness.getValDouble() % (SkinBlink.PARTS_HORIZONTAL.length * 2));
                if (i >= SkinBlink.PARTS_HORIZONTAL.length) {
                    i -= SkinBlink.PARTS_HORIZONTAL.length;
                    SkinBlink.mc.field_71474_y.func_178878_a(this.mode.getValString().equalsIgnoreCase("Vertical") ? SkinBlink.PARTS_VERTICAL[i] : SkinBlink.PARTS_HORIZONTAL[i], true);
                    break;
                }
                break;
            }
        }
    }
    
    static {
        PARTS_HORIZONTAL = new EnumPlayerModelParts[] { EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE };
        PARTS_VERTICAL = new EnumPlayerModelParts[] { EnumPlayerModelParts.HAT, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.RIGHT_SLEEVE, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG };
    }
}
