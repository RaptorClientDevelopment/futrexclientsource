package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import Method.Client.utils.visual.*;
import java.util.*;

public class NewChunks extends Module
{
    Setting OverlayColor;
    Setting Mode;
    Setting LineWidth;
    Setting MaxDistance;
    private final List<Vec2f> chunkDataList;
    
    public NewChunks() {
        super("NewChunks", 0, Category.RENDER, "NewChunks");
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 1.0));
        this.Mode = Main.setmgr.add(new Setting("Hole Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.MaxDistance = Main.setmgr.add(new Setting("MaxDistance", this, 1000.0, 0.0, 50000.0, false));
        this.chunkDataList = new ArrayList<Vec2f>();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketChunkData) {
            final SPacketChunkData packet2 = (SPacketChunkData)packet;
            if (!packet2.func_149274_i()) {
                final Vec2f chunk = new Vec2f((float)(packet2.func_149273_e() * 16), (float)(packet2.func_149271_f() * 16));
                if (!this.chunkDataList.contains(chunk)) {
                    this.chunkDataList.add(chunk);
                }
            }
        }
        return true;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final List<Vec2f> found = new ArrayList<Vec2f>();
        for (final Vec2f chunkData : this.chunkDataList) {
            if (chunkData != null) {
                if (NewChunks.mc.field_71439_g.func_70011_f((double)chunkData.field_189982_i, NewChunks.mc.field_71439_g.field_70163_u, (double)chunkData.field_189983_j) > this.MaxDistance.getValDouble()) {
                    found.add(chunkData);
                }
                final double renderPosX = chunkData.field_189982_i - NewChunks.mc.func_175598_ae().field_78730_l;
                final double renderPosY = -NewChunks.mc.func_175598_ae().field_78731_m;
                final double renderPosZ = chunkData.field_189983_j - NewChunks.mc.func_175598_ae().field_78728_n;
                final AxisAlignedBB bb = new AxisAlignedBB(renderPosX, renderPosY, renderPosZ, renderPosX + 16.0, renderPosY + 1.0, renderPosZ + 16.0);
                RenderUtils.RenderBlock(this.Mode.getValString(), bb, this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
            }
        }
        this.chunkDataList.removeAll(found);
        super.onRenderWorldLast(event);
    }
}
