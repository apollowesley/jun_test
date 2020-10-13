package tom.cocook.ext;
import java.lang.reflect.Method;

import javassist.*;
class Hello {
    public void say() {
        System.out.println("Hello");
    }
}

public class Test {
	
	public static void create() throws Exception{

    	long a = System.currentTimeMillis();
    	ClassPool cp = ClassPool.getDefault();
    	cp.insertClassPath("tom/cocook/ext");
        /**
         * 只可使用一次类加载器, 此处如果换成Hello.class.getName() 会报错
         * 不能修改本类 tom.cocook.ext.Test时同时使用此类加载, 
         * attempted  duplicate class definition for name 试图复制的类定义的名字
         */
        CtClass cc = cp.get("tom.cocook.ext.Hello");
        cc.detach();
        String bodyString = "public static void getNumber(Integer num){System.out.println(num);}";
        CtMethod m = CtNewMethod.make(bodyString, cc);
        cc.addMethod(m);
        //String webRoot= "context.getReallPath";
        cc.writeFile("WEB-INF/classes");
        Class c = cc.toClass();
        
        Method[] mes =  c.getMethods();
        for(Method mm: mes){
        	System.out.println(mm.getName());
        }
      
    
	}
    public static void main(String[] args) throws Exception {
    	create();
    	create();
    	
    }
}
