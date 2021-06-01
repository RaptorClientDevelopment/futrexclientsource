package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;
import java.text.*;
import java.util.*;
import net.minecraft.util.text.*;

public class TimeStamp extends Module
{
    Setting Formats;
    Setting Deco;
    Setting Location;
    Setting Colortype;
    Setting Textcolor;
    Setting BracketColor;
    
    public TimeStamp() {
        super("TimeStamp", 0, Category.MISC, "TimeStamp");
        this.Formats = Main.setmgr.add(new Setting("formats", this, "H24:mm", new String[] { "H24:mm", "H12:mm", "H12:mm a", "H24:mm:ss", "H12:mm:ss", "H12:mm:ss a" }));
        this.Deco = Main.setmgr.add(new Setting("Deco", this, "bracket", new String[] { "bracket", "square", "curley", "none" }));
        this.Location = Main.setmgr.add(new Setting("Location", this, "Start", new String[] { "Start", "End" }));
    }
    
    @Override
    public void setup() {
        final ArrayList<String> Color = new ArrayList<String>();
        Color.add("GREEN");
        Color.add("BLACK");
        Color.add("DARK_BLUE");
        Color.add("DARK_GREEN");
        Color.add("DARK_AQUA");
        Color.add("DARK_RED");
        Color.add("DARK_PURPLE");
        Color.add("GOLD");
        Color.add("GRAY");
        Color.add("DARK_GRAY");
        Color.add("BLUE");
        Color.add("AQUA");
        Color.add("RED");
        Color.add("LIGHT_PURPLE");
        Color.add("YELLOW");
        Color.add("WHITE");
        Main.setmgr.add(this.Colortype = new Setting("Time", this, "GREEN", Color));
        Main.setmgr.add(this.Textcolor = new Setting("Text", this, "WHITE", Color));
        Main.setmgr.add(this.BracketColor = new Setting("{C}", this, "WHITE", Color));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (packet instanceof SPacketChat) {
            final SPacketChat packet2 = (SPacketChat)packet;
            final String input = packet2.func_148915_c().func_150260_c();
            if (this.Location.getValString().equalsIgnoreCase("Start")) {
                packet2.field_148919_a = (ITextComponent)new TextComponentString(this.TmStamp() + " " + TextFormatting.func_96300_b(this.Textcolor.getValString().toLowerCase()) + input + TextFormatting.RESET);
            }
            if (this.Location.getValString().equalsIgnoreCase("End")) {
                packet2.field_148919_a = (ITextComponent)new TextComponentString(TextFormatting.func_96300_b(this.Textcolor.getValString().toLowerCase()) + input + " " + TextFormatting.RESET + this.TmStamp() + TextFormatting.RESET);
            }
        }
        return true;
    }
    
    private String TmStamp() {
        String decoLeft = "";
        String decoRight = "";
        if (this.Deco.getValString().equalsIgnoreCase("bracket")) {
            decoLeft = "(";
            decoRight = ")";
        }
        if (this.Deco.getValString().equalsIgnoreCase("square")) {
            decoLeft = "[";
            decoRight = "]";
        }
        if (this.Deco.getValString().equalsIgnoreCase("curley")) {
            decoLeft = "{";
            decoRight = "}";
        }
        final String dateFormat = this.Formats.getValString().replace("H24", "k").replace("H12", "h");
        final String date = new SimpleDateFormat(dateFormat).format(new Date());
        final TextComponentString time = new TextComponentString(TextFormatting.func_96300_b(this.BracketColor.getValString().toLowerCase()) + decoLeft + TextFormatting.RESET + TextFormatting.func_96300_b(this.Colortype.getValString().toLowerCase()) + date + TextFormatting.RESET + TextFormatting.func_96300_b(this.BracketColor.getValString().toLowerCase()) + decoRight + TextFormatting.RESET);
        return time.func_150265_g();
    }
}
