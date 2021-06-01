package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.event.entity.living.*;

public class FreeCam extends Module
{
    public Entity301 entity301;
    Setting ShowPlayer;
    Setting Speed;
    Setting Tp;
    
    public FreeCam() {
        super("FreeCam", 0, Category.PLAYER, "FreeCam");
        this.entity301 = null;
        this.ShowPlayer = Main.setmgr.add(new Setting("ShowPlayer", this, true));
        this.Speed = Main.setmgr.add(new Setting("Speed", this, 1.0, 0.0, 3.0, false));
        this.Tp = Main.setmgr.add(new Setting("Tp", this, false));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return (side != Connection.Side.IN || !(packet instanceof SPacketPlayerPosLook)) && (side != Connection.Side.OUT || !(packet instanceof CPacketPlayer));
    }
    
    @Override
    public void onEnable() {
        if (FreeCam.mc.field_71439_g != null && FreeCam.mc.field_71441_e != null && !this.Tp.getValBoolean()) {
            (this.entity301 = new Entity301((World)FreeCam.mc.field_71441_e, FreeCam.mc.field_71439_g.func_146103_bH())).func_70107_b(FreeCam.mc.field_71439_g.field_70165_t, FreeCam.mc.field_71439_g.field_70163_u, FreeCam.mc.field_71439_g.field_70161_v);
            this.entity301.field_71071_by = FreeCam.mc.field_71439_g.field_71071_by;
            this.entity301.field_70125_A = FreeCam.mc.field_71439_g.field_70125_A;
            this.entity301.field_70177_z = FreeCam.mc.field_71439_g.field_70177_z;
            this.entity301.field_70759_as = FreeCam.mc.field_71439_g.field_70759_as;
            if (this.ShowPlayer.getValBoolean()) {
                FreeCam.mc.field_71441_e.func_72838_d((Entity)this.entity301);
            }
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        if (this.entity301 != null && FreeCam.mc.field_71441_e != null) {
            FreeCam.mc.field_71439_g.func_70107_b(this.entity301.field_70165_t, this.entity301.field_70163_u, this.entity301.field_70161_v);
            FreeCam.mc.field_71439_g.field_70125_A = this.entity301.field_70125_A;
            FreeCam.mc.field_71439_g.field_70177_z = this.entity301.field_70177_z;
            FreeCam.mc.field_71439_g.field_70759_as = this.entity301.field_70759_as;
            FreeCam.mc.field_71441_e.func_72900_e((Entity)this.entity301);
            this.entity301 = null;
        }
        FreeCam.mc.field_71439_g.field_70145_X = false;
        super.onDisable();
    }
    
    @Override
    public void SetOpaqueCubeEvent(final SetOpaqueCubeEvent event) {
        event.setCanceled(true);
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (FreeCam.mc.field_71439_g.field_70725_aQ > 0 || FreeCam.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
            this.toggle();
            return;
        }
        final EntityPlayerSP player = FreeCam.mc.field_71439_g;
        player.field_71075_bZ.field_75100_b = false;
        player.field_70181_x = 0.0;
        player.field_70179_y = 0.0;
        player.field_70159_w = 0.0;
        final double[] directionSpeedVanilla = Utils.directionSpeed(this.Speed.getValDouble());
        player.field_70747_aH = (float)this.Speed.getValDouble();
        if (FreeCam.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f || FreeCam.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f) {
            final EntityPlayerSP field_71439_g = FreeCam.mc.field_71439_g;
            field_71439_g.field_70159_w += directionSpeedVanilla[0];
            final EntityPlayerSP field_71439_g2 = FreeCam.mc.field_71439_g;
            field_71439_g2.field_70179_y += directionSpeedVanilla[1];
        }
        if (FreeCam.mc.field_71474_y.field_74314_A.func_151470_d()) {
            final EntityPlayerSP entityPlayerSP = player;
            entityPlayerSP.field_70181_x += this.Speed.getValDouble();
        }
        if (FreeCam.mc.field_71474_y.field_74311_E.func_151470_d()) {
            final EntityPlayerSP entityPlayerSP2 = player;
            entityPlayerSP2.field_70181_x -= this.Speed.getValDouble();
        }
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        FreeCam.mc.field_71439_g.field_70181_x = 0.0;
        if (FreeCam.mc.field_71474_y.field_74314_A.func_151470_d()) {
            final EntityPlayerSP field_71439_g = FreeCam.mc.field_71439_g;
            field_71439_g.field_70181_x += this.Speed.getValDouble();
        }
        if (FreeCam.mc.field_71474_y.field_74311_E.func_151470_d()) {
            final EntityPlayerSP field_71439_g2 = FreeCam.mc.field_71439_g;
            field_71439_g2.field_70181_x -= this.Speed.getValDouble();
        }
        FreeCam.mc.field_71439_g.field_70145_X = true;
        super.onLivingUpdate(event);
    }
}
