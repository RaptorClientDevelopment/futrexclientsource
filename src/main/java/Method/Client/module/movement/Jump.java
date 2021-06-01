package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraft.client.entity.*;

public class Jump extends Module
{
    Setting mode;
    Setting height;
    Setting Ymotion;
    Setting AirJump;
    Setting RapidJump;
    
    public Jump() {
        super("Jump", 0, Category.MOVEMENT, "Jump Mod");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "PotionHJ", new String[] { "PotionHJ", "Ymotion", "JumpPos", "Random", "Packet", "None" }));
        this.height = Main.setmgr.add(new Setting("height", this, 1.0, 0.5, 20.0, true, this.mode, "PotionHJ", 1));
        this.Ymotion = Main.setmgr.add(new Setting("Ymotion", this, 1.0, 0.0, 2.0, false, this.mode, "Ymotion", 1));
        this.AirJump = Main.setmgr.add(new Setting("AirJump", this, false));
        this.RapidJump = Main.setmgr.add(new Setting("RapidJump", this, false));
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("PotionHJ")) {
            final PotionEffect nv = new PotionEffect(MobEffects.field_76430_j, 3, (int)this.height.getValDouble());
            Jump.mc.field_71439_g.func_70690_d(nv);
        }
        if (this.mode.getValString().equalsIgnoreCase("JumpPos") && Jump.mc.field_71474_y.field_74314_A.field_74513_e) {
            Jump.mc.field_71439_g.func_70107_b(Jump.mc.field_71439_g.field_70142_S, (double)(Jump.mc.field_71439_g.field_70117_cu + 0.139f), (double)Jump.mc.field_71439_g.field_70116_cv);
        }
        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
            Jump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Jump.mc.field_71439_g.field_70165_t, Jump.mc.field_71439_g.field_70163_u + 0.41999998688698, Jump.mc.field_71439_g.field_70161_v, true));
            Jump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Jump.mc.field_71439_g.field_70165_t, Jump.mc.field_71439_g.field_70163_u + 0.7531999805211997, Jump.mc.field_71439_g.field_70161_v, true));
            Jump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Jump.mc.field_71439_g.field_70165_t, Jump.mc.field_71439_g.field_70163_u + 1.00133597911214, Jump.mc.field_71439_g.field_70161_v, true));
            Jump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Jump.mc.field_71439_g.field_70165_t, Jump.mc.field_71439_g.field_70163_u + 1.16610926093821, Jump.mc.field_71439_g.field_70161_v, true));
        }
        if (this.AirJump.getValBoolean() && !Jump.mc.field_71439_g.field_70122_E && Jump.mc.field_71474_y.field_74314_A.func_151468_f()) {
            Jump.mc.field_71439_g.func_70664_aZ();
        }
        if (this.RapidJump.getValBoolean() && Jump.mc.field_71439_g.field_70122_E && Jump.mc.field_71474_y.field_74314_A.field_74513_e) {
            Jump.mc.field_71439_g.func_70664_aZ();
        }
    }
    
    @Override
    public void onPlayerJump(final EntityPlayerJumpEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Random")) {
            final EntityPlayerSP field_71439_g = Jump.mc.field_71439_g;
            field_71439_g.field_70181_x += Math.random() / 10.0;
            final EntityPlayerSP field_71439_g2 = Jump.mc.field_71439_g;
            field_71439_g2.field_70163_u += Math.random() / 10.0;
        }
        if (this.mode.getValString().equalsIgnoreCase("Ymotion")) {
            Jump.mc.field_71439_g.field_70181_x *= this.Ymotion.getValDouble();
        }
    }
    
    @Override
    public void onDisable() {
        Jump.mc.field_71439_g.func_184596_c(MobEffects.field_76430_j);
        super.onDisable();
    }
}
