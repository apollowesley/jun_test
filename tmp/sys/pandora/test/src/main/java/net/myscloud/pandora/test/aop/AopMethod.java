package net.myscloud.pandora.test.aop;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author Jin Xiaotian
 * @Description: TODO
 * @date 2015/10/27 16:11
 */
public class AopMethod extends MethodVisitor implements Opcodes {
    public AopMethod(int api, MethodVisitor mv) {
        super(api, mv);
    }

    public void visitCode() {
        super.visitCode();
        this.visitMethodInsn(INVOKESTATIC, "net/myscloud/pandora/test/aop/AopInterceptor", "beforeInvoke", "()V");
    }

    public void visitInsn(int opcode) {
        if (opcode == RETURN) {//在返回之前安插after 代码。
            mv.visitMethodInsn(INVOKESTATIC, "net/myscloud/pandora/test/aop/AopInterceptor", "afterInvoke", "()V");
        }
        super.visitInsn(opcode);
    }
}
