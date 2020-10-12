package net.myscloud.pandora.test.aop;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.InputStream;

/**
 * @author Jin Xiaotian
 * @Description: TODO
 * @date 2015/10/27 16:11
 */
public class AopClassLoader extends ClassLoader implements Opcodes {
    public AopClassLoader(ClassLoader parent) {
        super(parent);
    }
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!name.contains("TestBean_Tmp"))
            return super.loadClass(name);
        try {
            ClassWriter cw = new ClassWriter(0);
            //
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("net/myscloud/pandora/test/aop/TestBean.class");
            ClassReader reader = new ClassReader(is);
            reader.accept(new AopClassAdapter(ASM4, cw), ClassReader.SKIP_DEBUG);
            //
            byte[] code = cw.toByteArray();
            //            FileOutputStream fos = new FileOutputStream("c:\\TestBean_Tmp.class");
            //            fos.write(code);
            //            fos.flush();
            //            fos.close();
            return this.defineClass(name, code, 0, code.length);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }
}
