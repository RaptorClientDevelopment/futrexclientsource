package Method.Client.module.combat;

import Method.Client.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.system.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import Method.Client.utils.*;
import org.lwjgl.input.*;
import Method.Client.managers.*;
import net.minecraft.entity.*;

public class InteractClick extends Module
{
    public InteractClick() {
        super("InteractClick", 0, Category.COMBAT, "InteractClick");
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final RayTraceResult object = Wrapper.INSTANCE.mc().field_71476_x;
        if (object == null) {
            return;
        }
        if (object.field_72313_a == RayTraceResult.Type.ENTITY) {
            final Entity entity = object.field_72308_g;
            if (entity instanceof EntityPlayer && !InteractClick.mc.field_71439_g.field_70128_L && InteractClick.mc.field_71439_g.func_70685_l(entity)) {
                final EntityPlayer player = (EntityPlayer)entity;
                final String ID = Utils.getPlayerName(player);
                if (Mouse.isButtonDown(2) && Wrapper.INSTANCE.mc().field_71462_r == null) {
                    FriendManager.addFriend(ID);
                }
                else if (Mouse.isButtonDown(1) && Wrapper.INSTANCE.mc().field_71462_r == null) {
                    FriendManager.removeFriend(ID);
                }
            }
        }
        super.onClientTick(event);
    }
}
