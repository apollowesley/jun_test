package service.impl;

import mapper.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SiteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SiteServiceImpl implements SiteService {
    @Autowired
    private SiteMapper mapper;


    @Override
    public Map<String, String> getAll() {
        List<Map<String, String>> list = mapper.getAll();
        Map<String, String> map = new HashMap();
        for (Map<String, String> item : list) {
            map.put(item.get("item"), item.get("info"));
        }
        return map;
    }

    @Override
    public void editSite(Map<String, String> map) {
        for (String key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
            mapper.writeSite(key, map.get(key));
        }
    }
}