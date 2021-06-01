package Method.Client.module.misc;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import net.minecraftforge.event.entity.player.*;
import java.nio.charset.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.client.config.*;
import Method.Client.utils.visual.*;
import Method.Client.utils.font.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class ToolTipPlus extends Module
{
    Setting TooltipModify;
    Setting Customfont;
    Setting CustomBackground;
    Setting Color;
    Setting Outline;
    Setting OutlineColor;
    Setting ItemSize;
    
    public ToolTipPlus() {
        super("ToolTipPlus", 0, Category.MISC, "ToolTipPlus Item Size");
        this.TooltipModify = Main.setmgr.add(new Setting("TooltipModify", this, true));
        this.Customfont = Main.setmgr.add(new Setting("Customfont", this, true, this.TooltipModify, 2));
        this.CustomBackground = Main.setmgr.add(new Setting("CustomBackground", this, false, this.TooltipModify, 3));
        this.Color = Main.setmgr.add(new Setting("Color", this, 0.1, 1.0, 0.95, 0.8, this.TooltipModify, 4));
        this.Outline = Main.setmgr.add(new Setting("Outline", this, false, this.TooltipModify, 5));
        this.OutlineColor = Main.setmgr.add(new Setting("OutlineColor", this, 0.55, 1.0, 1.0, 1.0, this.TooltipModify, 6));
        final SettingsManager setmgr = Main.setmgr;
        final Setting setting = new Setting("ItemSize", this, true);
        this.ItemSize = setting;
        this.ItemSize = setmgr.add(setting);
    }
    
    public String bytesToString(final int count) {
        if (count >= 1024) {
            return String.format("%.2f kb", count / 1024.0f);
        }
        return String.format("%d bytes", count);
    }
    
    @Override
    public void ItemTooltipEvent(final ItemTooltipEvent event) {
        if (!this.ItemSize.getValBoolean()) {
            return;
        }
        final String interesting = String.valueOf(event.getItemStack().func_77978_p());
        final byte[] utf8Bytes = interesting.getBytes(StandardCharsets.UTF_8);
        event.getToolTip().add(" " + this.bytesToString(utf8Bytes.length) + " TextSize");
        final String dd = String.valueOf(event.getItemStack().func_151000_E());
        final byte[] ee = dd.getBytes(StandardCharsets.UTF_8);
        event.getToolTip().add(" " + this.bytesToString(ee.length) + " TagSize");
    }
    
    @Override
    public void RendertooltipPre(final RenderTooltipEvent.Pre event) {
        if (!this.TooltipModify.getValBoolean()) {
            return;
        }
        final int mouseX = event.getX();
        final int screenWidth = event.getScreenWidth();
        final int screenHeight = event.getScreenHeight();
        final int maxTextWidth = event.getMaxWidth();
        List<String> textLines = (List<String>)event.getLines();
        final FontRenderer font = event.getFontRenderer();
        GlStateManager.func_179101_C();
        RenderHelper.func_74518_a();
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        int tooltipTextWidth = 0;
        for (final String textLine : textLines) {
            final int textLineWidth = font.func_78256_a(textLine);
            if (textLineWidth > tooltipTextWidth) {
                tooltipTextWidth = textLineWidth;
            }
        }
        boolean needsWrap = false;
        int titleLinesCount = 1;
        int tooltipX = mouseX + 12;
        if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
            tooltipX = mouseX - 16 - tooltipTextWidth;
            if (tooltipX < 4) {
                if (mouseX > screenWidth / 2) {
                    tooltipTextWidth = mouseX - 12 - 8;
                }
                else {
                    tooltipTextWidth = screenWidth - 16 - mouseX;
                }
                needsWrap = true;
            }
        }
        if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
            tooltipTextWidth = maxTextWidth;
            needsWrap = true;
        }
        if (needsWrap) {
            int wrappedTooltipWidth = 0;
            final List<String> wrappedTextLines = new ArrayList<String>();
            for (int i = 0; i < textLines.size(); ++i) {
                final String textLine2 = textLines.get(i);
                final List<String> wrappedLine = (List<String>)font.func_78271_c(textLine2, tooltipTextWidth);
                if (i == 0) {
                    titleLinesCount = wrappedLine.size();
                }
                for (final String line : wrappedLine) {
                    final int lineWidth = font.func_78256_a(line);
                    if (lineWidth > wrappedTooltipWidth) {
                        wrappedTooltipWidth = lineWidth;
                    }
                    wrappedTextLines.add(line);
                }
            }
            tooltipTextWidth = wrappedTooltipWidth;
            textLines = wrappedTextLines;
            if (mouseX > screenWidth / 2) {
                tooltipX = mouseX - 16 - tooltipTextWidth;
            }
            else {
                tooltipX = mouseX + 12;
            }
        }
        int tooltipY = event.getY() - 12;
        int tooltipHeight = 8;
        if (textLines.size() > 1) {
            tooltipHeight += (textLines.size() - 1) * 10;
            if (textLines.size() > titleLinesCount) {
                tooltipHeight += 2;
            }
        }
        if (tooltipY < 4) {
            tooltipY = 4;
        }
        else if (tooltipY + tooltipHeight + 4 > screenHeight) {
            tooltipY = screenHeight - tooltipHeight - 4;
        }
        if (!this.CustomBackground.getValBoolean()) {
            final int zLevel = 300;
            final int backgroundColor = -267386864;
            final int borderColorStart = 1347420415;
            final int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | (borderColorStart & 0xFF000000);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(300, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(300, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            GuiUtils.drawGradientRect(300, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            GuiUtils.drawGradientRect(300, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);
        }
        else {
            Gui.func_73734_a(tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, this.Color.getcolor());
            if (this.Outline.getValBoolean()) {
                RenderUtils.drawRectOutline(tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, 1.0, this.OutlineColor.getcolor());
            }
        }
        for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
            final String line2 = textLines.get(lineNumber);
            if (this.Customfont.getValBoolean()) {
                CFont.tfontRenderer22.drawStringWithShadow(line2, tooltipX, tooltipY, -1);
            }
            else {
                font.func_175063_a(line2, (float)tooltipX, (float)tooltipY, -1);
            }
            if (lineNumber + 1 == titleLinesCount) {
                tooltipY += 2;
            }
            tooltipY += 10;
        }
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
        RenderHelper.func_74519_b();
        GlStateManager.func_179091_B();
        event.setCanceled(true);
    }
}
