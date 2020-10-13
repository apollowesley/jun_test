package com.laycms.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EntityView {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(EntityView.class);

	private List<Criterion> criterionList = new ArrayList<Criterion>();
	private Class<?> entityClass;
	private List<Order> orderList = new ArrayList<Order>();

	public EntityView() {
		super();
	}

	public EntityView(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;

	}

	public EntityView(Map<String, Object> map) {
		this.add(map);
	}

	public EntityView(Map<String, Object> map, boolean isFuzzyQuery) {
		this.add(map, isFuzzyQuery);
	}

	public EntityView add(Criterion criterion) {
		this.criterionList.add(criterion);
		return this;
	}

	public EntityView addOrder(Order order) {
		this.orderList.add(order);
		return this;
	}

	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	/**
	 * @functionDescription :将Map转化为entityView,翻译为sql相当于 where key1=value1 and key2=value2 and key3 like '%value3%'
	 * @param map
	 * @param isFuzzyQuery 是否为模糊查询，模糊查询时，运算符翻译为like
	 * @return
	 */
	public EntityView add(Map<String, Object> map, boolean isFuzzyQuery) {
		for (Iterator<Entry<String, Object>> ite = map.entrySet().iterator(); ite.hasNext();) {
			Entry<String, Object> entry = ite.next();
			Object value = entry.getValue();
			if (isFuzzyQuery && value instanceof String) {
				this.criterionList.add(Restrictions.like(entry.getKey(), "%" + value.toString().trim() + "%"));
			} else {
				this.criterionList.add(Restrictions.eq(entry.getKey(), value));
			}

		}
		return this;
	}

	public EntityView add(Map<String, Object> map) {
		return this.add(map, false);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (criterionList.size() > 0) {
			sb.append("where 1=1");
			for (Criterion criterion : criterionList) {
				sb.append(" and ").append(criterion);
			}
		}
		if (orderList.size() > 0) {
			sb.append(" order by ");
			for (Order order : orderList) {
				sb.append(order).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.length() > 0 ? sb.toString() : super.toString();
	}

	public static void main(String[] args) {
		EntityView ev = new EntityView();
		ev.add(Restrictions.le("score", 60));
		ev.add(Restrictions.between("date", new Date(), new Date()));
		ev.add(Restrictions.like("id", 60));
		ev.addOrder(Order.asc("number"));
		ev.addOrder(Order.desc("name"));
		System.out.print(ev.toString());
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * @functionDescription :设置该EntityView对应的实体
	 * @param entityClass
	 */
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
}
