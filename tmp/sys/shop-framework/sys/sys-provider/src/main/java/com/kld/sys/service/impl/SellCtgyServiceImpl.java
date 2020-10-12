package com.kld.sys.service.impl;

import com.kld.sys.api.ISellCtgyService;
import org.springframework.stereotype.Service;

/**
 * Created by anpushang on 2016/3/13.
 */
@Service
public class SellCtgyServiceImpl implements ISellCtgyService {

    @Override
    public String getString(String prop) {
        return "anps..."+prop;
    }
}
