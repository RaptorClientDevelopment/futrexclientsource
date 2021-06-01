package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.init.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class NoSlowdown extends Module
{
    private boolean sneaking;
    Setting web;
    Setting Webfall;
    Setting Eat;
    Setting Slowdownbypass;
    Setting Breakdelay;
    Setting Slimeblock;
    Setting NoIceSlip;
    
    public NoSlowdown() {
        super("NoSlowdown", 0, Category.MISC, "No more slow");
        this.web = Main.setmgr.add(new Setting("webs", this, false));
        this.Webfall = Main.setmgr.add(new Setting("Webfall", this, false));
        this.Eat = Main.setmgr.add(new Setting("Eat", this, false));
        this.Slowdownbypass = Main.setmgr.add(new Setting("Slowdown Bypass", this, false));
        this.Breakdelay = Main.setmgr.add(new Setting("Breakdelay", this, false));
        this.Slimeblock = Main.setmgr.add(new Setting("Slimeblock", this, false));
        this.NoIceSlip = Main.setmgr.add(new Setting("NoIceSlip", this, false));
    }
    
    @Override
    public void onDisable() {
        Blocks.field_150432_aD.field_149765_K = 0.98f;
        Blocks.field_150403_cj.field_149765_K = 0.98f;
        Blocks.field_185778_de.field_149765_K = 0.98f;
    }
    
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.Slowdownbypass.getValBoolean()) {
            if (this.sneaking && !NoSlowdown.mc.field_71439_g.func_184587_cr()) {
                NoSlowdown.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)NoSlowdown.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneaking = false;
            }
            if (!this.sneaking && NoSlowdown.mc.field_71439_g.func_184587_cr()) {
                NoSlowdown.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)NoSlowdown.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                this.sneaking = true;
            }
        }
        if (this.NoIceSlip.getValBoolean()) {
            Blocks.field_150432_aD.field_149765_K = 0.0f;
            Blocks.field_150403_cj.field_149765_K = 0.0f;
            Blocks.field_185778_de.field_149765_K = 0.0f;
        }
        if (this.Slimeblock.getValBoolean()) {
            final BlockPos pos = new BlockPos(Math.floor(NoSlowdown.mc.field_71439_g.field_70165_t), Math.ceil(NoSlowdown.mc.field_71439_g.field_70163_u), Math.floor(NoSlowdown.mc.field_71439_g.field_70161_v));
            if (NoSlowdown.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockSlime && NoSlowdown.mc.field_71439_g.field_70122_E) {
                NoSlowdown.mc.field_71439_g.field_70181_x = 1.5;
            }
        }
        if (NoSlowdown.mc.field_71439_g.func_184587_cr() && this.Eat.getValBoolean()) {
            final EntityPlayerSP field_71439_g = NoSlowdown.mc.field_71439_g;
            field_71439_g.field_191988_bg *= 5.0f;
            final EntityPlayerSP field_71439_g2 = NoSlowdown.mc.field_71439_g;
            field_71439_g2.field_70702_br *= 5.0f;
            NoSlowdown.mc.field_71442_b.func_78750_j();
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
        if (this.web.getValBoolean()) {
            NoSlowdown.mc.field_71439_g.field_70134_J = false;
        }
        if (this.Webfall.getValBoolean() && !NoSlowdown.mc.field_71439_g.field_70122_E && NoSlowdown.mc.field_71439_g.field_70143_R > 3.0f) {
            NoSlowdown.mc.field_71439_g.field_70181_x = -0.22000000000000003;
        }
        if (this.Breakdelay.getValBoolean()) {
            NoSlowdown.mc.field_71442_b.field_78781_i = 0;
        }
    }
}
