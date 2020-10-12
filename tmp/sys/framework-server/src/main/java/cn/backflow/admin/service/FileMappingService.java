package cn.backflow.admin.service;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.controller.FileMappingController;
import cn.backflow.admin.dao.FileMappingDao;
import cn.backflow.admin.entity.FileMapping;
import cn.backflow.admin.service.base.BaseService;
import cn.backflow.lib.thirdpart.QiniuUtil;
import com.qiniu.storage.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileMappingService extends BaseService<FileMapping, java.lang.Integer> {

    private final FileMappingDao fileMappingDao;

    @Autowired
    public FileMappingService(FileMappingDao fileMappingDao) {this.fileMappingDao = fileMappingDao;}

    public int save(List<FileMapping> mappings) {
        if (mappings == null || mappings.isEmpty())
            return 0;
        return fileMappingDao.save(mappings);
    }

    public List<FileMapping> findByKeys(List<String> keys) {
        if (keys == null || keys.isEmpty()) return Collections.emptyList();
        return fileMappingDao.findByKeys(keys);
    }

    public Collection<FileMapping> findByFileInfos(FileInfo[] infos) {
        List<String> keys = new ArrayList<>(infos.length);
        for (FileInfo info : infos) {
            keys.add(info.key);
        }
        Map<String, FileMapping> map = findMapByKeys(keys);

        List<FileMapping> list = new ArrayList<>();
        for (FileInfo info : infos) {
            if (map.containsKey(info.key)) {
                list.add(map.get(info.key));
            } else {
                list.add(FileMappingController.fromFileInfo(info));
            }
        }
        return list;
    }

    public Map<String, FileMapping> findMapByKeys(List<String> keys) {
        return fileMappingDao.findMapByKeys(keys, "key");
    }

    public List<String> findKeyByIds(Collection<Integer> ids) {
        return fileMappingDao.findKeyByIds(ids);
    }

    public Page<FileMapping> findByFolder(String folder, int pageNumber, int pageSize) {
        PageRequest pr = new PageRequest();
        pr.addFilter("folder", folder);
        pr.setSortColumns("id desc");
        pr.setPageNumber(pageNumber);
        pr.setPageSize(pageSize);
        return fileMappingDao.findByPageRequest(pr);
    }

    /**
     * 查找所有父文件夹
     *
     * @param id 文件id
     * @return 父文件夹列表(包含当前文件)
     */
    public List<FileMapping> findParentsById(Integer id) {
        FileMapping current = getById(id);
        if (current.getParent() == 0 || current.getAncestors() == null) {
            return Collections.singletonList(current);
        }
        String[] arr = current.getAncestors().split(",");
        List<Integer> ids = Stream.of(arr)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        ids.add(id);
        return fileMappingDao.findByIds(ids);
    }

    @Override
    @Transactional
    public int saveOrUpdate(FileMapping mapping) throws DataAccessException {
        return mapping.getId() == null ? save(mapping) : update(mapping);
    }

    @Override
    @Transactional
    public int save(FileMapping mapping) throws DataAccessException {
        setAncestors(mapping);
        return super.save(mapping);
    }

    @Override
    @Transactional
    public int update(FileMapping mapping) throws DataAccessException {
        setAncestors(mapping);
        return super.updateSelective(mapping);
    }

    /* 设置文件祖先ID路径 */
    private void setAncestors(FileMapping mapping) {
        Integer parent = mapping.getParent();
        if (parent != 0) {
            FileMapping p = getById(parent);
            String ancestors = p.getAncestors();
            if (ancestors == null) {
                ancestors = p.getId().toString();
            } else {
                ancestors = ancestors + "," + p.getId();
            }
            mapping.setAncestors(ancestors);
        }
    }

    @Transactional
    public int deleteByKeys(String[] keys) throws Exception {
        QiniuUtil.delete(keys);
        return fileMappingDao.deleteByKeys(keys);
    }

    @Override
    @Transactional
    public int deleteBatch(Collection<Integer> ids) {
        List<String> keys = findKeyByIds(ids);
        QiniuUtil.delete(keys.toArray(new String[keys.size()]));
        return super.deleteBatch(ids);
    }

}