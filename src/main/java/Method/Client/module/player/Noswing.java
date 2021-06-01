package Method.Client.module.player;

import Method.Client.managers.*;
import net.minecraft.util.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.Patcher.Events.*;
import Method.Client.utils.system.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;

public class Noswing extends Module
{
    Setting mode;
    Setting Nobreakani;
    EnumFacing lastFacing;
    BlockPos lastPos;
    boolean isMining;
    
    public Noswing() {
        super("Noswing", 0, Category.PLAYER, "Noswing");
        this.mode = Main.setmgr.add(new Setting("No swing", this, "Vanilla", new String[] { "Vanilla", "Packet", "BlockClick", "PacketSwing", "Clientonly" }));
        this.Nobreakani = Main.setmgr.add(new Setting("Nobreakani", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Clientonly")) {
            final ItemRenderer itemRenderer = Noswing.mc.field_71460_t.field_78516_c;
            itemRenderer.field_187469_f = 1.0f;
            itemRenderer.field_187467_d = Noswing.mc.field_71439_g.func_184614_ca();
        }
        if (this.mode.getValString().equalsIgnoreCase("Vanilla") && Noswing.mc.field_71439_g.field_70733_aJ <= 0.0f) {
            Noswing.mc.field_71439_g.field_110158_av = 5;
        }
        if (this.Nobreakani.getValBoolean()) {
            if (Noswing.mc.field_71474_y.field_74312_F.func_151470_d()) {
                this.resetMining();
            }
            else if (this.isMining && this.lastPos != null && this.lastFacing != null) {
                Noswing.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.lastPos, this.lastFacing));
            }
        }
    }
    
    public void resetMining() {
        this.isMining = false;
        this.lastPos = null;
        this.lastFacing = null;
    }
    
    @Override
    public void DamageBlock(final PlayerDamageBlockEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("PacketSwing")) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, event.getPos(), event.getFacing()));
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketPlayerDigging && side == Connection.Side.OUT && this.Nobreakani.getValBoolean()) {
            final CPacketPlayerDigging packet2 = (CPacketPlayerDigging)packet;
            for (final Entity entity : Noswing.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(packet2.func_179715_a()))) {
                if (entity instanceof EntityEnderCrystal) {
                    this.resetMining();
                }
                else {
                    if (!(entity instanceof EntityLivingBase)) {
                        continue;
                    }
                    this.resetMining();
                }
            }
            if (packet2.func_180762_c().equals((Object)CPacketPlayerDigging.Action.START_DESTROY_BLOCK)) {
                this.isMining = true;
                this.setMiningInfo(packet2.func_179715_a(), packet2.func_179714_b());
            }
            if (packet2.func_180762_c().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                this.resetMining();
            }
        }
        return !(packet instanceof CPacketAnimation) || !this.mode.getValString().equalsIgnoreCase("packet");
    }
    
    @Override
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        if (this.mode.getValString().equalsIgnoreCase("BlockClick")) {
            this.Blockclick((Event)event);
        }
    }
    
    @Override
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (this.mode.getValString().equalsIgnoreCase("BlockClick")) {
            this.Blockclick((Event)event);
        }
    }
    
    void Blockclick(final Event event) {
        if (Noswing.mc.field_71476_x.field_72308_g == null) {
            final BlockPos blockPos = Noswing.mc.field_71476_x.func_178782_a();
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
            event.setCanceled(true);
        }
        if (Noswing.mc.field_71476_x.field_72308_g != null) {
            Noswing.mc.field_71442_b.func_78764_a((EntityPlayer)Noswing.mc.field_71439_g, Noswing.mc.field_71476_x.field_72308_g);
            event.setCanceled(true);
        }
    }
    
    private void setMiningInfo(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.lastPos = blockPos;
        this.lastFacing = enumFacing;
    }
}
