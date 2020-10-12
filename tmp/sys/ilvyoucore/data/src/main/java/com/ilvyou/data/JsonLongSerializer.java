package com.ilvyou.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by ljw on 2016/10/29.
 */
public class JsonLongSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long mlong, JsonGenerator jsonGenerator,
                          SerializerProvider serialzerProvider)
            throws IOException,JsonProcessingException {
        jsonGenerator.writeString(Long.toString(mlong));
    }
}
