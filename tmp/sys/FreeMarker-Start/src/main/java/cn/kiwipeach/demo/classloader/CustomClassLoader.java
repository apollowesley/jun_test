package cn.kiwipeach.demo.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 自定义Java类加载器来实现Java类的热加载
 *
 * @author liuyazhuang
 */
public class CustomClassLoader extends ClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(CustomClassLoader.class);
    /**
     * 默认递归查找目标次数
     */
    private static final int DEFAULT_RECURSIONCOUNT = 10;

    /**
     * 递归次数
     */
    private int curRecursionCount = 0;

    //要加载的Java类的classpath路径
    private String classpath;

    public CustomClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    /**
     * 加载class文件中的内容
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        curRecursionCount++;
        try {
            name = name.replace(".", "//");
            FileInputStream is = new FileInputStream(new File(classpath + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                baos.write(b);
            }
            is.close();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            //文件不存在时候将延迟1s在递归加载数据，这里加载字节码文件时，会发生文件不存在异常
            logger.debug("热加载文件暂时移除，重新递归检测目标:{}", classpath + name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //达到了递归的限定次数则报错误
            if (curRecursionCount>DEFAULT_RECURSIONCOUNT){
                logger.error("尝试加载字节码文件次数达到上限:{}", DEFAULT_RECURSIONCOUNT);
                return null;
            }else {
                return loadClassData(name);
            }
        } catch (Exception e) {
            logger.error("加载字节码文件时，未知异常", e);
            return null;
        }
    }

}
