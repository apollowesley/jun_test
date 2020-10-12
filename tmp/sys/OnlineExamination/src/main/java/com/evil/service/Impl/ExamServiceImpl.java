package com.evil.service.Impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.Exam;
import com.evil.service.ExamService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.ValidateUtil;

@Service("examService")
public class ExamServiceImpl extends BaseServiceImpl<Exam> implements
		ExamService {

	@Override
	@Resource(name = "examDao")
	public void setBaseDao(BaseDao<Exam> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public PageResult findExamPage(Page page, Map<String, Object> map,
			String... sortfields) {
		return this.findEntityByPage(page,
				accordingParamHql(map, " ", sortfields));
	}
	/**
	 * 根据参数拼hql语句
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("examName")) {
						hql += " and examName like '" + entry.getValue() + "'";
					} else if (entry.getKey().equals("examtypeId")) {
						hql += " and examtypeId ='" + entry.getValue() + "'";
					} else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
		}
		// 拼接排序
		if (!ValidateUtil.isNull(sortfields)) {
			hql += " order by ";
			for (String s : sortfields) {
				if (!ValidateUtil.isNull(s))
					hql += s + ",";
			}
			if (hql.endsWith(" order by ")) {
				hql.substring(0, hql.length() - 10);
			}
			hql = hql.substring(0, hql.length() - 1);
		}
		return hql;
	}

	@Override
	public void deleteExam(Exam model) {
		this.deleteEntity(model);
	}
}
