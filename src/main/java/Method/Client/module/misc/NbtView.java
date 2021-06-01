package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import net.minecraftforge.common.*;
import Method.Client.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.util.text.*;
import java.awt.datatransfer.*;
import javax.annotation.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import java.awt.event.*;

public class NbtView extends Module
{
    private static int line_scrolled;
    private static int time;
    static Setting maxLinesShown;
    static Setting ticksBeforeScroll;
    static Setting showDelimiters;
    static Setting showSeparator;
    static Setting compress;
    private boolean readytocopy;
    private static final String FORMAT;
    
    public NbtView() {
        super("Nbt View", 0, Category.MISC, "Show Nbt Data,Press alt to copy");
        this.readytocopy = false;
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(NbtView.maxLinesShown = new Setting("maxLinesShown", this, 10.0, 1.0, 100.0, true));
        Main.setmgr.add(NbtView.ticksBeforeScroll = new Setting("ticksBeforeScroll", this, 20.0, 1.0, 400.0, true));
        Main.setmgr.add(NbtView.showDelimiters = new Setting("showDelimiters", this, true));
        Main.setmgr.add(NbtView.showSeparator = new Setting("showSeparator", this, true));
        Main.setmgr.add(NbtView.compress = new Setting("compress", this, true));
    }
    
