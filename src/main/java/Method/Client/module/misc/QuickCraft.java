package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class QuickCraft extends Module
{
    public QuickCraft() {
        super("QuickCraft", 0, Category.MISC, "Quick Craft");
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN && packet instanceof SPacketSetSlot && ((SPacketSetSlot)packet).func_149173_d() == 0 && ((SPacketSetSlot)packet).func_149174_e().func_77973_b() != Items.field_190931_a && (QuickCraft.mc.field_71462_r instanceof GuiInventory || QuickCraft.mc.field_71462_r instanceof GuiCrafting)) {
            QuickCraft.mc.field_71442_b.func_187098_a(QuickCraft.mc.field_71439_g.field_71070_bA.field_75152_c, 0, 0, ClickType.QUICK_MOVE, (EntityPlayer)QuickCraft.mc.field_71439_g);
            QuickCraft.mc.field_71442_b.func_78765_e();
        }
        return true;
    }
}
