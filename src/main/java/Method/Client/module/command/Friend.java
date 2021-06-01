package Method.Client.module.command;

import net.minecraft.entity.player.*;
import Method.Client.utils.*;
import Method.Client.managers.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class Friend extends Command
{
    public Friend() {
        super("friend");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equalsIgnoreCase("all")) {
                    for (final Object object : Friend.mc.field_71441_e.field_72996_f) {
                        if (object instanceof EntityPlayer) {
                            final EntityPlayer player = (EntityPlayer)object;
                            if (player.func_82150_aj()) {
                                continue;
                            }
                            FriendManager.addFriend(Utils.getPlayerName(player));
                        }
                    }
                }
                else {
                    FriendManager.addFriend(args[1]);
                }
            }
            if (args[0].equalsIgnoreCase("list")) {
                ChatUtils.message(FriendManager.friendsList.toString());
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                FriendManager.removeFriend(args[1]);
            }
            else if (args[0].equalsIgnoreCase("clear")) {
                FriendManager.clear();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Friend manager.";
    }
    
    @Override
    public String getSyntax() {
        return "friend <add/remove/list/clear> <nick>";
    }
}
