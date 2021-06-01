package Method.Client.utils;

import Method.Client.module.*;
import Method.Client.module.misc.*;
import Method.Client.utils.visual.*;
import Method.Client.module.combat.*;
import Method.Client.utils.Screens.*;
import Method.Client.utils.system.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.client.event.*;
import Method.Client.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.SeedViewer.*;
import net.minecraftforge.event.terraingen.*;
import Method.Client.module.render.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.living.*;
import Method.Client.module.Onscreen.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.client.event.*;

public class EventsHandler
{
    public static boolean isInit;
    
    public boolean onPacket(final Object packet, final Connection.Side side) {
        boolean suc = true;
        try {
            for (final Module m : ModuleManager.getEnabledmodules()) {
                if (!EventsHandler.isInit) {
                    suc &= m.onDisablePacket(packet, side);
                }
                if (Wrapper.INSTANCE.world() == null) {
                    continue;
                }
                suc &= m.onPacket(packet, side);
            }
        }
        catch (RuntimeException ex) {
            this.cow("PacketError", ex);
        }
        return suc;
    }
    
    private void cow(final String Evnt, final RuntimeException ex) {
        if (!ModSettings.ShowErrors.getValBoolean()) {
            return;
        }
        ex.printStackTrace();
        ChatUtils.error("RuntimeException: " + Evnt);
        ChatUtils.error(ex.toString());
        Wrapper.INSTANCE.copy(ex.toString());
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
            AntiBot.bots.clear();
            EventsHandler.isInit = false;
            try {
                NewScreen.onClientTick(event);
            }
            catch (RuntimeException ex) {
                this.cow("onClientTick", ex);
            }
            return;
        }
        try {
            if (!EventsHandler.isInit) {
                new Connection(this);
                EventsHandler.isInit = true;
            }
            WorldDownloader.Clienttick();
            ModuleManager.onClientTick(event);
        }
        catch (RuntimeException ex) {
            this.cow("onClientTick", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            final int key = Keyboard.getEventKey();
            if (Keyboard.getEventKeyState()) {
                ModuleManager.onKeyPressed(key);
            }
        }
        catch (RuntimeException ex) {
            this.cow("onKeyInput", ex);
        }
    }
    
