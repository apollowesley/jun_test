package com.example.demo.controller;

import com.example.demo.entity.Msg;
import com.example.demo.service.IndecService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndecController {
    /*
    简单总结一下
    1.使用一个实体
    2.实现分页
    3.对数据进行CRUD
    4.又一次变成被迫写前端的后端程序员

    最重要的是：我终于完成了分页的实现

    不足
    1.需要对发的表情包进行处理一下才能显示，只需要对数据进行一次截取就可以进行显示了
     */


    @Autowired
    private IndecService indecService;

    @RequestMapping("/")
    public String index(Model model){
        //进入首页进行数据渲染:用Map进行，因为是一对多个跟帖，最难是的如何进行sql语句的书写
        Map<Msg, List<Msg> > allMsg=new HashMap<>();//主键是主贴，list集合是跟帖
        //1.先找总贴数---一次查询
        List<Msg> msgcount=indecService.findCount();
        for(int i=0;i<msgcount.size();i++){
            //回帖的主键是空，说明是主贴
            if(msgcount.get(i).getMsgmainid().equals("")){
                List<Msg> tmpreply=new ArrayList<>();
                for(int j=0;j<msgcount.size();j++){
                    if(msgcount.get(j).getMsgmainid().equals(msgcount.get(i).getMsguuid())){
                        tmpreply.add(msgcount.get(j));
                    }
                }
                allMsg.put(msgcount.get(i), tmpreply);
            }
        }
        /*
        //这是一个map循环，测试数据使用，最重要
        for(Map.Entry<Msg,List<Msg>> entry : allMsg.entrySet()){
            Msg key=entry.getKey();
            System.out.println("主贴："+key);
            List<Msg> tmmplist=entry.getValue();
            System.out.println(tmmplist.size());
            for(int i=0;i<tmmplist.size();i++){
                System.out.println("跟帖:"+tmmplist.get(i));
            }
        }
        */
        model.addAttribute("allMsg", allMsg);
        model.addAttribute("pageNum", msgcount.size()%5==0? msgcount.size()/5:msgcount.size()/5+1 );
        model.addAttribute("current", 1);
        return "index";
    }
    //发帖
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public String indexShow(@RequestParam(value="message") String message){
        //进入此方法说明是发帖，是主贴
        Msg msg=new Msg();
        msg.setMsguuid(UUID.randomUUID().toString());
        msg.setMsgusername(IndecController.getRandomJianHan((int)(Math.random()*8+1)));//随机生成由1-8个汉字组成的用户名
        msg.setMsguserpicture("/static/images/tx.jpg");//主贴人的头像
        msg.setMsgdate(IndecController.getStringDate());
        msg.setMsgcontent(message);
        msg.setMsgmainid("");
        //插入数据库中,主题帖没有跟帖的id，所以设置为空
        indecService.insertData(msg.getMsguuid(),msg.getMsgusername(),msg.getMsguserpicture(),msg.getMsgdate(),msg.getMsgcontent(),"");
        return "redirect:/";
    }
    //跟帖
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public String Reply(@RequestParam(value="replyid") String replyid,@RequestParam("replymessage") String replymessage){
        Msg msg=new Msg();
        msg.setMsguuid(UUID.randomUUID().toString());
        msg.setMsgusername(IndecController.getRandomJianHan((int)(Math.random()*8+1)));//随机生成由1-8个汉字组成的用户名
        msg.setMsguserpicture("/static/images/tx.jpg");//主贴人的头像
        msg.setMsgdate(IndecController.getStringDate());
        msg.setMsgcontent(replymessage);
        msg.setMsgmainid(replyid);
        //插入数据库中,主题帖没有跟帖的id，所以设置为空
        indecService.insertData(msg.getMsguuid(),msg.getMsgusername(),msg.getMsguserpicture(),msg.getMsgdate(),msg.getMsgcontent(),msg.getMsgmainid());
        return "redirect:/";
    }

    //删帖
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="msguuid") String msguuid){
        //1.对于删帖，一定要删除主贴和跟帖，这样才能实现对帖子的真正删除
        //2.如果限制只有管理员才能进行删除，只需要获取管理员的权限，在页面进行权限判断是否显示
        //删除这个按钮即可
        List<Msg> msgcount=indecService.findCount();
        for(int i=0;i<msgcount.size();i++){
            //第一个判断条件是跟帖的主贴，第二个是主贴
            if(msgcount.get(i).getMsgmainid().equals(msguuid)||msgcount.get(i).getMsguuid().equals(msguuid)){
                indecService.deleteMsg(msgcount.get(i).getMsguuid());
            }
        }
        return "redirect:/";
    }
    @RequestMapping("/getpagenum")
    public String shownumindex(@RequestParam(value = "pageNum") Integer pageNum,Model model){
        List<Msg> msgcount=indecService.findCount();
        PageHelper.startPage(pageNum, 5);
        Page<Msg> msglist= indecService.getListMsg();
        System.out.println(msglist.size());
        model.addAttribute("pageNum", msgcount.size()%5==0? msgcount.size()/5:msgcount.size()/5+1 );
        model.addAttribute("current", pageNum);
        return "redirect:/";
    }
    @ResponseBody
    @RequestMapping("/getUserList")
    public Page<Msg> getMsgList(@RequestParam(value = "pageNum")Integer pageNum,@RequestParam(value = "pageSize")Integer pageSize){
        //下面这两句话实现的分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Msg> msglist= indecService.getListMsg();
        return msglist;
    }
    /**
     * 获取指定长度随机简体中文
     */
    public static String getRandomJianHan(int len)
    {
        String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try
            {
                str = new String(b, "GBk"); //转成中文
            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
    }
    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /*

     */
}
