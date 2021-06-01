package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.block.*;
import Method.Client.utils.visual.*;
import net.minecraft.util.math.*;

public class BlockOverlay extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting LineWidth;
    
    public BlockOverlay() {
        super("BlockOverlay", 0, Category.RENDER, "BlockOverlay");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.62));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (BlockOverlay.mc.field_71476_x == null) {
            return;
        }
        if (Block.func_149682_b(BlockOverlay.mc.field_71441_e.func_180495_p(BlockOverlay.mc.field_71476_x.func_178782_a()).func_177230_c()) == 0) {
            return;
        }
        if (BlockOverlay.mc.field_71476_x.field_72313_a == RayTraceResult.Type.BLOCK) {
            final BlockPos blockPos = BlockOverlay.mc.field_71476_x.func_178782_a();
            RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(blockPos), this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
        }
        super.onRenderWorldLast(event);
    }
}
