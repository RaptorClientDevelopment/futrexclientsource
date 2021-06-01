package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import Method.Client.utils.visual.*;
import net.minecraft.world.chunk.*;

public class ChunkBorder extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting LineWidth;
    Setting Height;
    Setting FollowPlayer;
    
    public ChunkBorder() {
        super("ChunkBorder", 0, Category.RENDER, "ChunkBorder");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.52));
        this.Mode = Main.setmgr.add(new Setting("Chunk Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.Height = Main.setmgr.add(new Setting("Height", this, 0.0, 0.0, 255.0, true));
        this.FollowPlayer = Main.setmgr.add(new Setting("FollowPlayer", this, true));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final Chunk chunk = ChunkBorder.mc.field_71441_e.func_175726_f(ChunkBorder.mc.field_71439_g.func_180425_c());
        final double renderPosX = chunk.field_76635_g * 16 - ChunkBorder.mc.func_175598_ae().field_78730_l;
        final double renderPosY = -ChunkBorder.mc.func_175598_ae().field_78731_m;
        final double renderPosZ = chunk.field_76647_h * 16 - ChunkBorder.mc.func_175598_ae().field_78728_n;
        AxisAlignedBB bb1;
        if (this.FollowPlayer.getValBoolean()) {
            bb1 = new AxisAlignedBB(renderPosX, renderPosY + ChunkBorder.mc.field_71439_g.field_70163_u, renderPosZ, renderPosX + 16.0, renderPosY + 1.0 + ChunkBorder.mc.field_71439_g.field_70163_u, renderPosZ + 16.0);
        }
        else {
            bb1 = new AxisAlignedBB(renderPosX, renderPosY + this.Height.getValDouble(), renderPosZ, renderPosX + 16.0, renderPosY + 1.0 + this.Height.getValDouble(), renderPosZ + 16.0);
        }
        RenderUtils.RenderBlock(this.Mode.getValString(), bb1, this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
    }
}
