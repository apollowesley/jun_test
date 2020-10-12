package org.gen.code.parse;

import org.gen.code.config.Config;
import org.gen.code.meta.CodecMeta;
import org.gen.code.meta.EncoderMeta;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2015/8/3.
 */
public class CodecParse extends AbstractParser {
    public CodecParse(Config config, Set<Class<?>> klasses) {
        super(config, klasses, null);
    }

        public List<CodecMeta> parseCodecMeta(){
            List<CodecMeta> result = new ArrayList<>();
            klasses.forEach(klass ->{
                CodecMeta codec = new CodecMeta();
                result.add(codec);
                codec.setCodecPackage(config.getCodecPackage());
                codec.setKlassName(klass.getName());
                codec.setKlassSimpleName(klass.getSimpleName());
                Arrays.asList(klass.getDeclaredFields()).stream().filter(this::filterSerialVersionUID).forEach(field -> {
                    codec.getFields().add(field.getName());
                    handleType(codec,field);
                });
            });
            return result;
        }

    private void handleType(CodecMeta codec, Field field) {
        final Class type = field.getType();
        if(type.isAssignableFrom(String.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"String"));
        else if(type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Integer"));
        else if(type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Long"));
        else if(type.isAssignableFrom(short.class) || type.isAssignableFrom(Short.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Short"));
        else if(type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Double"));
        else if(type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class))
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Boolean"));
        else if(type.isAssignableFrom(Date.class) || type.isAssignableFrom(Date.class)){
            codec.getImportList().add("java.util.Date");
            codec.getEncoderMetas().add(new EncoderMeta(field.getName(),"Date"));
        }


    }


}
