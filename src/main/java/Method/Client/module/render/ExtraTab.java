package Method.Client.module.render;

import Method.Client.managers.*;
import net.minecraft.client.gui.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.Screens.Override.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;

public class ExtraTab extends Module
{
    public static Setting Players;
    public static Setting ReplaceBar;
    public static Setting FriendColor;
    public static Setting FriendObfus;
    public static Setting NoHeaderFooter;
    private static GuiPlayerTabOverlay defaultscreen;
    
    public ExtraTab() {
        super("ExtraTab", 0, Category.RENDER, "ExtraTab");
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(ExtraTab.Players = new Setting("Players", this, 100.0, 1.0, 500.0, true));
        Main.setmgr.add(ExtraTab.ReplaceBar = new Setting("ReplaceBar", this, true));
        Main.setmgr.add(ExtraTab.NoHeaderFooter = new Setting("NoHeaderFooter", this, true));
        Main.setmgr.add(ExtraTab.FriendColor = new Setting("Friend Color", this, 0.0, 1.0, 1.0, 1.0));
        Main.setmgr.add(ExtraTab.FriendObfus = new Setting("FriendObfus", this, true));
    }
    
    @Override
    public void onEnable() {
        ExtraTab.defaultscreen = ExtraTab.mc.field_71456_v.field_175196_v;
        Mixintab.Run();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        return side != Connection.Side.IN || !ExtraTab.NoHeaderFooter.getValBoolean() || !(packet instanceof SPacketPlayerListHeaderFooter);
    }
    
    @Override
    public void onDisable() {
        if (ExtraTab.defaultscreen != null) {
            ExtraTab.mc.field_71456_v.field_175196_v = ExtraTab.defaultscreen;
        }
    }
}
