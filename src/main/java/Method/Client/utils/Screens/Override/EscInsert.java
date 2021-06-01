package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import Method.Client.utils.system.*;
import Method.Client.*;

public class EscInsert extends Screen
{
    boolean Disconnect;
    ServerData lastserver;
    
    public EscInsert() {
        this.Disconnect = false;
    }
    
    @Override
    public void GuiScreenEventPre(final GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiIngameMenu && event.getButton().field_146127_k == 1 && !(EscInsert.mc.field_71462_r instanceof GuiYesNo)) {
            EscInsert.mc.func_147108_a((GuiScreen)new GuiYesNo((GuiYesNoCallback)event.getGui(), "Disconnect", "Are you sure?", 1));
            event.setCanceled(this.Disconnect = true);
        }
    }
    
    @Override
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiIngameMenu) {
            event.getButtonList().add(new GuiButton(554, event.getGui().field_146294_l / 2 - 150, event.getGui().field_146295_m / 4 + 32, 50, 20, "Relog"));
            event.getButtonList().add(new GuiButton(555, event.getGui().field_146294_l / 2 - 150, event.getGui().field_146295_m / 4 + 56, 50, 20, "Download"));
            event.getButtonList().add(new GuiButton(556, event.getGui().field_146294_l / 2 - 150, event.getGui().field_146295_m / 4 + 80, 50, 20, "ClickGui"));
        }
    }
    
    @Override
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiYesNo && this.Disconnect) {
            if (event.getButton().field_146127_k == 0) {
                this.Disconnect = false;
                EscInsert.mc.field_71441_e.func_72882_A();
                EscInsert.mc.func_71403_a((WorldClient)null);
                EscInsert.mc.func_147108_a((GuiScreen)new GuiMainMenu());
            }
            if (event.getButton().field_146127_k == 1) {
                EscInsert.mc.func_147108_a((GuiScreen)new GuiIngameMenu());
            }
        }
        if (event.getGui() instanceof GuiIngameMenu) {
            if (event.getButton().field_146127_k == 554 && !EscInsert.mc.func_71387_A()) {
                this.lastserver = EscInsert.mc.func_147104_D();
                this.Disconnect = false;
                EscInsert.mc.field_71441_e.func_72882_A();
                EscInsert.mc.func_71403_a((WorldClient)null);
                Wrapper.INSTANCE.mc().func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
                final ServerAddress serveraddress = ServerAddress.func_78860_a(this.lastserver.field_78845_b);
                EscInsert.mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), EscInsert.mc, this.lastserver.field_78845_b, serveraddress.func_78864_b()));
            }
            if (event.getButton().field_146127_k == 555) {
                if (!WorldDownloader.Saving) {
                    WorldDownloader.start();
                }
                else {
                    WorldDownloader.stop();
                }
            }
            if (event.getButton().field_146127_k == 556) {
                EscInsert.mc.func_147108_a((GuiScreen)Main.ClickGui);
            }
        }
    }
}
