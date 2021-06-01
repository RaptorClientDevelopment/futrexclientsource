package Method.Client.module;

import Method.Client.module.misc.*;
import Method.Client.module.Onscreen.Display.*;
import Method.Client.module.movement.*;
import Method.Client.module.combat.*;
import Method.Client.module.player.*;
import Method.Client.module.render.*;
import Method.Client.module.Profiles.*;
import org.lwjgl.input.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.client.event.*;
import Method.Client.managers.*;
import java.util.function.*;
import java.util.*;

public class ModuleManager
{
    public static ArrayList<Module> modules;
    public static ArrayList<Module> toggledModules;
    public static ArrayList<Module> FileManagerLoader;
    
    public ModuleManager() {
        addModule(new Antispam());
        addModule(new ChatMutator());
        addModule(new TimeStamp());
        addModule(new AimBot());
        addModule(new AntiBot());
        addModule(new HoleFill());
        addModule(new AutoArmor());
        addModule(new AntiCrystal());
        addModule(new Anchor());
        addModule(new AutoTotem());
        addModule(new AutoTrap());
        addModule(new Burrow());
        addModule(new Criticals());
        addModule(new CrystalAura());
        addModule(new BowMod());
        addModule(new InteractClick());
        addModule(new KillAura());
        addModule(new MoreKnockback());
        addModule(new Regen());
        addModule(new Refill());
        addModule(new Strafe());
        addModule(new Offhand());
        addModule(new SelfTrap());
        addModule(new Surrond());
        addModule(new Trigger());
        addModule(new Webfill());
        addModule(new TotemPop());
        addModule(new Velocity());
        addModule(new DiscordRPCModule());
        addModule(new AntiCrash());
        addModule(new AntiCheat());
        addModule(new AntiHurt());
        addModule(new Antipacket());
        addModule(new AntiHandShake());
        addModule(new AutoClicker());
        addModule(new AutoNametag());
        addModule(new Pickupmod());
        addModule(new Livestock());
        addModule(new CoordsFinder());
        addModule(new EchestBP());
        addModule(new FastSleep());
        addModule(new HitEffects());
        addModule(new Derp());
        addModule(new GuiModule());
        addModule(new InvMove());
        addModule(new ToolTipPlus());
        addModule(new NbtView());
        addModule(new NoSlowdown());
        addModule(new Ghost());
        addModule(new ModSettings());
        addModule(new VersionSpoofer());
        addModule(new PluginsGetter());
        addModule(new GuiPeek());
        addModule(new Shulkerspy());
        addModule(new ServerCrash());
        addModule(new VanishDetector());
        addModule(new QuickCraft());
        addModule(new AntiFall());
        addModule(new AutoSwim());
        addModule(new AutoHold());
        addModule(new Bunnyhop());
        addModule(new Blink());
        addModule(new BoatFly());
        addModule(new ElytraFly());
        addModule(new Entityspeed());
        addModule(new EntityVanish());
        addModule(new FastFall());
        addModule(new Fly());
        addModule(new Glide());
        addModule(new Jump());
        addModule(new Jesus());
        addModule(new Levitate());
        addModule(new LiquidSpeed());
        addModule(new LongJump());
        addModule(new Parkour());
        addModule(new Phase());
        addModule(new SafeWalk());
        addModule(new Sneak());
        addModule(new Speed());
        addModule(new Spider());
        addModule(new Sprint());
        addModule(new Step());
        addModule(new Teleport());
        addModule(new Armor());
        addModule(new Biome());
        addModule(new Coords());
        addModule(new ChunkSize());
        addModule(new Direction());
        addModule(new Durability());
        addModule(new EnabledMods());
        addModule(new Enemypos());
        addModule(new KeyStroke());
        addModule(new Fps());
        addModule(new CombatItems());
        addModule(new Angles());
        addModule(new Blockview());
        addModule(new Hole());
        addModule(new Hunger());
        addModule(new Inventory());
        addModule(new NetherCords());
        addModule(new Ping());
        addModule(new Player());
        addModule(new PlayerCount());
        addModule(new PlayerSpeed());
        addModule(new Potions());
        addModule(new Server());
        addModule(new ServerResponce());
        addModule(new Time());
        addModule(new Tps());
        addModule(new AntiAFK());
        addModule(new AutoFish());
        addModule(new AutoRemount());
        addModule(new AutoRespawn());
        addModule(new Autotool());
        addModule(new BuildHeight());
        addModule(new ChestStealer());
        addModule(new Disconnect());
        addModule(new FastBreak());
        addModule(new FastLadder());
        addModule(new FastPlace());
        addModule(new FireballReturn());
        addModule(new FoodMod());
        addModule(new FreeCam());
        addModule(new Reach());
        addModule(new God());
        addModule(new LiquidInteract());
        addModule(new NoServerChange());
        addModule(new Noswing());
        addModule(new Nowall());
        addModule(new Nuker());
        addModule(new PitchLock());
        addModule(new PortalMod());
        addModule(new Scaffold());
        addModule(new SchematicaNCP());
        addModule(new SkinBlink());
        addModule(new SmallShield());
        addModule(new Timer());
        addModule(new Xcarry());
        addModule(new YawLock());
        addModule(new BlockOverlay());
        addModule(new Breadcrumb());
        addModule(new BossStack());
        addModule(new BreakEsp());
        addModule(new ChestESP());
        addModule(new ChunkBorder());
        addModule(new ESP());
        addModule(new ExtraTab());
        addModule(new ArmorRender());
        addModule(new HoleEsp());
        addModule(new Fullbright());
        addModule(new F3Spoof());
        addModule(new ItemESP());
        addModule(new Visualrange());
        addModule(new MobOwner());
        addModule(new SeedViewer());
        addModule(new MotionBlur());
        addModule(new NewChunks());
        addModule(new NoEffect());
        addModule(new NoRender());
        addModule(new NoBlockLag());
        addModule(new NetherSky());
        addModule(new NameTags());
        addModule(new Search());
        addModule(new SkyColor());
        addModule(new Tracers());
        addModule(new Trail());
        addModule(new Trajectories());
        addModule(new WallHack());
        addModule(new Xray());
        addModule(new Fovmod());
        if (!FileManager.SaveDir.exists()) {
            addModule(new Profiletem("Example"));
            addModule(new Profiletem("Example2"));
        }
        else {
            FileManager.loadPROFILES();
        }
    }
    
