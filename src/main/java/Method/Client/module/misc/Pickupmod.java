package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import Method.Client.utils.system.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.network.play.server.*;

public class Pickupmod extends Module
{
    Setting Fast;
    Setting Antipickup;
    Setting RemoveDrops;
    Setting LongRange;
    Setting Distance;
    
    public Pickupmod() {
        super("Pickupmod", 0, Category.MISC, "Pickup tools");
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("Fast", this, true);
        this.Fast = setting;
        this.Fast = setmgr.add(setting);
        this.Antipickup = Main.setmgr.add(new Setting("Antipickup", this, false));
        this.RemoveDrops = Main.setmgr.add(new Setting("RemoveDrops", this, false));
        this.LongRange = Main.setmgr.add(new Setting("LongRange", this, true));
        this.Distance = Main.setmgr.add(new Setting("Distance", this, 4.0, 0.0, 10.0, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        for (final EntityItem entityItem : Pickupmod.mc.field_71441_e.func_72872_a((Class)EntityItem.class, Pickupmod.mc.field_71439_g.func_174813_aQ().func_72314_b(this.Distance.getValDouble(), this.Distance.getValDouble(), this.Distance.getValDouble()))) {
            if (this.Antipickup.getValBoolean()) {
                if (entityItem.field_70173_aa > 30) {
                    entityItem.field_70173_aa = 0;
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(entityItem.field_70165_t, entityItem.field_70163_u + 2.0, entityItem.field_70161_v, false));
                }
                entityItem.field_145802_g = "NULL";
                entityItem.field_70132_H = false;
                entityItem.field_70176_ah = 0;
                entityItem.field_70162_ai = 0;
                entityItem.field_70164_aj = 0;
                entityItem.field_71093_bK = 57;
                entityItem.lifespan = -1;
                entityItem.field_70123_F = false;
                entityItem.field_70124_G = false;
            }
            if (this.LongRange.getValBoolean() && entityItem.field_70173_aa > 30) {
                entityItem.field_70173_aa = 10;
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(entityItem.field_70165_t, entityItem.field_70163_u, entityItem.field_70161_v, Pickupmod.mc.field_71439_g.field_70122_E));
            }
            if (this.RemoveDrops.getValBoolean()) {
                entityItem.func_70106_y();
                entityItem.onRemovedFromWorld();
            }
            if (this.Fast.getValBoolean()) {
                entityItem.field_70173_aa = 45;
                entityItem.func_174868_q();
                entityItem.field_70123_F = true;
                entityItem.field_70124_G = true;
                entityItem.field_70132_H = true;
            }
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return !this.Antipickup.getValBoolean() || (!(packet instanceof PlayerEvent.ItemPickupEvent) && !(packet instanceof EntityItemPickupEvent) && !(packet instanceof SPacketCollectItem));
    }
    
    @Override
    public void onItemPickup(final EntityItemPickupEvent event) {
        if (this.Antipickup.getValBoolean()) {
            event.setCanceled(true);
        }
    }
}
