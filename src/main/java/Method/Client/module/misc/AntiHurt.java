package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;

public class AntiHurt extends Module
{
    Setting mode;
    
    public AntiHurt() {
        super("AntiHurt", 0, Category.MISC, "Anti Hurt on some instance");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Packet", new String[] { "Death", "Packet" }));
    }
    
    @Override
    public void onEnable() {
        if (this.mode.getValString().equalsIgnoreCase("Death") && AntiHurt.mc.field_71439_g != null) {
            AntiHurt.mc.field_71439_g.field_70128_L = true;
            AntiHurt.mc.field_71474_y.field_74351_w.func_151468_f();
        }
    }
    
    @Override
    public void onDisable() {
        AntiHurt.mc.field_71439_g.field_70128_L = false;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return !this.mode.getValString().equalsIgnoreCase("Packet") || !(packet instanceof SPacketUpdateHealth);
    }
}
