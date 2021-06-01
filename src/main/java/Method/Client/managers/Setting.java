package Method.Client.managers;

import Method.Client.module.*;
import net.minecraft.client.gui.*;
import java.util.*;
import Method.Client.utils.visual.*;
import java.awt.*;

public class Setting
{
    private String name;
    private final Module parent;
    private final String mode;
    private ArrayList<String> options;
    private GuiScreen screen;
    private Setting Dependant;
    private boolean onlyint;
    private double index;
    private String selected;
    private String sval;
    private boolean bval;
    private double dval;
    private double min;
    private double max;
    private double saval;
    private double brval;
    private double Alval;
    
    public Setting(final Setting setting) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = setting.getName();
        this.parent = setting.getParentMod();
        this.mode = setting.getMode();
        this.options = setting.getOptions();
        this.screen = setting.getScreen();
        this.Dependant = setting.getDependant();
        this.onlyint = setting.onlyint;
        this.index = setting.index;
        this.selected = setting.getselected();
        this.dval = setting.getValDouble();
        this.min = setting.getMin();
        this.max = setting.getMax();
        this.saval = setting.getSat();
        this.brval = setting.getBri();
        this.Alval = setting.getAlpha();
        this.Dependant = setting.getDependant();
        this.sval = setting.getValString();
        this.bval = setting.getValBoolean();
    }
    
    public void setall(final Setting inputsetting) {
        this.selected = inputsetting.getselected();
        this.sval = inputsetting.getValString();
        this.dval = inputsetting.getValDouble();
        this.bval = inputsetting.getValBoolean();
        this.min = inputsetting.getMin();
        this.max = inputsetting.getMax();
        this.saval = inputsetting.getSat();
        this.brval = inputsetting.getBri();
        this.Alval = inputsetting.getAlpha();
    }
    
    public Setting(final String name, final Module parent, final String sval, final ArrayList<String> options) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
    }
    
    public Setting(final String name, final Module parent, final String sval, final String... modes) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = new ArrayList<String>(Arrays.asList(modes));
        this.mode = "Combo";
    }
    
    public Setting(final String name, final Module parent, final boolean bval) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
    }
    
    public Setting(final String name, final Module parent, final GuiScreen screen) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.screen = screen;
        this.mode = "Screen";
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
    }
    
    public Setting(final String name, final Module parent, final double HUE, final double Saturation, final double Brightness, final double Alpha) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = HUE;
        this.min = 0.0;
        this.max = 1.0;
        this.saval = Saturation;
        this.brval = Brightness;
        this.Alval = Alpha;
        this.mode = "Color";
    }
    
    public Setting(final String name, final Module parent, final double HUE, final double Saturation, final double Brightness, final double Alpha, final Setting dependant, final int index) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = HUE;
        this.min = 0.0;
        this.max = 1.0;
        this.saval = Saturation;
        this.brval = Brightness;
        this.Alval = Alpha;
        this.Dependant = dependant;
        this.index = index;
        this.mode = "Color";
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint, final Setting Dependant, final int index) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.Dependant = Dependant;
        this.onlyint = onlyint;
        this.index = index;
        this.mode = "Slider";
    }
    
    public Setting(final String name, final Module parent, final boolean bval, final Setting Dependant, final int index) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.Dependant = Dependant;
        this.index = index;
        this.mode = "Check";
    }
    
    public Setting(final String name, final Module parent, final boolean bval, final Setting Dependant, final String selected, final int index) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.selected = selected;
        this.bval = bval;
        this.Dependant = Dependant;
        this.index = index;
        this.mode = "Check";
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint, final Setting Dependant, final String selected, final int index) {
        this.Dependant = null;
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.Dependant = Dependant;
        this.selected = selected;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.index = index;
        this.mode = "Slider";
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String nename) {
        this.name = nename;
    }
    
    public final double GetIndex() {
        return this.index;
    }
    
    public Module getParentMod() {
        return this.parent;
    }
    
    public String getValString() {
        return this.sval;
    }
    
    public void setValString(final String in) {
        this.sval = in;
    }
    
    public ArrayList<String> getOptions() {
        return this.options;
    }
    
    public String getTooltip() {
        return "CTRL + Click For Exact Input";
    }
    
    public boolean getValBoolean() {
        return this.bval;
    }
    
    public void setValBoolean(final boolean in) {
        this.bval = in;
    }
    
    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return this.dval;
    }
    
    public double getSat() {
        return this.saval;
    }
    
    public double getBri() {
        return this.brval;
    }
    
    public double getAlpha() {
        return this.Alval;
    }
    
    public int getcolor() {
        final double saturation = this.saval;
        final double brightness = this.brval;
        if (this.dval == 0.0) {
            return ColorUtils.rainbow(saturation, brightness, this.Alval);
        }
        final int rgba = Color.HSBtoRGB((float)this.dval, (float)saturation, (float)brightness);
        final float red = (rgba >> 16 & 0xFF) / 255.0f;
        final float green = (rgba >> 8 & 0xFF) / 255.0f;
        final float blue = (rgba & 0xFF) / 255.0f;
        final Color c = new Color(red, green, blue, (float)this.Alval);
        return c.getRGB();
    }
    
    public void setValDouble(final double in) {
        this.dval = in;
    }
    
    public void setScreen(final GuiScreen in) {
        this.screen = in;
    }
    
    public void setsaturation(final float in) {
        this.saval = in;
    }
    
    public void setbrightness(final float in) {
        this.brval = in;
    }
    
    public void setAlpha(final float in) {
        this.Alval = in;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public GuiScreen getScreen() {
        return this.screen;
    }
    
    public String getMode() {
        return this.mode;
    }
    
    public Setting getDependant() {
        return this.Dependant;
    }
    
    public String getselected() {
        return this.selected;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }
    
    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }
    
    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }
    
    public boolean isGui() {
        return this.mode.equalsIgnoreCase("Screen");
    }
    
    public boolean isColor() {
        return this.mode.equalsIgnoreCase("Color");
    }
    
    public boolean onlyInt() {
        return this.onlyint;
    }
}
