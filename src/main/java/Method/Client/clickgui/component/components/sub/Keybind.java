package Method.Client.clickgui.component.components.sub;

import Method.Client.clickgui.component.*;
import Method.Client.clickgui.component.components.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import Method.Client.module.misc.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class Keybind extends Component
{
    private boolean hovered;
    private boolean binding;
    public static boolean PublicBinding;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean LControl;
    private boolean LShift;
    private boolean LAlt;
    protected Minecraft mc;
    
    public Keybind(final Button button, final int offset) {
        this.LControl = false;
        this.LShift = false;
        this.LAlt = false;
        this.mc = Minecraft.func_71410_x();
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void renderComponent() {
        GL11.glEnable(3042);
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? GuiModule.Hover.getcolor() : GuiModule.innercolor.getcolor());
        Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -1508830959);
        GL11.glPushMatrix();
        final StringBuilder Keys = new StringBuilder();
        for (final Integer key : this.parent.mod.getKeys()) {
            Keys.append(" ").append(Keyboard.getKeyName((int)key));
        }
        Button.fontSelectButton(this.binding ? "Press a key..." : ("Key: " + (Object)Keys), this.parent.parent.getX() + 7, this.parent.parent.getY() + this.offset + 2, -1);
        GlStateManager.func_179121_F();
    }
    
    @Override
    public void RenderTooltip() {
        if (this.hovered && this.parent.open) {
            Button.fontSelect("Press End To Clear", 0.0f, (float)(this.mc.field_71440_d / 2.085), -1);
        }
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered && button == 0 && this.parent.open) {
            this.binding = !this.binding;
            Keybind.PublicBinding = !Keybind.PublicBinding;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.binding) {
            if (key == 29) {
                this.LControl = !this.LControl;
                return;
            }
            if (key == 42) {
                this.LShift = !this.LShift;
                return;
            }
            if (key == 56) {
                this.LAlt = !this.LAlt;
                return;
            }
            this.parent.mod.setKey(key, this.LControl, this.LShift, this.LAlt);
            this.binding = false;
            this.LAlt = false;
            this.LControl = false;
            this.LShift = false;
            Keybind.PublicBinding = false;
            if (key == 207) {
                this.parent.mod.setKey(key, false, false, false);
            }
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + this.parent.parent.getBarHeight();
    }
}
