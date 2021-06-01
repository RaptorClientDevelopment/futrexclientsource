package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import Method.Client.module.render.*;

public class HitEffects extends Module
{
    Setting Lightning;
    Setting Sounds;
    Setting Mode;
    Setting Yoffset;
    
    public HitEffects() {
        super("HitEffects", 0, Category.MISC, "Effects on Hit");
        this.Lightning = Main.setmgr.add(new Setting("Lightning", this, false));
        this.Sounds = Main.setmgr.add(new Setting("Sounds", this, false));
        this.Mode = Main.setmgr.add(new Setting("Mode", this, "SMOKE", new String[] { "HEART", "FIREWORK", "FLAME", "CLOUD", "WATER", "LAVA", "SLIME", "EXPLOSION", "MAGIC", "REDSTONE", "SWORD" }));
        this.Yoffset = Main.setmgr.add(new Setting("YPos offset", this, 0.0, 0.0, 2.0, false));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketUseEntity) {
            final CPacketUseEntity packet2 = (CPacketUseEntity)packet;
            if (packet2.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                final Entity entity = ((CPacketUseEntity)packet).func_149564_a((World)HitEffects.mc.field_71441_e);
                if (entity != null && !entity.field_70128_L) {
                    if (this.Lightning.getValBoolean()) {
                        HitEffects.mc.field_71441_e.func_72838_d((Entity)new EntityLightningBolt((World)HitEffects.mc.field_71441_e, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, true));
                    }
                    if (this.Sounds.getValBoolean()) {
                        final SoundEvent thunderSound = new SoundEvent(new ResourceLocation("minecraft", "entity.lightning.thunder"));
                        final SoundEvent lightningImpactSound = new SoundEvent(new ResourceLocation("minecraft", "entity.lightning.impact"));
                        HitEffects.mc.field_71441_e.func_184133_a((EntityPlayer)HitEffects.mc.field_71439_g, new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), thunderSound, SoundCategory.WEATHER, 1.0f, 1.0f);
                        HitEffects.mc.field_71441_e.func_184133_a((EntityPlayer)HitEffects.mc.field_71439_g, new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), lightningImpactSound, SoundCategory.WEATHER, 1.0f, 1.0f);
                    }
                    for (int i = 0; i < 5; ++i) {
                        Trail.Renderparticle((EntityLivingBase)entity, this.Mode.getValString(), this.Yoffset.getValDouble());
                    }
                }
            }
        }
        return true;
    }
}
