package org.gen.code.parse;

import org.gen.code.config.Config;
import org.gen.code.meta.DaoImplMeta;
import org.gen.code.meta.QueryMeta;

import java.lang.reflect.Field;
import java.util.*;



/**
 * Created by Administrator on 2015/8/11.
 */
public class MongoDaoParse extends AbstractParser {
    public MongoDaoParse(Config config, Set<Class<?>> klasses, Map<String, Class<?>> querys) {
        super(config, klasses, querys);
    }
    public List<DaoImplMeta> parse(){
        List<DaoImplMeta> result = new ArrayList<>();
        this.klasses.stream().forEach(k->{
            final DaoImplMeta d = new DaoImplMeta();
            result.add(d);
            d.setEntitySimpleName(k.getSimpleName());
            d.setEntityName(k.getName());
            d.setCodecPackage(config.getCodecPackage());
            d.setDaoPackage(config.getDaoPackage());
            final Class<?> aClass = querys.get(k.getSimpleName() + "QueryModel");
            if (aClass != null) {
                d.setQueryModelName(aClass.getName());
                d.setQuerySimpleName(aClass.getSimpleName());
                Arrays.asList(aClass.getDeclaredFields()).forEach(f -> {
                    final QueryMeta e = new QueryMeta();
                    final String name = f.getName();
                    e.setName(name);
                    if (name.endsWith("NL") || name.endsWith("NN")) {
                        d.getNullQuerys().add(e);
                    } else {
                        handle(e, f);
                        d.getQuerys().add(e);
                    }

                });
            }
        });


        return result;
    }

    private void handle(QueryMeta e, Field field) {
        String name = field.getName();
        String value = null;
        if (name.endsWith("NEQ")) {
            e.setFieldName(name.replace("NEQ", ""));
            e.setLink("$ne");
        } else if (name.endsWith("EQ")) {
            e.setFieldName(name.replace("EQ", ""));
            e.setLink("$eq");
        } else if (name.endsWith("GT")) {
            e.setFieldName(name.replace("GT", ""));
            e.setLink("$gt");
        } else if (name.endsWith("GTE")) {
            e.setFieldName(name.replace("GTE", ""));
            e.setLink("$gte");
        } else if (name.endsWith("LT")) {
            e.setFieldName(name.replace("LT", ""));
            e.setLink("$lt");
        } else if (name.endsWith("LTE")) {
            e.setFieldName(name.replace("LTE", ""));
            e.setLink("$lte");
        } /*else if (name.endsWith("NL")) {
            String camelToUnderline = name.replace("NL", "");
            value = " and " + camelToUnderline + " is null #{" + name + "}";
        } else if (name.endsWith("NN")) {
            String camelToUnderline = name.replace("NN", "");
            value = " and " + camelToUnderline + " is not null #{" + name + "}";
        }*/ else if (name.endsWith("LK")) {
            e.setFieldName(name.replace("LK", ""));
            e.setStart(" java.util.regex.Pattern.compile(\"^.*\" +");
            e.setEnd("+ \".*$\");");
        } else if (name.endsWith("SW")) {
            e.setFieldName(name.replace("SW", ""));
            e.setStart(" java.util.regex.Pattern.compile(\"^\" +");
            e.setEnd("+ \".*$\");");
        } else if (name.endsWith("EW")) {
            e.setFieldName(name.replace("EW", ""));
            e.setStart(" java.util.regex.Pattern.compile(\"^\" +");
            e.setEnd("+ \"$\");");
        } else if (name.endsWith("IN")) {
            e.setFieldName(name.replace("IN", ""));
            e.setLink("$in");
        } else
            return;

    }

}
