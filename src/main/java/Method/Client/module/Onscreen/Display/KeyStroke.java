package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import java.util.*;

public final class KeyStroke extends Module
{
    static Setting TextColor;
    static Setting Presscolor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Background;
    static Setting Shadow;
    static Setting Clicks;
    static Setting ClicksPerSec;
    static Setting FontSize;
    
    public KeyStroke() {
        super("KeyStroke", 0, Category.ONSCREEN, "KeyStroke");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(KeyStroke.TextColor = new Setting("TextColor", this, 0.3, 0.4, 0.4, 1.0));
        Main.setmgr.add(KeyStroke.Presscolor = new Setting("Presscolor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(KeyStroke.BgColor = new Setting("BGColor", this, 0.01, 0.5, 0.3, 0.22));
        Main.setmgr.add(KeyStroke.Background = new Setting("Background", this, false));
        Main.setmgr.add(KeyStroke.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(KeyStroke.Clicks = new Setting("Clicks", this, false));
        Main.setmgr.add(KeyStroke.ClicksPerSec = new Setting("ClicksPerSec", this, false));
        Main.setmgr.add(KeyStroke.xpos = new Setting("xpos", this, 200.0, -20.0, KeyStroke.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(KeyStroke.ypos = new Setting("ypos", this, 220.0, -20.0, KeyStroke.mc.field_71440_d / 2 + 250, true));
        Main.setmgr.add(KeyStroke.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(KeyStroke.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("KeyStrokeSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("KeyStrokeSET", false);
    }
    
    public static class KeyStrokeRUN extends PinableFrame
    {
        ArrayList<Double> clicks;
        boolean startclick;
        
        public KeyStrokeRUN() {
            super("KeyStrokeSET", new String[0], (int)KeyStroke.ypos.getValDouble(), (int)KeyStroke.xpos.getValDouble());
            this.clicks = new ArrayList<Double>();
            this.startclick = false;
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, KeyStroke.xpos, KeyStroke.ypos, KeyStroke.Frame, KeyStroke.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, KeyStroke.xpos, KeyStroke.ypos, KeyStroke.Frame, KeyStroke.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71474_y.field_74312_F.field_74513_e) {
                this.startclick = true;
            }
            else if (this.startclick) {
                this.startclick = false;
                this.clicks.add((double)System.currentTimeMillis());
            }
            final ArrayList<Double> rem = new ArrayList<Double>();
            for (final Double click : this.clicks) {
                if (click + 1000.0 < System.currentTimeMillis()) {
                    rem.add(click);
                }
            }
            this.clicks.removeAll(rem);
            final int black = new Color(0, 0, 0, 40).getRGB();
            final int rain = KeyStroke.TextColor.getcolor();
            final int white = KeyStroke.Presscolor.getcolor();
            this.fontSelect(KeyStroke.Frame, this.mc.field_71474_y.field_74351_w.getDisplayName(), this.x + 18, this.y, this.mc.field_71474_y.field_74351_w.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            this.fontSelect(KeyStroke.Frame, this.mc.field_71474_y.field_74370_x.getDisplayName(), this.x, this.y + 20, this.mc.field_71474_y.field_74370_x.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            this.fontSelect(KeyStroke.Frame, this.mc.field_71474_y.field_74368_y.getDisplayName(), this.x + 20, this.y + 20, this.mc.field_71474_y.field_74368_y.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            this.fontSelect(KeyStroke.Frame, this.mc.field_71474_y.field_74366_z.getDisplayName(), this.x + 40, this.y + 20, this.mc.field_71474_y.field_74366_z.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            if (KeyStroke.Clicks.getValBoolean()) {
                if (KeyStroke.Background.getValBoolean()) {
                    Gui.func_73734_a(this.x, this.y + 40, this.x + 20, this.y + 20, this.mc.field_71474_y.field_74312_F.field_74513_e ? rain : black);
                    Gui.func_73734_a(this.x + 20, this.y + 40, this.x + 40, this.y + 20, this.mc.field_71474_y.field_74313_G.field_74513_e ? rain : black);
                }
                this.fontSelect(KeyStroke.Frame, "LMB", this.x, this.y + 40, this.mc.field_71474_y.field_74312_F.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
                this.fontSelect(KeyStroke.Frame, "RMB", this.x + 30, this.y + 40, this.mc.field_71474_y.field_74313_G.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            }
            if (KeyStroke.ClicksPerSec.getValBoolean()) {
                this.fontSelect(KeyStroke.Frame, "Clicks: " + this.clicks.size(), this.x, this.y + 60, this.mc.field_71474_y.field_74312_F.field_74513_e ? white : rain, KeyStroke.Shadow.getValBoolean());
            }
            if (KeyStroke.Background.getValBoolean()) {
                Gui.func_73734_a(this.x + 15, this.y, this.x + 25, this.y + 20, this.mc.field_71474_y.field_74351_w.field_74513_e ? rain : black);
                Gui.func_73734_a(this.x, this.y + 20, this.x + 10, this.y + 40, this.mc.field_71474_y.field_74370_x.field_74513_e ? rain : black);
                Gui.func_73734_a(this.x + 20, this.y + 20, this.x + 30, this.y + 40, this.mc.field_71474_y.field_74368_y.field_74513_e ? rain : black);
                Gui.func_73734_a(this.x + 40, this.y + 20, this.x + 50, this.y + 40, this.mc.field_71474_y.field_74366_z.field_74513_e ? rain : black);
            }
            super.onRenderGameOverlay(event);
        }
    }
}
