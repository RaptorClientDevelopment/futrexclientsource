package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class PlayerControllerMPVisitor extends ModClassVisitor
{
    public PlayerControllerMPVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String blockPos = this.unmap("net/minecraft/util/math/BlockPos");
        final String enumFacing = this.unmap("net/minecraft/util/EnumFacing");
        final String onPlayerDamageBlock_name = obf ? "b" : "onPlayerDamageBlock";
        final String onPlayerDamageBlock_desc = "(L" + blockPos + ";L" + enumFacing + ";)Z";
        this.registerMethodVisitor(onPlayerDamageBlock_name, onPlayerDamageBlock_desc, OnPlayerDamageBlockVisitor::new);
    }
    
    public static class OnPlayerDamageBlockVisitor extends MethodVisitor
    {
        public OnPlayerDamageBlockVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("PlayerControllerMPVisitor.OnPlayerDamageBlockVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitVarInsn(25, 2);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "onPlayerDamageBlock", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)V", false);
        }
        
        public static void onPlayerDamageBlock(final BlockPos pos, final EnumFacing facing) {
            MinecraftForge.EVENT_BUS.post((Event)new PlayerDamageBlockEvent(pos, facing));
        }
    }
}
