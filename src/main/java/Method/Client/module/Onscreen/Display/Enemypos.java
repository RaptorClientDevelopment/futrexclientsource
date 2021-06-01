package Method.Client.module.Onscreen.Display;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import Method.Client.managers.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.entity.*;
import java.awt.*;

public final class Enemypos extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting Friends;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting ColorDistance;
    static Setting Background;
    static Setting LefttoRight;
    static Setting Shadow;
    static Setting FontSize;
    
    public Enemypos() {
        super("Enemypos", 0, Category.ONSCREEN, "Enemypos");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Enemypos.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Enemypos.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Enemypos.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Enemypos.LefttoRight = new Setting("LefttoRight", this, true));
        Main.setmgr.add(Enemypos.Friends = new Setting("Friends", this, true));
        Main.setmgr.add(Enemypos.ColorDistance = new Setting("ColorDistance", this, true));
        Main.setmgr.add(Enemypos.Background = new Setting("Background", this, false));
        Main.setmgr.add(Enemypos.xpos = new Setting("xpos", this, 200.0, -20.0, Enemypos.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Enemypos.ypos = new Setting("ypos", this, 60.0, -20.0, Enemypos.mc.field_71440_d / 2 + 40, true));
        Main.setmgr.add(Enemypos.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Enemypos.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("EnemyposSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("EnemyposSET", false);
    }
    
    public static class EnemyposRUN extends PinableFrame
    {
        public EnemyposRUN() {
            super("EnemyposSET", new String[0], (int)Enemypos.ypos.getValDouble(), (int)Enemypos.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Enemypos.xpos, Enemypos.ypos, Enemypos.Frame, Enemypos.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Enemypos.xpos, Enemypos.ypos, Enemypos.Frame, Enemypos.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            int yCount = this.y + this.barHeight + 3;
            for (final EntityPlayer player : this.mc.field_71441_e.field_73010_i) {
                if (!FriendManager.isFriend(player.func_70005_c_()) || !Enemypos.Friends.getValBoolean()) {
                    if (player.func_70005_c_().equals(this.mc.field_71439_g.func_70005_c_())) {
                        continue;
                    }
                    final int Lr = Enemypos.LefttoRight.getValBoolean() ? (this.widthcal(Enemypos.Frame, player.func_70005_c_() + this.Pos(player)) - 70) : -3;
                    if (Enemypos.Background.getValBoolean()) {
                        Gui.func_73734_a(this.x + 4, yCount, this.widthcal(Enemypos.Frame, player.func_70005_c_() + this.Pos(player)) + this.x + 3, yCount + this.heightcal(Enemypos.Frame, player.func_70005_c_() + player.func_180425_c()) - 1, Enemypos.Background.getcolor());
                    }
                    this.fontSelect(Enemypos.Frame, player.func_70005_c_() + this.Pos(player), this.x - Lr, yCount, Enemypos.ColorDistance.getValBoolean() ? this.distancecolor(player) : Enemypos.TextColor.getcolor(), Enemypos.Shadow.getValBoolean());
                    yCount += 8;
                }
            }
            super.onRenderGameOverlay(event);
        }
        
        private int distancecolor(final EntityPlayer player) {
            int g = 0;
            int r = 0;
            if (this.mc.field_71439_g.func_70032_d((Entity)player) > 50.0f && this.mc.field_71439_g.func_70032_d((Entity)player) < 100.0f) {
                g = (int)((this.mc.field_71439_g.func_70032_d((Entity)player) - 50.0f) * 5.1);
            }
            if (this.mc.field_71439_g.func_70032_d((Entity)player) < 50.0f) {
                r = (int)(this.mc.field_71439_g.func_70032_d((Entity)player) * 5.1);
            }
            this.mc.field_71439_g.func_70032_d((Entity)player);
            return new Color(r, g, 0).getRGB();
        }
        
        public String Pos(final EntityPlayer player) {
            return " X:" + player.func_180425_c().func_177958_n() + ", Y:" + player.func_180425_c().func_177956_o() + ", Z:" + player.func_180425_c().func_177952_p();
        }
    }
}
