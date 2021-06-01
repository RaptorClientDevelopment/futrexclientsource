package Method.Client.clickgui.component.components.sub;

import Method.Client.clickgui.component.*;
import net.minecraft.client.*;
import Method.Client.managers.*;
import Method.Client.clickgui.component.components.*;
import org.lwjgl.opengl.*;
import Method.Client.module.misc.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import Method.Client.clickgui.*;
import Method.Client.module.Onscreen.*;
import org.lwjgl.input.*;
import java.math.*;

public class Slider extends Component
{
    protected Minecraft mc;
    private boolean hovered;
    private final Setting set;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private GuiTextField Inputbox;
    public double renderWidth;
    
    public Slider(final Setting value, final Button button, final int offset) {
        this.mc = Minecraft.func_71410_x();
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        GL11.glEnable(3042);
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? GuiModule.Hover.getcolor() : GuiModule.innercolor.getcolor());
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, this.hovered ? -1084926635 : -1086045116);
        Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, (this.set.getValDouble() > this.set.getMax() || this.set.getValDouble() < this.set.getMin()) ? -1500442351 : -1500409455);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(1.0f, 1.0f, 0.5f);
        Button.fontSelectButton(this.set.getName() + ": " + this.set.getValDouble(), this.parent.parent.getX() + 8, this.parent.parent.getY() + this.offset + 2, -1);
        GlStateManager.func_179121_F();
        if (this.hovered) {
            Button.fontSelectButton(this.set.getTooltip(), 0.0f, (float)(this.mc.field_71440_d / 2.085), this.hovered ? -1499883111 : -1);
        }
        if (this.Inputbox != null) {
            this.Inputbox.func_146194_f();
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void RenderTooltip() {
        if (this.hovered && this.parent.open) {
            Button.fontSelect("Press Ctrl Click For Exact Input.", 0.0f, (float)(this.mc.field_71440_d / 2.085), -1);
        }
    }
    
    @Override
    public String getName() {
        return this.set.getName();
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        if (this.Inputbox != null) {
            this.Inputbox.func_146178_a();
        }
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        final double diff = Math.min(88, Math.max(0, mouseX - this.x));
        final double min = this.set.getMin();
        final double max = this.set.getMax();
        this.renderWidth = 88.0 * (this.set.getValDouble() - min) / (max - min);
        if (this.set.getValDouble() > max || this.set.getValDouble() < min) {
            this.renderWidth = 0.1;
        }
        if (this.dragging && (this.mc.field_71462_r instanceof ClickGui || this.mc.field_71462_r instanceof OnscreenGUI)) {
            if (diff == 0.0) {
                this.set.setValDouble(this.set.getMin());
            }
            else {
                final double newValue = roundToPlace(diff / 88.0 * (max - min) + min);
                this.set.setValDouble(newValue);
            }
        }
        if (this.dragging && !(this.mc.field_71462_r instanceof ClickGui) && !(this.mc.field_71462_r instanceof OnscreenGUI)) {
            this.dragging = false;
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.Inputbox != null) {
            this.Inputbox.func_146195_b(false);
        }
        if (this.hovered && button == 0 && this.parent.open && this.Inputbox == null) {
            this.dragging = true;
        }
        if (this.hovered && button == 0 && this.parent.open && this.Inputbox != null) {
            this.Inputbox.func_146195_b(true);
        }
        if (this.hovered && Keyboard.isKeyDown(29) && this.Inputbox == null) {
            this.Input();
            this.dragging = false;
        }
    }
    
    void Input() {
        (this.Inputbox = new GuiTextField(0, Slider.FontRend, this.x + 5, this.y, 64, 10)).func_146195_b(true);
        this.Inputbox.func_146191_b(String.valueOf(this.set.getValDouble()));
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.Inputbox != null) {
            this.Inputbox.func_146201_a(typedChar, keyCode);
            if (Keyboard.getEventKey() == 28) {
                try {
                    this.set.setValDouble(Double.parseDouble(this.Inputbox.func_146179_b()));
                    this.Inputbox = null;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.dragging = false;
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + this.parent.parent.getBarHeight();
    }
    
    private static double roundToPlace(final double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
