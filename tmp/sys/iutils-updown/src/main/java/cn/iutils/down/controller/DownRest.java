package cn.iutils.down.controller;

import cn.iutils.common.Page;
import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.down.entity.Down;
import cn.iutils.down.service.DownService;
import cn.iutils.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 踩插件 接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${restPath}/down")
public class DownRest extends BaseController {

    @Autowired
    private DownService downService;

    /**
     * 踩 接口
     * @param down
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResultVo submit(Down down){
        ResultVo resultVo = null;
        try {
            //记录操作者
            User user = UserUtils.getLoginUser();
            String operator = user!=null?user.getId():"";
            Down downTemp = downService.get(down);
            if(downTemp!=null){
                down = downTemp;
            }
            //未登录和重复点击 过滤
            if (operator!=null && !"".equals(operator) && down.getOperator().indexOf(operator+",")<=-1){
                down.setHits(down.getHits()+1);
                down.setOperator(down.getOperator() + operator + ",");
                downService.save(down);
            }
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",down.getHits());
        }catch (Exception e){
            logger.error("踩接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 踩 接口
     * @param down
     * @return
     */
    @RequestMapping(value = "/down",method = RequestMethod.GET)
    public @ResponseBody
    ResultVo down(Down down){
        ResultVo resultVo = null;
        try {
            //记录操作者
            User user = UserUtils.getLoginUser();
            String operator = user!=null?user.getId():"";
            Down downTemp = downService.get(down);
            if(downTemp!=null){
                down = downTemp;
            }
            //判断是有用户
            if (operator!=null && !"".equals(operator) && down.getOperator().indexOf(operator+",")<=-1){
                down.setOperator(down.getOperator() + operator + ",");
            }
            down.setHits(down.getHits()+1);
            downService.save(down);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",down.getHits());
        }catch (Exception e){
            logger.error("踩接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

    /**
     * 获取踩数量 接口
     * @param down
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public @ResponseBody
    ResultVo count(Down down){
        ResultVo resultVo = null;
        try {
            Down downTemp = downService.get(down);
            resultVo = new ResultVo(ResultVo.SUCCESS,"1","调用成功",downTemp.getHits());
        }catch (Exception e){
            logger.error("获取踩数量接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","调用失败",null);
        }
        return resultVo;
    }

}
