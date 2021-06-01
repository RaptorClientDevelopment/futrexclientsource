package Method.Client.utils.Patcher.Core;

import net.minecraft.launchwrapper.*;
import java.util.*;
import Method.Client.utils.Patcher.*;
import Method.Client.utils.Patcher.Patches.*;
import org.objectweb.asm.*;
import net.minecraftforge.fml.common.asm.transformers.deobf.*;

public final class ClassTransformer implements IClassTransformer
{
    private final HashMap<String, Class<? extends ModClassVisitor>> visitors;
    public static boolean obfuscated;
    
    public ClassTransformer() {
        (this.visitors = new HashMap<String, Class<? extends ModClassVisitor>>()).put("net.minecraft.block.BlockLiquid", BlockLiquidVisitor.class);
        this.visitors.put("net.minecraft.client.entity.EntityPlayerSP", EntityPlayerSPVisitor.class);
        this.visitors.put("net.minecraft.entity.player.EntityPlayer", EntityPlayerVisitor.class);
        this.visitors.put("net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer", ForgeBlockModelRendererVisitor.class);
        this.visitors.put("net.minecraft.client.multiplayer.PlayerControllerMP", PlayerControllerMPVisitor.class);
        this.visitors.put("net.minecraft.block.state.BlockStateContainer$StateImplementation", StateImplementationVisitor.class);
        this.visitors.put("net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher", TileEntityRendererDispatcherVisitor.class);
        this.visitors.put("net.minecraft.client.renderer.chunk.VisGraph", VisGraphVisitor.class);
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (!this.visitors.containsKey(transformedName)) {
            return basicClass;
        }
        System.out.println("Transforming " + transformedName + ", obfuscated=" + ClassTransformer.obfuscated);
        try {
            final ClassReader reader = new ClassReader(basicClass);
            final ClassWriter writer = new ClassWriter(3);
            reader.accept((ClassVisitor)this.visitors.get(transformedName).getConstructor(ClassVisitor.class, Boolean.TYPE).newInstance(writer, ClassTransformer.obfuscated), 0);
            return writer.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
            return basicClass;
        }
    }
    
    static {
        ClassTransformer.obfuscated = !FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/client/Minecraft").equals("net/minecraft/client/Minecraft");
    }
}
