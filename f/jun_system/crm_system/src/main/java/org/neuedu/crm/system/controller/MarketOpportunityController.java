package org.neuedu.crm.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.neuedu.crm.system.entity.MarketOpportunity;
import org.neuedu.crm.system.entity.SysUser;
import org.neuedu.crm.system.response.ResponseCode;
import org.neuedu.crm.system.response.ResponseEntity;
import org.neuedu.crm.system.exception.CrmSystemException;
import org.neuedu.crm.system.service.IMarketOpportunityService;
import org.neuedu.crm.system.service.ISysUserService;
import org.neuedu.crm.system.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 营销机会 前端控制器
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
@Api(tags = "销售机会管理模块")
@RestController
@RequestMapping("/opportunity")
public class MarketOpportunityController {

    @Autowired
    private IMarketOpportunityService opportunityService;
    @Autowired
    private ISysUserService userService;

    @PreAuthorize("hasPermission('/opportunity','page')")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerName",value = "客户名称",paramType = "query"),
            @ApiImplicitParam(name = "summary",value = "概述",paramType = "query"),
            @ApiImplicitParam(name = "linkman",value = "联系人",paramType = "query"),
            @ApiImplicitParam(name = "pageIndex",defaultValue = "1", value = "当前页码",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",defaultValue = "10", value = "每页显示条数",paramType = "query")
    })
    @GetMapping("/page")
    public ResponseEntity page(String customerName,String summary,String linkman,
                               @RequestParam(value = "pageIndex", required = false,defaultValue = "1") Integer pageIndex,
                               @RequestParam(value = "pageSize", required = false,defaultValue = "10") Integer pageSize){
        IPage<MarketOpportunity> marketOpportunityPage = opportunityService.lambdaQuery()
                .like(Objects.nonNull(customerName),MarketOpportunity::getCustomerName, customerName)
                .like(Objects.nonNull(summary),MarketOpportunity::getLinkman, linkman)
                .like(Objects.nonNull(linkman),MarketOpportunity::getSummary, summary)
                .page(new Page<>(pageIndex, pageSize));
        return ResponseEntity.page(marketOpportunityPage.getTotal(),marketOpportunityPage.getRecords());
    }

    @ApiOperation("添加或编辑销售机会")
    @PostMapping("/saveOrUpdate")
    public ResponseEntity saveOrUpdate(MarketOpportunity opportunity){
        //TODO 创建人和创建时间需要加入 - 前端已保存传递
        if(Objects.isNull(opportunity)){
            return ResponseEntity.exception(ResponseCode.PARAMS_VALIDATOR_ERROR);
        }
        boolean flg = opportunityService.saveOrUpdate(opportunity);
        if(flg){
            return ResponseEntity.ok("操作成功~");
        }
        return ResponseEntity.failure("操作失败~");
    }

    @ApiOperation("根据主键ID 查询销售机会详细信息")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Integer id){
        return ResponseEntity.data(opportunityService.getById(id));
    }

    @ApiOperation("查询所有的销售主管，用于指派销售机会")
    @GetMapping("/getAllMgr")
    public ResponseEntity findAllMgr(){
        //查询角色为销售的主管的所有用户名称以及用户编号
        List<SysUser> list = userService.findUserByRoleId(ConstUtil.SysRole.MAIN_MANAGER);
        return ResponseEntity.data(list);
    }

    @ApiOperation("根据主键ID ，删除对应的销售机会")
    @DeleteMapping("/{id}")
    public ResponseEntity delById(@PathVariable("id") Integer id){
        //TODO 当前销售机会的创建人是否为当前登录用户

        //判断当前销售机会状态是否为未指派
        MarketOpportunity opportunity = opportunityService.lambdaQuery()
                .eq(MarketOpportunity::getState, ConstUtil.OpportunityState.NOT_ASSIGNED)
                .eq(MarketOpportunity::getId, id)
                .one();
        if(Objects.isNull(opportunity)){
            throw new CrmSystemException("销售机会已指派，不能删除~");
        }
        //刪除
        boolean flg = opportunityService.removeById(id);
        if(flg){
            return ResponseEntity.ok("删除成功~");
        }
        return ResponseEntity.failure("删除失败~");
    }

    @ApiOperation("指派销售机会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "销售机会ID",required = true,paramType = "path"),
            @ApiImplicitParam(name = "appointId",value = "指派用户ID",required = true,paramType = "path")
    })
    @PutMapping("/{id}/{appointId}")
    public ResponseEntity appoint(
            @PathVariable("id") Integer id,
            @PathVariable("appointId") Integer appointId){
        boolean flg = opportunityService.lambdaUpdate()
                .set(MarketOpportunity::getAppointId, appointId)
                .set(MarketOpportunity::getAppointTime, LocalDateTime.now())
                //修改销售机会状态为已指派
                .set(MarketOpportunity::getState, ConstUtil.OpportunityState.ASSIGNED)
                .eq(MarketOpportunity::getId, id)
                .update();
        if(flg){
            return ResponseEntity.ok("指派成功~");
        }
        return ResponseEntity.failure("指派失败~");
    }


}
