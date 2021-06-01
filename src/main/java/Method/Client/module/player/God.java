package Method.Client.module.player;

import Method.Client.managers.*;
import net.minecraft.entity.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import Method.Client.utils.system.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.*;

public class God extends Module
{
    Setting mode;
    Setting Footsteps;
    private Entity riding;
    
    public God() {
        super("God", 0, Category.PLAYER, "Take no damage in certain situations");
        this.mode = Main.setmgr.add(new Setting("God mode", this, "Vanilla", new String[] { "Vanilla", "TickMode", "Riding" }));
        this.Footsteps = Main.setmgr.add(new Setting("Footsteps", this, false, this.mode, "Riding", 1));
    }
    
    @Override
    public void onEnable() {
        if (this.mode.getValString().equalsIgnoreCase("Riding") && God.mc.field_71439_g.func_184187_bx() != null) {
            this.riding = God.mc.field_71439_g.func_184187_bx();
            God.mc.field_71439_g.func_184210_p();
            God.mc.field_71441_e.func_72900_e(this.riding);
            God.mc.field_71439_g.func_70107_b((double)God.mc.field_71439_g.func_180425_c().func_177958_n(), (double)(God.mc.field_71439_g.func_180425_c().func_177956_o() - 1), (double)God.mc.field_71439_g.func_180425_c().func_177952_p());
        }
    }
    
    @Override
    public void onDisable() {
        if (this.mode.getValString().equalsIgnoreCase("Riding") && this.riding != null) {
            God.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(this.riding, EnumHand.MAIN_HAND));
        }
        if (this.mode.getValString().equalsIgnoreCase("TickMode")) {
            God.mc.field_71439_g.func_71004_bE();
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("Riding") && side == Connection.Side.OUT) {
            if (packet instanceof CPacketUseEntity) {
                final CPacketUseEntity packet2 = (CPacketUseEntity)packet;
                if (this.riding != null) {
                    final Entity entity = packet2.func_149564_a((World)God.mc.field_71441_e);
                    if (entity != null) {
                        this.riding.field_70165_t = entity.field_70165_t;
                        this.riding.field_70163_u = entity.field_70163_u;
                        this.riding.field_70161_v = entity.field_70161_v;
                        this.riding.field_70177_z = God.mc.field_71439_g.field_70177_z;
                        this.Movepackets(God.mc);
                    }
                }
            }
            if (packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.PositionRotation) {
                return false;
            }
        }
        return !(packet instanceof CPacketConfirmTeleport) || !this.mode.getValString().equalsIgnoreCase("Vanilla");
    }
    
    private void Movepackets(final Minecraft mc) {
        mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, true));
        mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketInput(mc.field_71439_g.field_71158_b.field_192832_b, mc.field_71439_g.field_71158_b.field_78902_a, false, false));
        mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove(this.riding));
    }
    
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Riding") && this.riding != null) {
            this.riding.field_70165_t = God.mc.field_71439_g.field_70165_t;
            this.riding.field_70163_u = God.mc.field_71439_g.field_70163_u + (this.Footsteps.getValBoolean() ? 0.3f : 0.0f);
            this.riding.field_70161_v = God.mc.field_71439_g.field_70161_v;
            this.riding.field_70177_z = God.mc.field_71439_g.field_70177_z;
            this.Movepackets(God.mc);
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("TickMode")) {
            God.mc.field_71439_g.func_70606_j(20.0f);
            God.mc.field_71439_g.func_71024_bL().func_75114_a(20);
            God.mc.field_71439_g.field_70128_L = false;
            if (God.mc.field_71462_r instanceof GuiGameOver) {
                God.mc.func_147108_a((GuiScreen)null);
            }
        }
        if (God.mc.field_71462_r instanceof GuiGameOver && this.mode.getValString().equalsIgnoreCase("Tickmode")) {
            try {
                God.mc.field_71439_g.func_71004_bE();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
