package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FastLadder extends Module
{
    Setting mode;
    
    public FastLadder() {
        super("FastLadder", 0, Category.PLAYER, "Climb Faster");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Simple", new String[] { "Simple", "DOWN", "Skip" }));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!FastLadder.mc.field_71439_g.func_70617_f_() || (FastLadder.mc.field_71439_g.field_191988_bg == 0.0f && FastLadder.mc.field_71439_g.field_70702_br == 0.0f)) {
            return;
        }
        if (this.mode.getValString().equalsIgnoreCase("Skip") && FastLadder.mc.field_71439_g.func_70617_f_()) {
            FastLadder.mc.field_71439_g.func_70031_b(true);
            FastLadder.mc.field_71439_g.field_70122_E = true;
        }
        if (this.mode.getValString().equalsIgnoreCase("DOWN")) {
            while (FastLadder.mc.field_71439_g.func_70617_f_() && FastLadder.mc.field_71439_g.field_70181_x != 0.0) {
                FastLadder.mc.field_71439_g.func_70107_b(FastLadder.mc.field_71439_g.field_70165_t, FastLadder.mc.field_71439_g.field_70163_u + ((FastLadder.mc.field_71439_g.field_70181_x > 0.0) ? 1 : -1), FastLadder.mc.field_71439_g.field_70161_v);
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(FastLadder.mc.field_71439_g.field_70165_t, FastLadder.mc.field_71439_g.field_70163_u, FastLadder.mc.field_71439_g.field_70161_v, true));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("simple")) {
            FastLadder.mc.field_71439_g.field_70181_x = 0.16999999433755875;
        }
        super.onClientTick(event);
    }
}
