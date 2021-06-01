package Method.Client.module.Onscreen.Display;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.module.Onscreen.*;
import net.minecraftforge.client.event.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;
import net.minecraft.block.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import Method.Client.module.movement.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

public final class Hole extends Module
{
    static Setting xpos;
    static Setting ypos;
    
    public Hole() {
        super("Hole", 0, Category.ONSCREEN, "Hole");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(Hole.xpos = new Setting("xpos", this, 200.0, -20.0, Hole.mc.field_71443_c + 40, true));
        Main.setmgr.add(Hole.ypos = new Setting("ypos", this, 90.0, -20.0, Hole.mc.field_71440_d + 40, true));
    }
    
    @Override
    public void onEnable() {
        PinableFrame.Toggle("HoleSET", true);
    }
    
    @Override
    public void onDisable() {
        PinableFrame.Toggle("HoleSET", false);
    }
    
    public static class HoleRUN extends PinableFrame
    {
        public HoleRUN() {
            super("HoleSET", new String[0], (int)Hole.ypos.getValDouble(), (int)Hole.xpos.getValDouble());
        }
        
        @Override
        public void setup() {
            Hole.xpos.setValDouble(this.x);
            Hole.ypos.setValDouble(this.y);
        }
        
        @Override
        public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
            float yaw = 0.0f;
            final int dir = MathHelper.func_76128_c(this.mc.field_71439_g.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
            switch (dir) {
                case 1: {
                    yaw = 90.0f;
                    break;
                }
                case 2: {
                    yaw = -180.0f;
                    break;
                }
                case 3: {
                    yaw = -90.0f;
                    break;
                }
            }
            final BlockPos northPos = this.traceToBlock(this.mc.func_184121_ak(), yaw);
            final Block north = this.getBlock(northPos);
            if (north != null && north != Blocks.field_150350_a) {
                final int damage = this.getBlockDamage(northPos);
                if (damage != 0) {
                    Gui.func_73734_a(this.getX() + 16, this.getY(), this.getX() + 32, this.getY() + 16, 1627324416);
                }
                this.drawBlock(north, this.getX() + 16, this.getY());
            }
            final BlockPos southPos = this.traceToBlock(this.mc.func_184121_ak(), yaw - 180.0f);
            final Block south = this.getBlock(southPos);
            if (south != null && south != Blocks.field_150350_a) {
                final int damage2 = this.getBlockDamage(southPos);
                if (damage2 != 0) {
                    Gui.func_73734_a(this.getX() + 16, this.getY() + 32, this.getX() + 32, this.getY() + 48, 1627324416);
                }
                this.drawBlock(south, this.getX() + 16, this.getY() + 32);
            }
            final BlockPos eastPos = this.traceToBlock(this.mc.func_184121_ak(), yaw + 90.0f);
            final Block east = this.getBlock(eastPos);
            if (east != null && east != Blocks.field_150350_a) {
                final int damage3 = this.getBlockDamage(eastPos);
                if (damage3 != 0) {
                    Gui.func_73734_a(this.getX() + 32, this.getY() + 16, this.getX() + 48, this.getY() + 32, 1627324416);
                }
                this.drawBlock(east, this.getX() + 32, this.getY() + 16);
            }
            final BlockPos westPos = this.traceToBlock(this.mc.func_184121_ak(), yaw - 90.0f);
            final Block west = this.getBlock(westPos);
            if (west != null && west != Blocks.field_150350_a) {
                final int damage4 = this.getBlockDamage(westPos);
                if (damage4 != 0) {
                    Gui.func_73734_a(this.getX(), this.getY() + 16, this.getX() + 16, this.getY() + 32, 1627324416);
                }
                this.drawBlock(west, this.getX(), this.getY() + 16);
            }
        }
        
        private BlockPos traceToBlock(final float partialTicks, final float yaw) {
            final Vec3d pos = Utils.interpolateEntity((Entity)this.mc.field_71439_g, partialTicks);
            final Vec3d dir = direction(yaw);
            return new BlockPos(pos.field_72450_a + dir.field_72450_a, pos.field_72448_b, pos.field_72449_c + dir.field_72449_c);
        }
        
        public static Vec3d direction(final float yaw) {
            return new Vec3d(Math.cos(Phase.degToRad(yaw + 90.0f)), 0.0, Math.sin(Phase.degToRad(yaw + 90.0f)));
        }
        
        private int getBlockDamage(final BlockPos pos) {
            for (final DestroyBlockProgress destBlockProgress : this.mc.field_71438_f.field_72738_E.values()) {
                if (destBlockProgress.func_180246_b().func_177958_n() == pos.func_177958_n() && destBlockProgress.func_180246_b().func_177956_o() == pos.func_177956_o() && destBlockProgress.func_180246_b().func_177952_p() == pos.func_177952_p()) {
                    return destBlockProgress.func_73106_e();
                }
            }
            return 0;
        }
        
        private Block getBlock(final BlockPos pos) {
            final Block block = this.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (block == Blocks.field_150357_h || block == Blocks.field_150343_Z) {
                return block;
            }
            return Blocks.field_150350_a;
        }
        
        private void drawBlock(final Block block, final float x, final float y) {
            final ItemStack stack = new ItemStack(block);
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            RenderHelper.func_74520_c();
            GlStateManager.func_179109_b(x, y, 0.0f);
            this.mc.func_175599_af().func_180450_b(stack, 0, 0);
            RenderHelper.func_74518_a();
            GlStateManager.func_179084_k();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179121_F();
        }
    }
}
