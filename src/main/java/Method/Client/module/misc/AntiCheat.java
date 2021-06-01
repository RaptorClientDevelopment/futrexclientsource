package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import Method.Client.managers.*;
import net.minecraft.entity.*;
import Method.Client.utils.visual.*;
import Method.Client.utils.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class AntiCheat extends Module
{
    Setting Experimental;
    
    public AntiCheat() {
        super("CheatDetect", 0, Category.MISC, "AntiCheat For others!");
        this.Experimental = Main.setmgr.add(new Setting("Experimental", this, true));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (AntiCheat.mc.field_71439_g.field_70173_aa % 5 == 0) {
            for (final Entity entityplayer : AntiCheat.mc.field_71441_e.field_72996_f) {
                if (entityplayer instanceof EntityPlayer && !entityplayer.func_70005_c_().equalsIgnoreCase(AntiCheat.mc.field_71439_g.func_70005_c_()) && !FriendManager.isFriend(entityplayer.func_70005_c_())) {
                    final EntityLivingBase entity = (EntityLivingBase)entityplayer;
                    if (entity.field_70173_aa <= 40) {
                        continue;
                    }
                    if (entity.field_70125_A > 90.0f || entity.field_70125_A < -90.0f) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Invalid Head Pitch ");
                    }
                    if (Math.abs(Math.abs(entity.field_70163_u) - Math.abs(entity.field_70167_r)) > 1.0 && !entity.field_70122_E && !entity.field_70124_G && entity.field_70163_u > entity.field_70167_r && !entity.func_70617_f_() && !entity.func_70090_H()) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Ascension ");
                    }
                    if (Utils.isMoving((Entity)entity) && ((entity.field_70726_aT == entity.field_70727_aS && entity.field_70726_aT == (int)entity.field_70726_aT) || (entity.field_70177_z == entity.field_70126_B && entity.field_70177_z == (int)entity.field_70126_B))) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged  Yaw/Pitch [AA]");
                    }
                    if (!this.Experimental.getValBoolean()) {
                        continue;
                    }
                    final double yawDelta = Math.abs(Math.abs(entity.field_70177_z) - Math.abs(entity.field_70126_B));
                    final double pitchDelta = Math.abs(Math.abs(entity.field_70125_A) - Math.abs(entity.field_70127_C));
                    if (entity.field_70177_z == Math.round(entity.field_70177_z) && entity.field_70177_z != 0.0f && yawDelta > 10.0) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged KillAura [AA]");
                    }
                    if (entity.field_70125_A == Math.round(entity.field_70125_A) && entity.field_70125_A != 90.0f && entity.field_70125_A != -90.0f && pitchDelta > 10.0) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged KillAura [AB]");
                    }
                    if (entity.func_70051_ag() && Utils.isMoving((Entity)entity) && entity.field_191988_bg < 0.0f) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Omni-Sprint ");
                    }
                    if (entity.func_184218_aH() && entity.func_184187_bx() instanceof EntityBoat && !entity.field_70171_ac) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Boat-Fly ");
                    }
                    if (entity.func_191953_am() && entity.func_70051_ag()) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Jesus ");
                    }
                    if (entity.field_70138_W > 1.0f) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Step ");
                    }
                    if (Math.abs(entity.field_70165_t - entity.field_70142_S) > 0.42 || Math.abs(entity.field_70161_v - entity.field_70136_U) > 0.42) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Speed ");
                    }
                    if (entity.func_184585_cz() && (Math.abs(entity.field_70165_t - entity.field_70142_S) > 0.3 || Math.abs(entity.field_70161_v - entity.field_70136_U) > 0.3)) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged NoSlow ");
                    }
                    if (!entity.func_184613_cA() && !AntiCheat.mc.field_71441_e.func_72829_c(entity.func_174813_aQ().func_72317_d(0.0, -1.1, 0.0)) && entity.field_70167_r < entity.field_70163_u) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged Flying ");
                    }
                    if (entity.field_70172_ad > 6 && entity.field_70172_ad < 12 && entity.field_70142_S == entity.field_70165_t && entity.field_70161_v == entity.field_70136_U && !AntiCheat.mc.field_71441_e.func_72829_c(entity.func_174813_aQ().func_72321_a(0.05, 0.0, 0.05))) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged KnockBack ");
                    }
                    if (entity.field_70172_ad > 6 && entity.field_70172_ad < 12 && entity.field_70137_T == entity.field_70163_u) {
                        ChatUtils.message(entity.func_70005_c_() + " flagged KnockBack ");
                    }
                    if (!entity.field_70122_E || !entity.func_184613_cA()) {
                        continue;
                    }
                    ChatUtils.message(entity.func_70005_c_() + " flagged Ground Elytra ");
                }
            }
        }
    }
}
