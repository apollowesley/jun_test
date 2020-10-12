package com.evil.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.dao.PaperDao;
import com.evil.pojo.system.TypeDictionary;
import com.evil.service.TypeDictionaryService;
import com.evil.util.StringUtil;

@Service("typeDictionaryService")
public class TypesDictionaryServiceImpl extends BaseServiceImpl<TypeDictionary>
		implements TypeDictionaryService {

	@Resource(name = "paperDao")
	private PaperDao paperdao;

	@Override
	@Resource(name = "typeDictionaryDao")
	public void setBaseDao(BaseDao<TypeDictionary> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public List<TypeDictionary> findDictionaryByType(int type) {
		String hql = "from TypeDictionary where type=?";
		return this.findEntityByHQL(hql, type);
	}

	@Override
	public void deleteClassifyByPid(String id) {
		String sql = "select p.id from tb_paper p where paperType=(select t.value from tb_typeDictionary t where t.id =?) ";
		String pids = StringUtil.arr2SqlStr(this.executeSQLQuery(null, sql, id)
				.toArray());
		// ɾ��Answer
		sql = "delete from tb_answers where paperid in ("+pids+")";
		this.executeSQL(sql);
		// ��ɾ���������
		sql = "delete from tb_paper_questions where paperID in ("+pids+")";
		this.executeSQL(sql);
		// ��ɾ���ɼ�
		sql = "delete from tb_user_paper where paperID in ("+pids+")";
		paperdao.executeSQL(sql);
		// ��ɾ���������µ��Ծ�
		String hql = "Update Paper set isDelete=0 where paperType in (select t.value from TypeDictionary t where t.id =?)";
		paperdao.batchEntityByHQL(hql, id);
		// ��ɾ���ֵ�����
		hql = "delete from TypeDictionary where id=?";
		this.batchEntityByHQL(hql, id);

	}

	@Override
	public int getMaxValueByType(int type) {
		String hql = "select max(value) from TypeDictionary where type=?";
		Integer count = (Integer) this.uniqueResult(hql, type);
		return count == null ? 0 : count;
	}
}
