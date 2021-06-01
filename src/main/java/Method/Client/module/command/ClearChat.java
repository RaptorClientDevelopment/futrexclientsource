package Method.Client.module.command;

import Method.Client.utils.visual.*;

public class ClearChat extends Command
{
    public ClearChat() {
        super("Clear");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            ClearChat.mc.field_71456_v.func_146158_b().func_146231_a(true);
            ChatUtils.message("Cleared Chat");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Clears Chat";
    }
    
    @Override
    public String getSyntax() {
        return "Clear";
    }
}
