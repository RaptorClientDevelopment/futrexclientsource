package Method.Client.module.command;

import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import Method.Client.utils.visual.*;
import Method.Client.*;

public class OpenGui extends Command
{
    Thread t;
    
    public OpenGui() {
        super("Gui");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            OpenGui.mc.func_147108_a((GuiScreen)null);
            final Thread t = new Thread(new ThreadDemo());
            t.start();
            OpenGui.mc.field_71417_B.func_74373_b();
            ChatUtils.message(TextFormatting.GOLD + "Tried to open Gui");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Opens gui";
    }
    
    @Override
    public String getSyntax() {
        return "gui";
    }
    
    private static class ThreadDemo implements Runnable
    {
        @Override
        public void run() {
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            Command.mc.func_147108_a((GuiScreen)Main.ClickGui);
        }
    }
}
