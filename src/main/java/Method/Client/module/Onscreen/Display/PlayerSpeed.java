package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import java.text.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;

public final class PlayerSpeed extends Module
{
    static Setting TextColor;
    static Setting BgColor;
    static Setting background;
    static Setting Shadow;
    static Setting Frame;
    static Setting xpos;
    static Setting ypos;
    static Setting Decimal;
    static Setting FontSize;
    static Setting MotionX;
    static Setting MotionY;
    static Setting MotionZ;
    
    public PlayerSpeed() {
        super("PlayerSpeed", 0, Category.ONSCREEN, "PlayerSpeed");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(PlayerSpeed.TextColor = new Setting("TextColor", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(PlayerSpeed.BgColor = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(PlayerSpeed.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(PlayerSpeed.background = new Setting("background", this, false));
        Main.setmgr.add(PlayerSpeed.MotionX = new Setting("MotionX", this, false));
        Main.setmgr.add(PlayerSpeed.MotionY = new Setting("MotionY", this, false));
        Main.setmgr.add(PlayerSpeed.MotionZ = new Setting("MotionZ", this, false));
        Main.setmgr.add(PlayerSpeed.Decimal = new Setting("Decimal", this, 2.0, 0.0, 5.0, true));
        Main.setmgr.add(PlayerSpeed.xpos = new Setting("xpos", this, 200.0, -20.0, PlayerSpeed.mc.field_71443_c + 40, true));
        Main.setmgr.add(PlayerSpeed.ypos = new Setting("ypos", this, 150.0, -20.0, PlayerSpeed.mc.field_71440_d + 40, true));
        Main.setmgr.add(PlayerSpeed.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(PlayerSpeed.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("SpeedSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("SpeedSET", false);
    }
    
    public static class SpeedRUN extends PinableFrame
    {
        DecimalFormat decimalFormat;
        
        public SpeedRUN() {
            super("SpeedSET", new String[0], (int)PlayerSpeed.ypos.getValDouble(), (int)PlayerSpeed.xpos.getValDouble());
            this.decimalFormat = new DecimalFormat("0.00");
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, PlayerSpeed.xpos, PlayerSpeed.ypos, PlayerSpeed.Frame, PlayerSpeed.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, PlayerSpeed.xpos, PlayerSpeed.ypos, PlayerSpeed.Frame, PlayerSpeed.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            this.decimalFormat = this.getDecimalFormat((int)PlayerSpeed.Decimal.getValDouble());
            final double deltaX = this.mc.field_71439_g.field_70165_t - this.mc.field_71439_g.field_70169_q;
            final double deltaZ = this.mc.field_71439_g.field_70161_v - this.mc.field_71439_g.field_70166_s;
            final float tickRate = this.mc.field_71428_T.field_194149_e / 1000.0f;
            String bps = "BPS: " + this.decimalFormat.format(MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ) / tickRate);
            if (PlayerSpeed.MotionX.getValBoolean()) {
                bps = bps + " MotionX: " + this.decimalFormat.format(this.mc.field_71439_g.field_70159_w);
            }
            if (PlayerSpeed.MotionY.getValBoolean()) {
                bps = bps + " MotionY: " + this.decimalFormat.format(this.mc.field_71439_g.field_70181_x);
            }
            if (PlayerSpeed.MotionZ.getValBoolean()) {
                bps = bps + " MotionZ: " + this.decimalFormat.format(this.mc.field_71439_g.field_70179_y);
            }
            this.fontSelect(PlayerSpeed.Frame, bps, this.getX(), this.getY() + 10, PlayerSpeed.TextColor.getcolor(), PlayerSpeed.Shadow.getValBoolean());
            if (PlayerSpeed.background.getValBoolean()) {
                Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(PlayerSpeed.Frame, bps), this.y + 20, PlayerSpeed.BgColor.getcolor());
            }
            super.onRenderGameOverlay(event);
        }
    }
}
