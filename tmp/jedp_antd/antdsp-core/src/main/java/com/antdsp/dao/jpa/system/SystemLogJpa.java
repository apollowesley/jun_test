package com.antdsp.dao.jpa.system;

import org.springframework.stereotype.Repository;

import com.antdsp.dao.jpa.AntdspBaseRepository;
import com.antdsp.data.entity.system.SystemLog;

@Repository("systemLogJpa")
public interface SystemLogJpa extends AntdspBaseRepository<SystemLog, Long>{

}
