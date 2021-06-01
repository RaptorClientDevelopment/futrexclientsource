package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.client.gui.*;
import Method.Client.utils.Screens.Custom.Xray.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.Patcher.Events.*;
import java.util.*;

public class Xray extends Module
{
    public static ArrayList<String> blockNames;
    Setting Gui;
    
    public Xray() {
        super("Xray", 0, Category.RENDER, "Xray");
        this.Gui = Main.setmgr.add(new Setting("Gui", this, Main.Xray));
        new XrayGuiSettings(new Block[] { Blocks.field_150365_q, Blocks.field_150402_ci, Blocks.field_150366_p, Blocks.field_150339_S, Blocks.field_150352_o, Blocks.field_150340_R, Blocks.field_150369_x, Blocks.field_150368_y, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150451_bX, Blocks.field_150482_ag, Blocks.field_150484_ah, Blocks.field_150412_bA, Blocks.field_150475_bE, Blocks.field_150449_bY, Blocks.field_150353_l, Blocks.field_150474_ac, Blocks.field_150427_aO, Blocks.field_150384_bq, Blocks.field_150378_br });
    }
    
    @Override
    public void onEnable() {
        Xray.blockNames = new ArrayList<String>(XrayGuiSettings.getBlockNames());
        MinecraftForge.EVENT_BUS.register((Object)this);
        Xray.mc.field_71438_f.func_72712_a();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        Xray.mc.field_71474_y.field_74333_Y = 16.0f;
    }
    
    @Override
    public void SetOpaqueCubeEvent(final SetOpaqueCubeEvent event) {
        event.setCanceled(true);
    }
    
    @Override
    public void onGetAmbientOcclusionLightValue(final GetAmbientOcclusionLightValueEvent event) {
        event.setLightValue(1.0f);
    }
    
    @Override
    public void onShouldSideBeRendered(final ShouldSideBeRenderedEvent event) {
        event.setRendered(this.isVisible(event.getState().func_177230_c()));
    }
    
    @Override
    public void onRenderBlockModel(final RenderBlockModelEvent event) {
        if (!this.isVisible(event.getState().func_177230_c())) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onRenderTileEntity(final RenderTileEntityEvent event) {
        if (!this.isVisible(event.getTileEntity().func_145838_q())) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Xray.mc.field_71438_f.func_72712_a();
    }
    
    private boolean isVisible(final Block block) {
        final String name = getName(block);
        final int index = Collections.binarySearch(Xray.blockNames, name);
        return index >= 0;
    }
    
    public static String getName(final Block block) {
        return "" + Block.field_149771_c.func_177774_c((Object)block);
    }
}
