package io.neural.filter;

import io.neural.extension.Extension;
import io.neural.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterChain<M> {

	private Logger logger = LoggerFactory.getLogger(FilterChain.class);

	public static final String PRE = "PRE";
	public static final String POST = "POST";
	public static final String ERROR = "ERROR";

	@SuppressWarnings("rawtypes")
	private final ConcurrentHashMap<String, List<Filter>> filters = new ConcurrentHashMap<String, List<Filter>>();

	@SuppressWarnings("rawtypes")
	public FilterChain() {
		try {
			List<Filter> filterList = ExtensionLoader.getLoader(Filter.class).getExtensions();
			if (filterList.size() > 0) {
				for (Filter filter : filterList) {
					logger.debug("The add filter: {}", filter.getClass().getName());

					Extension extension = filter.getClass().getAnnotation(Extension.class);
					if (extension != null) {
						String[] categories = extension.category();
						if (categories != null) {
							for (int i = 0; i < categories.length; i++) {
								List<Filter> temoFilterList = filters.get(categories[i]);
								if (temoFilterList == null) {
									filters.put(categories[i], temoFilterList = new ArrayList<Filter>());
								}
								temoFilterList.add(filter);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("The start neural chain is exception", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public ConcurrentHashMap<String, List<Filter>> getFilters() {
		return filters;
	}

	/**
	 * 执行复合过滤器链<br>
	 * <br>
	 * 执行流程如下：<br>
	 * <code>
	 * try{<br>
	 * <font color="green">// PRE Filter</font><br>
	 * }catch(Throwable t){<br>
	 * <font color="red">// ERROR Filter</font><br>
	 * }finally{<br>
	 * <font color="blue">// POST Filter</font><br>
	 * }<br>
	 * </code>
	 * 
	 * @param m
	 * @throws Exception
	 */
	public void doCompositeChain(M m) throws Exception {
		try {
			this.doChain(m, PRE);
		} catch (Throwable t) {
			this.doChain(m, ERROR);
			logger.error("The execute class pre filter exceptions", t);
		} finally {
			this.doChain(m, POST);
		}
	}

	/**
	 * 执行指定类型的过滤器链
	 * 
	 * @param m
	 * @param type 如果type为null则默认执行没有指定category属性的所有过滤器, 如果不为空则默认执行category中包含的特征属性过滤器
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void doChain(M m, String... type) throws Exception {
		List<Filter> tempFilters = null;
		if (type == null || type.length == 0) {
			tempFilters = filters.get("");
		} else if (type.length == 1) {
			tempFilters = filters.get(type[0]);
		} else {
			throw new IllegalArgumentException("The illegal argument 'type' length:" + type.length);
		}

		Chain<M> chain = new Chain<M>(tempFilters);
		chain.doFilter(chain, m);
	}

}
