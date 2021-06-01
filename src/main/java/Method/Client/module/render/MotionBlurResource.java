package Method.Client.module.render;

import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;
import java.util.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.data.*;
import javax.annotation.*;

class MotionBlurResource implements IResource
{
    public ResourceLocation func_177241_a() {
        return null;
    }
    
    public InputStream func_110527_b() {
        final double amount = 0.7 + MotionBlur.blurAmount.getValDouble() / 100.0 * 3.0 - 0.01;
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}", amount, amount, amount));
    }
    
    public boolean func_110528_c() {
        return false;
    }
    
    @Nullable
    public <T extends IMetadataSection> T func_110526_a(final String s) {
        return null;
    }
    
    public String func_177240_d() {
        return null;
    }
    
    public void close() {
    }
}
