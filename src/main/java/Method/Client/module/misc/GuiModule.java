package Method.Client.module.misc;

import Method.Client.clickgui.*;
import Method.Client.managers.*;
import Method.Client.module.*;
import java.util.*;
import Method.Client.*;
import net.minecraft.client.gui.*;

public class GuiModule extends Module
{
    public ClickGui clickgui;
    public static Setting Frame;
    public static Setting Button;
    public static Setting Subcomponents;
    public static Setting Animations;
    public static Setting Framecolor;
    public static Setting Background;
    public static Setting innercolor;
    public static Setting Hover;
    public static Setting Anispeed;
    public static Setting ColorAni;
    public static Setting Highlight;
    public static Setting border;
    
    public GuiModule() {
        super("ClickGUI", 54, Category.MISC, "Settings for the Clickgui");
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Arial");
        options.add("Impact");
        options.add("Times");
        options.add("MC");
        Main.setmgr.add(GuiModule.Frame = new Setting("Frame_Font", this, "Times", options));
        Main.setmgr.add(GuiModule.Button = new Setting("Button_Font", this, "Times", options));
        Main.setmgr.add(GuiModule.Subcomponents = new Setting("Sub_Font", this, "Times", options));
        Main.setmgr.add(GuiModule.Framecolor = new Setting("Frame", this, 0.0, 0.7, 0.65, 0.7));
        Main.setmgr.add(GuiModule.Background = new Setting("Background", this, 0.0, 1.0, 0.01, 0.22));
        Main.setmgr.add(GuiModule.Hover = new Setting("Hover", this, 0.0, 1.0, 0.01, 0.1));
        Main.setmgr.add(GuiModule.ColorAni = new Setting("ColorAni", this, 0.0, 1.0, 0.5, 0.4));
        Main.setmgr.add(GuiModule.innercolor = new Setting("innercolor", this, 0.68, 0.35, 0.05, 0.3));
        Main.setmgr.add(GuiModule.border = new Setting("border", this, false));
        Main.setmgr.add(GuiModule.Highlight = new Setting("Border Color", this, 0.0, 1.0, 1.0, 0.88, GuiModule.border, 9));
        Main.setmgr.add(GuiModule.Animations = new Setting("Animations", this, true));
        Main.setmgr.add(GuiModule.Anispeed = new Setting("ButtonSpeed", this, 1.8, 0.0, 3.0, false));
    }
    
    @Override
    public void setup() {
        this.visible = false;
    }
    
    @Override
    public void onEnable() {
        GuiModule.mc.func_147108_a((GuiScreen)Main.ClickGui);
        this.toggle();
        super.onEnable();
    }
}
