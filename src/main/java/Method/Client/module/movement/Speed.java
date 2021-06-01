package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;

public class Speed extends Module
{
    public Setting mode;
    Setting Icespeed;
    Setting motiony;
    Setting groundmultiplier;
    Setting airmultiplier;
    Setting motionzp;
    int counter;
    public static double G;
    public boolean sink;
    
    public Speed() {
        super("Speed", 0, Category.PLAYER, "Speed");
        this.mode = Main.setmgr.add(new Setting("Speed Mode", this, "Jump", new String[] { "Jump", "Forward", "Ice", "GroundHop", "Y-Port", "MoveTry", "AAC", "Hypixel BHop", "AAC_ZOOM", "Packet" }));
        this.Icespeed = Main.setmgr.add(new Setting("Icespeed", this, 1.0, 0.1, 5.0, false, this.mode, "Ice", 2));
        this.motiony = Main.setmgr.add(new Setting("motiony", this, 0.0, 0.0, 1.5, false, this.mode, "Forward", 2));
        this.groundmultiplier = Main.setmgr.add(new Setting("groundmultiplier", this, 0.2, 0.001, 0.5, false, this.mode, "Forward", 3));
        this.airmultiplier = Main.setmgr.add(new Setting("airmultiplier", this, 1.0064, 1.0, 1.1, false, this.mode, "Forward", 4));
        this.motionzp = Main.setmgr.add(new Setting("motionzp", this, 1.0, 0.5, 4.0, false, this.mode, "Packet", 2));
        this.sink = false;
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("packet") && Utils.isMoving((Entity)Speed.mc.field_71439_g) && Speed.mc.field_71439_g.field_70122_E) {
            final boolean step = ModuleManager.getModuleByName("step").isToggled();
            final double posX = Speed.mc.field_71439_g.field_70165_t;
            final double posY = Speed.mc.field_71439_g.field_70163_u;
            final double posZ = Speed.mc.field_71439_g.field_70161_v;
            final double[] dir1 = Utils.directionSpeed(0.5);
            final BlockPos pos = new BlockPos(posX + dir1[0], posY, posZ + dir1[1]);
            final Block block = Speed.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (step && !(block instanceof BlockAir)) {
                setSpeed((EntityLivingBase)Speed.mc.field_71439_g, 0.0);
                return;
            }
            if (Speed.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o() - 1, pos.func_177952_p())).func_177230_c() instanceof BlockAir) {
                return;
            }
            setSpeed((EntityLivingBase)Speed.mc.field_71439_g, 4.0);
            Speed.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(posX + Speed.mc.field_71439_g.field_70159_w, (Speed.mc.field_71439_g.field_70163_u <= 10.0) ? 255.0 : 1.0, posZ + Speed.mc.field_71439_g.field_70179_y, true));
        }
    }
    
    public static void setSpeed(final EntityLivingBase entity, final double speed) {
        final double[] dir = Utils.directionSpeed(speed);
        entity.field_70159_w = dir[0];
        entity.field_70179_y = dir[1];
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Fast") && Speed.mc.field_71439_g.field_70122_E && Speed.mc.field_71439_g.field_191988_bg > 0.0f) {
            final double yaw = Math.toRadians(Speed.mc.field_71439_g.field_70177_z);
            final double motionX = -Math.sin(yaw);
            final double motionZ = Math.cos(yaw);
            Speed.mc.field_71439_g.field_70159_w = motionX * 5.0;
            Speed.mc.field_71439_g.field_70179_y = motionZ * 5.0;
            Speed.mc.field_71439_g.field_71109_bG = 0.15f;
            Speed.mc.field_71439_g.func_70031_b(true);
        }
        if (this.mode.getValString().equalsIgnoreCase("MoveTry")) {
            if (Speed.mc.field_71439_g.func_70093_af() || (Speed.mc.field_71439_g.field_191988_bg == 0.0f && Speed.mc.field_71439_g.field_70702_br == 0.0f)) {
                return;
            }
            if (Speed.mc.field_71439_g.field_191988_bg > 0.0f && !Speed.mc.field_71439_g.field_70123_F) {
                Speed.mc.field_71439_g.func_70031_b(true);
            }
            if (Speed.mc.field_71439_g.field_70122_E) {
                final EntityPlayerSP field_71439_g = Speed.mc.field_71439_g;
                field_71439_g.field_70181_x += 0.1;
                final EntityPlayerSP field_71439_g2 = Speed.mc.field_71439_g;
                field_71439_g2.field_70159_w *= 1.8;
                final EntityPlayerSP field_71439_g3 = Speed.mc.field_71439_g;
                field_71439_g3.field_70179_y *= 1.8;
                final double currentSpeed = Math.sqrt(Math.pow(Speed.mc.field_71439_g.field_70159_w, 2.0) + Math.pow(Speed.mc.field_71439_g.field_70179_y, 2.0));
                final double maxSpeed = 0.6600000262260437;
                if (currentSpeed > maxSpeed) {
                    Speed.mc.field_71439_g.field_70159_w = Speed.mc.field_71439_g.field_70159_w / currentSpeed * maxSpeed;
                    Speed.mc.field_71439_g.field_70179_y = Speed.mc.field_71439_g.field_70179_y / currentSpeed * maxSpeed;
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Forward")) {
            this.Forward(this.motiony.getValDouble(), (float)this.groundmultiplier.getValDouble(), this.airmultiplier.getValDouble());
        }
        if (this.mode.getValString().equalsIgnoreCase("Jump")) {
            final boolean boost = Math.abs(Speed.mc.field_71439_g.field_70759_as - Speed.mc.field_71439_g.field_70177_z) < 90.0f;
            if (Speed.mc.field_71439_g.field_191988_bg > 0.0f && Speed.mc.field_71439_g.field_70737_aN < 5) {
                if (Speed.mc.field_71439_g.field_70122_E) {
                    Speed.mc.field_71439_g.field_70181_x = 0.405;
                    final float f = Utils.getDirection();
                    final EntityPlayerSP field_71439_g4 = Speed.mc.field_71439_g;
                    field_71439_g4.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
                    final EntityPlayerSP field_71439_g5 = Speed.mc.field_71439_g;
                    field_71439_g5.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
                }
                else {
                    final double currentSpeed2 = Math.sqrt(Speed.mc.field_71439_g.field_70159_w * Speed.mc.field_71439_g.field_70159_w + Speed.mc.field_71439_g.field_70179_y * Speed.mc.field_71439_g.field_70179_y);
                    final double speed = boost ? 1.0064 : 1.001;
                    final double direction = Utils.getDirection();
                    Speed.mc.field_71439_g.field_70159_w = -Math.sin(direction) * speed * currentSpeed2;
                    Speed.mc.field_71439_g.field_70179_y = Math.cos(direction) * speed * currentSpeed2;
                }
            }
            super.onClientTick(event);
        }
        if (this.mode.getValString().equalsIgnoreCase("Ice")) {
            Blocks.field_150432_aD.field_149765_K = 0.4f * (float)(0.4000000059604645 * this.Icespeed.getValDouble());
            Blocks.field_150403_cj.field_149765_K = 0.4f * (float)(0.4000000059604645 * this.Icespeed.getValDouble());
            Blocks.field_185778_de.field_149765_K = 0.4f * (float)(0.4000000059604645 * this.Icespeed.getValDouble());
        }
        if (this.mode.getValString().equalsIgnoreCase("GroundHop") && Speed.mc.field_71439_g.field_70122_E) {
            this.sink = !this.sink;
            ++this.counter;
            if (this.counter > 3.189546) {
                final EntityPlayerSP field_71439_g6 = Speed.mc.field_71439_g;
                field_71439_g6.field_70159_w *= 0.009999999776482582;
                final EntityPlayerSP field_71439_g7 = Speed.mc.field_71439_g;
                field_71439_g7.field_70179_y *= 0.009999999776482582;
                Speed.mc.field_71439_g.func_184614_ca().func_77972_a(0, (EntityLivingBase)Speed.mc.field_71439_g);
                Speed.mc.field_71439_g.field_70737_aN = 62284;
                this.counter = 0;
            }
            final EntityPlayerSP field_71439_g8 = Speed.mc.field_71439_g;
            field_71439_g8.field_70159_w *= 1.8300000429153442;
            final EntityPlayerSP field_71439_g9 = Speed.mc.field_71439_g;
            field_71439_g9.field_70179_y *= 1.8300000429153442;
            Speed.mc.field_71439_g.field_70172_ad = 1;
            Speed.mc.field_71439_g.field_70181_x = (Speed.mc.field_71439_g.func_70093_af() ? -0.02 : (Speed.mc.field_71474_y.field_74314_A.field_74513_e ? 0.43 : (this.sink ? 0.37 : 0.25)));
            if (Speed.mc.field_71439_g.field_191988_bg <= 0.0f) {
                Speed.mc.field_71439_g.field_70159_w = 0.0;
                Speed.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Y-Port")) {
            if (this.isOnLiquid()) {
                return;
            }
            if (Speed.mc.field_71439_g.field_70122_E && (Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f)) {
                if (Speed.mc.field_71439_g.field_70173_aa % 2 != 0) {
                    final EntityPlayerSP field_71439_g10 = Speed.mc.field_71439_g;
                    field_71439_g10.field_70163_u += 0.4;
                }
                this.setSpeed((Speed.mc.field_71439_g.field_70173_aa % 2 == 0) ? 0.45f : 0.2f);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("ACC")) {
            final boolean boost = Math.abs(Speed.mc.field_71439_g.field_70759_as - Speed.mc.field_71439_g.field_70177_z) < 90.0f;
            if (Speed.mc.field_71439_g.field_191988_bg > 0.0f && Speed.mc.field_71439_g.field_70737_aN < 5) {
                this.MoveSpeed(boost);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Hypixel BHop")) {
            Speed.mc.field_71439_g.func_70031_b(true);
            if (Speed.mc.field_71441_e != null && Speed.mc.field_71439_g != null) {
                Speed.mc.field_71474_y.field_74314_A.field_74513_e = false;
                if (Speed.mc.field_71474_y.field_74351_w.field_74513_e && !Speed.mc.field_71439_g.field_70123_F) {
                    if (Speed.mc.field_71439_g.field_70122_E) {
                        Speed.mc.field_71439_g.func_70664_aZ();
                        Speed.mc.field_71428_T.field_74280_b = 1;
                        final EntityPlayerSP field_71439_g11 = Speed.mc.field_71439_g;
                        field_71439_g11.field_70159_w *= 1.0700000524520874;
                        final EntityPlayerSP field_71439_g12 = Speed.mc.field_71439_g;
                        field_71439_g12.field_70179_y *= 1.0700000524520874;
                    }
                    else {
                        Speed.mc.field_71439_g.field_70747_aH = 0.0265f;
                        Speed.mc.field_71428_T.field_74280_b = 1;
                        final double direction2 = getDirection();
                        final double speed2 = 1.0;
                        final double currentMotion = Math.sqrt(Speed.mc.field_71439_g.field_70159_w * Speed.mc.field_71439_g.field_70159_w + Speed.mc.field_71439_g.field_70179_y * Speed.mc.field_71439_g.field_70179_y);
                        Speed.mc.field_71439_g.field_70159_w = -Math.sin(direction2) * speed2 * currentMotion;
                        Speed.mc.field_71439_g.field_70179_y = Math.cos(direction2) * speed2 * currentMotion;
                    }
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("AAC_ZOOM")) {
            final boolean boost = Math.abs(Speed.mc.field_71439_g.field_70759_as - Speed.mc.field_71439_g.field_70177_z) < 90.0f;
            if (Speed.mc.field_71439_g.field_191988_bg > 0.0f && Speed.mc.field_71439_g.field_70737_aN < 5) {
                Speed.mc.field_71428_T.field_74280_b = 1;
                this.MoveSpeed(boost);
            }
        }
    }
    
    private void MoveSpeed(final boolean boost) {
        if (Speed.mc.field_71439_g.field_70122_E) {
            Speed.mc.field_71439_g.field_70181_x = 0.405;
            final float f = (float)getDirection();
            final EntityPlayerSP field_71439_g = Speed.mc.field_71439_g;
            field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
            final EntityPlayerSP field_71439_g2 = Speed.mc.field_71439_g;
            field_71439_g2.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
        }
        else {
            final double currentSpeed = Math.sqrt(Speed.mc.field_71439_g.field_70159_w * Speed.mc.field_71439_g.field_70159_w + Speed.mc.field_71439_g.field_70179_y * Speed.mc.field_71439_g.field_70179_y);
            final double speed = boost ? 1.0064 : 1.001;
            final double direction = getDirection();
            Speed.mc.field_71439_g.field_70159_w = -Math.sin(direction) * speed * currentSpeed;
            Speed.mc.field_71439_g.field_70179_y = Math.cos(direction) * speed * currentSpeed;
        }
    }
    
    @Override
    public void onDisable() {
        Blocks.field_150432_aD.field_149765_K = 0.98f;
        Blocks.field_150403_cj.field_149765_K = 0.98f;
        Blocks.field_185778_de.field_149765_K = 0.98f;
        Speed.mc.field_71428_T.field_74280_b = 1;
        super.onDisable();
    }
    
    private boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(Speed.mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01);
        for (int x = floor_double(Speed.mc.field_71439_g.func_174813_aQ().field_72340_a); x < floor_double(Speed.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1; ++x) {
            for (int z = floor_double(Speed.mc.field_71439_g.func_174813_aQ().field_72339_c); z < floor_double(Speed.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1; ++z) {
                final Block block = Speed.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                if (!(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    public void setSpeed(final float speed) {
        Speed.mc.field_71439_g.field_70159_w = -(Math.sin(getDirection()) * speed);
        Speed.mc.field_71439_g.field_70179_y = Math.cos(getDirection()) * speed;
    }
    
    public static double getDirection() {
        float var1 = Speed.mc.field_71439_g.field_70177_z;
        if (Speed.mc.field_71439_g.field_191988_bg < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.field_71439_g.field_191988_bg < 0.0f) {
            forward = -0.5f;
        }
        else if (Speed.mc.field_71439_g.field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.field_71439_g.field_70702_br > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Speed.mc.field_71439_g.field_70702_br < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
    
    public static int floor_double(final double value) {
        final int i = (int)value;
        return (value < i) ? (i - 1) : i;
    }
    
    private void Forward(final double motionY, final float groundmultiplier, final double airmultiplier) {
        if (Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) {
            Speed.mc.field_71439_g.func_70031_b(true);
            if (Speed.mc.field_71439_g.field_70122_E) {
                if (motionY > 0.01) {
                    Speed.mc.field_71439_g.field_70181_x = motionY;
                }
                final float a = Converter();
                final EntityPlayerSP field_71439_g = Speed.mc.field_71439_g;
                field_71439_g.field_70159_w -= MathHelper.func_76126_a(a) * groundmultiplier;
                final EntityPlayerSP field_71439_g2 = Speed.mc.field_71439_g;
                field_71439_g2.field_70179_y += MathHelper.func_76134_b(a) * groundmultiplier;
            }
            else {
                final double sqrt = Math.sqrt(Speed.mc.field_71439_g.field_70159_w * Speed.mc.field_71439_g.field_70159_w + Speed.mc.field_71439_g.field_70179_y * Speed.mc.field_71439_g.field_70179_y);
                final double n3 = Converter();
                Speed.mc.field_71439_g.field_70159_w = -Math.sin(n3) * airmultiplier * sqrt;
                Speed.mc.field_71439_g.field_70179_y = Math.cos(n3) * airmultiplier * sqrt;
            }
        }
    }
    
    public static float Converter() {
        float rotationYaw = Speed.mc.field_71439_g.field_70177_z;
        if (Speed.mc.field_71439_g.field_191988_bg < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (Speed.mc.field_71439_g.field_191988_bg < 0.0f) {
            n = -0.5f;
        }
        else if (Speed.mc.field_71439_g.field_191988_bg > 0.0f) {
            n = 0.5f;
        }
        if (Speed.mc.field_71439_g.field_70702_br > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Speed.mc.field_71439_g.field_70702_br < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
}
