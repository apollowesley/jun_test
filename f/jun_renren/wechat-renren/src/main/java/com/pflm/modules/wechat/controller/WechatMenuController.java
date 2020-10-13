package com.pflm.modules.wechat.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pflm.api.WeChatApi;
import com.pflm.modules.sys.controller.AbstractController;
import com.pflm.modules.wechat.entity.WxMenuEntity;
import com.pflm.modules.wechat.service.AccessTokenService;
import com.pflm.modules.wechat.service.WxMenService;
import com.pflm.utils.DateUtils;
import com.pflm.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * 微信自定义菜单
 * @author qinxuewu
 * @version 1.00
 * @time 16/11/2018下午 1:22
 */
@RestController
@RequestMapping("/sys/wechatmenu")
public class WechatMenuController extends AbstractController {

    @Autowired
    WxMenService wxMenService;
    @Autowired
    AccessTokenService accessTokenService;
    @Autowired
    WeChatApi weChatApi;
    /**
     * 微信公众号配置
     */
    @RequestMapping("/get")
    @RequiresPermissions("sys:wechatmenu:all")
    public R get() {
        WxMenuEntity menu=wxMenService.get();
        if(menu==null){
            JSONObject info=weChatApi.getCurrentSelfmenuInfo(accessTokenService.getToken());
            if(info.containsKey("data")&&info.getJSONObject("data")!=null){
                JSONObject me=info.getJSONObject("data");
                if(me.containsKey("selfmenu_info")){
                    menu=new WxMenuEntity();
                    JSONObject menuInfo=me.getJSONObject("selfmenu_info");

                    //组装成js需要的格式
                    //menu
                    JSONObject jsMenu=new JSONObject();
                    //button
                    JSONArray jsButton=new JSONArray();

                    JSONArray button=menuInfo.getJSONArray("button");
                    if(button.size()>0){
                        for (int i = 0; i <button.size() ; i++) {
                          JSONObject bt=button.getJSONObject(i);
                            if(bt.containsKey("sub_button")){
                                //有二级目录
                                JSONObject subButton=bt.getJSONObject("sub_button");
                                JSONArray list=subButton.getJSONArray("list");
                                bt.remove("sub_button");
                                JSONArray subArrray=new JSONArray();
                                for (int j = 0; j <list.size() ; j++) {
                                    JSONObject children=list.getJSONObject(j);
                                    subArrray.add(children);
                                }
                                bt.put("sub_button",subArrray);
                            }else{
                                bt.put("sub_button",new JSONArray());
                            }
                            jsButton.add(bt);
                            jsMenu.put("button",jsButton);
                        }
                    }else{
                        jsMenu.put("button",jsButton);
                    }
                    menu.setName(jsMenu.toJSONString());
                    menu.setCreateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
                    wxMenService.save(menu);
                }
            }

        }
        return R.ok().put("data",JSONObject.parseObject(menu.getName()));
    }
}
