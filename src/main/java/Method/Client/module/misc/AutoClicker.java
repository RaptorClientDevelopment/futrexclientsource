package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;

public class AutoClicker extends Module
{
    Setting Type;
    Setting Delay;
    Setting Hold;
    private final TimerUtils timer;
    
    public AutoClicker() {
        super("AutoClicker", 0, Category.MISC, "Auto Clicker");
        this.Type = Main.setmgr.add(new Setting("Click Mode", this, "Left", new String[] { "Left", "Right" }));
        this.Delay = Main.setmgr.add(new Setting("Delay", this, 0.2, 0.0, 5.0, false));
        this.Hold = Main.setmgr.add(new Setting("Hold", this, false));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!this.Hold.getValBoolean()) {
            AutoClicker.mc.field_71474_y.field_74312_F.field_74513_e = false;
            AutoClicker.mc.field_71474_y.field_74313_G.field_74513_e = false;
        }
        if (this.Type.getValString().equalsIgnoreCase("Left") && this.timer.isDelay((long)this.Delay.getValDouble() * 1000L)) {
            AutoClicker.mc.field_71474_y.field_74312_F.field_74513_e = true;
            this.timer.setLastMS();
        }
        if (this.Type.getValString().equalsIgnoreCase("Right") && this.timer.isDelay((long)this.Delay.getValDouble() * 1000L)) {
            AutoClicker.mc.field_71474_y.field_74313_G.field_74513_e = true;
            this.timer.setLastMS();
        }
    }
    
    @Override
    public void onDisable() {
        AutoClicker.mc.field_71474_y.field_74312_F.field_74513_e = false;
        AutoClicker.mc.field_71474_y.field_74313_G.field_74513_e = false;
    }
}
