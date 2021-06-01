package Method.Client.utils.system;

import Method.Client.utils.*;
import net.minecraft.client.network.*;
import java.util.*;
import Method.Client.utils.visual.*;
import io.netty.channel.*;

public class Connection extends ChannelDuplexHandler
{
    private final EventsHandler eventHandler;
    
    public Connection(final EventsHandler eventHandler) {
        this.eventHandler = eventHandler;
        try {
            final ChannelPipeline pipeline = Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_147298_b().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", (ChannelHandler)this);
        }
        catch (Exception exception) {
            ChatUtils.error("Connection: Error on attaching");
            exception.printStackTrace();
        }
    }
    
    public void channelRead(final ChannelHandlerContext ctx, final Object packet) throws Exception {
        if (!this.eventHandler.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }
    
    public void write(final ChannelHandlerContext ctx, final Object packet, final ChannelPromise promise) throws Exception {
        if (!this.eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }
    
    public enum Side
    {
        IN, 
        OUT;
    }
}
