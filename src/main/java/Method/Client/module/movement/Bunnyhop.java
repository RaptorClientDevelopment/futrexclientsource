package Method.Client.module.movement;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;

public class Bunnyhop extends Module
{
    private int airMoves;
    Setting mode;
    
    public Bunnyhop() {
        super("Bunnyhop", 0, Category.MOVEMENT, "Bunny hop");
        this.mode = Main.setmgr.add(new Setting("Mode", this, "AAC", new String[] { "AAC", "NCP", "Timer", "Spartan" }));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equalsIgnoreCase("Timer")) {
            if (Bunnyhop.mc.field_71439_g.field_70122_E) {
                if (Bunnyhop.mc.field_71439_g.field_191988_bg != 0.0f || Bunnyhop.mc.field_71439_g.field_70702_br != 0.0f) {
                    Bunnyhop.mc.field_71439_g.func_70664_aZ();
                }
                final EntityPlayerSP field_71439_g = Bunnyhop.mc.field_71439_g;
                field_71439_g.field_70179_y /= 2.0;
                final EntityPlayerSP field_71439_g2 = Bunnyhop.mc.field_71439_g;
                field_71439_g2.field_70159_w /= 2.0;
                final EntityPlayerSP field_71439_g3 = Bunnyhop.mc.field_71439_g;
                field_71439_g3.field_70181_x += 0.05000000074505806;
            }
            else {
                final EntityPlayerSP field_71439_g4 = Bunnyhop.mc.field_71439_g;
                field_71439_g4.field_70181_x -= 0.029999999329447746;
                final EntityPlayerSP field_71439_g5 = Bunnyhop.mc.field_71439_g;
                field_71439_g5.field_70702_br *= 0.07f;
                Bunnyhop.mc.field_71439_g.field_70747_aH = 0.05f;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("Spartan") && Bunnyhop.mc.field_71474_y.field_74351_w.func_151468_f() && !Bunnyhop.mc.field_71474_y.field_74314_A.func_151468_f()) {
            if (Bunnyhop.mc.field_71439_g.field_70122_E) {
                Bunnyhop.mc.field_71439_g.func_70664_aZ();
                this.airMoves = 0;
            }
            else {
                if (this.airMoves >= 3) {
                    Bunnyhop.mc.field_71439_g.field_70747_aH = 0.0275f;
                }
                if (this.airMoves >= 4 && this.airMoves % 2 == 0.0) {
                    Bunnyhop.mc.field_71439_g.field_70181_x = -0.3199999928474426 - 0.009 * Math.random();
                    Bunnyhop.mc.field_71439_g.field_70747_aH = 0.0238f;
                }
                ++this.airMoves;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("AAC")) {
            if (Bunnyhop.mc.field_71439_g.func_70090_H()) {
                return;
            }
            if (Bunnyhop.mc.field_71439_g.field_191988_bg != 0.0f || Bunnyhop.mc.field_71439_g.field_70702_br != 0.0f) {
                if (Bunnyhop.mc.field_71439_g.field_70122_E) {
                    Bunnyhop.mc.field_71439_g.func_70664_aZ();
                    final EntityPlayerSP field_71439_g6 = Bunnyhop.mc.field_71439_g;
                    field_71439_g6.field_70159_w *= 1.012;
                    final EntityPlayerSP field_71439_g7 = Bunnyhop.mc.field_71439_g;
                    field_71439_g7.field_70179_y *= 1.012;
                }
                else if (Bunnyhop.mc.field_71439_g.field_70181_x > -0.2) {
                    Bunnyhop.mc.field_71439_g.field_70747_aH = 0.08f;
                    final EntityPlayerSP field_71439_g8 = Bunnyhop.mc.field_71439_g;
                    field_71439_g8.field_70181_x += 3.1E-4;
                    Bunnyhop.mc.field_71439_g.field_70747_aH = 0.07f;
                }
            }
            else {
                Bunnyhop.mc.field_71439_g.field_70159_w = 0.0;
                Bunnyhop.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
        if (this.mode.getValString().equalsIgnoreCase("NCP") && Bunnyhop.mc.field_71439_g != null && Bunnyhop.mc.field_71441_e != null && Bunnyhop.mc.field_71474_y.field_74351_w.field_74513_e && !Bunnyhop.mc.field_71439_g.field_70123_F) {
            Bunnyhop.mc.field_71474_y.field_74314_A.field_74513_e = false;
            if (Bunnyhop.mc.field_71439_g.field_70122_E) {
                Bunnyhop.mc.field_71439_g.func_70664_aZ();
                final EntityPlayerSP field_71439_g9 = Bunnyhop.mc.field_71439_g;
                field_71439_g9.field_70159_w *= 1.0707999467849731;
                final EntityPlayerSP field_71439_g10 = Bunnyhop.mc.field_71439_g;
                field_71439_g10.field_70179_y *= 1.0707999467849731;
                final EntityPlayerSP field_71439_g11 = Bunnyhop.mc.field_71439_g;
                field_71439_g11.field_70702_br *= 2.0f;
            }
            else {
                Bunnyhop.mc.field_71439_g.field_70747_aH = 0.0265f;
            }
        }
    }
    
    @Override
    public void onDisable() {
        Bunnyhop.mc.field_71439_g.field_70747_aH = 0.03f;
        super.onDisable();
    }
}
