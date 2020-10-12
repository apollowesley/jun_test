package org.neuedu.his.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.neuedu.his.entity.ConstantCategory;
import org.neuedu.his.response.ResponseEntity;
import org.neuedu.his.service.IConstantCategoryService;
import org.neuedu.his.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 常数类别 常数类别 前端控制器
 * get  del  edit  list  save
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Api(tags = "常数类别模块")
@RestController
@RequestMapping("/constant-category")
public class ConstantCategoryController {

    @Autowired
    private IConstantCategoryService categoryService;

    /**
     * @apiNote : 根据常数编码查询所有类别
     * @param code: 常数分类
     */
    @ApiOperation("根据常数编码查询所有类别")
    @ApiImplicitParam(name = "code",value = "常数编码：KSFL")
    @GetMapping("/{code}")
    public ResponseEntity listByConstantCode(@PathVariable String code){
        List<ConstantCategory> list = categoryService.lambdaQuery()
                .select(ConstantCategory::getId,ConstantCategory::getConstantName)
                .eq(ConstantCategory::getConstantCode, code).list();
        return ResponseEntity.data(list);
    }

    @ApiOperation("查询当前所有的常数类别")
    @GetMapping("/type")
    public ResponseEntity listConstantType(){
        List<ConstantCategory> list = categoryService.query()
                .select("distinct constant_type").list();
        return ResponseEntity.data(list);
    }


    @ApiOperation("条件分页查询常数类别")
    @GetMapping
    public ResponseEntity pageConstantCategory(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                               @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                               ConstantCategory constantCategory){

        Page<ConstantCategory> page = categoryService.lambdaQuery()
                .like(StringUtils.isNotBlank(constantCategory.getConstantCode()),ConstantCategory::getConstantCode, constantCategory.getConstantCode())
                .like(StringUtils.isNotBlank(constantCategory.getConstantName()),ConstantCategory::getConstantName, constantCategory.getConstantName())
                .eq(StringUtils.isNotBlank(constantCategory.getConstantType()),ConstantCategory::getConstantType, constantCategory.getConstantType())
                .page(new Page<>(pageIndex, pageSize));

        return ResponseEntity.page(page.getTotal(),page.getRecords());
    }

    @ApiOperation("保存常数类别")
    @PostMapping
    public ResponseEntity saveConstantCategory(ConstantCategory constantCategory){
        //根据常数类别转为汉子首字母大写的 常数编码，并添加到对象中
        constantCategory.setConstantCode(PinYinUtil.getFirstLettersUp(constantCategory.getConstantType()));
        boolean flg = categoryService.save(constantCategory);
        if(flg){
            return ResponseEntity.ok("添加成功~");
        }
        return ResponseEntity.error("添加失败~");
    }

    @ApiOperation("根据主键ID编辑常数类别")
    @PutMapping
    public ResponseEntity editById(ConstantCategory constantCategory){
        //根据常数类别转为汉子首字母大写的 常数编码，并添加到对象中
        constantCategory.setConstantCode(PinYinUtil.getFirstLettersUp(constantCategory.getConstantType()));
        boolean flg = categoryService.updateById(constantCategory);
        if(flg){
            return ResponseEntity.ok("修改成功~");
        }
        return ResponseEntity.error("修改失败~");
    }

    @ApiOperation("根据主键ID删除常数类别")
    @ApiImplicitParam(name = "id",value = "常数类别主键ID")
    @DeleteMapping("/{id}")
    public ResponseEntity delById(@PathVariable Integer id){
        boolean flg = categoryService.removeById(id);
        if(flg){
            return ResponseEntity.ok("删除成功~");
        }
        return ResponseEntity.error("删除失败~");
    }


}
