package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.server.*;
import Method.Client.utils.visual.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Method.Client.utils.Patcher.Events.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

public class LongJump extends Module
{
    private int airTicks;
    private int groundTicks;
    Setting mode;
    Setting boostval;
    Setting Lagback;
    private double moveSpeed;
    private double lastDist;
    private int level;
    private final TimerUtils timer;
    private final TimerUtils aac;
    int delay2;
    double y;
    double speed;
    boolean teleported;
    private float air;
    private int stage;
    private boolean jump;
    
    public LongJump() {
        super("LongJump", 0, Category.MOVEMENT, "Jump Far");
        this.mode = Main.setmgr.add(new Setting("Jump mode", this, "Vanilla", new String[] { "Vanilla", "AAC", "Damage", "Long", "Legit", "Quack", "AAC4", "Mineplex", "Hypixel", "NeruxVace", "NeruxVace2" }));
        this.boostval = Main.setmgr.add(new Setting("boostval", this, 1.0, 0.0, 3.0, false, this.mode, "Long", 3));
        this.Lagback = Main.setmgr.add(new Setting("Lagback", this, true));
        this.timer = new TimerUtils();
        this.aac = new TimerUtils();
        this.delay2 = 0;
        this.y = 0.0;
        this.speed = 0.0;
        this.teleported = false;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook pac = (SPacketPlayerPosLook)packet;
            if (this.Lagback.getValBoolean()) {
                ChatUtils.warning("Lagback checks!");
                LongJump.mc.field_71439_g.field_70122_E = false;
                final EntityPlayerSP field_71439_g = LongJump.mc.field_71439_g;
                field_71439_g.field_70159_w *= 0.0;
                final EntityPlayerSP field_71439_g2 = LongJump.mc.field_71439_g;
                field_71439_g2.field_70179_y *= 0.0;
                LongJump.mc.field_71439_g.field_70747_aH = 0.0f;
                this.toggle();
            }
            else if (this.timer.hasReached(300.0f)) {
                pac.field_148936_d = LongJump.mc.field_71439_g.field_70177_z;
                pac.field_148937_e = LongJump.mc.field_71439_g.field_70125_A;
            }
            this.timer.reset();
        }
        return true;
    }
    
    @Override
    public void onDisable() {
        if (LongJump.mc.field_71439_g != null) {
            this.moveSpeed = this.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        assert LongJump.mc.field_71439_g != null;
        LongJump.mc.field_71439_g.field_71102_ce = 0.02f;
        if (this.mode.getValString().equalsIgnoreCase("NeruxVace2")) {
            this.setMotion(0.2);
        }
        if (this.mode.getValString().equalsIgnoreCase("NeruxVace")) {
            this.teleported = false;
            this.setMotion(0.22);
        }
        this.speed = 0.0;
        this.delay2 = 0;
        LongJump.mc.field_71428_T.field_194149_e = 50.0f;
    }
    
    @Override
    public void onEnable() {
        this.teleported = false;
        this.groundTicks = -5;
        if (LongJump.mc.field_71439_g == null || LongJump.mc.field_71441_e == null) {
            return;
        }
        this.level = 0;
        this.lastDist = 0.0;
        if (this.mode.getValString().equalsIgnoreCase("Hypixel")) {
            this.setMotion(0.15);
            this.speed = 0.4;
            this.y = 0.02;
        }
    }
    
    public static void toFwd(final double speed) {
        final float f = LongJump.mc.field_71439_g.field_70177_z * 0.017453292f;
        final EntityPlayerSP field_71439_g = LongJump.mc.field_71439_g;
        field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * speed;
        final EntityPlayerSP field_71439_g2 = LongJump.mc.field_71439_g;
        field_71439_g2.field_70179_y += MathHelper.func_76134_b(f) * speed;
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final double xDist = LongJump.mc.field_71439_g.field_70165_t - LongJump.mc.field_71439_g.field_70169_q;
        final double zDist = LongJump.mc.field_71439_g.field_70161_v - LongJump.mc.field_71439_g.field_70166_s;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        if (this.mode.getValString().equalsIgnoreCase("AAC")) {
            LongJump.mc.field_71474_y.field_74351_w.field_74513_e = false;
            if (LongJump.mc.field_71439_g.field_70122_E) {
                this.jump = true;
            }
            if (LongJump.mc.field_71439_g.field_70122_E && this.aac.isDelay(500L)) {
                LongJump.mc.field_71439_g.field_70181_x = 0.42;
                toFwd(2.3);
                this.timer.setLastMS();
            }
            else if (!LongJump.mc.field_71439_g.field_70122_E && this.jump) {
                final EntityPlayerSP field_71439_g = LongJump.mc.field_71439_g;
                final EntityPlayerSP field_71439_g2 = LongJump.mc.field_71439_g;
                final double n = 0.0;
                field_71439_g2.field_70179_y = n;
                field_71439_g.field_70159_w = n;
                this.jump = false;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Quack")) {
            final boolean moving = LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d();
            if (!moving) {
                return;
            }
            double forward = LongJump.mc.field_71439_g.field_71158_b.field_192832_b;
            final float yaw = LongJump.mc.field_71439_g.field_70177_z;
            if (forward != 0.0) {
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            else {
                forward = 0.0;
            }
            final float[] motion = { 0.4206065f, 0.4179245f, 0.41525924f, 0.41261f, 0.409978f, 0.407361f, 0.404761f, 0.402178f, 0.399611f, 0.39706f, 0.394525f, 0.392f, 0.3894f, 0.38644f, 0.383655f, 0.381105f, 0.37867f, 0.37625f, 0.37384f, 0.37145f, 0.369f, 0.3666f, 0.3642f, 0.3618f, 0.35945f, 0.357f, 0.354f, 0.351f, 0.348f, 0.345f, 0.342f, 0.339f, 0.336f, 0.333f, 0.33f, 0.327f, 0.324f, 0.321f, 0.318f, 0.315f, 0.312f, 0.309f, 0.307f, 0.305f, 0.303f, 0.3f, 0.297f, 0.295f, 0.293f, 0.291f, 0.289f, 0.287f, 0.285f, 0.283f, 0.281f, 0.279f, 0.277f, 0.275f, 0.273f, 0.271f, 0.269f, 0.267f, 0.265f, 0.263f, 0.261f, 0.259f, 0.257f, 0.255f, 0.253f, 0.251f, 0.249f, 0.247f, 0.245f, 0.243f, 0.241f, 0.239f, 0.237f };
            final float[] glide = { 0.3425f, 0.5445f, 0.65425f, 0.685f, 0.675f, 0.2f, 0.895f, 0.719f, 0.76f };
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            if (!LongJump.mc.field_71439_g.field_70124_G && !LongJump.mc.field_71439_g.field_70122_E) {
                ++this.airTicks;
                this.groundTicks = -5;
                if (this.airTicks - 6 >= 0 && this.airTicks - 6 < glide.length) {
                    final EntityPlayerSP field_71439_g3 = LongJump.mc.field_71439_g;
                    field_71439_g3.field_70181_x *= glide[this.airTicks - 6];
                }
                if (LongJump.mc.field_71439_g.field_70181_x < -0.2 && LongJump.mc.field_71439_g.field_70181_x > -0.24) {
                    final EntityPlayerSP field_71439_g4 = LongJump.mc.field_71439_g;
                    field_71439_g4.field_70181_x *= 0.7;
                }
                else if (LongJump.mc.field_71439_g.field_70181_x < -0.25 && LongJump.mc.field_71439_g.field_70181_x > -0.32) {
                    final EntityPlayerSP field_71439_g5 = LongJump.mc.field_71439_g;
                    field_71439_g5.field_70181_x *= 0.8;
                }
                else if (LongJump.mc.field_71439_g.field_70181_x < -0.35 && LongJump.mc.field_71439_g.field_70181_x > -0.8) {
                    final EntityPlayerSP field_71439_g6 = LongJump.mc.field_71439_g;
                    field_71439_g6.field_70181_x *= 0.98;
                }
                if (this.airTicks - 1 >= 0 && this.airTicks - 1 < motion.length) {
                    LongJump.mc.field_71439_g.field_70159_w = forward * motion[this.airTicks - 1] * 3.0 * cos;
                    LongJump.mc.field_71439_g.field_70179_y = forward * motion[this.airTicks - 1] * 3.0 * sin;
                }
                else {
                    LongJump.mc.field_71439_g.field_70159_w = 0.0;
                    LongJump.mc.field_71439_g.field_70179_y = 0.0;
                }
            }
            else {
                this.airTicks = 0;
                ++this.groundTicks;
                if (this.groundTicks <= 2) {
                    LongJump.mc.field_71439_g.field_70159_w = forward * 0.009999999776482582 * cos;
                    LongJump.mc.field_71439_g.field_70179_y = forward * 0.009999999776482582 * sin;
                }
                else {
                    LongJump.mc.field_71439_g.field_70159_w = forward * 0.30000001192092896 * cos;
                    LongJump.mc.field_71439_g.field_70179_y = forward * 0.30000001192092896 * sin;
                    LongJump.mc.field_71439_g.field_70181_x = 0.42399999499320984;
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Damage")) {
            if (LongJump.mc.field_71439_g.field_70122_E) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u + 0.300001, LongJump.mc.field_71439_g.field_70161_v, false));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u, LongJump.mc.field_71439_g.field_70161_v, false));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u, LongJump.mc.field_71439_g.field_70161_v, true));
            }
            if (LongJump.mc.field_71439_g.field_70737_aN > 0) {
                Movemulti(5.0);
            }
            else {
                Movemulti(0.0);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla") && (LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74370_x.func_151470_d() || LongJump.mc.field_71474_y.field_74366_z.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d()) && LongJump.mc.field_71474_y.field_74314_A.func_151470_d()) {
            final float dir = LongJump.mc.field_71439_g.field_70177_z + ((LongJump.mc.field_71439_g.field_191988_bg < 0.0f) ? 180 : 0) + ((LongJump.mc.field_71439_g.field_70702_br > 0.0f) ? (-90.0f * ((LongJump.mc.field_71439_g.field_191988_bg < 0.0f) ? -0.5f : ((LongJump.mc.field_71439_g.field_191988_bg > 0.0f) ? 0.4f : 1.0f))) : 0.0f);
            final float xDir = (float)Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0);
            final float zDir = (float)Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0);
            if (LongJump.mc.field_71439_g.field_70124_G && (LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74370_x.func_151470_d() || LongJump.mc.field_71474_y.field_74366_z.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d()) && LongJump.mc.field_71474_y.field_74314_A.func_151470_d()) {
                LongJump.mc.field_71439_g.field_70159_w = xDir * 0.29f;
                LongJump.mc.field_71439_g.field_70179_y = zDir * 0.29f;
            }
            if (LongJump.mc.field_71439_g.field_70181_x == 0.33319999363422365 && (LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74370_x.func_151470_d() || LongJump.mc.field_71474_y.field_74366_z.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d())) {
                LongJump.mc.field_71439_g.field_70159_w = xDir * 1.261;
                LongJump.mc.field_71439_g.field_70179_y = zDir * 1.261;
            }
        }
    }
    
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (LongJump.mc.field_71439_g == null) {
            return;
        }
        if (LongJump.mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (this.mode.getValString().equalsIgnoreCase("NeruxVace2")) {
            LongJump.mc.field_71428_T.field_194149_e = 30.0f;
            if (LongJump.mc.field_71439_g.field_70122_E) {
                LongJump.mc.field_71439_g.func_70664_aZ();
                LongJump.mc.field_71439_g.field_70181_x = 1.0;
                this.setMotion(9.5);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("NeruxVace")) {
            LongJump.mc.field_71439_g.field_71102_ce = 0.025f;
            final EntityPlayerSP field_71439_g = LongJump.mc.field_71439_g;
            field_71439_g.field_70159_w *= 1.08;
            final EntityPlayerSP field_71439_g2 = LongJump.mc.field_71439_g;
            field_71439_g2.field_70179_y *= 1.08;
            if (LongJump.mc.field_71439_g.field_70122_E) {
                LongJump.mc.field_71439_g.func_70664_aZ();
            }
            final EntityPlayerSP field_71439_g3 = LongJump.mc.field_71439_g;
            field_71439_g3.field_70181_x += 0.072;
        }
        if (this.mode.getValString().equalsIgnoreCase("Hypixel")) {
            final boolean var1 = false;
            if (this.y > 0.0) {
                this.y *= 0.9;
            }
            final float var2 = LongJump.mc.field_71439_g.field_70143_R;
            LongJump.mc.field_71439_g.func_70107_b(LongJump.mc.field_71439_g.field_70165_t * 1.0, LongJump.mc.field_71439_g.field_70163_u + 0.035423123132, LongJump.mc.field_71439_g.field_70161_v * 1.0);
            final float var3 = 0.7f + this.getSpeedEffect() * 0.45f;
            if ((LongJump.mc.field_71439_g.field_191988_bg != 0.0f || LongJump.mc.field_71439_g.field_70702_br != 0.0f) && LongJump.mc.field_71439_g.field_70122_E) {
                this.setMotion(0.15);
                LongJump.mc.field_71439_g.func_70664_aZ();
                this.stage = 1;
            }
            if (LongJump.mc.field_71439_g.field_70122_E) {
                this.air = 0.0f;
            }
            else {
                if (LongJump.mc.field_71439_g.field_70124_G) {
                    this.stage = 0;
                }
                double var4 = 0.95 + this.getSpeedEffect() * 0.2 - this.air / 25.0f;
                if (var4 < this.defaultSpeed() - 0.05) {
                    var4 = this.defaultSpeed() - 0.05;
                }
                this.setMotion(var4 * 0.75);
                if (this.stage > 0) {
                    this.stage |= 0x1;
                }
                this.air += var3;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("AAC4")) {
            if (LongJump.mc.field_71439_g.field_70122_E) {
                LongJump.mc.field_71439_g.func_70664_aZ();
                final EntityPlayerSP field_71439_g4 = LongJump.mc.field_71439_g;
                field_71439_g4.field_70181_x += 0.2;
                this.speed = 0.5972999999999999;
            }
            else {
                final EntityPlayerSP field_71439_g5 = LongJump.mc.field_71439_g;
                field_71439_g5.field_70181_x += 0.03;
                this.speed *= 0.99;
            }
            this.setMotion(this.speed);
            if (!LongJump.mc.field_71439_g.field_70122_E) {
                this.delay2 |= 0x1;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Mineplex")) {
            if (LongJump.mc.field_71439_g.field_70122_E) {
                LongJump.mc.field_71439_g.func_70664_aZ();
                final EntityPlayerSP field_71439_g6 = LongJump.mc.field_71439_g;
                field_71439_g6.field_70181_x += 0.1;
                this.speed = 0.65;
            }
            else {
                final EntityPlayerSP field_71439_g7 = LongJump.mc.field_71439_g;
                field_71439_g7.field_70181_x += 0.03;
                this.speed *= 0.992;
            }
            if (!LongJump.mc.field_71474_y.field_74370_x.field_74513_e && !LongJump.mc.field_71474_y.field_74366_z.field_74513_e) {
                this.setMotion(this.speed);
            }
            else {
                this.setMotion(this.speed * 0.7);
            }
            if (LongJump.mc.field_71439_g.field_70122_E) {
                this.setMotion(0.0);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("legit")) {
            if (Utils.isMoving((Entity)LongJump.mc.field_71439_g)) {
                if (LongJump.mc.field_71439_g.field_70122_E) {
                    final EntityPlayerSP field_71439_g8 = LongJump.mc.field_71439_g;
                    final EntityPlayerSP field_71439_g9 = LongJump.mc.field_71439_g;
                    final double n = 0.41;
                    field_71439_g9.field_70181_x = n;
                    field_71439_g8.field_70181_x = n;
                }
            }
            else {
                LongJump.mc.field_71439_g.field_70159_w = 0.0;
                LongJump.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("long")) {
            double forward = LongJump.mc.field_71439_g.field_71158_b.field_192832_b;
            double strafe = LongJump.mc.field_71439_g.field_71158_b.field_78902_a;
            final float yaw = LongJump.mc.field_71439_g.field_70177_z;
            if (forward == 0.0 && strafe == 0.0) {
                LongJump.mc.field_71439_g.field_70159_w = 0.0;
                LongJump.mc.field_71439_g.field_70179_y = 0.0;
            }
            if (forward != 0.0 && strafe != 0.0) {
                forward *= Math.sin(0.7853981633974483);
                strafe *= Math.cos(0.7853981633974483);
            }
            if (this.level != 1 || (LongJump.mc.field_71439_g.field_191988_bg == 0.0f && LongJump.mc.field_71439_g.field_70702_br == 0.0f)) {
                if (this.level == 2) {
                    ++this.level;
                    double motionY = 0.40123128;
                    if ((LongJump.mc.field_71439_g.field_191988_bg != 0.0f || LongJump.mc.field_71439_g.field_70702_br != 0.0f) && LongJump.mc.field_71439_g.field_70122_E) {
                        if (LongJump.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                            motionY += (Objects.requireNonNull(LongJump.mc.field_71439_g.func_70660_b(MobEffects.field_76430_j)).func_76458_c() + 1) * 0.1f;
                        }
                        final EntityPlayerSP field_71439_g10 = LongJump.mc.field_71439_g;
                        final EntityPlayerSP field_71439_g11 = LongJump.mc.field_71439_g;
                        final double n2 = motionY;
                        field_71439_g11.field_70181_x = n2;
                        field_71439_g10.field_70181_x = n2;
                        this.moveSpeed *= 2.149;
                    }
                }
                else if (this.level == 3) {
                    ++this.level;
                    final double difference = 0.763 * (this.lastDist - this.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                }
                else {
                    if (LongJump.mc.field_71441_e.func_184144_a((Entity)LongJump.mc.field_71439_g, LongJump.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, LongJump.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || LongJump.mc.field_71439_g.field_70124_G) {
                        this.level = 1;
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
            }
            else {
                this.level = 2;
                final double boost = LongJump.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) ? this.boostval.getValDouble() : (this.boostval.getValDouble() + 1.1);
                this.moveSpeed = boost * this.getBaseMoveSpeed() - 0.01;
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            final double mx = -Math.sin(Math.toRadians(yaw));
            final double mz = Math.cos(Math.toRadians(yaw));
            LongJump.mc.field_71439_g.field_70159_w = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
            LongJump.mc.field_71439_g.field_70179_y = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
        }
    }
    
    public static void Movemulti(final double moveSpeed) {
        float forward = LongJump.mc.field_71439_g.field_191988_bg;
        float strafe = LongJump.mc.field_71439_g.field_70702_br;
        float yaw = LongJump.mc.field_71439_g.field_70177_z;
        if (forward == 0.0 && strafe == 0.0) {
            LongJump.mc.field_71439_g.field_70159_w = 0.0;
            LongJump.mc.field_71439_g.field_70179_y = 0.0;
        }
        final int d = 45;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? (-d) : d);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? d : (-d));
            }
            strafe = 0.0f;
            if (forward > 0.0) {
                forward = 1.0f;
            }
            else if (forward < 0.0) {
                forward = -1.0f;
            }
        }
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double xDist = forward * moveSpeed * cos + strafe * moveSpeed * sin;
        final double zDist = forward * moveSpeed * sin - strafe * moveSpeed * cos;
        LongJump.mc.field_71439_g.field_70159_w = xDist;
        LongJump.mc.field_71439_g.field_70179_y = zDist;
    }
    
    public void setMotion(final double var1) {
        double var2 = LongJump.mc.field_71439_g.field_71158_b.field_192832_b;
        double var3 = LongJump.mc.field_71439_g.field_71158_b.field_78902_a;
        float var4 = LongJump.mc.field_71439_g.field_70177_z;
        if (this.mode.getValString().equalsIgnoreCase("aac4")) {
            var3 = 0.0;
            var4 = 0.0f;
        }
        if (var2 == 0.0 && var3 == 0.0) {
            LongJump.mc.field_71439_g.field_70159_w = 0.0;
            LongJump.mc.field_71439_g.field_70179_y = 0.0;
        }
        else {
            if (var2 != 0.0) {
                if (var3 > 0.0) {
                    var4 += ((var2 > 0.0) ? -45 : 45);
                }
                else if (var3 < 0.0) {
                    var4 += ((var2 > 0.0) ? 45 : -45);
                }
                var3 = 0.0;
                if (var2 > 0.0) {
                    var2 = 1.0;
                }
                else if (var2 < 0.0) {
                    var2 = -1.0;
                }
            }
            LongJump.mc.field_71439_g.field_70159_w = var2 * var1 * Math.cos(Math.toRadians(var4 + 90.0f)) + var3 * var1 * Math.sin(Math.toRadians(var4 + 90.0f));
            LongJump.mc.field_71439_g.field_70179_y = var2 * var1 * Math.sin(Math.toRadians(var4 + 90.0f)) - var3 * var1 * Math.cos(Math.toRadians(var4 + 90.0f));
        }
    }
    
    public int getSpeedEffect() {
        return LongJump.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) ? (Objects.requireNonNull(LongJump.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c() | 0x1) : 0;
    }
    
    public double defaultSpeed() {
        double var1 = 0.2873;
        if (LongJump.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
            final int var2 = Objects.requireNonNull(LongJump.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c();
            var1 *= 1.0 + 0.2 * (var2 | 0x1);
        }
        return var1;
    }
    
    private double getBaseMoveSpeed() {
        double n = 0.2873;
        if (LongJump.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
            n *= 1.0 + 0.2 * (Objects.requireNonNull(LongJump.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c() + 1);
        }
        return n;
    }
}
