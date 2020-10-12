import org.gen.code.config.Config;
import org.gen.code.parse.CodecParse;
import org.gen.code.parse.DaoParse;
import org.gen.code.parse.FileParse;
import org.gen.code.parse.MongoDaoParse;
import org.gen.code.util.FM;
import org.gen.code.util.KlassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/8/3.
 */
public class Gener {
    public static void main(String[] args) {

        final Config config = new Config();
        config.setCodecPackage("test.mongo.codec");
        config.setEntityPackage("test.mongo.entity");
        config.setDaoPackage("test.mongo.dao");
        config.setQueryModelPackage("test.mongo.model.query");


        final Set<Class<?>> classes = KlassHelper.getClasses(config.getEntityPackage());
        final Map<String, Class<?>> querysClasses = KlassHelper.getQuerysClasses(config.getQueryModelPackage());
        System.out.println(querysClasses);

        FileParse fileParse = new FileParse(config, classes, querysClasses);

        new CodecParse(config, classes).parseCodecMeta().forEach(codecMeta -> fileParse.saveCodec(codecMeta.getKlassSimpleName()+"Codec.java",FM.FM.runTemplate("codec",new HashMap<String,Object>(){{put("codec",codecMeta);}})));

        new DaoParse(config,classes,querysClasses).parse().forEach(daoMeta ->  fileParse.saveDao(daoMeta.getEntitySimpleName() +"Dao.java", FM.FM.runTemplate("dao", new HashMap<String, Object>() {{put("dao", daoMeta); }})));

        new MongoDaoParse(config,classes,querysClasses).parse().forEach(implMeta -> fileParse.saveDaoImpl("Mongo"+implMeta.getEntitySimpleName()+"Dao.java",FM.FM.runTemplate("impl", new HashMap<String, Object>() {{ put("impl", implMeta);}})));
    }


}
