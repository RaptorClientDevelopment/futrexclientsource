package Method.Client.module.Onscreen.Display;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import Method.Client.managers.*;
import net.minecraft.client.gui.*;
import java.util.*;

public final class PlayerCount extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting Friends;
    static Setting background;
    static Setting Shadow;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting FontSize;
    
    public PlayerCount() {
        super("PlayerCount", 0, Category.ONSCREEN, "PlayerCount");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(PlayerCount.Friends = new Setting("Friends", this, false));
        Main.setmgr.add(PlayerCount.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(PlayerCount.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(PlayerCount.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(PlayerCount.background = new Setting("background", this, false));
        Main.setmgr.add(PlayerCount.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(PlayerCount.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(PlayerCount.xpos = new Setting("xpos", this, 200.0, -20.0, PlayerCount.mc.field_71443_c + 40, true));
        Main.setmgr.add(PlayerCount.ypos = new Setting("ypos", this, 140.0, -20.0, PlayerCount.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("PlayerCountSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("PlayerCountSET", false);
    }
    
    public static class PlayerCountRUN extends PinableFrame
    {
        int lasting;
        
        public PlayerCountRUN() {
            super("PlayerCountSET", new String[0], (int)PlayerCount.ypos.getValDouble(), (int)PlayerCount.xpos.getValDouble());
            this.lasting = 0;
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, PlayerCount.xpos, PlayerCount.ypos, PlayerCount.Frame, PlayerCount.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, PlayerCount.xpos, PlayerCount.ypos, PlayerCount.Frame, PlayerCount.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            String playerCount = "ONLINE: " + this.mc.field_71439_g.field_71174_a.func_175106_d().size();
            if (PlayerCount.Friends.getValBoolean()) {
                if (this.mc.field_71439_g.field_70173_aa % 20 == 0) {
                    int onlinefriend = 0;
                    for (final EntityPlayer s : this.mc.field_71441_e.field_73010_i) {
                        if (FriendManager.friendsList.contains(s.func_70005_c_())) {
                            ++onlinefriend;
                        }
                    }
                    this.lasting = onlinefriend;
                }
                playerCount = playerCount + " Friends: " + this.lasting;
            }
            this.fontSelect(PlayerCount.Frame, playerCount, this.getX(), this.getY() + 10, PlayerCount.TextColor.getcolor(), PlayerCount.Shadow.getValBoolean());
            if (PlayerCount.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(PlayerCount.Frame, playerCount), this.y + 20, PlayerCount.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
