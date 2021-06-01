package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;

public class Trajectories extends Module
{
    public final List<Bpos> Pos;
    Setting FindEpearl;
    Setting ChatPrint;
    Setting RenderTime;
    Setting Mode;
    Setting LineWidth;
    Setting Color;
    Setting skeleton;
    
    public Trajectories() {
        super("Trajectories", 0, Category.RENDER, "Trajectories");
        this.Pos = new ArrayList<Bpos>();
        this.FindEpearl = Main.setmgr.add(new Setting("Follow Pearl", this, true));
        this.ChatPrint = Main.setmgr.add(new Setting("ChatPrint", this, false, this.FindEpearl, 3));
        this.RenderTime = Main.setmgr.add(new Setting("RenderTime", this, 5.0, 0.0, 35.0, false, this.FindEpearl, 4));
        this.Mode = Main.setmgr.add(new Setting("Mode", this, "Xspot", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.Color = Main.setmgr.add(new Setting("Color", this, 0.22, 1.0, 0.6, 0.65));
        this.skeleton = Main.setmgr.add(new Setting("Skeleton", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.FindEpearl.getValBoolean()) {
            for (final Entity entity : Trajectories.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityEnderPearl) {
                    final EntityEnderPearl e = (EntityEnderPearl)entity;
                    boolean notfound = true;
                    for (final Bpos po : this.Pos) {
                        if (po.getUuid().equals(e.func_110124_au())) {
                            notfound = false;
                            break;
                        }
                    }
                    if (notfound) {
                        this.Pos.add(new Bpos(new ArrayList<Vec3d>(Collections.singletonList(e.func_174791_d())), e.func_110124_au(), System.currentTimeMillis()));
                        if (!this.ChatPrint.getValBoolean()) {
                            continue;
                        }
                        ChatUtils.message(e.field_181555_c.toString() + " Threw a pearl!");
                    }
                    else {
                        for (final Bpos po : this.Pos) {
                            if (po.uuid.equals(e.func_110124_au())) {
                                po.vec3ds.add(e.func_174791_d());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean itemcheck(final Item item) {
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Entity entity : Trajectories.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase livingBase = (EntityLivingBase)entity;
                if (livingBase instanceof EntitySkeleton && !this.skeleton.getValBoolean()) {
                    return;
                }
                if (!this.itemcheck(livingBase.func_184614_ca().func_77973_b()) && !this.itemcheck(livingBase.func_184592_cb().func_77973_b())) {
                    continue;
                }
                livingBase.func_184607_cu();
                final boolean usingBow = livingBase.func_184607_cu().func_77973_b() instanceof ItemBow;
                double arrowPosX = livingBase.field_70142_S + (livingBase.field_70165_t - livingBase.field_70142_S) * event.getPartialTicks() - MathHelper.func_76134_b((float)Math.toRadians(livingBase.field_70177_z)) * 0.16f;
                double arrowPosY = livingBase.field_70137_T + (livingBase.field_70163_u - livingBase.field_70137_T) * event.getPartialTicks() + livingBase.func_70047_e() - 0.1;
                double arrowPosZ = livingBase.field_70136_U + (livingBase.field_70161_v - livingBase.field_70136_U) * event.getPartialTicks() - MathHelper.func_76126_a((float)Math.toRadians(livingBase.field_70177_z)) * 0.16f;
                final float arrowMotionFactor = usingBow ? 1.0f : 0.4f;
                final float yaw = (float)Math.toRadians(livingBase.field_70177_z);
                final float pitch = (float)Math.toRadians(livingBase.field_70125_A);
                float arrowMotionX = -MathHelper.func_76126_a(yaw) * MathHelper.func_76134_b(pitch) * arrowMotionFactor;
                float arrowMotionY = -MathHelper.func_76126_a(pitch) * arrowMotionFactor;
                float arrowMotionZ = MathHelper.func_76134_b(yaw) * MathHelper.func_76134_b(pitch) * arrowMotionFactor;
                final double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
                double bowPower = 1.5;
                if (usingBow) {
                    bowPower = (72000 - livingBase.func_184605_cv()) / 20.0f;
                    bowPower = (bowPower * bowPower + bowPower * 2.0) / 3.0;
                    bowPower = ((bowPower > 1.0 || bowPower <= 0.10000000149011612) ? 3.0 : (bowPower * 3.0));
                }
                arrowMotionX = (float)(arrowMotionX / arrowMotion * bowPower);
                arrowMotionY = (float)(arrowMotionY / arrowMotion * bowPower);
                arrowMotionZ = (float)(arrowMotionZ / arrowMotion * bowPower);
                final double gravity = usingBow ? 0.05 : ((livingBase.func_184614_ca().func_77973_b() instanceof ItemPotion || livingBase.func_184592_cb().func_77973_b() instanceof ItemPotion) ? 0.4 : ((livingBase.func_184614_ca().func_77973_b() instanceof ItemFishingRod || livingBase.func_184592_cb().func_77973_b() instanceof ItemFishingRod) ? 0.15 : 0.03));
                final Vec3d playerVector = new Vec3d(livingBase.field_70165_t, livingBase.field_70163_u + livingBase.func_70047_e(), livingBase.field_70161_v);
                RenderUtils.OpenGl();
                GL11.glEnable(32925);
                GlStateManager.func_187441_d((float)this.LineWidth.getValDouble());
                ColorUtils.glColor(this.Color.getcolor());
                GlStateManager.func_187447_r(3);
                final RenderManager renderManager = Trajectories.mc.func_175598_ae();
                for (int i = 0; i < 1000; ++i) {
                    GL11.glVertex3d(arrowPosX - renderManager.field_78730_l, arrowPosY - renderManager.field_78731_m, arrowPosZ - renderManager.field_78728_n);
                    arrowPosX += arrowMotionX * 0.1;
                    arrowPosY += arrowMotionY * 0.1;
                    arrowPosZ += arrowMotionZ * 0.1;
                    arrowMotionX *= 0.999;
                    arrowMotionY = (float)(arrowMotionY * 0.999 - gravity * 0.1);
                    arrowMotionZ *= 0.999;
                    if (Trajectories.mc.field_71441_e.func_72933_a(playerVector, new Vec3d(arrowPosX, arrowPosY, arrowPosZ)) != null) {
                        break;
                    }
                }
                GlStateManager.func_187437_J();
                final double renderX = arrowPosX - renderManager.field_78730_l;
                final double renderY = arrowPosY - renderManager.field_78731_m;
                final double renderZ = arrowPosZ - renderManager.field_78728_n;
                final AxisAlignedBB bb = new AxisAlignedBB(renderX - 0.5, renderY, renderZ - 0.5, renderX + 0.5, renderY + 0.5, renderZ + 0.5);
                RenderUtils.RenderBlock(this.Mode.getValString(), bb, this.Color.getcolor(), this.LineWidth.getValDouble());
                RenderUtils.ReleaseGl();
                GL11.glDisable(32925);
            }
        }
        if (this.FindEpearl.getValBoolean()) {
            RenderUtils.OpenGl();
            GlStateManager.func_187441_d((float)this.LineWidth.getValDouble() * 3.0f);
            final List<Bpos> toremove = new ArrayList<Bpos>();
            for (final Bpos po : this.Pos) {
                if (po.getaLong() + this.RenderTime.getValDouble() * 1000.0 < System.currentTimeMillis()) {
                    toremove.add(po);
                }
            }
            this.Pos.removeAll(toremove);
            if (!this.Pos.isEmpty()) {
                for (final Bpos po : this.Pos) {
                    GlStateManager.func_187447_r(1);
                    ColorUtils.glColor(this.Color.getcolor());
                    final double[] rPos = this.rPos();
                    Vec3d priorpoint = po.getVec3ds().get(0);
                    for (final Vec3d vec3d : po.getVec3ds()) {
                        GL11.glVertex3d(vec3d.field_72450_a - rPos[0], vec3d.field_72448_b - rPos[1], vec3d.field_72449_c - rPos[2]);
                        GL11.glVertex3d(priorpoint.field_72450_a - rPos[0], priorpoint.field_72448_b - rPos[1], priorpoint.field_72449_c - rPos[2]);
                        priorpoint = vec3d;
                    }
                    GlStateManager.func_187437_J();
                }
            }
            RenderUtils.ReleaseGl();
        }
        super.onRenderWorldLast(event);
    }
    
    private double[] rPos() {
        try {
            final double renderPosX = Trajectories.mc.func_175598_ae().field_78730_l;
            final double renderPosY = Trajectories.mc.func_175598_ae().field_78731_m;
            final double renderPosZ = Trajectories.mc.func_175598_ae().field_78728_n;
            return new double[] { renderPosX, renderPosY, renderPosZ };
        }
        catch (Exception e) {
            return new double[] { 0.0, 0.0, 0.0 };
        }
    }
    
    static class Bpos
    {
        private final List<Vec3d> vec3ds;
        private final UUID uuid;
        private final long aLong;
        
        public List<Vec3d> getVec3ds() {
            return this.vec3ds;
        }
        
        public Bpos(final List<Vec3d> vec3ds, final UUID uuid, final long l) {
            this.vec3ds = vec3ds;
            this.uuid = uuid;
            this.aLong = l;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        public long getaLong() {
            return this.aLong;
        }
    }
}
