package Method.Client.module.command;

import java.awt.*;
import Method.Client.managers.*;
import Method.Client.utils.visual.*;

public class OpenDir extends Command
{
    public OpenDir() {
        super("Opendir");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            Desktop.getDesktop().open(FileManager.SaveDir);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Open Dir";
    }
    
    @Override
    public String getSyntax() {
        return "OpenDir";
    }
}
