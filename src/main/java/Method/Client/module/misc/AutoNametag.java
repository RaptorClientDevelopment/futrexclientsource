package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import Method.Client.utils.visual.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.entity.boss.*;

public class AutoNametag extends Module
{
    Setting Radius;
    Setting ReplaceOldNames;
    Setting AutoSwitch;
    Setting WithersOnly;
    
    public AutoNametag() {
        super("AutoNametag", 0, Category.MISC, "AutoNametag");
        this.Radius = Main.setmgr.add(new Setting("range", this, 4.0, 0.0, 10.0, true));
        this.ReplaceOldNames = Main.setmgr.add(new Setting("ReplaceOldNames", this, true));
        this.AutoSwitch = Main.setmgr.add(new Setting("AutoSwitch", this, true));
        this.WithersOnly = Main.setmgr.add(new Setting("WithersOnly ", this, true));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (AutoNametag.mc.field_71462_r != null) {
            return;
        }
        if (!(AutoNametag.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemNameTag)) {
            int i1 = -1;
            if (this.AutoSwitch.getValBoolean()) {
                for (int j = 0; j < 9; ++j) {
                    final ItemStack item = AutoNametag.mc.field_71439_g.field_71071_by.func_70301_a(j);
                    if (!item.func_190926_b()) {
                        if (item.func_77973_b() instanceof ItemNameTag) {
                            if (item.func_82837_s()) {
                                i1 = j;
                                AutoNametag.mc.field_71439_g.field_71071_by.field_70461_c = i1;
                                AutoNametag.mc.field_71442_b.func_78765_e();
                                break;
                            }
                        }
                    }
                }
            }
            if (i1 == -1) {
                return;
            }
        }
        final ItemStack name = AutoNametag.mc.field_71439_g.func_184614_ca();
        if (!name.func_82837_s()) {
            return;
        }
        final EntityLivingBase l_Entity = (EntityLivingBase)AutoNametag.mc.field_71441_e.field_72996_f.stream().filter(p_Entity -> this.IsValidEntity(p_Entity, name.func_82833_r())).map(p_Entity -> p_Entity).min(Comparator.comparing(p_Entity -> AutoNametag.mc.field_71439_g.func_70032_d(p_Entity))).orElse(null);
        if (l_Entity != null) {
            final double[] lPos = calculateLookAt(l_Entity.field_70165_t, l_Entity.field_70163_u, l_Entity.field_70161_v, (EntityPlayer)AutoNametag.mc.field_71439_g);
            ChatUtils.message(String.format("Gave %s the nametag of %s", l_Entity.func_70005_c_(), name.func_82833_r()));
            AutoNametag.mc.field_71439_g.field_70759_as = (float)lPos[0];
            Objects.requireNonNull(AutoNametag.mc.func_147114_u()).func_147297_a((Packet)new CPacketUseEntity((Entity)l_Entity, EnumHand.MAIN_HAND));
        }
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.field_70165_t - px;
        double diry = me.field_70163_u - py;
        double dirz = me.field_70161_v - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    private boolean IsValidEntity(final Entity entity, final String pName) {
        return entity instanceof EntityLivingBase && entity.func_70032_d((Entity)AutoNametag.mc.field_71439_g) <= this.Radius.getValDouble() && !(entity instanceof EntityPlayer) && (entity.func_95999_t().isEmpty() || this.ReplaceOldNames.getValBoolean()) && (!this.ReplaceOldNames.getValBoolean() || entity.func_95999_t().isEmpty() || !entity.func_70005_c_().equals(pName)) && (!this.WithersOnly.getValBoolean() || entity instanceof EntityWither);
    }
}
