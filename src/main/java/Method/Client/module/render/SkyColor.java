package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;

public class SkyColor extends Module
{
    Setting Color;
    
    public SkyColor() {
        super("FogColor", 0, Category.RENDER, "FogColor");
        this.Color = Main.setmgr.add(new Setting("Color", this, 0.22, 1.0, 1.0, 1.0));
    }
    
    @Override
    public void fogDensity(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
    
    @Override
    public void fogColor(final EntityViewRenderEvent.FogColors event) {
        SkyColor.mc.field_71460_t.field_175080_Q = ColorUtils.colorcalc(this.Color.getcolor(), 16) / 255.0f;
        SkyColor.mc.field_71460_t.field_175082_R = ColorUtils.colorcalc(this.Color.getcolor(), 8) / 255.0f;
        SkyColor.mc.field_71460_t.field_175081_S = ColorUtils.colorcalc(this.Color.getcolor(), 0) / 255.0f;
        event.setRed(ColorUtils.colorcalc(this.Color.getcolor(), 16));
        event.setGreen(ColorUtils.colorcalc(this.Color.getcolor(), 8));
        event.setBlue(ColorUtils.colorcalc(this.Color.getcolor(), 0));
    }
}
