/**
 * 
 * @auther wuwang
 * @createTime 2014-5-3 下午2:55:43
 */
package com.staticClass;

import java.util.List;

import com.hibernate.voDao.JoinAsAPartner;
import com.hibernate.voDao.JoinAsAPartnerDAO;

/**
 * 
 * 
 * @author peaches
 */
public class JoinUsGetButton {
	public static Short getstate(String uid, String jaapid) {

		JoinAsAPartnerDAO joinAsAPartnerDao = new JoinAsAPartnerDAO();
		List<JoinAsAPartner> list = joinAsAPartnerDao.findByUidAndJaapid(uid, jaapid);
		if (list.size() == 0) {
			return 0;
		} else {
			List<JoinAsAPartner> joinAsAPartner = joinAsAPartnerDao.findByUidAndJaapid(uid, jaapid);
			return joinAsAPartner.get(0).getJaapjoinstate();
		}
	}
}
