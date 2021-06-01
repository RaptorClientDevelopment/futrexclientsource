package Method.Client.module.misc;

import java.text.*;
import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import Method.Client.utils.visual.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;

public class VanishDetector extends Module
{
    private final HashMap<UUID, String> Hashmap;
    DecimalFormat decimalFormat;
    Setting EntityMove;
    Setting EntityBedloc;
    Setting StopRemove;
    Setting Soundpos;
    Setting BlockChanges;
    
    public VanishDetector() {
        super("VanishDetector", 0, Category.MISC, "Staff Vanish Detector");
        this.decimalFormat = new DecimalFormat("0.00");
        this.EntityMove = Main.setmgr.add(new Setting("EntityMove", this, false));
        this.EntityBedloc = Main.setmgr.add(new Setting("Entity Bed ", this, true, this.EntityMove, 1));
        this.StopRemove = Main.setmgr.add(new Setting("Stop Entity Remove", this, true));
        this.Soundpos = Main.setmgr.add(new Setting("Sound pos", this, false));
        this.BlockChanges = Main.setmgr.add(new Setting("BlockChanges", this, false));
        this.Hashmap = new HashMap<UUID, String>();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN) {
            if (this.EntityMove.getValBoolean() && packet instanceof SPacketEntity.S17PacketEntityLookMove) {
                final SPacketEntity.S17PacketEntityLookMove packet2 = (SPacketEntity.S17PacketEntityLookMove)packet;
                if (packet2.func_149065_a((World)VanishDetector.mc.field_71441_e) instanceof EntityOtherPlayerMP) {
                    ChatUtils.message("Player: " + packet2.func_149065_a((World)VanishDetector.mc.field_71441_e).func_70005_c_() + " pos X:" + this.decimalFormat.format(packet2.func_149065_a((World)VanishDetector.mc.field_71441_e).field_70165_t) + " Y: " + this.decimalFormat.format(packet2.func_149065_a((World)VanishDetector.mc.field_71441_e).field_70163_u) + " Z: " + this.decimalFormat.format(packet2.func_149065_a((World)VanishDetector.mc.field_71441_e).field_70161_v));
                    if (this.EntityBedloc.getValBoolean()) {
                        ChatUtils.message("Player: " + packet2.func_149065_a((World)VanishDetector.mc.field_71441_e).func_70005_c_() + " Bed " + ((EntityPlayer)packet2.func_149065_a((World)VanishDetector.mc.field_71441_e)).field_71081_bT);
                    }
                }
            }
            if (this.Soundpos.getValBoolean() && packet instanceof SPacketSoundEffect) {
                final SPacketSoundEffect packet3 = (SPacketSoundEffect)packet;
                if (packet3.func_186977_b() == SoundCategory.PLAYERS && ((SPacketSoundEffect)packet).field_149218_c < 250) {
                    boolean found = false;
                    for (final Entity entity : VanishDetector.mc.field_71441_e.field_72996_f) {
                        if (Math.sqrt(Math.pow(entity.field_70165_t - packet3.field_149217_b, 2.0) + Math.pow(entity.field_70163_u - packet3.field_149218_c, 2.0) + Math.pow(entity.field_70161_v - packet3.field_149215_d, 2.0)) < 8.0) {
                            found = true;
                            break;
                        }
                    }
                    for (final TileEntity entity2 : VanishDetector.mc.field_71441_e.field_147482_g) {
                        if (Math.sqrt(Math.pow(entity2.func_174877_v().field_177962_a - packet3.field_149217_b, 2.0) + Math.pow(entity2.func_174877_v().field_177960_b - packet3.field_149218_c, 2.0) + Math.pow(entity2.func_174877_v().field_177961_c - packet3.field_149215_d, 2.0)) < 8.0) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ChatUtils.message("Sound near: X: " + packet3.field_149217_b + " Y: " + packet3.field_149218_c + " Z: " + packet3.field_149215_d);
                    }
                }
            }
            if (packet instanceof SPacketDestroyEntities && this.StopRemove.getValBoolean()) {
                return false;
            }
            if (this.BlockChanges.getValBoolean() && packet instanceof SPacketBlockChange) {
                final SPacketBlockChange packet4 = (SPacketBlockChange)packet;
                boolean found = false;
                for (final Entity entity : VanishDetector.mc.field_71441_e.field_72996_f) {
                    if (Math.sqrt(Math.pow(entity.field_70165_t - packet4.func_179827_b().field_177962_a, 2.0) + Math.pow(entity.field_70163_u - packet4.func_179827_b().field_177960_b, 2.0) + Math.pow(entity.field_70161_v - packet4.func_179827_b().field_177961_c, 2.0)) < 8.0) {
                        found = true;
                        break;
                    }
                }
                for (final TileEntity entity2 : VanishDetector.mc.field_71441_e.field_147482_g) {
                    if (Math.sqrt(Math.pow(entity2.func_174877_v().field_177962_a - packet4.func_179827_b().field_177962_a, 2.0) + Math.pow(entity2.func_174877_v().field_177960_b - packet4.func_179827_b().field_177960_b, 2.0) + Math.pow(entity2.func_174877_v().field_177961_c - packet4.func_179827_b().field_177961_c, 2.0)) < 8.0) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    ChatUtils.message("BlockChange: X: " + packet4.func_179827_b().field_177962_a + " Y: " + packet4.func_179827_b().field_177960_b + " Z: " + packet4.func_179827_b().field_177961_c);
                }
            }
        }
        if (packet instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem sPacketPlayerListItem = (SPacketPlayerListItem)packet;
            if (sPacketPlayerListItem.func_179768_b() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
                final HashSet<UUID> set = new HashSet<UUID>();
                for (final SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.func_179767_a()) {
                    set.add(addPlayerData.func_179962_a().getId());
                }
                final Iterator<UUID> iterator6;
                UUID uuid;
                new Thread(() -> {
                    set.iterator();
                    while (iterator6.hasNext()) {
                        uuid = iterator6.next();
                        this.Hashmap.put(uuid, uuid.toString());
                    }
                    return;
                }).start();
            }
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        synchronized (this.Hashmap) {
            for (final Map.Entry<UUID, String> entry : this.Hashmap.entrySet()) {
                ChatUtils.message("PlayerPreviewElement " + entry.getValue() + " has gone into vanish (" + entry.getKey() + ")");
            }
            this.Hashmap.clear();
        }
    }
}
