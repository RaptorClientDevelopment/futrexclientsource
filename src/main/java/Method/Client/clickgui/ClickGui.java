package Method.Client.clickgui;

import net.minecraft.client.gui.*;
import Method.Client.module.*;
import Method.Client.module.Onscreen.*;
import net.minecraft.client.renderer.*;
import Method.Client.clickgui.component.components.*;
import Method.Client.clickgui.component.*;
import Method.Client.utils.visual.*;
import java.io.*;
import Method.Client.managers.*;
import Method.Client.module.command.*;
import java.util.*;
import Method.Client.*;
import Method.Client.clickgui.component.components.sub.*;

public class ClickGui extends GuiScreen
{
    public static ArrayList<Frame> frames;
    private GuiTextField textbox;
    boolean nomultidrag;
    boolean loaded;
    boolean Trycommand;
    
    public ClickGui() {
        this.nomultidrag = false;
        this.Trycommand = false;
        int frameX = 5;
        for (final Category category : Category.values()) {
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            ClickGui.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
        FileManager.LoadMods();
        this.loaded = true;
        for (final PinableFrame me : OnscreenGUI.pinableFrames) {
            me.setup();
        }
    }
    
    public void func_73866_w_() {
        (this.textbox = new GuiTextField(0, this.field_146289_q, (int)(this.field_146297_k.field_71443_c / 5.4), 1, 240, this.field_146297_k.field_71443_c / 100)).func_146195_b(true);
        this.textbox.func_146203_f(999);
        this.textbox.func_146185_a(false);
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        GlStateManager.func_179152_a(1.0f, 1.0f, 0.5f);
        final String parse = this.textbox.func_146179_b();
        this.textbox.func_146194_f();
        Frame.updateFont();
        Button.updateFont();
        for (final Frame frame : ClickGui.frames) {
            if (frame.isWithinBounds(mouseX, mouseY)) {
                frame.handleScrollinput();
            }
            frame.updatePosition(mouseX, mouseY);
            frame.renderFrame();
            if (frame.isOpen()) {
                for (final Component comp : frame.getComponents()) {
                    comp.RenderTooltip();
                    if (comp.getName().toLowerCase().contains(parse.toLowerCase()) && !parse.isEmpty()) {
                        RenderUtils.drawRectOutline(frame.getX(), comp.gety(), frame.getX() + 88, comp.gety() + 12, 1.0, ColorUtils.rainbow().getRGB());
                    }
                    try {
                        comp.updateComponent(mouseX, mouseY);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.func_73733_a((int)(this.field_146297_k.field_71443_c / 5.4), 0, (int)(this.field_146297_k.field_71443_c / 5.4 + 240.0), 14, 865704345, 865704345);
        if (!parse.isEmpty()) {
            int add = 0;
            for (final Command c : CommandManager.commands) {
                if (c.getCommand().toLowerCase().startsWith(parse.toLowerCase())) {
                    Component.FontRend.func_175063_a(c.getSyntax(), (float)(this.field_146297_k.field_71443_c / 5.4), (float)(add + 10), -1);
                    add += 10;
                }
            }
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.nomultidrag) {
            Collections.reverse(ClickGui.frames);
            this.nomultidrag = false;
        }
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty() && frame.isWithinBounds(mouseX, mouseY)) {
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0 && !this.nomultidrag) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
                this.nomultidrag = true;
            }
            if (frame.isWithinFooter(mouseX, mouseY) && mouseButton == 0) {
                frame.dragScrollstop = mouseY - frame.getScrollpos();
                frame.setDragBot(true);
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                if (frame.getName().equalsIgnoreCase("Onscreen")) {
                    this.field_146297_k.func_147108_a((GuiScreen)Main.OnscreenGUI);
                }
                else {
                    frame.setOpen(!frame.isOpen());
                }
            }
        }
        if (mouseButton == 0 && (mouseY >= 14 || mouseX >= this.field_146297_k.field_71443_c / 5.4 + 240.0 || mouseX <= this.field_146297_k.field_71443_c / 5.4 - 5.0)) {
            this.textbox.func_146180_a("");
            this.Trycommand = false;
        }
        if (mouseY < 14 && mouseButton == 0 && mouseX < this.field_146297_k.field_71443_c / 5.4 + 240.0 && mouseX > this.field_146297_k.field_71443_c / 5.4 - 5.0) {
            this.Trycommand = true;
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) {
        if (this.loaded) {
            FileManager.SaveMods();
            FileManager.saveframes();
            FileManager.savePROFILES();
        }
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
        if (!Keybind.PublicBinding) {
            this.textbox.func_146201_a(typedChar, keyCode);
        }
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (typedChar == '\u000f') {
            for (final Command c : CommandManager.commands) {
                final String parse = this.textbox.func_146179_b();
                if (parse.length() > 0 && (c.getCommand().toLowerCase().startsWith(parse.toLowerCase().substring(0, parse.indexOf(32))) || parse.substring(0, parse.indexOf(32)).toLowerCase().startsWith(c.getCommand()))) {
                    this.textbox.func_146180_a(c.getCommand());
                    break;
                }
            }
        }
        if (this.textbox.func_146206_l() && keyCode == 28 && this.Trycommand) {
            CommandManager.getInstance().runCommands(CommandManager.cmdPrefix + this.textbox.func_146179_b());
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
    }
    
    public void func_73876_c() {
        this.textbox.func_146178_a();
        super.func_73876_c();
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : ClickGui.frames) {
            frame.setDrag(false);
            frame.setDragBot(false);
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    static {
        ClickGui.frames = new ArrayList<Frame>();
    }
}
