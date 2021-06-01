package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.Screens.Custom.Esp.*;
import java.util.*;
import net.minecraft.entity.*;
import Method.Client.utils.visual.*;
import net.minecraft.client.shader.*;

public class ESP extends Module
{
    Setting Box;
    Setting Nametag;
    Setting MobColor;
    Setting Mode;
    Setting LineWidth;
    Setting Glow;
    Setting GlowWidth;
    Setting MobSelect;
    
    public ESP() {
        super("ESP", 0, Category.RENDER, "ESP");
        this.Box = Main.setmgr.add(new Setting("Box", this, true));
        this.Nametag = Main.setmgr.add(new Setting("Nametag", this, false));
        this.MobColor = Main.setmgr.add(new Setting("MobColor", this, 0.88, 1.0, 1.0, 1.0));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.Glow = Main.setmgr.add(new Setting("Glow", this, true));
        this.GlowWidth = Main.setmgr.add(new Setting("GlowWidth", this, 0.0, 0.0, 1.0, false));
        this.MobSelect = Main.setmgr.add(new Setting("Gui", this, Main.GuiEsp));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Entity object : ESP.mc.field_71441_e.field_72996_f) {
            for (final String mob : EspGui.Getmobs()) {
                if (Objects.requireNonNull(EntityList.func_192839_a(mob)).getName().equalsIgnoreCase(object.getClass().getName())) {
                    this.render(object);
                }
            }
        }
        super.onRenderWorldLast(event);
    }
    
    void render(final Entity ent) {
        if (ent == ESP.mc.field_71439_g) {
            return;
        }
        if (this.Nametag.getValBoolean()) {
            ent.func_174805_g(true);
        }
        if (this.Glow.getValBoolean()) {
            final ShaderUniform outlineRadius;
            ESP.mc.field_71438_f.field_174991_A.field_148031_d.forEach(shader -> {
                outlineRadius = shader.func_148043_c().func_147991_a("Radius");
                if (outlineRadius != null) {
                    outlineRadius.func_148090_a((float)this.GlowWidth.getValDouble());
                }
                return;
            });
        }
        if (ent instanceof EntityLivingBase) {
            final EntityLivingBase entity = (EntityLivingBase)ent;
            entity.func_184195_f(this.Glow.getValBoolean());
            if (this.Nametag.getValBoolean()) {
                entity.func_96094_a(ent.func_70005_c_());
            }
            if (this.Box.getValBoolean()) {
                RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Boundingbb((Entity)entity, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), this.MobColor.getcolor(), this.LineWidth.getValDouble());
            }
        }
    }
    
    @Override
    public void onDisable() {
        for (final Object object : ESP.mc.field_71441_e.field_72996_f) {
            final Entity entity = (Entity)object;
            if (entity.func_184202_aL()) {
                entity.func_184195_f(false);
            }
        }
        super.onDisable();
    }
}
