package Method.Client.clickgui.component.components.sub;

import Method.Client.clickgui.component.*;
import Method.Client.managers.*;
import Method.Client.clickgui.component.components.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import java.math.*;

public class ColorPicker extends Component
{
    private final Setting set;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private String localname;
    private int indexer;
    private boolean hovered;
    public double renderWidth;
    
    public ColorPicker(final Setting value, final Button button, final int offset) {
        this.dragging = false;
        this.renderWidth = 0.0;
        this.indexer = 0;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.localname = this.set.getName();
    }
    
    @Override
    public void renderComponent() {
        GL11.glEnable(3042);
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, -1507712478);
        Gui.func_73734_a(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.set.getcolor());
        Gui.func_73734_a((int)(this.parent.parent.getX() + this.renderWidth), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth + 6, this.parent.parent.getY() + this.offset + 12, -1086045116);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(1.0f, 1.0f, 0.5f);
        Button.fontSelectButton(this.localname, this.parent.parent.getX() + 8, this.parent.parent.getY() + this.offset + 2, -1);
        GlStateManager.func_179121_F();
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public String getName() {
        return this.set.getName();
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        final double diff = Math.min(this.parent.parent.getWidth(), Math.max(0, mouseX - this.x));
        final double min = this.set.getMin();
        final double max = this.set.getMax();
        switch (this.indexer) {
            case 0: {
                this.renderWidth = 88.0 * (this.set.getValDouble() - min) / (max - min);
                this.localname = this.set.getName() + " Color";
                break;
            }
            case 1: {
                this.renderWidth = 88.0 * (this.set.getSat() - min) / (max - min);
                this.localname = this.set.getName() + " Saturation";
                break;
            }
            case 2: {
                this.localname = this.set.getName() + " Brightness";
                this.renderWidth = 88.0 * (this.set.getBri() - min) / (max - min);
                break;
            }
            case 3: {
                this.localname = this.set.getName() + " Alpha";
                this.renderWidth = 88.0 * (this.set.getAlpha() - min) / (max - min);
                break;
            }
        }
        if (this.dragging) {
            final double value = diff / 88.0 * (max - min) + min;
            switch (this.indexer) {
                case 0: {
                    this.set.setValDouble((diff == 0.0) ? this.set.getMin() : roundToPlace(value));
                    break;
                }
                case 1: {
                    this.set.setsaturation((float)((diff == 0.0) ? this.set.getMin() : roundToPlace(value)));
                    break;
                }
                case 2: {
                    this.set.setbrightness((float)((diff == 0.0) ? this.set.getMin() : roundToPlace(value)));
                    break;
                }
                case 3: {
                    this.set.setAlpha((float)((diff == 0.0) ? this.set.getMin() : roundToPlace(value)));
                    break;
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.hovered && button == 1 && this.parent.open) {
            this.indexer = ((this.indexer == 3) ? 0 : (this.indexer + 1));
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
