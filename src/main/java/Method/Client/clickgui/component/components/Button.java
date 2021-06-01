package Method.Client.clickgui.component.components;

import Method.Client.module.*;
import Method.Client.clickgui.component.*;
import net.minecraft.client.*;
import Method.Client.*;
import Method.Client.managers.*;
import Method.Client.clickgui.component.components.sub.*;
import Method.Client.module.misc.*;
import Method.Client.utils.font.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import java.io.*;
import java.util.*;

public class Button extends Component
{
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private boolean saveisHovered;
    private boolean deleteisHovered;
    private double Animate;
    private final ArrayList<Component> subcomponents;
    private final ArrayList<Component> Inviscomponents;
    public static CFontRenderer ButtonFont;
    public static CFontRenderer SubcomponentFont;
    public boolean open;
    public int opentiming;
    protected Minecraft mc;
    
    public Button(final Module mod, final Frame parent, final int offset) {
        this.mc = Minecraft.func_71410_x();
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.saveisHovered = false;
        this.deleteisHovered = false;
        this.subcomponents = new ArrayList<Component>();
        this.Inviscomponents = new ArrayList<Component>();
        this.open = false;
        int opY = offset + 12;
        if (Main.setmgr.getSettingsByMod(mod) != null) {
            for (final Setting s : Main.setmgr.getSettingsByMod(mod)) {
                if (s.isCombo()) {
                    this.subcomponents.add(new ModeButton(s, this, opY));
                    opY += 12;
                }
                if (s.isSlider()) {
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }
                if (s.isCheck()) {
                    this.subcomponents.add(new Checkbox(s, this, opY));
                    opY += 12;
                }
                if (s.isColor()) {
                    this.subcomponents.add(new ColorPicker(s, this, opY));
                    opY += 12;
                }
                if (s.isGui()) {
                    this.subcomponents.add(new Guibutton(s, this, opY, s.getScreen()));
                    opY += 12;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }
    
    public static void updateFont() {
        if (GuiModule.Button.getValString().equalsIgnoreCase("Arial")) {
            Button.ButtonFont = CFont.afontRenderer22;
        }
        if (GuiModule.Button.getValString().equalsIgnoreCase("Times")) {
            Button.ButtonFont = CFont.tfontRenderer22;
        }
        if (GuiModule.Button.getValString().equalsIgnoreCase("Impact")) {
            Button.ButtonFont = CFont.ifontRenderer22;
        }
        if (GuiModule.Subcomponents.getValString().equalsIgnoreCase("Arial")) {
            Button.SubcomponentFont = CFont.afontRenderer18;
        }
        if (GuiModule.Subcomponents.getValString().equalsIgnoreCase("Times")) {
            Button.SubcomponentFont = CFont.tfontRenderer18;
        }
        if (GuiModule.Subcomponents.getValString().equalsIgnoreCase("Impact")) {
            Button.SubcomponentFont = CFont.ifontRenderer18;
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }
    
    @Override
    public void renderComponent() {
        GL11.glEnable(3042);
        if (this.getCategory().equalsIgnoreCase("PROFILES")) {
            Gui.func_73734_a(this.parent.getX() + 50, this.parent.getY() + this.offset, this.parent.getX() + 60, this.parent.getY() + 12 + this.offset, 1712497170);
            Gui.func_73734_a(this.parent.getX() + 65, this.parent.getY() + this.offset, this.parent.getX() + 75, this.parent.getY() + 12 + this.offset, 1727543858);
        }
        Gui.func_73734_a(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? GuiModule.Hover.getcolor() : GuiModule.Background.getcolor());
        Gui.func_73734_a(this.parent.getX(), this.parent.getY() + this.offset, (int)(this.parent.getX() + this.Animate), this.parent.getY() + 12 + this.offset, GuiModule.ColorAni.getcolor());
        GlStateManager.func_179094_E();
        if (this.getCategory().equalsIgnoreCase("PROFILES")) {
            fontSelect("S   D", this.parent.getX() + 2 + 49.0f, (float)(this.parent.getY() + this.offset + 2.5), this.mod.isToggled() ? -1498924494 : -1);
        }
        fontSelect(this.mod.getName(), this.parent.getX() + 2, (float)(this.parent.getY() + this.offset + 2.5) - 2.0f, this.mod.isToggled() ? -1498924494 : -1);
        if (this.subcomponents.size() > 2) {
            fontSelect(this.open ? "-" : "+", this.parent.getX() + this.parent.getWidth() - 10, (float)(this.parent.getY() + this.offset + 2.5) - 2.0f, -1);
        }
        GlStateManager.func_179121_F();
        if (this.open && !this.subcomponents.isEmpty()) {
            this.subcomponents.forEach(Component::renderComponent);
            if (GuiModule.border.getValBoolean()) {
                RenderUtils.drawRectOutline(this.parent.getX() + 4, this.parent.getY() + this.offset + 10, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, 1.0, GuiModule.Highlight.getcolor());
            }
        }
    }
    
    @Override
    public void RenderTooltip() {
        if (!this.subcomponents.isEmpty()) {
            this.subcomponents.forEach(Component::RenderTooltip);
        }
        if (this.isHovered) {
            Gui.func_73734_a(0, (int)(this.mc.field_71440_d / 2.085), (int)(this.mod.getTooltip().length() * 5.1), (int)(this.mc.field_71440_d / 2.085) + 10, 1294082594);
            fontSelect(this.mod.getTooltip(), 0.0f, (float)(this.mc.field_71440_d / 2.085), this.mod.isToggled() ? -1499883111 : -1);
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }
    
    @Override
    public int gety() {
        return this.parent.getY() + this.offset;
    }
    
    @Override
    public String getName() {
        return this.mod.getName();
    }
    
    @Override
    public String getCategory() {
        return this.mod.getCategory().toString();
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) throws IOException {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (this.getCategory().equalsIgnoreCase("PROFILES")) {
            this.saveisHovered = this.ProfileisMouseOnButton(mouseX, mouseY, false);
            this.deleteisHovered = this.ProfileisMouseOnButton(mouseX, mouseY, true);
        }
        if (this.isHovered && this.Animate < this.parent.getWidth()) {
            this.Animate += GuiModule.Anispeed.getValDouble();
        }
        if (!this.isHovered && this.Animate > 0.0) {
            this.Animate -= GuiModule.Anispeed.getValDouble();
        }
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    public static void fontSelect(final String name, final float v, final float v1, final int i) {
        if (GuiModule.Button.getValString().equalsIgnoreCase("MC")) {
            Minecraft.func_71410_x().field_71466_p.func_175063_a(name, (float)(int)v, (float)(int)v1, i);
        }
        else {
            Button.ButtonFont.drawStringWithShadow(name, v, v1, i);
        }
    }
    
    public static void fontSelectButton(final String name, final float v, final float v1, final int i) {
        if (GuiModule.Subcomponents.getValString().equalsIgnoreCase("MC")) {
            Minecraft.func_71410_x().field_71466_p.func_175063_a(name, (float)(int)v, (float)(int)v1, i);
        }
        else {
            Button.SubcomponentFont.drawStringWithShadow(name, v, v1, i);
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isHovered && button == 0) {
            if (this.getCategory().equalsIgnoreCase("PROFILES")) {
                if (this.saveisHovered) {
                    this.mod.setsave();
                }
                else {
                    if (this.deleteisHovered) {
                        this.mod.setdelete();
                        return;
                    }
                    this.mod.toggle();
                }
            }
            else {
                this.mod.toggle();
            }
        }
        if (this.isHovered && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
        this.CheckInvis();
    }
    
    private void CheckInvis() {
        if (Main.setmgr.getSettingsByMod(this.mod) != null) {
            for (final Setting s : Main.setmgr.getSettingsByMod(this.mod)) {
                if (s.getDependant() != null) {
                    double index = 0.0;
                    Component Compnt = null;
                    for (final Component com : Objects.requireNonNull(s.getDependant().isCheck() ? (s.getDependant().getValBoolean() ? this.Inviscomponents : this.subcomponents) : (s.getDependant().isCombo() ? (s.getDependant().getValString().equalsIgnoreCase(s.getselected()) ? this.Inviscomponents : this.subcomponents) : null))) {
                        if (com.getName().equalsIgnoreCase(s.getName())) {
                            Compnt = com;
                            index = s.GetIndex();
                            break;
                        }
                    }
                    if (Compnt == null) {
                        continue;
                    }
                    this.Updateinvis(Compnt, index);
                }
            }
        }
    }
    
    private void Updateinvis(final Component compnt, final double index) {
        if (this.subcomponents.contains(compnt)) {
            this.subcomponents.remove(compnt);
        }
        else {
            this.subcomponents.add((int)index, compnt);
        }
        if (this.Inviscomponents.contains(compnt)) {
            this.Inviscomponents.remove(compnt);
        }
        else {
            this.Inviscomponents.add(compnt);
        }
        this.parent.refresh();
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
    
    public boolean ProfileisMouseOnButton(final int x, final int y, final boolean delete) {
        return x > this.parent.getX() + (delete ? 65 : 50) && x < this.parent.getX() + (delete ? 75 : 60) && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
