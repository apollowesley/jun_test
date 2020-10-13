package com.imzdx.common.utils.poi;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;

import java.util.Map;

/**
 * Created by imzdx on 2017/12/28 0028.
 */
public class ExcelKit {

    /**
     * 导入数据
     * @param upload
     * @param insertSql
     * @return
     */
    public static Map<String, Object> importData(UploadFile upload, String insertSql, int count) {
        return importData(upload, insertSql, false, null, count);
    }

    /**
     * 导入数据，情况原有数据
     * @param uplad
     * @param insertSql
     * @param isDrop
     * @param deleteSql
     * @return
     */
    public static Map<String, Object> importData(UploadFile uplad, String insertSql, boolean isDrop, String deleteSql, int count) {
        String filename = PathKit.getWebRootPath() + "/upload/" + uplad.getFileName();
        filename = filename.replaceAll("\\\\", "/");
        if (isDrop) {
            Db.update(deleteSql);
        }
        Map<String, Object> map = new ConcurrentReaderHashMap();
        try {
            map = PoiExt.ReadExcel(filename, insertSql, count);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.getMessage());
        }
        return map;
    }
}
