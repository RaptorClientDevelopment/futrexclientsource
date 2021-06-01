package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

public class SignInsert extends Screen
{
    @Override
    public void GuiOpen(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiEditSign) {
            event.setGui((GuiScreen)new BetterSignGui(((GuiEditSign)event.getGui()).field_146848_f));
        }
    }
    
    class BetterSignGui extends GuiScreen
    {
        private int focusedField;
        public final TileEntitySign sign;
        private List<GuiTextField> textFields;
        private String[] defaultStrings;
        
        public BetterSignGui(final TileEntitySign sign) {
            this.focusedField = 0;
            this.sign = sign;
        }
        
        public void func_73866_w_() {
            this.field_146292_n.clear();
            Keyboard.enableRepeatEvents(true);
            this.textFields = new LinkedList<GuiTextField>();
            this.defaultStrings = new String[4];
            for (int i = 0; i < 4; ++i) {
                final GuiTextField field = new GuiTextField(i, this.field_146289_q, this.field_146294_l / 2 + 4, 75 + i * 24, 120, 20);
                field.func_175205_a(this::validateText);
                field.func_146203_f(100);
                final String text = this.sign.field_145915_a[i].func_150260_c();
                field.func_146180_a(this.defaultStrings[i] = text);
                this.textFields.add(field);
            }
            this.textFields.get(this.focusedField).func_146195_b(true);
            this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 5, this.field_146295_m / 4 + 120, 120, 20, "Done"));
            this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 125, this.field_146295_m / 4 + 120, 120, 20, "Cancel"));
            this.func_189646_b(new GuiButton(6, this.field_146294_l / 2 - 41, 147, 40, 20, "Shift"));
            this.func_189646_b(new GuiButton(7, this.field_146294_l / 2 - 41, 123, 40, 20, "Clear"));
            this.func_189646_b(new GuiButton(8, this.field_146294_l / 2 + 130, 124, 40, 20, "Lag"));
            this.func_189646_b(new GuiButton(9, this.field_146294_l / 2 + 130, 99, 40, 20, "FillMax"));
            for (int i = 0; i < 22; ++i) {
                this.func_189646_b(new GuiButton(i + 11, this.field_146294_l / 2 + 130 + i % 5 * 15, 215 - i / 5 * 15, 15, 15, SignInsert.this.ColorfromInt(i) + "&A"));
            }
            this.sign.func_145913_a(false);
        }
        
        protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            super.func_73864_a(mouseX, mouseY, mouseButton);
            this.textFields.forEach(field -> {
                field.func_146192_a(mouseX, mouseY, mouseButton);
                if (field.func_146206_l()) {
                    this.focusedField = field.func_175206_d();
                }
                return;
            });
            if (!this.textFields.get(this.focusedField).func_146206_l()) {
                this.textFields.get(this.focusedField).func_146195_b(true);
            }
        }
        
        protected void func_73869_a(final char typedChar, final int keyCode) {
            switch (keyCode) {
                case 1: {
                    this.exit();
                    return;
                }
                case 15: {
                    final int change = func_146272_n() ? -1 : 1;
                    this.tabFocus(change);
                    return;
                }
                case 200: {
                    this.tabFocus(-1);
                    return;
                }
                case 28:
                case 156:
                case 208: {
                    this.tabFocus(1);
                    break;
                }
            }
            this.textFields.forEach(field -> field.func_146201_a(typedChar, keyCode));
            this.sign.field_145915_a[this.focusedField] = (ITextComponent)new TextComponentString(this.textFields.get(this.focusedField).func_146179_b());
        }
        
        protected void func_146284_a(final GuiButton button) throws IOException {
            super.func_146284_a(button);
            switch (button.field_146127_k) {
                case 5: {
                    for (int i = 0; i < 4; ++i) {
                        this.sign.field_145915_a[i] = (ITextComponent)new TextComponentString(this.defaultStrings[i]);
                    }
                }
                case 4: {
                    this.exit();
                    break;
                }
                case 6: {
                    final String[] replacements = new String[4];
                    for (int j = 0; j < 4; ++j) {
                        final int change = func_146272_n() ? 1 : -1;
                        final int target = this.wrapLine(j + change);
                        replacements[j] = this.sign.field_145915_a[target].func_150260_c();
                    }
                    this.applytext(replacements);
                    break;
                }
                case 7: {
                    final int id;
                    this.textFields.forEach(field -> {
                        id = field.func_175206_d();
                        field.func_146180_a("");
                        this.sign.field_145915_a[id] = (ITextComponent)new TextComponentString("");
                        return;
                    });
                    break;
                }
                case 8: {
                    final StringBuilder lagStringBuilder = new StringBuilder();
                    for (int k = 0; k < 500; ++k) {
                        lagStringBuilder.append("/(!\u00c2§()%/\u00c2§)=/(!\u00c2§()%/\u00c2§)=/(!\u00c2§()%/\u00c2§)=");
                    }
                    final String[] Builder = new String[4];
                    for (int l = 0; l < 4; ++l) {
                        Builder[l] = lagStringBuilder.toString();
                    }
                    this.applytext(Builder);
                    break;
                }
                case 9: {
                    final String line = this.Random();
                    final String[] rando = new String[4];
                    for (int m = 0; m < 4; ++m) {
                        rando[m] = line.substring(m * 384, (m + 1) * 384);
                    }
                    this.applytext(rando);
                    break;
                }
                default: {
                    if (button.field_146127_k < 27) {
                        final StringBuilder sb = new StringBuilder();
                        final GuiTextField guiTextField = this.textFields.get(this.focusedField);
                        guiTextField.field_146216_j = sb.append(guiTextField.field_146216_j).append("&").append(Integer.toHexString(button.field_146127_k - 11)).toString();
                    }
                    if (button.field_146127_k == 27) {
                        final StringBuilder sb2 = new StringBuilder();
                        final GuiTextField guiTextField2 = this.textFields.get(this.focusedField);
                        guiTextField2.field_146216_j = sb2.append(guiTextField2.field_146216_j).append("&k").toString();
                    }
                    if (button.field_146127_k == 28) {
                        final StringBuilder sb3 = new StringBuilder();
                        final GuiTextField guiTextField3 = this.textFields.get(this.focusedField);
                        guiTextField3.field_146216_j = sb3.append(guiTextField3.field_146216_j).append("&l").toString();
                    }
                    if (button.field_146127_k == 29) {
                        final StringBuilder sb4 = new StringBuilder();
                        final GuiTextField guiTextField4 = this.textFields.get(this.focusedField);
                        guiTextField4.field_146216_j = sb4.append(guiTextField4.field_146216_j).append("&m").toString();
                    }
                    if (button.field_146127_k == 30) {
                        final StringBuilder sb5 = new StringBuilder();
                        final GuiTextField guiTextField5 = this.textFields.get(this.focusedField);
                        guiTextField5.field_146216_j = sb5.append(guiTextField5.field_146216_j).append("&n").toString();
                    }
                    if (button.field_146127_k == 31) {
                        final StringBuilder sb6 = new StringBuilder();
                        final GuiTextField guiTextField6 = this.textFields.get(this.focusedField);
                        guiTextField6.field_146216_j = sb6.append(guiTextField6.field_146216_j).append("&o").toString();
                    }
                    if (button.field_146127_k == 32) {
                        final StringBuilder sb7 = new StringBuilder();
                        final GuiTextField guiTextField7 = this.textFields.get(this.focusedField);
                        guiTextField7.field_146216_j = sb7.append(guiTextField7.field_146216_j).append("&r").toString();
                        break;
                    }
                    break;
                }
            }
        }
        
        void applytext(final String[] index) {
            final int id;
            this.textFields.forEach(field -> {
                id = field.func_175206_d();
                field.func_146180_a(index[id]);
                this.sign.field_145915_a[id] = (ITextComponent)new TextComponentString(index[id]);
            });
        }
        
        public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
            this.func_146276_q_();
            this.func_73732_a(this.field_146289_q, I18n.func_135052_a("sign.edit", new Object[0]), this.field_146294_l / 2, 40, 16777215);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(this.field_146294_l / 2 - 63.0f, 0.0f, 50.0f);
            GlStateManager.func_179152_a(-93.75f, -93.75f, -93.75f);
            GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
            final Block block = this.sign.func_145838_q();
            if (block == Blocks.field_150472_an) {
                final float f1 = this.sign.func_145832_p() * 360 / 16.0f;
                GlStateManager.func_179114_b(f1, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179109_b(0.0f, -1.0625f, 0.0f);
            }
            else {
                final int i = this.sign.func_145832_p();
                final float f2 = (i == 2) ? 180.0f : ((i == 4) ? 90.0f : ((i == 5) ? -90.0f : 0.0f));
                GlStateManager.func_179114_b(f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179109_b(0.0f, -0.7625f, 0.0f);
            }
            this.sign.field_145918_i = -1;
            TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)this.sign, -0.5, -0.75, -0.5, 0.0f);
            GlStateManager.func_179121_F();
            this.textFields.forEach(GuiTextField::func_146194_f);
            super.func_73863_a(mouseX, mouseY, partialTicks);
        }
        
        void exit() {
            this.sign.func_70296_d();
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
        
        private String Random() {
            final IntStream gen = new Random().ints(128, 1112063).map(i -> (i < 55296) ? i : (i + 2048));
            return gen.limit(1536L).mapToObj(i -> String.valueOf((char)i)).collect((Collector<? super Object, ?, String>)Collectors.joining());
        }
        
        public void func_146281_b() {
            final String[] color = new String[4];
            for (int i = 0; i < 4; ++i) {
                color[i] = this.sign.field_145915_a[i].func_150260_c().replace("&", "§§a");
            }
            this.applytext(color);
            Keyboard.enableRepeatEvents(false);
            final NetHandlerPlayClient nethandlerplayclient = this.field_146297_k.func_147114_u();
            if (nethandlerplayclient != null) {
                nethandlerplayclient.func_147297_a((Packet)new CPacketUpdateSign(this.sign.func_174877_v(), this.sign.field_145915_a));
            }
            this.sign.func_145913_a(true);
        }
        
        void tabFocus(final int change) {
            this.textFields.get(this.focusedField).func_146195_b(false);
            this.focusedField = this.wrapLine(this.focusedField + change);
            this.textFields.get(this.focusedField).func_146195_b(true);
        }
        
        int wrapLine(final int line) {
            return (line > 3) ? 0 : ((line < 0) ? 3 : line);
        }
        
        boolean validateText(final String s) {
            if (this.field_146289_q.func_78256_a(s) > 90) {
                return false;
            }
            for (final char c : s.toCharArray()) {
                if (!ChatAllowedCharacters.func_71566_a(c)) {
                    return false;
                }
            }
            return true;
        }
    }
}
