package Method.Client.clickgui.component;

import net.minecraft.client.*;
import Method.Client.module.*;
import Method.Client.clickgui.component.components.*;
import java.util.*;
import Method.Client.utils.font.*;
import Method.Client.clickgui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import Method.Client.module.misc.*;
import org.lwjgl.input.*;

public class Frame
{
    public ArrayList<Component> components;
    public Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int Bottomy;
    private int scrollpos;
    private int CollapseRate;
    private final int barHeight;
    private boolean isDragging;
    private boolean isDraggingBot;
    public int dragX;
    public int dragScrollstop;
    public int dragY;
    protected Minecraft mc;
    int scroll;
    boolean wasopen;
    public static CFontRenderer FrameFont;
    
    public Frame(final Category cat) {
        this.mc = Minecraft.func_71410_x();
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 20;
        this.barHeight = 12;
        this.CollapseRate = 0;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        this.isDraggingBot = false;
        this.wasopen = false;
        int tY = this.barHeight;
        for (final Module mod : ModuleManager.getModulesInCategory(this.category)) {
            final Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += this.barHeight;
        }
        this.Bottomy = tY;
    }
    
    public static void updateFont() {
        if (GuiModule.Frame.getValString().equalsIgnoreCase("Arial")) {
            Frame.FrameFont = CFont.afontRenderer26;
        }
        if (GuiModule.Frame.getValString().equalsIgnoreCase("Times")) {
            Frame.FrameFont = CFont.tfontRenderer26;
        }
        if (GuiModule.Frame.getValString().equalsIgnoreCase("Impact")) {
            Frame.FrameFont = CFont.ifontRenderer26;
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void updateRefresh() {
        int tY = this.barHeight;
        this.components.clear();
        for (final Module mod : ModuleManager.getModulesInCategory(this.category)) {
            final Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += this.barHeight;
        }
        this.Bottomy = tY;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setBottomy(final int setby) {
        this.Bottomy = setby;
    }
    
    public void setScrollpos(final int NewY) {
        this.scrollpos = NewY;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public void setDragBot(final boolean drag) {
        this.isDraggingBot = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
        if (open) {
            this.wasopen = true;
        }
    }
    
    public void renderFrame() {
        if (this.mc.field_71462_r instanceof ClickGui && this.category.name().equalsIgnoreCase("Onscreen")) {
            this.setX((int)(this.mc.field_71443_c / 5.4 - this.width));
            this.setY(2);
            this.setWidth(22);
        }
        GL11.glEnable(3042);
        Gui.func_73734_a(this.x, this.y, this.x + this.width, this.y + this.barHeight, GuiModule.Framecolor.getcolor());
        if (this.open && !this.category.name().equalsIgnoreCase("Profiles") && this.CollapseRate < 5) {
            Gui.func_73734_a(this.x, this.Bottomy + this.scrollpos + this.y, this.x + this.width, this.Bottomy + this.barHeight + this.scrollpos + this.y, GuiModule.Framecolor.getcolor());
        }
        GlStateManager.func_179094_E();
        if (!this.category.name().equalsIgnoreCase("Onscreen")) {
            this.fontSelect(this.category.name(), this.x + 3, (float)(this.y + 2.5f - 1.5), -1);
            this.fontSelect(this.open ? "-" : "+", this.x + this.width - 9, (float)(this.y + 2.5f - 1.5), -1);
        }
        GlStateManager.func_179121_F();
        this.mc.func_110434_K().func_110577_a(new ResourceLocation("futurex", this.getName().toLowerCase() + ".png"));
        Gui.func_146110_a(this.x + this.width - this.barHeight - 6, this.y + 1, 0.0f, 0.0f, this.barHeight, this.barHeight, (float)this.barHeight, (float)this.barHeight);
        if ((this.open || (this.wasopen && GuiModule.Animations.getValBoolean())) && !this.components.isEmpty()) {
            if (!this.open) {
                if (this.CollapseRate + this.barHeight + this.barHeight >= (this.Bottomy + this.scrollpos - this.barHeight) * 2) {
                    this.wasopen = false;
                    return;
                }
                this.CollapseRate += (int)ModSettings.GuiSpeed.getValDouble();
            }
            else if (this.CollapseRate > 0) {
                this.CollapseRate -= (int)ModSettings.GuiSpeed.getValDouble();
            }
            else {
                this.CollapseRate = 0;
            }
            if (this.category.name().equalsIgnoreCase("Profiles")) {
                this.components.forEach(Component::renderComponent);
            }
            else {
                GL11.glEnable(3089);
                GL11.glScissor(this.x * 2, this.mc.field_71440_d - (this.Bottomy + this.y + this.scrollpos) * 2 + this.CollapseRate, this.width * 2, (this.Bottomy + this.scrollpos - this.barHeight) * 2 - this.CollapseRate);
                this.components.forEach(Component::renderComponent);
                GL11.glDisable(3089);
            }
        }
    }
    
    public void handleScrollinput() {
        int off = this.barHeight;
        for (final Component component : this.components) {
            component.setOff(off - this.barHeight * this.scroll);
            off += component.getHeight();
        }
        boolean Canscoll = false;
        off -= this.barHeight * this.scroll;
        if (off > this.Bottomy + this.scrollpos) {
            Canscoll = true;
        }
        final int wheel = Mouse.getDWheel();
        if (wheel < 0 && Canscoll) {
            ++this.scroll;
        }
        else if (wheel > 0) {
            --this.scroll;
        }
        if (this.scroll < 0) {
            this.scroll = 0;
        }
    }
    
    public void fontSelect(final String name, final float v, final float v1, final int i) {
        if (GuiModule.Frame.getValString().equalsIgnoreCase("MC")) {
            this.mc.field_71466_p.func_175063_a(name, (float)(int)v, (float)(int)v1, i);
        }
        else {
            Frame.FrameFont.drawStringWithShadow(name, v, v1, i);
        }
    }
    
    public void refresh() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public String getName() {
        return this.category.name();
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getScrollpos() {
        return this.scrollpos;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getBarHeight() {
        return this.barHeight;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            if (mouseX - this.dragX + this.width <= this.mc.field_71443_c / 2 && mouseX - this.dragX >= 0) {
                this.setX(mouseX - this.dragX);
            }
            if (mouseY - this.dragY + this.barHeight <= this.mc.field_71440_d / 2) {
                this.setY(mouseY - this.dragY);
            }
        }
        int off = this.barHeight;
        for (final Component component : this.components) {
            off += component.getHeight();
        }
        if (this.isDraggingBot) {
            if (mouseY + 6 <= off + this.y + this.barHeight) {
                this.setScrollpos(mouseY - this.dragScrollstop);
            }
            this.handleScrollinput();
        }
        if (this.scrollpos + this.Bottomy > off) {
            this.setScrollpos(this.scrollpos - this.barHeight);
        }
        if ((this.scrollpos < 0 && this.scrollpos > -12) || (this.scrollpos > 0 && this.scrollpos < 12)) {
            this.scrollpos = 0;
        }
        off -= this.barHeight * this.scroll;
        if (off <= this.Bottomy + this.scrollpos && this.scroll > 0) {
            --this.scroll;
        }
        if (this.scrollpos < -this.Bottomy + this.barHeight) {
            this.scrollpos = -this.Bottomy + this.barHeight;
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
    
    public boolean isWithinFooter(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.Bottomy + this.scrollpos + this.y && y <= this.Bottomy + this.scrollpos + this.barHeight + this.y;
    }
    
    public boolean isWithinBounds(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.Bottomy + ((this.category != Category.PROFILES) ? this.scrollpos : 1000);
    }
}
