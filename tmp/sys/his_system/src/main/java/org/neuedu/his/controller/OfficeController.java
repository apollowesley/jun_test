package org.neuedu.his.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.neuedu.his.entity.Office;
import org.neuedu.his.response.ResponseEntity;
import org.neuedu.his.service.IOfficeService;
import org.neuedu.his.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 科室 前端控制器
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Api(tags = "科室管理")
@RestController
@RequestMapping("/office")
public class OfficeController {

    @Autowired
    private IOfficeService officeService;

    /**
     * @apiNote :分页查询
     * @param pageIndex 页码
     * @param pageSize 每页显示的条数
     * @param search 查询条件：科室名称或科室编码
     */
    @ApiOperation("分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageIndex",value = "页码",defaultValue = "1"),
        @ApiImplicitParam(name = "pageSize",value = "每页显示的条数",defaultValue = "10"),
        @ApiImplicitParam(name = "search",value = "查询条件：科室名称或科室编号"),
    })
    @GetMapping
    public ResponseEntity pageOffice(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     String search){
        Page<Office> page = officeService.lambdaQuery()
                .like(StringUtils.isNotBlank(search),Office::getOfficeCode, search)
                .or()
                .like(StringUtils.isNotBlank(search),Office::getOfficeName, search)
                .page(new Page<>(pageIndex, pageSize));
        return ResponseEntity.page(page.getTotal(),page.getRecords());

    }

    /**
     * @apiNote 根据科室ID修改
     * @param office 科室实体
     */
    @ApiOperation("根据科室ID修改")
    @PutMapping
    public ResponseEntity editById(Office office){
        //使用拼音转换工具通过科室名称生成科室编码
        office.setOfficeCode(PinYinUtil.getFirstLettersUp(office.getOfficeName()));
        boolean flg = officeService.updateById(office);
        if(flg){
            return ResponseEntity.ok("编辑科室成功~");
        }
        return ResponseEntity.error("编辑科室失败~");
    }

    /**
     * @apiNote 根据科室ID删除
     * @param id 科室编号
     */
    @ApiOperation("根据科室ID删除")
    @ApiImplicitParam(name = "id",value = "科室编号",required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity delById(@PathVariable Integer id){
        boolean flg = officeService.removeById(id);
        if(flg){
            return ResponseEntity.ok("删除科室成功~");
        }
        return ResponseEntity.error("删除科室失败~");
    }

    /**
     * @apiNote 新增科室
     * @param office 科室实体
     */
    @ApiOperation("新增科室")
    @PostMapping
    public ResponseEntity save(Office office){
        //使用拼音转换工具通过科室名称生成科室编码
        office.setOfficeCode(PinYinUtil.getFirstLettersUp(office.getOfficeName()));
        boolean flg = officeService.save(office);
        if(flg){
            return ResponseEntity.ok("保存科室成功~");
        }
        return ResponseEntity.error("保存科室失败~");
    }

    @ApiOperation("查询所有科室信息")
    /**
     * @apiNote 查询所有科室名称,用于下拉选择框
     */
    @GetMapping("/list")
    public ResponseEntity listOffice(){
        List<Office> list = officeService.lambdaQuery().select(Office::getId, Office::getOfficeName).list();
        return ResponseEntity.data(list);
    }


}