    @Override
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().field_72995_K || !event.getWorld().field_72995_K) {
            final ItemStack stack = event.getEntityPlayer().func_184586_b(event.getHand());
            if (event.getWorld().func_175625_s(event.getPos()) != null && stack.func_77973_b() == Items.field_151032_g) {
                final ArrayList<String> tag = new ArrayList<String>();
                unwrapTag(tag, (NBTBase)Objects.requireNonNull(event.getWorld().func_175625_s(event.getPos())).func_189515_b(new NBTTagCompound()), "", "", "\t");
                final StringBuilder sb = new StringBuilder();
                final StringBuilder sb2;
                tag.forEach(s -> {
                    sb2.append(s);
                    sb2.append('\n');
                    return;
                });
                new InfoWindow(sb.toString(), event.getWorld().field_72995_K);
            }
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (GuiScreen.func_175283_s()) {
            this.readytocopy = true;
        }
        if (!GuiScreen.func_146272_n()) {
            ++NbtView.time;
            if (NbtView.time >= NbtView.ticksBeforeScroll.getValDouble() / (GuiScreen.func_175283_s() ? 4 : 1)) {
                NbtView.time = 0;
                ++NbtView.line_scrolled;
            }
        }
    }
    
    @Override
    public void ItemTooltipEvent(final ItemTooltipEvent event) {
        final NBTTagCompound tag = event.getItemStack().func_77978_p();
        ArrayList<String> ttip = new ArrayList<String>((int)NbtView.maxLinesShown.getValDouble());
        if (tag != null) {
            if (this.readytocopy) {
                final StringSelection selection = new StringSelection(tag.toString());
                final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                this.readytocopy = false;
            }
            event.getToolTip().add("");
            if (NbtView.showDelimiters.getValBoolean()) {
                ttip.add(TextFormatting.DARK_PURPLE + " - nbt start -");
            }
            if (NbtView.compress.getValBoolean()) {
                ttip.add(NbtView.FORMAT + tag.toString());
            }
            else {
                unwrapTag(ttip, (NBTBase)tag, NbtView.FORMAT, "", NbtView.compress.getValBoolean() ? "" : "  ");
            }
            if (NbtView.showDelimiters.getValBoolean()) {
                ttip.add(TextFormatting.DARK_PURPLE + " - nbt end -");
            }
            ttip = transformTtip(ttip);
            event.getToolTip().addAll(ttip);
        }
        else {
            event.getToolTip().add(NbtView.FORMAT + "No NBT tag");
        }
    }
    
    private static ArrayList<String> transformTtip(final ArrayList<String> ttip) {
        final ArrayList<String> newttip = new ArrayList<String>((int)NbtView.maxLinesShown.getValDouble());
        if (NbtView.showSeparator.getValBoolean()) {
            newttip.add("- NBTView -");
        }
        if (ttip.size() > NbtView.maxLinesShown.getValDouble()) {
            if (NbtView.maxLinesShown.getValDouble() + NbtView.line_scrolled > ttip.size()) {
                NbtView.line_scrolled = 0;
            }
            for (int i = 0; i < NbtView.maxLinesShown.getValDouble(); ++i) {
                newttip.add(ttip.get(i + NbtView.line_scrolled));
            }
        }
        else {
            NbtView.line_scrolled = 0;
            newttip.addAll(ttip);
        }
        return newttip;
    }
    
    static void unwrapTag(final List<String> tooltip, final NBTBase base, final String pad, @Nonnull final String tagName, final String padIncrement) {
        if (base.func_74732_a() == 10) {
            final NBTTagCompound tag = (NBTTagCompound)base;
            final NBTTagCompound nbtTagCompound;
            final boolean nested;
            tag.func_150296_c().forEach(s -> {
                nested = (nbtTagCompound.func_74781_a(s).func_74732_a() == 10 || nbtTagCompound.func_74781_a(s).func_74732_a() == 9);
                if (nested) {
                    tooltip.add(pad + s + ": {");
                    unwrapTag(tooltip, nbtTagCompound.func_74781_a(s), pad + padIncrement, s, padIncrement);
                    tooltip.add(pad + "}");
                }
                else {
                    addValueToTooltip(tooltip, nbtTagCompound.func_74781_a(s), s, pad);
                }
            });
        }
        else if (base.func_74732_a() == 9) {
            final NBTTagList tag2 = (NBTTagList)base;
            int index = 0;
            for (final NBTBase nbtnext : tag2) {
                if (nbtnext.func_74732_a() == 10 || nbtnext.func_74732_a() == 9) {
                    tooltip.add(pad + "[" + index + "]: {");
                    unwrapTag(tooltip, nbtnext, pad + padIncrement, "", padIncrement);
                    tooltip.add(pad + "}");
                }
                else {
                    tooltip.add(pad + "[" + index + "] -> " + nbtnext.toString());
                }
                ++index;
            }
        }
        else {
            addValueToTooltip(tooltip, base, tagName, pad);
        }
    }
    
    private static void addValueToTooltip(final List<String> tooltip, final NBTBase nbt, final String name, final String pad) {
        tooltip.add(pad + name + ": " + nbt.toString());
    }
    
    static {
        NbtView.line_scrolled = 0;
        NbtView.time = 0;
        FORMAT = TextFormatting.ITALIC.toString() + TextFormatting.DARK_GRAY;
    }
    
    public static class InfoWindow extends Frame
    {
        private static final long serialVersionUID = 8935325049409596603L;
        private static InfoWindow client;
        private static InfoWindow server;
        protected boolean isRemote;
        
        public InfoWindow(final String tag, final boolean isRemote) {
            this.setSize(400, 300);
            this.setTitle(I18n.func_135052_a("reader.window", new Object[] { I18n.func_135052_a("reader.side_" + (isRemote ? "client" : "server"), new Object[0]) }));
            final TextArea ta = new TextArea(tag);
            this.add(ta);
            this.isRemote = isRemote;
            this.setAutoRequestFocus(false);
            this.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(final WindowEvent e) {
                }
                
                @Override
                public void windowIconified(final WindowEvent e) {
                }
                
                @Override
                public void windowDeiconified(final WindowEvent e) {
                }
                
                @Override
                public void windowDeactivated(final WindowEvent e) {
                }
                
                @Override
                public void windowClosing(final WindowEvent e) {
                    InfoWindow.this.setVisible(false);
                    InfoWindow.this.dispose();
                    if (InfoWindow.this.isRemote) {
                        InfoWindow.client = null;
                    }
                    else {
                        InfoWindow.server = null;
                    }
                }
                
                @Override
                public void windowClosed(final WindowEvent e) {
                }
                
                @Override
                public void windowActivated(final WindowEvent e) {
                }
            });
            if (isRemote) {
                if (InfoWindow.client != null && InfoWindow.client.isValid()) {
                    this.setBounds(InfoWindow.client.getX(), InfoWindow.client.getY(), InfoWindow.client.getWidth(), InfoWindow.client.getHeight());
                    InfoWindow.client.setVisible(false);
                    InfoWindow.client.dispose();
                }
                InfoWindow.client = this;
            }
            else {
                if (InfoWindow.server != null && InfoWindow.server.isValid()) {
                    this.setBounds(InfoWindow.server.getX(), InfoWindow.server.getY(), InfoWindow.server.getWidth(), InfoWindow.server.getHeight());
                    InfoWindow.server.setVisible(false);
                    InfoWindow.server.dispose();
                }
                InfoWindow.server = this;
            }
            this.setVisible(true);
        }
        
        static {
            InfoWindow.client = null;
            InfoWindow.server = null;
        }
    }
}
