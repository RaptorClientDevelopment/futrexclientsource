package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import Method.Client.utils.Patcher.Events.*;

public final class StateImplementationVisitor extends ModClassVisitor
{
    public StateImplementationVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String iBlockAccess = this.unmap("net/minecraft/world/IBlockAccess");
        final String blockPos = this.unmap("net/minecraft/util/math/BlockPos");
        final String enumFacing = this.unmap("net/minecraft/util/EnumFacing");
        final String getAmbientOcclusionLightValue_name = obf ? "j" : "getAmbientOcclusionLightValue";
        final String getAmbientOcclusionLightValue_desc = "()F";
        final String shouldSideBeRendered_name = obf ? "c" : "shouldSideBeRendered";
        final String shouldSideBeRendered_desc = "(L" + iBlockAccess + ";L" + blockPos + ";L" + enumFacing + ";)Z";
        this.registerMethodVisitor(getAmbientOcclusionLightValue_name, getAmbientOcclusionLightValue_desc, GetAmbientOcclusionLightValueVisitor::new);
        this.registerMethodVisitor(shouldSideBeRendered_name, shouldSideBeRendered_desc, ShouldSideBeRenderedVisitor::new);
    }
    
    public static class GetAmbientOcclusionLightValueVisitor extends MethodVisitor
    {
        public GetAmbientOcclusionLightValueVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitInsn(final int opcode) {
            if (opcode == 174) {
                System.out.println("StateImplementationVisitor.GetAmbientOcclusionLightValueVisitor.visitInsn()");
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "getAmbientOcclusionLightValue", "(FLnet/minecraft/block/state/IBlockState;)F", false);
            }
            super.visitInsn(opcode);
        }
        
        public static float getAmbientOcclusionLightValue(final float f, final IBlockState state) {
            final GetAmbientOcclusionLightValueEvent event = new GetAmbientOcclusionLightValueEvent(state, f);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event.getLightValue();
        }
    }
    
    public static class ShouldSideBeRenderedVisitor extends MethodVisitor
    {
        public ShouldSideBeRenderedVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitInsn(final int opcode) {
            if (opcode == 172) {
                System.out.println("StateImplementationVisitor.ShouldSideBeRenderedVisitor.visitInsn()");
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "shouldSideBeRendered", "(ZLnet/minecraft/block/state/IBlockState;)Z", false);
            }
            super.visitInsn(opcode);
        }
        
        public static boolean shouldSideBeRendered(final boolean b, final IBlockState state) {
            final ShouldSideBeRenderedEvent event = new ShouldSideBeRenderedEvent(state, b);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event.isRendered();
        }
    }
}
