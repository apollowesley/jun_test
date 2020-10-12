package com.evil.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.ExamType;
import com.evil.service.ExamTypeService;

@Service("examTypeService")
public class ExamTypeServiceImpl extends
		BaseServiceImpl<ExamType> implements ExamTypeService {

	@Override
	@Resource(name = "examTypeDao")
	public void setBaseDao(BaseDao<ExamType> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public void deleteExamType(ExamType model) {
		
		//��ɾ���������µ����п���
		String sql="delete from tb_exam where examTypeId=?";
		this.executeSQL(sql, model.getId());
		//��ɾ����������
		this.deleteEntity(model);
	}
}
