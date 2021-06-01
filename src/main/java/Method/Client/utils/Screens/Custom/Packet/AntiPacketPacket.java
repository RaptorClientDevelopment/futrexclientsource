package Method.Client.utils.Screens.Custom.Packet;

import net.minecraft.network.*;

public class AntiPacketPacket
{
    public Packet packet;
    public boolean visable;
    
    public AntiPacketPacket(final Packet packet) {
        this.packet = packet;
    }
}
