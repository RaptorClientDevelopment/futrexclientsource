package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

public final class Blockview extends Module
{
    static Setting xpos;
    static Setting ypos;
    static Setting Background;
    static Setting Shadow;
    static Setting Frame;
    static Setting Color;
    static Setting TextColor;
    static Setting FontSize;
    static Setting Text;
    static Setting Image;
    
    public Blockview() {
        super("Blockview", 0, Category.ONSCREEN, "BlockOverlay");
    }
    
    @Override
    public void setup() {
        this.visible = false;
        Main.setmgr.add(Blockview.Color = new Setting("BGColor", this, 0.01, 0.0, 0.3, 0.22));
        Main.setmgr.add(Blockview.TextColor = new Setting("TextColor", this, 0.01, 0.0, 0.0, 0.55));
        Main.setmgr.add(Blockview.Background = new Setting("Background", this, false));
        Main.setmgr.add(Blockview.Shadow = new Setting("Shadow", this, true));
        Main.setmgr.add(Blockview.Text = new Setting("Text", this, true));
        Main.setmgr.add(Blockview.Image = new Setting("Image", this, true));
        Main.setmgr.add(Blockview.Frame = new Setting("Font", this, "Times", this.fontoptions()));
        Main.setmgr.add(Blockview.FontSize = new Setting("FontSize", this, 22.0, 10.0, 40.0, true));
        Main.setmgr.add(Blockview.xpos = new Setting("xpos", this, 200.0, -20.0, Blockview.mc.field_71443_c + 40, true));
        Main.setmgr.add(Blockview.ypos = new Setting("ypos", this, 10.0, -20.0, Blockview.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("BlockviewSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("BlockviewSET", false);
    }
    
    public static class BlockviewRUN extends PinableFrame
    {
        public BlockviewRUN() {
            super("BlockviewSET", new String[0], (int)Blockview.ypos.getValDouble(), (int)Blockview.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            this.GetSetup(this, Blockview.xpos, Blockview.ypos, Blockview.Frame, Blockview.FontSize);
        }
        
        @Override
        public void Ongui() {
            this.GetInit(this, Blockview.xpos, Blockview.ypos, Blockview.Frame, Blockview.FontSize);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            if (this.mc.field_71476_x.field_72313_a == RayTraceResult.Type.BLOCK) {
                final Block block = this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c();
                if (Block.func_149682_b(block) == 0) {
                    return;
                }
                if (Blockview.Text.getValBoolean()) {
                    if (Blockview.Background.getValBoolean()) {
                        Gui.func_73734_a(this.x, this.y + 10, this.x + this.widthcal(Blockview.Frame, block.func_149732_F()), this.y + 22, Blockview.Color.getcolor());
                    }
                    this.fontSelect(Blockview.Frame, block.func_149732_F(), this.getX(), this.getY() + 10, Blockview.TextColor.getcolor(), Blockview.Shadow.getValBoolean());
                }
                if (Blockview.Image.getValBoolean()) {
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179109_b((float)(this.x + 8), (float)(this.y - 1), 0.0f);
                    GlStateManager.func_179139_a(0.75, 0.75, 0.75);
                    RenderHelper.func_74520_c();
                    this.mc.func_175599_af().func_180450_b(new ItemStack(block), 0, 0);
                    RenderHelper.func_74518_a();
                    GlStateManager.func_179121_F();
                }
            }
            super.onRenderGameOverlay(event);
        }
    }
}
