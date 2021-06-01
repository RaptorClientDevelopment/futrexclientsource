package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.visual.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import Method.Client.utils.system.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;

public class WallHack extends Module
{
    Setting players;
    Setting mobs;
    Setting Barrier;
    TimerUtils timer;
    
    public WallHack() {
        super("WallHack", 0, Category.RENDER, "WallHack");
        this.players = Main.setmgr.add(new Setting("players", this, false));
        this.mobs = Main.setmgr.add(new Setting("mobs", this, false));
        this.Barrier = Main.setmgr.add(new Setting("Barrier", this, false));
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onEnable() {
        Executer.init();
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.timer.isDelay(4500L)) {
            if (this.Barrier.getValBoolean()) {
                Vec3i playerPos;
                int x;
                int z;
                int y;
                Executer.execute(() -> {
                    for (playerPos = new Vec3i(WallHack.mc.field_71439_g.field_70165_t, WallHack.mc.field_71439_g.field_70163_u, WallHack.mc.field_71439_g.field_70161_v), x = playerPos.func_177958_n() - 10; x < playerPos.func_177958_n() + 10; ++x) {
                        for (z = playerPos.func_177952_p() - 10; z < playerPos.func_177952_p() + 10; ++z) {
                            for (y = playerPos.func_177956_o() + 6; y > playerPos.func_177956_o() - 6; --y) {
                                if (WallHack.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c() == Blocks.field_180401_cv) {
                                    WallHack.mc.field_71441_e.func_175688_a(EnumParticleTypes.BARRIER, x + 0.5, y + 0.5, z + 0.5, 0.0, 0.0, 0.0, new int[0]);
                                }
                            }
                        }
                    }
                    return;
                });
            }
            this.timer.setLastMS();
        }
        GlStateManager.func_179086_m(256);
        RenderHelper.func_74519_b();
        for (final Object object : WallHack.mc.field_71441_e.field_72996_f) {
            final Entity entity = (Entity)object;
            this.render(entity, event.getPartialTicks());
        }
        super.onRenderWorldLast(event);
    }
    
    void render(final Entity entity, final float ticks) {
        final Entity ent = this.getEntity(entity);
        if (ent == null || ent == WallHack.mc.field_71439_g) {
            return;
        }
        if (ent == WallHack.mc.func_175606_aa() && Wrapper.INSTANCE.mcSettings().field_74320_O == 0) {
            return;
        }
        WallHack.mc.field_71460_t.func_175072_h();
        WallHack.mc.func_175598_ae().func_188388_a(ent, ticks, false);
        WallHack.mc.field_71460_t.func_180436_i();
    }
    
    Entity getEntity(final Entity e) {
        Entity entity = null;
        if (this.players.getValBoolean() && e instanceof EntityPlayer) {
            entity = e;
        }
        else if (this.mobs.getValBoolean() && e instanceof EntityLiving) {
            entity = e;
        }
        else if (e instanceof EntityItem) {
            entity = e;
        }
        else if (e instanceof EntityArrow) {
            entity = e;
        }
        return entity;
    }
}
