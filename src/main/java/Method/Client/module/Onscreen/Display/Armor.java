package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public final class Armor extends Module
{
    static Setting Rotation;
    static Setting xpos;
    static Setting ypos;
    static Setting Background;
    static Setting Numbervals;
    static Setting Reverse;
    static Setting Shadow;
    static Setting Frame;
    static Setting Color;
    static Setting FontSize;
    
    public Armor() {
        super("Armor", 0, Category.ONSCREEN, "Armor");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Armor.Color = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Armor.Rotation = new Setting("Rotation", this, false));
        Main.setmgr.add(Armor.Numbervals = new Setting("Numbervals", this, false));
        Main.setmgr.add(Armor.Reverse = new Setting("Reverse", this, false));
        Main.setmgr.add(Armor.Background = new Setting("Background", this, false));
        Main.setmgr.add(Armor.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Armor.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Armor.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Armor.xpos = new Setting("xpos", this, 200.0, -20.0, Armor.mc.field_71443_c / 2 + 40, true));
        Main.setmgr.add(Armor.ypos = new Setting("ypos", this, 10.0, -20.0, Armor.mc.field_71440_d / 2 + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("ArmorSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("ArmorSET", false);
    }
    
    public static class ArmorRUN extends PinableFrame
    {
        public ArmorRUN() {
            super("ArmorSET", new String[0], (int)Armor.ypos.getValDouble(), (int)Armor.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Armor.xpos, Armor.ypos, Armor.Frame, Armor.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Armor.xpos, Armor.ypos, Armor.Frame, Armor.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            int space = 0;
            if (!Armor.Reverse.getValBoolean()) {
                for (int i = 0; i <= 3; ++i) {
                    space = this.Armordisplay(space, i);
                }
            }
            if (Armor.Reverse.getValBoolean()) {
                for (int i = 3; i >= 0; --i) {
                    space = this.Armordisplay(space, i);
                }
            }
            super.onRenderGameOverlay(event);
        }
        
        private int Armordisplay(int space, final int i) {
            final ItemStack stack = this.mc.field_71439_g.field_71069_bz.func_75139_a(8 - i).func_75211_c();
            if (stack != ItemStack.field_190927_a) {
                RenderHelper.func_74520_c();
                if (Armor.Rotation.getValBoolean()) {
                    if (Armor.Background.getValBoolean()) {
                        Gui.func_73734_a(this.x, this.y + 10, this.x + 72, this.y + 25, Armor.Color.getcolor());
                    }
                    this.mc.func_175599_af().func_180450_b(stack, this.getX() + space, this.getY() + 10);
                    this.mc.func_175599_af().func_175030_a(this.mc.field_71466_p, stack, this.getX() + space, this.getY() + 10);
                    if (Armor.Numbervals.getValBoolean()) {
                        this.fontSelect(Armor.Frame, String.valueOf(stack.func_77958_k() - stack.func_77952_i()), this.getX() + space, this.getY(), (stack.func_77958_k() - stack.func_77952_i() < 30) ? -65536 : -1, Armor.Shadow.getValBoolean());
                    }
                }
                else {
                    if (Armor.Background.getValBoolean()) {
                        Gui.func_73734_a(this.x, this.y + 10, this.x + 15, this.y + 80, Armor.Color.getcolor());
                    }
                    this.mc.func_175599_af().func_180450_b(stack, this.getX(), this.getY() + space + 10);
                    this.mc.func_175599_af().func_175030_a(this.mc.field_71466_p, stack, this.getX(), this.getY() + space + 10);
                    if (Armor.Numbervals.getValBoolean()) {
                        this.fontSelect(Armor.Frame, String.valueOf(stack.func_77952_i()), this.getX() + 10, this.getY() + 10 + space, -1, Armor.Shadow.getValBoolean());
                    }
                }
                RenderHelper.func_74518_a();
                space += 18;
            }
            return space;
        }
    }
}
