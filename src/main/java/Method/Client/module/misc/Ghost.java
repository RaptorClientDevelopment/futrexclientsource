package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.Screens.Override.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Ghost extends Module
{
    public static Setting health;
    
    public Ghost() {
        super("Ghost", 0, Category.MISC, "Move While dead");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(Ghost.health = new Setting("health", this, 40.0, 0.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        DeathOverride.Override = true;
    }
    
    @Override
    public void onDisable() {
        DeathOverride.Override = false;
        if (DeathOverride.isDead) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            DeathOverride.isDead = false;
        }
    }
}
