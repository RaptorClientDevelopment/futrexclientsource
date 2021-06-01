package Method.Client.module.Onscreen;

import Method.Client.utils.font.*;
import net.minecraft.client.*;
import java.util.*;
import java.awt.*;
import java.text.*;
import Method.Client.managers.*;
import Method.Client.clickgui.component.*;
import net.minecraftforge.client.event.*;

public class PinableFrame
{
    private final int width;
    public int defaultWidth;
    public int y;
    public int x;
    public int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private boolean isPinned;
    public CFontRenderer FontRender;
    public String title;
    public String[] text;
    protected Minecraft mc;
    
    public static void Toggle(final String s, final boolean b) {
        for (final PinableFrame i : OnscreenGUI.pinableFrames) {
            if (i.title.equals(s)) {
                i.setPinned(b);
                break;
            }
        }
    }
    
    public PinableFrame(final String title, final String[] text, final int y, final int x) {
        this.isPinned = false;
        this.mc = Minecraft.func_71410_x();
        this.FontRender = new CFontRenderer(new Font("Impact", 0, 18), true, false);
        this.title = title;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = 88;
        this.defaultWidth = 88;
        this.barHeight = 13;
        this.dragX = 0;
        this.isDragging = false;
    }
    
    protected DecimalFormat getDecimalFormat(final int i) {
        switch (i) {
            case 0: {
                return new DecimalFormat("0");
            }
            case 1: {
                return new DecimalFormat("0.0");
            }
            case 2: {
                return new DecimalFormat("0.00");
            }
            case 3: {
                return new DecimalFormat("0.000");
            }
            case 4: {
                return new DecimalFormat("0.0000");
            }
            case 5: {
                return new DecimalFormat("0.00000");
            }
            default: {
                return null;
            }
        }
    }
    
    protected void GetSetup(final PinableFrame pinableFrame, final Setting xpos, final Setting ypos, final Setting Frame, final Setting FontSize) {
        pinableFrame.x = (int)xpos.getValDouble();
        pinableFrame.y = (int)ypos.getValDouble();
        if (Frame.getValString().equalsIgnoreCase("false") || Frame.getValString() == null) {
            Frame.setValString("Times");
        }
        pinableFrame.FontRender.setFontS(Frame.getValString(), FontSize.getValDouble(), this.FontRender);
    }
    
    protected void GetInit(final PinableFrame pinableFrame, final Setting xpos, final Setting ypos, final Setting Frame, final Setting FontSize) {
        if (pinableFrame.FontRender.getSize() != (int)FontSize.getValDouble() || !pinableFrame.FontRender.getFont().getName().equalsIgnoreCase(Frame.getValString())) {
            pinableFrame.FontRender.setFontS(Frame.getValString(), FontSize.getValDouble(), pinableFrame.FontRender);
        }
        if (!pinableFrame.getDrag()) {
            pinableFrame.x = (int)xpos.getValDouble();
            pinableFrame.y = (int)ypos.getValDouble();
        }
        else {
            xpos.setValDouble(pinableFrame.x);
            ypos.setValDouble(pinableFrame.y);
        }
    }
    
    protected void fontSelect(final Setting s, final String name, final float v, final float v1, final int color, final boolean shadow) {
        if (s.getValString().equalsIgnoreCase("MC")) {
            if (shadow) {
                this.mc.field_71466_p.func_175063_a(name, (float)(int)v, (float)(int)v1, color);
            }
            if (!shadow) {
                this.mc.field_71466_p.func_78276_b(name, (int)v, (int)v1, color);
            }
        }
        else {
            if (shadow) {
                this.FontRender.drawStringWithShadow(name, v, v1, color);
            }
            if (!shadow) {
                this.FontRender.String(name, (int)v, (int)v1, color);
            }
        }
    }
    
    protected int widthcal(final Setting s, final String name) {
        if (s.getValString().equalsIgnoreCase("MC")) {
            return this.mc.field_71466_p.func_78256_a(name);
        }
        return this.FontRender.getStringWidth(name);
    }
    
    protected int heightcal(final Setting s, final String name) {
        if (s.getValString().equalsIgnoreCase("MC")) {
            return 10;
        }
        return this.FontRender.getStringHeight(name);
    }
    
    public void renderFrame() {
        if (this.isPinned) {
            Component.FontRend.func_175063_a(this.title, (float)(this.x + 3), (float)(this.y + 3), -1);
        }
    }
    
    public void Ongui() {
    }
    
    public void renderFrameText() {
        int yCount = this.y + this.barHeight + 3;
        for (final String line : this.text) {
            Component.FontRend.func_78276_b(line, this.x + 3, yCount, -1);
            yCount += 10;
        }
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging && this.isPinned) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
    
    public boolean isWithinExtendRange(final int x, final int y) {
        return x <= this.x + this.width - 2 && x >= this.x + this.width - 2 - 8 && y >= this.y + 2 && y <= this.y + 10;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public Boolean getDrag() {
        return this.isDragging;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public boolean isPinned() {
        return this.isPinned;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setPinned(final boolean pinned) {
        this.isPinned = pinned;
    }
    
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
    }
    
    public void setup() {
    }
}
