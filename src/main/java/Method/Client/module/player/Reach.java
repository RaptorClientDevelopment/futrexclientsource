package Method.Client.module.player;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import net.minecraft.entity.player.*;

public class Reach extends Module
{
    Setting Reach;
    
    public Reach() {
        super("Reach", 0, Category.PLAYER, "Reach");
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("Reach", this, 10.0, 0.0, 20.0, true);
        this.Reach = setting;
        this.Reach = setmgr.add(setting);
    }
    
    @Override
    public void onEnable() {
        Method.Client.module.player.Reach.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111128_a(this.Reach.getValDouble());
    }
    
    @Override
    public void onDisable() {
        Method.Client.module.player.Reach.mc.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111128_a(5.0);
    }
}
