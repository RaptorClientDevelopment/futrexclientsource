package Method.Client.module.command;

import Method.Client.utils.visual.*;

public class WorldBorder extends Command
{
    public WorldBorder() {
        super("WorldBorder");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        try {
            final net.minecraft.world.border.WorldBorder worldBorder = WorldBorder.mc.field_71441_e.func_175723_af();
            ChatUtils.message("World border is at:\nMinX: " + worldBorder.func_177726_b() + "\nMinZ: " + worldBorder.func_177736_c() + "\nMaxX: " + worldBorder.func_177728_d() + "\nMaxZ: " + worldBorder.func_177733_e() + "\n");
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    @Override
    public String getDescription() {
        return "WorldBorder distance";
    }
    
    @Override
    public String getSyntax() {
        return "WorldBorder ";
    }
}
