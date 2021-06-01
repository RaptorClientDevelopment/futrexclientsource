package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import Method.Client.utils.system.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;

public class Disconnect extends Module
{
    Setting leaveHealth;
    Setting Totem;
    Setting Playersight;
    
    public Disconnect() {
        super("Auto Disconnect", 0, Category.PLAYER, "Disconnect on low health");
        this.leaveHealth = Main.setmgr.add(new Setting("LeaveHealth", this, 4.0, 0.0, 20.0, true));
        this.Totem = Main.setmgr.add(new Setting("Totem", this, false));
        this.Playersight = Main.setmgr.add(new Setting("Player", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Disconnect.mc.field_71439_g.func_110143_aJ() <= this.leaveHealth.getValDouble()) {
            if (!this.Totem.getValBoolean()) {
                this.Quit();
            }
            else if (Totemcount() < 1) {
                this.Quit();
            }
            this.toggle();
        }
    }
    
    public static int Totemcount() {
        int totem = 0;
        if (Disconnect.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_190929_cY)) {
            ++totem;
        }
        for (int i = 9; i <= 44; ++i) {
            if (Disconnect.MC.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b() == Items.field_190929_cY) {
                ++totem;
            }
        }
        return totem;
    }
    
    private void Quit() {
        final boolean flag = Wrapper.INSTANCE.mc().func_71387_A();
        Disconnect.mc.field_71441_e.func_72882_A();
        Wrapper.INSTANCE.mc().func_71403_a((WorldClient)null);
        if (flag) {
            Wrapper.INSTANCE.mc().func_147108_a((GuiScreen)new GuiMainMenu());
        }
        else {
            Wrapper.INSTANCE.mc().func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
        }
    }
}
