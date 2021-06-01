package Method.Client.module.command;

import Method.Client.clickgui.*;
import Method.Client.clickgui.component.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class ResetGui extends Command
{
    public ResetGui() {
        super("ResetGui");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            int xOffset = 5;
            for (final Frame frame : ClickGui.frames) {
                frame.setY(20);
                frame.setX(xOffset + 10);
                xOffset += frame.getWidth();
            }
            ChatUtils.message("Guireset!");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "ResetGui";
    }
    
    @Override
    public String getSyntax() {
        return "ResetGui";
    }
}
