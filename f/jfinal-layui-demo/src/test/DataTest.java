import com.imzdx.common.AppConfig;
import com.imzdx.common.model.File;
import com.imzdx.common.model.User;
import com.imzdx.common.model._MappingKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by imzdx on 2017/12/27 0027.
 */
public class DataTest {

    public static void main(String[] args) {
        init();
        insertModel(new File(), "name,url,category,module,ext");
        insertModel(new User(), "username,password,nickname,sex,sign,experience,score");
//        insertModel(new Blog(), "title,content");
    }

    private static void insertModel(Model model, String arr) {
        String[] params = arr.split(",");
        String param;
        int times = 100;
        for (int i = 1; i <= times; i++) {
            model.set("id", null);
            for (int j = 0; j < params.length; j++) {
                param = params[j];
                model.set(param, param + i);
            }
            model.save();
        }
    }

    private static void init() {
        PropKit.use("config.properties");
        DruidPlugin dp = AppConfig.createDruidPlugin();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        _MappingKit.mapping(arp);
        // 与web环境唯一的不同是要手动调用一次相关插件的start()方法
        dp.start();
        arp.start();
    }

}