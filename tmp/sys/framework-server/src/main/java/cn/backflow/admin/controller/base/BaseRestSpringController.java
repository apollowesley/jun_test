package cn.backflow.admin.controller.base;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/**
 * 标准的rest方法列表
 * <pre>
 * /users                    => index()
 * /users/new                => _new()  不使用/users/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /users/{id}               => show()
 * /users/{id}/edit          => edit()
 * /users            POST    => create()
 * /users/{id}       PUT     => update()
 * /users/{id}       DELETE  => delete()
 * /users            DELETE  => batchDelete()
 * </pre>
 */
public abstract class BaseRestSpringController<E, PK> extends BaseSpringController {

    @RequestMapping
    public Object index(E entity, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }

    /* 显示 */
    @RequestMapping(value = "{id}")
    public Object view(@PathVariable PK id, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }

    /* 保存新增 */

    @RequestMapping(method = RequestMethod.POST)
    public Object create(@Valid E entity, BindingResult result, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }

    /* 保存更新 */

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Object update(@PathVariable PK id, @Valid E entity, BindingResult result, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }

    /* 删除 */

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable PK id, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }

    /* 批量删除 */

    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(@RequestParam("items") Set<PK> items, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("not yet implement");
    }
}