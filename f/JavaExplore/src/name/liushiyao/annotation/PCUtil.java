package name.liushiyao.annotation;

import java.lang.reflect.Field;

/**
 * Created by 北岭山下 on 2017/7/19.
 */
//注解处理器
public class PCUtil {

        public static void getPCInfo(Class<?> clazz){

                String PCType = "计算机类型：";
                String PCCpu = "计算机CPU型号：";
                String PCCountry = "计算机产地：";


                /*
                                注解操作
                 */
                //获取所有操作
                Field [] fields = clazz.getDeclaredFields (  );
                for(Field field  :fields){

                        if(field.isAnnotationPresent ( PCAnnotation.type.class )){
                                //计算机类型
                                PCAnnotation.type type = field.getAnnotation ( PCAnnotation.type.class );
                                PCType = PCType + type.value ();
                                System.out.println (PCType );
                        }
                        else if(field.isAnnotationPresent ( PCAnnotation.cpu.class )){
                                //计算机CPU型号
                                PCAnnotation.cpu cpu = field.getAnnotation ( PCAnnotation.cpu.class );
                                PCCpu = PCCpu + cpu.cpu ().toString ();
                                System.out.println (PCCpu );
                        }
                        else if(field.isAnnotationPresent ( PCAnnotation.country.class )){
                                //计算机CPU信号
                                PCAnnotation.country country = field.getAnnotation ( PCAnnotation.country.class );
                                PCCountry = PCCountry + country.value ();
                                System.out.println (PCCountry );
                        }


                }



        }

}
