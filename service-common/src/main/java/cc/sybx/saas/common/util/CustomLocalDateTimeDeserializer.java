package cc.sybx.saas.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final String DATE_TIME_FORMATTER_MILLI_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN);
    private static DateTimeFormatter DATE_TIME_FORMATTER_MILLI = DateTimeFormatter.ofPattern
            (DATE_TIME_FORMATTER_MILLI_PATTERN);

    @SuppressWarnings("deprecation")
    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText();
            if(StringUtils.isBlank(text)) {
                return null;
            }
            String str = text.trim();
            if (DATE_TIME_FORMATTER_PATTERN.length() == str.length()) {
                str  = str.replace('T',' ');
                return LocalDateTime.parse(str, DATE_TIME_FORMATTER);
            } else if (DATE_TIME_FORMATTER_MILLI_PATTERN.length() == str.length()) {
                return LocalDateTime.parse(str, DATE_TIME_FORMATTER_MILLI);
            }
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new Timestamp(jp.getLongValue()).toLocalDateTime();
        }
        throw ctxt.mappingException(handledType());
    }
}
