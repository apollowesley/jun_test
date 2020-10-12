package org.scau.mystudio.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author jinglun
 * @create 2016-09-13 19:00
 */
@Controller
@Scope("prototype")
public class IndexAction extends ActionSupport {

    public String index() throws Exception{
        return SUCCESS;
    }
}
