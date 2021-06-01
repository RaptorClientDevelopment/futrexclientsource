package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.math.*;
import Method.Client.module.*;
import Method.Client.module.combat.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.client.resources.*;
import net.minecraft.init.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;

public class NameTags extends Module
{
    public static boolean toggled;
    public Setting Scale;
    public Setting armor;
    public Setting Background;
    public Setting TextColor;
    public Setting ScaleMode;
    public Setting Gamemode;
    public Setting Player;
    public Setting Ping;
    public Setting Mob;
    public Setting Hostile;
    public Setting Totem;
    public Setting Healthmode;
    
    public NameTags() {
        super("NameTags", 0, Category.RENDER, "NameTags");
        this.Scale = Main.setmgr.add(new Setting("Scale", this, 2.0, 0.0, 4.0, false));
        this.armor = Main.setmgr.add(new Setting("armor", this, true));
        this.Background = Main.setmgr.add(new Setting("Background", this, 0.0, 1.0, 0.01, 0.22));
        this.TextColor = Main.setmgr.add(new Setting("Name", this, 0.0, 1.0, 1.0, 1.0));
        this.ScaleMode = Main.setmgr.add(new Setting("Armor Mode", this, "H", new String[] { "H", "V" }));
        this.Gamemode = Main.setmgr.add(new Setting("Gamemode", this, true));
        this.Player = Main.setmgr.add(new Setting("Player", this, true));
        this.Ping = Main.setmgr.add(new Setting("Ping", this, true));
        this.Mob = Main.setmgr.add(new Setting("Mob", this, false));
        this.Hostile = Main.setmgr.add(new Setting("Hostile", this, false));
        this.Totem = Main.setmgr.add(new Setting("Totem Pops", this, false));
        this.Healthmode = Main.setmgr.add(new Setting("Health Mode", this, "Number", new String[] { "Number", "Bar" }));
    }
    
    @Override
    public void onDisable() {
        NameTags.toggled = false;
    }
    
