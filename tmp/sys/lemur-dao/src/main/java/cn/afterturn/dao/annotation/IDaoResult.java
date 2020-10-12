package cn.afterturn.dao.annotation;

/**
 * 默认是根据返回对象标示的类型进行返回,如果用户不想写,可以直接
 * @author JueYue
 * @date 2014年12月7日 下午5:36:23
 */
public @interface IDaoResult {

    Class<?> value();

}
