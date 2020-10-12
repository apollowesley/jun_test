package com.cnhuashao.rapiddevelopment.core.demo4.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * 类 {@code FilterCoreUtil} 用于Xss非法标签过滤工具类 <br> 过滤html中的xss字符.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/5 19:42
 */
@Slf4j
public class FilterCoreUtil {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    private static final Whitelist WHITE_LIST = Whitelist.basicWithImages();
    /** 配置过滤化参数,不对代码进行格式化 */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);
    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        WHITE_LIST.addAttributes(":all", "style");
    }

    /**
     * 过滤主方法入口
     * @param content 需要过滤的字符串
     * @return 过滤后的字符串
     */
    public static String clean(String content) {
        if(StringUtils.isNotBlank(content)){
            content = content.trim();
        }
        return Jsoup.clean(content, "", WHITE_LIST, OUTPUT_SETTINGS);
    }
}
