package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.init.*;
import Method.Client.utils.visual.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;

public class BreakEsp extends Module
{
    Setting ignoreSelf;
    Setting onlyObsi;
    Setting fade;
    Setting Mode;
    Setting LineWidth;
    Setting OverlayColor;
    Setting Distance;
    
    public BreakEsp() {
        super("BreakEsp", 0, Category.RENDER, "BreakEsp");
        this.ignoreSelf = Main.setmgr.add(new Setting("ignoreSelf", this, false));
        this.onlyObsi = Main.setmgr.add(new Setting("onlyObsi", this, false));
        this.fade = Main.setmgr.add(new Setting("fade", this, true));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.56));
        this.Distance = Main.setmgr.add(new Setting("Distance", this, 10.0, 0.0, 50.0, false));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        AxisAlignedBB pos;
        BreakEsp.mc.field_71438_f.field_72738_E.forEach((var1x, destroyBlockProgress) -> {
            if (destroyBlockProgress != null && this.Distance.getValDouble() * 5.0 > BreakEsp.mc.field_71439_g.func_174831_c(destroyBlockProgress.func_180246_b()) && (!this.ignoreSelf.getValBoolean() || BreakEsp.mc.field_71441_e.func_73045_a((int)var1x) != BreakEsp.mc.field_71439_g) && (!this.onlyObsi.getValBoolean() || BreakEsp.mc.field_71441_e.func_180495_p(destroyBlockProgress.func_180246_b()).func_177230_c() == Blocks.field_150343_Z)) {
                pos = RenderUtils.Standardbb(destroyBlockProgress.func_180246_b());
                if (this.fade.getValBoolean()) {
                    pos = pos.func_186664_h((3.0 - destroyBlockProgress.func_73106_e() / 2.6666666666666665) / 9.0);
                }
                RenderUtils.RenderBlock(this.Mode.getValString(), pos, this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
            }
            return;
        });
        super.onRenderWorldLast(event);
    }
}
