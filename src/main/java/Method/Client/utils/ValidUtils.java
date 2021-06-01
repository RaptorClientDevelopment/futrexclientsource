package Method.Client.utils;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import Method.Client.utils.system.*;
import java.util.*;
import net.minecraft.entity.*;
import Method.Client.module.combat.*;
import Method.Client.module.*;
import Method.Client.managers.*;
import Method.Client.module.player.*;

public class ValidUtils
{
    public static boolean pingCheck(final EntityLivingBase entity) {
        if (ModuleManager.getModuleByName("AntiBot").isToggled() && entity instanceof EntityPlayer) {
            Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175102_a(entity.func_110124_au());
            return Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175102_a(entity.func_110124_au()).func_178853_c() <= 5;
        }
        return true;
    }
    
    public static boolean isInAttackFOV(final EntityLivingBase entity, final int fov) {
        return Utils.getDistanceFromMouse(entity) <= fov;
    }
    
    public static boolean isClosest(final EntityLivingBase entity, final EntityLivingBase entityPriority) {
        return entityPriority == null || Wrapper.INSTANCE.player().func_70032_d((Entity)entity) < Wrapper.INSTANCE.player().func_70032_d((Entity)entityPriority);
    }
    
    public static boolean isLowHealth(final EntityLivingBase entity, final EntityLivingBase entityPriority) {
        return entityPriority == null || entity.func_110143_aJ() < entityPriority.func_110143_aJ();
    }
    
    public static boolean isInAttackRange(final EntityLivingBase entity, final float range) {
        return entity.func_70032_d((Entity)Wrapper.INSTANCE.player()) <= range;
    }
    
    public static boolean isBot(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final Module module = ModuleManager.getModuleByName("AntiBot");
            return module.isToggled() && AntiBot.isBot(player);
        }
        return false;
    }
    
    public static boolean isFriendEnemy(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final String ID = Utils.getPlayerName(player);
            return FriendManager.friendsList.contains(ID);
        }
        return true;
    }
    
    public static boolean isNoScreen() {
        return !ModuleManager.getModuleByName("NoEffect").isToggled() || (NoEffect.NoScreenEvents.getValBoolean() && !Utils.checkScreen());
    }
}
