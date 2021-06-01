package Method.Client.utils.proxy;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import Method.Client.module.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.event.*;
import Method.Client.utils.proxy.Overrides.*;
import net.minecraft.client.resources.*;

@Mod.EventBusSubscriber(value = { Side.CLIENT }, modid = "futurex")
public class ClientProxy implements IProxy
{
    public static Module Gl;
    protected static Minecraft mc;
    
    @Override
    public void init(final FMLInitializationEvent event) {
        ColorMix.replaceRenderers();
        ViewBobOverride();
    }
    
    public static void ViewBobOverride() {
        ClientProxy.mc.field_71460_t = new EntityRenderMixin(ClientProxy.mc, (IResourceManager)ClientProxy.mc.field_110451_am);
    }
    
    static {
        ClientProxy.mc = Minecraft.func_71410_x();
    }
}
