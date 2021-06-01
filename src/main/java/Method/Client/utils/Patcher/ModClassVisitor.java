package Method.Client.utils.Patcher;

import org.objectweb.asm.*;
import java.util.*;
import net.minecraftforge.fml.common.asm.transformers.deobf.*;

public abstract class ModClassVisitor extends ClassVisitor
{
    private final ArrayList<MethodVisitorRegistryEntry> methodVisitorRegistry;
    
    public ModClassVisitor(final ClassVisitor cv) {
        super(262144, cv);
        this.methodVisitorRegistry = new ArrayList<MethodVisitorRegistryEntry>();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        for (final MethodVisitorRegistryEntry entry : this.methodVisitorRegistry) {
            if (name.equals(entry.name) && desc.equals(entry.desc)) {
                return entry.factory.createMethodVisitor(mv);
            }
        }
        return mv;
    }
    
    protected String unmap(final String typeName) {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(typeName);
    }
    
    protected void registerMethodVisitor(final String name, final String desc, final MethodVisitorFactory factory) {
        this.methodVisitorRegistry.add(new MethodVisitorRegistryEntry(name, desc, factory));
    }
    
    private static final class MethodVisitorRegistryEntry
    {
        private final String name;
        private final String desc;
        private final MethodVisitorFactory factory;
        
        public MethodVisitorRegistryEntry(final String name, final String desc, final MethodVisitorFactory factory) {
            this.name = name;
            this.desc = desc;
            this.factory = factory;
        }
    }
    
    public interface MethodVisitorFactory
    {
        MethodVisitor createMethodVisitor(final MethodVisitor p0);
    }
}
