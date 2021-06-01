package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import Method.Client.utils.Patcher.Events.*;

public final class EntityPlayerSPVisitor extends ModClassVisitor
{
    public EntityPlayerSPVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String onUpdatedesc = "()V";
        final String moverType = this.unmap("net/minecraft/entity/MoverType");
        final String onUpdate = obf ? "B_" : "onUpdate";
        final String moveEntity_name = obf ? "a" : "move";
        final String moveEntity_desc = "(L" + moverType + ";DDD)V";
        this.registerMethodVisitor(onUpdate, onUpdatedesc, onUpdateplayer::new);
        this.registerMethodVisitor(moveEntity_name, moveEntity_desc, MoveEntityVisitor::new);
    }
    
    public static class onUpdateplayer extends MethodVisitor
    {
        public onUpdateplayer(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("EntityPlayerSPVisitor.onUpdateplayer.visitCode()");
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "onPreMotion", "(Lnet/minecraft/client/entity/EntityPlayerSP;)V", false);
            super.visitCode();
        }
        
        public void visitInsn(final int opcode) {
            if (opcode == 177) {
                System.out.println("EntityPlayerSPVisitor.onUpdateplayer.visitInsn()");
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "onPostMotion", "(Lnet/minecraft/client/entity/EntityPlayerSP;)V", false);
            }
            super.visitInsn(opcode);
        }
        
        public static void onPreMotion(final EntityPlayerSP player) {
            MinecraftForge.EVENT_BUS.post((Event)new PreMotionEvent(player));
        }
        
        public static void onPostMotion(final EntityPlayerSP player) {
            MinecraftForge.EVENT_BUS.post((Event)new PostMotionEvent(player));
        }
    }
    
    public static class MoveEntityVisitor extends MethodVisitor
    {
        public MoveEntityVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("EntityPlayerSPVisitor.MoveEntityVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "onPlayerMove", "(Lnet/minecraft/client/entity/EntityPlayerSP;)V", false);
        }
        
        public static void onPlayerMove(final EntityPlayerSP player) {
            MinecraftForge.EVENT_BUS.post((Event)new PlayerMoveEvent(player));
        }
    }
}
