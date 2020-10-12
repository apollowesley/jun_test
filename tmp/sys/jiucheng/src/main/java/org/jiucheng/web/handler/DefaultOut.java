package org.jiucheng.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.util.WebUtil;

public class DefaultOut implements Out {

    public void invoke(WebWrapper webWrapper, Object rs) {
        if(null == rs) {
            return;
        }
        HttpServletResponse response = WebUtil.getResponse();
        response.setCharacterEncoding(WebUtil.getEncoding());
        response.setContentType("text/html;charset=" + WebUtil.getEncoding());
        if(rs instanceof String) {
            WebUtil.dispatcher(rs.toString());
        }else {
            try {
                PrintWriter out = response.getWriter();
                out.print(rs);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
