package com.luoqy.speedy.core.base.controller;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.luoqy.speedy.common.ConfigManage;
import com.luoqy.speedy.util.ParameDispose;
import com.luoqy.speedy.util.Result;
import com.luoqy.speedy.util.SendMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * @author luoqy
 * @date 2019年9月6日
 * 	工具方法类
 */
@Controller
@RequestMapping("/tool")
public class ToolController {
    /**
     *
     * type images videos audios
     * */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile file,String type){
        Result result=new Result();
       try{
           Map<String,String> map=ConfigManage.findProperties("path");
           String saveImagesPath=map.get("saveImagesPath");
           String getImagesPath=map.get("getImagesPath");
           if(null!=type&&!"".equals(type)){
               saveImagesPath=saveImagesPath.replaceAll("images",type);
               getImagesPath=getImagesPath.replaceAll("images",type);
           }
           if(!new File(saveImagesPath).exists()){
               new File(saveImagesPath).mkdirs();
           }
           file.transferTo(new File(saveImagesPath+File.separator+file.getOriginalFilename()));
           result.setMessage("上传成功");
           result.setState(1);

           result.setData(getImagesPath+"/"+file.getOriginalFilename());
           return result;
       }catch (Exception e){
           result.setState(0);
           result.setData(e);
           result.setMessage("上传失败");
           return result;
       }

    }
    /**
     *  短信发送机制
     * */
    @RequestMapping("/sendMessage")
    @ResponseBody
    public Result sendMessage(String phone, HttpServletRequest req){
            Result result=new Result();
        try{
            //腾讯云短信发送机制
            SmsSingleSenderResult res=SendMessage.sendTx(phone,req);
            //
            result.setState(1);
            result.setData(res);
            result.setMessage("发送成功");
            return result;
        }catch (Exception e){
            result.setData(e);
            result.setMessage("发送失败");
            result.setState(0);
            return result;
        }
    }
    /**
     *
     *  支付接口
     * */
    @RequestMapping("/pay")
    @ResponseBody
    public Result pay(String type,HttpServletRequest req){
        Result result=new Result();
        try{
            Map<String,String> parame=ParameDispose.getPara(req);
            if(null!=parame){
                if("wx".equals(type)){
                   //微信支付
                }else if("ali".equals(type)){
                    //支付宝支付
                }else{
                    //其他支付方式
                    result.setState(0);
                    result.setData(new String[0]);
                    result.setMessage("暂未支付其他付款方式");
                    return result;
                }
                result.setState(1);
                result.setData(new String[0]);
                result.setMessage("预订单已生成");
                return result;
            }else{
                result.setData(new String[0]);
                result.setMessage("订单生成失败");
                result.setState(0);
                return result;
            }
        }catch (Exception e){
            result.setData(e);
            result.setMessage("订单生成失败");
            result.setState(0);
            return result;
        }
    }
}
