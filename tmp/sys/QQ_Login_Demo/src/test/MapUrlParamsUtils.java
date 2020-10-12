/*
 * Copyright 2018 kiwipeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 地址栏参数和Map类型互相转换工具类
 *
 * @author kiwipeach [1099501218@qq.com]
 * @create 2018/09/10
 */
public class MapUrlParamsUtils {
    /**
     * 将url参数转换成map
     *
     * @param url 地址：aa=11&bb=22&cc=33
     * @return 返回Map类型
     */
    public static Map<String, String> urlToMap(String url) {
        Map<String, String> map = new HashMap<String, String>(0);
        if (StringUtils.isEmpty(url)) {
            return map;
        } else {
            url = url.substring(url.indexOf("?") + 1, url.length());
            String[] params = url.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    map.put(p[0], p[1]);
                }
            }
            return map;
        }
    }

    /**
     * 将map转换成url
     *
     * @param map Map类型参数
     * @return 返回地址字符串
     */
    public static String mapToUrl(String host, Map<String, String> map) {
        if (map.isEmpty()) {
            return "";
        } else {
            host = StringUtils.isEmpty(host) ? "" : host;
            StringBuffer sb = new StringBuffer(host).append("?");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
            String s = sb.toString();
            if (s.endsWith("&")) {
                s = s.substring(0, s.lastIndexOf("&"));
            }
            return s;
        }
    }

    public static void main(String[] args) {
        /*1.获取地址栏参数转Map类型*/
        String url = "http:www.kiwipeach.cn?aa=11&bb=22&cc=33";
        Map<String, String> urlMap = urlToMap(url);
        //{aa=11, bb=22, cc=33}
        System.out.println(urlMap);

        /*2.Map类型转Url字符串*/
        String urlStr = mapToUrl("http://www.kiwipeach.cn", urlMap);
        System.out.println(urlStr);
    }
}
