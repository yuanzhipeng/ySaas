package cc.sybx.saas.common.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ValidationPattern {

    private static final Logger logger = LoggerFactory.getLogger(ValidationPattern.class);
    private static Map<String, String> map = null;
    private static final Map<String, Pattern> patternCache;

    private ValidationPattern() {
    }

    public static Pattern getValidationPattern(String key) {
        String value = map.get("Validator." + key);
        Pattern p = patternCache.get(value);
        if (p != null) {
            return p;
        } else if (value != null && !value.equals("")) {
            try {
                Pattern q = Pattern.compile(value);
                patternCache.put(value, q);
                return q;
            } catch (PatternSyntaxException var4) {
                logger.warn("SecurityConfiguration for " + key + " not a valid regex in XSS.properties. Returning null");
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getValidInput(String context, String input, String type, int maxLength, boolean allowNull) throws ValidationException {
        ValidationRule vr = new ValidationRule(type);
        Pattern p = getValidationPattern(type);
        if (p != null) {
            vr.addWhitelistPattern(p);
            vr.setMaximumLength(maxLength);
            vr.setAllowNull(allowNull);
            String var7 = vr.getValid(context, input);
            return var7;
        } else {
            throw new IllegalArgumentException("The selected type [" + type + "] was not set via the validation configuration");
        }
    }

    public static String stripXSS(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return value;
            } else {
                value = value.replaceAll("\u0000", "");
                value = htmlEncode(value);
                return value;
            }
        } catch (Exception var5) {
            logger.warn("stripXSS fail:  " + value);
            var5.printStackTrace();
            return value;
        } finally {
            ;
        }
    }

    public static String htmlEncode(String source) {
        String html = "";
        if (source == null) {
            return html;
        } else {
            StringBuffer buffer = new StringBuffer();

            for(int i = 0; i < source.length(); ++i) {
                char c = source.charAt(i);
                switch(c) {
                    case '\n':
                    case '\r':
                        break;
                    case '"':
                        buffer.append("&quot;");
                        break;
                    case '&':
                        buffer.append("&amp;");
                        break;
                    case '\'':
                        buffer.append("&apos;");
                        break;
                    case '<':
                        buffer.append("&lt;");
                        break;
                    case '>':
                        buffer.append("&gt;");
                        break;
                    default:
                        buffer.append(c);
                }
            }

            html = buffer.toString();
            return html;
        }
    }

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("XSS.properties");

        try {
            Properties properties = new Properties();
            properties.load(is);
            map = new HashMap(properties);
        } catch (Exception var4) {
            var4.printStackTrace();
            logger.error("load XSS Filter conf file error:", var4);
        }

        patternCache = new HashMap();
    }
}
