package service;

import dao.IBaseDAO;


/** 基础Service接口实现抽象类 */
public abstract class BaseService implements
		IBaseService {

	private IBaseDAO baseDAO;
	
	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/** 保存对象 */
	public void save(Object obj){
		baseDAO.saveOrUpdateObject(obj);
	}
	
}
