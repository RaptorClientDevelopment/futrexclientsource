package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Regen extends Module
{
    Setting mode;
    Setting packets;
    
    public Regen() {
        super("Regen", 0, Category.COMBAT, "Regen");
        this.mode = Main.setmgr.add(new Setting("Regen Mode", this, "Vanilla", new String[] { "Vanilla", "Packet" }));
        this.packets = Main.setmgr.add(new Setting("packets", this, 20.0, 20.0, 200.0, false, this.mode, "Packet", 2));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Packet") && Regen.mc.field_71439_g.func_110143_aJ() < Regen.mc.field_71439_g.func_110138_aP() && Regen.mc.field_71439_g.func_71024_bL().func_75116_a() > 1) {
            for (int i = 0; i < this.packets.getValDouble(); ++i) {
                Regen.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer());
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla") && Regen.mc.field_71439_g.func_110143_aJ() < 20.0f) {
            Regen.mc.field_71428_T.field_194149_e = 0.8f;
            Regen.mc.field_71439_g.func_70606_j(20.0f);
        }
        super.onClientTick(event);
    }
    
    @Override
    public void onDisable() {
        if (this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            Regen.mc.field_71428_T.field_194149_e = 1.0f;
        }
    }
}
