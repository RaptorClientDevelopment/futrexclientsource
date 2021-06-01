package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.util.*;

public class MobOwner extends Module
{
    Setting Speedh;
    Setting Jumph;
    Setting Hpm;
    public static LinkedHashMap<String, PlayerIdentity> identityCacheMap;
    
    public MobOwner() {
        super("MobOwner", 0, Category.RENDER, "MobOwner");
        this.Speedh = Main.setmgr.add(new Setting("Speed horse", this, false));
        this.Jumph = Main.setmgr.add(new Setting("Jump Horse", this, false));
        this.Hpm = Main.setmgr.add(new Setting("Hp", this, false));
    }
    
    public static PlayerIdentity getPlayerIdentity(final String UUID) {
        if (MobOwner.identityCacheMap.containsKey(UUID)) {
            return MobOwner.identityCacheMap.get(UUID);
        }
        return new PlayerIdentity(UUID);
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Entity entity : MobOwner.mc.field_71441_e.func_72910_y()) {
            if (entity instanceof EntityTameable) {
                final EntityTameable tameableEntity = (EntityTameable)entity;
                if (tameableEntity.func_70909_n() && tameableEntity.func_184753_b() != null) {
                    tameableEntity.func_174805_g(true);
                    final String Hp = this.Hpm.getValBoolean() ? ("\n" + ((EntityTameable)entity).func_110143_aJ()) : "";
                    final PlayerIdentity identity = getPlayerIdentity(tameableEntity.func_184753_b().toString());
                    tameableEntity.func_96094_a("Owned by " + identity.getDisplayName() + Hp);
                }
            }
            if (entity instanceof AbstractHorse) {
                final AbstractHorse tameableEntity2 = (AbstractHorse)entity;
                if (!tameableEntity2.func_110248_bS() || tameableEntity2.func_184780_dh() == null) {
                    continue;
                }
                final String Speed = this.Speedh.getValBoolean() ? (" Speed: " + ((AbstractHorse)entity).func_70689_ay() * 43.17) : "";
                final String Hp2 = this.Hpm.getValBoolean() ? (" HP: " + ((AbstractHorse)entity).func_110143_aJ()) : "";
                final String Jump = this.Jumph.getValBoolean() ? (" Jump: " + (-0.1817584952 * Math.pow(((AbstractHorse)entity).func_110215_cj(), 3.0) + 3.689713992 * Math.pow(((AbstractHorse)entity).func_110215_cj(), 2.0) + 2.128599134 * ((AbstractHorse)entity).func_110215_cj() - 0.343930367)) : "";
                tameableEntity2.func_174805_g(true);
                final PlayerIdentity identity2 = getPlayerIdentity(tameableEntity2.func_184780_dh().toString());
                tameableEntity2.func_96094_a("Owned by " + identity2.getDisplayName() + Speed + Jump + Hp2);
            }
        }
        super.onRenderWorldLast(event);
    }
    
    static {
        MobOwner.identityCacheMap = new LinkedHashMap<String, PlayerIdentity>();
    }
}
