package Method.Client.module.combat;

import Method.Client.module.*;
import Method.Client.*;
import java.util.concurrent.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import Method.Client.managers.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.client.event.*;
import Method.Client.utils.visual.*;
import java.text.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.*;
import Method.Client.utils.*;
import Method.Client.utils.system.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import net.minecraft.potion.*;
import java.util.*;

public class CrystalAura extends Module
{
    public Setting range;
    public Setting Explode;
    public Setting Placer;
    public Setting Damage;
    public Setting OtherDamage;
    public Setting HitDelayBetween;
    public Setting PlaceDelayBetween;
    public Setting FastSwitch;
    public Setting Packetreach;
    public Setting Hand;
    public Setting SpoofAngle;
    public Setting OverlayColor;
    public Setting Mode;
    public Setting LineWidth;
    public Setting HoleJiggle;
    public Setting MultiPlace;
    public Setting SwordHit;
    private final TimerUtils attackTimer;
    private final TimerUtils placeTimer;
    private final List<PlaceLocation> placeLocations;
    private static ExecutorService executor;
    public float[] Rots;
    
    public CrystalAura() {
        super("CrystalAura", 0, Category.COMBAT, "CrystalAura");
        this.range = Main.setmgr.add(new Setting("Hit Range", this, 6.0, 0.0, 8.0, false));
        this.Explode = Main.setmgr.add(new Setting("Explode", this, true));
        this.Placer = Main.setmgr.add(new Setting("Place Crystals", this, true));
        this.Damage = Main.setmgr.add(new Setting("Max Self Dmg", this, 14.0, 0.0, 20.0, false));
        this.OtherDamage = Main.setmgr.add(new Setting("Min Enemy Dmg", this, 4.0, 0.0, 20.0, false));
        this.HitDelayBetween = Main.setmgr.add(new Setting("Hit Delay", this, 0.2, 0.0, 1.0, false));
        this.PlaceDelayBetween = Main.setmgr.add(new Setting("Place Delay", this, 0.2, 0.0, 1.0, false));
        this.FastSwitch = Main.setmgr.add(new Setting("Fast Switch", this, true));
        this.Packetreach = Main.setmgr.add(new Setting("Packet reach", this, false));
        this.Hand = Main.setmgr.add(new Setting("Hand", this, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.SpoofAngle = Main.setmgr.add(new Setting("Spoof Angle", this, true));
        this.OverlayColor = Main.setmgr.add(new Setting("OverlayColor", this, 0.0, 1.0, 1.0, 0.62));
        this.Mode = Main.setmgr.add(new Setting("Render", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.HoleJiggle = Main.setmgr.add(new Setting("Hole Jiggle", this, true));
        this.MultiPlace = Main.setmgr.add(new Setting("MultiPlace", this, true));
        this.SwordHit = Main.setmgr.add(new Setting("SwordHit", this, false));
        this.attackTimer = new TimerUtils();
        this.placeTimer = new TimerUtils();
        this.placeLocations = new ArrayList<PlaceLocation>();
    }
    
    @Override
    public void setup() {
        CrystalAura.executor = Executors.newSingleThreadExecutor();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final ArrayList<EntityEnderCrystal> Crystals = new ArrayList<EntityEnderCrystal>();
        for (final Entity entity : CrystalAura.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityEnderCrystal) {
                Crystals.add((EntityEnderCrystal)entity);
            }
        }
        try {
            final Iterator<Entity> iterator2;
            Entity entity2;
            EntityOtherPlayerMP playerMP;
            final List<?> list;
            final Iterator<EntityEnderCrystal> iterator3;
            EntityEnderCrystal crystal;
            ArrayList<BlockPos> posable;
            int x;
            int z;
            int y;
            BlockPos blockPos;
            CrystalAura.executor.execute(() -> {
                CrystalAura.mc.field_71441_e.field_72996_f.iterator();
                while (iterator2.hasNext()) {
                    entity2 = iterator2.next();
                    if (entity2 instanceof EntityOtherPlayerMP) {
                        playerMP = (EntityOtherPlayerMP)entity2;
                        if (FriendManager.isFriend(playerMP.func_70005_c_())) {
                            continue;
                        }
                        else {
                            if (this.Explode.getValBoolean()) {
                                if (this.MultiPlace.getValBoolean()) {
                                    Collections.shuffle(list);
                                }
                                ((ArrayList<EntityEnderCrystal>)list).iterator();
                                while (iterator3.hasNext()) {
                                    crystal = iterator3.next();
                                    if (CrystalAura.mc.field_71439_g.func_70685_l((Entity)crystal) && playerMP.func_70032_d((Entity)crystal) < 12.0f && crystal.func_70032_d((Entity)CrystalAura.mc.field_71439_g) <= this.range.getValDouble() && Calculate_Damage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (Entity)CrystalAura.mc.field_71439_g) < this.Damage.getValDouble() && Calculate_Damage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (Entity)playerMP) > this.OtherDamage.getValDouble()) {
                                        this.processAttack(crystal);
                                    }
                                }
                            }
                            if (this.placeTimer.isDelay((long)(this.PlaceDelayBetween.getValDouble() * 1000.0)) && this.Placer.getValBoolean()) {
                                posable = new ArrayList<BlockPos>();
                                if (CrystalAura.mc.field_71439_g.func_70032_d((Entity)playerMP) < 13.0f) {
                                    for (x = (int)playerMP.field_70165_t - 8; x <= (int)playerMP.field_70165_t + 8; ++x) {
                                        for (z = (int)playerMP.field_70161_v - 8; z <= (int)playerMP.field_70161_v + 8; ++z) {
                                            for (y = (int)playerMP.field_70163_u - 4; y <= (int)playerMP.field_70163_u + 3; ++y) {
                                                blockPos = new BlockPos(x, y, z);
                                                if (this.canPlaceCrystal(blockPos) && Calculate_Damage(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5, (Entity)CrystalAura.mc.field_71439_g) < this.Damage.getValDouble() && Calculate_Damage(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5, (Entity)playerMP) > this.OtherDamage.getValDouble()) {
                                                    posable.add(blockPos);
                                                }
                                            }
                                        }
                                    }
                                }
                                this.placeCrystalOnBlock(posable, playerMP);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            });
        }
        catch (ConcurrentModificationException ex) {}
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.IN && packet instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packetSpawnObject = (SPacketSpawnObject)packet;
            if (packetSpawnObject.func_148993_l() == 51) {
                for (final PlaceLocation placeLocation : this.placeLocations) {
                    if (placeLocation.func_177951_i((Vec3i)new BlockPos(packetSpawnObject.func_186880_c(), packetSpawnObject.func_186882_d(), packetSpawnObject.func_186881_e())) < 2.0) {
                        placeLocation.Timeset = System.currentTimeMillis();
                        placeLocation.PacketConfirmed = true;
                    }
                }
            }
        }
        if (side == Connection.Side.OUT && this.SpoofAngle.getValBoolean() && this.Rots != null && (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation)) {
            final CPacketPlayer packet2 = (CPacketPlayer)packet;
            packet2.field_149476_e = this.Rots[0];
            packet2.field_149473_f = this.Rots[1];
        }
        return true;
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        for (final PlaceLocation placeLocation2 : this.placeLocations) {
            if (placeLocation2.PacketConfirmed) {
                RenderUtils.RenderBlock(this.Mode.getValString(), RenderUtils.Standardbb(new BlockPos(placeLocation2.field_177962_a, placeLocation2.field_177960_b, placeLocation2.field_177961_c)), this.OverlayColor.getcolor(), this.LineWidth.getValDouble());
                RenderUtils.SimpleNametag(new Vec3d((double)placeLocation2.field_177962_a, (double)placeLocation2.field_177960_b, (double)placeLocation2.field_177961_c), new DecimalFormat("0.00").format(placeLocation2.damage));
            }
        }
        this.placeLocations.removeIf(placeLocation -> placeLocation.Timeset + 1000.0 < System.currentTimeMillis());
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos) {
        try {
            if (CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z && CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h) {
                return false;
            }
            if (CrystalAura.mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a || CrystalAura.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2)).func_177230_c() != Blocks.field_150350_a) {
                return false;
            }
            for (final Object entity : CrystalAura.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(blockPos).func_72314_b(0.0, 3.0, 0.0))) {
                if ((!(entity instanceof EntityEnderCrystal) || this.MultiPlace.getValBoolean()) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityItem)) {
                    return false;
                }
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    public void placeCrystalOnBlock(final ArrayList<BlockPos> finalcheck, final EntityOtherPlayerMP playerMP) {
        for (final BlockPos pos : finalcheck) {
            final RayTraceResult result = CrystalAura.mc.field_71441_e.func_72933_a(CrystalAura.mc.field_71439_g.func_174824_e(1.0f), new Vec3d(pos.func_177958_n() + 0.5, pos.func_177956_o() - 0.5, pos.func_177952_p() + 0.5));
            EnumFacing facing;
            if (result == null || result.field_178784_b == null) {
                facing = EnumFacing.UP;
            }
            else {
                facing = result.field_178784_b;
            }
            if (CrystalAura.mc.field_71439_g.field_70163_u + CrystalAura.mc.field_71439_g.eyeHeight < pos.field_177961_c && facing == EnumFacing.UP) {
                continue;
            }
            assert result != null;
            if (result.func_178782_a().func_177951_i((Vec3i)pos) > 2.0) {
                continue;
            }
            if (CrystalAura.mc.field_71439_g.eyeHeight + CrystalAura.mc.field_71439_g.field_70163_u < pos.field_177960_b && facing.equals((Object)EnumFacing.UP)) {
                continue;
            }
            this.placeTimer.setLastMS();
            if (!CrystalAura.mc.field_71439_g.func_184614_ca().func_77973_b().equals(Items.field_185158_cP) && this.Hand.getValString().equalsIgnoreCase("Mainhand") && this.FastSwitch.getValBoolean() && this.find_in_hotbar(Items.field_185158_cP) != -1) {
                CrystalAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.find_in_hotbar(Items.field_185158_cP)));
                CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c = this.find_in_hotbar(Items.field_185158_cP);
            }
            this.TryJiggle(pos);
            EnumHand hand = null;
            if (CrystalAura.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_185158_cP) && (this.Hand.getValString().equalsIgnoreCase("Offhand") || this.Hand.getValString().equalsIgnoreCase("Either"))) {
                hand = EnumHand.OFF_HAND;
            }
            if (CrystalAura.mc.field_71439_g.func_184614_ca().func_77973_b().equals(Items.field_185158_cP) && (this.Hand.getValString().equalsIgnoreCase("Mainhand") || this.Hand.getValString().equalsIgnoreCase("Either"))) {
                hand = EnumHand.MAIN_HAND;
            }
            if (hand != null) {
                if (this.SpoofAngle.getValBoolean()) {
                    this.Rots = Utils.getNeededRotations(new Vec3d(pos.field_177962_a + 0.5, pos.field_177960_b + ((facing == EnumFacing.UP) ? 1.0 : 0.5), pos.field_177961_c + 0.5), 0.0f, 0.0f);
                    CrystalAura.mc.field_71439_g.field_70177_z = this.Rots[0];
                    CrystalAura.mc.field_71439_g.field_70125_A = this.Rots[1];
                }
                CrystalAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.5f, 0.5f, 0.5f));
                this.placeLocations.add(new PlaceLocation((double)pos.field_177962_a, (double)pos.field_177960_b, (double)pos.field_177961_c, (double)Calculate_Damage(pos.field_177962_a + 0.5, pos.field_177960_b + 1, pos.field_177961_c + 0.5, (Entity)playerMP)));
                break;
            }
        }
    }
    
    private void TryJiggle(final BlockPos pos) {
        if (this.HoleJiggle.getValBoolean() && CrystalAura.mc.field_71439_g.func_70011_f((double)pos.field_177962_a, (double)pos.field_177960_b, (double)pos.field_177961_c) > 5.0) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Math.floor(CrystalAura.mc.field_71439_g.field_70165_t) + 0.5 + ((CrystalAura.mc.field_71439_g.field_70165_t < pos.field_177962_a) ? 0.2 : -0.2), CrystalAura.mc.field_71439_g.field_70163_u, Math.floor(CrystalAura.mc.field_71439_g.field_70161_v) + 0.5 + ((CrystalAura.mc.field_71439_g.field_70161_v < pos.field_177961_c) ? 0.2 : -0.2), CrystalAura.mc.field_71439_g.field_70122_E));
        }
    }
    
    private int find_in_hotbar(final Item item) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = CrystalAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b().equals(item)) {
                return i;
            }
        }
        return -1;
    }
    
    public void processAttack(final EntityEnderCrystal entity) {
        if (this.Packetreach.getValBoolean()) {
            final double posX = entity.field_70165_t - 3.5 * Math.cos(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            final double posZ = entity.field_70161_v - 3.5 * Math.sin(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, entity.field_70163_u, posZ, Utils.getYaw((Entity)entity), Utils.getPitch((Entity)entity), CrystalAura.mc.field_71439_g.field_70122_E));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(CrystalAura.mc.field_71439_g.field_70165_t, CrystalAura.mc.field_71439_g.field_70163_u, CrystalAura.mc.field_71439_g.field_70161_v, CrystalAura.mc.field_71439_g.field_70122_E));
        }
        if (this.attackTimer.isDelay((long)(this.HitDelayBetween.getValDouble() * 1000.0))) {
            this.attackTimer.setLastMS();
            this.TryJiggle(entity.func_180425_c());
            if (this.SpoofAngle.getValBoolean()) {
                this.Rots = Utils.getNeededRotations(entity.func_174791_d().func_72441_c(0.0, 0.8, 0.0), 0.0f, 0.0f);
                CrystalAura.mc.field_71439_g.field_70177_z = this.Rots[0];
                CrystalAura.mc.field_71439_g.field_70125_A = this.Rots[1];
            }
            if (this.SwordHit.getValBoolean() && this.find_in_hotbar(Items.field_151048_u) != -1 && CrystalAura.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
                CrystalAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.find_in_hotbar(Items.field_151048_u)));
                CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c = this.find_in_hotbar(Items.field_151048_u);
            }
            if (!this.SwordHit.getValBoolean() || CrystalAura.mc.field_71439_g.func_184614_ca().func_77973_b().equals(Items.field_151048_u)) {
                Wrapper.INSTANCE.attack((Entity)entity);
                CrystalAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            }
        }
    }
    
    private static float get_damage_multiplied(final float damage) {
        final int diff = CrystalAura.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float Calculate_Damage(final double posX, final double posY, final double posZ, final Entity entity) {
        final double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0;
        final double blockDensity = entity.field_70170_p.func_72842_a(new Vec3d(posX, posY, posZ), entity.func_174813_aQ());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        if (entity instanceof EntityLivingBase) {
            return get_blast_reduction((EntityLivingBase)entity, get_damage_multiplied(damage), new Explosion((World)CrystalAura.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return 1.0f;
    }
    
    public static float get_blast_reduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            final int k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), DamageSource.func_94539_a(explosion));
            damage *= 1.0f - MathHelper.func_76131_a((float)k, 0.0f, 20.0f) / 25.0f;
            if (entity.func_70644_a((Potion)Objects.requireNonNull(MobEffects.field_76429_m))) {
                damage -= damage / 4.0f;
            }
            return Math.max(damage, 0.0f);
        }
        return CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
    }
    
    static class PlaceLocation extends Vec3i
    {
        double damage;
        boolean PacketConfirmed;
        double Timeset;
        
        private PlaceLocation(final double xIn, final double yIn, final double zIn, final double v) {
            super(xIn, yIn, zIn);
            this.damage = v;
        }
    }
}
