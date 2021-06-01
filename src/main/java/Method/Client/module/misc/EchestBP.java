package Method.Client.module.misc;

import net.minecraft.client.gui.*;
import Method.Client.module.*;
import com.mojang.realmsclient.gui.*;
import Method.Client.utils.visual.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.inventory.*;

public class EchestBP extends Module
{
    private GuiScreen echestScreen;
    boolean EchestSet;
    boolean Tryrightclick;
    
    public EchestBP() {
        super("EchestBP", 0, Category.MISC, "EchestBP");
        this.echestScreen = null;
        this.EchestSet = false;
        this.Tryrightclick = false;
    }
    
    @Override
    public void onEnable() {
        this.EchestSet = false;
        this.Tryrightclick = false;
        ChatUtils.message(ChatFormatting.AQUA + " Open an Echest to start!");
    }
    
    @Override
    public void onDisable() {
        if (this.echestScreen != null) {
            EchestBP.mc.func_147108_a(this.echestScreen);
        }
        this.echestScreen = null;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketEntityAction) {
            final CPacketEntityAction pac = (CPacketEntityAction)packet;
            if (pac.func_180764_b().equals((Object)CPacketEntityAction.Action.OPEN_INVENTORY)) {
                return false;
            }
        }
        return !(packet instanceof CPacketCloseWindow);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (EchestBP.mc.field_71462_r instanceof GuiInventory) {
            EchestBP.mc.field_71442_b.func_78765_e();
        }
        if (EchestBP.mc.field_71462_r instanceof GuiContainer) {
            if (((GuiContainer)EchestBP.mc.field_71462_r).field_147002_h instanceof ContainerChest) {
                final Container inventorySlots = ((GuiContainer)EchestBP.mc.field_71462_r).field_147002_h;
                if (((ContainerChest)inventorySlots).func_85151_d() instanceof InventoryBasic && ((ContainerChest)inventorySlots).func_85151_d().func_70005_c_().equalsIgnoreCase("Ender Chest")) {
                    if (!this.EchestSet) {
                        this.EchestSet = true;
                        EchestBP.mc.field_71439_g.func_71053_j();
                    }
                    else {
                        this.echestScreen = EchestBP.mc.field_71462_r;
                        EchestBP.mc.field_71462_r = null;
                        ChatUtils.message(ChatFormatting.AQUA + "Done! To open please disable EchestBP");
                        Mouse.setGrabbed(true);
                    }
                }
            }
        }
        else if (this.EchestSet && !this.Tryrightclick) {
            this.Tryrightclick = true;
            EchestBP.mc.func_147121_ag();
        }
    }
}
