package com.nikijava.elklog.webfilter;

import com.niki.log.MdcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
  *
  * @ProjectName:
  * @Package:        com.niki.log.webfilter
  * @ClassName:      LogTraceFilter
  * @Description:     类作描述
  * @Author:         Niki Zheng
  * @CreateDate:     2019/8/26 16:55
  * @UpdateRemark:   更新说明
  * @Version:        1.0
 */
public class LogTraceFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean bInsertMDC = MdcUtils.insertMDC();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (bInsertMDC) {
                MdcUtils.remove();
            }
        }
    }

    @Override
    public void destroy() {

    }


}