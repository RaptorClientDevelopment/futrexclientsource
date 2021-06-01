package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import Method.Client.module.*;
import net.minecraft.client.gui.*;
import Method.Client.utils.visual.*;
import java.util.*;

public final class EnabledMods extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting xpos;
    static Setting ypos;
    static Setting ToptoBottom;
    static Setting LefttoRight;
    static Setting Frame;
    static Setting Shadow;
    static Setting FontSize;
    static Setting Wave;
    
    public EnabledMods() {
        super("EnabledMods", 0, Category.ONSCREEN, "EnabledMods");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(EnabledMods.TextColor = new Setting("TextColor", this, 0.0, 0.75, 0.85, 0.85));
        Main.setmgr.add(EnabledMods.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(EnabledMods.Wave = new Setting("Wave", this, true));
        Main.setmgr.add(EnabledMods.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(EnabledMods.ToptoBottom = new Setting("ToptoBottom", this, true));
        Main.setmgr.add(EnabledMods.LefttoRight = new Setting("LefttoRight", this, false));
        Main.setmgr.add(EnabledMods.background = new Setting("background", this, false));
        Main.setmgr.add(EnabledMods.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(EnabledMods.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(EnabledMods.xpos = new Setting("xpos", this, 0.0, -20.0, EnabledMods.mc.field_71443_c + 40, true));
        Main.setmgr.add(EnabledMods.ypos = new Setting("ypos", this, 0.0, -20.0, EnabledMods.mc.field_71440_d + 40, true));
        this.setToggled(true);
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("EnabledModsSet", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("EnabledModsSet", false);
    }
    
    public static class EnabledModsRUN extends PinableFrame
    {
        public EnabledModsRUN() {
            super("EnabledModsSet", new String[0], (int)EnabledMods.ypos.getValDouble(), (int)EnabledMods.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, EnabledMods.xpos, EnabledMods.ypos, EnabledMods.Frame, EnabledMods.FontSize);
            this.setPinned(true);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, EnabledMods.xpos, EnabledMods.ypos, EnabledMods.Frame, EnabledMods.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            int yCount = this.y + 3;
            for (final Module module : ModuleManager.getSortedMods(EnabledMods.ToptoBottom.getValBoolean(), true, true)) {
                final int Lr = EnabledMods.LefttoRight.getValBoolean() ? (this.widthcal(EnabledMods.Frame, module.getDisplayName()) - 70) : -3;
                if (EnabledMods.background.getValBoolean()) {
                    Gui.func_73734_a(this.x - Lr, yCount + 3, this.widthcal(EnabledMods.Frame, module.getDisplayName()) + this.x + 2 - Lr, yCount + this.heightcal(EnabledMods.Frame, module.getDisplayName()) + 1, EnabledMods.BgColor.getcolor());
                }
                this.fontSelect(EnabledMods.Frame, module.getDisplayName(), this.x - Lr, yCount, EnabledMods.Wave.getValBoolean() ? ColorUtils.wave((yCount - this.y - this.barHeight - 3) / 8, EnabledMods.TextColor.getSat(), EnabledMods.TextColor.getBri()).getRGB() : EnabledMods.TextColor.getcolor(), EnabledMods.Shadow.getValBoolean());
                yCount += 9;
            }
            super.onRenderGameOverlay(event);
        }
    }
}
