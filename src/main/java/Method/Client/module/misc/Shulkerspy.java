package Method.Client.module.misc;

import java.util.concurrent.*;
import net.minecraft.tileentity.*;
import Method.Client.managers.*;
import Method.Client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import Method.Client.utils.visual.*;
import Method.Client.module.*;
import Method.Client.module.render.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.item.*;
import java.util.stream.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.inventory.*;

public class Shulkerspy extends Module
{
    public static ConcurrentHashMap<String, TileEntityShulkerBox> shulkerMap;
    public Setting Mode;
    public Setting X;
    public Setting Y;
    public Setting Scale;
    public Setting Background;
    public Setting BgColor;
    
    public Shulkerspy() {
        super("Shulkerspy", 0, Category.MISC, "See others last held Shulker");
        this.Mode = Main.setmgr.add(new Setting(" Mode", this, "Dynamic", new String[] { "Dynamic", "Static" }));
        this.X = Main.setmgr.add(new Setting("X", this, 1.0, 0.0, 1000.0, false, this.Mode, "Static", 2));
        this.Y = Main.setmgr.add(new Setting("Y", this, 1.0, 0.0, 1000.0, false, this.Mode, "Static", 3));
        this.Scale = Main.setmgr.add(new Setting("Scale", this, 1.0, 0.0, 4.0, false, this.Mode, "Dynamic", 2));
        this.Background = Main.setmgr.add(new Setting("Background", this, true));
        this.BgColor = Main.setmgr.add(new Setting("BgColor", this, 0.22, 0.88, 0.22, 0.22, this.Background, 2));
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.Mode.getValString().equalsIgnoreCase("Dynamic")) {
            for (final Entity object : Shulkerspy.mc.field_71441_e.field_72996_f) {
                if (object instanceof EntityOtherPlayerMP && Shulkerspy.shulkerMap.containsKey(object.func_70005_c_().toLowerCase())) {
                    final IInventory inv = (IInventory)Shulkerspy.shulkerMap.get(object.func_70005_c_().toLowerCase());
                    this.DrawBox((EntityLivingBase)object, inv);
                }
            }
        }
    }
    
    @Override
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        if (this.Mode.getValString().equalsIgnoreCase("Static")) {
            int Players = 0;
            for (final Entity object : Shulkerspy.mc.field_71441_e.field_72996_f) {
                if (object instanceof EntityOtherPlayerMP && Shulkerspy.shulkerMap.containsKey(object.func_70005_c_().toLowerCase())) {
                    final IInventory inv = (IInventory)Shulkerspy.shulkerMap.get(object.func_70005_c_().toLowerCase());
                    this.DrawSbox(inv, Players, (EntityLivingBase)object);
                    ++Players;
                }
            }
        }
    }
    
    private void DrawSbox(final IInventory inv, final int players, final EntityLivingBase object) {
        RenderHelper.func_74520_c();
        if (this.Background.getValBoolean()) {
            RenderUtils.drawRectDouble(this.X.getValDouble(), this.Y.getValDouble() - players * 100, this.X.getValDouble() + 88.0 + 60.0, this.Y.getValDouble() + 50.0 - players * 100, this.BgColor.getcolor());
        }
        Shulkerspy.mc.field_71466_p.func_175063_a(object.func_70005_c_() + "'s Shulker", (float)this.X.getValDouble() + 45.0f, (float)this.Y.getValDouble() - 10.0f, -1);
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack itemStack = inv.func_70301_a(i);
            final int offsetX = (int)(this.X.getValDouble() + i % 9 * 16);
            final int offsetY = (int)(this.Y.getValDouble() + i / 9 * 16) - players * 100;
            Shulkerspy.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
            Shulkerspy.mc.func_175599_af().func_180453_a(Shulkerspy.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
        }
        RenderHelper.func_74518_a();
        Shulkerspy.mc.func_175599_af().field_77023_b = 0.0f;
    }
    
    public void DrawBox(final EntityLivingBase e, final IInventory inv) {
        final int morey = ModuleManager.getModuleByName("NameTags").isToggled() ? -6 : 0;
        final double scale = Math.max(this.Scale.getValDouble() * (Shulkerspy.mc.field_71439_g.func_70032_d((Entity)e) / 20.0f), 2.0);
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            NameTags.drawItem(e.field_70165_t, e.field_70163_u + e.field_70131_O + 0.75 * scale, e.field_70161_v, -2.5 + i % 9, -(i / 9 * 1.2) - morey, scale, inv.func_70301_a(i));
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final List<Entity> valids = (List<Entity>)Shulkerspy.mc.field_71441_e.func_72910_y().stream().filter(en -> en instanceof EntityOtherPlayerMP).filter(mp -> mp.func_184614_ca().func_77973_b() instanceof ItemShulkerBox).collect(Collectors.toList());
        for (final Entity valid : valids) {
            final EntityOtherPlayerMP mp2 = (EntityOtherPlayerMP)valid;
            final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            final ItemShulkerBox shulker = (ItemShulkerBox)mp2.func_184614_ca().func_77973_b();
            entityBox.field_145854_h = shulker.func_179223_d();
            entityBox.func_145834_a((World)Shulkerspy.mc.field_71441_e);
            ItemStackHelper.func_191283_b(Objects.requireNonNull(mp2.func_184614_ca().func_77978_p()).func_74775_l("BlockEntityTag"), entityBox.field_190596_f);
            entityBox.func_145839_a(mp2.func_184614_ca().func_77978_p().func_74775_l("BlockEntityTag"));
            entityBox.func_190575_a(mp2.func_184614_ca().func_82837_s() ? mp2.func_184614_ca().func_82833_r() : (mp2.func_70005_c_() + "'s current shulker box"));
            Shulkerspy.shulkerMap.put(mp2.func_70005_c_().toLowerCase(), entityBox);
        }
    }
    
    static {
        Shulkerspy.shulkerMap = new ConcurrentHashMap<String, TileEntityShulkerBox>();
    }
}
