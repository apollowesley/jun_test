package cn.kiwipeach.ws.api;


import cn.kiwipeach.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/11/12
 * Description: Helloworld接口
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@WebService
public interface HelloWorldWS {
    @WebMethod
    String sayHello(String word);

    @WebMethod
    User getUser(String name);

    @WebMethod
    List<User> getListUser();

    /*CXF支持返回Map类型数据*/
    @WebMethod
    Map<String, User> getMapUser(String key, String username);

}
