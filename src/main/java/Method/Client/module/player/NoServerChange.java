package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class NoServerChange extends Module
{
    Setting Inventory;
    Setting Rotate;
    
    public NoServerChange() {
        super("NoServerChange", 0, Category.PLAYER, "NoServerChange");
        this.Inventory = Main.setmgr.add(new Setting("Held Item Change", this, false));
        this.Rotate = Main.setmgr.add(new Setting("Rotate", this, true));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof SPacketSetSlot && this.Inventory.getValBoolean()) {
            final int currentSlot = NoServerChange.mc.field_71439_g.field_71071_by.field_70461_c;
            final SPacketSetSlot packet2 = (SPacketSetSlot)packet;
            if (packet2.func_149173_d() != currentSlot) {
                Objects.requireNonNull(NoServerChange.mc.field_71453_ak).func_179290_a((Packet)new CPacketHeldItemChange(currentSlot));
                NoServerChange.MC.field_71474_y.field_74313_G.field_74513_e = true;
                return false;
            }
        }
        if (packet instanceof SPacketPlayerPosLook && this.Rotate.getValBoolean()) {
            final SPacketPlayerPosLook packet3 = (SPacketPlayerPosLook)packet;
            if (NoServerChange.mc.field_71439_g != null) {
                packet3.field_148936_d = NoServerChange.mc.field_71439_g.field_70177_z;
                packet3.field_148937_e = NoServerChange.mc.field_71439_g.field_70125_A;
            }
        }
        return true;
    }
}
