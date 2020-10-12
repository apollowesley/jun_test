package cn.backflow.admin.controller.base;

import cn.backflow.admin.common.Constants;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.entity.User;
import cn.backflow.lib.util.JsonMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class BaseSpringController {

    protected static int DEFAULT_PAGE_SIZE = 10, MAX_PAGE_SIZE = 500;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-M-d"), true));
    }

    public PageRequest pageRequest(HttpServletRequest request, String defaultSortColumns) {
        return pageRequest(request, defaultSortColumns, DEFAULT_PAGE_SIZE);
    }

    public PageRequest pageRequest(HttpServletRequest request, String defaultSortColumns, Integer defaultPageSize) {
        String sort = request.getParameter("sc");
        String numb = request.getParameter("pn");
        String size = request.getParameter("ps");

        if (defaultPageSize == null) {
            defaultPageSize = DEFAULT_PAGE_SIZE;
        }
        int pageSize = NumberUtils.toInt(size, defaultPageSize);
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        int pageNumber = NumberUtils.toInt(numb, 1);
        if (isBlank(sort)) {
            sort = defaultSortColumns;
        }

        PageRequest pr = new PageRequest(pageSize, pageNumber, sort);
        return setParameters(pr, request);
    }

    private PageRequest setParameters(PageRequest pr, HttpServletRequest request) {
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String[] values = entry.getValue();
            String value = values[0].trim();
            if (value.isEmpty()) {
                continue;
            }
            if (values.length == 1) {
                pr.addFilter(entry.getKey(), value);
                continue;
            }
            List<String> list = new ArrayList<>();
            for (String s : values) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }
                list.add(s);
            }
            pr.addFilter(entry.getKey(), list.toArray(new String[list.size()]));
        }
        return pr.setRequestUri(request.getRequestURI());
    }

    protected User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
    }

    /**
     * Errors mapping for jquery.validate.js showErrors() method
     *
     * @param errors BindingResult
     */
    protected JsonMap filedErrors(BindingResult errors, JsonMap json) {
        JsonMap child = json.success(false).child("errors");
        for (ObjectError e : errors.getAllErrors()) {
            child.put(e.getCode(), e.getDefaultMessage());
        }
        return json;
    }
}
