package Method.Client.clickgui.component;

import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class Component
{
    public static FontRenderer FontRend;
    
    public void renderComponent() {
    }
    
    public void updateComponent(final int mouseX, final int mouseY) throws IOException {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }
    
    public void RenderTooltip() {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public int getParentHeight() {
        return 0;
    }
    
    public void keyTyped(final char typedChar, final int key) {
    }
    
    public void setOff(final int newOff) {
    }
    
    public int getHeight() {
        return 0;
    }
    
    public int gety() {
        return 0;
    }
    
    public String getName() {
        return "";
    }
    
    public String getCategory() {
        return null;
    }
    
    static {
        Component.FontRend = new FontRenderer(Minecraft.func_71410_x().field_71474_y, new ResourceLocation("textures/font/ascii_sga.png"), Minecraft.func_71410_x().field_71446_o, true);
    }
}
