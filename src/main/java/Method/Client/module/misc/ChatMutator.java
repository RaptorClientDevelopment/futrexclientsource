package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.*;
import java.util.*;
import java.io.*;

public class ChatMutator extends Module
{
    Setting mode;
    
    public ChatMutator() {
        super("ChatMutator", 0, Category.MISC, "ChatMutator");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "FANCY", new String[] { "LEET", "FANCY", "DUMB", "CONSOLE", "BYPASS" }));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof CPacketChatMessage) {
            final CPacketChatMessage packet2 = (CPacketChatMessage)packet;
            if (packet2.func_149439_c().startsWith("/") || packet2.func_149439_c().startsWith("&")) {
                return false;
            }
            if (this.mode.getValString().equalsIgnoreCase("BYPASS")) {
                final StringBuilder msg = new StringBuilder();
                final char[] charArray2;
                final char[] charArray = charArray2 = packet2.func_149439_c().toCharArray();
                for (final char c : charArray2) {
                    msg.append(c).append("\u0158\u015b");
                }
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketChatMessage(msg.toString().replaceFirst("%", "")));
                return false;
            }
            if (this.mode.getValString().equalsIgnoreCase("leet")) {
                packet2.field_149440_a = this.leetSpeak(packet2.field_149440_a);
            }
            if (this.mode.getValString().equalsIgnoreCase("FANCY")) {
                packet2.field_149440_a = this.fancy(packet2.field_149440_a);
            }
            if (this.mode.getValString().equalsIgnoreCase("DUMB")) {
                packet2.field_149440_a = this.retard(packet2.field_149440_a);
            }
            if (this.mode.getValString().equalsIgnoreCase("CONSOLE")) {
                packet2.field_149440_a = this.console(packet2.field_149440_a);
            }
        }
        return true;
    }
    
    public String leetSpeak(String input) {
        input = input.toLowerCase().replace("a", "4");
        input = input.toLowerCase().replace("e", "3");
        input = input.toLowerCase().replace("g", "9");
        input = input.toLowerCase().replace("h", "1");
        input = input.toLowerCase().replace("o", "0");
        input = input.toLowerCase().replace("s", "5");
        input = input.toLowerCase().replace("t", "7");
        input = input.toLowerCase().replace("i", "1");
        return input;
    }
    
    public String fancy(final String input) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : input.toCharArray()) {
            if (c >= '!' && c <= '\u0080') {
                sb.append(Character.toChars(c + '\ufee0'));
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public String retard(final String input) {
        final StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i += 2) {
            sb.replace(i, i + 1, sb.substring(i, i + 1).toUpperCase());
        }
        return sb.toString();
    }
    
    public String console(final String input) {
        final StringBuilder ret = new StringBuilder();
        final char[] unicodeChars = { '\u2e3b', '\u26d0', '\u26e8', '\u26bd', '\u26be', '\u26f7', '\u23ea', '\u23e9', '\u23eb', '\u23ec', '\u2705', '\u274c', '\u26c4' };
        for (int length = input.length(), i = 1, current = 0; i <= length || current < length; current = i, ++i) {
            if (current != 0) {
                final Random random = new Random();
                for (int j = 0; j <= 2; ++j) {
                    ret.append(unicodeChars[random.nextInt(unicodeChars.length)]);
                }
            }
            if (i <= length) {
                ret.append(input, current, i);
            }
            else {
                ret.append(input.substring(current));
            }
        }
        return ret.toString();
    }
}
