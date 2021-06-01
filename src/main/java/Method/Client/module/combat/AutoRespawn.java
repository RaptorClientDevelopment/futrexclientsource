package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.client.gui.*;
import Method.Client.utils.visual.*;

public class AutoRespawn extends Module
{
    Setting DeathCoords;
    Setting Delay;
    private TimerUtils timer;
    boolean canrespawn;
    
    public AutoRespawn() {
        super("AutoRespawn", 0, Category.COMBAT, "AutoRespawn");
        this.DeathCoords = Main.setmgr.add(new Setting("DeathCoords", this, true));
        this.Delay = Main.setmgr.add(new Setting("Delay", this, 2.0, 0.0, 50.0, true));
        this.timer = new TimerUtils();
        this.canrespawn = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.mc.field_71462_r instanceof GuiGameOver) {
            if (!this.canrespawn) {
                this.timer.reset();
                this.canrespawn = true;
            }
            if (this.timer.hasReached((float)(this.Delay.getValDouble() * 1000.0))) {
                this.timer.reset();
                AutoRespawn.mc.field_71439_g.func_71004_bE();
                Wrapper.mc.func_147108_a((GuiScreen)null);
                if (this.DeathCoords.getValBoolean()) {
                    ChatUtils.message(String.format("you have died at x %d y %d z %d", (int)AutoRespawn.mc.field_71439_g.field_70165_t, (int)AutoRespawn.mc.field_71439_g.field_70163_u, (int)AutoRespawn.mc.field_71439_g.field_70161_v));
                }
                this.canrespawn = false;
            }
        }
    }
}
