package Method.Client.module;

import net.minecraft.client.*;
import java.util.*;
import Method.Client.managers.*;
import Method.Client.utils.system.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.gameevent.*;

public class Module
{
    protected static Minecraft mc;
    protected static Minecraft MC;
    public ArrayList<Module> StoredModules;
    public ArrayList<Setting> StoredSettings;
    public ArrayList<Integer> Keys;
    private boolean toggled;
    public boolean visible;
    private String name;
    private String displayName;
    private final String tooltip;
    private Category category;
    
    public Module(final String name, final int key, final Category category, final String tooltip) {
        this.StoredModules = new ArrayList<Module>();
        this.StoredSettings = new ArrayList<Setting>();
        this.visible = true;
        this.name = name;
        this.tooltip = tooltip;
        (this.Keys = new ArrayList<Integer>()).add(key);
        this.category = category;
        this.toggled = false;
        this.setup();
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return true;
    }
    
    public boolean onDisablePacket(final Object packet, final Connection.Side side) {
        return true;
    }
    
    public void onToggle() {
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        this.onToggle();
        if (this.toggled) {
            if (!ModuleManager.toggledModules.contains(this)) {
                ModuleManager.toggledModules.add(this);
            }
            this.onEnable();
        }
        else {
            this.onDisable();
            ModuleManager.toggledModules.remove(this);
        }
    }
    
    public ArrayList<String> fontoptions() {
        final ArrayList<String> Fontoptions = new ArrayList<String>();
        Fontoptions.add("Arial");
        Fontoptions.add("Impact");
        Fontoptions.add("Times");
        Fontoptions.add("MC");
        return Fontoptions;
    }
    
