package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;

public final class Direction extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting xpos;
    static Setting ypos;
    static Setting Frame;
    static Setting Background;
    static Setting Shadow;
    static Setting FontSize;
    
    public Direction() {
        super("Direction", 0, Category.ONSCREEN, "Direction");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(Direction.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Direction.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Direction.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Direction.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(Direction.Background = new Setting("Background", this, false));
        Main.setmgr.add(Direction.xpos = new Setting("xpos", this, 200.0, -20.0, Direction.mc.field_71443_c + 40, true));
        Main.setmgr.add(Direction.ypos = new Setting("ypos", this, 40.0, -20.0, Direction.mc.field_71440_d + 40, true));
        Main.setmgr.add(Direction.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("DirectionSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("DirectionSET", false);
    }
    
    public static class DirectionRUN extends PinableFrame
    {
        public DirectionRUN() {
            super("DirectionSET", new String[0], (int)Direction.ypos.getValDouble(), (int)Direction.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Direction.xpos, Direction.ypos, Direction.Frame, Direction.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Direction.xpos, Direction.ypos, Direction.Frame, Direction.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            final String direction = String.format("%s " + ChatFormatting.GRAY + "%s", this.getFacing(), this.getTowards());
            if (Direction.Background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Direction.Frame, direction), this.y + 20, Direction.BgColor.getcolor());
            }
            this.fontSelect(Direction.Frame, direction, this.getX(), this.getY() + 10, Direction.TextColor.getcolor(), Direction.Shadow.getValBoolean());
            super.onRenderGameOverlay(event);
        }
        
        public String getFacing() {
            switch (MathHelper.func_76128_c(this.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
                case 0: {
                    return "South";
                }
                case 1: {
                    return "South West";
                }
                case 2: {
                    return "West";
                }
                case 3: {
                    return "North West";
                }
                case 4: {
                    return "North";
                }
                case 5: {
                    return "North East";
                }
                case 6: {
                    return "East";
                }
                case 7: {
                    return "South East";
                }
                default: {
                    return "Invalid";
                }
            }
        }
        
        private String getTowards() {
            switch (MathHelper.func_76128_c(this.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
                case 0: {
                    return "+Z";
                }
                case 1: {
                    return "-X +Z";
                }
                case 2: {
                    return "-X";
                }
                case 3: {
                    return "-X -Z";
                }
                case 4: {
                    return "-Z";
                }
                case 5: {
                    return "+X -Z";
                }
                case 6: {
                    return "+X";
                }
                case 7: {
                    return "+X +Z";
                }
                default: {
                    return "Invalid";
                }
            }
        }
    }
}
