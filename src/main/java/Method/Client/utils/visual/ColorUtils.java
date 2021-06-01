package Method.Client.utils.visual;

import java.awt.*;
import net.minecraft.client.renderer.*;

public class ColorUtils
{
    public static Color rainbow() {
        final long offset = 999999999999L;
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        return new Color((int)color);
    }
    
    public static Color wave(final double wave, final double satur, final double bright) {
        float hue = System.nanoTime() / 1.0E10f % 1.0f;
        hue += (float)(wave / 10.0);
        final int color = Color.HSBtoRGB(hue, (float)satur, (float)bright);
        return new Color(color);
    }
    
    public static void glColor(final int color) {
        GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
    }
    
    public static int rgbToInt(final int r, final int g, final int b, final int a) {
        return r << 16 | g << 8 | b | a << 24;
    }
    
    public static int color(final int r, final int g, final int b, final int a) {
        return new Color(r, g, b, a).getRGB();
    }
    
    public static int color(final float r, final float g, final float b, final float a) {
        return new Color(r, g, b, a).getRGB();
    }
    
    public static float colorcalc(final int c, final int location) {
        return (c >> location & 0xFF) / 255.0f;
    }
    
    public static int rainbow(final double saturation, final double brightness, final double alpha) {
        final long offset = 999999999999L;
        final float fade = 1.0f;
        final float hue2 = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue2, (float)saturation, (float)brightness)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, (float)alpha).getRGB();
    }
}
