package com.slavic.hors.admin.api.controller.portal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slavic.hors.admin.api.dto.portal.user.request.*;
import com.slavic.hors.admin.api.dto.portal.user.response.LoginResponse;
import com.slavic.hors.admin.api.dto.portal.user.response.RoleResponse;
import com.slavic.hors.orm.entity.*;
import com.slavic.hors.service.CommonFileDiskService;
import com.slavic.hors.service.PortalRoleService;
import com.slavic.hors.service.PortalUserService;
import com.slavic.veles.base.response.ResponseEntity;
import com.slavic.veles.utils.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "portal/user",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
public class UserController {

    private final PortalUserService portalUserService;

    private final PortalRoleService portalRoleService;

    private final CommonFileDiskService commonFileDiskService;

    public UserController(PortalUserService portalUserService, PortalRoleService portalRoleService, CommonFileDiskService commonFileDiskService) {
        this.portalUserService = portalUserService;
        this.portalRoleService = portalRoleService;
        this.commonFileDiskService = commonFileDiskService;
    }

    @RequestMapping("login")
    public ResponseEntity<LoginResponse> login(@Validated LoginRequest request) {
        HorsPortalUser user = portalUserService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.SUCCESS(LoginResponse.builder()
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .token(JwtUtils.createToken(user.getId().toString()))
                .nickName(user.getNickName())
                .build()
        );
    }

    @RequestMapping(value = "userResources")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("resources",Optional
                .of(Optional
                        .ofNullable(portalRoleService.userResources(JwtUtils.getUserId()))
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(HorsPortalResource::getResourceName)
                        .collect(Collectors.toList())
                ).orElse(new ArrayList<>())
        );
        return ResponseEntity.SUCCESS(map);
    }

    @RequestMapping("list")
    public ResponseEntity<PageInfo<HorsPortalUser>> list(ListRequest request) {
        return ResponseEntity.SUCCESS(
                PageHelper
                        .startPage(request.getCurrentPage(), request.getPageSize())
                        .doSelectPageInfo(
                                () -> portalUserService.list(BeanUtil.copyObject(request,new HorsPortalUser()))
                        )
        );
    }

    @RequestMapping("detail")
    public ResponseEntity<HorsPortalUser> detail() {
        return ResponseEntity.SUCCESS(portalUserService.findById(JwtUtils.getUserId()));
    }

    @RequestMapping("update")
    public ResponseEntity<HorsPortalUser> update(UpdateRequest request) {
        return ResponseEntity.CONDITION(
                null != portalUserService.update(BeanUtil.copyObject(request, portalUserService.findById(request.getId()))),
                "更新用户失败");
    }

    @RequestMapping("add")
    public ResponseEntity<Boolean> add(@Validated AddRequest request) {
        return ResponseEntity.CONDITION(
                portalUserService.add(BeanUtil.copyObject(request,new HorsPortalUser())),
                "添加用户失败");
    }

    @RequestMapping("delete")
    public ResponseEntity<Boolean> delete(Long id) {
        AssertUtil.assertTrue(null != id, "删除用户ID不允许为空");
        AssertUtil.assertTrue(null != portalUserService.findById(id), "用户已不存在,请刷新确认");
        return ResponseEntity.CONDITION(portalUserService.delete(id),"删除用户失败");
    }

    @RequestMapping("roles")
    public ResponseEntity<RoleResponse> roles(Long id) {
        AssertUtil.assertTrue(null != id, "用户ID不能为空");
        AssertUtil.assertTrue(null != portalUserService.findById(id), "用户已不存在,请刷新确认");
        List<HorsPortalRole> allRoles = portalRoleService.list(new HorsPortalRole());
        AssertUtil.assertTrue(!CollectionUtils.isEmpty(allRoles), "角色列表为空,请先添加角色");
        return ResponseEntity.SUCCESS(
                new RoleResponse(allRoles,Optional
                        .ofNullable(portalRoleService.userRoles(id))
                        .orElse(new ArrayList<>())
                )
        );
    }

    @RequestMapping("reRoles")
    public ResponseEntity<Boolean> reRoles(@RequestBody @Validated ReRolesRequest request) {
        AssertUtil.assertTrue(null != portalUserService.findById(request.getId()), "用户已不存在,请刷新确认");
        return ResponseEntity.CONDITION(portalRoleService.reRoles(request.getId(), request.getRoleIds()),"授权失败");
    }

    @RequestMapping("uploadAvatar")
    public ResponseEntity<Long> uploadAvatar(MultipartHttpServletRequest request) throws Exception{
        MultipartFile file = request.getFile("file");
        AssertUtil.assertTrue(null != file, "文件为空,请核实后上传");
        Long id = commonFileDiskService.storeFile(file.getOriginalFilename(),
                Base64Util.bytes2Base64String(file.getBytes()));
        return ResponseEntity.CONDITION(null != id,id,"头像上传失败");
    }

    @RequestMapping("loginRecords")
    public ResponseEntity<List<HorsPortalLoginRecord>> loginRecords() {
        PageHelper.startPage(1, 10);
        return ResponseEntity.SUCCESS(portalUserService.nearLoginRecords(JwtUtils.getUserId()));
    }

    @RequestMapping(value = "avatar", method = RequestMethod.GET)
    public void avatar(String id ,HttpServletResponse response) throws Exception{
        AssertUtil.assertTrue(StringUtils.isNotBlank(id),"文件id不允许为空");
        HorsCommonFileDisk fileDisk = commonFileDiskService.receiveFile(Long.valueOf(id));
        AssertUtil.assertTrue(null != fileDisk, "文件不存在,请核实后查看");
        IOUtils.write(Base64Util.base64String2Bytes(fileDisk.getFileContent()),response.getOutputStream());
    }
}