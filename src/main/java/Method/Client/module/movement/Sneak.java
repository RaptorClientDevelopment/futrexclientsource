package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import Method.Client.utils.system.*;

public class Sneak extends Module
{
    Setting mode;
    Setting Antisneak;
    Setting fullSprint;
    
    public Sneak() {
        super("Sneak", 0, Category.MOVEMENT, "Sneak");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Legit", new String[] { "Legit", "Packet" }));
        this.Antisneak = Main.setmgr.add(new Setting("Antisneak", this, false));
        this.fullSprint = Main.setmgr.add(new Setting("FullSprint", this, false, this.Antisneak, 2));
    }
    
    @Override
    public void onDisable() {
        if (this.mode.getValString().equalsIgnoreCase("legit")) {
            Sneak.mc.field_71474_y.field_74311_E.field_74513_e = false;
        }
        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
            Sneak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Sneak.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.Antisneak.getValBoolean()) {
            final EntityPlayerSP player = Sneak.mc.field_71439_g;
            final GameSettings settings = Wrapper.INSTANCE.mcSettings();
            if (player.field_70122_E && settings.field_74311_E.func_151470_d()) {
                if (!this.fullSprint.getValBoolean() && settings.field_74351_w.func_151470_d()) {
                    player.func_70031_b(Utils.isMoving((Entity)player));
                }
                else if (this.fullSprint.getValBoolean()) {
                    player.func_70031_b(Utils.isMoving((Entity)player));
                }
                if (settings.field_74366_z.func_151470_d() || settings.field_74370_x.func_151470_d() || settings.field_74368_y.func_151470_d()) {
                    if (settings.field_74368_y.func_151470_d()) {
                        final EntityPlayerSP entityPlayerSP = player;
                        entityPlayerSP.field_70159_w *= 1.268;
                        final EntityPlayerSP entityPlayerSP2 = player;
                        entityPlayerSP2.field_70179_y *= 1.268;
                    }
                    else {
                        final EntityPlayerSP entityPlayerSP3 = player;
                        entityPlayerSP3.field_70159_w *= 1.252;
                        final EntityPlayerSP entityPlayerSP4 = player;
                        entityPlayerSP4.field_70179_y *= 1.252;
                    }
                }
                else {
                    final EntityPlayerSP entityPlayerSP5 = player;
                    entityPlayerSP5.field_70159_w *= 1.2848;
                    final EntityPlayerSP entityPlayerSP6 = player;
                    entityPlayerSP6.field_70179_y *= 1.2848;
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Legit")) {
            Sneak.mc.field_71474_y.field_74311_E.field_74513_e = true;
        }
        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
            final EntityPlayerSP player = Sneak.mc.field_71439_g;
            if (!Utils.isMoving((Entity)Sneak.mc.field_71439_g)) {
                player.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
                player.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (Utils.isMoving((Entity)Sneak.mc.field_71439_g)) {
                player.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.STOP_SNEAKING));
                player.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            }
        }
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.Antisneak.getValBoolean() && side == Connection.Side.OUT && packet instanceof CPacketEntityAction) {
            final CPacketEntityAction p = (CPacketEntityAction)packet;
            return p.func_180764_b() != CPacketEntityAction.Action.STOP_SNEAKING;
        }
        return true;
    }
}
