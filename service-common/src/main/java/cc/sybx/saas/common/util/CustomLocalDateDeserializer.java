package cc.sybx.saas.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @SuppressWarnings("deprecation")
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            return LocalDate.parse(str);
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new java.sql.Date(jp.getLongValue()).toLocalDate();
        }
        throw ctxt.mappingException(handledType());
    }
}
