package com.jfire.codejson.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.codejson.function.JsonWriter;
import com.jfire.codejson.function.WriterContext;
import com.jfire.codejson.function.impl.write.array.BooleanArrayWriter;
import com.jfire.codejson.function.impl.write.array.ByteArrayWriter;
import com.jfire.codejson.function.impl.write.array.CharArrayWriter;
import com.jfire.codejson.function.impl.write.array.DoubleArrayWriter;
import com.jfire.codejson.function.impl.write.array.FloatArrayWriter;
import com.jfire.codejson.function.impl.write.array.IntArrayWriter;
import com.jfire.codejson.function.impl.write.array.LongArrayWriter;
import com.jfire.codejson.function.impl.write.array.ShortArrayWriter;
import com.jfire.codejson.function.impl.write.array.StringArrayWriter;
import com.jfire.codejson.function.impl.write.extra.ArrayListWriter;
import com.jfire.codejson.function.impl.write.extra.DateWriter;
import com.jfire.codejson.function.impl.write.wrapper.BooleanWriter;
import com.jfire.codejson.function.impl.write.wrapper.ByteWriter;
import com.jfire.codejson.function.impl.write.wrapper.CharacterWriter;
import com.jfire.codejson.function.impl.write.wrapper.DoubleWriter;
import com.jfire.codejson.function.impl.write.wrapper.FloatWriter;
import com.jfire.codejson.function.impl.write.wrapper.IntegerWriter;
import com.jfire.codejson.function.impl.write.wrapper.LongWriter;
import com.jfire.codejson.function.impl.write.wrapper.ShortWriter;
import com.jfire.codejson.function.impl.write.wrapper.StringWriter;

public class WriteStrategy implements Strategy
{
    private Map<Class<?>, JsonWriter> typeStrategy  = new HashMap<>();
    private Map<String, JsonWriter>   fieldStrategy = new HashMap<>();
    private Set<String>               ignoreFields  = new HashSet<>();
    private Map<String, String>       renameFields  = new HashMap<>();
    private JsonWriter                writer;
    private ThreadLocal<StringCache>  cacheLocal    = new ThreadLocal<StringCache>() {
                                                        protected StringCache initialValue()
                                                        {
                                                            return new StringCache();
                                                        }
                                                    };
    
    public WriteStrategy()
    {
        typeStrategy.put(String.class, new StringWriter());
        typeStrategy.put(Double.class, new DoubleWriter());
        typeStrategy.put(Float.class, new FloatWriter());
        typeStrategy.put(Integer.class, new IntegerWriter());
        typeStrategy.put(Long.class, new LongWriter());
        typeStrategy.put(Short.class, new ShortWriter());
        typeStrategy.put(Boolean.class, new BooleanWriter());
        typeStrategy.put(Byte.class, new ByteWriter());
        typeStrategy.put(Character.class, new CharacterWriter());
        typeStrategy.put(int[].class, new IntArrayWriter());
        typeStrategy.put(boolean[].class, new BooleanArrayWriter());
        typeStrategy.put(long[].class, new LongArrayWriter());
        typeStrategy.put(short[].class, new ShortArrayWriter());
        typeStrategy.put(byte[].class, new ByteArrayWriter());
        typeStrategy.put(float[].class, new FloatArrayWriter());
        typeStrategy.put(double[].class, new DoubleArrayWriter());
        typeStrategy.put(char[].class, new CharArrayWriter());
        typeStrategy.put(String[].class, new StringArrayWriter());
        typeStrategy.put(ArrayList.class, new ArrayListWriter());
        typeStrategy.put(Date.class, new DateWriter());
        typeStrategy.put(java.sql.Date.class, new DateWriter());
    }
    
    public boolean containsType(Class<?> type)
    {
        return typeStrategy.containsKey(type);
    }
    
    public JsonWriter getWriter(Class<?> type)
    {
        writer = typeStrategy.get(type);
        if (writer == null)
        {
            writer = WriterContext.getWriter(type, this);
            typeStrategy.put(type, writer);
            return writer;
        }
        else
        {
            return writer;
        }
    }
    
    public void addWriter(Class<?> ckass, JsonWriter jsonWriter)
    {
        typeStrategy.put(ckass, jsonWriter);
    }
    
    public boolean containsField(String fieldName)
    {
        return fieldStrategy.containsKey(fieldName);
    }
    
    public JsonWriter getWriterByField(String fieldName)
    {
        return fieldStrategy.get(fieldName);
    }
    
    public void addFieldStrategy(String fieldName, JsonWriter writer)
    {
        fieldStrategy.put(fieldName, writer);
        
    }
    
    public void addIgnoreField(String fieldName)
    {
        ignoreFields.add(fieldName);
    }
    
    public boolean ignore(String fieldName)
    {
        return ignoreFields.contains(fieldName);
    }
    
    public void addRenameField(String originName, String rename)
    {
        renameFields.put(originName, rename);
    }
    
    public String getRename(String fieldName)
    {
        return renameFields.get(fieldName);
    }
    
    public boolean containsRename(String fieldName)
    {
        return renameFields.containsKey(fieldName);
    }
    
    public String write(Object entity)
    {
        StringCache cache = cacheLocal.get();
        cache.clear();
        getWriter(entity.getClass()).write(entity, cache);
        return cache.toString();
    }
}
