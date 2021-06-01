package Method.Client.module.player;

import net.minecraft.util.math.*;
import net.minecraft.util.*;
import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.client.multiplayer.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraftforge.event.*;

public class Autotool extends Module
{
    public BlockPos position;
    public EnumFacing facing;
    Setting silent;
    Setting Select;
    Setting EchestSilk;
    
    public Autotool() {
        super("Autotool", 0, Category.PLAYER, "Autotool");
        this.silent = Main.setmgr.add(new Setting("spoof tool", this, false));
        this.Select = Main.setmgr.add(new Setting("Full Inventory", this, true));
        this.EchestSilk = Main.setmgr.add(new Setting("Echest Silk", this, false));
    }
    
    @Override
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        final int slot = this.getTool(event.getPos(), this.Select.getValBoolean() ? 36 : 9);
        if (slot != -1) {
            if (this.silent.getValBoolean()) {
                final PlayerControllerMP field_71442_b = Autotool.mc.field_71442_b;
                field_71442_b.field_78770_f += this.blockStrength(event.getPos(), Autotool.mc.field_71439_g.field_71069_bz.func_75139_a(slot).func_75211_c());
            }
            Autotool.mc.field_71439_g.field_71071_by.field_70461_c = slot;
            Autotool.mc.field_71442_b.func_78765_e();
            this.Pswap(slot);
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging packet2 = (CPacketPlayerDigging)packet;
            if (packet2.func_180762_c() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                this.position = packet2.func_179715_a();
                this.facing = packet2.func_179714_b();
            }
        }
        return true;
    }
    
    private void Pswap(final int slot) {
        Autotool.mc.field_71442_b.func_187098_a(Autotool.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)Autotool.mc.field_71439_g);
        Autotool.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.position, this.facing));
        Autotool.mc.field_71442_b.func_187098_a(Autotool.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)Autotool.mc.field_71439_g);
    }
    
    private int getTool(final BlockPos pos, final int slots) {
        int index = -1;
        float CurrentFastest = 1.0f;
        for (int i = 0; i <= slots; ++i) {
            final ItemStack stack = Autotool.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a) {
                final float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
                final float destroySpeed = stack.func_150997_a(Autotool.mc.field_71441_e.func_180495_p(pos));
                if (Autotool.mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockEnderChest && this.EchestSilk.getValBoolean()) {
                    if (EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, stack) > 0 && digSpeed + destroySpeed > CurrentFastest) {
                        CurrentFastest = digSpeed + destroySpeed;
                        index = i;
                    }
                }
                else if (digSpeed + destroySpeed > CurrentFastest) {
                    CurrentFastest = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
    
    private float blockStrength(final BlockPos pos, final ItemStack stack) {
        final float hardness = Autotool.mc.field_71441_e.func_180495_p(pos).func_185887_b((World)Autotool.mc.field_71441_e, pos);
        if (hardness < 0.0f) {
            return 0.0f;
        }
        return this.getDigSpeed(Autotool.mc.field_71441_e.func_180495_p(pos), pos, stack) / hardness / (this.canHarvestBlock(Autotool.mc.field_71441_e.func_180495_p(pos).func_177230_c(), pos, stack) ? 30.0f : 100.0f);
    }
    
    private boolean canHarvestBlock(final Block block, final BlockPos pos, final ItemStack stack) {
        IBlockState state = Autotool.mc.field_71441_e.func_180495_p(pos);
        state = state.func_177230_c().func_176221_a(state, (IBlockAccess)Autotool.mc.field_71441_e, pos);
        if (state.func_185904_a().func_76229_l()) {
            return true;
        }
        final String tool = block.getHarvestTool(state);
        if (stack.func_190926_b() || tool == null) {
            return Autotool.mc.field_71439_g.func_184823_b(state);
        }
        final int toolLevel = stack.func_77973_b().getHarvestLevel(stack, tool, (EntityPlayer)Autotool.mc.field_71439_g, state);
        if (toolLevel < 0) {
            return Autotool.mc.field_71439_g.func_184823_b(state);
        }
        return toolLevel >= block.getHarvestLevel(state);
    }
    
    private float getDigSpeed(final IBlockState state, final BlockPos pos, final ItemStack stack) {
        float f = stack.func_150997_a(state);
        if (f > 1.0f) {
            final int i = EnchantmentHelper.func_185293_e((EntityLivingBase)Autotool.mc.field_71439_g);
            if (i > 0 && !stack.func_190926_b()) {
                f += i * i + 1;
            }
        }
        if (Autotool.mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
            f *= 1.0f + (Objects.requireNonNull(Autotool.mc.field_71439_g.func_70660_b(MobEffects.field_76422_e)).func_76458_c() + 1) * 0.2f;
        }
        if (Autotool.mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
            float f2 = 0.0f;
            switch (Objects.requireNonNull(Autotool.mc.field_71439_g.func_70660_b(MobEffects.field_76419_f)).func_76458_c()) {
                case 0: {
                    f2 = 0.3f;
                    break;
                }
                case 1: {
                    f2 = 0.09f;
                    break;
                }
                case 2: {
                    f2 = 0.0027f;
                    break;
                }
                default: {
                    f2 = 8.1E-4f;
                    break;
                }
            }
            f *= f2;
        }
        if (Autotool.mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i((EntityLivingBase)Autotool.mc.field_71439_g)) {
            f /= 5.0f;
        }
        if (!Autotool.mc.field_71439_g.field_70122_E) {
            f /= 5.0f;
        }
        f = ForgeEventFactory.getBreakSpeed((EntityPlayer)Autotool.mc.field_71439_g, state, f, pos);
        return (f < 0.0f) ? 0.0f : f;
    }
}
