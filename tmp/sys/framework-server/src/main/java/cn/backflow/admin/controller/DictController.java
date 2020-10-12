package cn.backflow.admin.controller;

import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.common.secure.Permissions;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.service.DictService;
import cn.backflow.lib.util.JsonMap;
import cn.backflow.lib.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

;

@RestController
@RequestMapping("dict")
public class DictController extends BaseSpringController {

    private static final String DEFAULT_SORT_COLUMNS = "updated desc"; //默认多列排序,example: username desc,created asc

    private final DictService dictService;

    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    /* 查询 */
    @RequestMapping
    @Permissions("dict.view")
    public Object query(Dict dict, HttpServletRequest request) throws Exception {
        PageRequest pageRequest = pageRequest(request, DEFAULT_SORT_COLUMNS);
        return dictService.findPage(pageRequest);
    }

    /* 按id查找 */
    @RequestMapping("{id}")
    @Permissions("dict.view")
    public Object byId(@PathVariable String id) throws Exception {
        return dictService.findByCode(id);
    }

    /* 按编码查找 */
    @RequestMapping(value = "bycode")
    public Object bycode(@RequestParam("code") String code) {
        return dictService.findByCode(code);
    }

    /* 保存 */
    @Permissions("dict.edit")
    @RequestMapping(method = RequestMethod.POST)
    public Object save(@RequestBody String body, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        Collection<Dict> dicts = extractDictsFromVuejs(body, errors);
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        dictService.saveOrUpdate(dicts);
        return json;
    }

    /* 更新 */
    @Permissions("dict.edit")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody String body, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        // errors.reject("code", null, "字典编码创建后不可以修改.");
        Collection<Dict> dicts = extractDictsFromVuejs(body, errors);
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        dictService.saveOrUpdate(dicts);
        return json;
    }

    /**
     * 从请求参数中抽取返回字典对象 (针对vue-resource的封装形式)
     *
     * @param body   RequestBody String
     * @param errors BindingResult
     * @return 字典对象合集
     */
    private Collection<Dict> extractDictsFromVuejs(String body, BindingResult errors) {
        Map<String, Dict> map = new HashMap<>();
        JSONObject json = new JSONObject(body);

        String code = json.getString("code");
        if (StringUtil.isBlank(code)) {
            errors.reject("code", null, "键不能为空");
        }
        String description = json.getString("description");
        JSONArray array = json.getJSONArray("dicts");

        for (int i = 0, len = array.length(); i < len; i++) {
            JSONObject dict = array.getJSONObject(i);
            String key = dict.getString("key");
            String value = dict.getString("value");
            String comment = dict.optString("comment");
            Integer priority = dict.getInt("priority");

            if (StringUtil.isBlank(key))
                errors.reject("key" + i, null, "键不能为空");
            if (StringUtil.isBlank(value))
                errors.reject("value" + i, null, "值不能为空");

            if (errors.hasErrors()) break;

            map.put(key, new Dict(code, description, key, value, comment, priority));
        }
        return map.values(); // 利用Map特性过滤键值重复的数据
    }

    /**
     * 从请求参数中抽取返回字典对象
     *
     * @param request HttpServletRequest
     * @param errors  BindingResult
     * @param dict    表单绑定的字典对象
     * @return 字典对象合集
     */
    private Collection<Dict> extractDicts(HttpServletRequest request, BindingResult errors, Dict dict) {
        Map<String, Dict> map = new HashMap<>();

        String[] keys = request.getParameterValues("key");
        String[] values = request.getParameterValues("value");
        String[] comments = request.getParameterValues("comment");
        String[] priorities = request.getParameterValues("priority");

        for (int i = 0, len = keys.length; i < len; i++) {

            String key = keys[i];
            String value = values[i];
            String comment = comments[i];
            String priority = priorities[i];

            if (StringUtil.isBlank(key))
                errors.reject(key, null, "键不能为空");
            if (StringUtil.isBlank(value))
                errors.reject(value, null, "值不能为空");
            if (!StringUtil.isNumeric(priority))
                errors.reject(priority, null, "值只能为数字");

            if (errors.hasErrors()) break;

            map.put(key, new Dict(dict.getCode(), dict.getDescription(), key, value, comment, Integer.decode(priority)));
        }
        return map.values(); // 利用Map特性过滤键值重复的数据
    }

    @RequestMapping("code-validation")
    public Object checkCodeValid(HttpServletResponse response, String code, @RequestParam(value = "original", required = false) String original) {
        JsonMap json = new JsonMap();
        boolean valid = code.equals(original) || dictService.findByCode(code).isEmpty();
        if (!valid) {
            json.msg("编码[" + code + "]已存在");
        }
        return json.put("valid", valid);
    }

    /* 删除 */
    @Permissions("dict.del")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable String id) throws Exception {
        int rows = dictService.deleteByCode(id);
        return new JsonMap(rows > 0);
    }
}