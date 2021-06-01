package Method.Client.utils.Screens.Override;

import net.minecraft.client.*;
import com.google.common.collect.*;
import javax.annotation.*;
import net.minecraft.scoreboard.*;
import Method.Client.module.render.*;
import net.minecraft.client.network.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import net.minecraft.util.text.*;
import Method.Client.managers.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class Mixintab extends GuiPlayerTabOverlay
{
    protected static Minecraft mc;
    public static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING;
    public ITextComponent field_175255_h;
    public ITextComponent field_175256_i;
    public long field_175253_j;
    public boolean field_175254_k;
    
    public static void Run() {
        Mixintab.mc.field_71456_v.field_175196_v = new Mixintab(Mixintab.mc, Mixintab.mc.field_71456_v);
    }
    
    public Mixintab(final Minecraft mcIn, final GuiIngame guiIngameIn) {
        super(mcIn, guiIngameIn);
    }
    
    public String func_175243_a(final NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
    }
    
    public void func_175246_a(final boolean willBeRendered) {
        if (willBeRendered && !this.field_175254_k) {
            this.field_175253_j = Minecraft.func_71386_F();
        }
        this.field_175254_k = willBeRendered;
    }
    
    public void func_175249_a(final int width, final Scoreboard scoreboardIn, @Nullable final ScoreObjective scoreObjectiveIn) {
        final NetHandlerPlayClient nethandlerplayclient = Mixintab.mc.field_71439_g.field_71174_a;
        List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)Mixintab.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.func_175106_d());
        int i = 0;
        int j = 0;
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            int k = Mixintab.mc.field_71466_p.func_78256_a(this.func_175243_a(networkplayerinfo));
            i = Math.max(i, k);
            if (scoreObjectiveIn != null && scoreObjectiveIn.func_178766_e() != IScoreCriteria.EnumRenderType.HEARTS) {
                k = Mixintab.mc.field_71466_p.func_78256_a(" " + scoreboardIn.func_96529_a(networkplayerinfo.func_178845_a().getName(), scoreObjectiveIn).func_96652_c());
                j = Math.max(j, k);
            }
        }
        list = list.subList(0, (int)Math.min(list.size(), ExtraTab.Players.getValDouble()));
        int i2;
        int l3;
        int j2;
        for (l3 = (i2 = list.size()), j2 = 1; i2 > 20; i2 = (l3 + j2 - 1) / j2) {
            ++j2;
        }
        final boolean flag = Mixintab.mc.func_71387_A() || Objects.requireNonNull(Mixintab.mc.func_147114_u()).func_147298_b().func_179292_f();
        int m;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.func_178766_e() == IScoreCriteria.EnumRenderType.HEARTS) {
                m = 90;
            }
            else {
                m = j;
            }
        }
        else {
            m = 0;
        }
        final int i3 = Math.min(j2 * ((flag ? 9 : 0) + i + m + 13), width - 50) / j2;
        final int j3 = width / 2 - (i3 * j2 + (j2 - 1) * 5) / 2;
        int k2 = 10;
        int l4 = i3 * j2 + (j2 - 1) * 5;
        List<String> list2 = null;
        if (this.field_175256_i != null) {
            list2 = (List<String>)Mixintab.mc.field_71466_p.func_78271_c(this.field_175256_i.func_150254_d(), width - 50);
            for (final String s : list2) {
                l4 = Math.max(l4, Mixintab.mc.field_71466_p.func_78256_a(s));
            }
        }
        List<String> list3 = null;
        if (this.field_175255_h != null) {
            list3 = (List<String>)Mixintab.mc.field_71466_p.func_78271_c(this.field_175255_h.func_150254_d(), width - 50);
            for (final String s2 : list3) {
                l4 = Math.max(l4, Mixintab.mc.field_71466_p.func_78256_a(s2));
            }
        }
        if (list2 != null) {
            k2 = this.getK1(width, k2, l4, list2);
            ++k2;
        }
        func_73734_a(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + i2 * 9, Integer.MIN_VALUE);
        for (int k3 = 0; k3 < l3; ++k3) {
            final int l5 = k3 / i2;
            final int i4 = k3 % i2;
            int j4 = j3 + l5 * i3 + l5 * 5;
            final int k4 = k2 + i4 * 9;
            func_73734_a(j4, k4, j4 + i3, k4 + 8, 553648127);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179141_d();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (k3 < list.size()) {
                final NetworkPlayerInfo networkplayerinfo2 = list.get(k3);
                final GameProfile gameprofile = networkplayerinfo2.func_178845_a();
                if (flag) {
                    final EntityPlayer entityplayer = Mixintab.mc.field_71441_e.func_152378_a(gameprofile.getId());
                    final boolean flag2 = entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                    Mixintab.mc.func_110434_K().func_110577_a(networkplayerinfo2.func_178837_g());
                    final int l6 = 8 + (flag2 ? 8 : 0);
                    final int i5 = 8 * (flag2 ? -1 : 1);
                    Gui.func_152125_a(j4, k4, 8.0f, (float)l6, 8, i5, 8, 8, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.HAT)) {
                        final int j5 = 8 + (flag2 ? 8 : 0);
                        final int k5 = 8 * (flag2 ? -1 : 1);
                        Gui.func_152125_a(j4, k4, 40.0f, (float)j5, 8, k5, 8, 8, 64.0f, 64.0f);
                    }
                    j4 += 9;
                }
                final String s3 = this.func_175243_a(networkplayerinfo2);
                if (networkplayerinfo2.func_178848_b() == GameType.SPECTATOR) {
                    Mixintab.mc.field_71466_p.func_175063_a(TextFormatting.ITALIC + s3, (float)j4, (float)k4, -1862270977);
                }
                else if (FriendManager.isFriend(s3)) {
                    Mixintab.mc.field_71466_p.func_175063_a(ExtraTab.FriendObfus.getValBoolean() ? "Friend" : s3, (float)j4, (float)k4, ExtraTab.FriendColor.getcolor());
                }
                else {
                    Mixintab.mc.field_71466_p.func_175063_a(s3, (float)j4, (float)k4, -1);
                }
                if (scoreObjectiveIn != null && networkplayerinfo2.func_178848_b() != GameType.SPECTATOR) {
                    final int k6 = j4 + i + 1;
                    final int l7 = k6 + m;
                    if (l7 - k6 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, k4, gameprofile.getName(), k6, l7, networkplayerinfo2);
                    }
                }
                this.func_175245_a(i3, j4 - (flag ? 9 : 0), k4, networkplayerinfo2);
            }
        }
        if (list3 != null) {
            k2 = k2 + i2 * 9 + 1;
            this.getK1(width, k2, l4, list3);
        }
    }
    
    private int getK1(final int width, int k1, final int l1, final List<String> list1) {
        func_73734_a(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * Mixintab.mc.field_71466_p.field_78288_b, Integer.MIN_VALUE);
        for (final String s2 : list1) {
            final int i2 = Mixintab.mc.field_71466_p.func_78256_a(s2);
            Mixintab.mc.field_71466_p.func_175063_a(s2, (float)(width / 2 - i2 / 2), (float)k1, -1);
            k1 += Mixintab.mc.field_71466_p.field_78288_b;
        }
        return k1;
    }
    
    protected void func_175245_a(final int offset, final int x, final int yval, final NetworkPlayerInfo networkPlayerInfoIn) {
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        if (ExtraTab.ReplaceBar.getValBoolean()) {
            this.field_73735_i += 100.0f;
            Mixintab.mc.field_71466_p.func_175063_a(String.valueOf(networkPlayerInfoIn.func_178853_c()), (float)(x + offset - 11), (float)yval, -1);
            this.field_73735_i -= 100.0f;
            return;
        }
        Mixintab.mc.func_110434_K().func_110577_a(Mixintab.field_110324_m);
        int j;
        if (networkPlayerInfoIn.func_178853_c() < 0) {
            j = 5;
        }
        else if (networkPlayerInfoIn.func_178853_c() < 150) {
            j = 0;
        }
        else if (networkPlayerInfoIn.func_178853_c() < 300) {
            j = 1;
        }
        else if (networkPlayerInfoIn.func_178853_c() < 600) {
            j = 2;
        }
        else if (networkPlayerInfoIn.func_178853_c() < 1000) {
            j = 3;
        }
        else {
            j = 4;
        }
        this.field_73735_i += 100.0f;
        this.func_73729_b(x + offset - 11, yval, 0, 176 + j * 8, 10, 8);
        this.field_73735_i -= 100.0f;
    }
    
    private void drawScoreboardValues(final ScoreObjective objective, final int p_175247_2_, final String name, final int p_175247_4_, final int p_175247_5_, final NetworkPlayerInfo info) {
        final int i = objective.func_96682_a().func_96529_a(name, objective).func_96652_c();
        if (objective.func_178766_e() == IScoreCriteria.EnumRenderType.HEARTS) {
            Mixintab.mc.func_110434_K().func_110577_a(Mixintab.field_110324_m);
            if (this.field_175253_j == info.func_178855_p()) {
                if (i < info.func_178835_l()) {
                    info.func_178846_a(Minecraft.func_71386_F());
                    info.func_178844_b((long)(this.field_175251_g.func_73834_c() + 20));
                }
                else if (i > info.func_178835_l()) {
                    info.func_178846_a(Minecraft.func_71386_F());
                    info.func_178844_b((long)(this.field_175251_g.func_73834_c() + 10));
                }
            }
            if (Minecraft.func_71386_F() - info.func_178847_n() > 1000L || this.field_175253_j != info.func_178855_p()) {
                info.func_178836_b(i);
                info.func_178857_c(i);
                info.func_178846_a(Minecraft.func_71386_F());
            }
            info.func_178843_c(this.field_175253_j);
            info.func_178836_b(i);
            final int j = MathHelper.func_76123_f(Math.max(i, info.func_178860_m()) / 2.0f);
            final int k = Math.max(MathHelper.func_76123_f((float)(i / 2)), Math.max(MathHelper.func_76123_f((float)(info.func_178860_m() / 2)), 10));
            final boolean flag = info.func_178858_o() > this.field_175251_g.func_73834_c() && (info.func_178858_o() - this.field_175251_g.func_73834_c()) / 3L % 2L == 1L;
            if (j > 0) {
                final float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / k, 9.0f);
                if (f > 3.0f) {
                    for (int l = j; l < k; ++l) {
                        this.func_175174_a(p_175247_4_ + l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }
                    for (int j2 = 0; j2 < j; ++j2) {
                        this.func_175174_a(p_175247_4_ + j2 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                        if (flag) {
                            if (j2 * 2 + 1 < info.func_178860_m()) {
                                this.func_175174_a(p_175247_4_ + j2 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }
                            if (j2 * 2 + 1 == info.func_178860_m()) {
                                this.func_175174_a(p_175247_4_ + j2 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (j2 * 2 + 1 < i) {
                            this.func_175174_a(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 160 : 52, 0, 9, 9);
                        }
                        if (j2 * 2 + 1 == i) {
                            this.func_175174_a(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 169 : 61, 0, 9, 9);
                        }
                    }
                }
                else {
                    final float f2 = MathHelper.func_76131_a(i / 20.0f, 0.0f, 1.0f);
                    final int i2 = (int)((1.0f - f2) * 255.0f) << 16 | (int)(f2 * 255.0f) << 8;
                    String s = "" + i / 2.0f;
                    if (p_175247_5_ - Mixintab.mc.field_71466_p.func_78256_a(s + "hp") >= p_175247_4_) {
                        s += "hp";
                    }
                    Mixintab.mc.field_71466_p.func_175063_a(s, (float)((p_175247_5_ + p_175247_4_) / 2 - Mixintab.mc.field_71466_p.func_78256_a(s) / 2), (float)p_175247_2_, i2);
                }
            }
        }
        else {
            final String s2 = TextFormatting.YELLOW + "" + i;
            Mixintab.mc.field_71466_p.func_175063_a(s2, (float)(p_175247_5_ - Mixintab.mc.field_71466_p.func_78256_a(s2)), (float)p_175247_2_, 16777215);
        }
    }
    
    public void func_175248_a(@Nullable final ITextComponent footerIn) {
        this.field_175255_h = footerIn;
    }
    
    public void func_175244_b(@Nullable final ITextComponent headerIn) {
        this.field_175256_i = headerIn;
    }
    
    public void func_181030_a() {
        this.field_175256_i = null;
        this.field_175255_h = null;
    }
    
    static {
        Mixintab.mc = Minecraft.func_71410_x();
        ENTRY_ORDERING = GuiPlayerTabOverlay.field_175252_a;
    }
}
