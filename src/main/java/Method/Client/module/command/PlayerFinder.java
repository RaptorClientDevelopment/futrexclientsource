package Method.Client.module.command;

import Method.Client.utils.system.*;
import net.minecraft.client.network.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class PlayerFinder extends Command
{
    public PlayerFinder() {
        super("pfind");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final ArrayList<String> list = new ArrayList<String>();
            if (args[0].equalsIgnoreCase("all")) {
                for (final NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175106_d()) {
                    list.add("\n" + npi.func_178845_a().getName());
                }
            }
            else if (args[0].equalsIgnoreCase("creatives")) {
                for (final NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175106_d()) {
                    if (npi.func_178848_b().func_77145_d()) {
                        list.add("\n" + npi.func_178845_a().getName());
                    }
                }
            }
            if (list.isEmpty()) {
                ChatUtils.error("List is empty.");
            }
            else {
                Wrapper.INSTANCE.copy(list.toString());
                ChatUtils.message("List copied to clipboard.");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "Get list of players.";
    }
    
    @Override
    public String getSyntax() {
        return "pfind <all/creatives>";
    }
}
