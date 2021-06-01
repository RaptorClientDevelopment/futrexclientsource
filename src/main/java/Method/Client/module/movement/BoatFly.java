package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.renderer.entity.*;
import Method.Client.utils.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;

public class BoatFly extends Module
{
    Setting Gravity;
    Setting AllEntities;
    Setting mode;
    Setting FakePackets;
    Setting BoatClip;
    Setting BoatClipSpeed;
    Setting bypass;
    Setting Tickdelay;
    Setting UpYmotion;
    Setting DownYmotion;
    Setting PlaceBypass;
    Setting ComplexMotion;
    Setting ignoreVehicleMove;
    Setting NoKick;
    Setting ignorePlayerPosRot;
    Setting Fakerotdist;
    Setting Ncptoggle;
    Setting PacketJump;
    public static Setting BoatRender;
    public static Setting Boatblend;
    double FakerotX;
    double FakerotZ;
    boolean aBoolean;
    String updatetexture;
    int tpId;
    private int PacketLazyTimer;
    int ClipLazyTimer;
    public static ResourceLocation[] BOAT_TEXTURES;
    
    public BoatFly() {
        super("BoatFly", 0, Category.MOVEMENT, "Boat Fly");
        this.Gravity = Main.setmgr.add(new Setting("Gravity", this, true));
        this.AllEntities = Main.setmgr.add(new Setting("All Entities", this, true));
        this.mode = Main.setmgr.add(new Setting("Boat Mode", this, "Vanilla", new String[] { "Vanilla", "Fast", "Packet" }));
        this.FakePackets = Main.setmgr.add(new Setting("Fake Packet Spam", this, false));
        this.BoatClip = Main.setmgr.add(new Setting("BoatClip", this, "None", new String[] { "None", "Vanilla", "Fast" }));
        this.BoatClipSpeed = Main.setmgr.add(new Setting("BoatClipSpeed", this, 1.0, 0.5, 5.0, false, this.BoatClip, 13));
        this.bypass = Main.setmgr.add(new Setting("bypass Mode", this, "None", new String[] { "Packet", "Vanilla", "None" }));
        this.Tickdelay = Main.setmgr.add(new Setting("Tickdelay", this, 1.0, 0.0, 20.0, true, this.bypass, "Packet", 13));
        this.UpYmotion = Main.setmgr.add(new Setting("UpYmotion", this, 0.2, 0.10000000149011612, 2.0, false));
        this.DownYmotion = Main.setmgr.add(new Setting("Fallmotion", this, 0.1, 0.0, 2.0, false));
        this.PlaceBypass = Main.setmgr.add(new Setting("PlaceBypass", this, true));
        this.ComplexMotion = Main.setmgr.add(new Setting("Complex Y Motion", this, true));
        this.ignoreVehicleMove = Main.setmgr.add(new Setting("No Boat Motion", this, false));
        this.NoKick = Main.setmgr.add(new Setting("NoKick", this, false));
        this.ignorePlayerPosRot = Main.setmgr.add(new Setting("No Player Rotation", this, false));
        this.Fakerotdist = Main.setmgr.add(new Setting("Fakerotdist", this, 1.0, 0.5, 10.0, false, this.ignorePlayerPosRot, 14));
        this.Ncptoggle = Main.setmgr.add(new Setting("Ncptoggle", this, true, this.bypass, "Packet", 13));
        this.PacketJump = Main.setmgr.add(new Setting("PacketJump", this, false));
        this.aBoolean = false;
        this.updatetexture = "NULL";
        this.tpId = 0;
        this.ClipLazyTimer = 0;
    }
    