    @Override
    public void onEnable() {
        NameTags.toggled = true;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final Entity object : NameTags.mc.field_71441_e.field_72996_f) {
            if ((this.Player.getValBoolean() && object instanceof EntityPlayer) || (this.Mob.getValBoolean() && object instanceof IAnimals) || (this.Hostile.getValBoolean() && object instanceof IMob)) {
                assert object instanceof EntityLivingBase;
                if (object == NameTags.mc.field_71439_g) {
                    continue;
                }
                this.Runnametag((EntityLivingBase)object);
            }
        }
    }
    
    private void Runnametag(final EntityLivingBase e) {
        final double scale = Math.max(this.Scale.getValDouble() * (NameTags.mc.field_71439_g.func_70032_d((Entity)e) / 20.0f), 2.0);
        final StringBuilder healthBuilder = new StringBuilder();
        for (int i = 0; i < e.func_110143_aJ(); ++i) {
            healthBuilder.append(ChatFormatting.GREEN + "|");
        }
        final StringBuilder health = new StringBuilder(healthBuilder.toString());
        for (int j = 0; j < MathHelper.func_76131_a(e.func_110139_bj(), 0.0f, e.func_110138_aP() - e.func_110143_aJ()); ++j) {
            health.append(ChatFormatting.RED + "|");
        }
        for (int j = 0; j < e.func_110138_aP() - (e.func_110143_aJ() + e.func_110139_bj()); ++j) {
            health.append(ChatFormatting.YELLOW + "|");
        }
        if (e.func_110139_bj() - (e.func_110138_aP() - e.func_110143_aJ()) > 0.0f) {
            health.append(ChatFormatting.BLUE + " +").append((int)(e.func_110139_bj() - (e.func_110138_aP() - e.func_110143_aJ())));
        }
        String tag = "";
        if (this.Totem.getValBoolean() && e instanceof EntityPlayer && ModuleManager.getModuleByName("TotemPop").isToggled()) {
            tag = tag + " T:" + TotemPop.getpops((Entity)e) + " ";
        }
        if (this.Ping.getValBoolean() && e instanceof EntityPlayer) {
            try {
                tag = tag + " " + (int)MathHelper.func_76131_a((float)Objects.requireNonNull(NameTags.mc.func_147114_u()).func_175102_a(e.func_110124_au()).func_178853_c(), 1.0f, 300.0f) + " P ";
            }
            catch (NullPointerException ex) {}
        }
        if (this.Gamemode.getValBoolean() && e instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)e;
            if (entityPlayer.func_184812_l_()) {
                tag += "[C]";
            }
            if (entityPlayer.func_175149_v()) {
                tag += " [I]";
            }
            if (!entityPlayer.func_175142_cm() && !entityPlayer.func_175149_v()) {
                tag += " [A]";
            }
            if (!entityPlayer.func_184812_l_() && !entityPlayer.func_175149_v() && entityPlayer.func_175142_cm()) {
                tag += " [S]";
            }
        }
        if (this.Healthmode.getValString().equalsIgnoreCase("Number")) {
            this.Processtext(e.func_70005_c_() + " [" + (int)(e.func_110143_aJ() + e.func_110139_bj()) + "/" + (int)e.func_110138_aP() + "]" + tag, NameTags.mc.field_71466_p.func_78256_a(e.func_70005_c_() + tag + "[]") / 2, this.TextColor, (Entity)e, e.field_70131_O + 0.5 * scale, scale);
        }
        else if (this.Healthmode.getValString().equalsIgnoreCase("Bar")) {
            this.Processtext(e.func_70005_c_() + tag, NameTags.mc.field_71466_p.func_78256_a(e.func_70005_c_() + tag) / 2, this.TextColor, (Entity)e, e.field_70131_O + 0.5 * scale, scale);
            this.Processtext(health.toString(), NameTags.mc.field_71466_p.func_78256_a(health.toString()) / 2, this.TextColor, (Entity)e, e.field_70131_O + 0.75 * scale, scale);
        }
        if (this.armor.getValBoolean()) {
            double c = 0.0;
            final double higher = this.Healthmode.getValString().equalsIgnoreCase("Bar") ? 0.25 : 0.0;
            if (this.ScaleMode.getValString().equalsIgnoreCase("H")) {
                drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, -2.5, 0.0, scale, e.func_184614_ca());
                drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, 2.5, 0.0, scale, e.func_184592_cb());
                for (final ItemStack k : e.func_184193_aE()) {
                    drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, c + 1.5, 0.0, scale, k);
                    --c;
                }
            }
            else if (this.ScaleMode.getValString().equalsIgnoreCase("V")) {
                drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, -1.25, 0.0, scale, e.func_184614_ca());
                drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, 1.25, 0.0, scale, e.func_184592_cb());
                for (final ItemStack k : e.func_184193_aE()) {
                    if (k.func_190916_E() < 1) {
                        continue;
                    }
                    drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + (0.75 + higher) * scale, e.field_70161_v, 0.0, c, scale, k);
                    ++c;
                }
            }
        }
    }
    
    private void Processtext(final String s, final int stringWidth, final Setting getcolor, final Entity entity, final double rofl, final double scale) {
        try {
            glSetup(entity.field_70165_t, entity.field_70163_u + rofl, entity.field_70161_v);
            GlStateManager.func_179139_a(-0.025 * scale, -0.025 * scale, 0.025 * scale);
            GlStateManager.func_179090_x();
            final Tessellator tessellator = Tessellator.func_178181_a();
            final BufferBuilder bufferbuilder = tessellator.func_178180_c();
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            bufferbuilder.func_181662_b((double)(-stringWidth - 1), -1.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
            bufferbuilder.func_181662_b((double)(-stringWidth - 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
            bufferbuilder.func_181662_b((double)(stringWidth + 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
            bufferbuilder.func_181662_b((double)(stringWidth + 1), -1.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
            tessellator.func_78381_a();
            GlStateManager.func_179098_w();
            NameTags.mc.field_71466_p.func_175063_a(s, (float)(-stringWidth), 0.0f, getcolor.getcolor());
            glCleanup();
        }
        catch (Exception ex) {}
    }
    
    public static void drawItem(final double x, final double y, final double z, final double offX, final double offY, final double scale, final ItemStack item) {
        glSetup(x, y, z);
        GlStateManager.func_179139_a(0.4 * scale, 0.4 * scale, 0.0);
        GlStateManager.func_179137_b(offX, offY, 0.0);
        NameTags.mc.field_175620_Y.func_187462_a((EntityLivingBase)NameTags.mc.field_71439_g, item, ItemCameraTransforms.TransformType.NONE, false);
        GlStateManager.func_179098_w();
        GlStateManager.func_179140_f();
        GlStateManager.func_179152_a(-0.05f, -0.05f, 0.0f);
        try {
            if (item.func_190916_E() > 0) {
                final int w = NameTags.mc.field_71466_p.func_78256_a("x" + item.func_190916_E()) / 2;
                NameTags.mc.field_71466_p.func_175063_a("x" + item.func_190916_E(), (float)(7 - w), 5.0f, 16777215);
            }
            GlStateManager.func_179152_a(0.85f, 0.85f, 0.85f);
            int c = 0;
            for (final Map.Entry<Enchantment, Integer> m : EnchantmentHelper.func_82781_a(item).entrySet()) {
                final int w2 = NameTags.mc.field_71466_p.func_78256_a(I18n.func_135052_a(m.getKey().func_77320_a().substring(0, 2), new Object[0]) + m.getValue() / 2);
                NameTags.mc.field_71466_p.func_175063_a(I18n.func_135052_a(m.getKey().func_77320_a(), new Object[0]).substring(0, 2) + m.getValue(), (float)(-4 - w2 + 3), (float)(c * 10 - 1), (m.getKey() == Enchantments.field_190940_C || m.getKey() == Enchantments.field_190941_k) ? 16732240 : 16756960);
                --c;
            }
            GlStateManager.func_179152_a(0.6f, 0.6f, 0.6f);
            final String dur = item.func_77958_k() - item.func_77952_i() + "";
            final int color = MathHelper.func_181758_c((item.func_77958_k() - item.func_77952_i()) / item.func_77958_k() / 3.0f, 1.0f, 1.0f);
            if (item.func_77984_f()) {
                NameTags.mc.field_71466_p.func_175063_a(dur, (float)(-8 - dur.length() * 3), 15.0f, new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF).getRGB());
            }
        }
        catch (Exception ex) {}
        glCleanup();
    }
    
    public static void glSetup(final double x, final double y, final double z) {
        GlStateManager.func_179094_E();
        final RenderManager renderManager = NameTags.mc.func_175598_ae();
        GlStateManager.func_179137_b(x - NameTags.mc.func_175598_ae().field_78730_l, y - NameTags.mc.func_175598_ae().field_78731_m, z - NameTags.mc.func_175598_ae().field_78728_n);
        GlStateManager.func_187432_a(0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((NameTags.mc.field_71474_y.field_74320_O == 2) ? (-renderManager.field_78732_j) : renderManager.field_78732_j, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179140_f();
        GL11.glDisable(2929);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
    }
    
    public static void glCleanup() {
        GlStateManager.func_179145_e();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2929);
        GlStateManager.func_179126_j();
        GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
        GlStateManager.func_179121_F();
    }
    
    static {
        NameTags.toggled = false;
    }
}
