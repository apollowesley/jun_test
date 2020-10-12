package com.tentcoo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tentcoo.dao.AddressRecordDao;
import com.tentcoo.entity.Address;
import com.tentcoo.service.AddressRecordService;





@Transactional
@Service
public class AddressRecordServiceImpl implements AddressRecordService{

	@Resource
	AddressRecordDao addressRecordDao;
	
	@Override
	public boolean addUserAddressInfo(Address address) {
		// TODO Auto-generated method stub
		return addressRecordDao.addUserAddress(address);
	}

}
