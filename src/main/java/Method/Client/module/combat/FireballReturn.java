package Method.Client.module.combat;

import Method.Client.managers.*;
import net.minecraft.entity.projectile.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;

public class FireballReturn extends Module
{
    Setting yaw;
    Setting pitch;
    Setting range;
    public EntityFireball target;
    public TimerUtils timer;
    
    public FireballReturn() {
        super("FireballReturn", 0, Category.COMBAT, "Returns Fireballs to sender");
        this.yaw = Main.setmgr.add(new Setting("yaw", this, 25.0, 0.0, 50.0, false));
        this.pitch = Main.setmgr.add(new Setting("pitch", this, 25.0, 0.0, 50.0, false));
        this.range = Main.setmgr.add(new Setting("range", this, 10.0, 0.1, 10.0, false));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.updateTarget();
        this.attackTarget();
        super.onClientTick(event);
    }
    
    void updateTarget() {
        for (final Object object : FireballReturn.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityFireball) {
                final EntityFireball entity = (EntityFireball)object;
                if (!this.isInAttackRange(entity) || entity.field_70128_L || entity.field_70122_E || !entity.func_70075_an()) {
                    continue;
                }
                this.target = entity;
            }
        }
    }
    
    void attackTarget() {
        if (this.target == null) {
            return;
        }
        Utils.getNeededRotations(this.target.func_174791_d(), (float)this.yaw.getValDouble(), (float)this.pitch.getValDouble());
        final int currentCPS = Utils.random(4, 7);
        if (this.timer.isDelay(1000 / currentCPS)) {
            FireballReturn.mc.func_147116_af();
            this.timer.setLastMS();
            this.target = null;
        }
    }
    
    public boolean isInAttackRange(final EntityFireball entity) {
        return entity.func_70032_d((Entity)FireballReturn.mc.field_71439_g) <= this.range.getValDouble();
    }
}
