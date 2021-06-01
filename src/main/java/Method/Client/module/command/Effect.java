package Method.Client.module.command;

import Method.Client.utils.visual.*;
import net.minecraft.potion.*;
import java.util.*;

public class Effect extends Command
{
    public Effect() {
        super("effect");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                final int id = Integer.parseInt(args[1]);
                final int duration = Integer.parseInt(args[2]);
                final int amplifier = Integer.parseInt(args[3]);
                if (Potion.func_188412_a(id) == null) {
                    ChatUtils.error("Potion is null");
                    return;
                }
                this.addEffect(id, duration, amplifier);
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                final int id = Integer.parseInt(args[1]);
                if (Potion.func_188412_a(id) == null) {
                    ChatUtils.error("Potion is null");
                    return;
                }
                this.removeEffect(id);
            }
            else if (args[0].equalsIgnoreCase("clear")) {
                this.clearEffects();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    void addEffect(final int id, final int duration, final int amplifier) {
        Effect.mc.field_71439_g.func_70690_d(new PotionEffect((Potion)Objects.requireNonNull(Potion.func_188412_a(id)), duration, amplifier));
    }
    
    void removeEffect(final int id) {
        Effect.mc.field_71439_g.func_184589_d((Potion)Objects.requireNonNull(Potion.func_188412_a(id)));
    }
    
    void clearEffects() {
        for (final PotionEffect effect : Effect.mc.field_71439_g.func_70651_bq()) {
            Effect.mc.field_71439_g.func_184589_d(effect.func_188419_a());
        }
    }
    
    @Override
    public String getDescription() {
        return "Effect manager.";
    }
    
    @Override
    public String getSyntax() {
        return "effect <add/remove/clear> <id> <duration> <amplifier>";
    }
}
