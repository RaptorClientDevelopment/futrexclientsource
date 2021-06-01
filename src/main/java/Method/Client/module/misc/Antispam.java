package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import java.util.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import com.google.common.collect.*;
import net.minecraft.util.text.*;

public class Antispam extends Module
{
    Setting unicode;
    Setting broadcasts;
    Setting spam;
    Setting deletenew;
    Setting Similarity;
    public static List<Priorchatlist> priorchatlists;
    public static int line;
    public static List<ChatLine> lastChatLine;
    
    public Antispam() {
        super("Antispam", 0, Category.MISC, "Antispam");
        this.unicode = Main.setmgr.add(new Setting("Unicode", this, true));
        this.broadcasts = Main.setmgr.add(new Setting("Server Broadcasts", this, false));
        this.spam = Main.setmgr.add(new Setting("Repeated messages", this, true));
        this.deletenew = Main.setmgr.add(new Setting("Delete Repeated", this, true, this.spam, 3));
        this.Similarity = Main.setmgr.add(new Setting("Similarity %", this, 0.85, 0.0, 1.0, false, this.spam, 4));
    }
    
    @Override
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (this.spam.getValBoolean()) {
            final GuiNewChat chatinst = Antispam.mc.field_71456_v.func_146158_b();
            final String Incomingtext = event.getMessage().func_150260_c();
            for (final Priorchatlist Oldchat : Antispam.priorchatlists) {
                if (levenshteinDistance(Oldchat.fulltext, Incomingtext) >= this.Similarity.getValDouble()) {
                    final Priorchatlist priorchatlist = Oldchat;
                    ++priorchatlist.spammedtimes;
                    final String change = TextFormatting.GRAY + " (" + TextFormatting.GOLD + "x" + Oldchat.spammedtimes + TextFormatting.GRAY + ")";
                    event.getMessage().func_150258_a(change);
                    chatinst.func_146242_c(Oldchat.Removable + 1);
                    Oldchat.Removable = Antispam.line;
                    break;
                }
            }
            Antispam.priorchatlists.add(new Priorchatlist(Antispam.line, Incomingtext, 1));
            ++Antispam.line;
            if (!event.isCanceled()) {
                chatinst.func_146234_a(event.getMessage(), Antispam.line);
            }
            if (Antispam.line > 256) {
                Antispam.line = 0;
                Antispam.priorchatlists.clear();
            }
            event.setCanceled(true);
        }
        super.ClientChatReceivedEvent(event);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketChat) {
            final SPacketChat packet2 = (SPacketChat)packet;
            if (this.broadcasts.getValBoolean() && packet2.func_148915_c().func_150254_d().startsWith("§5[SERVER]")) {
                return false;
            }
            if (this.unicode.getValBoolean() && packet2.func_148915_c() instanceof TextComponentString) {
                final TextComponentString component = (TextComponentString)packet2.func_148915_c();
                final StringBuilder sb = new StringBuilder();
                boolean containsUnicode = false;
                for (final String s : component.func_150254_d().split(" ")) {
                    final StringBuilder line = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        if (c >= '\ufee0') {
                            c -= '\ufee0';
                            containsUnicode = true;
                        }
                        line.append(c);
                    }
                    sb.append((CharSequence)line).append(" ");
                }
                if (containsUnicode) {
                    packet2.field_148919_a = (ITextComponent)new TextComponentString(sb.toString());
                }
            }
        }
        return true;
    }
    
    public static double levenshteinDistance(final String s1, final String s2) {
        String longer = s1;
        String shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        final int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return (longerLength - editDistance(longer, shorter)) / longerLength;
    }
    
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        final int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); ++i) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); ++j) {
                if (i == 0) {
                    costs[j] = j;
                }
                else if (j > 0) {
                    int newValue = costs[j - 1];
                    if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                        newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                    }
                    costs[j - 1] = lastValue;
                    lastValue = newValue;
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }
    
    static {
        Antispam.priorchatlists = (List<Priorchatlist>)Lists.newArrayList();
    }
    
    public static class Priorchatlist
    {
        public int Removable;
        public String fulltext;
        public int spammedtimes;
        
        public Priorchatlist(final int Removable, final String fulltext, final int spammedtimes) {
            this.Removable = Removable;
            this.fulltext = fulltext;
            this.spammedtimes = spammedtimes;
        }
    }
}
