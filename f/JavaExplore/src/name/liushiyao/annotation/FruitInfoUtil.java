package name.liushiyao.annotation;

import java.lang.reflect.Field;

/***********注解处理器***************/

public class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz){
        
        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";
        String strFruitProvicer="供应商信息：";

        //返回程序上所有的注解，如果没有则返回null
        Field[] fields = clazz.getDeclaredFields();
        /*
        获取注解
         */
        for(Field field :fields){
            //判断是否存在指定类型的注解
            if(field.isAnnotationPresent(MyAnnotation.FruitName.class)){
                MyAnnotation.FruitName fruitName = (MyAnnotation.FruitName ) field.getAnnotation(MyAnnotation.FruitName.class);
                strFruitName=strFruitName+fruitName.value();
                System.out.println(strFruitName);
            }
            else if(field.isAnnotationPresent(MyAnnotation.FruitColor.class)){
                MyAnnotation.FruitColor fruitColor= (MyAnnotation.FruitColor ) field.getAnnotation(MyAnnotation.FruitColor.class);
                strFruitColor=strFruitColor+fruitColor.fruitColor().toString();
                System.out.println(strFruitColor);
            }
            else if(field.isAnnotationPresent(MyAnnotation.FruitProvider.class)){
                MyAnnotation.FruitProvider fruitProvider= (MyAnnotation.FruitProvider ) field.getAnnotation(MyAnnotation.FruitProvider.class);
                strFruitProvicer=" 供应商编号："+fruitProvider.id()+" 供应商名称："+fruitProvider.name()+" 供应商地址："+fruitProvider.address();
                System.out.println(strFruitProvicer);
            }
        }
    }
}