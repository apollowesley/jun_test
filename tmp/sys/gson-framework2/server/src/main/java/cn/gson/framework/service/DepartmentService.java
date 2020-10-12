package cn.gson.framework.service;

import cn.gson.framework.model.mapper.CompanyMapper;
import cn.gson.framework.model.mapper.DepartmentMapper;
import cn.gson.framework.model.pojo.Company;
import cn.gson.framework.model.pojo.Department;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 获取所有公司和部门的集合
     *
     * @return
     */
    public ArrayList<Map<String, Object>> fullTree() {
        final List<Company> companies = companyMapper.selectList(null);
        final List<Department> departments = baseMapper.selectList(null);

        final ArrayList<Map<String, Object>> collect = companies.stream().collect(ArrayList::new, (list, company) -> {
            Map<String, Object> node = new HashMap<>();
            node.put("id", "c" + company.getId());
            node.put("title", company.getName());
            node.put("disabled", true);
            list.add(node);
        }, List::addAll);

        collect.addAll(departments.stream().collect(ArrayList::new, (list, dept) -> {
            Map<String, Object> node = new HashMap<>();
            node.put("id", dept.getId());
            node.put("title", dept.getName());
            if (dept.getParentId() == null) {
                node.put("parent", "c" + dept.getCompanyId());
            } else {
                node.put("parent", dept.getParentId());
            }
            list.add(node);
        }, List::addAll));

        return collect;
    }
}
