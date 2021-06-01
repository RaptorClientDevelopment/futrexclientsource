package Method.Client.module.misc;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import Method.Client.utils.system.*;
import Method.Client.utils.visual.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class CoordsFinder extends Module
{
    Setting BossDetector;
    Setting logLightning;
    Setting minLightningDist;
    Setting logWolf;
    Setting minWolfDist;
    Setting logPlayer;
    Setting minPlayerDist;
    
    public CoordsFinder() {
        super("CoordsFinder", 0, Category.MISC, "Coords Finder exploit");
        this.BossDetector = Main.setmgr.add(new Setting("Boss detector", this, true));
        this.logLightning = Main.setmgr.add(new Setting("logLightning", this, true));
        this.minLightningDist = Main.setmgr.add(new Setting("minLightningDist", this, 32.0, 0.0, 100.0, true, this.logLightning, 3));
        this.logWolf = Main.setmgr.add(new Setting("logWolf", this, true));
        this.minWolfDist = Main.setmgr.add(new Setting("minWolfDist", this, 256.0, 0.0, 1024.0, true, this.logWolf, 3));
        this.logPlayer = Main.setmgr.add(new Setting("logPlayer", this, true));
        this.minPlayerDist = Main.setmgr.add(new Setting("minPlayerDist", this, 256.0, 0.0, 1024.0, true, this.logPlayer, 3));
    }
    
    private boolean pastDistance(final EntityPlayer player, final BlockPos pos, final double dist) {
        return player.func_174831_c(pos) >= Math.pow(dist, 2.0);
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.BossDetector.getValBoolean() && packet instanceof SPacketEffect) {
            final SPacketEffect sPacketEffect = (SPacketEffect)packet;
            final BlockPos pos = new BlockPos(sPacketEffect.func_179746_d().field_177962_a, sPacketEffect.func_179746_d().field_177960_b, sPacketEffect.func_179746_d().field_177961_c);
            switch (sPacketEffect.func_149242_d()) {
                case 1023: {
                    ChatUtils.message("Wither Spawned " + pos.field_177962_a + " Y:" + pos.field_177960_b + " Z:" + pos.field_177961_c);
                    break;
                }
                case 1028: {
                    ChatUtils.message("Ender Dragon Defeated " + pos.field_177962_a + " Y:" + pos.field_177960_b + " Z:" + pos.field_177961_c);
                    break;
                }
                case 1038: {
                    ChatUtils.message("End Portal Activated " + pos.field_177962_a + " Y:" + pos.field_177960_b + " Z:" + pos.field_177961_c);
                    break;
                }
            }
        }
        if (this.logLightning.getValBoolean() && packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect)packet;
            if (packet2.func_186978_a() != SoundEvents.field_187754_de) {
                return true;
            }
            final BlockPos pos = new BlockPos(packet2.func_149207_d(), packet2.func_149211_e(), packet2.func_149210_f());
            if (this.pastDistance((EntityPlayer)CoordsFinder.mc.field_71439_g, pos, this.minLightningDist.getValDouble())) {
                ChatUtils.warning("Lightning strike At X:" + pos.field_177962_a + " Y:" + pos.field_177960_b + " Z:" + pos.field_177961_c);
            }
        }
        else if (packet instanceof SPacketEntityTeleport) {
            final SPacketEntityTeleport sPacketEntityTeleport = (SPacketEntityTeleport)packet;
            final Entity teleporting = CoordsFinder.mc.field_71441_e.func_73045_a(sPacketEntityTeleport.func_149451_c());
            final BlockPos pos2 = new BlockPos(sPacketEntityTeleport.func_186982_b(), sPacketEntityTeleport.func_186983_c(), sPacketEntityTeleport.func_186981_d());
            if (this.logWolf.getValBoolean() && teleporting instanceof EntityWolf) {
                if (this.pastDistance((EntityPlayer)CoordsFinder.mc.field_71439_g, pos2, this.minWolfDist.getValDouble())) {
                    ChatUtils.warning("Wolf Teleport At X:" + pos2.field_177962_a + " Y:" + pos2.field_177960_b + " Z:" + pos2.field_177961_c);
                }
            }
            else if (this.logPlayer.getValBoolean() && teleporting instanceof EntityPlayer && this.pastDistance((EntityPlayer)CoordsFinder.mc.field_71439_g, pos2, this.minPlayerDist.getValDouble())) {
                ChatUtils.warning(teleporting.func_70005_c_() + " Teleported to X:" + pos2.field_177962_a + " Y:" + pos2.field_177960_b + " Z:" + pos2.field_177961_c);
            }
        }
        return true;
    }
}
