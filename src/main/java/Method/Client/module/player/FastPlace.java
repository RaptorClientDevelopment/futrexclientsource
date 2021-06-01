package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.item.*;

public class FastPlace extends Module
{
    Setting Delay;
    Setting XP;
    Setting Crystal;
    
    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER, "Place Blocks Faster");
        this.Delay = Main.setmgr.add(new Setting("Delay", this, 0.0, 0.0, 20.0, false));
        this.XP = Main.setmgr.add(new Setting("XP Only", this, false));
        this.Crystal = Main.setmgr.add(new Setting("Crystal Only", this, false));
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.XP.getValBoolean() && (FastPlace.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle || FastPlace.mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemExpBottle)) {
            FastPlace.mc.field_71467_ac = (int)this.Delay.getValDouble();
        }
        if (this.Crystal.getValBoolean() && (FastPlace.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemEndCrystal || FastPlace.mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal)) {
            FastPlace.mc.field_71467_ac = (int)this.Delay.getValDouble();
        }
        if (!this.XP.getValBoolean() || !this.Crystal.getValBoolean()) {
            FastPlace.mc.field_71467_ac = (int)this.Delay.getValDouble();
        }
    }
}
