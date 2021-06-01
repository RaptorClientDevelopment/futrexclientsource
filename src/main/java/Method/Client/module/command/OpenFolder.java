package Method.Client.module.command;

import java.awt.*;
import net.minecraft.client.*;
import Method.Client.utils.visual.*;

public class OpenFolder extends Command
{
    public OpenFolder() {
        super("OpenFolder ");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            Desktop.getDesktop().open(Minecraft.func_71410_x().field_71412_D);
            ChatUtils.message("Local .Minecraft Folder Opened");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Opens Folder for .minecraft";
    }
    
    @Override
    public String getSyntax() {
        return "OpenFolder";
    }
}
