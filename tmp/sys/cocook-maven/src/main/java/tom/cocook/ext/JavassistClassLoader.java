package tom.cocook.ext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class JavassistClassLoader extends ClassLoader {

    // ClassLoader 路径
    private String rootDir;

    /**
     * @param rootDir
     * @param parent
     */
    public JavassistClassLoader(String rootDir, ClassLoader parent) {
        super(parent);
        this.rootDir = rootDir;
    }

    /**
     * @param rootDir
     */
    public JavassistClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
	protected Class<?> findClass(String className)
            throws ClassNotFoundException {
        byte[] classData = null;
        try {
            classData = loadClassBytes(className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class<?> c = defineClass(className, classData, 0, classData.length);
        return c;
    }

    /**
     * 加载Class的byte
     * 
     * @param className
     * @return
     * @throws IOException
     */
    private byte[] loadClassBytes(String className) throws IOException {
        String classFile = getClassFile(className);
        FileInputStream fis = new FileInputStream(classFile);
        FileChannel fileC = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel outC = Channels.newChannel(baos);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            int i = fileC.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();
            outC.write(buffer);
            buffer.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

    /**
     * 获取Class文件
     * 
     * @param name
     * @return
     */
    private String getClassFile(String name) {
        int len = name.length();
        char[] nameChars = new char[len];
        name.getChars(0, len, nameChars, 0);
        for (int i = 0; i < len; i++) {
            if (nameChars[i] == '.') {
                nameChars[i] = File.separatorChar;
            }
        }
        StringBuilder sb = new StringBuilder(rootDir);
        sb.append(File.separator).append(nameChars).append(".class");
        return sb.toString();
    }
}