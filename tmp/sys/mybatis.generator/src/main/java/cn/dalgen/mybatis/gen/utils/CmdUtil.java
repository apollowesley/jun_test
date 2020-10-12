package cn.dalgen.mybatis.gen.utils;

import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

/**
 * Created by bangis.wangdf on 15/12/3. Desc 控制台输入
 */
public class CmdUtil {
    private static final String mvn_version_url
        = "http://search.maven.org/solrsearch/select?q=g%3A%22cn.dalgen"
        + ".plugins%22%20a%3A%22mybatis-maven-plugin%22&rows=20&wt=json";

    public static String DALGEN_VERSION = "";
    /**
     * 获取控制台输入
     *
     * @return 控制台命令
     */
    public String consoleInput() {

        Scanner cmdIn = new Scanner(System.in);
        //只有一个DB时
        if (ConfigUtil.getConfig().getDataSourceMap().size() == 1) {
            ConfigUtil.setCmd(chooseTableCmd(cmdIn));
        } else {
            System.out.println("==============选择需要从哪个库中生成===============");
            int i = 0;
            for (String dbStr : ConfigUtil.getConfig().getDataSourceMap().keySet()) {
                i++;
                System.out.println(i + " : " + dbStr);
            }
            System.out.println("==============选择需要从哪个库中生成===============");

            int dbInt = cmdIn.nextInt();
            if (dbInt > i && dbInt < 1) {

                System.out.println("输入有误,自动退出[后续改进...]");
                return "q";
            } else {
                i = 0;
                for (String dbStr : ConfigUtil.getConfig().getDataSourceMap().keySet()) {
                    i++;
                    if (i == dbInt) {
                        ConfigUtil.setCurrentDb(dbStr);
                    }
                }
            }

            ConfigUtil.setCmd(chooseTableCmd(cmdIn));
        }

        return ConfigUtil.getCmd();
    }

    private String chooseTableCmd(Scanner cmdIn) {

        // checkNewVersion();
        System.out.println("===========输入需要生成的表==============");
        System.out.println("-- * 标示所有");
        System.out.println("-- 多表用 ; 分隔");
        //System.out.println("-- test 生成单元测试 依赖recruit.test");
        System.out.println("-- 新表会先生成默认配置,已有表不会影响修改后的SQL");
        System.out.println("-- q 退出");
        for (String custCmd : ConfigUtil.getConfig().cmdNames()) {
            System.out.println("-- " + custCmd + " 扩展命令");

        }


        System.out.println("===========输入需要生成的表==============");
        String _cmd = cmdIn.next();
        if (StringUtils.isBlank(_cmd)) {
            System.out.println("空输入默认为 * ");
            _cmd = "*";
        }
        return _cmd;
    }

    //private void checkNewVersion() {
    //    try {
    //        String jsonBody = Jsoup.connect(mvn_version_url).timeout(500).ignoreContentType(true).execute().body();
    //        JSONObject jsonObject = JSON.parseObject(jsonBody);
    //        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("docs");
    //        jsonObject = (JSONObject)jsonArray.get(0);
    //        String lastVersion = jsonObject.getString("latestVersion");
    //        if(!lastVersion.equals(CmdUtil.DALGEN_VERSION)){
    //            System.out.println("===========升级提醒==============");
    //            System.out.println("...... 建议升级至最新版本:" + lastVersion);
    //        }
    //
    //    } catch (Exception e) {
    //        //e.printStackTrace();
    //    }
    //}

}
