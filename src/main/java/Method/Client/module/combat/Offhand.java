package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import Method.Client.module.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class Offhand extends Module
{
    Setting switch_mode;
    Setting totem_switch;
    Setting gapple_in_hole;
    Setting gapple_hole_hp;
    Setting delay;
    boolean switching;
    int last_slot;
    
    public Offhand() {
        super("Offhand", 0, Category.COMBAT, "Offhand Item");
        this.switch_mode = Main.setmgr.add(new Setting("switch_mode", this, "Totem", new String[] { "Totem", "Crystal", "Gapple" }));
        this.totem_switch = Main.setmgr.add(new Setting("totem_switch", this, 16.0, 0.0, 36.0, true));
        this.gapple_in_hole = Main.setmgr.add(new Setting("gapple_in_hole", this, true));
        this.gapple_hole_hp = Main.setmgr.add(new Setting("gapple_hole_hp", this, 8.0, 0.0, 36.0, true));
        this.delay = Main.setmgr.add(new Setting("delay", this, true));
        this.switching = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Offhand.mc.field_71462_r == null || Offhand.mc.field_71462_r instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            final float hp = Offhand.mc.field_71439_g.func_110143_aJ() + Offhand.mc.field_71439_g.func_110139_bj();
            if (hp <= this.totem_switch.getValDouble()) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.getValBoolean() ? 1 : 0);
                return;
            }
            if (this.switch_mode.getValString().equalsIgnoreCase("Crystal") && ModuleManager.getModuleByName("CrystalAura").isToggled()) {
                this.swap_items(this.get_item_slot(Items.field_185158_cP), 0);
                return;
            }
            if (this.gapple_in_hole.getValBoolean() && hp > this.gapple_hole_hp.getValDouble() && this.is_in_hole()) {
                this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.getValBoolean() ? 1 : 0);
                return;
            }
            if (this.switch_mode.getValString().equalsIgnoreCase("Totem")) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.getValBoolean() ? 1 : 0);
                return;
            }
            if (this.switch_mode.getValString().equalsIgnoreCase("Gapple")) {
                this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.getValBoolean() ? 1 : 0);
                return;
            }
            if (this.switch_mode.getValString().equalsIgnoreCase("Crystal") && !ModuleManager.getModuleByName("CrystalAura").isToggled()) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), 0);
                return;
            }
            if (Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190931_a) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.getValBoolean() ? 1 : 0);
            }
        }
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            Offhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
            Offhand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
            Offhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
        }
        if (step == 1) {
            Offhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            Offhand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
            Offhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.field_71439_g);
            this.switching = false;
        }
        Offhand.mc.field_71442_b.func_78765_e();
    }
    
    private boolean is_in_hole() {
        final BlockPos player_block = new BlockPos(Math.floor(Offhand.mc.field_71439_g.field_70165_t), Math.floor(Offhand.mc.field_71439_g.field_70163_u), Math.floor(Offhand.mc.field_71439_g.field_70161_v));
        return Offhand.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && Offhand.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && Offhand.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && Offhand.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a;
    }
    
    private int get_item_slot(final Item input) {
        if (input == Offhand.mc.field_71439_g.func_184592_cb().func_77973_b()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = Offhand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.field_151153_ao) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
