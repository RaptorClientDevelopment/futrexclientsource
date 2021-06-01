package Method.Client.module.command;

import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import Method.Client.utils.visual.*;

public class FakePlayer extends Command
{
    public FakePlayer() {
        super("FakePlayer");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final EntityOtherPlayerMP fake = new EntityOtherPlayerMP((World)FakePlayer.mc.field_71441_e, new GameProfile(UUID.randomUUID(), args[0]));
            fake.func_70107_b(FakePlayer.mc.field_71439_g.field_70165_t, FakePlayer.mc.field_71439_g.field_70163_u, FakePlayer.mc.field_71439_g.field_70161_v);
            FakePlayer.mc.field_71441_e.field_72996_f.add(fake);
            ChatUtils.message("Added Fake Player ");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "FakePlayer Spawner";
    }
    
    @Override
    public String getSyntax() {
        return "FakePlayer <Name>";
    }
}