    public static void addModule(final Module m) {
        ModuleManager.modules.add(m);
    }
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static ArrayList<Module> getEnabledmodules() {
        return ModuleManager.toggledModules;
    }
    
    public static void onKeyPressed(final int key) {
        for (final Module m : ModuleManager.modules) {
            boolean NeedControl = false;
            boolean NeedShift = false;
            boolean NeedAlt = false;
            int Keydiff = 0;
            for (final Integer mKey : m.getKeys()) {
                if (mKey == 29) {
                    NeedControl = true;
                }
                else if (mKey == 42) {
                    NeedShift = true;
                }
                else if (mKey == 56) {
                    NeedAlt = true;
                }
                else {
                    Keydiff = mKey;
                }
            }
            if (key == Keydiff) {
                if (NeedControl && !Keyboard.isKeyDown(29)) {
                    return;
                }
                if (NeedShift && !Keyboard.isKeyDown(42)) {
                    return;
                }
                if (NeedAlt && !Keyboard.isKeyDown(56)) {
                    return;
                }
                m.toggle();
            }
        }
    }
    
    public static Module getModuleByName(final String name) {
        return ModuleManager.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    public static ArrayList<Module> getModulesInCategory(final Category categoryIn) {
        final ArrayList<Module> mods = new ArrayList<Module>();
        for (final Module m : getSortedHacksabc(false)) {
            if (m.getCategory() == categoryIn) {
                mods.add(m);
            }
        }
        return mods;
    }
    
    public static void onWorldLoad(final WorldEvent.Load event) {
        for (final Module module : ModuleManager.FileManagerLoader) {
            module.setToggled(true);
        }
        ModuleManager.FileManagerLoader.clear();
        for (final Module m : ModuleManager.toggledModules) {
            m.onWorldLoad(event);
        }
    }
    
    public static void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onCameraSetup(event);
        }
    }
    
    public static void onItemPickup(final EntityItemPickupEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onItemPickup(event);
        }
    }
    
    public static void onProjectileImpact(final ProjectileImpactEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onProjectileImpact(event);
        }
    }
    
    public static void onAttackEntity(final AttackEntityEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onAttackEntity(event);
        }
    }
    
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onPlayerTick(event);
        }
    }
    
    public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onLivingUpdate(event);
        }
    }
    
    public static void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRenderWorldLast(event);
        }
    }
    
    public static void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRenderGameOverlay(event);
        }
    }
    
    public static void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onLeftClickBlock(event);
        }
    }
    
    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRightClickBlock(event);
        }
    }
    
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onClientTick(event);
        }
    }
    
    public static void SetOpaqueCubeEvent(final SetOpaqueCubeEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.SetOpaqueCubeEvent(event);
        }
    }
    
    public static void onGetAmbientOcclusionLightValue(final GetAmbientOcclusionLightValueEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onGetAmbientOcclusionLightValue(event);
        }
    }
    
    public static void onShouldSideBeRendered(final ShouldSideBeRenderedEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onShouldSideBeRendered(event);
        }
    }
    
    public static void onRenderBlockModel(final RenderBlockModelEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRenderBlockModel(event);
        }
    }
    
    public static void onRenderTileEntity(final RenderTileEntityEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRenderTileEntity(event);
        }
    }
    
    public static void EventCanCollide(final EventCanCollide event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.EventCanCollide(event);
        }
    }
    
    public static void ItemTooltipEvent(final ItemTooltipEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.ItemTooltipEvent(event);
        }
    }
    
    public static void postBackgroundTooltipRender(final RenderTooltipEvent.PostBackground event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.postBackgroundTooltipRender(event);
        }
    }
    
    public static void postDrawScreen(final GuiScreenEvent.DrawScreenEvent.Post event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.postDrawScreen(event);
        }
    }
    
    public static void RenderGameOverLayPost(final RenderGameOverlayEvent.Post event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RenderGameOverLayPost(event);
        }
    }
    
    public static void onPlayerMove(final PlayerMoveEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onPlayerMove(event);
        }
    }
    
    public static void onPlayerJump(final EntityPlayerJumpEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onPlayerJump(event);
        }
    }
    
    public static void RendergameOverlay(final RenderGameOverlayEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RendergameOverlay(event);
        }
    }
    
    public static void ChunkeventUNLOAD(final ChunkEvent.Unload event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.ChunkeventUNLOAD(event);
        }
    }
    
    public static void ChunkeventLOAD(final ChunkEvent.Load event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.ChunkeventLOAD(event);
        }
    }
    
    public static void fogColor(final EntityViewRenderEvent.FogColors event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.fogColor(event);
        }
    }
    
    public static void fogDensity(final EntityViewRenderEvent.FogDensity event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.fogDensity(event);
        }
    }
    
    public static void DamageBlock(final PlayerDamageBlockEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.DamageBlock(event);
        }
    }
    
    public static void PlayerSleepInBedEvent(final PlayerSleepInBedEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.PlayerSleepInBedEvent(event);
        }
    }
    
    public static void onGetLiquidCollisionBox(final GetLiquidCollisionBoxEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.GetLiquidCollisionBoxEvent(event);
        }
    }
    
    public static void EventBookPage(final EventBookPage event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.EventBookPage(event);
        }
    }
    
    public static void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.ClientChatReceivedEvent(event);
        }
    }
    
    public static void LivingDeathEvent(final LivingDeathEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.LivingDeathEvent(event);
        }
    }
    
    public static void WorldEvent(final WorldEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.WorldEvent(event);
        }
    }
    
    public static void GuiScreenEvent(final GuiScreenEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.GuiScreenEvent(event);
        }
    }
    
    public static void RendertooltipPre(final RenderTooltipEvent.Pre event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RendertooltipPre(event);
        }
    }
    
    public static void RenderPlayerEvent(final RenderPlayerEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RenderPlayerEvent(event);
        }
    }
    
    public static void RenderBlockOverlayEvent(final RenderBlockOverlayEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RenderBlockOverlayEvent(event);
        }
    }
    
    public static void GetCollisionBoxesEvent(final GetCollisionBoxesEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.GetCollisionBoxesEvent(event);
        }
    }
    
    public static void FOVModifier(final EntityViewRenderEvent.FOVModifier event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.FOVModifier(event);
        }
    }
    
    public static void DrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.DrawBlockHighlightEvent(event);
        }
    }
    
    public static void PreMotionEvent(final PreMotionEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.PreMotionEvent(event);
        }
    }
    
    public static void PostMotionEvent(final PostMotionEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.PostMotionEvent(event);
        }
    }
    
    public static void BackgroundDrawnEvent(final GuiScreenEvent.BackgroundDrawnEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.BackgroundDrawnEvent(event);
        }
    }
    
    public static void RenderTickEvent(final TickEvent.RenderTickEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.RenderTickEvent(event);
        }
    }
    
    public static void onRenderPre(final RenderGameOverlayEvent.Pre event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onRenderPre(event);
        }
    }
    
    public static void onWorldUnload(final WorldEvent.Unload event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.onWorldUnload(event);
        }
    }
    
    public static void GuiOpen(final GuiOpenEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.GuiOpen(event);
        }
    }
    
    public static void PlayerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        for (final Module m : ModuleManager.toggledModules) {
            m.PlayerRespawnEvent(event);
        }
    }
    
    public static void ClientChatEvent(final ClientChatEvent event) {
        if (event.getMessage().startsWith(String.valueOf(CommandManager.cmdPrefix))) {
            CommandManager.getInstance().runCommands(CommandManager.cmdPrefix + event.getMessage().substring(1));
            event.setCanceled(true);
            event.setMessage((String)null);
        }
        for (final Module m : ModuleManager.toggledModules) {
            m.ClientChatEvent(event);
        }
    }
    
    public static ArrayList<Module> getSortedMods(final boolean reverse, final boolean enabled, final boolean visible) {
        final ArrayList<Module> list = new ArrayList<Module>();
        final ArrayList<String> listofmods = new ArrayList<String>();
        if (!enabled) {
            for (final Module mod : getModules()) {
                if (visible && mod.visible) {
                    listofmods.add(mod.getName());
                }
                if (!visible) {
                    listofmods.add(mod.getName());
                }
            }
        }
        if (enabled) {
            for (final Module mod : getEnabledmodules()) {
                if (visible && mod.visible) {
                    listofmods.add(mod.getName());
                }
                if (!visible) {
                    listofmods.add(mod.getName());
                }
            }
        }
        listofmods.sort(Comparator.comparing((Function<? super String, ? extends Comparable>)String::length));
        if (reverse) {
            Collections.reverse(listofmods);
        }
        for (final String s : listofmods) {
            list.add(getModuleByName(s));
        }
        return list;
    }
    
    public static ArrayList<Module> getSortedHacksabc(final boolean reverse) {
        final ArrayList<Module> list = new ArrayList<Module>();
        final ArrayList<String> listofcountries = new ArrayList<String>();
        for (final Module module : getModules()) {
            listofcountries.add(module.getName());
        }
        Collections.sort(listofcountries);
        if (reverse) {
            Collections.reverse(listofcountries);
        }
        for (final String s : listofcountries) {
            list.add(getModuleByName(s));
        }
        return list;
    }
    
    static {
        ModuleManager.modules = new ArrayList<Module>();
        ModuleManager.toggledModules = new ArrayList<Module>();
        ModuleManager.FileManagerLoader = new ArrayList<Module>();
    }
}
