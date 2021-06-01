package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import Method.Client.utils.system.*;

public class AntiFall extends Module
{
    private int state;
    private double fall;
    Setting mode;
    public TimerUtils timer;
    
    public AntiFall() {
        super("NoFall", 0, Category.MOVEMENT, "Take no fall damage");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Vanilla", new String[] { "Vanilla", "LAAC", "Hypixel", "SpoofGround", "NoGround", "AAC", "AAC3.3.15", "Spartan", "Quick", "NCP" }));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.fall = 0.0;
    }
    
    public double getDistanceToGround() {
        for (int var3 = 0; var3 < 256; ++var3) {
            final BlockPos var4 = new BlockPos(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - var3, AntiFall.mc.field_71439_g.field_70161_v);
            if (AntiFall.mc.field_71441_e.func_180495_p(var4).func_177230_c() != Blocks.field_150350_a && AntiFall.mc.field_71441_e.func_180495_p(var4).func_177230_c() != Blocks.field_150349_c && AntiFall.mc.field_71441_e.func_180495_p(var4).func_177230_c() != Blocks.field_150329_H && AntiFall.mc.field_71441_e.func_180495_p(var4).func_177230_c() != Blocks.field_150328_O && AntiFall.mc.field_71441_e.func_180495_p(var4).func_177230_c() != Blocks.field_150327_N) {
                final double var5 = AntiFall.mc.field_71439_g.field_70163_u - var4.func_177956_o();
                return var5 - 1.0;
            }
        }
        return 256.0;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("NCP")) {
            final Block block = AntiFall.mc.field_71441_e.func_180495_p(new BlockPos(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - 6.0, AntiFall.mc.field_71439_g.field_70161_v)).func_177230_c();
            final Block block2 = AntiFall.mc.field_71441_e.func_180495_p(new BlockPos(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - 5.0, AntiFall.mc.field_71439_g.field_70161_v)).func_177230_c();
            final Block block3 = AntiFall.mc.field_71441_e.func_180495_p(new BlockPos(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - 4.0, AntiFall.mc.field_71439_g.field_70161_v)).func_177230_c();
            if ((block != Blocks.field_150350_a || block2 != Blocks.field_150350_a || block3 != Blocks.field_150350_a) && AntiFall.mc.field_71439_g.field_70143_R > 2.0f) {
                AntiFall.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u + 0.1, AntiFall.mc.field_71439_g.field_70161_v, false));
                AntiFall.mc.field_71439_g.field_70181_x = -10.0;
                AntiFall.mc.field_71439_g.field_70143_R = MathHelper.field_180189_a;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla") && AntiFall.mc.field_71439_g.field_70143_R > 2.0f) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(true));
        }
        if (this.mode.getValString().equalsIgnoreCase("Quick") && AntiFall.mc.field_71439_g.field_70143_R > 3.1) {
            if (this.getDistanceToGround() > 40.0) {
                return;
            }
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - 0.5, AntiFall.mc.field_71439_g.field_70161_v, true));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u + 0.5, AntiFall.mc.field_71439_g.field_70161_v, true));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, -9.0, AntiFall.mc.field_71439_g.field_70161_v, true));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u, AntiFall.mc.field_71439_g.field_70161_v, true));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u + this.getDistanceToGround(), AntiFall.mc.field_71439_g.field_70161_v, true));
            final EntityPlayerSP field_71439_g = AntiFall.mc.field_71439_g;
            field_71439_g.field_70181_x += 0.3;
        }
        if (this.mode.getValString().equalsIgnoreCase("LAAC") && AntiFall.mc.field_71439_g != null && AntiFall.mc.field_71441_e != null && AntiFall.mc.field_71439_g.field_70143_R > 2.0f && AntiFall.mc.field_71439_g.field_70173_aa % 6 == 0) {
            AntiFall.mc.field_71439_g.func_70107_b(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - AntiFall.mc.field_71439_g.field_70143_R, AntiFall.mc.field_71439_g.field_70161_v);
        }
        if (this.mode.getValString().equalsIgnoreCase("AAC")) {
            if (AntiFall.mc.field_71439_g.field_70143_R > 2.0f) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(true));
                this.state = 2;
            }
            else if (this.state == 2 && AntiFall.mc.field_71439_g.field_70143_R < 2.0f) {
                AntiFall.mc.field_71439_g.field_70181_x = 0.1;
                this.state = 3;
            }
            switch (this.state) {
                case 3: {
                    AntiFall.mc.field_71439_g.field_70181_x = 0.1;
                    this.state = 4;
                    break;
                }
                case 4: {
                    AntiFall.mc.field_71439_g.field_70181_x = 0.1;
                    this.state = 5;
                    break;
                }
                case 5: {
                    AntiFall.mc.field_71439_g.field_70181_x = 0.1;
                    this.state = 1;
                    break;
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("aac3.3.15") && AntiFall.mc.field_71439_g.field_70143_R > 2.0f) {
            if (!AntiFall.mc.func_71387_A()) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, Double.NaN, AntiFall.mc.field_71439_g.field_70161_v, false));
            }
            AntiFall.mc.field_71439_g.field_70143_R = -9999.0f;
        }
        if (this.mode.getValString().equalsIgnoreCase("spartan")) {
            this.timer.reset();
            if (AntiFall.mc.field_71439_g.field_70143_R > 1.5 && this.timer.hasReached(10.0f)) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u + 10.0, AntiFall.mc.field_71439_g.field_70161_v, true));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(AntiFall.mc.field_71439_g.field_70165_t, AntiFall.mc.field_71439_g.field_70163_u - 10.0, AntiFall.mc.field_71439_g.field_70161_v, true));
                this.timer.reset();
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("hypixel")) {
            if (!AntiFall.mc.field_71439_g.field_70122_E) {
                if (AntiFall.mc.field_71439_g.field_70181_x < -0.08) {
                    this.fall -= AntiFall.mc.field_71439_g.field_70181_x;
                }
                if (this.fall > 2.0) {
                    this.fall = 0.0;
                    AntiFall.mc.field_71439_g.field_70122_E = false;
                }
            }
            this.fall = 0.0;
        }
        super.onClientTick(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketPlayer) {
            final CPacketPlayer playerPacket = (CPacketPlayer)packet;
            if (this.mode.getValString().equalsIgnoreCase("SpoofGround")) {
                playerPacket.field_149474_g = true;
            }
            if (this.mode.getValString().equalsIgnoreCase("NoGround")) {
                playerPacket.field_149474_g = false;
            }
        }
        return true;
    }
}
