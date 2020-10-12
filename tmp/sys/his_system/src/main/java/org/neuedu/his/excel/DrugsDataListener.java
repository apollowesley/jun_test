package org.neuedu.his.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.neuedu.his.entity.Drugs;
import org.neuedu.his.entity.dto.DrugsDTO;
import org.neuedu.his.service.IConstantCategoryService;
import org.neuedu.his.service.IDrugsService;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote : 
 * @author : CDHONG.IT
 * @date : 2020/4/7
 * @version 1.0
 */
@Slf4j
public class DrugsDataListener extends AnalysisEventListener<DrugsDTO> {

    //注意：AnalysisEventListener不支持Spring管理，需要通过构造的形式注入
    private IConstantCategoryService categoryService;

    private IDrugsService drugsService;

    //通过构造器获取所需要的执行对象
    public DrugsDataListener(IConstantCategoryService categoryService,IDrugsService drugsService){
         this.categoryService = categoryService;
         this.drugsService = drugsService;
    }


    /**
     * 每隔1000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    /**
     * 存储读取的行数据，用于批量插入数据库
     */
    List<Drugs> list = new ArrayList<>();


    /**
     * @apiNote 解析 excel 每一行数据都会调用的方法
     * @param data 把excel 中的每一行数据注入到 data 对象
     * @param context 上下文
     */
    @Override
    public void invoke(DrugsDTO data, AnalysisContext context) {
        log.debug("读取的行数据：{}",data);
        //数据库存储对象
        Drugs drugs = new Drugs();

        //转换处理,通过excel 中获取的 药品剂型和药品类型，到数据库中查询对应的ID，用于入库
        Integer drugsDosageId = categoryService.getIdByConstantCategoryName(data.getDrugsDosage());
        if(drugsDosageId!=-1){
            drugs.setDrugsDosageId(drugsDosageId);
        }

        Integer drugsTypeId = categoryService.getIdByConstantCategoryName(data.getDrugsType());
        if(drugsTypeId!=-1){
            drugs.setDrugsTypeId(drugsTypeId);
        }

        //把 data 中的其他数据存储到 drugs 对象: 名称和类型匹配，不匹配则不管
        BeanUtils.copyProperties(data,drugs);

        list.add(drugs);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            this.saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * @apiNote 执行批量保存操作
     */
    private void saveData() {
        log.debug("开始执行批量插入");
        drugsService.saveBatch(list);
        log.debug("批量插入执行完毕~");
    }

    /**
     * @apiNote excel 中的所有数据解析完毕后会执行的方法
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if(list.size()>0){
            this.saveData();
        }
        log.info("所有数据解析完成！");
    }
}
