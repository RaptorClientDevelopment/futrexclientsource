package Method.Client.clickgui.component.components.sub;

import Method.Client.clickgui.component.*;
import Method.Client.managers.*;
import Method.Client.clickgui.component.components.*;
import org.lwjgl.opengl.*;
import Method.Client.module.misc.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.system.*;

public class Guibutton extends Component
{
    private boolean hovered;
    private final Setting op;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    private GuiScreen screen;
    
    public Guibutton(final Setting option, final Button button, final int offset, final GuiScreen screen) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.screen = screen;
    }
    
    @Override
    public void renderComponent() {
        GL11.glEnable(3042);
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? GuiModule.Hover.getcolor() : GuiModule.innercolor.getcolor());
        Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -1508830959);
        GL11.glPushMatrix();
        Button.fontSelectButton(this.op.getName(), this.parent.parent.getX() + 35, this.parent.parent.getY() + this.offset + 2, -1);
        GlStateManager.func_179121_F();
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public String getName() {
        return this.op.getName();
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
            Wrapper.mc.func_147108_a(this.screen);
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + this.parent.parent.getBarHeight();
    }
}
