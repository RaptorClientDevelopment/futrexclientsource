package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.*;
import Method.Client.utils.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.*;

public class Entityspeed extends Module
{
    Setting mode;
    Setting speed;
    Setting foodview;
    Setting antiStuck;
    Setting Jump;
    Setting isAirBorne;
    Setting Modifyfalldist;
    Setting Yawlock;
    Setting boatInputsfalse;
    
    public Entityspeed() {
        super("EntitySpeed", 0, Category.MOVEMENT, "Entity Speed + Control");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "Vanilla", new String[] { "Vanilla", "Glide", "Tp", "TpUpdate" }));
        this.speed = Main.setmgr.add(new Setting("Speed", this, 1.0, 0.01, 2.0, false));
        this.foodview = Main.setmgr.add(new Setting("foodbar view", this, true));
        this.antiStuck = Main.setmgr.add(new Setting("antiStuck", this, true));
        this.Jump = Main.setmgr.add(new Setting("Horse Jump", this, false));
        this.isAirBorne = Main.setmgr.add(new Setting("Airmode Mode", this, "Default", new String[] { "Default", "False", "True" }));
        this.Modifyfalldist = Main.setmgr.add(new Setting("No Falldist", this, false));
        this.Yawlock = Main.setmgr.add(new Setting("Yawlock", this, false));
        this.boatInputsfalse = Main.setmgr.add(new Setting("Dont Row Boat", this, false));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.Jump.getValBoolean()) {
            Entityspeed.mc.field_71439_g.field_110321_bQ = 1.0f;
            Entityspeed.mc.field_71439_g.field_110320_a = -10;
        }
        if (Entityspeed.mc.field_71441_e != null && Entityspeed.mc.field_71439_g.func_184187_bx() != null) {
            final Entity riding = Entityspeed.mc.field_71439_g.func_184187_bx();
            if (this.Modifyfalldist.getValBoolean()) {
                riding.field_70143_R = 0.0f;
            }
            riding.field_70160_al = (this.isAirBorne.getValString().equalsIgnoreCase("Default") ? riding.field_70160_al : this.isAirBorne.getValString().equalsIgnoreCase("True"));
            if (Entityspeed.mc.field_71439_g.func_184218_aH()) {
                this.steerEntity(riding);
            }
        }
    }
    
    private void steerEntity(final Entity entity) {
        final double[] directionSpeedVanilla = Utils.directionSpeed(this.speed.getValDouble());
        if ((this.mode.getValString().equalsIgnoreCase("Glide") && Utils.isMovinginput()) || this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            entity.field_70159_w = directionSpeedVanilla[0];
            entity.field_70179_y = directionSpeedVanilla[1];
        }
        if (this.mode.getValString().equalsIgnoreCase("Tp")) {
            entity.func_70107_b(entity.field_70165_t + directionSpeedVanilla[0], entity.field_70163_u, entity.field_70161_v + directionSpeedVanilla[1]);
        }
        if (this.mode.getValString().equalsIgnoreCase("TpUpdate")) {
            entity.func_70080_a(entity.field_70165_t + directionSpeedVanilla[0], entity.field_70163_u, entity.field_70161_v + directionSpeedVanilla[1], entity.field_70177_z, entity.field_70125_A);
        }
        if (this.isBorderingChunk(entity, directionSpeedVanilla[0], directionSpeedVanilla[1])) {
            entity.field_70159_w = 0.0;
            entity.field_70179_y = 0.0;
        }
        if (this.Yawlock.getValBoolean()) {
            entity.field_70177_z = Entityspeed.mc.field_71439_g.field_70177_z;
        }
        if (entity instanceof EntityBoat && this.boatInputsfalse.getValBoolean()) {
            ((EntityBoat)entity).func_184442_a(false, false, false, false);
        }
    }
    
    public boolean isBorderingChunk(final Entity entity, final double motX, final double motZ) {
        return this.antiStuck.getValBoolean() && Entityspeed.mc.field_71441_e.func_72964_e((int)(entity.field_70165_t + motX) >> 4, (int)(entity.field_70161_v + motZ) >> 4) instanceof EmptyChunk;
    }
    
    @Override
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        GuiIngameForge.renderFood = this.foodview.getValBoolean();
    }
}
