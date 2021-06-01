package Method.Client.utils.Screens.Override;

import Method.Client.utils.Screens.*;
import net.minecraftforge.client.event.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import java.util.*;
import java.util.stream.*;

public class BookInsert extends Screen
{
    @Override
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiScreenBook) {
            event.getButtonList().add(new GuiButton(3334, event.getGui().field_146294_l / 2 + 80, 50, 50, 20, "Fillmax"));
            event.getButtonList().add(new GuiButton(3335, event.getGui().field_146294_l / 2 + 80, 70, 50, 20, "AutoSign"));
            event.getButtonList().add(new GuiButton(3336, event.getGui().field_146294_l / 2 + 80, 90, 50, 20, "Copy"));
            event.getButtonList().add(new GuiButton(3337, event.getGui().field_146294_l / 2 + 80, 110, 50, 20, "Fillbook"));
            event.getButtonList().add(new GuiButton(3338, event.getGui().field_146294_l / 2 + 80, 130, 50, 20, "Clear"));
            event.getButtonList().add(new GuiButton(3339, event.getGui().field_146294_l / 2 + 80, 150, 50, 20, "Author"));
            for (int i = 0; i < 22; ++i) {
                event.getButtonList().add(new GuiButton(i + 11, event.getGui().field_146294_l / 2 + 130 + i % 5 * 15, 105 - i / 5 * 15, 15, 15, this.ColorfromInt(i) + "&A"));
            }
        }
    }
    
    @Override
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiScreenBook) {
            final GuiScreenBook Gui = (GuiScreenBook)event.getGui();
            switch (event.getButton().field_146127_k) {
                case 3334: {
                    this.RandomFill(Gui, Gui.field_146484_x);
                    break;
                }
                case 3335: {
                    Gui.field_146480_s = true;
                    Gui.field_146482_z = BookInsert.mc.field_71439_g.func_70005_c_();
                    try {
                        Gui.func_146462_a(true);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    BookInsert.mc.func_147108_a((GuiScreen)null);
                    break;
                }
                case 3336: {
                    final String s = this.pageGetCurrent(Gui);
                    this.NewPage(Gui);
                    this.pageSetCurrent(Gui, s);
                    break;
                }
                case 3337: {
                    for (int G = 0; G < Gui.field_146476_w; ++G) {
                        this.NewPage(Gui);
                    }
                    for (int G = 0; G < Gui.field_146476_w; ++G) {
                        this.RandomFill(Gui, G);
                    }
                    break;
                }
                case 3338: {
                    this.pageSetCurrent(Gui, "");
                    break;
                }
                case 3339: {
                    BookInsert.mc.func_147108_a((GuiScreen)null);
                    BookInsert.mc.func_147108_a((GuiScreen)new Authorinsert());
                    break;
                }
                case 27: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.OBFUSCATED);
                    break;
                }
                case 28: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.BOLD);
                    break;
                }
                case 29: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.STRIKETHROUGH);
                    break;
                }
                case 30: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.UNDERLINE);
                    break;
                }
                case 31: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.ITALIC);
                    break;
                }
                case 32: {
                    this.checkandinstert(Gui, event.getButton().field_146127_k, TextFormatting.RESET);
                    break;
                }
                default: {
                    if (event.getButton().field_146127_k >= 27 || event.getButton().field_146127_k <= 10) {
                        break;
                    }
                    final String getCurrent = this.pageGetCurrent(Gui);
                    final String s2 = getCurrent + TextFormatting.func_175744_a(event.getButton().field_146127_k - 11);
                    final int wordWrappedHeight = Gui.field_146289_q.func_78267_b(s2 + "" + TextFormatting.BLACK + "_", 118);
                    if (wordWrappedHeight <= 128 && s2.length() < 256) {
                        this.pageSetCurrent(Gui, s2);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void RandomFill(final GuiScreenBook gui, final int loc) {
        final String collect;
        new Thread(() -> {
            collect = this.randomget().substring(0, 254);
            gui.field_146483_y.func_150304_a(loc, (NBTBase)new NBTTagString(collect));
            gui.field_146481_r = true;
        }).start();
    }
    
    public String randomget() {
        final IntStream gen = new Random().ints(128, 1112063).map(i -> (i < 55296) ? i : (i + 2048));
        return gen.limit(1536L).mapToObj(i -> String.valueOf((char)i)).collect((Collector<? super Object, ?, String>)Collectors.joining());
    }
    
    private void NewPage(final GuiScreenBook gui) {
        if (gui.field_146475_i) {
            if (gui.field_146483_y != null && gui.field_146483_y.func_74745_c() < 50) {
                gui.field_146483_y.func_74742_a((NBTBase)new NBTTagString(""));
                ++gui.field_146476_w;
                gui.field_146481_r = true;
            }
            if (gui.field_146484_x < gui.field_146476_w - 1) {
                ++gui.field_146484_x;
            }
        }
    }
    
    void checkandinstert(final GuiScreenBook gui, final int id, final TextFormatting d) {
        final String s = this.pageGetCurrent(gui);
        final String s2 = s + d;
        final int i = gui.field_146289_q.func_78267_b(s2 + "" + TextFormatting.BLACK + "_", 118);
        if (i <= 128 && s2.length() < 256) {
            this.pageSetCurrent(gui, s2);
        }
    }
    
    public String pageGetCurrent(final GuiScreenBook gui) {
        return (gui.field_146483_y != null && gui.field_146484_x >= 0 && gui.field_146484_x < gui.field_146483_y.func_74745_c()) ? gui.field_146483_y.func_150307_f(gui.field_146484_x) : "";
    }
    
    private void pageSetCurrent(final GuiScreenBook gui, final String s) {
        if (gui.field_146483_y != null && gui.field_146484_x >= 0 && gui.field_146484_x < gui.field_146483_y.func_74745_c()) {
            gui.field_146483_y.func_150304_a(gui.field_146484_x, (NBTBase)new NBTTagString(s));
            gui.field_146481_r = true;
        }
    }
}
