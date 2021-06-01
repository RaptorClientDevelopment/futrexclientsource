package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;

public class ItemESP extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting Glow;
    Setting LifeSpan;
    Setting Nametag;
    Setting LineWidth;
    Setting Scale;
    
    public ItemESP() {
        super("ItemESP", 0, Category.RENDER, "Droped item glow");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 1.0));
        this.Mode = Main.setmgr.add(new Setting("Drop Mode", this, "Outline", this.BlockEspOptions()));
        this.Glow = Main.setmgr.add(new Setting("Glow", this, false));
        this.LifeSpan = Main.setmgr.add(new Setting("Thrower", this, true));
        this.Nametag = Main.setmgr.add(new Setting("Nametag", this, true));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.Scale = Main.setmgr.add(new Setting("Scale", this, 0.1, 0.0, 1.0, false));
    }
    
    @Override
    public void onDisable() {
        for (final Object object : ItemESP.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityItem) {
                final Entity item = (Entity)object;
                item.func_184195_f(false);
            }
        }
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Object object : ItemESP.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityItem) {
                final EntityItem item = (EntityItem)object;
                final double S = this.Scale.getValDouble();
                RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Boundingbb((Entity)item, -S, 0.2 - S, -S, S, 0.2 + S, S), this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
                if (this.Nametag.getValBoolean()) {
                    RenderUtils.SimpleNametag(item.func_174791_d(), item.func_92059_d().func_82833_r() + " x" + item.func_92059_d().func_190916_E() + (this.LifeSpan.getValBoolean() ? (" " + (item.lifespan - item.field_70292_b) / 20 + " S") : ""));
                }
                item.func_184195_f(this.Glow.getValBoolean());
            }
        }
        super.onRenderWorldLast(event);
    }
}
