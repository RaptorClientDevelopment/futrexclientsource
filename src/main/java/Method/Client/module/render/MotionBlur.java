package Method.Client.module.render;

import java.util.*;
import Method.Client.managers.*;
import Method.Client.*;
import Method.Client.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.lang.reflect.*;

public class MotionBlur extends Module
{
    boolean Setup;
    double old;
    private static Map domainResourceManagers;
    public static Setting blurAmount;
    
    @Override
    public void setup() {
        Main.setmgr.add(MotionBlur.blurAmount = new Setting("blurAmount", this, 1.0, 0.0, 10.0, false));
    }
    
    public MotionBlur() {
        super("MotionBlur", 0, Category.RENDER, "MotionBlur");
        this.Setup = true;
        this.old = 0.0;
    }
    
    @Override
    public void onEnable() {
        this.Setup = true;
        MotionBlur.domainResourceManagers = null;
    }
    
    @Override
    public void onDisable() {
        MotionBlur.mc.field_71460_t.func_181022_b();
        MotionBlur.domainResourceManagers = null;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.old != MotionBlur.blurAmount.getValDouble()) {
            this.old = MotionBlur.blurAmount.getValDouble();
            this.Setup = true;
            MotionBlur.domainResourceManagers = null;
            return;
        }
        if (MotionBlur.domainResourceManagers == null) {
            try {
                final Field[] declaredFields;
                final Field[] var2 = declaredFields = SimpleReloadableResourceManager.class.getDeclaredFields();
                for (final Field field : declaredFields) {
                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        MotionBlur.domainResourceManagers = (Map)field.get(MotionBlur.mc.func_110442_L());
                        break;
                    }
                }
            }
            catch (Exception var3) {
                throw new RuntimeException(var3);
            }
        }
        assert MotionBlur.domainResourceManagers != null;
        if (!MotionBlur.domainResourceManagers.containsKey("motionblur")) {
            MotionBlur.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }
        if (this.Setup) {
            MotionBlur.mc.field_71460_t.func_175069_a(new ResourceLocation("motionblur", "motionblur"));
            MotionBlur.mc.field_71460_t.func_147706_e().func_148026_a(MotionBlur.MC.field_71443_c, MotionBlur.MC.field_71440_d);
            this.Setup = false;
        }
    }
}
