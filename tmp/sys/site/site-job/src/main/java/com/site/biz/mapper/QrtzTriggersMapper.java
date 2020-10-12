package com.site.biz.mapper;

import java.util.List;

import com.site.biz.model.QrtzTriggers;

public interface QrtzTriggersMapper extends BaseMapper<String, QrtzTriggers> {

    List<QrtzTriggers> findAll(QrtzTriggers qrtzTriggers);

}
