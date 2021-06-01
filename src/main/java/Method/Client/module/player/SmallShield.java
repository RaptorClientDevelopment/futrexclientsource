package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.client.renderer.*;

public class SmallShield extends Module
{
    Setting MainHand;
    Setting OffHand;
    Setting armPitch;
    Setting armYaw;
    
    public SmallShield() {
        super("SmallShield", 0, Category.PLAYER, "SmallShield");
        this.MainHand = Main.setmgr.add(new Setting("MainHand", this, 1.0, 0.0, 2.0, true));
        this.OffHand = Main.setmgr.add(new Setting("OffHand", this, 2.0, 0.0, 2.0, true));
        this.armPitch = Main.setmgr.add(new Setting("Arm Pitch", this, 0.0, 90.0, 360.0, true));
        this.armYaw = Main.setmgr.add(new Setting("Arm Yaw", this, 0.0, 220.0, 360.0, true));
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        final ItemRenderer itemRenderer = SmallShield.mc.field_71460_t.field_78516_c;
        itemRenderer.field_187471_h = (float)(0.5 * this.OffHand.getValDouble());
        itemRenderer.field_187469_f = (float)(0.5 * this.MainHand.getValDouble());
        SmallShield.mc.field_71439_g.field_71155_g = (float)this.armPitch.getValDouble();
        SmallShield.mc.field_71439_g.field_71154_f = (float)this.armYaw.getValDouble();
    }
}
