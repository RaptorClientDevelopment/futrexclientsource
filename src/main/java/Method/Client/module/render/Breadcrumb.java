package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class Breadcrumb extends Module
{
    Setting tickdelay;
    Setting Color;
    Setting OtherColor;
    Setting Width;
    Setting BlockSnap;
    Setting OtherPlayers;
    List<Vec3d> doubles;
    List<OtherPos> OtherPos;
    
    public Breadcrumb() {
        super("Breadcrumb", 0, Category.RENDER, "Breadcrumbs");
        this.tickdelay = Main.setmgr.add(new Setting("tickdelay", this, 2.0, 1.0, 20.0, true));
        this.Color = Main.setmgr.add(new Setting("Color", this, 0.4, 0.8, 0.9, 1.0));
        this.OtherColor = Main.setmgr.add(new Setting("OtherColor", this, 0.8, 0.8, 0.9, 1.0));
        this.Width = Main.setmgr.add(new Setting("Width", this, 2.5, 1.0, 5.0, false));
        this.BlockSnap = Main.setmgr.add(new Setting("BlockSnap", this, false));
        this.OtherPlayers = Main.setmgr.add(new Setting("OtherPlayers", this, false));
        this.doubles = new ArrayList<Vec3d>();
        this.OtherPos = new ArrayList<OtherPos>();
    }
    
    @Override
    public void onEnable() {
        this.doubles.clear();
        if (Breadcrumb.mc.field_71439_g.func_70011_f(Breadcrumb.mc.field_71439_g.field_70142_S, Breadcrumb.mc.field_71439_g.field_70137_T, Breadcrumb.mc.field_71439_g.field_70136_U) < 200.0) {
            this.doubles.add(new Vec3d(Breadcrumb.mc.field_71439_g.field_70142_S, Breadcrumb.mc.field_71439_g.field_70137_T, Breadcrumb.mc.field_71439_g.field_70136_U));
            this.doubles.add(new Vec3d((Vec3i)Breadcrumb.mc.field_71439_g.func_180425_c()));
        }
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (Breadcrumb.mc.field_71439_g.field_70173_aa % (int)this.tickdelay.getValDouble() == 0) {
            this.doubles.add(new Vec3d(Breadcrumb.mc.field_71439_g.field_70165_t, Breadcrumb.mc.field_71439_g.field_70163_u, Breadcrumb.mc.field_71439_g.field_70161_v));
            if (this.OtherPlayers.getValBoolean()) {
                for (final Entity entity : Breadcrumb.mc.field_71441_e.field_72996_f) {
                    if (entity instanceof EntityOtherPlayerMP) {
                        final EntityOtherPlayerMP otherPlayerMP = (EntityOtherPlayerMP)entity;
                        boolean newplayer = true;
                        for (final OtherPos otherPo : this.OtherPos) {
                            if (otherPo.getName().equals(otherPlayerMP.func_70005_c_())) {
                                otherPo.doubles.add(new Vec3d(otherPlayerMP.field_70165_t, otherPlayerMP.field_70163_u, otherPlayerMP.field_70161_v));
                                newplayer = false;
                            }
                        }
                        if (!newplayer) {
                            continue;
                        }
                        final OtherPos NewPla = new OtherPos(otherPlayerMP.func_70005_c_());
                        this.OtherPos.add(NewPla);
                        NewPla.doubles.add(new Vec3d(otherPlayerMP.field_70165_t, otherPlayerMP.field_70163_u, otherPlayerMP.field_70161_v));
                    }
                }
            }
        }
        RenderUtils.RenderLine(this.doubles, this.Color.getcolor(), this.Width.getValDouble(), this.BlockSnap.getValBoolean());
        if (this.OtherPlayers.getValBoolean()) {
            for (final OtherPos otherPo2 : this.OtherPos) {
                RenderUtils.RenderLine(otherPo2.doubles, this.OtherColor.getcolor(), this.Width.getValDouble(), this.BlockSnap.getValBoolean());
            }
        }
    }
    
    static class OtherPos
    {
        private final String name;
        List<Vec3d> doubles;
        
        public String getName() {
            return this.name;
        }
        
        public OtherPos(final String name) {
            this.doubles = new ArrayList<Vec3d>();
            this.name = name;
        }
    }
}
