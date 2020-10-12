/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-26 下午4:01:49
 */
package com.absir.system.test.configure;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.absir.appserv.configure.xls.XlsBase;
import com.absir.appserv.configure.xls.XlsUtils;
import com.absir.core.dyna.DynaBinder;
import com.absir.core.helper.HelperFile;
import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelLang.BreakException;
import com.absir.core.kernel.KernelLang.CallbackBreak;
import com.absir.core.kernel.KernelReflect;
import com.absir.system.test.AbstractTest;

/**
 * @author absir
 * 
 */
public class TestXlsExport extends AbstractTest {

	/** EXPORT_PATH */
	private static final String EXPORT_PATH = "/Users/absir/Desktop/";

	@Test
	public void main() throws IOException {
		Class<? extends XlsBase> beanClass = XTaskDefine.class;
		List<XlsBase> beanList = new ArrayList<XlsBase>();

		/*
		 * XCardDefine cardDefine = new XCardDefine();
		 * XCardDefine.EvolutionRequire evolutionRequire = new
		 * XCardDefine.EvolutionRequire(); KernelObject.declaredSet(cardDefine,
		 * "evolutionRequires", new EvolutionRequire[] { evolutionRequire,
		 * evolutionRequire }); beanList.add(cardDefine);
		 */
		beanList.add(newXlsBean(beanClass));
		beanList.add(newXlsBean(beanClass));
		HSSFWorkbook hssfWorkbook = XlsUtils.getWorkbook(beanList);
		OutputStream outputStream = HelperFile.openOutputStream(new File(EXPORT_PATH + beanClass.getSimpleName() + ".xls"));
		hssfWorkbook.write(outputStream);
	}

	/**
	 * @param beanClass
	 * @return
	 */
	private <T> T newXlsBean(Class<T> beanClass) {
		T bean = KernelClass.newInstance(beanClass);
		initXlsBean(bean, new HashSet<Class<?>>());
		return bean;
	}

	/** mapArray */
	private Object[] mapArray = new Object[] { new HashMap<Object, Object>(), new HashMap<Object, Object>() };

	/**
	 * @param bean
	 * @param caches
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initXlsBean(final Object bean, final Set<Class<?>> caches) {
		if (bean == null) {
			return;
		}

		if (bean instanceof Object[]) {
			for (Object o : (Object[]) bean) {
				initXlsBean(o, caches);
			}

		} else if (bean instanceof Collection) {
			for (Object o : (Collection) bean) {
				initXlsBean(o, caches);
			}

		} else if (bean instanceof Map) {
			for (Entry<Object, Object> entry : ((Map<Object, Object>) bean).entrySet()) {
				initXlsBean(entry.getKey(), caches);
				initXlsBean(entry.getValue(), caches);
			}
		}

		else if (KernelClass.isCustomClass(bean.getClass()) && caches.add(bean.getClass())) {

			KernelReflect.doWithDeclaredFields(bean.getClass(), new CallbackBreak<Field>() {

				@Override
				public void doWith(Field template) throws BreakException {
					// TODO Auto-generated method stub
					if ((template.getModifiers() & (Modifier.STATIC | Modifier.FINAL | Modifier.TRANSIENT)) == 0) {
						template.setAccessible(true);
						if (KernelReflect.get(bean, template) == null) {
							if (template.getType().isArray() || Collection.class.isAssignableFrom(template.getType())) {
								KernelReflect.set(bean, template, DynaBinder.INSTANCE.binder(mapArray, null, template.getGenericType()));

							} else {
								KernelReflect.set(bean, template, DynaBinder.INSTANCE.binder(mapArray[0], null, template.getGenericType()));
							}
						}
					}

					initXlsBean(KernelReflect.get(bean, template), caches);
				}
			});
		}
	}
}
