package com.cdh.service;

import com.cdh.dao.ClazzDAO;
import com.cdh.model.Clazz;
import com.cdh.util.ResponseData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClazzService {

    private ClazzDAO clazzDAO = new ClazzDAO();

    public ResponseData save(String clazzName, String claInfo){
        Clazz clazz = Clazz.builder().claName(clazzName).claInfo(claInfo).build();
        boolean flg = clazzDAO.save(clazz);
        if (flg){
            return ResponseData.ok("班级添加成功！");
        }
        return ResponseData.fail("班级添加失败！");
    }

    public ResponseData page(String search,Integer pageIndex,Integer pageSize) {
        List<Clazz> clazz = clazzDAO.page(search,pageIndex,pageSize);
        Object[][] list = new Object[clazz.size()][];
        for (int i = 0; i < clazz.size(); i++) {
            Clazz cla = clazz.get(i);
            list[i] = new Object[]{cla.getClaId(),cla.getClaName(),cla.getClaInfo(),
                    cla.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))};
        }
        Integer count = clazzDAO.pageCount(search,pageSize);
        return ResponseData.okPage(list,count);
    }

    public ResponseData update(Integer claId, String claName, String claInfo) {
        Clazz clazz = Clazz.builder().claId(claId).claName(claName).claInfo(claInfo).build();
        boolean flg = clazzDAO.edit(clazz);
        if(flg){
            return ResponseData.ok("修改成功！！");
        }
        return ResponseData.fail("修改失败！！");
    }

    public ResponseData delById(Integer claId) {
        boolean flg = clazzDAO.del(claId);
        if (flg){
            return ResponseData.ok("删除成功！！");
        }
        return ResponseData.fail("删除失败！！");
    }

    public List<Clazz> findAll() {
        return clazzDAO.findAll();
    }

}
