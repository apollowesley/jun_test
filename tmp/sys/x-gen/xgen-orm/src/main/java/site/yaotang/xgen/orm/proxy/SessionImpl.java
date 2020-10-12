package site.yaotang.xgen.orm.proxy;

import java.io.Serializable;

import net.sf.cglib.proxy.Enhancer;
import site.yaotang.xgen.orm.sqltools.DetachCriteria;

/**
 * Session实现类，利用代理对象提供懒加载特性
 * @author hyt
 * @date 2018年1月1日
 */
public class SessionImpl implements Session {

	@Override
	public Object load(Class<?> clazz, Serializable id) {
		// 构造方法传递保存核心参数
		return Enhancer.create(clazz, new CGLibLazyInitializer(clazz, id));
	}

	@Override
	public Object load(Class<?> clazz, DetachCriteria dc) {
		return Enhancer.create(clazz, new CGLibLazyInitializer(clazz, dc));
	}

	@Override
	public Object load(Class<?> clazz, String condition) {
		return Enhancer.create(clazz, new CGLibLazyInitializer(clazz, condition));
	}

}
