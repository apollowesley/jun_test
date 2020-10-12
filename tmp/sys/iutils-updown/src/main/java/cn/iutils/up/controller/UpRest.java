package cn.iutils.up.controller;

import cn.iutils.common.Page;
import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.up.entity.Up;
import cn.iutils.up.service.UpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 顶插件 接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${restPath}/up")
public class UpRest extends BaseController {

    @Autowired
    private UpService upService;

    /**
     * 顶 接口
     * @param up
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResultVo submit(Up up){
        ResultVo resultVo = null;
        try {
            //记录操作者
            User user = UserUtils.getLoginUser();
            String operator = user!=null?user.getId():"";
            Up upTemp = upService.get(up);
            if(upTemp!=null){
                up = upTemp;
            }
            //未登录和重复点击 过滤
            if (operator!=null && !"".equals(operator) && up.getOperator().indexOf(operator+",")<=-1){
                up.setOperator(up.getOperator() + operator + ",");
                up.setHits(up.getHits()+1);
                upService.save(up);
            }
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",up.getHits());
        }catch (Exception e){
            logger.error("顶接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 顶 接口
     * @param up
     * @return
     */
    @RequestMapping(value = "/up",method = RequestMethod.GET)
    public @ResponseBody
    ResultVo up(Up up){
        ResultVo resultVo = null;
        try {
            //记录操作者
            User user = UserUtils.getLoginUser();
            String operator = user!=null?user.getId():"";
            Up upTemp = upService.get(up);
            if(upTemp!=null){
                up = upTemp;
            }
            //判断是否有操作者
            if (operator!=null && !"".equals(operator) && up.getOperator().indexOf(operator+",")<=-1){
                up.setOperator(up.getOperator() + operator + ",");
            }
            up.setHits(up.getHits()+1);
            upService.save(up);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",up.getHits());
        }catch (Exception e){
            logger.error("顶接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 获取顶数量 接口
     * @param up
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo count(Up up){
        ResultVo resultVo = null;
        try {
            Up upTemp = upService.get(up);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",upTemp.getHits());
        }catch (Exception e){
            logger.error("获取踩数量接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

}
