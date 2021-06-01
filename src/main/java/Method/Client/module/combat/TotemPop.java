package Method.Client.module.combat;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import com.mojang.realmsclient.gui.*;
import Method.Client.utils.visual.*;
import java.util.*;
import Method.Client.managers.*;

public class TotemPop extends Module
{
    public static HashMap<String, Integer> popList;
    Setting Friend;
    
    public TotemPop() {
        super("TotemPop", 0, Category.COMBAT, "TotemPop");
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("Friend", this, true);
        this.Friend = setting;
        this.Friend = setmgr.add(setting);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet2 = (SPacketEntityStatus)packet;
            if (packet2.func_149160_c() == 35) {
                final Entity entity = packet2.func_149161_a((World)TotemPop.mc.field_71441_e);
                this.pop(entity);
            }
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        for (final EntityPlayer player : TotemPop.mc.field_71441_e.field_73010_i) {
            if (player.func_110143_aJ() <= 0.0f && TotemPop.popList.containsKey(player.func_70005_c_())) {
                ChatUtils.message(ChatFormatting.RED + player.func_70005_c_() + " died after popping " + ChatFormatting.GREEN + TotemPop.popList.get(player.func_70005_c_()) + ChatFormatting.RED + " totems!");
                TotemPop.popList.remove(player.func_70005_c_(), TotemPop.popList.get(player.func_70005_c_()));
            }
        }
        super.onClientTick(event);
    }
    
    public void pop(final Entity entity) {
        if (TotemPop.mc.field_71439_g == null || TotemPop.mc.field_71441_e == null) {
            return;
        }
        if (TotemPop.popList == null) {
            TotemPop.popList = new HashMap<String, Integer>();
        }
        if (this.Friend.getValBoolean() || !FriendManager.isFriend(entity.func_70005_c_())) {
            if (TotemPop.popList.get(entity.func_70005_c_()) == null) {
                TotemPop.popList.put(entity.func_70005_c_(), 1);
                ChatUtils.message(ChatFormatting.RED + entity.func_70005_c_() + " popped " + ChatFormatting.YELLOW + 1 + ChatFormatting.RED + " totem!");
            }
            else {
                this.Check(entity);
            }
        }
    }
    
    private void Check(final Entity entity) {
        if (TotemPop.popList.get(entity.func_70005_c_()) != null) {
            int popCounter = TotemPop.popList.get(entity.func_70005_c_());
            TotemPop.popList.put(entity.func_70005_c_(), ++popCounter);
            ChatUtils.message(ChatFormatting.RED + entity.func_70005_c_() + ChatFormatting.RED + " popped " + ChatFormatting.YELLOW + ++popCounter + ChatFormatting.RED + " totems!");
        }
    }
    
    public static int getpops(final Entity entity) {
        if (TotemPop.popList.get(entity.func_70005_c_()) != null) {
            return TotemPop.popList.get(entity.func_70005_c_());
        }
        return 0;
    }
}
