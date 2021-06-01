package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import Method.Client.utils.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.settings.*;

public class AntiAFK extends Module
{
    boolean switcheraro;
    Setting spin;
    Setting delay;
    Setting swing;
    Setting walk;
    TimerUtils timer;
    
    public AntiAFK() {
        super("AntiAFK", 0, Category.PLAYER, "Preforms action to not be kicked!");
        this.spin = Main.setmgr.add(new Setting("spin", this, false));
        this.delay = Main.setmgr.add(new Setting("delay", this, 1.0, 0.0, 60.0, false));
        this.swing = Main.setmgr.add(new Setting("swing", this, true));
        this.walk = Main.setmgr.add(new Setting("walk", this, false));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay((long)(this.delay.getValDouble() * 1000.0))) {
            this.switcheraro = !this.switcheraro;
            if (this.switcheraro) {
                if (this.spin.getValBoolean()) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Rotation((float)Utils.random(-160, 160), (float)Utils.random(-160, 160), true));
                }
                if (this.swing.getValBoolean()) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                if (this.walk.getValBoolean()) {
                    final int c = Utils.random(0, 10);
                    if (c == 4) {
                        KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74351_w.func_151463_i(), true);
                    }
                    if (c == 1) {
                        KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74368_y.func_151463_i(), true);
                    }
                    if (c == 2) {
                        KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74370_x.func_151463_i(), true);
                    }
                    if (c == 3) {
                        KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74366_z.func_151463_i(), true);
                    }
                }
            }
            else if (this.walk.getValBoolean()) {
                KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74351_w.func_151463_i(), false);
                KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74366_z.func_151463_i(), false);
                KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74368_y.func_151463_i(), false);
                KeyBinding.func_74510_a(AntiAFK.mc.field_71474_y.field_74370_x.func_151463_i(), false);
            }
            this.timer.setLastMS();
        }
    }
}
