package Method.Client.module.misc;

import Method.Client.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import joptsimple.internal.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class PluginsGetter extends Module
{
    public PluginsGetter() {
        super("PluginsGetter", 0, Category.MISC, "Trys Plugins Getter");
    }
    
    @Override
    public void onEnable() {
        if (PluginsGetter.mc.field_71439_g == null) {
            return;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketTabComplete("/", (BlockPos)null, false));
        super.onEnable();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketTabComplete) {
            final SPacketTabComplete s3APacketTabComplete = (SPacketTabComplete)packet;
            final List<String> plugins = new ArrayList<String>();
            final String[] func_149630_c;
            final String[] commands = func_149630_c = s3APacketTabComplete.func_149630_c();
            for (final String s : func_149630_c) {
                final String[] command = s.split(":");
                if (command.length > 1) {
                    final String pluginName = command[0].replace("/", "");
                    if (!plugins.contains(pluginName)) {
                        plugins.add(pluginName);
                    }
                }
            }
            Collections.sort(plugins);
            if (!plugins.isEmpty()) {
                ChatUtils.message("Plugins §7(§8" + plugins.size() + "§7): §9" + Strings.join((String[])plugins.toArray(new String[0]), "§7, §9"));
            }
            else {
                ChatUtils.error("No plugins found.");
            }
            this.toggle();
        }
        return true;
    }
}
