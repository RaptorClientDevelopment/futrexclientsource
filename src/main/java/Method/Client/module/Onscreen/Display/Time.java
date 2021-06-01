package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import java.text.*;
import java.util.*;
import net.minecraft.client.gui.*;

public final class Time extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting Worldtime;
    static Setting GameTime;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Shadow;
    static Setting background;
    static Setting FontSize;
    
    public Time() {
        super("Time", 0, Category.ONSCREEN, "Time");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Time.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Time.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Time.Worldtime = new Setting("Worldtime", this, true));
        Main.setmgr.add(Time.GameTime = new Setting("GameTime", this, false));
        Main.setmgr.add(Time.background = new Setting("background", this, false));
        Main.setmgr.add(Time.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Time.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Time.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Time.xpos = new Setting("xpos", this, 200.0, -20.0, Time.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Time.ypos = new Setting("ypos", this, 190.0, -20.0, Time.mc.field_71440_d / 2 + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("TimeSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("TimeSET", false);
    }
    
    public static class TimeRUN extends PinableFrame
    {
        public TimeRUN() {
            super("TimeSET", new String[0], (int)Time.ypos.getValDouble(), (int)Time.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Time.xpos, Time.ypos, Time.Frame, Time.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Time.xpos, Time.ypos, Time.Frame, Time.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            String time = "";
            if (Time.Worldtime.getValBoolean()) {
                time = new SimpleDateFormat("h:mm a").format(new Date()) + "\n  ";
            }
            if (Time.GameTime.getValBoolean()) {
                time += this.mc.field_71441_e.func_72820_D();
            }
            this.fontSelect(Time.Frame, time, this.getX(), this.getY() + 10, Time.TextColor.getcolor(), Time.Shadow.getValBoolean());
            if (Time.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Time.Frame, time), this.y + 20, Time.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
