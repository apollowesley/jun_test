package org.neuedu.his.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.neuedu.his.entity.Drugs;
import org.neuedu.his.entity.dto.DrugsDTO;

import java.util.List;

/**
 * <p>
 * 非药品收费项目 Mapper 接口
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
public interface DrugsMapper extends BaseMapper<Drugs> {

    @Select(" SELECT drugs_code,drugs_name,drugs_format,drugs_price,mnemonic_code,drugs_unit, " +
            " (select constant_name from his_constant_category where id = his_drugs.drugs_type_id) drugs_type, " +
            " (select constant_name from his_constant_category where id = his_drugs.drugs_dosage_Id) drugs_dosage " +
            " FROM his_drugs ${ew.customSqlSegment} ")
    List<DrugsDTO> excelExport(@Param(Constants.WRAPPER) Wrapper<DrugsDTO> wrapper);

}
