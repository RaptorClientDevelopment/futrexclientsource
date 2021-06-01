package Method.Client.module.render;

import Method.Client.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraft.block.*;
import net.minecraftforge.client.event.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.util.text.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;

public class NoBlockLag extends Module
{
    Setting StopCollision;
    Setting antiSound;
    Setting antiPiston;
    Setting antiSign;
    Setting Storage;
    Setting Spawner;
    Setting Beacon;
    Setting MobHead;
    Setting Falling;
    Setting Banner;
    Setting Gentity;
    Setting Object;
    Setting Grass;
    Setting Painting;
    Setting Fire;
    Setting NoChunk;
    ArrayList<BlockPos> modifiedsigns;
    private Set<SoundEvent> sounds;
    
    public NoBlockLag() {
        super("NoBlockLag", 0, Category.RENDER, "NoBlockLag");
        this.StopCollision = Main.setmgr.add(new Setting("StopCollision", this, false));
        this.antiSound = Main.setmgr.add(new Setting("antiSound", this, true));
        this.antiPiston = Main.setmgr.add(new Setting("antiPiston", this, false));
        this.antiSign = Main.setmgr.add(new Setting("antiSign", this, false));
        this.Storage = Main.setmgr.add(new Setting("Storage", this, false));
        this.Spawner = Main.setmgr.add(new Setting("Spawner", this, false));
        this.Beacon = Main.setmgr.add(new Setting("Beacon", this, false));
        this.MobHead = Main.setmgr.add(new Setting("MobHead", this, false));
        this.Falling = Main.setmgr.add(new Setting("Falling", this, false));
        this.Banner = Main.setmgr.add(new Setting("Banner", this, false));
        this.Gentity = Main.setmgr.add(new Setting("Global Entity", this, false));
        this.Object = Main.setmgr.add(new Setting("objects", this, false));
        this.Grass = Main.setmgr.add(new Setting("Grass", this, false));
        this.Painting = Main.setmgr.add(new Setting("Paintings", this, false));
        this.Fire = Main.setmgr.add(new Setting("Fire", this, false));
        this.NoChunk = Main.setmgr.add(new Setting("NoChunk", this, false));
        this.modifiedsigns = new ArrayList<BlockPos>();
    }
    
    @Override
    public void setup() {
        this.sounds = new HashSet<SoundEvent>();
    }
    
    @Override
    public void onRenderTileEntity(final RenderTileEntityEvent event) {
        final Block block = event.getTileEntity().func_145838_q().func_176194_O().func_177622_c();
        if (this.BlockCheck(block)) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onRenderBlockModel(final RenderBlockModelEvent event) {
        final Block block = event.getState().func_177230_c();
        if (this.BlockCheck(block)) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void DrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        if (this.StopCollision.getValBoolean() && this.BlockCheck(NoBlockLag.mc.field_71441_e.func_180495_p(event.getTarget().func_178782_a()).func_177230_c())) {
            event.setCanceled(true);
        }
    }
    
    public boolean BlockCheck(final Block block) {
        return (this.antiPiston.getValBoolean() && (block instanceof BlockPistonMoving || block instanceof BlockPistonExtension)) || (this.Storage.getValBoolean() && (block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockDispenser || block instanceof BlockFurnace || block instanceof BlockHopper || block instanceof BlockShulkerBox || block instanceof BlockBrewingStand)) || (this.Spawner.getValBoolean() && block instanceof BlockMobSpawner) || (this.Beacon.getValBoolean() && block instanceof BlockBeacon) || (this.Banner.getValBoolean() && block instanceof BlockBanner) || (this.Fire.getValBoolean() && block instanceof BlockFire) || (this.Grass.getValBoolean() && (block instanceof BlockDoublePlant || block instanceof BlockTallGrass || block instanceof BlockDeadBush)) || (this.MobHead.getValBoolean() && block instanceof BlockSkull) || (this.Falling.getValBoolean() && block instanceof BlockFalling);
    }
    
    @Override
    public void onEnable() {
        this.modifiedsigns.clear();
        NoBlockLag.mc.field_71438_f.func_72712_a();
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.antiSign.getValBoolean()) {
            for (final TileEntity tileEntity : NoBlockLag.mc.field_71441_e.field_147482_g) {
                if (tileEntity instanceof TileEntitySign) {
                    final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
                    if (!this.modifiedsigns.contains(tileEntity.func_174877_v())) {
                        this.modifiedsigns.add(tileEntity.func_174877_v());
                        int lenght = 0;
                        for (final ITextComponent line : tileEntitySign.field_145915_a) {
                            lenght = line.func_150260_c().length();
                        }
                        final String[] array = { "METHOD", "Sign length", "" + lenght + "", "" };
                        for (int i = 0; i < tileEntitySign.field_145915_a.length; ++i) {
                            tileEntitySign.field_145915_a[i] = (ITextComponent)new TextComponentString(array[i]);
                        }
                    }
                    else {
                        final ITextComponent[] field_145915_a2 = tileEntitySign.field_145915_a;
                        final int length2 = field_145915_a2.length;
                        final int n = 0;
                        if (n >= length2) {
                            continue;
                        }
                        final ITextComponent line2 = field_145915_a2[n];
                        if (line2.func_150260_c().startsWith("METHOD")) {
                            continue;
                        }
                        this.modifiedsigns.remove(tileEntity.func_174877_v());
                    }
                }
            }
        }
        super.onRenderWorldLast(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if ((packet instanceof SPacketSpawnGlobalEntity && this.Gentity.getValBoolean()) || (packet instanceof SPacketSpawnObject && this.Object.getValBoolean()) || (packet instanceof SPacketSpawnPainting && this.Painting.getValBoolean())) {
            return false;
        }
        if (this.antiSound.getValBoolean() && packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)packet;
            if (this.sounds.contains(sPacketSoundEffect.func_186978_a())) {
                return false;
            }
            this.sounds.add(sPacketSoundEffect.func_186978_a());
        }
        return side != Connection.Side.IN || !(packet instanceof SPacketChunkData) || !this.NoChunk.getValBoolean();
    }
}
