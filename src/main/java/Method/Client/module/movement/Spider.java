package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;

public class Spider extends Module
{
    Setting mode;
    Setting Speed;
    public boolean shouldjump;
    public float forcedYaw;
    public float forcedPitch;
    
    public Spider() {
        super("Spider", 0, Category.MOVEMENT, "Climb Walls");
        this.mode = Main.setmgr.add(new Setting("Spider mode", this, "Vanilla", new String[] { "NCP", "DEV", "Root", "Vanilla" }));
        this.Speed = Main.setmgr.add(new Setting("Speed", this, 0.2, 0.0, 1.0, false, this.mode, "Vanilla", 1));
        this.shouldjump = true;
    }
    
    @Override
    public void onEnable() {
        this.shouldjump = true;
    }
    
    @Override
    public void onDisable() {
        this.shouldjump = true;
        Spider.mc.field_71439_g.field_70138_W = 0.5f;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Vanilla") && !Spider.mc.field_71439_g.func_70617_f_() && Spider.mc.field_71439_g.field_70123_F && Spider.mc.field_71439_g.field_70181_x < 0.2) {
            Spider.mc.field_71439_g.field_70181_x = this.Speed.getValDouble();
        }
        if (this.mode.getValString().equalsIgnoreCase("Root") && Spider.mc.field_71439_g.field_70123_F && Spider.mc.field_71439_g.field_70173_aa % 4 == 0) {
            Spider.mc.field_71439_g.field_70181_x = 0.25;
        }
        if (this.mode.getValString().equalsIgnoreCase("DEV") && Spider.mc.field_71439_g.field_70123_F) {
            if (Spider.mc.field_71439_g.field_70173_aa % 4 == 0) {
                Spider.mc.field_71439_g.field_70181_x = 0.5;
            }
            else {
                Spider.mc.field_71439_g.field_70181_x = -0.01;
            }
        }
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("NCP")) {
            if (packet instanceof CPacketKeepAlive) {
                final CPacketKeepAlive packet2 = (CPacketKeepAlive)packet;
                if (Spider.mc.field_71439_g.field_70123_F) {
                    final EntityPlayerSP field_71439_g = Spider.mc.field_71439_g;
                    field_71439_g.field_70159_w *= 0.0;
                    final EntityPlayerSP field_71439_g2 = Spider.mc.field_71439_g;
                    field_71439_g2.field_70179_y *= 0.0;
                    if (this.shouldjump) {
                        Spider.mc.field_71439_g.func_70664_aZ();
                        this.shouldjump = false;
                    }
                    if (Spider.mc.field_71439_g.field_70143_R > 0.0f) {
                        Spider.mc.field_71439_g.func_70107_b(Spider.mc.field_71439_g.field_70165_t, Spider.mc.field_71439_g.field_70163_u + 0.08, Spider.mc.field_71439_g.field_70161_v);
                        Spider.mc.field_71474_y.field_74314_A.field_74513_e = false;
                        Spider.mc.field_71439_g.field_70122_E = true;
                        Spider.mc.field_71439_g.field_70138_W = 2.0f;
                    }
                    else {
                        Spider.mc.field_71439_g.field_70138_W = 0.5f;
                    }
                }
                else {
                    this.forcedYaw = Spider.mc.field_71439_g.field_70177_z;
                    this.forcedPitch = Spider.mc.field_71439_g.field_70125_A;
                    this.shouldjump = true;
                    Spider.mc.field_71439_g.field_70138_W = 0.5f;
                }
            }
            if (packet instanceof CPacketPlayer) {
                final CPacketPlayer packet3 = (CPacketPlayer)packet;
                packet3.field_149474_g = true;
                packet3.field_149476_e = this.forcedYaw;
                packet3.field_149473_f = this.forcedPitch;
            }
        }
        return true;
    }
}
