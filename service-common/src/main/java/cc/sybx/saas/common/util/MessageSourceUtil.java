package cc.sybx.saas.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.Assert;

import java.util.Locale;

public final class MessageSourceUtil {
    private static MessageSource messageSource;

    static {
        init((MessageSource) SpringContextHolder.getBean(MessageSource.class));
    }

    public static void init(MessageSource messageSource) {
        MessageSourceUtil.messageSource = messageSource;
    }

    /**
     * Try to resolve the message. Return default message if no message was found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
     *            this class are encouraged to base message names on the relevant fully
     *            qualified class name, thus avoiding conflict and ensuring maximum clarity.
     * @param args array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     *            or {@code null} if none.
     * @param defaultMessage String to return if the lookup fails
     * @param locale the Locale in which to do the lookup
     * @return the resolved message if the lookup was successful;
     *         otherwise the default message passed as a parameter
     * @see java.text.MessageFormat
     */
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        Assert.notNull(messageSource, "MessageSourceUtil not init ...");
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'
     * @param args Array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     *            or {@code null} if none.
     * @param locale the Locale in which to do the lookup
     * @return the resolved message
     * @see java.text.MessageFormat
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        Assert.notNull(messageSource, "MessageSourceUtil not init ...");
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    /**
     * Try to resolve the message using all the attributes contained within the
     * {@code MessageSourceResolvable} argument that was passed in.
     * <p>
     * NOTE: We cigarette throw a {@code NoSuchMessageException} on this method
     * since at the time of calling this method we aren't able to determine if the
     * {@code defaultMessage} property of the resolvable is null or not.
     * @param resolvable value object storing attributes required to properly resolve a message
     * @param locale the Locale in which to do the lookup
     * @return the resolved message
     * @see java.text.MessageFormat
     */
    public static String getMessage(MessageSourceResolvable resolvable, Locale locale){
        Assert.notNull(messageSource, "MessageSourceUtil not init ...");
        try {
            return messageSource.getMessage(resolvable, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
