package Method.Client.module.render;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.settings.*;

public class Fovmod extends Module
{
    public float defaultFov;
    Setting Change;
    Setting Smooth;
    Setting FovMode;
    
    public Fovmod() {
        super("Fovmod", 0, Category.RENDER, "Fovmod");
        this.Change = Main.setmgr.add(new Setting("Change", this, 100.0, 0.0, 500.0, true));
        this.Smooth = Main.setmgr.add(new Setting("Smooth", this, true));
        this.FovMode = Main.setmgr.add(new Setting("FovMode", this, "ViewModelChanger", new String[] { "ViewModelChanger", "FovChanger", "Zoom" }));
    }
    
    @Override
    public void FOVModifier(final EntityViewRenderEvent.FOVModifier event) {
        if (this.FovMode.getValString().equalsIgnoreCase("ViewModelChanger")) {
            event.setFOV((float)this.Change.getValDouble());
        }
    }
    
    @Override
    public void onEnable() {
        this.defaultFov = Fovmod.mc.field_71474_y.field_74334_X;
    }
    
    @Override
    public void onDisable() {
        Fovmod.mc.field_71474_y.field_74334_X = this.defaultFov;
        Fovmod.mc.field_71474_y.field_74326_T = false;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        Fovmod.mc.field_71474_y.field_74326_T = this.Smooth.getValBoolean();
        if (this.FovMode.getValString().equalsIgnoreCase("FovChanger")) {
            Fovmod.mc.field_71474_y.field_74334_X = (float)this.Change.getValDouble();
        }
        if (this.FovMode.getValString().equalsIgnoreCase("Zoom")) {
            if (Fovmod.mc.field_71474_y.field_74334_X > 12.0f) {
                for (int i = 0; i < this.Change.getValDouble(); ++i) {
                    if (Fovmod.mc.field_71474_y.field_74334_X > 12.0f) {
                        final GameSettings field_71474_y = Fovmod.mc.field_71474_y;
                        field_71474_y.field_74334_X -= 0.1f;
                    }
                }
            }
            else if (Fovmod.mc.field_71474_y.field_74334_X < this.defaultFov) {
                for (int i = 0; i < this.Change.getValDouble(); ++i) {
                    final GameSettings field_71474_y2 = Fovmod.mc.field_71474_y;
                    field_71474_y2.field_74334_X += 0.1f;
                }
            }
        }
    }
}
