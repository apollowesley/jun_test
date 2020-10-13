package com.example.express.common.cache;

import com.example.express.domain.bean.DataArea;
import com.example.express.domain.bean.DataCompany;
import com.example.express.domain.bean.DataSchool;
import com.example.express.domain.bean.UserEvaluate;
import com.example.express.service.DataAreaService;
import com.example.express.service.DataCompanyService;
import com.example.express.service.DataSchoolService;
import com.example.express.service.UserEvaluateService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CommonDataCache {
    @Autowired
    private DataAreaService dataAreaService;
    @Autowired
    private DataSchoolService dataSchoolService;
    @Autowired
    private DataCompanyService dataCompanyService;
    @Autowired
    private UserEvaluateService userEvaluateService;

    /**
     * 行政区域数据缓存
     * key: parentId
     */
    public static LoadingCache<Integer, List<DataArea>> dataAreaCache;
    /**
     * 学校数据缓存
     * key: 省份
     */
    public static LoadingCache<Integer, List<DataSchool>> dataSchoolCache;
    /**
     * 学校数据缓存
     * key: schoolId
     */
    public static LoadingCache<Integer, DataCompany> dataCompanyCache;
    /**
     * 用户评分Score
     * key: 用户ID
     */
    public static LoadingCache<String, String> userScoreCache;

    @PostConstruct
    private void init() {
        dataAreaCache = CacheBuilder.newBuilder()
                .maximumSize(35)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, List<DataArea>>() {
                    @Override
                    public List<DataArea> load(Integer parentId) throws Exception {
                        return dataAreaService.listByParentId(parentId);
                    }
                });

        dataSchoolCache = CacheBuilder.newBuilder()
                .maximumSize(35)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, List<DataSchool>>() {
                    @Override
                    public List<DataSchool> load(Integer provinceId) throws Exception {
                        return dataSchoolService.listByProvinceId(provinceId);
                    }
                });

        dataCompanyCache = CacheBuilder.newBuilder()
                .maximumSize(35)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, DataCompany>() {
                    @Override
                    public DataCompany load(Integer id) throws Exception {
                        return dataCompanyService.getById(id);
                    }
                });

        userScoreCache = CacheBuilder.newBuilder()
                .maximumSize(35)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String id) throws Exception {
                        UserEvaluate evaluate = userEvaluateService.getById(id);
                        return evaluate.getScore().toPlainString();
                    }
                });
    }
}
