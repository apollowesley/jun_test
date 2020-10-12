package com.nikijava.elklog.dubbofilter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.nikijava.elklog.MdcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
  *
  * @ProjectName:
  * @Package:        com.niki.log.dubbofilter
  * @ClassName:      DubboLogFilter
  * @Description:     类作描述
  * @Author:         Niki Zheng
  * @CreateDate:     2019/8/26 16:55
  * @UpdateRemark:   更新说明
  * @Version:        1.0
 */

@Activate
public class DubboLogFilter implements Filter {
    private static final String UNIQUE_ID = "traceRootId";

    protected final static Logger logger = LoggerFactory.getLogger(DubboLogFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        boolean bInsertMDC = MdcUtils.insertMDC();
        Result result = null;
        try {
            result = invoker.invoke(invocation);
            if (result.hasException()) {
                /**
                 *  如果有异常
                 */
            } else {
                /**
                 * 如果成功
                 */
            }
        }  catch (Exception e) {
        } finally {
            if (bInsertMDC) {
                MdcUtils.remove();
            }
        }
        return result;
    }
}

