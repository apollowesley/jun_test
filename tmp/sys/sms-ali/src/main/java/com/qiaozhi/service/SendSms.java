package com.qiaozhi.service;

import java.util.Map;

public interface SendSms {
    public boolean send(String phoneNum, String templateCode, Map<String,Object> code);
}
