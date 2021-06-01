package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.init.*;
import Method.Client.utils.visual.*;
import net.minecraftforge.fml.common.eventhandler.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class AutoTotem extends Module
{
    private final int OFFHAND_SLOT = 45;
    Setting allowGui;
    Setting health;
    Setting needheal;
    Setting predict;
    private boolean predicted;
    
    public AutoTotem() {
        super("Auto Totem", 0, Category.COMBAT, "Auto Totem");
        this.allowGui = Main.setmgr.add(new Setting("Use in Gui", this, true));
        this.health = Main.setmgr.add(new Setting("health for switch", this, 10.0, 0.0, 35.0, true));
        this.needheal = Main.setmgr.add(new Setting("Use Health", this, false));
        this.predict = Main.setmgr.add(new Setting("Predict", this, false));
        this.predicted = false;
    }
    
    @SubscribeEvent
    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (getOffhand().func_77973_b() == Items.field_190929_cY) {
            return;
        }
        if (AutoTotem.mc.field_71462_r != null && !this.allowGui.getValBoolean()) {
            return;
        }
        if (this.needheal.getValBoolean()) {
            if (AutoTotem.mc.field_71439_g.func_110143_aJ() < this.health.getValDouble()) {
                findItem().ifPresent(slot -> {
                    this.invPickup(slot);
                    this.invPickup(45);
                    return;
                });
            }
        }
        else if (this.predict.getValBoolean()) {
            if (this.predicted) {
                findItem().ifPresent(slot -> {
                    this.invPickup(slot);
                    this.invPickup(45);
                    return;
                });
            }
        }
        else {
            findItem().ifPresent(slot -> {
                this.invPickup(slot);
                this.invPickup(45);
                return;
            });
        }
        if (this.predicted) {
            ChatUtils.warning("Predicted Totem!");
            this.predicted = false;
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.predict.getValBoolean() && side == Connection.Side.IN && packet instanceof SPacketUpdateHealth) {
            final SPacketUpdateHealth packet2 = (SPacketUpdateHealth)packet;
            if (packet2.func_149332_c() <= 0.0f) {
                this.predicted = true;
            }
        }
        return true;
    }
    
    private void invPickup(final int slot) {
        AutoTotem.MC.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.MC.field_71439_g);
    }
    
    private static OptionalInt findItem() {
        for (int i = 9; i <= 44; ++i) {
            if (AutoTotem.MC.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b() == Items.field_190929_cY) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }
    
    private static ItemStack getOffhand() {
        return AutoTotem.MC.field_71439_g.func_184582_a(EntityEquipmentSlot.OFFHAND);
    }
}
