package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.living.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;

public class PortalMod extends Module
{
    Setting gui;
    Setting god;
    
    public PortalMod() {
        super("PortalMod", 0, Category.PLAYER, "PortalMod");
        this.gui = Main.setmgr.add(new Setting("gui", this, true));
        this.god = Main.setmgr.add(new Setting("god", this, true));
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.gui.getValBoolean()) {
            PortalMod.mc.field_71439_g.field_71087_bX = false;
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return !this.god.getValBoolean() || !(packet instanceof CPacketConfirmTeleport);
    }
}
