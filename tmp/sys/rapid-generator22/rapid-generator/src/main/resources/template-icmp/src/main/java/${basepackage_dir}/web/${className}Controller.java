<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.web;
import ${basepackage}.service.${className}Service;
import ${basepackage}.service.bo.${className}SaveBO;
import ${basepackage}.service.query.${className}QUERY;
import ${basepackage}.service.dto.${className}DTO;
import ${basepackage}.service.bo.${className}UpdateBO;
import ${basepackage}.web.vo.${className}VO;
import cn.winner.beans.base.BaseController;
import cn.winner.beans.base.Pagination;
import cn.winner.core.orika.mapper.OrikaBeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import cn.winner.cloud.icmp.core.web.condition.ApiVersion;

@Api(description="模块描述")
@RestController
@RequestMapping("/{version}/${table.underscoreName}")
public class ${className}Controller extends BaseController{

    @Autowired
    private ${className}Service ${classNameLower}Service;

    @Autowired
    private OrikaBeanMapper beanMapper;

    @ApiOperation(value = "获取${table.remarks!''}列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "bean", value = "${table.remarks!''}查询对象", dataType = "${className}QUERY")
    })
    @ApiVersion(1)
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public Pagination<${className}VO> page${className}(@Valid ${className}QUERY bean){
        Pagination<${className}DTO> dto = ${classNameLower}Service.page(bean);
        Pagination<${className}VO> result = new Pagination<>();
        result.setData(beanMapper.mapAsList(dto.getData(),${className}VO.class));
        result.setTotal(dto.getTotal());
        return result;
    }

    @ApiOperation(value = "获取${table.remarks!''}列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "bean", value = "${table.remarks!''}查询对象", dataType = "${className}QUERY")
    })
    @ApiVersion(1)
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<${className}VO> list${className}(@Valid ${className}QUERY bean){
        return beanMapper.mapAsList(${classNameLower}Service.list(bean),${className}VO.class);
    }

    @ApiOperation(value = "根据ID获取${table.remarks!''}对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "${table.remarks!''}ID", required = true, paramType = "path", dataType = "Long"),
    })
    @ApiVersion(1)
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ${className}VO get${className}(@Valid Long id){
        return beanMapper.map(${classNameLower}Service.get(id),${className}VO.class);
    }

    @ApiOperation(value="创建${table.remarks!''}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "bean", value = "${table.remarks!''}详细实体信息", required = true, dataType = "${className}SaveBO")
    })
    @ApiVersion(1)
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Long post${className}(@Valid @RequestBody ${className}SaveBO bean){
        return ${classNameLower}Service.doSave(bean);
    }


    @ApiOperation(value="更新${table.remarks!''}详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name="bean", value = "需要更新的${table.remarks!''}实体信息",required = true, dataType = "${className}UpdateBO"),
            @ApiImplicitParam(name = "id", value = "${table.remarks!''}ID", required = true, paramType="path", dataType = "Long")
    })
    @ApiVersion(1)
    @RequestMapping(value = "/edit",method = RequestMethod.PUT)
    public String put${className}(@Valid @RequestBody ${className}UpdateBO bean){
        ${classNameLower}Service.doUpdate(bean);
        return SUCCESS;
    }

    @ApiOperation(value="根据ID删除指定对象${table.remarks!''}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "${table.remarks!''}ID", required = true, paramType="path", dataType = "Long")
    })
    @ApiVersion(1)
    @RequestMapping(value="/del/{id}", method=RequestMethod.DELETE)
    public String delete${className}(@PathVariable("id") Long id) {
        ${classNameLower}Service.doRemove(id);
        return SUCCESS;
    }

    <#--
    @ApiOperation(value="删除${table.remarks!''}", notes="根据条件删除对象")
    @ApiImplicitParam(name = "bean", value = "${table.remarks!''}", required = true,  dataType = "${className}RemoveBO")
    @ApiVersion(1)
    @RequestMapping(value="/remove", method=RequestMethod.DELETE)
    public String remove${className}(${className}RemoveBO bean) {
        ${classNameLower}Service.doRemove(bean);
        return SUCCESS;
    }
    -->

}