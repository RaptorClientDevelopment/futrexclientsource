package Method.Client.utils.Patcher.Patches;

import Method.Client.utils.Patcher.*;
import org.objectweb.asm.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;
import Method.Client.utils.Patcher.Events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class EntityPlayerVisitor extends ModClassVisitor
{
    public EntityPlayerVisitor(final ClassVisitor cv, final boolean obf) {
        super(cv);
        final String jump_name = obf ? "cu" : "jump";
        final String jump_desc = "()V";
        this.registerMethodVisitor(jump_name, jump_desc, JumpVisitor::new);
    }
    
    public static class JumpVisitor extends MethodVisitor
    {
        public JumpVisitor(final MethodVisitor mv) {
            super(262144, mv);
        }
        
        public void visitCode() {
            System.out.println("EntityPlayerVisitor.JumpVisitor.visitCode()");
            super.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(184, Type.getInternalName((Class)this.getClass()), "entityPlayerJump", "(Lnet/minecraft/entity/player/EntityPlayer;)Z", false);
            final Label l1 = new Label();
            this.mv.visitJumpInsn(154, l1);
            this.mv.visitInsn(177);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        
        public static boolean entityPlayerJump(final EntityPlayer player) {
            return !MinecraftForge.EVENT_BUS.post((Event)new EntityPlayerJumpEvent(player));
        }
    }
}
