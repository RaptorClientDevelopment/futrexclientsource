package Method.Client.module.misc;

import net.minecraft.util.*;
import Method.Client.module.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;

public class GuiPeek extends Module
{
    private String name;
    private boolean box;
    private static final ResourceLocation RES_MAP_BACKGROUND;
    
    public GuiPeek() {
        super("Gui Peek", 0, Category.MISC, "Peek into maps/shulker ");
        this.name = "";
        this.box = false;
    }
    
    @Override
    public void ItemTooltipEvent(final ItemTooltipEvent event) {
        if (this.box) {
            event.getToolTip().clear();
            event.getToolTip().add(this.name);
        }
        if (event.getItemStack().func_77973_b() instanceof ItemMap) {
            event.getToolTip().clear();
            event.getToolTip().add(event.getItemStack().func_82833_r());
        }
    }
    
    @Override
    public void postDrawScreen(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiContainer && ((GuiContainer)event.getGui()).getSlotUnderMouse() != null) {
            final ItemStack item = Objects.requireNonNull(((GuiContainer)event.getGui()).getSlotUnderMouse()).func_75211_c();
            if (item.func_77973_b() instanceof ItemShulkerBox) {
                this.name = item.func_82833_r();
                this.box = true;
                final int X = event.getMouseX() + 8;
                final int Y = event.getMouseY() - 82;
                final NBTTagList nbttaglist = Objects.requireNonNull(item.func_77942_o() ? item.func_77978_p() : new NBTTagCompound()).func_74775_l("BlockEntityTag").func_150295_c("Items", 10);
                int xMod = 6;
                int yMod = 6;
                GlStateManager.func_179141_d();
                GlStateManager.func_179147_l();
                GL11.glDisable(2929);
                GlStateManager.func_179131_c(2.0f, 2.0f, 2.0f, 1.0f);
                GuiPeek.mc.func_110434_K().func_110577_a(new ResourceLocation("futurex", "shulker.png"));
                GuiPeek.mc.field_71456_v.func_73729_b(X, Y, 0, 0, 172, 64);
                GL11.glEnable(2929);
                for (final NBTBase list : nbttaglist) {
                    final ItemStack stack = new ItemStack((NBTTagCompound)list);
                    final String stringofitems = list.toString();
                    final int slotnum = Integer.parseInt(stringofitems.substring(stringofitems.lastIndexOf("{Slot:") + 1, stringofitems.indexOf("b,")).replaceAll("[^0-9]", ""));
                    if (slotnum > 8 && slotnum < 17) {
                        xMod = -156;
                        yMod = 24;
                    }
                    else if (slotnum > 17) {
                        xMod = -318;
                        yMod = 42;
                    }
                    GlStateManager.func_179094_E();
                    GL11.glDepthFunc(517);
                    RenderHelper.func_74518_a();
                    RenderHelper.func_74520_c();
                    GuiPeek.mc.func_175599_af().func_180450_b(stack, X + xMod + 18 * slotnum, Y + yMod);
                    String string = Integer.toString(stack.func_190916_E());
                    if (stack.func_190916_E() == 1) {
                        string = "";
                    }
                    GuiPeek.mc.func_175599_af().func_180453_a(GuiPeek.mc.field_71466_p, stack, X + xMod + 18 * slotnum, Y + yMod, string);
                    GlStateManager.func_179121_F();
                }
                GL11.glDepthFunc(515);
            }
            if (GuiPeek.mc.field_71439_g.field_71071_by.func_70445_o().func_77973_b() instanceof ItemAir) {
                final Slot slotUnderMouse = ((GuiContainer)event.getGui()).getSlotUnderMouse();
                if (slotUnderMouse != null && slotUnderMouse.func_75216_d()) {
                    final ItemStack itemUnderMouse = slotUnderMouse.func_75211_c();
                    if (itemUnderMouse.func_77973_b() instanceof ItemMap) {
                        final MapData mapdata = ((ItemMap)itemUnderMouse.func_77973_b()).func_77873_a(itemUnderMouse, (World)GuiPeek.mc.field_71441_e);
                        if (mapdata != null) {
                            GlStateManager.func_179097_i();
                            GlStateManager.func_179140_f();
                            GuiPeek.mc.func_110434_K().func_110577_a(GuiPeek.RES_MAP_BACKGROUND);
                            final Tessellator tessellator = Tessellator.func_178181_a();
                            final BufferBuilder bufferbuilder = tessellator.func_178180_c();
                            GlStateManager.func_179137_b((double)event.getMouseX(), event.getMouseY() + 15.5, 0.0);
                            GlStateManager.func_179139_a(0.5, 0.5, 1.0);
                            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                            bufferbuilder.func_181662_b(-7.0, 135.0, 0.0).func_187315_a(0.0, 1.0).func_181675_d();
                            bufferbuilder.func_181662_b(135.0, 135.0, 0.0).func_187315_a(1.0, 1.0).func_181675_d();
                            bufferbuilder.func_181662_b(135.0, -7.0, 0.0).func_187315_a(1.0, 0.0).func_181675_d();
                            bufferbuilder.func_181662_b(-7.0, -7.0, 0.0).func_187315_a(0.0, 0.0).func_181675_d();
                            tessellator.func_78381_a();
                            GuiPeek.mc.field_71460_t.func_147701_i().func_148250_a(mapdata, true);
                            GlStateManager.func_179145_e();
                            GlStateManager.func_179126_j();
                        }
                    }
                }
            }
        }
        else {
            this.box = false;
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final ItemStack itemStack = GuiPeek.mc.field_71439_g.func_184614_ca();
        if (itemStack.func_77973_b() instanceof ItemShulkerBox && Mouse.getEventButton() == 1) {
            Peekcode(itemStack, GuiPeek.mc);
        }
    }
    
    public static void Peekcode(final ItemStack itemStack, final Minecraft mc) {
        final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
        entityBox.field_145854_h = ((ItemShulkerBox)itemStack.func_77973_b()).func_179223_d();
        entityBox.func_145834_a((World)mc.field_71441_e);
        assert itemStack.func_77978_p() != null;
        entityBox.func_145839_a(itemStack.func_77978_p().func_74775_l("BlockEntityTag"));
        entityBox.func_190575_a("Shulker Peek");
        mc.field_71439_g.func_71007_a((IInventory)entityBox);
    }
    
    static {
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    }
}
