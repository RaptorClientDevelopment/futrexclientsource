package Method.Client.utils.proxy.renderers;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class ModLayerBipedArmor extends ModLayerArmorBase<ModelBiped>
{
    public ModLayerBipedArmor(final RenderLivingBase<?> rendererIn) {
        super(rendererIn);
    }
    
    @Override
    protected void initArmor() {
        this.modelLeggings = (T)new ModelBiped(0.5f);
        this.modelArmor = (T)new ModelBiped(1.0f);
    }
    
    @Override
    protected void setModelSlotVisible(final ModelBiped modelIn, final EntityEquipmentSlot slotIn) {
        this.setModelVisible(modelIn);
        switch (slotIn) {
            case HEAD: {
                modelIn.field_78116_c.field_78806_j = true;
                modelIn.field_178720_f.field_78806_j = true;
                break;
            }
            case CHEST: {
                modelIn.field_78115_e.field_78806_j = true;
                modelIn.field_178723_h.field_78806_j = true;
                modelIn.field_178724_i.field_78806_j = true;
                break;
            }
            case LEGS: {
                modelIn.field_78115_e.field_78806_j = true;
                modelIn.field_178721_j.field_78806_j = true;
                modelIn.field_178722_k.field_78806_j = true;
                break;
            }
            case FEET: {
                modelIn.field_178721_j.field_78806_j = true;
                modelIn.field_178722_k.field_78806_j = true;
                break;
            }
        }
    }
    
    protected void setModelVisible(final ModelBiped model) {
        model.func_178719_a(false);
    }
    
    @Override
    protected ModelBiped getArmorModelHook(final EntityLivingBase entity, final ItemStack itemStack, final EntityEquipmentSlot slot, final ModelBiped model) {
        return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }
}
