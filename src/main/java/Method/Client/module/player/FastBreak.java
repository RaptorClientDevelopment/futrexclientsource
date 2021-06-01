package Method.Client.module.player;

import Method.Client.managers.*;
import net.minecraft.util.math.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import Method.Client.utils.*;
import Method.Client.utils.system.*;

public class FastBreak extends Module
{
    Setting mode;
    PotionEffect Haste;
    Setting autoBreak;
    Setting picOnly;
    Setting Blockair;
    Setting delay;
    private BlockPos renderBlock;
    private BlockPos lastBlock;
    private boolean packetCancel;
    public static final TimerUtils timer;
    private EnumFacing direction;
    
    public FastBreak() {
        super("FastBreak", 0, Category.PLAYER, "FastBreak");
        this.mode = Main.setmgr.add(new Setting("break mode", this, "potion", new String[] { "Potion", "Packet", "INSTANT", "NoDelay" }));
        this.Haste = new PotionEffect((Potion)Objects.requireNonNull(Potion.func_188412_a(3)));
        this.autoBreak = Main.setmgr.add(new Setting("autoBreak", this, false, this.mode, "INSTANT", 1));
        this.picOnly = Main.setmgr.add(new Setting("picOnly", this, false, this.mode, "INSTANT", 2));
        this.Blockair = Main.setmgr.add(new Setting("Blockair", this, false, this.mode, "INSTANT", 3));
        this.delay = Main.setmgr.add(new Setting("delay", this, 1.0, 0.0, 5.0, true, this.mode, "INSTANT", 4));
        this.packetCancel = false;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.renderBlock != null) {}
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("INSTANT") && side == Connection.Side.OUT && packet instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging digPacket = (CPacketPlayerDigging)packet;
            return digPacket.func_180762_c() != CPacketPlayerDigging.Action.START_DESTROY_BLOCK || !this.packetCancel;
        }
        return true;
    }
    
    private boolean canBreak(final BlockPos pos) {
        return FastBreak.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176195_g(FastBreak.mc.field_71441_e.func_180495_p(pos), (World)FastBreak.mc.field_71441_e, pos) != -1.0f;
    }
    
    public void setTarget(final BlockPos pos) {
        this.renderBlock = pos;
        this.packetCancel = false;
        FastBreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
        this.packetCancel = true;
        FastBreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.DOWN));
        this.direction = EnumFacing.DOWN;
        this.lastBlock = pos;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("INSTANT")) {
            if (this.renderBlock != null && this.autoBreak.getValBoolean() && FastBreak.timer.isDelay((long)this.delay.getValDouble() * 1000L)) {
                if (this.picOnly.getValBoolean() && FastBreak.mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() != Items.field_151046_w) {
                    return;
                }
                FastBreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.renderBlock, this.direction));
                FastBreak.timer.setLastMS();
            }
            FastBreak.mc.field_71442_b.field_78781_i = 0;
        }
        if (this.mode.getValString().equalsIgnoreCase("NoDelay")) {
            FastBreak.mc.field_71442_b.field_78781_i = 0;
        }
        if (this.mode.getValString().equalsIgnoreCase("potion") && FastBreak.mc.field_71439_g.field_70122_E) {
            FastBreak.mc.field_71439_g.func_70690_d(this.Haste);
        }
        if (this.mode.getValString().equalsIgnoreCase("Packet")) {
            FastBreak.mc.field_71439_g.func_184596_c(this.Haste.func_188419_a());
            if (FastBreak.mc.field_71442_b.field_78770_f > 0.7f) {
                FastBreak.mc.field_71442_b.field_78770_f = 1.0f;
            }
            FastBreak.mc.field_71442_b.field_78781_i = 0;
        }
        super.onClientTick(event);
    }
    
    @Override
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        if (this.mode.getValString().equalsIgnoreCase("INSTANT") && this.canBreak(event.getPos())) {
            if (this.lastBlock == null || event.getPos().field_177962_a != this.lastBlock.field_177962_a || event.getPos().field_177960_b != this.lastBlock.field_177960_b || event.getPos().field_177961_c != this.lastBlock.field_177961_c) {
                this.packetCancel = false;
                FastBreak.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                FastBreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), (EnumFacing)Objects.requireNonNull(event.getFace())));
            }
            this.packetCancel = true;
            FastBreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), (EnumFacing)Objects.requireNonNull(event.getFace())));
            this.renderBlock = event.getPos();
            this.lastBlock = event.getPos();
            this.direction = event.getFace();
            if (this.Blockair.getValBoolean()) {
                FastBreak.mc.field_71442_b.func_187103_a(event.getPos());
                FastBreak.mc.field_71441_e.func_175698_g(event.getPos());
            }
            event.setResult(Event.Result.DENY);
        }
        if (this.mode.getValString().equalsIgnoreCase("packet")) {
            final float progress = FastBreak.mc.field_71442_b.field_78770_f + BlockUtils.getHardness(event.getPos());
            if (progress >= 1.0f) {
                return;
            }
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), FastBreak.mc.field_71476_x.field_178784_b));
        }
        super.onLeftClickBlock(event);
    }
    
    @Override
    public void onDisable() {
        FastBreak.mc.field_71439_g.func_184596_c(this.Haste.func_188419_a());
        super.onDisable();
    }
    
    static {
        timer = new TimerUtils();
    }
}
