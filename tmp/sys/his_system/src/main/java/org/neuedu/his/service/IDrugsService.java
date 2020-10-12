package org.neuedu.his.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.neuedu.his.entity.Drugs;
import org.neuedu.his.entity.dto.DrugsDTO;

import java.util.List;

/**
 * <p>
 * 非药品收费项目 服务类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
public interface IDrugsService extends IService<Drugs> {

    List<DrugsDTO> excelExport(String search);

}
