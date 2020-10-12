package cn.kiwipeach.controller;

import cn.kiwipeach.bean.Employ;
import cn.kiwipeach.util.DBTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by liuburu on 2017/6/6.
 */
@Controller
@RequestMapping("jdbc")
public class KiWiPeachControl {

    private Logger logger = LoggerFactory.getLogger(KiWiPeachControl.class);

    @ModelAttribute
    public void beforeController(){
        DBTools.initDataSource(null);
    }

    @RequestMapping("/list/{id}")
    public String toListPage(@PathVariable("id") Integer id, Model model) throws Exception {
        String sql = "SELECT * FROM emp";
        List<Employ> employs = DBTools.queryList(Employ.class, sql);
        for (Employ employ : employs) {
            System.out.println(employ);
        }

        logger.info("进入==>列表页面");
        System.out.println("调用业务逻辑");
        if(id==0){
            logger.info("程序发生了异常:{}",new Exception("我草你妈了"));
            throw new Exception("我草你妈了");
        }
        logger.info("退出==>列表页面");
        model.addAttribute("employs",employs);
        return "list";
    }
}
