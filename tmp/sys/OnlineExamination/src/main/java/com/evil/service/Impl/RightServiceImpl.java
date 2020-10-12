package com.evil.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.system.Right;
import com.evil.service.RightService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements
		RightService {

	@Override
	@Resource(name = "rightDao")
	public void setBaseDao(BaseDao<Right> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public void saveOrUpdateRight(Right model) {
		// modelû��id����model ����Ȩ��λ��Ȩ����
		if (model.getId() == null) {
			int pos = 0;
			long code = 1l;
			String hql = "select max(r.rightPos),max(r.rightCode) from Right r"
					+ " where r.rightPos= (select max(rr.rightPos) from Right rr)";
			Object[] arr = (Object[]) this.uniqueResult(hql);
			Integer topPos = (Integer) arr[0];
			Long topcode = (Long) arr[1];
			// ������Ȩ��Ϊ��Ϊ�� ��ʾrights������
			if (topPos != null) {
				// ������Ȩ���� ����1L<<60 ����һ��
				if (topcode >= (1l << 60)) {
					code = 1l;
					pos = topPos + 1;
				}
				// ���Ȩ����������һλ
				else {
					pos = topPos;
					code = topcode << 1;
				}
			}
			if(ValidateUtil.isNull(model.getRightName())){
				model.setRightName("δ����");
			}
			model.setRightPos(pos);
			model.setRightCode(code);
		}
		this.saveOrUpdateEntity(model);
	}

	@Override
	public void appendRightByUrl(String url) {
		String hql = "select count(*) from Right r where r.rightUrl=?";
		Long count = (Long) this.uniqueResult(hql, url);
		if (count == 0) {
			Right right = new Right();
			right.setRightUrl(url);
			this.saveOrUpdateRight(right);
		}
	}

	@Override
	public void batchUpdateRights(List<Right> allRights) {
		String hql = "update Right r set r.rightName=?,r.common=? where r.id=?";
		for (Right r : allRights) {
			this.batchEntityByHQL(hql, r.getRightName(), r.isCommon(),
					r.getId());
		}
	}

	/**
	 * ��ѯ��ָ����Χ�ڵ�Ȩ��
	 */
	@Override
	public List<Right> findRightsInRange(Integer[] ownRightIds) {
		if (!ValidateUtil.isNull(ownRightIds)) {
			String hql = "from Right r where r.id in ("
					+ StringUtil.arr2SqlStr(ownRightIds) + ")";
			return this.findEntityByHQL(hql);
		}
		return null;
	}

	@Override
	public List<Right> findRightsNotInRange(Set<Right> rights) {
		if (!ValidateUtil.isNull(rights)) {
			String hql = "from Right r where r.id not in ("
					+ StringUtil.extractEntityIds(rights) + ")";
			return this.findEntityByHQL(hql);
		}
		return this.findAllEntities();
	}

	@Override
	public int getMaxPos() {
		String hql = "select max(r.rightPos) from Right r";
		Integer maxPos = (Integer) this.uniqueResult(hql);
		return maxPos == null ? 0 : maxPos;
	}

	@Override
	public PageResult findRightsPage(Page page, Map<String, Object> map,
			String... sortfields) {
		return this.findEntityByPage(page,
				accordingParamHql(map, " ", sortfields));
	}

	/**
	 * ���ݲ���ƴhql���
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("rightName")) {
						hql += " and rightName like '" + entry.getValue() + "'";
					} else if (entry.getKey().equals("rightUrl")) {
						hql += " and rightUrl like '" + entry.getValue() + "'";
					} else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
		}
		// ƴ������
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

}
