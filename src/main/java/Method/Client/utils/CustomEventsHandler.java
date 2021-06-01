package Method.Client.utils;

import Method.Client.utils.visual.*;
import Method.Client.utils.system.*;
import Method.Client.module.*;
import net.minecraftforge.fml.common.eventhandler.*;
import Method.Client.utils.Patcher.Events.*;

public class CustomEventsHandler
{
    private void cow(final String Evnt, final RuntimeException ex) {
        ex.printStackTrace();
        ChatUtils.error("RuntimeException: " + Evnt);
        ChatUtils.error(ex.toString());
        Wrapper.INSTANCE.copy(ex.toString());
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSetOpaqueCube(final SetOpaqueCubeEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.SetOpaqueCubeEvent(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void EventBookPage(final EventBookPage event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.EventBookPage(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void PreMotionEvent(final PreMotionEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.PreMotionEvent(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void PostMotionEvent(final PostMotionEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.PostMotionEvent(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onPlayerMove(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerJump(final EntityPlayerJumpEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onPlayerJump(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void DamageBlock(final PlayerDamageBlockEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.DamageBlock(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGetLiquidCollisionBox(final GetLiquidCollisionBoxEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onGetLiquidCollisionBox(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGetAmbientOcclusionLightValue(final GetAmbientOcclusionLightValueEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onGetAmbientOcclusionLightValue(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onShouldSideBeRendered(final ShouldSideBeRenderedEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onShouldSideBeRendered(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void Liquidvisitor(final EventCanCollide event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.EventCanCollide(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderBlockModel(final RenderBlockModelEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onRenderBlockModel(event);
        }
        catch (RuntimeException ex) {}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderTileEntity(final RenderTileEntityEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onRenderTileEntity(event);
        }
        catch (RuntimeException ex) {
            this.cow("onRenderTileEntity", ex);
        }
    }
}
