package service;

import dao.IBaseDAO;


/** ����Service�ӿ�ʵ�ֳ����� */
public abstract class BaseService implements
		IBaseService {

	private IBaseDAO baseDAO;
	
	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/** ������� */
	public void save(Object obj){
		baseDAO.saveOrUpdateObject(obj);
	}
	
}
