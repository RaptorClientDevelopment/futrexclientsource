package Method.Client.managers;

import Method.Client.module.command.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class CommandManager
{
    public static ArrayList<Command> commands;
    private static volatile CommandManager instance;
    public static char cmdPrefix;
    
    public CommandManager() {
        this.addCommands();
    }
    
    public void addCommands() {
        CommandManager.commands.add(new Help());
        CommandManager.commands.add(new VClip());
        CommandManager.commands.add(new OpenFolder());
        CommandManager.commands.add(new Login());
        CommandManager.commands.add(new FakePlayer());
        CommandManager.commands.add(new UsernameHistory());
        CommandManager.commands.add(new Say());
        CommandManager.commands.add(new PrefixChange());
        CommandManager.commands.add(new OpenGui());
        CommandManager.commands.add(new Effect());
        CommandManager.commands.add(new PlayerFinder());
        CommandManager.commands.add(new WorldSeed());
        CommandManager.commands.add(new Friend());
        CommandManager.commands.add(new ClearChat());
        CommandManager.commands.add(new OpenDir());
        CommandManager.commands.add(new Author());
        CommandManager.commands.add(new ResetGui());
        CommandManager.commands.add(new Yaw());
        CommandManager.commands.add(new Pitch());
        CommandManager.commands.add(new BedCoords());
        CommandManager.commands.add(new Drop());
        CommandManager.commands.add(new Peek());
        CommandManager.commands.add(new Vanish());
        CommandManager.commands.add(new StackSize());
        CommandManager.commands.add(new Hclip());
        CommandManager.commands.add(new Reset());
        CommandManager.commands.add(new Give());
        CommandManager.commands.add(new Hat());
        CommandManager.commands.add(new Head());
        CommandManager.commands.add(new Lore());
        CommandManager.commands.add(new Nbt());
        CommandManager.commands.add(new Rename());
        CommandManager.commands.add(new Repair());
        CommandManager.commands.add(new Tp());
        CommandManager.commands.add(new Profile());
    }
    
    public void runCommands(final String s) {
        final String readString = s.trim().substring(Character.toString(CommandManager.cmdPrefix).length()).trim();
        boolean commandResolved = false;
        final boolean hasArgs = readString.trim().contains(" ");
        final String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
        final String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];
        for (final Command command : CommandManager.commands) {
            if (command.getCommand().trim().equalsIgnoreCase(commandName.trim())) {
                command.runCommand(readString, args);
                commandResolved = true;
                break;
            }
        }
        if (!commandResolved) {
            ChatUtils.error("Cannot resolve internal command: §c" + commandName);
        }
    }
    
    public static CommandManager getInstance() {
        if (CommandManager.instance == null) {
            CommandManager.instance = new CommandManager();
        }
        return CommandManager.instance;
    }
    
    static {
        CommandManager.commands = new ArrayList<Command>();
        CommandManager.cmdPrefix = '@';
    }
}
