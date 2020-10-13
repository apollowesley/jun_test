package fun.oook.c17.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * InputProductController
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 22:13
 */
public class InputProductController implements Controller {
    public static final Log log = LogFactory.getLog(InputProductController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("InputProductController called");
        return new ModelAndView("/c17/productForm");
    }
}
