package org.neuedu.his.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.neuedu.his.entity.User;
import org.neuedu.his.response.ResponseEntity;
import org.neuedu.his.service.IUserService;
import org.neuedu.his.util.ConstUtil;
import org.neuedu.his.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Api(tags = "用户信息管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired

    /**
     * @apiNote : 生成验证码，并存储在redis 中，返回 key和base64封装的 Map 对象给前端
     */
    @ApiOperation("生成验证码")
    @GetMapping("/captcha")
    public ResponseEntity captcha() throws Exception {
        return userService.captcha();
    }

    /**
     * @apiNote : 登录方法
     * @param logName 用户名
     * @param logPwd 密码
     * @param key redis 存储key
     * @param verifyCode 验证码
     * @return
     */
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "用户名",value = "logName",required = true),
            @ApiImplicitParam(name = "密码",value = "logPwd",required = true),
            @ApiImplicitParam(name = "redis 存储key",value = "key",required = true),
            @ApiImplicitParam(name = "验证码",value = "verifyCode",required = true)
    })
    @PostMapping("/login")
    public ResponseEntity login(String logName,String logPwd,String key,String verifyCode)throws Exception {
        return userService.login(logName, logPwd, key, verifyCode);
    }

    /**
     * @apiNote :分页查询
     * @param pageIndex 页码
     * @param pageSize 每页显示的条数
     * @param user 查询条件：真实姓名，所在科室，用户类别
     */
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "页码",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示的条数",defaultValue = "10")
    })
    @GetMapping
    public ResponseEntity pageUser(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     User user){
        Page<User> page = userService.lambdaQuery()
                .like(StringUtils.isNotBlank(user.getRelName()),User::getRelName, user.getRelName())
                .eq(Objects.nonNull(user.getByOfficeId()),User::getByOfficeId,user.getByOfficeId())
                .eq(Objects.nonNull(user.getUserTypeId()),User::getUserTypeId,user.getUserTypeId())
                .page(new Page<>(pageIndex, pageSize));
        return ResponseEntity.page(page.getTotal(),page.getRecords());

    }

    /**
     * @apiNote 新增用户
     * @param user 用户实体
     */
    @ApiOperation("新增用户")
    @PostMapping
    public ResponseEntity save(User user){
        //使用拼音转换工具通过用户真实姓名生成用户登录名
        user.setLogName(PinYinUtil.getPinyinString(user.getRelName()));
        user.setLogPwd(ConstUtil.USER_DEFAULT_PASSWORD);
        boolean flg = userService.save(user);
        if(flg){
            return ResponseEntity.ok("保存用户成功~");
        }
        return ResponseEntity.error("保存用户失败~");
    }


    /**
     * @apiNote 根据用户ID修改
     * @param user 用户实体
     */
    @ApiOperation("根据用户ID修改")
    @PutMapping
    public ResponseEntity editById(User user){
        //如果是门诊医生则不管，如果不是则需要清空职称信息和是否排班
        if(!Objects.equals(user.getUserTypeId(),ConstUtil.OUTPATIENT_DOCTOR_TYPE)){
            user.setParticipateArrange(0);
            user.setAcademicInfoId(-1);
        }
        boolean flg = userService.updateById(user);
        if(flg){
            return ResponseEntity.ok("编辑用户成功~");
        }
        return ResponseEntity.error("编辑用户失败~");
    }

    /**
     * @apiNote 根据用户ID删除
     * @param id 科室编号
     */
    @ApiOperation("根据用户ID删除")
    @ApiImplicitParam(name = "id",value = "用户编号",required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity delById(@PathVariable Integer id){
        boolean flg = userService.removeById(id);
        if(flg){
            return ResponseEntity.ok("删除用户成功~");
        }
        return ResponseEntity.error("删除用户失败~");
    }

    /**
     * @apiNote 判断当前用户是否存在
     * @param relName 真实姓名
     */
    @ApiOperation("判断当前用户是否存在")
    @ApiImplicitParam(name = "relName",value = "真实姓名",required = true)
    @GetMapping("/exist/{relName}")
    public ResponseEntity existRelName(@PathVariable String relName){
        User user = userService.lambdaQuery().eq(User::getRelName, relName).one();
        if(Objects.nonNull(user)){
            return ResponseEntity.error("当前用户已经存在~");
        }
        return ResponseEntity.ok("当前用户没有注册，可以使用~");

    }

    @ApiOperation("重置密码")
    @PostMapping("/reload/{id}")
    public ResponseEntity reload(@PathVariable Integer id){
        boolean flg = userService.lambdaUpdate()
                .set(User::getLogPwd, ConstUtil.USER_DEFAULT_PASSWORD)
                .eq(User::getId,id)
                .update();
        if(flg){
            return ResponseEntity.ok("重置成功~");
        }
        return ResponseEntity.error("重置失败~");

    }



}
