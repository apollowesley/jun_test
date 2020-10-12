package com.gs.struts;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * ModelDriven接口通常与Preparable接口结合使用,在获取Model前就先获取相应的表单数据
 * 此时action中的package应该使用paramsPrepareParamsStack拦截器栈
 * Created by WangGenshen on 3/28/16.
 */
public class ModelDrivenAndPrepareAction implements ModelDriven<User>, Preparable {

    private User user;
    private String userId;

    public String save() {
        return "success";
    }

    /**
     * 为save方法准备user对象
     */
    public void prepareSave() {
        user = new User();
    }

    public String edit() {
        return "success";
    }

    public void prepareEdit() {
        // user = dao.getUser();
    }

    @Override
    public User getModel() {
        return user;
    }

    /**
     * prepare方法为getModel方法准备model
     * 此时的getModel方法并不需要去获取相应的对象,而直接return 对象
     * 如果为save, edit等方法定制prepareMethodName方法,则prepare方法也不需要去获取相应的user对象了
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        if (userId == null) {
            user = new User();
        } else {
            // user = dao.getUser(); // 从数据库中获取user对象
        }
    }
}
