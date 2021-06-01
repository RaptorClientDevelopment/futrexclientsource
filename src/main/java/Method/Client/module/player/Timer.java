package Method.Client.module.player;

import Method.Client.managers.*;
import java.util.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import Method.Client.utils.*;
import net.minecraft.entity.*;

public class Timer extends Module
{
    public Setting Speed;
    public Setting OnMove;
    public Setting mode;
    public Setting RandomTiming;
    TimerUtils timer;
    public boolean switcheraro;
    Random randomno;
    
    public Timer() {
        super("Timer", 0, Category.PLAYER, "Timer");
        this.Speed = Main.setmgr.add(new Setting("Speed", this, 2.0, 0.1, 5.0, false));
        this.OnMove = Main.setmgr.add(new Setting("OnMove", this, true));
        this.mode = Main.setmgr.add(new Setting("Timer Mode", this, "Vanilla", new String[] { "Vanilla", "Even", "Odd", "Random", "PerSec" }));
        this.RandomTiming = Main.setmgr.add(new Setting("Time per sec", this, 0.5, 0.0, 5.0, false, this.mode, "PerSec", 3));
        this.timer = new TimerUtils();
        this.switcheraro = false;
        this.randomno = new Random();
    }
    
    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Vanilla")) {
            if (!this.OnMove.getValBoolean()) {
                this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
            }
            if (this.OnMove.getValBoolean() && Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
                this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Random")) {
            if (this.randomno.nextBoolean()) {
                if (!this.OnMove.getValBoolean()) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
                if (this.OnMove.getValBoolean() && Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
            }
            else {
                this.setTickLength(50.0f);
            }
        }
        else if (this.mode.getValString().equalsIgnoreCase("PerSec") && this.timer.isDelay((long)(this.RandomTiming.getValDouble() * 1000.0))) {
            this.switcheraro = !this.switcheraro;
            if (this.switcheraro) {
                if (!this.OnMove.getValBoolean()) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
                if (this.OnMove.getValBoolean() && Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
            }
            else {
                this.setTickLength(50.0f);
            }
            this.timer.setLastMS();
        }
        if (this.mode.getValString().equalsIgnoreCase("Even")) {
            if (Timer.mc.field_71439_g.field_70173_aa % 2 == 0) {
                if (!this.OnMove.getValBoolean()) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
                if (this.OnMove.getValBoolean() && Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
            }
            else {
                this.setTickLength(50.0f);
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Odd")) {
            if (Timer.mc.field_71439_g.field_70173_aa % 2 != 0) {
                if (!this.OnMove.getValBoolean()) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
                if (this.OnMove.getValBoolean() && Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
                    this.setTickLength((float)(50.0 / this.Speed.getValDouble()));
                }
            }
            else {
                this.setTickLength(50.0f);
            }
        }
        if (this.OnMove.getValBoolean() && !Utils.isMoving((Entity)Timer.mc.field_71439_g)) {
            this.setTickLength(50.0f);
        }
    }
    
    private void setTickLength(final float tickLength) {
        Timer.mc.field_71428_T.field_194149_e = 1.0f * tickLength;
    }
}
