package Method.Client.utils.system;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.network.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class Wrapper
{
    public static FontRenderer fr;
    public static volatile Wrapper INSTANCE;
    public static Minecraft mc;
    
    public Minecraft mc() {
        return Minecraft.func_71410_x();
    }
    
    public EntityPlayerSP player() {
        return Wrapper.INSTANCE.mc().field_71439_g;
    }
    
    public WorldClient world() {
        return Wrapper.INSTANCE.mc().field_71441_e;
    }
    
    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().field_71474_y;
    }
    
    public FontRenderer fontRenderer() {
        return Wrapper.INSTANCE.mc().field_71466_p;
    }
    
    public void sendPacket(final Packet packet) {
        this.player().field_71174_a.func_147297_a(packet);
    }
    
    public PlayerControllerMP controller() {
        return Wrapper.INSTANCE.mc().field_71442_b;
    }
    
    public void swingArm() {
        this.player().func_184609_a(EnumHand.MAIN_HAND);
    }
    
    public void attack(final Entity entity) {
        this.controller().func_78764_a((EntityPlayer)this.player(), entity);
    }
    
    public void copy(final String content) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
    }
    
    static {
        Wrapper.fr = Minecraft.func_71410_x().field_71466_p;
        Wrapper.INSTANCE = new Wrapper();
        Wrapper.mc = Minecraft.func_71410_x();
    }
}
