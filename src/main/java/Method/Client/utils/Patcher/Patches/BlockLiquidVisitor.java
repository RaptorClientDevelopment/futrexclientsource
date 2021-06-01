package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class BlockLiquidVisitor extends ModClassVisitor
{
    public BlockLiquidVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String iBlockState = this.unmap("net/minecraft/block/state/IBlockState");
        final String Getcollidecheck_name = obf ? "a" : "canCollideCheck";
        final String Getcollidecheck_desc = "(L" + iBlockState + ";Z)Z";
        this.registerMethodVisitor(Getcollidecheck_name, Getcollidecheck_desc, canCollideCheckVisitor::new);
    }
    
    public static class canCollideCheckVisitor extends MethodVisitor
    {
        public canCollideCheckVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("BlockLiquidVisitor.canCollideCheck.visitFieldInsn()");
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "canCollideCheckHook", "()Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(153, l1);
            this.mv.visitInsn(4);
            this.mv.visitInsn(172);
            this.mv.visitLabel(l1);
            super.visitCode();
        }
        
        public static boolean canCollideCheckHook() {
            return MinecraftForge.EVENT_BUS.post((Event)new EventCanCollide());
        }
    }
}
