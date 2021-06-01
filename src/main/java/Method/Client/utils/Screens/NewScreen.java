package Method.Client.utils.Screens;

import Method.Client.utils.Screens.Override.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.event.world.*;

public class NewScreen
{
    public static ArrayList<Screen> Screens;
    
    public NewScreen() {
        addScreen(new EscInsert());
        addScreen(new SignInsert());
        addScreen(new ChestGuiInsert());
        addScreen(new BookInsert());
        addScreen(new DisconnectedInsert());
        addScreen(new ConnectingInsert());
        addScreen(new DeathOverride());
    }
    
    public static void addScreen(final Screen m) {
        NewScreen.Screens.add(m);
    }
    
    public static void GuiOpen(final GuiOpenEvent event) {
        for (final Screen m : NewScreen.Screens) {
            m.GuiOpen(event);
        }
    }
    
    public static void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        for (final Screen m : NewScreen.Screens) {
            m.GuiScreenEventPost(event);
        }
    }
    
    public static void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        for (final Screen m : NewScreen.Screens) {
            m.GuiScreenEventInit(event);
        }
    }
    
    public static void GuiScreenEventPre(final GuiScreenEvent.ActionPerformedEvent.Pre event) {
        for (final Screen m : NewScreen.Screens) {
            m.GuiScreenEventPre(event);
        }
    }
    
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        for (final Screen m : NewScreen.Screens) {
            m.onClientTick(event);
        }
    }
    
    public static void DrawScreenEvent(final GuiScreenEvent.DrawScreenEvent event) {
        for (final Screen m : NewScreen.Screens) {
            m.DrawScreenEvent(event);
        }
    }
    
    public static void onWorldUnload(final WorldEvent.Unload event) {
        for (final Screen m : NewScreen.Screens) {
            m.onWorldUnload(event);
        }
    }
    
    static {
        NewScreen.Screens = new ArrayList<Screen>();
    }
}