    @SubscribeEvent
    public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("futurex")) {
            new Thread(() -> {
                try {
                    while (Wrapper.mc.field_71462_r != Main.ClickGui) {
                        Wrapper.mc.func_147108_a((GuiScreen)Main.ClickGui);
                        Thread.sleep(25L);
                        if (Wrapper.mc.field_71462_r == Main.ClickGui) {
                            break;
                        }
                    }
                    Wrapper.mc.func_147108_a((GuiScreen)Main.ClickGui);
                }
                catch (Exception ex) {}
                return;
            }).start();
            Main.config.syncConfig();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.ClientChatReceivedEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("ClientChatReceivedEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void PlayerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.PlayerRespawnEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("PlayerRespawnEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void BackgroundDrawnEvent(final GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.BackgroundDrawnEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("BackgroundDrawnEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderPre(final RenderGameOverlayEvent.Pre event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onRenderPre(event);
        }
        catch (RuntimeException ex) {
            this.cow("onRenderPre", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GetCollisionBoxesEvent(final GetCollisionBoxesEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.GetCollisionBoxesEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("GetCollisionBoxesEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void DrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.DrawBlockHighlightEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("DrawBlockHighlightEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void ClientChatEvent(final ClientChatEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.ClientChatEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("ClientChatEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingDeath(final LivingDeathEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.LivingDeathEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("LivingDeathEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        try {
            NewScreen.GuiScreenEventInit(event);
        }
        catch (RuntimeException ex) {
            this.cow("GuiScreenEventPre", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void DrawScreenEvent(final GuiScreenEvent.DrawScreenEvent event) {
        try {
            NewScreen.DrawScreenEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("DrawScreenEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void PopulateChunkEvent(final PopulateChunkEvent.Populate event) {
        try {
            WorldLoader.event(event);
        }
        catch (RuntimeException ex) {
            this.cow("PopulateChunkEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void DecorateBiomeEvent(final DecorateBiomeEvent.Decorate event) {
        try {
            WorldLoader.DecorateBiomeEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("DecorateBiomeEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWorldUnload(final WorldEvent.Unload event) {
        try {
            NewScreen.onWorldUnload(event);
            if (!EventsHandler.isInit) {
                return;
            }
            ModuleManager.onWorldUnload(event);
        }
        catch (RuntimeException ex) {
            this.cow("onWorldUnload", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWorldLoad(final WorldEvent.Load event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onWorldLoad(event);
        }
        catch (RuntimeException ex) {
            this.cow("onWorldLoad", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        try {
            NewScreen.GuiScreenEventPost(event);
        }
        catch (RuntimeException ex) {
            this.cow("GuiScreenEventActionPerformedEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GuiScreenEventPre(final GuiScreenEvent.ActionPerformedEvent.Pre event) {
        try {
            NewScreen.GuiScreenEventPre(event);
        }
        catch (RuntimeException ex) {
            this.cow("GuiScreenEventPre", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GuiOpen(final GuiOpenEvent event) {
        try {
            NewScreen.GuiOpen(event);
            if (!EventsHandler.isInit) {
                return;
            }
            ModuleManager.GuiOpen(event);
        }
        catch (RuntimeException ex) {
            this.cow("GuiOpen", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onCameraSetup(event);
        }
        catch (RuntimeException ex) {
            this.cow("onCameraSetup", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderBlockOverlayEvent(final RenderBlockOverlayEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RenderBlockOverlayEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderBlockOverlayEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderPlayerEvent(final RenderPlayerEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RenderPlayerEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderPlayerEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void fogColor(final EntityViewRenderEvent.FogColors event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.fogColor(event);
        }
        catch (RuntimeException ex) {
            this.cow("fogColor", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void fogDensity(final EntityViewRenderEvent.FogDensity event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.fogDensity(event);
        }
        catch (RuntimeException ex) {
            this.cow("fogColor", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void renderNamePlate(final RenderLivingEvent.Specials.Pre e) {
        try {
            if (NameTags.toggled && e.getEntity() instanceof EntityPlayer) {
                e.setCanceled(true);
            }
        }
        catch (RuntimeException ex) {
            this.cow("renderNamePlate", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(final ItemTooltipEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.ItemTooltipEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("onCameraSetup", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RendergameOverlay(final RenderGameOverlayEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RendergameOverlay(event);
        }
        catch (RuntimeException ex) {
            this.cow("onCameraSetup", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void BedSleep(final PlayerSleepInBedEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.PlayerSleepInBedEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("onCameraSetup", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onItemPickup(final EntityItemPickupEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onItemPickup(event);
        }
        catch (RuntimeException ex) {
            this.cow("onItemPickup", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onProjectileImpact(final ProjectileImpactEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onProjectileImpact(event);
        }
        catch (RuntimeException ex) {
            this.cow("ProjectileImpact", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAttackEntity(final AttackEntityEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onAttackEntity(event);
        }
        catch (RuntimeException ex) {
            this.cow("onAttackEntity", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onPlayerTick(event);
        }
        catch (RuntimeException ex) {
            this.cow("onPlayerTick", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void Chunkevent(final ChunkEvent.Unload event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.ChunkeventUNLOAD(event);
        }
        catch (RuntimeException ex) {
            this.cow("ChunkeventUnload", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void Chunkevent(final ChunkEvent.Load event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            WorldDownloader.ChunkeventLOAD(event);
            ModuleManager.ChunkeventLOAD(event);
        }
        catch (RuntimeException ex) {
            this.cow("ChunkeventLOAD", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onLivingUpdate(event);
        }
        catch (RuntimeException ex) {
            this.cow("onLivingUpdate", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null || Wrapper.INSTANCE.mcSettings().field_74319_N) {
            return;
        }
        try {
            ModuleManager.onRenderWorldLast(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderWorldLastEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            OnscreenGUI.onRenderGameOverlay(event);
            ModuleManager.onRenderGameOverlay(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderGameOverlayEvent.Text", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderGameOverLayPost(final RenderGameOverlayEvent.Post event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RenderGameOverLayPost(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderGameOverLayPost", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void FOVModifier(final EntityViewRenderEvent.FOVModifier event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.FOVModifier(event);
        }
        catch (RuntimeException ex) {
            this.cow("FOVModifier", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void PlayerLoggedInEvent(final PlayerEvent.PlayerLoggedInEvent event) {
        try {
            if (event.player != null) {
                final ArrayList<IRecipe> recipes = (ArrayList<IRecipe>)Lists.newArrayList((Iterable)CraftingManager.field_193380_a);
                recipes.removeIf(recipe -> recipe.func_77571_b().func_190926_b());
                recipes.removeIf(recipe -> recipe.func_192400_c().isEmpty());
                event.player.func_192021_a((List)recipes);
            }
        }
        catch (RuntimeException ex) {
            this.cow("PlayerLoggedInEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onLeftClickBlock(event);
        }
        catch (RuntimeException ex) {
            this.cow("onLeftClickBlock", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void WorldEvent(final WorldEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.WorldEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("WorldEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.onRightClickBlock(event);
        }
        catch (RuntimeException ex) {
            this.cow("OnRightClickBlock", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void postBackgroundTooltipRender(final RenderTooltipEvent.PostBackground event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.postBackgroundTooltipRender(event);
        }
        catch (RuntimeException ex) {
            this.cow("postBackgroundTooltipRender", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RendertooltipPre(final RenderTooltipEvent.Pre event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RendertooltipPre(event);
        }
        catch (RuntimeException ex) {
            this.cow("RendertooltipPre", ex);
        }
    }
    
    @SubscribeEvent
    public void RenderTickEvent(final TickEvent.RenderTickEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.RenderTickEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("RenderTickEvent", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void postDrawScreen(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.postDrawScreen(event);
        }
        catch (RuntimeException ex) {
            this.cow("postDrawScreen", ex);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void GuiScreenEvent(final GuiScreenEvent event) {
        if (!EventsHandler.isInit) {
            return;
        }
        try {
            ModuleManager.GuiScreenEvent(event);
        }
        catch (RuntimeException ex) {
            this.cow("GuiScreenEvent", ex);
        }
    }
    
    static {
        EventsHandler.isInit = false;
    }
}
