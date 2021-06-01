package Method.Client.module.render;

import Method.Client.managers.*;
import net.minecraft.util.math.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import Method.Client.utils.visual.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.util.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;

public class ChestESP extends Module
{
    public boolean shouldInform;
    Setting Chest;
    Setting Shulker;
    Setting Trappedchest;
    Setting EnderChest;
    Setting MinecartChest;
    Setting ChestColor;
    Setting TrappedchestColor;
    Setting EnderChestColor;
    Setting MinecartChestColor;
    Setting ShulkerColor;
    Setting Mode;
    Setting LineWidth;
    Setting ChangeSeen;
    Setting OpenedColor;
    Setting Notify;
    Setting maxChests;
    ArrayList<BlockPos> Openedpos;
    
    public ChestESP() {
        super("ChestESP", 0, Category.RENDER, "ChestESP");
        this.shouldInform = true;
        this.Chest = Main.setmgr.add(new Setting("Chest", this, true));
        this.Shulker = Main.setmgr.add(new Setting("Shulker", this, true));
        this.Trappedchest = Main.setmgr.add(new Setting("Trapped", this, true));
        this.EnderChest = Main.setmgr.add(new Setting("Ender", this, true));
        this.MinecartChest = Main.setmgr.add(new Setting("Minecart", this, true));
        this.ChestColor = Main.setmgr.add(new Setting("ChestC", this, 0.22, 1.0, 1.0, 0.5, this.Chest, 7));
        this.TrappedchestColor = Main.setmgr.add(new Setting("TrappedC", this, 0.0, 1.0, 1.0, 0.5, this.Trappedchest, 7));
        this.EnderChestColor = Main.setmgr.add(new Setting("EnderC", this, 0.44, 1.0, 1.0, 0.5, this.EnderChest, 7));
        this.MinecartChestColor = Main.setmgr.add(new Setting("MinecartC", this, 0.88, 1.0, 1.0, 0.5, this.MinecartChest, 7));
        this.ShulkerColor = Main.setmgr.add(new Setting("ShulkerColor", this, 0.96, 1.0, 1.0, 0.5, this.MinecartChest, 7));
        this.Mode = Main.setmgr.add(new Setting("Draw Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.ChangeSeen = Main.setmgr.add(new Setting("Has been opened", this, true));
        this.OpenedColor = Main.setmgr.add(new Setting("OpenedColor", this, 0.0, 1.0, 1.0, 0.5, this.ChangeSeen, 7));
        this.Notify = Main.setmgr.add(new Setting("Notify", this, 50.0, 100.0, 2000.0, true));
        this.maxChests = Main.setmgr.add(new Setting("maxChests", this, 1000.0, 100.0, 2000.0, true));
        this.Openedpos = new ArrayList<BlockPos>();
    }
    
    @Override
    public void onEnable() {
        this.shouldInform = true;
        this.Openedpos.clear();
        super.onEnable();
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        int chests = 0;
        for (final TileEntity entity : ChestESP.mc.field_71441_e.field_147482_g) {
            if (entity instanceof TileEntityChest) {
                if (chests >= this.maxChests.getValDouble() && this.shouldInform) {
                    break;
                }
                ++chests;
                final TileEntityChest chest = (TileEntityChest)entity;
                if (this.ChangeSeen.getValBoolean()) {
                    if (chest.field_145987_o > 0) {
                        this.Openedpos.add(chest.func_174877_v());
                    }
                    if (this.Openedpos.contains(chest.func_174877_v())) {
                        RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(chest.func_174877_v()), this.OpenedColor.getcolor(), this.LineWidth.getValDouble());
                        continue;
                    }
                }
                if (chest.func_145980_j() == BlockChest.Type.TRAP && this.Trappedchest.getValBoolean()) {
                    RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(chest.func_174877_v()), this.TrappedchestColor.getcolor(), this.LineWidth.getValDouble());
                    continue;
                }
                if (this.Chest.getValBoolean()) {
                    RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(chest.func_174877_v()), this.ChestColor.getcolor(), this.LineWidth.getValDouble());
                    continue;
                }
            }
            if (entity instanceof TileEntityEnderChest && this.EnderChest.getValBoolean()) {
                ++chests;
                RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(entity.func_174877_v()), this.EnderChestColor.getcolor(), this.LineWidth.getValDouble());
            }
            if (entity instanceof TileEntityShulkerBox && this.Shulker.getValBoolean()) {
                ++chests;
                RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(entity.func_174877_v()), this.ShulkerColor.getcolor(), this.LineWidth.getValDouble());
            }
        }
        for (final Entity entity2 : ChestESP.mc.field_71441_e.field_72996_f) {
            if (chests >= this.maxChests.getValDouble()) {
                break;
            }
            if (!(entity2 instanceof EntityMinecartChest) || !this.MinecartChest.getValBoolean()) {
                continue;
            }
            ++chests;
            RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(entity2.func_180425_c()), this.MinecartChestColor.getcolor(), this.LineWidth.getValDouble());
        }
        if (this.shouldInform) {
            if (chests >= this.Notify.getValDouble()) {
                ChatUtils.warning("Found " + chests + " chests.");
                ChestESP.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0f, 1.0f));
            }
            if (chests >= this.maxChests.getValDouble()) {
                ChatUtils.warning("To prevent lag, it will only show the first " + this.maxChests.getValDouble() + " chests.");
                this.shouldInform = false;
            }
        }
        else if (chests < this.maxChests.getValDouble()) {
            this.shouldInform = true;
        }
        super.onRenderWorldLast(event);
    }
    
    @Nullable
    public ILockableContainer getLockableContainer(final World worldIn, final BlockPos pos) {
        return this.getContainer(worldIn, pos, false);
    }
    
    @Nullable
    public ILockableContainer getContainer(final World worldIn, final BlockPos pos, final boolean allowBlocking) {
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (!(tileentity instanceof TileEntityChest)) {
            return null;
        }
        ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
        for (final EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            final BlockPos blockpos = pos.func_177972_a(enumfacing);
            final TileEntity tileentity2 = worldIn.func_175625_s(blockpos);
            if (tileentity2 instanceof TileEntityChest) {
                if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                    ilockablecontainer = (ILockableContainer)new InventoryLargeChest("container.chestDouble", ilockablecontainer, (ILockableContainer)tileentity2);
                }
                else {
                    ilockablecontainer = (ILockableContainer)new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileentity2, ilockablecontainer);
                }
            }
        }
        return ilockablecontainer;
    }
}
