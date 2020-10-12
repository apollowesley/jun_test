package com.iotechn.iot.gw.controller;

import com.iotechn.iot.gw.api.ApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-09-13
 * Time: 上午9:31
 */
@Controller
@RequestMapping("/")
public class DocumentController {

    @Autowired
    private ApiManager apiManager;

    @RequestMapping("/")
    public ModelAndView index() {
        return group("user");
    }

    @RequestMapping("/info/group/{gp}")
    public ModelAndView group(@PathVariable("gp") String group) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("document");
        modelAndView.addObject("model", apiManager.generateGroupModel(group));
        modelAndView.addObject("groupNames", apiManager.generateDocumentModel().getGroups());
        return modelAndView;
    }


    @RequestMapping("/info/api/{gp}/{mt}")
    public ModelAndView api(@PathVariable("gp") String gp,@PathVariable("mt") String mt) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("method");
        modelAndView.addObject("methods", apiManager.methods(gp));
        modelAndView.addObject("model",apiManager.generateMethodModel(gp,mt));
        modelAndView.addObject("gp",gp);
        return modelAndView;
    }





}