    @Override
    public void setup() {
        Main.setmgr.add(BoatFly.BoatRender = new Setting("Render", this, "Defualt", new String[] { "Defualt", "Vanish", "Rainbow", "Carpet" }));
        Main.setmgr.add(BoatFly.Boatblend = new Setting("Boatblend", this, false));
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && this.PlaceBypass.getValBoolean() && ((packet instanceof CPacketPlayerTryUseItemOnBlock && BoatFly.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBoat) || BoatFly.mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemBoat)) {
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            return false;
        }
        if (BoatFly.mc.field_71439_g.func_184187_bx() instanceof EntityBoat || (this.AllEntities.getValBoolean() && BoatFly.mc.field_71439_g.func_184187_bx() != null)) {
            final Entity e = BoatFly.mc.field_71439_g.func_184187_bx();
            if (e == null) {
                return true;
            }
            if (side == Connection.Side.OUT) {
                if (this.mode.getValString().equalsIgnoreCase("Packet") && !(packet instanceof CPacketVehicleMove) && !(packet instanceof CPacketSteerBoat) && !(packet instanceof CPacketPlayer) && packet instanceof CPacketEntityAction) {
                    final CPacketEntityAction.Action Getaction = ((CPacketEntityAction)packet).func_180764_b();
                    if (Getaction != CPacketEntityAction.Action.OPEN_INVENTORY) {
                        return false;
                    }
                }
                if (this.bypass.getValString().equalsIgnoreCase("Packet") && BoatFly.mc.field_71439_g != null) {
                    if (this.Ncptoggle.getValBoolean()) {
                        if (packet instanceof CPacketInput && !BoatFly.mc.field_71474_y.field_151444_V.func_151470_d() && !BoatFly.mc.field_71439_g.func_184187_bx().field_70122_E) {
                            ++this.PacketLazyTimer;
                            if (this.PacketLazyTimer > this.Tickdelay.getValDouble()) {
                                this.PacketLazyTimer = 0;
                                BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(e, EnumHand.MAIN_HAND));
                            }
                        }
                    }
                    else if (packet instanceof CPacketVehicleMove && this.PacketLazyTimer++ >= this.Tickdelay.getValDouble()) {
                        BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(e, EnumHand.MAIN_HAND));
                        this.PacketLazyTimer = 0;
                    }
                    else if (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketInput) {
                        return false;
                    }
                }
                if (this.ignorePlayerPosRot.getValBoolean() && BoatFly.mc.field_71439_g.field_70173_aa % 5 == 0) {
                    BoatFly.MC.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(BoatFly.mc.field_71439_g.func_184187_bx(), EnumHand.OFF_HAND));
                    BoatFly.MC.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove(BoatFly.mc.field_71439_g.func_184187_bx()));
                    BoatFly.MC.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(BoatFly.mc.field_71439_g.field_70165_t, BoatFly.mc.field_71439_g.field_70163_u, BoatFly.mc.field_71439_g.field_70161_v, BoatFly.MC.field_71439_g.field_70122_E));
                    BoatFly.MC.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTeleport(this.tpId));
                }
                if (this.bypass.getValString().equalsIgnoreCase("Vanilla")) {
                    if (packet instanceof CPacketVehicleMove) {
                        final CPacketVehicleMove cPacketVehicleMove = (CPacketVehicleMove)packet;
                        cPacketVehicleMove.field_187011_e = BoatFly.mc.field_71439_g.func_184187_bx().field_70127_C;
                        cPacketVehicleMove.field_187010_d = BoatFly.mc.field_71439_g.func_184187_bx().field_70126_B;
                        cPacketVehicleMove.field_187007_a = Double.parseDouble(null);
                        cPacketVehicleMove.field_187008_b = Double.parseDouble(null);
                        cPacketVehicleMove.field_187009_c = Double.parseDouble(null);
                    }
                    if (packet instanceof CPacketSteerBoat) {
                        BoatFly.mc.field_71439_g.field_70181_x = 0.0;
                        return e.field_70160_al = false;
                    }
                }
            }
            if (side == Connection.Side.IN) {
                if (this.ignoreVehicleMove.getValBoolean()) {
                    if (packet instanceof SPacketEntityVelocity) {
                        final Entity entity = BoatFly.mc.field_71441_e.func_73045_a(((SPacketEntityVelocity)packet).func_149412_c());
                        if (entity == BoatFly.mc.field_71439_g || entity == BoatFly.mc.field_71439_g.func_184187_bx()) {
                            return false;
                        }
                    }
                    if (packet instanceof SPacketEntity) {
                        final Entity entity = ((SPacketEntity)packet).func_149065_a((World)BoatFly.mc.field_71441_e);
                        if (entity == BoatFly.mc.field_71439_g || entity == BoatFly.mc.field_71439_g.func_184187_bx()) {
                            return false;
                        }
                    }
                    if (packet instanceof SPacketEntityHeadLook) {
                        final Entity entity = ((SPacketEntityHeadLook)packet).func_149381_a((World)BoatFly.mc.field_71441_e);
                        if (entity == BoatFly.mc.field_71439_g || entity == BoatFly.mc.field_71439_g.func_184187_bx()) {
                            return false;
                        }
                    }
                    if (packet instanceof SPacketEntityTeleport) {
                        final Entity entity = BoatFly.mc.field_71441_e.func_73045_a(((SPacketEntityTeleport)packet).func_149451_c());
                        if (entity == BoatFly.mc.field_71439_g || entity == BoatFly.mc.field_71439_g.func_184187_bx()) {
                            return false;
                        }
                    }
                    if (packet instanceof SPacketMoveVehicle && BoatFly.mc.field_71439_g.func_184187_bx() instanceof EntityBoat) {
                        return false;
                    }
                }
                if (packet instanceof SPacketPlayerPosLook && this.ignorePlayerPosRot.getValBoolean()) {
                    final SPacketPlayerPosLook pp = (SPacketPlayerPosLook)packet;
                    this.tpId = pp.func_186965_f();
                    final double d = Math.sqrt(Math.pow(this.FakerotX - pp.func_148932_c(), 2.0) + Math.pow(this.FakerotZ - pp.func_148933_e(), 2.0));
                    if (d >= this.Fakerotdist.getValDouble()) {
                        this.respondToPosLook(packet);
                        this.FakerotX = pp.func_148932_c();
                        this.FakerotZ = pp.func_148933_e();
                        return false;
                    }
                    if (BoatFly.mc.field_71439_g.func_184218_aH() && this.isBorderingChunk(BoatFly.mc.field_71439_g.func_184187_bx(), 0.0, 0.0)) {
                        this.respondToPosLook(packet);
                        this.FakerotX = pp.func_148932_c();
                        this.FakerotZ = pp.func_148933_e();
                        return false;
                    }
                    return false;
                }
                else {
                    if (this.bypass.getValString().equalsIgnoreCase("Packet") && (packet instanceof SPacketMoveVehicle || packet instanceof SPacketPlayerPosLook)) {
                        return false;
                    }
                    if (this.mode.getValString().equalsIgnoreCase("Packet")) {
                        if (packet instanceof SPacketMoveVehicle) {
                            final SPacketMoveVehicle VehicleMove = (SPacketMoveVehicle)packet;
                            return BoatFly.mc.field_71439_g.func_70011_f(VehicleMove.func_186957_a(), VehicleMove.func_186955_b(), VehicleMove.func_186956_c()) > 15.0;
                        }
                        if (packet instanceof SPacketSetPassengers) {
                            final SPacketSetPassengers Setpassengers = (SPacketSetPassengers)packet;
                            if (Setpassengers.func_186972_b() == e.func_145782_y()) {
                                final int[] func_186971_a;
                                final int[] passengerIds = func_186971_a = Setpassengers.func_186971_a();
                                for (final int i : func_186971_a) {
                                    if (i != BoatFly.mc.field_71439_g.func_145782_y() && BoatFly.mc.field_71439_g.func_70089_S() && BoatFly.mc.field_71441_e.func_175668_a(new BlockPos(BoatFly.mc.field_71439_g.field_70165_t, BoatFly.mc.field_71439_g.field_70163_u, BoatFly.mc.field_71439_g.field_70161_v), false) && !(BoatFly.mc.field_71462_r instanceof GuiDownloadTerrain) && !BoatFly.mc.field_71439_g.func_184218_aH()) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public void onDisable() {
        if (BoatFly.mc.field_71439_g.func_184187_bx() instanceof EntityBoat || (this.AllEntities.getValBoolean() && BoatFly.mc.field_71439_g.func_184187_bx() != null)) {
            if (this.Gravity.getValBoolean() && BoatFly.mc.field_71439_g.func_184187_bx().func_189652_ae()) {
                BoatFly.mc.field_71439_g.func_184187_bx().func_189654_d(false);
            }
            BoatFly.mc.field_71439_g.func_184187_bx().field_70145_X = false;
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!BoatFly.BoatRender.getValString().equalsIgnoreCase(this.updatetexture)) {
            this.updatetexture = BoatFly.BoatRender.getValString();
            if (BoatFly.BoatRender.getValString().equalsIgnoreCase("Defualt")) {
                RenderBoat.field_110782_f = BoatFly.BOAT_TEXTURES;
            }
            if (BoatFly.BoatRender.getValString().equalsIgnoreCase("Carpet")) {
                RenderBoat.field_110782_f = new ResourceLocation[] { new ResourceLocation("futurex", "carpet.png"), new ResourceLocation("futurex", "carpet.png"), new ResourceLocation("futurex", "carpet.png"), new ResourceLocation("futurex", "carpet.png"), new ResourceLocation("futurex", "carpet.png"), new ResourceLocation("futurex", "carpet.png") };
            }
        }
        if (BoatFly.mc.field_71439_g.func_184187_bx() instanceof EntityBoat || (this.AllEntities.getValBoolean() && BoatFly.mc.field_71439_g.func_184187_bx() != null)) {
            final Entity e = BoatFly.mc.field_71439_g.func_184187_bx();
            if (e == null) {
                return;
            }
            e.func_189654_d(this.Gravity.getValBoolean());
            e.field_70171_ac = true;
            e.field_70160_al = false;
            if (this.mode.getValString().equalsIgnoreCase("Fast")) {
                final double[] directionSpeedVanilla = Utils.directionSpeed(0.20000000298023224);
                e.field_70159_w = directionSpeedVanilla[0];
                e.field_70179_y = directionSpeedVanilla[1];
                BoatFly.mc.field_71439_g.field_70181_x = 0.0;
                e.field_70181_x = 0.0;
                BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(e.field_70165_t + e.field_70159_w, e.field_70163_u, e.field_70161_v + e.field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, false));
                e.field_70181_x = 0.0;
                BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(e.field_70165_t + e.field_70159_w, e.field_70163_u - 2.0, e.field_70161_v + e.field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, true));
                final Entity entity = e;
                entity.field_70163_u -= 0.2;
            }
            if (this.ComplexMotion.getValBoolean()) {
                e.field_70181_x = (BoatFly.mc.field_71474_y.field_151444_V.field_74513_e ? (-this.UpYmotion.getValDouble()) : ((BoatFly.mc.field_71439_g.field_70173_aa % 2 != 0) ? (-this.DownYmotion.getValDouble() / 10.0) : (BoatFly.mc.field_71474_y.field_74314_A.field_74513_e ? this.getUpyMotion() : (this.DownYmotion.getValDouble() / 10.0))));
            }
            else if (BoatFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                final Entity entity2 = e;
                entity2.field_70181_x += this.getUpyMotion() / 20.0;
            }
            else if (BoatFly.mc.field_71474_y.field_151444_V.func_151470_d()) {
                final Entity entity3 = e;
                entity3.field_70181_x -= this.DownYmotion.getValDouble() / 20.0;
            }
            else {
                e.field_70181_x = 0.0;
            }
            if (this.NoKick.getValBoolean()) {
                if (BoatFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    if (BoatFly.mc.field_71439_g.field_70173_aa % 8 < 2) {
                        BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = -0.03999999910593033;
                    }
                }
                else if (BoatFly.mc.field_71439_g.field_70173_aa % 8 < 4) {
                    BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = -0.07999999821186066;
                }
            }
            if (this.FakePackets.getValBoolean()) {
                this.FakePackets();
            }
            if (this.BoatClip.getValString().equalsIgnoreCase("Vanilla")) {
                e.field_70145_X = true;
                e.field_70122_E = false;
                e.field_70144_Y = 1.0f;
            }
            if (this.BoatClip.getValString().equalsIgnoreCase("Fast")) {
                this.Boatclip(e);
            }
        }
        super.onClientTick(event);
    }
    
    private double getUpyMotion() {
        if (this.PacketJump.getValBoolean()) {
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BoatFly.mc.field_71439_g, CPacketEntityAction.Action.START_RIDING_JUMP));
        }
        return this.UpYmotion.getValDouble();
    }
    
    private void FakePackets() {
        BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove());
        BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketSteerBoat(true, true));
        BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove());
        BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketSteerBoat(true, true));
    }
    
    public void respondToPosLook(final Object packet) {
        if (BoatFly.mc.field_71441_e != null && BoatFly.mc.field_71439_g != null && packet instanceof SPacketPlayerPosLook && Objects.requireNonNull(BoatFly.mc.func_147114_u()).field_147309_h) {
            final SPacketPlayerPosLook packetIn = (SPacketPlayerPosLook)packet;
            double d0 = packetIn.func_148932_c();
            double d2 = packetIn.func_148933_e();
            if (packetIn.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                d0 += BoatFly.mc.field_71439_g.field_70165_t;
            }
            if (packetIn.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                d2 += BoatFly.mc.field_71439_g.field_70161_v;
            }
            BoatFly.mc.func_147114_u().func_147297_a((Packet)new CPacketConfirmTeleport(packetIn.func_186965_f()));
            BoatFly.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.PositionRotation(d0, BoatFly.mc.field_71439_g.func_174813_aQ().field_72338_b, d2, packetIn.field_148936_d, packetIn.field_148937_e, false));
        }
    }
    
    private void Boatclip(final Entity e) {
        final CPacketVehicleMove packetVehicleMove = new CPacketVehicleMove(e);
        e.field_70122_E = false;
        if (BoatFly.mc.field_71474_y.field_74368_y.func_151470_d()) {
            packetVehicleMove.field_187008_b = -2.0;
            if (BoatFly.mc.field_71439_g.field_70163_u > 0.0) {
                final EntityPlayerSP field_71439_g = BoatFly.mc.field_71439_g;
                field_71439_g.field_70159_w -= this.getMotionX(BoatFly.mc.field_71439_g.field_70177_z);
                final EntityPlayerSP field_71439_g2 = BoatFly.mc.field_71439_g;
                field_71439_g2.field_70179_y -= this.getMotionZ(BoatFly.mc.field_71439_g.field_70177_z);
            }
        }
        this.Packet();
        this.aBoolean = !this.aBoolean;
        Objects.requireNonNull(BoatFly.mc.func_147114_u()).func_147297_a((Packet)packetVehicleMove);
        if (BoatFly.mc.field_71439_g.field_70163_u < 0.0) {
            ++this.ClipLazyTimer;
        }
        else {
            this.ClipLazyTimer = 0;
        }
        if (this.ClipLazyTimer > 20 && BoatFly.mc.field_71439_g.field_70163_u < 0.0) {
            this.ClipLazyTimer = 21;
            if (BoatFly.mc.field_71439_g.func_184218_aH()) {
                BoatFly.mc.field_71439_g.func_184210_p();
            }
            final double oldMotionX = BoatFly.mc.field_71439_g.field_70159_w;
            final double oldMotionY = BoatFly.mc.field_71439_g.field_70181_x;
            final double oldMotionZ = BoatFly.mc.field_71439_g.field_70179_y;
            if ((BoatFly.mc.field_71474_y.field_74351_w.func_151470_d() || BoatFly.mc.field_71474_y.field_74370_x.func_151470_d() || BoatFly.mc.field_71474_y.field_74366_z.func_151470_d() || BoatFly.mc.field_71474_y.field_74368_y.func_151470_d()) && !BoatFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                BoatFly.mc.field_71439_g.field_70159_w = this.getMotionX(BoatFly.mc.field_71439_g.field_71109_bG) * 0.26;
                BoatFly.mc.field_71439_g.field_70179_y = this.getMotionZ(BoatFly.mc.field_71439_g.field_71109_bG) * 0.26;
            }
            this.Packet();
            BoatFly.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.field_71439_g.field_70165_t + BoatFly.mc.field_71439_g.field_70159_w, 3.0 + BoatFly.mc.field_71439_g.field_70163_u, BoatFly.mc.field_71439_g.field_70161_v + BoatFly.mc.field_71439_g.field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, true));
            BoatFly.mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)BoatFly.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
            BoatFly.mc.field_71439_g.field_70159_w = oldMotionX;
            BoatFly.mc.field_71439_g.field_70181_x = oldMotionY;
            BoatFly.mc.field_71439_g.field_70179_y = oldMotionZ;
        }
    }
    
    public boolean isBorderingChunk(final Entity entity, final double motX, final double motZ) {
        return BoatFly.mc.field_71441_e.func_72964_e((int)(entity.field_70165_t + motX) >> 4, (int)(entity.field_70161_v + motZ) >> 4) instanceof EmptyChunk;
    }
    
    private void Packet() {
        Objects.requireNonNull(BoatFly.mc.func_147114_u()).func_147297_a((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.field_71439_g.field_70165_t + BoatFly.mc.field_71439_g.field_70159_w, BoatFly.mc.field_71439_g.field_70163_u + (this.aBoolean ? 0.0625 : (BoatFly.mc.field_71474_y.field_74314_A.func_151470_d() ? 0.0624 : 1.0E-8)) - (this.aBoolean ? 0.0625 : (BoatFly.mc.field_71474_y.field_74311_E.func_151470_d() ? 0.0624 : 2.0E-8)), BoatFly.mc.field_71439_g.field_70161_v + BoatFly.mc.field_71439_g.field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, false));
    }
    
    private double getMotionX(final float yaw) {
        return MathHelper.func_76126_a(-yaw * 0.017453292f * 1.0f) * this.BoatClipSpeed.getValDouble();
    }
    
    private double getMotionZ(final float yaw) {
        return MathHelper.func_76134_b(yaw * 0.017453292f) * 1.0f * this.BoatClipSpeed.getValDouble();
    }
    
    static {
        BoatFly.BOAT_TEXTURES = new ResourceLocation[] { new ResourceLocation("textures/entity/boat/boat_oak.png"), new ResourceLocation("textures/entity/boat/boat_spruce.png"), new ResourceLocation("textures/entity/boat/boat_birch.png"), new ResourceLocation("textures/entity/boat/boat_jungle.png"), new ResourceLocation("textures/entity/boat/boat_acacia.png"), new ResourceLocation("textures/entity/boat/boat_darkoak.png") };
    }
}
