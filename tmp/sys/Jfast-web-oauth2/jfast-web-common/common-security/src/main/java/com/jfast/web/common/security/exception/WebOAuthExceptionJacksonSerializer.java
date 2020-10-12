package com.jfast.web.common.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * @author Pan Weilong
 * @date 2019/7/10 10:10
 * @description: 接口.
 */
public class WebOAuthExceptionJacksonSerializer extends StdSerializer<WebOAuth2Exception> {

    protected WebOAuthExceptionJacksonSerializer() {
        super(WebOAuth2Exception.class);
    }

    @Override
    public void serialize(WebOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("code", value.getHttpErrorCode());
        jgen.writeStringField("msg", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jgen.writeStringField(key, add);
            }
        }
        jgen.writeEndObject();
    }
}
