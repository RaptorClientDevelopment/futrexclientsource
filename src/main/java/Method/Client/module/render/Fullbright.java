package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraft.potion.*;

public class Fullbright extends Module
{
    private float oldBrightness;
    Setting mode;
    
    public Fullbright() {
        super("Fullbright", 0, Category.RENDER, "Makes the screen bright");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Potion", new String[] { "Gamma", "Potion" }));
    }
    
    @Override
    public void PlayerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        final PotionEffect nv = new PotionEffect((Potion)Objects.requireNonNull(Potion.func_188412_a(16)), 9999999, 3);
        Fullbright.mc.field_71439_g.func_70690_d(nv);
    }
    
    @Override
    public void onEnable() {
        if (this.mode.getValString().equalsIgnoreCase("Gamma")) {
            this.oldBrightness = Fullbright.mc.field_71474_y.field_74333_Y;
            Fullbright.mc.field_71474_y.field_74333_Y = 10.0f;
        }
        if (this.mode.getValString().equalsIgnoreCase("Potion")) {
            final PotionEffect nv = new PotionEffect((Potion)Objects.requireNonNull(Potion.func_188412_a(16)), 9999999, 3);
            Fullbright.mc.field_71439_g.func_70690_d(nv);
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Fullbright.mc.field_71474_y.field_74333_Y = this.oldBrightness;
        Fullbright.mc.field_71439_g.func_184596_c(Potion.func_188412_a(16));
        super.onDisable();
    }
}
