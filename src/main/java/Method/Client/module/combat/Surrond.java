package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import Method.Client.utils.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class Surrond extends Module
{
    Setting blocksPerTick;
    Setting rotate;
    Setting autoCenter;
    Setting offInAir;
    Setting BypassCenter;
    Setting Hand;
    Setting Onerun;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private int offsetStep;
    public static List<Block> blackList;
    public static List<Block> shulkerList;
    private static Vec3d[] SURROUND;
    
    public Surrond() {
        super("Surrond", 0, Category.COMBAT, "Surrond you with obsidian");
        this.blocksPerTick = Main.setmgr.add(new Setting("blocksPerTick", this, 10.0, 0.0, 10.0, true));
        this.rotate = Main.setmgr.add(new Setting("rotate", this, true));
        this.autoCenter = Main.setmgr.add(new Setting("autoCenter", this, true));
        this.offInAir = Main.setmgr.add(new Setting("offInAir", this, true));
        this.BypassCenter = Main.setmgr.add(new Setting("Bypass AutoCenter", this, true, this.autoCenter, 2));
        this.Hand = Main.setmgr.add(new Setting("Hand", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.Onerun = Main.setmgr.add(new Setting("Run Once", this, true));
    }
    
    @Override
    public void setup() {
        Surrond.blackList = Arrays.asList(Blocks.field_150477_bB, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn);
        Surrond.shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
        Surrond.SURROUND = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0) };
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.offsetStep = 0;
    }
    
    @Override
    public void onEnable() {
        if (this.autoCenter.getValBoolean()) {
            if (this.BypassCenter.getValBoolean()) {
                final double lMotionX = Math.floor(Surrond.mc.field_71439_g.field_70165_t) + 0.5 - Surrond.mc.field_71439_g.field_70165_t;
                final double lMotionZ = Math.floor(Surrond.mc.field_71439_g.field_70161_v) + 0.5 - Surrond.mc.field_71439_g.field_70161_v;
                Surrond.mc.field_71439_g.field_70159_w = lMotionX / 2.0;
                Surrond.mc.field_71439_g.field_70179_y = lMotionZ / 2.0;
            }
            else {
                this.centerPlayer(Math.floor(Surrond.mc.field_71439_g.field_70165_t) + 0.5, Surrond.mc.field_71439_g.field_70163_u, Math.floor(Surrond.mc.field_71439_g.field_70161_v) + 0.5);
            }
        }
        this.playerHotbarSlot = Surrond.mc.field_71439_g.field_71071_by.field_70461_c;
        this.lastHotbarSlot = -1;
    }
    
    @Override
    public void onDisable() {
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Surrond.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
    
    private void centerPlayer(final double x, final double y, final double z) {
        Surrond.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(x, y, z, true));
        Surrond.mc.field_71439_g.func_70107_b(x, y, z);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.offInAir.getValBoolean() && !Surrond.mc.field_71439_g.field_70122_E) {
            this.toggle();
        }
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValDouble()) {
            if (this.offsetStep >= Surrond.SURROUND.length) {
                this.offsetStep = 0;
                break;
            }
            final BlockPos offsetPos = new BlockPos(Surrond.SURROUND[this.offsetStep]);
            final BlockPos targetPos = new BlockPos(Surrond.mc.field_71439_g.func_174791_d()).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
            int old_slot = -1;
            if (this.find_obi_in_hotbar() != Surrond.mc.field_71439_g.field_71071_by.field_70461_c) {
                old_slot = Surrond.mc.field_71439_g.field_71071_by.field_70461_c;
                Surrond.mc.field_71439_g.field_71071_by.field_70461_c = this.find_obi_in_hotbar();
            }
            if (Utils.trytoplace(targetPos) && Utils.placeBlock(targetPos, this.rotate.getValBoolean(), this.Hand) && Utils.placeBlock(targetPos, this.rotate.getValBoolean(), this.Hand)) {
                ++blocksPlaced;
            }
            if (old_slot != -1) {
                Surrond.mc.field_71439_g.field_71071_by.field_70461_c = old_slot;
            }
            ++this.offsetStep;
            if (blocksPlaced > 0 && this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                Surrond.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (blocksPlaced != 0) {
                continue;
            }
            this.toggle();
        }
        if (this.Onerun.getValBoolean()) {
            this.toggle();
        }
    }
    
    private int find_obi_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Surrond.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
}
