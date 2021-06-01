package Method.Client.module.movement;

import Method.Client.managers.*;
import net.minecraft.entity.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.client.*;
import Method.Client.utils.visual.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.play.client.*;

public class EntityVanish extends Module
{
    Setting noDismountPlugin;
    Setting dismountEntity;
    Setting removeEntity;
    Setting respawnEntity;
    Setting sendMovePackets;
    Setting forceOnGround;
    Setting setMountPosition;
    private Entity originalRidingEntity;
    
    public EntityVanish() {
        super("EntityVanish", 0, Category.MOVEMENT, "Entity Vanish");
        this.noDismountPlugin = Main.setmgr.add(new Setting("no Dismount Plugin", this, true));
        this.dismountEntity = Main.setmgr.add(new Setting("dismoun tEntity", this, true));
        this.removeEntity = Main.setmgr.add(new Setting("remove Entity", this, true));
        this.respawnEntity = Main.setmgr.add(new Setting("respawn Entity", this, true));
        this.sendMovePackets = Main.setmgr.add(new Setting("send Move Packets", this, true));
        this.forceOnGround = Main.setmgr.add(new Setting("force On Ground", this, true));
        this.setMountPosition = Main.setmgr.add(new Setting("set MountPosition", this, true));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN) {
            if (packet instanceof SPacketSetPassengers && this.hasOriginalRidingEntity() && Minecraft.func_71410_x().field_71441_e != null) {
                final SPacketSetPassengers packetSetPassengers = (SPacketSetPassengers)packet;
                if (this.originalRidingEntity.equals((Object)Minecraft.func_71410_x().field_71441_e.func_73045_a(packetSetPassengers.func_186972_b()))) {
                    final OptionalInt isPlayerAPassenger = Arrays.stream(packetSetPassengers.func_186971_a()).filter(value -> Minecraft.func_71410_x().field_71441_e.func_73045_a(value) == Minecraft.func_71410_x().field_71439_g).findAny();
                    if (!isPlayerAPassenger.isPresent()) {
                        ChatUtils.message("You Have Been Dismounted.");
                        this.toggle();
                    }
                }
            }
            if (packet instanceof SPacketDestroyEntities) {
                final SPacketDestroyEntities packetDestroyEntities = (SPacketDestroyEntities)packet;
                final boolean isEntityNull = Arrays.stream(packetDestroyEntities.func_149098_c()).filter(value -> value == this.originalRidingEntity.func_145782_y()).findAny().isPresent();
                if (isEntityNull) {
                    ChatUtils.message("Your riding entity has been destroyed.");
                }
            }
        }
        if (side != Connection.Side.OUT || !this.noDismountPlugin.getValBoolean()) {
            return true;
        }
        if (packet instanceof CPacketPlayer.Position) {
            final CPacketPlayer.Position cPacketPlayer = (CPacketPlayer.Position)packet;
            Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(cPacketPlayer.field_149479_a, cPacketPlayer.field_149477_b, cPacketPlayer.field_149478_c, cPacketPlayer.field_149476_e, cPacketPlayer.field_149473_f, cPacketPlayer.field_149474_g));
            return false;
        }
        return !(packet instanceof CPacketPlayer) || packet instanceof CPacketPlayer.PositionRotation;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.originalRidingEntity = null;
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g != null && mc.field_71441_e != null) {
            if (mc.field_71439_g.func_184218_aH()) {
                this.originalRidingEntity = mc.field_71439_g.func_184187_bx();
                if (this.dismountEntity.getValBoolean()) {
                    mc.field_71439_g.func_184210_p();
                    ChatUtils.message("Dismounted entity.");
                }
                if (this.removeEntity.getValBoolean()) {
                    mc.field_71441_e.func_72900_e(this.originalRidingEntity);
                    ChatUtils.message("Removed entity from world.");
                }
            }
            else {
                ChatUtils.message("Please mount an entity before enabling this module.");
                this.toggle();
            }
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (EntityVanish.mc.field_71441_e != null && EntityVanish.mc.field_71439_g != null && !EntityVanish.mc.field_71439_g.func_184218_aH() && this.hasOriginalRidingEntity()) {
            if (this.forceOnGround.getValBoolean()) {
                EntityVanish.mc.field_71439_g.field_70122_E = true;
            }
            if (this.setMountPosition.getValBoolean()) {
                this.originalRidingEntity.func_70107_b(EntityVanish.mc.field_71439_g.field_70165_t, EntityVanish.mc.field_71439_g.field_70163_u, EntityVanish.mc.field_71439_g.field_70161_v);
            }
            if (this.sendMovePackets.getValBoolean()) {
                EntityVanish.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove(this.originalRidingEntity));
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.hasOriginalRidingEntity()) {
            final Minecraft mc = Minecraft.func_71410_x();
            if (this.respawnEntity.getValBoolean()) {
                this.originalRidingEntity.field_70128_L = false;
            }
            if (!mc.field_71439_g.func_184218_aH()) {
                mc.field_71441_e.func_72838_d(this.originalRidingEntity);
                mc.field_71439_g.func_184205_a(this.originalRidingEntity, true);
                ChatUtils.message("Spawned & mounted original entity.");
            }
            this.originalRidingEntity = null;
        }
    }
    
    private boolean hasOriginalRidingEntity() {
        return this.originalRidingEntity != null;
    }
}
