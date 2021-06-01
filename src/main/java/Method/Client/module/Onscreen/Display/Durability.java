package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;

public final class Durability extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Background;
    static Setting Shadow;
    static Setting FontSize;
    
    public Durability() {
        super("Durability", 0, Category.ONSCREEN, "Durability");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Durability.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Durability.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Durability.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Durability.Background = new Setting("Background", this, false));
        Main.setmgr.add(Durability.xpos = new Setting("xpos", this, 200.0, -20.0, Durability.mc.field_71443_c + 40, true));
        Main.setmgr.add(Durability.ypos = new Setting("ypos", this, 20.0, -20.0, Durability.mc.field_71440_d + 40, true));
        Main.setmgr.add(Durability.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Durability.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("DurabilitySET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("DurabilitySET", false);
    }
    
    public static class DurabilityRUN extends PinableFrame
    {
        public DurabilityRUN() {
            super("DurabilitySET", new String[0], (int)Durability.ypos.getValDouble(), (int)Durability.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Durability.xpos, Durability.ypos, Durability.Frame, Durability.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Durability.xpos, Durability.ypos, Durability.Frame, Durability.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final ItemStack stack = this.mc.field_71439_g.func_184614_ca();
            if (!stack.func_190926_b() && (stack.func_77973_b() instanceof ItemTool || stack.func_77973_b() instanceof ItemArmor || stack.func_77973_b() instanceof ItemSword)) {
                final String Durability = "Durability: " + (stack.func_77958_k() - stack.func_77952_i());
                if (Method.Client.module.Onscreen.Display.Durability.Background.getValBoolean()) {
                    Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Method.Client.module.Onscreen.Display.Durability.Frame, Durability), this.y + 22, Method.Client.module.Onscreen.Display.Durability.BgColor.getcolor());
                }
                this.fontSelect(Method.Client.module.Onscreen.Display.Durability.Frame, Durability, this.getX(), this.getY() + 10, Method.Client.module.Onscreen.Display.Durability.TextColor.getcolor(), Method.Client.module.Onscreen.Display.Durability.Shadow.getValBoolean());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
