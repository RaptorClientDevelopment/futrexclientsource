package Method.Client.module.command;

import net.minecraft.client.*;

public abstract class Command
{
    private final String command;
    protected static Minecraft mc;
    
    public Command(final String command) {
        this.command = command;
    }
    
    public abstract void runCommand(final String p0, final String[] p1);
    
    public abstract String getDescription();
    
    public abstract String getSyntax();
    
    public String getCommand() {
        return this.command;
    }
    
    static {
        Command.mc = Minecraft.func_71410_x();
    }
}
