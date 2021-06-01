package Method.Client.module.combat;

import Method.Client.utils.*;
import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public class Criticals extends Module
{
    TimerUtils timer;
    boolean cancelSomePackets;
    Setting mode;
    Setting ShowCrit;
    
    public Criticals() {
        super("Auto Criticals", 0, Category.COMBAT, "Criticals on hit");
        this.timer = new TimerUtils();
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Packet", new String[] { "Packet", "Simple", "Groundspoof", "Jump", "Fpacket", "Bpacket", "Falldist", "MiniJump", "NBypass" }));
        this.ShowCrit = Main.setmgr.add(new Setting("ShowCrit", this, true));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (Criticals.mc.field_71439_g.field_70122_E) {
            if (side == Connection.Side.OUT) {
                if (packet instanceof CPacketUseEntity) {
                    final CPacketUseEntity attack = (CPacketUseEntity)packet;
                    if (attack.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                        if (this.mode.getValString().equalsIgnoreCase("Bpacket")) {
                            try {
                                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.11, Criticals.mc.field_71439_g.field_70161_v, true));
                                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 1.3579E-6, Criticals.mc.field_71439_g.field_70161_v, false));
                            }
                            catch (Exception ex) {}
                        }
                        if (this.mode.getValString().equalsIgnoreCase("NBypass")) {
                            Criticals.mc.field_71439_g.field_70181_x = 0.41999998688697815;
                            if (Criticals.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                                final EntityPlayerSP field_71439_g = Criticals.mc.field_71439_g;
                                field_71439_g.field_70181_x += (Objects.requireNonNull(Criticals.mc.field_71439_g.func_70660_b(MobEffects.field_76430_j)).func_76458_c() + 1) * 0.1f;
                            }
                            if (Criticals.mc.field_71439_g.func_70051_ag()) {
                                final float var1 = Criticals.mc.field_71439_g.field_70177_z * 0.017453292f;
                                final EntityPlayerSP field_71439_g2 = Criticals.mc.field_71439_g;
                                field_71439_g2.field_70159_w -= MathHelper.func_76126_a(var1) * 0.2f;
                                final EntityPlayerSP field_71439_g3 = Criticals.mc.field_71439_g;
                                field_71439_g3.field_70179_y += MathHelper.func_76134_b(var1) * 0.2f;
                            }
                            Criticals.mc.field_71439_g.field_70160_al = true;
                        }
                        if (this.mode.getValString().equalsIgnoreCase("Simple")) {
                            if (this.canJump()) {
                                Criticals.mc.field_71439_g.func_70634_a(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u - 0.5, Criticals.mc.field_71439_g.field_70161_v);
                                Criticals.mc.field_71439_g.func_70031_b(true);
                            }
                            else {
                                Criticals.mc.field_71439_g.field_70181_x = -0.1;
                            }
                        }
                        if (this.mode.getValString().equalsIgnoreCase("MiniJump")) {
                            Criticals.mc.field_71439_g.func_70664_aZ();
                            final EntityPlayerSP field_71439_g4 = Criticals.mc.field_71439_g;
                            field_71439_g4.field_70181_x -= 0.30000001192092896;
                        }
                        if (this.mode.getValString().equalsIgnoreCase("Fpacket")) {
                            this.doCritical();
                        }
                        if (this.mode.getValString().equalsIgnoreCase("Falldist")) {
                            final EntityPlayerSP field_71439_g5 = Criticals.mc.field_71439_g;
                            field_71439_g5.field_70181_x += 0.001;
                            Criticals.mc.field_71439_g.field_70143_R = 9999.0f;
                            Criticals.mc.field_71439_g.func_180430_e(20.0f, 0.0f);
                        }
                        if (this.mode.getValString().equalsIgnoreCase("Groundspoof")) {
                            Criticals.mc.field_71439_g.field_70122_E = false;
                            Criticals.mc.field_71439_g.field_70160_al = true;
                        }
                        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
                            if (Criticals.mc.field_71439_g.field_70124_G && this.timer.isDelay(500L)) {
                                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.0627, Criticals.mc.field_71439_g.field_70161_v, false));
                                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                                this.timer.setLastMS();
                                this.cancelSomePackets = true;
                            }
                        }
                        else if (this.mode.getValString().equalsIgnoreCase("Jump") && this.canJump()) {
                            Criticals.mc.field_71439_g.func_70664_aZ();
                        }
                        if (this.ShowCrit.getValBoolean()) {
                            final Entity entity = attack.func_149564_a((World)Criticals.mc.field_71441_e);
                            if (entity != null) {
                                Criticals.mc.field_71439_g.func_71009_b(entity);
                            }
                        }
                    }
                }
            }
            else if (this.mode.getValString().equalsIgnoreCase("Packet") && packet instanceof CPacketPlayer && this.cancelSomePackets) {
                return this.cancelSomePackets = false;
            }
        }
        return true;
    }
    
    private void doCritical() {
        if (Criticals.mc.field_71439_g.func_180799_ab() || Criticals.mc.field_71439_g.func_70090_H()) {
            return;
        }
        final double posX = Criticals.mc.field_71439_g.field_70165_t;
        final double posY = Criticals.mc.field_71439_g.field_70163_u;
        final double posZ = Criticals.mc.field_71439_g.field_70161_v;
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(posX, posY + 0.0625, posZ, false));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(posX, posY, posZ, false));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(posX, posY + 1.1E-5, posZ, false));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(posX, posY, posZ, false));
    }
    
    boolean canJump() {
        return !Criticals.mc.field_71439_g.func_70617_f_() && !Criticals.mc.field_71439_g.func_70090_H() && !Criticals.mc.field_71439_g.func_180799_ab() && !Criticals.mc.field_71439_g.func_70093_af() && !Criticals.mc.field_71439_g.func_184218_aH() && !Criticals.mc.field_71439_g.func_70644_a(MobEffects.field_76440_q);
    }
}
