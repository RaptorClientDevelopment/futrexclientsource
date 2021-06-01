package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.potion.*;
import net.minecraft.client.gui.*;
import java.util.*;

public final class Potions extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting Shadow;
    static Setting name;
    static Setting amplifer;
    static Setting duration;
    static Setting Frame;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    static Setting FontSize;
    
    public Potions() {
        super("Potions", 0, Category.ONSCREEN, "Potions");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Potions.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Potions.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Potions.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Potions.background = new Setting("background", this, false));
        Main.setmgr.add(Potions.name = new Setting("name", this, true));
        Main.setmgr.add(Potions.amplifer = new Setting("amplifer", this, false));
        Main.setmgr.add(Potions.duration = new Setting("duration", this, false));
        Main.setmgr.add(Potions.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Potions.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Potions.xpos = new Setting("xpos", this, 200.0, -20.0, Potions.mc.field_71443_c + 40, true));
        Main.setmgr.add(Potions.ypos = new Setting("ypos", this, 160.0, -20.0, Potions.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("PotionsSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("PotionsSET", false);
    }
    
    public static class PotionsRUN extends PinableFrame
    {
        public PotionsRUN() {
            super("PotionsSET", new String[0], (int)Potions.ypos.getValDouble(), (int)Potions.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Potions.xpos, Potions.ypos, Potions.Frame, Potions.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Potions.xpos, Potions.ypos, Potions.Frame, Potions.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            int offset = 0;
            for (final PotionEffect activePotionEffect : this.mc.field_71439_g.func_70651_bq()) {
                final String effect = Potions.name.getValBoolean() ? (activePotionEffect.func_76453_d().substring(7) + " ") : "";
                final String amp = Potions.amplifer.getValBoolean() ? ("x" + activePotionEffect.func_76458_c() + " ") : "";
                final String dur = Potions.duration.getValBoolean() ? (activePotionEffect.func_76459_b() / 20 + " ") : "";
                final String all = effect + "" + amp + "" + dur;
                this.fontSelect(Potions.Frame, all, this.getX(), this.getY() + 10 - offset, Potions.TextColor.getcolor(), Potions.Shadow.getValBoolean());
                if (Potions.background.getValBoolean()) {
                    Gui.func_73734_a(this.x, this.y + 10 - offset, this.x + this.widthcal(Potions.Frame, effect + amp + dur), this.y + 20 - offset, Potions.BgColor.getcolor());
                }
                offset += 10;
            }
            super.onRenderGameOverlay(event);
        }
    }
}
