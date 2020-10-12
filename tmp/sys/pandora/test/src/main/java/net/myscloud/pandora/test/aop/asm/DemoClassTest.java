package net.myscloud.pandora.test.aop.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import javax.management.DescriptorKey;
import java.io.IOException;

/**
 * @author Jin Xiaotian
 * @Description: TODO
 * @date 2015/10/27 17:47
 */
public class DemoClassTest {
    public static void main(String[] args) throws IOException {
        ClassReader cr = new ClassReader(DemoClass.class.getName());
        cr.accept(new DemoClassVisitor(), ClassReader.SKIP_DEBUG);
        System.out.println("---ALL END---");
    }
}

class DemoClass {
    @DescriptorKey("")
    public static void main(String[] args) {
        System.out.println();
    }
}

class DemoClassVisitor extends ClassVisitor {
    public DemoClassVisitor() {
        super(Opcodes.ASM4);
    }
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("at Method name " + name);
        System.out.println("at Method signature " + signature);
        System.out.println("at Method access " + access);
        System.out.println("at Method desc " + desc);
        //
        MethodVisitor superMV = super.visitMethod(access, name, desc, signature, exceptions);
        return new DemoMethodVisitor(superMV, name);
    }
}
class DemoMethodVisitor extends MethodVisitor {
    private String methodName;
    public DemoMethodVisitor(MethodVisitor mv, String methodName) {
        super(Opcodes.ASM4, mv);
        this.methodName = methodName;
    }
    public void visitCode() {
        System.out.println("at Method ‘" + methodName + "’ Begin...");
        super.visitCode();
    }
    public void visitEnd() {
        System.out.println("at Method ‘" + methodName + "’End.");
        super.visitEnd();
    }
}