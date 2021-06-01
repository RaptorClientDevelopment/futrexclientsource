package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.client.gui.*;
import Method.Client.utils.system.*;
import Method.Client.utils.Screens.Custom.Packet.*;
import java.util.*;

public class Antipacket extends Module
{
    Setting Gui;
    
    public Antipacket() {
        super("Antipacket", 0, Category.MISC, "Cancel Packets");
        this.Gui = Main.setmgr.add(new Setting("Gui", this, Main.AntiPacketgui));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        System.out.println(packet.toString());
        for (final AntiPacketPacket packet2 : AntiPacketGui.GetPackets()) {
            if (packet.getClass().isInstance(packet2.packet)) {
                return false;
            }
        }
        return true;
    }
}
