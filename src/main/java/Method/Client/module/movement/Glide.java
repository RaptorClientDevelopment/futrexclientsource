package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import Method.Client.utils.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.stream.*;
import net.minecraft.block.*;

public class Glide extends Module
{
    Setting Mode;
    Setting Damage;
    Setting fallSpeed;
    Setting moveSpeed;
    Setting minHeight;
    Setting ypos;
    Setting ymotion;
    Setting MotionY;
    TimerUtils timer;
    
    public Glide() {
        super("Glide", 0, Category.MOVEMENT, "Glide");
        this.Mode = Main.setmgr.add(new Setting("Mode", this, "Falling", new String[] { "Falling", "Constant", "Flat", "ACC", "NCP", "Matrix", "Simple", "Randomadd" }));
        this.Damage = Main.setmgr.add(new Setting("Damage", this, false));
        this.fallSpeed = Main.setmgr.add(new Setting("fallSpeed", this, 0.25, 0.005, 0.25, false, this.Mode, "Falling", 2));
        this.moveSpeed = Main.setmgr.add(new Setting("moveSpeed", this, 1.0, 0.5, 5.0, false));
        this.minHeight = Main.setmgr.add(new Setting("minHeight", this, 0.0, 0.0, 2.0, false, this.Mode, "Falling", 2));
        this.ypos = Main.setmgr.add(new Setting("ypos", this, 1.0, 1.0, 5.0, false, this.Mode, "Randomadd", 2));
        this.ymotion = Main.setmgr.add(new Setting("ymotion", this, 1.0, 1.0, 5.0, false, this.Mode, "Randomadd", 2));
        this.MotionY = Main.setmgr.add(new Setting("MotionY", this, 12.0, 0.0, 100.0, false, this.Mode, "Constant", 2));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onEnable() {
        if (this.Damage.getValBoolean()) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Glide.mc.field_71439_g.field_70165_t, Glide.mc.field_71439_g.field_70163_u - 6.0, Glide.mc.field_71439_g.field_70161_v, true));
            final EntityPlayerSP field_71439_g = Glide.mc.field_71439_g;
            field_71439_g.field_70159_w *= 0.2;
            final EntityPlayerSP field_71439_g2 = Glide.mc.field_71439_g;
            field_71439_g2.field_70179_y *= 0.2;
            Glide.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        }
        if (this.Mode.getValString().equalsIgnoreCase("Flat")) {
            Glide.mc.field_71439_g.field_70181_x = 0.19;
        }
        super.onEnable();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final EntityPlayerSP player = Glide.mc.field_71439_g;
        if (this.Mode.getValString().equalsIgnoreCase("Randomadd") && Glide.mc.field_71439_g.field_70181_x < -0.01 && !Glide.mc.field_71439_g.field_70122_E) {
            final double firstrandom = Math.random() / this.ymotion.getValDouble() / 10.0;
            final double secondrandom = Math.random() / this.ypos.getValDouble() / 10.0;
            if (this.ymotion.getValDouble() > 0.0) {
                final EntityPlayerSP field_71439_g = Glide.mc.field_71439_g;
                field_71439_g.field_70181_x += firstrandom;
            }
            if (this.ypos.getValDouble() > 0.0) {
                final EntityPlayerSP field_71439_g2 = Glide.mc.field_71439_g;
                field_71439_g2.field_70163_u += secondrandom;
            }
        }
        if (this.Mode.getValString().equalsIgnoreCase("Constant")) {
            Glide.mc.field_71439_g.field_70181_x = -this.MotionY.getValDouble() / 60.0;
        }
        if (this.Mode.getValString().equalsIgnoreCase("Simple")) {
            if (Glide.mc.field_71474_y.field_74314_A.field_74513_e) {
                Glide.mc.field_71439_g.func_70107_b(Glide.mc.field_71439_g.field_70165_t, Glide.mc.field_71439_g.field_70163_u + 0.009999999776482582, Glide.mc.field_71439_g.field_70161_v);
            }
            Glide.mc.field_71439_g.field_70181_x = -0.10000000149011612;
            if (Glide.mc.field_71474_y.field_74311_E.field_74513_e) {
                Glide.mc.field_71439_g.func_70107_b(Glide.mc.field_71439_g.field_70165_t, Glide.mc.field_71439_g.field_70163_u - 0.5, Glide.mc.field_71439_g.field_70161_v);
            }
        }
        if (this.Mode.getValString().equalsIgnoreCase("Matrix") && Glide.mc.field_71439_g.field_70143_R > 3.0f) {
            if (Glide.mc.field_71439_g.field_70173_aa % 3 == 0) {
                Glide.mc.field_71439_g.field_70181_x = -0.1;
            }
            if (Glide.mc.field_71439_g.field_70173_aa % 4 == 0) {
                Glide.mc.field_71439_g.field_70181_x = -0.2;
            }
        }
        if (this.Mode.getValString().equalsIgnoreCase("NCP")) {
            Glide.mc.field_71439_g.field_70122_E = true;
            Glide.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            tpRel(Glide.mc.field_71439_g.field_70159_w, Glide.mc.field_71439_g.field_70181_x = -0.0222, Glide.mc.field_71439_g.field_70179_y);
            tpPacket(Glide.mc.field_71439_g.field_70159_w, Glide.mc.field_71439_g.field_70181_x - 9.0, Glide.mc.field_71439_g.field_70179_y);
        }
        if (this.Mode.getValString().equalsIgnoreCase("Flat")) {
            if (!player.field_71075_bZ.field_75100_b && player.field_70143_R > 0.0f && !player.func_70093_af()) {
                player.field_70181_x = 0.0;
            }
            if (Wrapper.INSTANCE.mcSettings().field_74311_E.func_151470_d()) {
                player.field_70181_x = -0.11;
            }
            if (Wrapper.INSTANCE.mcSettings().field_74314_A.func_151470_d()) {
                Objects.requireNonNull(Glide.mc.func_147114_u()).func_147297_a((Packet)new CPacketEntityAction((Entity)Glide.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
                player.field_70122_E = false;
            }
            if (this.timer.delay(50.0f)) {
                player.field_70122_E = false;
                this.timer.setLastMS();
            }
        }
        if (this.Mode.getValString().equalsIgnoreCase("ACC")) {
            if ((Glide.mc.field_71439_g.field_70181_x < 0.0 && Glide.mc.field_71439_g.field_70160_al) || Glide.mc.field_71439_g.field_70122_E) {
                Glide.mc.field_71439_g.field_70181_x = -0.125;
                final EntityPlayerSP field_71439_g3 = Glide.mc.field_71439_g;
                field_71439_g3.field_70747_aH *= 1.01227f;
                Glide.mc.field_71439_g.field_70145_X = true;
                Glide.mc.field_71439_g.field_70143_R = 0.0f;
                Glide.mc.field_71439_g.field_70122_E = true;
                final EntityPlayerSP field_71439_g4 = Glide.mc.field_71439_g;
                field_71439_g4.field_70702_br += (float)(0.800000011920929 * this.moveSpeed.getValDouble());
                final EntityPlayerSP field_71439_g5 = Glide.mc.field_71439_g;
                field_71439_g5.field_70747_aH += 0.2f;
                Glide.mc.field_71439_g.field_70133_I = true;
            }
        }
        else if (this.Mode.getValString().equalsIgnoreCase("Falling")) {
            final World world = (World)Glide.mc.field_71441_e;
            if (!player.field_70160_al || player.func_70090_H() || player.func_180799_ab() || player.func_70617_f_() || player.field_70181_x >= 0.0) {
                return;
            }
            if (this.minHeight.getValDouble() > 0.0) {
                AxisAlignedBB box = player.func_174813_aQ();
                box = box.func_111270_a(box.func_72317_d(0.0, -this.minHeight.getValDouble(), 0.0));
                if (world.func_184143_b(box)) {
                    return;
                }
                final BlockPos min = new BlockPos(new Vec3d(box.field_72340_a, box.field_72338_b, box.field_72339_c));
                final BlockPos max = new BlockPos(new Vec3d(box.field_72336_d, box.field_72337_e, box.field_72334_f));
                final Stream<BlockPos> stream = StreamSupport.stream(BlockPos.func_177980_a(min, max).spliterator(), true);
                if (stream.map((Function<? super BlockPos, ?>)BlockUtils::getBlock).anyMatch(b -> b instanceof BlockLiquid)) {
                    return;
                }
            }
            player.field_70181_x = Math.max(player.field_70181_x, -this.fallSpeed.getValDouble());
            final EntityPlayerSP entityPlayerSP = player;
            entityPlayerSP.field_70747_aH *= (float)this.moveSpeed.getValDouble();
        }
        super.onClientTick(event);
    }
    
    public static void tpRel(final double x, final double y, final double z) {
        final EntityPlayerSP player = Glide.mc.field_71439_g;
        player.func_70107_b(player.field_70165_t + x, player.field_70163_u + y, player.field_70161_v + z);
    }
    
    public static void tpPacket(final double x, final double y, final double z) {
        final EntityPlayerSP player = Glide.mc.field_71439_g;
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(player.field_70165_t + x, player.field_70163_u + y, player.field_70161_v + z, false));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(player.field_70165_t, player.field_70163_u, player.field_70161_v, false));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(player.field_70165_t, player.field_70163_u, player.field_70161_v, true));
    }
}
