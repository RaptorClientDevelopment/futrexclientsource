package Method.Client.utils.Factory;

import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraftforge.fml.client.config.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import javax.annotation.*;

public class MethodConfigGui extends GuiConfig
{
    public MethodConfigGui(final GuiScreen parent) {
        super(parent, (List)getConfigElements(), "futurex", false, false, GuiConfig.getAbridgedConfigPath(MethodConfig.getString()));
    }
    
    private static List<IConfigElement> getConfigElements() {
        return new ArrayList<IConfigElement>(MethodConfig.getConfigElements());
    }
    
    public void func_73866_w_() {
        if (this.entryList == null || this.needsRefresh) {
            this.entryList = new GuiConfigEntries(this, this.field_146297_k) {
                protected void drawContainerBackground(@Nonnull final Tessellator tessellator) {
                    if (this.field_148161_k.field_71441_e == null) {
                        super.drawContainerBackground(tessellator);
                    }
                }
            };
            this.needsRefresh = false;
        }
        super.func_73866_w_();
    }
    
    public void func_146276_q_() {
        this.func_146270_b(0);
    }
}
