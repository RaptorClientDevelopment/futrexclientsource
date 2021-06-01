package Method.Client.module.render;

import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import Method.Client.utils.visual.*;
import Method.Client.utils.*;
import Method.Client.managers.*;
import net.minecraft.client.shader.*;

public class Tracers extends Module
{
    Setting Player;
    Setting invis;
    Setting Box;
    Setting BoxMode;
    Setting BoxWidth;
    Setting FriendColor;
    Setting PlayerColor;
    Setting inviscolor;
    Setting LineWidth;
    Setting Distance;
    Setting Mode;
    Setting Glow;
    Setting GlowWidth;
    
    public Tracers() {
        super("Tracers", 0, Category.RENDER, "Tracers");
    }
    
    @Override
    public void setup() {
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Head");
        options.add("Body");
        options.add("Feet");
        Main.setmgr.add(this.Mode = new Setting("Player mode", this, "Head", options));
        Main.setmgr.add(this.Player = new Setting("Player", this, true));
        Main.setmgr.add(this.invis = new Setting("Invis", this, false));
        Main.setmgr.add(this.Glow = new Setting("Glow", this, true));
        Main.setmgr.add(this.GlowWidth = new Setting("GlowWidth", this, 0.0, 0.0, 1.0, false));
        Main.setmgr.add(this.Box = new Setting("Box", this, false));
        Main.setmgr.add(this.BoxMode = new Setting("Box", this, "Outline", this.BlockEspOptions()));
        Main.setmgr.add(this.BoxWidth = new Setting("BoxWidth", this, 1.0, 0.0, 3.0, false));
        Main.setmgr.add(this.LineWidth = new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        Main.setmgr.add(this.Distance = new Setting("Distance", this, 500.0, 0.0, 500.0, true));
        Main.setmgr.add(this.FriendColor = new Setting("FriendColor", this, 0.0, 1.0, 1.0, 1.0, this.Player, 7));
        Main.setmgr.add(this.PlayerColor = new Setting("PlayerColor", this, 0.44, 1.0, 1.0, 1.0, this.Player, 8));
        Main.setmgr.add(this.inviscolor = new Setting("Inviscolor", this, 0.88, 1.0, 1.0, 1.0, this.invis, 9));
    }
    
    @Override
    public void onDisable() {
        for (final Object object : Tracers.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer entity = (EntityPlayer)object;
                if (!entity.func_184202_aL()) {
                    continue;
                }
                entity.func_184195_f(false);
            }
        }
        super.onDisable();
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Object object : Tracers.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityLivingBase && !(object instanceof EntityArmorStand)) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                if (Tracers.mc.field_71439_g.func_70032_d((Entity)entity) >= this.Distance.getValDouble()) {
                    continue;
                }
                this.render(entity);
            }
        }
        super.onRenderWorldLast(event);
    }
    
    void render(final EntityLivingBase entity) {
        if (entity == Tracers.mc.field_71439_g) {
            return;
        }
        if (entity instanceof EntityPlayer && this.Player.getValBoolean()) {
            int y = 0;
            if (this.Mode.getValString().equalsIgnoreCase("Head")) {
                ++y;
            }
            if (this.Mode.getValString().equalsIgnoreCase("Feet")) {
                --y;
            }
            if (entity.field_70737_aN > 0) {
                RenderUtils.RenderBlock("Tracer", RenderUtils.Boundingbb((Entity)entity, 0.0, y, 0.0, 0.0, y, 0.0), ColorUtils.rainbow().getRGB(), this.LineWidth.getValDouble());
                if (this.Box.getValBoolean()) {
                    RenderUtils.RenderBlock(this.BoxMode.getValString(), RenderUtils.Boundingbb((Entity)entity, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), ColorUtils.rainbow().getRGB(), this.LineWidth.getValDouble());
                }
                return;
            }
            final EntityPlayer player = (EntityPlayer)entity;
            final String ID = Utils.getPlayerName(player);
            if (this.Glow.getValBoolean()) {
                player.func_184195_f(true);
                final ShaderUniform outlineRadius;
                Tracers.mc.field_71438_f.field_174991_A.field_148031_d.forEach(shader -> {
                    outlineRadius = shader.func_148043_c().func_147991_a("Radius");
                    if (outlineRadius != null) {
                        outlineRadius.func_148090_a((float)this.GlowWidth.getValDouble());
                    }
                    return;
                });
            }
            if (FriendManager.friendsList.contains(ID)) {
                if (this.Box.getValBoolean()) {
                    RenderUtils.RenderBlock(this.BoxMode.getValString(), RenderUtils.Boundingbb((Entity)entity, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), this.FriendColor.getcolor(), this.LineWidth.getValDouble());
                }
                RenderUtils.RenderBlock("Tracer", RenderUtils.Boundingbb((Entity)entity, 0.0, y, 0.0, 0.0, y, 0.0), this.FriendColor.getcolor(), this.LineWidth.getValDouble());
                return;
            }
            RenderUtils.RenderBlock("Tracer", RenderUtils.Boundingbb((Entity)entity, 0.0, y, 0.0, 0.0, y, 0.0), this.PlayerColor.getcolor(), this.LineWidth.getValDouble());
            if (this.Box.getValBoolean()) {
                RenderUtils.RenderBlock(this.BoxMode.getValString(), RenderUtils.Boundingbb((Entity)entity, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), this.PlayerColor.getcolor(), this.LineWidth.getValDouble());
            }
            if (entity.func_82150_aj() && this.invis.getValBoolean()) {
                RenderUtils.RenderBlock("Tracer", RenderUtils.Boundingbb((Entity)entity, 0.0, y, 0.0, 0.0, y, 0.0), this.inviscolor.getcolor(), this.LineWidth.getValDouble());
                if (this.Box.getValBoolean()) {
                    RenderUtils.RenderBlock(this.BoxMode.getValString(), RenderUtils.Boundingbb((Entity)entity, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), this.inviscolor.getcolor(), this.LineWidth.getValDouble());
                }
            }
        }
    }
}
