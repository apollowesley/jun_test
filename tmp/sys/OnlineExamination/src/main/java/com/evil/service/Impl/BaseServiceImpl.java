package com.evil.service.Impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import com.evil.dao.BaseDao;
import com.evil.service.BaseService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;

/**
 * �����BaseSerice���࣬ר�������̳�
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> baseDao;
	private Class<T> clazz;
	
	/**
	 * �ڹ��췽�������÷��䣬��ü̳и����Class����
	 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz=(Class<T>) type.getActualTypeArguments()[0];
	}

	// ע��basedao
	@Resource
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveEntity(T entity) {
		try {
			baseDao.saveEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateEntity(T entity) {
		baseDao.updateEntity(entity);
	}

	@Override
	public void deleteEntity(T entity) {
		baseDao.deleteEntity(entity);
	}

	@Override
	public void saveOrUpdateEntity(T entity) {
		baseDao.saveOrUpdateEntity(entity);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		baseDao.batchEntityByHQL(hql, objects);
	}

	@Override
	public void executeSQL(String sql, Object... objects) {
		this.baseDao.executeSQL(sql, objects);
	}

	@Override
	public T loadEntity(String id) {
		return baseDao.loadEntity(id);
	}

	@Override
	public T getEntity(String id) {
		return baseDao.getEntity(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return baseDao.findEntityByHQL(hql, objects);
	}

	public List<T> findEntityByPage(String hql, int page, int rows,
			Object... params) {
		return baseDao.findEntityByPage(hql, page, rows, params);
	}
	@Override
	public PageResult findEntityByPage(Page page, String conditions) {
		String hql = "select count(*) from "+ clazz.getSimpleName()  +" where 1=1";
		hql +=conditions;
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// �����ܼ�¼��������ҳ��Ϣ
		hql = "from "+clazz.getSimpleName()+" where 1=1";
		hql += conditions;
		List<T> list = this.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// ͨ����ҳ��Ϣȡ������
		PageResult result = new PageResult(page, list);// ��װ��ҳ��Ϣ�ͼ�¼��Ϣ�����ظ����ô�
		return result;
	}
	
	/**
	 * ��ȡ����ʵ��Ķ���
	 */
	public List<T> findAllEntities() {
		String hql="from "+clazz.getSimpleName();
		return baseDao.findEntityByHQL(hql);
	}

	/**
	 * ��ֵ������ȷ����ѯ�Ľ����Ψһ��
	 */
	public Object uniqueResult(String hql, Object... objects) {
		return this.baseDao.uniqueResult(hql, objects);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		return this.baseDao.executeSQLQuery(clazz, sql, objects);
	}

	
}
