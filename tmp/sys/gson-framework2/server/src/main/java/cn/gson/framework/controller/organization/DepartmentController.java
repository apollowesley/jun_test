package cn.gson.framework.controller.organization;

import cn.gson.framework.common.JsonResponse;
import cn.gson.framework.controller.BaseController;
import cn.gson.framework.model.pojo.Department;
import cn.gson.framework.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.controller.organization</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月30日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@RestController
@RequestMapping("organization/dept")
public class DepartmentController extends BaseController<DepartmentService, Department> {

    @Override
    public JsonResponse list(@RequestParam Map<String, String> params) {
        QueryWrapper<Department> qw = new QueryWrapper<>();
        qw.lambda().eq(Department::getCompanyId, params.get("company"))
                .isNull(Department::getParentId);
        List<Department> departmentDtos = service.list(qw);
        buildTree(departmentDtos, Long.parseLong(params.get("company")));
        return JsonResponse.success("", departmentDtos);
    }

    private void buildTree(List<Department> departmentDtos, Long company) {
        departmentDtos.forEach(department -> {
            QueryWrapper<Department> qw = new QueryWrapper<>();
            qw.lambda().eq(Department::getCompanyId, company)
                    .eq(Department::getParentId, department.getId());
            department.setChildren(service.list(qw));
            this.buildTree(department.getChildren(), company);
        });
    }

    @GetMapping("full/tree")
    public JsonResponse fullTree() {
        return JsonResponse.success("", service.fullTree());
    }
}
