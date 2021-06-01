package Method.Client.module.player;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;

public class AutoRemount extends Module
{
    Setting Bypass;
    Setting boat;
    Setting Minecart;
    Setting horse;
    Setting skeletonHorse;
    Setting donkey;
    Setting mule;
    Setting pig;
    Setting llama;
    Setting range;
    
    public AutoRemount() {
        super("AutoRemount", 0, Category.PLAYER, "AutoRemount");
        this.Bypass = Main.setmgr.add(new Setting("Bypass", this, true));
        this.boat = Main.setmgr.add(new Setting("boat", this, true));
        this.Minecart = Main.setmgr.add(new Setting("Minecart", this, true));
        this.horse = Main.setmgr.add(new Setting("horse", this, true));
        this.skeletonHorse = Main.setmgr.add(new Setting("skeletonHorse", this, true));
        this.donkey = Main.setmgr.add(new Setting("donkey", this, true));
        this.mule = Main.setmgr.add(new Setting("mule", this, true));
        this.pig = Main.setmgr.add(new Setting("pig ", this, true));
        this.llama = Main.setmgr.add(new Setting("llama", this, true));
        this.range = Main.setmgr.add(new Setting("range", this, 2.0, 0.0, 10.0, true));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!AutoRemount.mc.field_71439_g.func_184218_aH()) {
            AutoRemount.mc.field_71441_e.field_72996_f.stream().filter(this::isValidEntity).min(Comparator.comparing(en -> AutoRemount.mc.field_71439_g.func_70032_d(en))).ifPresent(entity -> AutoRemount.mc.field_71442_b.func_187097_a((EntityPlayer)AutoRemount.mc.field_71439_g, entity, EnumHand.MAIN_HAND));
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketUseEntity && this.Bypass.getValBoolean()) {
            final CPacketUseEntity packet2 = (CPacketUseEntity)packet;
            if (this.isValidEntity(packet2.func_149564_a((World)AutoRemount.mc.field_71441_e))) {
                return !packet2.field_149566_b.equals((Object)CPacketUseEntity.Action.INTERACT_AT);
            }
        }
        return true;
    }
    
    private boolean isValidEntity(final Entity entity) {
        if (AutoRemount.mc.field_71439_g.func_184218_aH()) {
            return false;
        }
        if (entity.func_70032_d((Entity)AutoRemount.mc.field_71439_g) > this.range.getValDouble()) {
            return false;
        }
        if (entity instanceof AbstractHorse) {
            final AbstractHorse horse = (AbstractHorse)entity;
            if (horse.func_70631_g_()) {
                return false;
            }
        }
        if (entity instanceof EntityDonkey) {
            final EntityDonkey entityDonkey = (EntityDonkey)entity;
            if (entityDonkey.func_70631_g_()) {
                return false;
            }
        }
        if (entity instanceof EntityMule) {
            final EntityMule entityDonkey2 = (EntityMule)entity;
            if (entityDonkey2.func_70631_g_()) {
                return false;
            }
        }
        if (entity instanceof EntityBoat && this.boat.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntityMinecart && this.Minecart.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntityHorse && this.horse.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntitySkeletonHorse && this.skeletonHorse.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntityDonkey && this.donkey.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntityMule && this.mule.getValBoolean()) {
            return true;
        }
        if (entity instanceof EntityPig && this.pig.getValBoolean()) {
            final EntityPig pig = (EntityPig)entity;
            return !pig.func_70631_g_() && pig.func_70901_n();
        }
        return entity instanceof EntityLlama && this.llama.getValBoolean();
    }
}
