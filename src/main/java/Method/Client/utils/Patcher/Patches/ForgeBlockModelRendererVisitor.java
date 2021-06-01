package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class ForgeBlockModelRendererVisitor extends ModClassVisitor
{
    public ForgeBlockModelRendererVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String iBlockAccess = this.unmap("net/minecraft/world/IBlockAccess");
        final String iBakedModel = this.unmap("net/minecraft/client/renderer/block/model/IBakedModel");
        final String iBlockState = this.unmap("net/minecraft/block/state/IBlockState");
        final String blockPos = this.unmap("net/minecraft/util/math/BlockPos");
        final String bufferBuilder = this.unmap("net/minecraft/client/renderer/BufferBuilder");
        final String renderModelFlat_name = obf ? "c" : "renderModelFlat";
        final String renderModelSmooth_name = obf ? "b" : "renderModelSmooth";
        final String renderModel_desc = "(L" + iBlockAccess + ";L" + iBakedModel + ";L" + iBlockState + ";L" + blockPos + ";L" + bufferBuilder + ";ZJ)Z";
        this.registerMethodVisitor(renderModelFlat_name, renderModel_desc, RenderModelFlatVisitor::new);
        this.registerMethodVisitor(renderModelSmooth_name, renderModel_desc, RenderModelSmoothVisitor::new);
    }
    
    public static class RenderModelFlatVisitor extends MethodVisitor
    {
        public RenderModelFlatVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("ForgeBlockModelRendererVisitor.RenderModelFlatVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 3);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "renderBlockModel", "(Lnet/minecraft/block/state/IBlockState;)Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(154, l1);
            this.mv.visitInsn(3);
            this.mv.visitInsn(172);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        
        public static boolean renderBlockModel(final IBlockState state) {
            return !MinecraftForge.EVENT_BUS.post((Event)new RenderBlockModelEvent(state));
        }
    }
    
    public static class RenderModelSmoothVisitor extends MethodVisitor
    {
        public RenderModelSmoothVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("ForgeBlockModelRendererVisitor.RenderModelSmoothVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 3);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "renderBlockModel", "(Lnet/minecraft/block/state/IBlockState;)Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(154, l1);
            this.mv.visitInsn(3);
            this.mv.visitInsn(172);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        
        public static boolean renderBlockModel(final IBlockState state) {
            return !MinecraftForge.EVENT_BUS.post((Event)new RenderBlockModelEvent(state));
        }
    }
}
