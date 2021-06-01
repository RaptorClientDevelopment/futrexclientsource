package Method.Client.module.movement;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.client.entity.*;

public class AutoSwim extends Module
{
    Setting mode;
    
    public AutoSwim() {
        super("Auto Swim", 0, Category.MOVEMENT, "Swims for you");
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("Mode", this, "Dolphin", new String[] { "Dolphin", "Jump", "Fish" });
        this.mode = setting;
        this.mode = setmgr.add(setting);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!AutoSwim.mc.field_71439_g.func_70090_H() && !AutoSwim.mc.field_71439_g.func_180799_ab()) {
            return;
        }
        if (AutoSwim.mc.field_71439_g.func_70093_af() || Wrapper.INSTANCE.mcSettings().field_74314_A.func_151470_d()) {
            return;
        }
        if (this.mode.getValString().equalsIgnoreCase("Jump")) {
            AutoSwim.mc.field_71439_g.func_70664_aZ();
        }
        else if (this.mode.getValString().equalsIgnoreCase("Dolphin")) {
            final EntityPlayerSP field_71439_g = AutoSwim.mc.field_71439_g;
            field_71439_g.field_70181_x += 0.03999999910593033;
        }
        else if (this.mode.getValString().equalsIgnoreCase("Fish")) {
            final EntityPlayerSP field_71439_g2 = AutoSwim.mc.field_71439_g;
            field_71439_g2.field_70181_x += 0.019999999552965164;
        }
        super.onClientTick(event);
    }
}
