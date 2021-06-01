package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.proxy.Overrides.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class NoEffect extends Module
{
    Setting hurtcam;
    Setting Levitate;
    Setting weather;
    Setting Time;
    Setting Settime;
    Setting fire;
    Setting push;
    Setting NoVoid;
    public static Setting NoScreenEvents;
    
    public NoEffect() {
        super("NoEffect", 0, Category.PLAYER, "Prevent effects such as weather");
        this.hurtcam = Main.setmgr.add(new Setting("hurtcam", this, false));
        this.Levitate = Main.setmgr.add(new Setting("Levitate", this, false));
        this.weather = Main.setmgr.add(new Setting("weather", this, false));
        this.Time = Main.setmgr.add(new Setting("Time", this, 0.0, 0.0, 18000.0, true));
        this.Settime = Main.setmgr.add(new Setting("Settime", this, false));
        this.fire = Main.setmgr.add(new Setting("fire", this, false));
        this.push = Main.setmgr.add(new Setting("push", this, false));
        this.NoVoid = Main.setmgr.add(new Setting("NoVoid", this, false));
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(NoEffect.NoScreenEvents = new Setting("NoScreenEvents", this, false));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return !(packet instanceof SPacketTimeUpdate) || !this.Settime.getValBoolean();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        EntityRenderMixin.Camswitch = !this.hurtcam.getValBoolean();
        if (this.weather.getValBoolean()) {
            NoEffect.mc.field_71441_e.func_72912_H().func_76084_b(false);
            NoEffect.mc.field_71441_e.func_72894_k(0.0f);
            NoEffect.mc.field_71441_e.func_72912_H().func_76090_f(0);
            NoEffect.mc.field_71441_e.func_72912_H().func_76069_a(false);
        }
        if (this.push.getValBoolean()) {
            NoEffect.mc.field_71439_g.field_70144_Y = 1.0f;
        }
        if (this.Levitate.getValBoolean() && NoEffect.mc.field_71439_g.func_70644_a((Potion)Objects.requireNonNull(Potion.func_188412_a(25)))) {
            NoEffect.mc.field_71439_g.func_184596_c(Potion.func_188412_a(25));
        }
        if (this.NoVoid.getValBoolean()) {
            if (NoEffect.mc.field_71439_g.field_70163_u <= 0.5) {
                NoEffect.mc.field_71439_g.field_70701_bs = 10.0f;
                NoEffect.mc.field_71439_g.func_70664_aZ();
            }
            final EntityPlayerSP field_71439_g = NoEffect.mc.field_71439_g;
            field_71439_g.field_70181_x += 0.1;
        }
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (NoEffect.mc.field_71439_g.func_70027_ad() && this.fire.getValBoolean()) {
            NoEffect.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer(NoEffect.mc.field_71439_g.field_70122_E));
            event.getEntityLiving().func_70066_B();
            NoEffect.mc.field_71439_g.func_70015_d(0);
        }
        if (this.Settime.getValBoolean()) {
            NoEffect.mc.field_71441_e.func_72877_b((long)this.Time.getValDouble());
        }
    }
}
