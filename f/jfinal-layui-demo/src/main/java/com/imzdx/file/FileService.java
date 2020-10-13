package com.imzdx.file;

import com.imzdx.common.model.File;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import static com.imzdx.file.FileController.srv;

/**
 * File 管理
 * 描述：
 */
public class FileService {

    //private static final Log log = Log.getLog(FileService.class);

    public static final FileService me = new FileService();
    private final File dao = new File().dao();


    /**
     * 列表-分页
     */
    public Page<File> paginateImg(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "SELECT * ", "FROM file  WHERE type = 'img' ORDER BY create_time DESC");
    }

    /**
     * 列表-分页
     */
    public Page<File> paginate(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "SELECT * ", "FROM file ORDER BY create_time DESC");
    }

    /**
     * 保存
     */
    public void save(File file) {
        file.save();
    }

    /**
     * 更新
     */
    public void update(File file) {
        file.update();
    }

    /**
     * 查询
     */
    public File findById(int fileId) {
        return dao.findFirst("select * from file where id=?", fileId);
    }

    /**
     * 删除
     */
    public void delete(int fileId) {
        Db.update("delete from file where id=?", fileId);
    }


    public File saveUploadFile(UploadFile uplad) {
        File file = new File();
        file.setName(uplad.getFileName())
                .setUrl(uplad.getUploadPath() + uplad.getFileName())
                .setCategory("param")
                .setModule("param")
                .setExt(uplad.getContentType());
        srv.save(file);
        return file;
    }
}