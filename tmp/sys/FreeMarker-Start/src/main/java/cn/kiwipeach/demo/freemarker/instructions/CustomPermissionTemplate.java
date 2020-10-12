/*
 * Copyright 2018 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.freemarker.instructions;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2018/02/26
 * Description: 自定义权限freemarker指令
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class CustomPermissionTemplate implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        //业务逻辑入参、出参处理
        System.out.println("cn.kiwipeach.demo.freemarker.instructions.CustomPermissionTemplate...");
        SimpleScalar roleName = (SimpleScalar) params.get("role");
        List<String> permissions = loadPerssionByRoleName(roleName);
        loopVars[0] = roleName;
        loopVars[1] = new SimpleSequence(permissions,null);
        body.render(env.getOut());
    }

    /**
     * 加载用户角色权限
     * @param roleName 角色名称
     * @return 返回权限集合
     */
    private List<String> loadPerssionByRoleName(SimpleScalar roleName) {
        List<String> resultPerssion = new ArrayList<>();
        if (roleName.getAsString().equals("admin")){
            resultPerssion.add("用户新增");
            resultPerssion.add("用户修改");
            resultPerssion.add("用户删除");
            resultPerssion.add("用户查询");
        }
        return resultPerssion;
    }


}
