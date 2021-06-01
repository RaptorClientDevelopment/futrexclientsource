package Method.Client.module.movement;

import Method.Client.utils.*;
import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.system.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class Blink extends Module
{
    public Entity301 entity301;
    Setting Limit;
    Setting Renable;
    Setting ShowPos;
    Setting Entity;
    int limitcount;
    
    public Blink() {
        super("Blink", 0, Category.MOVEMENT, "Cancel packets and move");
        this.entity301 = null;
        this.Limit = Main.setmgr.add(new Setting("Packet limit", this, 0.0, 0.0, 500.0, true));
        this.Renable = Main.setmgr.add(new Setting("Renable", this, false));
        this.ShowPos = Main.setmgr.add(new Setting("ShowPos", this, true));
        this.Entity = Main.setmgr.add(new Setting("Entity", this, true));
        this.limitcount = 0;
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketVehicleMove && this.Entity.getValBoolean()) {
            ++this.limitcount;
            if (this.Limit.getValDouble() == 0.0) {
                return false;
            }
            if (this.Limit.getValDouble() < this.limitcount) {
                this.limitcount = 0;
                this.onoff();
                return true;
            }
        }
        if (side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
            ++this.limitcount;
            if (this.Limit.getValDouble() == 0.0) {
                return false;
            }
            if (this.Limit.getValDouble() < this.limitcount) {
                this.limitcount = 0;
                this.onoff();
                return true;
            }
        }
        return true;
    }
    
    @Override
    public void onEnable() {
        this.Enable();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.limitcount = 0;
        if (this.entity301 != null && Blink.mc.field_71441_e != null) {
            Blink.mc.field_71441_e.func_72900_e((Entity)this.entity301);
            this.entity301 = null;
        }
        super.onDisable();
    }
    
    private void onoff() {
        this.limitcount = 0;
        if (this.entity301 != null && Blink.mc.field_71441_e != null) {
            Blink.mc.field_71441_e.func_72900_e((Entity)this.entity301);
            this.entity301 = null;
        }
        if (this.Renable.getValBoolean()) {
            this.Enable();
        }
    }
    
    private void Enable() {
        this.limitcount = 0;
        if (Blink.mc.field_71439_g != null && Blink.mc.field_71441_e != null && this.ShowPos.getValBoolean()) {
            (this.entity301 = new Entity301((World)Blink.mc.field_71441_e, Blink.mc.field_71439_g.func_146103_bH())).func_70107_b(Blink.mc.field_71439_g.field_70165_t, Blink.mc.field_71439_g.field_70163_u, Blink.mc.field_71439_g.field_70161_v);
            this.entity301.field_71071_by = Blink.mc.field_71439_g.field_71071_by;
            this.entity301.field_70125_A = Blink.mc.field_71439_g.field_70125_A;
            this.entity301.field_70177_z = Blink.mc.field_71439_g.field_70177_z;
            this.entity301.field_70759_as = Blink.mc.field_71439_g.field_70759_as;
            Blink.mc.field_71441_e.func_72838_d((Entity)this.entity301);
        }
    }
}
