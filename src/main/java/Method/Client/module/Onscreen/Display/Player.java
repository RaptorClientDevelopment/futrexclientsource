package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import java.util.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;

public final class Player extends Module
{
    static Setting xpos;
    static Setting ypos;
    static Setting Scale;
    static Setting Nolook;
    
    public Player() {
        super("Player", 0, Category.ONSCREEN, "Player");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Player.xpos = new Setting("xpos", this, 200.0, -20.0, Player.mc.field_71443_c + 40, true));
        Main.setmgr.add(Player.ypos = new Setting("ypos", this, 20.0, -20.0, Player.mc.field_71440_d + 40, true));
        Main.setmgr.add(Player.Scale = new Setting("Scale", this, 1.0, 0.0, 5.0, false));
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Free");
        options.add("Mouse");
        options.add("None");
        Main.setmgr.add(Player.Nolook = new Setting("Mode", this, "Free", options));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("PlayerSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("PlayerSET", false);
    }
    
    public static class PlayerRUN extends PinableFrame
    {
        public PlayerRUN() {
            super("PlayerSET", new String[0], (int)Player.ypos.getValDouble(), (int)Player.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.x = (int)Player.xpos.getValDouble();
            this.y = (int)Player.ypos.getValDouble();
        }
        
        @Override
        public void Ongui() {
            if (!this.getDrag()) {
                this.x = (int)Player.xpos.getValDouble();
                this.y = (int)Player.ypos.getValDouble();
            }
            else {
                Player.xpos.setValDouble(this.x);
                Player.ypos.setValDouble(this.y);
            }
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71439_g == null) {
                return;
            }
            if (this.mc.field_71474_y.field_74320_O != 0) {
                return;
            }
            GlStateManager.func_179094_E();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (Player.Nolook.getValString().equalsIgnoreCase("Free")) {
                this.drawPlayer(this.x, this.y, (EntityLivingBase)this.mc.field_71439_g);
            }
            else {
                GuiInventory.func_147046_a(this.x + 17, this.y + 60, (int)(Player.Scale.getValDouble() * 30.0), Player.Nolook.getValString().equalsIgnoreCase("None") ? 0.0f : (this.x - Mouse.getX()), Player.Nolook.getValString().equalsIgnoreCase("None") ? 0.0f : (-this.mc.field_71440_d + Mouse.getY()), (EntityLivingBase)this.mc.field_71439_g);
            }
            GlStateManager.func_179121_F();
            super.onRenderGameOverlay(event);
        }
        
        private void drawPlayer(final int x, final int y, final EntityLivingBase ent) {
            GlStateManager.func_179109_b(x + 30.0f, y + 50.0f, 50.0f);
            GlStateManager.func_179152_a((float)(-Player.Scale.getValDouble()) * 24.0f, (float)Player.Scale.getValDouble() * 24.0f, (float)Player.Scale.getValDouble() * 24.0f);
            GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179084_k();
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a(true);
            RenderHelper.func_74519_b();
            GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179114_b(-(float)Math.atan(0.0) * 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
            final RenderManager renderManager = this.mc.func_175598_ae();
            renderManager.func_178631_a(180.0f);
            renderManager.func_178633_a(false);
            renderManager.func_188391_a((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
            renderManager.func_178633_a(true);
            RenderHelper.func_74518_a();
            GlStateManager.func_179147_l();
            GlStateManager.func_179097_i();
            GlStateManager.func_179132_a(false);
        }
    }
}
