package Method.Client.module.render;

import net.minecraft.entity.player.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import Method.Client.managers.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import Method.Client.utils.visual.*;
import com.google.common.collect.*;

public class Visualrange extends Module
{
    Setting playSound;
    Setting leaving;
    Setting Box;
    Setting color;
    Setting Mode;
    Setting LineWidth;
    Setting ShowCoords;
    private List<EntityPlayer> knownPlayers;
    public static List<EntityPlayer> logoutPositions;
    
    public Visualrange() {
        super("Visualrange", 0, Category.RENDER, "Visualrange");
        this.playSound = Main.setmgr.add(new Setting("playSound", this, true));
        this.leaving = Main.setmgr.add(new Setting("leaving", this, true));
        this.Box = Main.setmgr.add(new Setting("Box", this, true));
        this.color = Main.setmgr.add(new Setting("Logoff", this, 1.0, 1.0, 1.0, 1.0));
        this.Mode = Main.setmgr.add(new Setting("Mode", this, "Outline", this.BlockEspOptions()));
        this.LineWidth = Main.setmgr.add(new Setting("LineWidth", this, 1.0, 0.0, 3.0, false));
        this.ShowCoords = Main.setmgr.add(new Setting("ShowCoords", this, true));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final List<EntityPlayer> tempplayer = new ArrayList<EntityPlayer>();
        for (final Entity entity : Visualrange.mc.field_71441_e.func_72910_y()) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entity2 = (EntityPlayer)entity;
                if (entity2.func_70005_c_().equals(Visualrange.mc.field_71439_g.func_70005_c_())) {
                    continue;
                }
                if (!this.knownPlayers.contains(entity2)) {
                    if (this.Box.getValBoolean()) {
                        Visualrange.logoutPositions.remove(entity2);
                    }
                    this.knownPlayers.add(entity2);
                    if (this.playSound.getValBoolean()) {
                        Visualrange.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0f, 1.0f));
                    }
                    ChatUtils.message(FriendManager.isFriend(entity2.func_70005_c_()) ? ChatFormatting.GREEN.toString() : (ChatFormatting.RED.toString() + entity2.func_70005_c_() + ChatFormatting.RESET.toString() + (this.ShowCoords.getValBoolean() ? (" Joined at, x: " + entity2.func_180425_c().func_177958_n() + " y: " + entity2.func_180425_c().func_177956_o() + " z: " + entity2.func_180425_c().func_177952_p()) : " Joined!")));
                }
                tempplayer.add(entity2);
            }
        }
        for (final EntityPlayer knownPlayer : this.knownPlayers) {
            if (!tempplayer.contains(knownPlayer)) {
                this.knownPlayers.remove(knownPlayer);
                if (!this.leaving.getValBoolean()) {
                    continue;
                }
                if (this.Box.getValBoolean()) {
                    Visualrange.logoutPositions.add(knownPlayer);
                }
                if (this.playSound.getValBoolean()) {
                    Visualrange.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0f, 1.0f));
                }
                ChatUtils.message(FriendManager.isFriend(knownPlayer.func_70005_c_()) ? ChatFormatting.GREEN.toString() : (ChatFormatting.RED.toString() + knownPlayer.func_70005_c_() + ChatFormatting.RESET.toString() + (this.ShowCoords.getValBoolean() ? (" Left at, x: " + knownPlayer.func_180425_c().func_177958_n() + " y: " + knownPlayer.func_180425_c().func_177956_o() + " z: " + knownPlayer.func_180425_c().func_177952_p()) : " Left!")));
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<EntityPlayer>();
        if (Visualrange.mc.field_71439_g != null && Visualrange.mc.func_71356_B()) {
            this.toggle();
        }
    }
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.Box.getValBoolean()) {
            for (final EntityPlayer logoutPosition : Visualrange.logoutPositions) {
                final double renderPosX = logoutPosition.field_70165_t - Visualrange.mc.func_175598_ae().field_78730_l;
                final double renderPosY = logoutPosition.field_70163_u - Visualrange.mc.func_175598_ae().field_78731_m;
                final double renderPosZ = logoutPosition.field_70161_v - Visualrange.mc.func_175598_ae().field_78728_n;
                final AxisAlignedBB bb = new AxisAlignedBB(renderPosX, renderPosY, renderPosZ, renderPosX + 1.0, renderPosY + 2.0, renderPosZ + 1.0);
                RenderUtils.SimpleNametag(logoutPosition.func_174791_d(), logoutPosition.func_70005_c_() + (this.ShowCoords.getValBoolean() ? ("X: " + (int)logoutPosition.field_70165_t + " Y: " + (int)logoutPosition.field_70163_u + " Z: " + (int)logoutPosition.field_70161_v) : ""));
                RenderUtils.RenderBlock(this.Mode.getValString(), bb, FriendManager.isFriend(logoutPosition.func_70005_c_()) ? ColorUtils.rainbow().getRGB() : this.color.getcolor(), this.LineWidth.getValDouble());
            }
        }
    }
    
    static {
        Visualrange.logoutPositions = (List<EntityPlayer>)Lists.newArrayList();
    }
}
