package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;

public class Xcarry extends Module
{
    Setting Packetclose;
    
    public Xcarry() {
        super("Xcarry", 0, Category.PLAYER, "Xcarry or SecretClose!");
        this.Packetclose = Main.setmgr.add(new Setting("Fake close", this, false));
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (Xcarry.mc.field_71441_e != null) {
            Xcarry.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(Xcarry.mc.field_71439_g.field_71069_bz.field_75152_c));
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.Packetclose.getValBoolean() && packet instanceof CPacketEntityAction) {
            final CPacketEntityAction pac = (CPacketEntityAction)packet;
            if (pac.func_180764_b().equals((Object)CPacketEntityAction.Action.OPEN_INVENTORY)) {
                return false;
            }
        }
        return !(packet instanceof CPacketCloseWindow);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Xcarry.mc.field_71462_r instanceof GuiInventory) {
            Xcarry.mc.field_71442_b.func_78765_e();
        }
    }
}
