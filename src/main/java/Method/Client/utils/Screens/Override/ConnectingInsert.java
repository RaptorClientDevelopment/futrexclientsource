package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.multiplayer.*;
import com.mojang.realmsclient.gui.*;

public class ConnectingInsert extends Screen
{
    boolean IsguiConnecting;
    double starttime;
    public static ServerData Currentserver;
    
    public ConnectingInsert() {
        this.IsguiConnecting = false;
    }
    
    @Override
    public void DrawScreenEvent(final GuiScreenEvent.DrawScreenEvent event) {
        if (event.getGui() instanceof GuiConnecting && this.IsguiConnecting) {
            final ServerAddress serveraddress = ServerAddress.func_78860_a(ConnectingInsert.Currentserver.field_78845_b);
            event.getGui().func_73732_a(event.getGui().field_146289_q, "Connecting to: " + ConnectingInsert.Currentserver.field_78845_b + " Port: " + serveraddress.func_78864_b(), event.getGui().field_146294_l / 2, event.getGui().field_146295_m / 2 - 10, 11184810);
            event.getGui().func_73732_a(event.getGui().field_146289_q, "Time Taken: " + ChatFormatting.GOLD + (System.currentTimeMillis() - this.starttime) + ChatFormatting.RESET + " ms", event.getGui().field_146294_l / 2, event.getGui().field_146295_m / 2 - 30, 11184810);
        }
    }
    
    @Override
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiConnecting) {
            this.starttime = System.currentTimeMillis();
            this.IsguiConnecting = true;
            ConnectingInsert.Currentserver = ConnectingInsert.mc.func_147104_D();
        }
        else {
            this.IsguiConnecting = false;
        }
    }
}
