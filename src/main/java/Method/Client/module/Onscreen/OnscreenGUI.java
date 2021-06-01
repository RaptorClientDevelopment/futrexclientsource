package Method.Client.module.Onscreen;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import Method.Client.module.*;
import Method.Client.module.Onscreen.Display.*;
import Method.Client.clickgui.component.*;
import java.io.*;
import java.util.*;
import net.minecraftforge.client.event.*;

public class OnscreenGUI extends GuiScreen
{
    public static final ArrayList<PinableFrame> pinableFrames;
    private final Frame Onscreen;
    protected Minecraft field_146297_k;
    
    public OnscreenGUI() {
        this.field_146297_k = Minecraft.func_71410_x();
        (this.Onscreen = new Frame(Category.ONSCREEN)).setOpen(true);
        OnscreenGUI.pinableFrames.add(new Angles.AnglesRUN());
        OnscreenGUI.pinableFrames.add(new Player.PlayerRUN());
        OnscreenGUI.pinableFrames.add(new EnabledMods.EnabledModsRUN());
        OnscreenGUI.pinableFrames.add(new Armor.ArmorRUN());
        OnscreenGUI.pinableFrames.add(new Biome.BiomeRUN());
        OnscreenGUI.pinableFrames.add(new Blockview.BlockviewRUN());
        OnscreenGUI.pinableFrames.add(new Durability.DurabilityRUN());
        OnscreenGUI.pinableFrames.add(new Coords.CoordsRUN());
        OnscreenGUI.pinableFrames.add(new Direction.DirectionRUN());
        OnscreenGUI.pinableFrames.add(new Fps.FpsRUN());
        OnscreenGUI.pinableFrames.add(new CombatItems.CombatItemsRUN());
        OnscreenGUI.pinableFrames.add(new ChunkSize.ChunkSizeRUN());
        OnscreenGUI.pinableFrames.add(new Inventory.InventoryRUN());
        OnscreenGUI.pinableFrames.add(new NetherCords.NetherCordsRUN());
        OnscreenGUI.pinableFrames.add(new Ping.PingRUN());
        OnscreenGUI.pinableFrames.add(new Hole.HoleRUN());
        OnscreenGUI.pinableFrames.add(new PlayerCount.PlayerCountRUN());
        OnscreenGUI.pinableFrames.add(new Server.ServerRUN());
        OnscreenGUI.pinableFrames.add(new PlayerSpeed.SpeedRUN());
        OnscreenGUI.pinableFrames.add(new KeyStroke.KeyStrokeRUN());
        OnscreenGUI.pinableFrames.add(new Time.TimeRUN());
        OnscreenGUI.pinableFrames.add(new Tps.TpsRUN());
        OnscreenGUI.pinableFrames.add(new Hunger.HungerRUN());
        OnscreenGUI.pinableFrames.add(new Potions.PotionsRUN());
        OnscreenGUI.pinableFrames.add(new Enemypos.EnemyposRUN());
        OnscreenGUI.pinableFrames.add(new ServerResponce.ServerResponceRUN());
    }
    
    public void func_73876_c() {
        super.func_73876_c();
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        if (this.Onscreen.isWithinBounds(mouseX, mouseY)) {
            this.Onscreen.handleScrollinput();
        }
        this.Onscreen.updatePosition(mouseX, mouseY);
        this.Onscreen.renderFrame();
        if (this.Onscreen.isOpen()) {
            for (final Component comp : this.Onscreen.getComponents()) {
                comp.RenderTooltip();
                try {
                    comp.updateComponent(mouseX, mouseY);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (final PinableFrame pinableFrame : OnscreenGUI.pinableFrames) {
            if (this.field_146297_k.field_71462_r instanceof OnscreenGUI) {
                pinableFrame.renderFrame();
                pinableFrame.Ongui();
            }
            pinableFrame.renderFrameText();
            pinableFrame.updatePosition(mouseX, mouseY);
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.Onscreen.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
            this.Onscreen.setDrag(true);
            this.Onscreen.dragX = mouseX - this.Onscreen.getX();
            this.Onscreen.dragY = mouseY - this.Onscreen.getY();
        }
        if (this.Onscreen.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
            this.Onscreen.setOpen(!this.Onscreen.isOpen());
        }
        if (this.Onscreen.isOpen() && !this.Onscreen.getComponents().isEmpty() && this.Onscreen.isWithinBounds(mouseX, mouseY)) {
            for (final Component component : this.Onscreen.getComponents()) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (this.Onscreen.isWithinFooter(mouseX, mouseY) && mouseButton == 0) {
            this.Onscreen.dragScrollstop = mouseY - this.Onscreen.getScrollpos();
            this.Onscreen.setDragBot(true);
        }
        boolean multidrag = false;
        for (final PinableFrame pinableFrame : OnscreenGUI.pinableFrames) {
            if (pinableFrame.isWithinHeader(mouseX, mouseY) && mouseButton == 0 && !multidrag && pinableFrame.isPinned()) {
                pinableFrame.setDrag(true);
                pinableFrame.dragX = mouseX - pinableFrame.getX();
                pinableFrame.dragY = mouseY - pinableFrame.getY();
                multidrag = true;
            }
        }
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int state) {
        this.Onscreen.setDrag(false);
        this.Onscreen.setDragBot(false);
        if (this.Onscreen.isOpen() && !this.Onscreen.getComponents().isEmpty()) {
            for (final Component component : this.Onscreen.getComponents()) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
        for (final PinableFrame pinableFrame : OnscreenGUI.pinableFrames) {
            pinableFrame.setDrag(false);
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) {
        if (this.Onscreen.isOpen() && keyCode != 1 && !this.Onscreen.getComponents().isEmpty()) {
            for (final Component component : this.Onscreen.getComponents()) {
                component.keyTyped(typedChar, keyCode);
            }
        }
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public static void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        for (final PinableFrame frame : OnscreenGUI.pinableFrames) {
            if (frame.isPinned()) {
                frame.onRenderGameOverlay(event);
            }
        }
    }
    
    static {
        pinableFrames = new ArrayList<PinableFrame>();
    }
}
