package service;

import pojo.Turnimg;

import java.util.List;
import java.util.Map;

public interface SiteService {


    Map<String, String> getAll();

    void editSite(Map<String, String> map);
}
