package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class VisGraphVisitor extends ModClassVisitor
{
    public VisGraphVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String blockPos = this.unmap("net/minecraft/util/math/BlockPos");
        final String setOpaqueCube_name = obf ? "a" : "setOpaqueCube";
        final String setOpaqueCube_desc = "(L" + blockPos + ";)V";
        this.registerMethodVisitor(setOpaqueCube_name, setOpaqueCube_desc, SetOpaqueCubeVisitor::new);
    }
    
    public static class SetOpaqueCubeVisitor extends MethodVisitor
    {
        public SetOpaqueCubeVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("VisGraphVisitor.SetOpaqueCubeVisitor.visitCode()");
            super.visitCode();
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "setOpaqueCube", "()Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(154, l1);
            this.mv.visitInsn(177);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        
        public static boolean setOpaqueCube() {
            return !MinecraftForge.EVENT_BUS.post((Event)new SetOpaqueCubeEvent());
        }
    }
}
