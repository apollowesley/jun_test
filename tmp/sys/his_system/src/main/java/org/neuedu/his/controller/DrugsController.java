package org.neuedu.his.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.neuedu.his.entity.Drugs;
import org.neuedu.his.entity.dto.DrugsDTO;
import org.neuedu.his.excel.DrugsDataListener;
import org.neuedu.his.response.ResponseEntity;
import org.neuedu.his.service.IConstantCategoryService;
import org.neuedu.his.service.IDrugsService;
import org.neuedu.his.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 非药品收费项目 前端控制器
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/drugs")
public class DrugsController {

    @Autowired
    private IDrugsService drugsService;
    @Autowired
    private IConstantCategoryService categoryService;

    /**
     * @apiNote :分页查询
     * @param pageIndex 页码
     * @param pageSize 每页显示的条数
     * @param search 查询条件：药品名称或者拼音助记码
     */
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "页码",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示的条数",defaultValue = "10"),
            @ApiImplicitParam(name = "search",value = "查询条件：药品名称或者拼音助记码"),
    })
    @GetMapping
    public ResponseEntity page(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                   String search){
        Page<Drugs> page = drugsService.lambdaQuery()
                .like(StringUtils.isNotBlank(search),Drugs::getDrugsName, search)
                .or()
                .like(StringUtils.isNotBlank(search),Drugs::getMnemonicCode, search)
                .page(new Page<>(pageIndex, pageSize));
        return ResponseEntity.page(page.getTotal(),page.getRecords());

    }

    /**
     * @apiNote 新增药品
     * @param drugs 药品实体
     */
    @ApiOperation("新增药品")
    @PostMapping
    public ResponseEntity save(Drugs drugs){
        //使用拼音转换工具生成助记码
        drugs.setMnemonicCode(PinYinUtil.getFirstLettersUp(drugs.getDrugsName()));
        boolean flg = drugsService.save(drugs);
        if(flg){
            return ResponseEntity.ok("保存药品成功~");
        }
        return ResponseEntity.error("保存药品失败~");
    }


    /**
     * @apiNote 根据药品ID修改
     * @param drugs 药品实体
     */
    @ApiOperation("根据药品ID修改")
    @PutMapping
    public ResponseEntity editById(Drugs drugs){
        //使用拼音转换工具生成助记码
        drugs.setMnemonicCode(PinYinUtil.getFirstLettersUp(drugs.getDrugsName()));
        boolean flg = drugsService.updateById(drugs);
        if(flg){
            return ResponseEntity.ok("编辑药品成功~");
        }
        return ResponseEntity.error("编辑药品失败~");
    }

    /**
     * @apiNote 根据药品ID删除
     * @param id 药品编号
     */
    @ApiOperation("根据药品ID删除")
    @ApiImplicitParam(name = "id",value = "药品编号",required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity delById(@PathVariable Integer id){
        boolean flg = drugsService.removeById(id);
        if(flg){
            return ResponseEntity.ok("删除药品成功~");
        }
        return ResponseEntity.error("删除药品失败~");
    }


    @ApiOperation("非药品目录导入")
    @ApiImplicitParam(name = "file",value = "excel文档",required = true)
    @PostMapping("/fileUpload")
    public ResponseEntity excelImport(MultipartFile file) throws Exception{
        //使用 阿里巴巴 提供的 EasyExcel 完成 Excel 读取
        EasyExcel
                .read(file.getInputStream(), DrugsDTO.class, new DrugsDataListener(categoryService,drugsService))
                .sheet() //指定读取的 sheet
                .doRead();
        return ResponseEntity.ok("非药品目录导入成功~");
    }

    @ApiOperation("非药品目录导出")
    @ApiImplicitParam(name = "search",value = "查询条件：药品名称或者拼音助记码")
    @GetMapping("/downLoad")
    public void excelExport(HttpServletResponse response,String search) throws Exception{
        //写入 excel 的数据
        List<DrugsDTO> list = drugsService.excelExport(search);

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("非药品目录-"+ LocalDate.now().toString(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), DrugsDTO.class)
                .sheet("非药品目录")
                .doWrite(list);
    }

}
