package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class TileEntityRendererDispatcherVisitor extends ModClassVisitor
{
    public TileEntityRendererDispatcherVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String tileEntity = this.unmap("net/minecraft/tileentity/TileEntity");
        final String render_name = obf ? "a" : "render";
        final String render_desc = "(L" + tileEntity + ";FI)V";
        this.registerMethodVisitor(render_name, render_desc, RenderVisitor::new);
    }
    
    public static class RenderVisitor extends MethodVisitor
    {
        public RenderVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("TileEntityRendererDispatcherVisitor.RenderVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "renderTileEntity", "(Lnet/minecraft/tileentity/TileEntity;)Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(154, l1);
            this.mv.visitInsn(177);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        
        public static boolean renderTileEntity(final TileEntity tileEntity) {
            return !MinecraftForge.EVENT_BUS.post((Event)new RenderTileEntityEvent(tileEntity));
        }
    }
}
