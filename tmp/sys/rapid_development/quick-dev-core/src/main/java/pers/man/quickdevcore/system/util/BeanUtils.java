package pers.man.quickdevcore.system.util;

import pers.man.quickdevcore.system.entity.BaseDTO;
import pers.man.quickdevcore.system.entity.BaseEntity;
import pers.man.quickdevcore.system.entity.BaseVO;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 对象工具类
 * 对象转换只能处理简单的应用场景,属性名必须相同
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-12 12:37
 * @project quick-dev
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 排除class属性
     */
    private final static String EXCLUSION_PROPERTY = "class";

    /**
     * 批量DO转换VO
     *
     * @param dos   List<DO>
     * @param clazz VO的class对象
     * @return
     */
    public static List<? extends BaseVO> bathConvertToVo(List<? extends BaseEntity> dos, Class<? extends BaseVO> clazz) {
        LinkedList<BaseVO> vos = new LinkedList<>();
        dos.stream().forEach(doo -> {
            vos.add(convertToVo(doo, clazz));
        });
        return vos;
    }

    /**
     * 将DO转换成对应的VO
     * 注意:属性名必须相同才能赋值
     *
     * @param doo   DO 需要继承BaseEntity
     * @param clazz VO的class对象 需要继承BaseVO
     * @return
     */
    public static BaseVO convertToVo(BaseEntity doo, Class<? extends BaseVO> clazz) {
        BaseVO vo = null;
        try {
            BeanInfo doInfo = Introspector.getBeanInfo(doo.getClass());
            PropertyDescriptor[] doInfoPropertyDescriptors = doInfo.getPropertyDescriptors();

            vo = clazz.newInstance();
            BeanInfo voInfo = Introspector.getBeanInfo(vo.getClass());
            PropertyDescriptor[] voInfoPropertyDescriptors = voInfo.getPropertyDescriptors();

            for (PropertyDescriptor doInfoPropertyDescriptor : doInfoPropertyDescriptors) {
                if (Objects.equals(doInfoPropertyDescriptor.getName(), EXCLUSION_PROPERTY)) {
                    continue;
                }
                for (PropertyDescriptor voInfoPropertyDescriptor : voInfoPropertyDescriptors) {
                    if (Objects.equals(doInfoPropertyDescriptor.getName(), voInfoPropertyDescriptor.getName())) {
                        voInfoPropertyDescriptor.getWriteMethod().invoke(vo, doInfoPropertyDescriptor.getReadMethod().invoke(doo));
                        break;
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * 将DTO转换成对应的DO
     *
     * @param dto   DTO 需要继承BaseDTO
     * @param clazz DO的class对象 需要继承BaseEntity
     * @return
     */
    public static BaseEntity convertToDo(BaseDTO dto, Class<? extends BaseEntity> clazz) {
        BaseEntity doo = null;
        try {
            BeanInfo dtoInfo = Introspector.getBeanInfo(dto.getClass());
            PropertyDescriptor[] dtoInfoPropertyDescriptors = dtoInfo.getPropertyDescriptors();

            doo = clazz.newInstance();
            BeanInfo voInfo = Introspector.getBeanInfo(doo.getClass());
            PropertyDescriptor[] voInfoPropertyDescriptors = voInfo.getPropertyDescriptors();

            for (PropertyDescriptor dtoInfoPropertyDescriptor : dtoInfoPropertyDescriptors) {
                if (Objects.equals(dtoInfoPropertyDescriptor.getName(), EXCLUSION_PROPERTY)) {
                    continue;
                }
                for (PropertyDescriptor voInfoPropertyDescriptor : voInfoPropertyDescriptors) {
                    if (Objects.equals(dtoInfoPropertyDescriptor.getName(), voInfoPropertyDescriptor.getName())) {
                        voInfoPropertyDescriptor.getWriteMethod().invoke(doo, dtoInfoPropertyDescriptor.getReadMethod().invoke(dto));
                        break;
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return doo;
    }

}