    public ArrayList<String> BlockEspOptions() {
        final ArrayList<String> BlockOptions = new ArrayList<String>();
        BlockOptions.add("Outline");
        BlockOptions.add("Full");
        BlockOptions.add("Flat");
        BlockOptions.add("FlatOutline");
        BlockOptions.add("Beacon");
        BlockOptions.add("Xspot");
        BlockOptions.add("Tracer");
        BlockOptions.add("Shape");
        BlockOptions.add("None");
        return BlockOptions;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTooltip() {
        return this.tooltip;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public ArrayList<Integer> getKeys() {
        return this.Keys;
    }
    
    public void setKey(final int Key, final boolean Control, final boolean Shift, final boolean Alt) {
        this.Keys = new ArrayList<Integer>();
        if (Control) {
            this.Keys.add(29);
        }
        if (Shift) {
            this.Keys.add(42);
        }
        if (Alt) {
            this.Keys.add(56);
        }
        this.Keys.add(Key);
    }
    
    public void setKeys(String keys) {
        if (keys != null) {
            keys = keys.replaceAll("\\[", "");
            keys = keys.replaceAll("]", "");
            keys = keys.replaceAll(" ", "");
            final String[] tryit = keys.split(",");
            final ArrayList<Integer> key = new ArrayList<Integer>();
            for (final String s : tryit) {
                key.add(Integer.valueOf(s));
            }
            this.Keys = key;
        }
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        this.onToggle();
        if (toggled) {
            if (!ModuleManager.toggledModules.contains(this)) {
                ModuleManager.toggledModules.add(this);
            }
            this.onEnable();
        }
        else {
            this.onDisable();
            ModuleManager.toggledModules.remove(this);
        }
    }
    
    public String getDisplayName() {
        return (this.displayName == null) ? this.name : this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public void setup() {
    }
    
    public ArrayList<Module> getStoredModules() {
        return this.StoredModules;
    }
    
    public void setStoredModules(final ArrayList<Module> storedModules) {
        this.StoredModules = storedModules;
    }
    
    public ArrayList<Setting> getStoredSettings() {
        return this.StoredSettings;
    }
    
    public void setStoredSettings(final ArrayList<Setting> storedSettings) {
        this.StoredSettings = storedSettings;
    }
    
    public void onClientTick(final TickEvent.ClientTickEvent event) {
    }
    
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
    }
    
    public void onItemPickup(final EntityItemPickupEvent event) {
    }
    
    public void onProjectileImpact(final ProjectileImpactEvent event) {
    }
    
    public void onAttackEntity(final AttackEntityEvent event) {
    }
    
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
    }
    
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
    }
    
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
    }
    
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
    }
    
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
    }
    
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
    }
    
    public void SetOpaqueCubeEvent(final SetOpaqueCubeEvent event) {
    }
    
    public void onGetAmbientOcclusionLightValue(final GetAmbientOcclusionLightValueEvent event) {
    }
    
    public void onShouldSideBeRendered(final ShouldSideBeRenderedEvent event) {
    }
    
    public void onRenderBlockModel(final RenderBlockModelEvent event) {
    }
    
    public void onRenderTileEntity(final RenderTileEntityEvent event) {
    }
    
    public void EventCanCollide(final EventCanCollide event) {
    }
    
    public void ItemTooltipEvent(final ItemTooltipEvent event) {
    }
    
    public void postBackgroundTooltipRender(final RenderTooltipEvent.PostBackground event) {
    }
    
    public void postDrawScreen(final GuiScreenEvent.DrawScreenEvent.Post event) {
    }
    
    public void RenderGameOverLayPost(final RenderGameOverlayEvent.Post event) {
    }
    
    public void onPlayerMove(final PlayerMoveEvent event) {
    }
    
    public void onPlayerJump(final EntityPlayerJumpEvent event) {
    }
    
    public void RendergameOverlay(final RenderGameOverlayEvent event) {
    }
    
    public void ChunkeventUNLOAD(final ChunkEvent.Unload event) {
    }
    
    public void ChunkeventLOAD(final ChunkEvent.Load event) {
    }
    
    public void fogColor(final EntityViewRenderEvent.FogColors event) {
    }
    
    public void fogDensity(final EntityViewRenderEvent.FogDensity event) {
    }
    
    public void DamageBlock(final PlayerDamageBlockEvent event) {
    }
    
    public void PlayerSleepInBedEvent(final PlayerSleepInBedEvent event) {
    }
    
    public void GetLiquidCollisionBoxEvent(final GetLiquidCollisionBoxEvent event) {
    }
    
    public void EventBookPage(final EventBookPage event) {
    }
    
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
    }
    
    public void ClientChatEvent(final ClientChatEvent event) {
    }
    
    public void LivingDeathEvent(final LivingDeathEvent event) {
    }
    
    public void WorldEvent(final WorldEvent event) {
    }
    
    public void GuiScreenEvent(final GuiScreenEvent event) {
    }
    
    public void GetCollisionBoxesEvent(final GetCollisionBoxesEvent event) {
    }
    
    public void RendertooltipPre(final RenderTooltipEvent.Pre event) {
    }
    
    public void RenderPlayerEvent(final RenderPlayerEvent event) {
    }
    
    public void RenderBlockOverlayEvent(final RenderBlockOverlayEvent event) {
    }
    
    public void FOVModifier(final EntityViewRenderEvent.FOVModifier event) {
    }
    
    public void DrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
    }
    
    public void PostMotionEvent(final PostMotionEvent event) {
    }
    
    public void PreMotionEvent(final PreMotionEvent event) {
    }
    
    public void BackgroundDrawnEvent(final GuiScreenEvent.BackgroundDrawnEvent event) {
    }
    
    public void RenderTickEvent(final TickEvent.RenderTickEvent event) {
    }
    
    public void onRenderPre(final RenderGameOverlayEvent.Pre event) {
    }
    
    public void setsave() {
    }
    
    public void setdelete() {
    }
    
    public void onWorldLoad(final WorldEvent.Load event) {
    }
    
    public void onWorldUnload(final WorldEvent.Unload event) {
    }
    
    public void GuiOpen(final GuiOpenEvent event) {
    }
    
    public void PlayerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
    }
    
    static {
        Module.mc = Minecraft.func_71410_x();
        Module.MC = Minecraft.func_71410_x();
    }
}
