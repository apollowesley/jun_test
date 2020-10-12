package org.gen.code.parse;

import org.gen.code.config.Config;
import org.gen.code.meta.DaoMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/8/4.
 */
public class DaoParse extends AbstractParser {
    public DaoParse(Config config, Set<Class<?>> klasses, Map<String, Class<?>> querys) {
        super(config, klasses, querys);
    }

    public List<DaoMeta> parse() {
        List<DaoMeta> result = new ArrayList<>();

        klasses.forEach(k->{
            DaoMeta meta = new DaoMeta();
            result.add(meta);
            meta.setDaoPackage(config.getDaoPackage());
            meta.setEntityName(k.getName());
            meta.setEntitySimpleName(k.getSimpleName());
            Class<?> class1 = querys.get(k.getSimpleName()+"QueryModel");
            if (class1 != null) {
                meta.setQueryModel(class1.getName());
                meta.setQuerySimpleModel(class1.getSimpleName());
            }
        });
        return result;
    }

    ;


}
