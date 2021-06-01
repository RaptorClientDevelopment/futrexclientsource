package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import java.util.*;

public class Livestock extends Module
{
    public Setting Dye;
    public Setting Shear;
    public Setting Breed;
    
    public Livestock() {
        super("Livestock Mod", 0, Category.MISC, "Auto Sheepmod");
        this.Dye = Main.setmgr.add(new Setting("Auto Dye", this, true));
        this.Shear = Main.setmgr.add(new Setting("Auto Shear", this, false));
        this.Breed = Main.setmgr.add(new Setting("Auto Breed", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if ((Livestock.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemDye && this.Dye.getValBoolean()) || (this.Shear.getValBoolean() && Livestock.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemShears) || this.Breed.getValBoolean()) {
            for (final Entity e : Livestock.mc.field_71441_e.field_72996_f) {
                if (this.Breed.getValBoolean() && e instanceof EntityAnimal) {
                    final EntityAnimal animal = (EntityAnimal)e;
                    if (animal.func_110143_aJ() > 0.0f && !animal.func_70631_g_() && !animal.func_70880_s() && Livestock.mc.field_71439_g.func_70032_d((Entity)animal) <= 4.5f && animal.func_70877_b(Livestock.mc.field_71439_g.field_71071_by.func_70448_g())) {
                        Livestock.mc.field_71442_b.func_187097_a((EntityPlayer)Livestock.mc.field_71439_g, (Entity)animal, EnumHand.MAIN_HAND);
                    }
                }
                if (e instanceof EntitySheep) {
                    final EntitySheep sheep = (EntitySheep)e;
                    if (sheep.func_110143_aJ() <= 0.0f) {
                        continue;
                    }
                    if (this.Dye.getValBoolean()) {
                        if (sheep.func_175509_cj() == EnumDyeColor.func_176766_a(Livestock.mc.field_71439_g.field_71071_by.func_70448_g().func_77960_j())) {
                            continue;
                        }
                    }
                    else if (sheep.func_70892_o() || Livestock.mc.field_71439_g.func_70032_d((Entity)sheep) > 4.5f) {
                        continue;
                    }
                    Livestock.mc.field_71442_b.func_187097_a((EntityPlayer)Livestock.mc.field_71439_g, (Entity)sheep, EnumHand.MAIN_HAND);
                }
            }
        }
    }
}
