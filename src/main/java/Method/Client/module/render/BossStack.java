package Method.Client.module.render;

import Method.Client.managers.*;
import net.minecraft.util.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

public class BossStack extends Module
{
    Setting mode;
    Setting scale;
    private static final ResourceLocation GUI_BARS_TEXTURES;
    
    public BossStack() {
        super("BossStack", 0, Category.RENDER, "BossStack");
        this.mode = Main.setmgr.add(new Setting("Stack mode", this, "Stack", new String[] { "Stack", "Minimize" }));
        this.scale = Main.setmgr.add(new Setting("Scale", this, 0.5, 0.5, 4.0, false));
    }
    
    @Override
    public void RenderGameOverLayPost(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.BOSSINFO) {
            return;
        }
        if (this.mode.getValString().equalsIgnoreCase("Minimize")) {
            final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)BossStack.mc.field_71456_v.func_184046_j().field_184060_g;
            if (map == null) {
                return;
            }
            final ScaledResolution scaledresolution = new ScaledResolution(BossStack.mc);
            final int i = scaledresolution.func_78326_a();
            int j = 12;
            for (final Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
                final BossInfoClient info = entry.getValue();
                final String text = info.func_186744_e().func_150254_d();
                j = this.Collapse(event, i, j, info, text);
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Stack")) {
            final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)BossStack.mc.field_71456_v.func_184046_j().field_184060_g;
            final HashMap<String, Pair<BossInfoClient, Integer>> to = new HashMap<String, Pair<BossInfoClient, Integer>>();
            for (final Map.Entry<UUID, BossInfoClient> entry2 : map.entrySet()) {
                final String s = entry2.getValue().func_186744_e().func_150254_d();
                Pair<BossInfoClient, Integer> p;
                if (to.containsKey(s)) {
                    p = to.get(s);
                    p = new Pair<BossInfoClient, Integer>(p.getKey(), p.getValue() + 1);
                }
                else {
                    p = new Pair<BossInfoClient, Integer>(entry2.getValue(), 1);
                }
                to.put(s, p);
            }
            final ScaledResolution scaledresolution2 = new ScaledResolution(BossStack.mc);
            final int k = scaledresolution2.func_78326_a();
            int l = 12;
            for (final Map.Entry<String, Pair<BossInfoClient, Integer>> entry3 : to.entrySet()) {
                String text = entry3.getKey();
                final BossInfoClient info2 = entry3.getValue().getKey();
                final int a = entry3.getValue().getValue();
                text = text + " x" + a;
                l = this.Collapse(event, k, l, info2, text);
            }
        }
    }
    
    @Override
    public void onRenderPre(final RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO) {
            event.setCanceled(true);
        }
    }
    
    private int Collapse(final RenderGameOverlayEvent.Post event, final int i, int j, final BossInfoClient info, final String text) {
        final int k = (int)(i / this.scale.getValDouble() / 2.0 - 91.0);
        GlStateManager.func_179139_a(this.scale.getValDouble(), this.scale.getValDouble(), 1.0);
        if (!event.isCanceled()) {
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            BossStack.mc.func_110434_K().func_110577_a(BossStack.GUI_BARS_TEXTURES);
            BossStack.mc.field_71456_v.func_184046_j().func_184052_a(k, j, (BossInfo)info);
            BossStack.mc.field_71466_p.func_175063_a(text, (float)(i / this.scale.getValDouble() / 2.0 - BossStack.mc.field_71466_p.func_78256_a(text) / 2), (float)(j - 9), 16777215);
        }
        GlStateManager.func_179139_a(1.0 / this.scale.getValDouble(), 1.0 / this.scale.getValDouble(), 1.0);
        j += 10 + BossStack.mc.field_71466_p.field_78288_b;
        return j;
    }
    
    static {
        GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    }
}
