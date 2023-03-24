package cc.sybx.saas.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String SQL_LIKE_CHAR = "%";

    public static final String ES_LIKE_CHAR = "*";

    /**
     * 对象转换类型
     * @param object 值
     * @param clazz 对象
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object object, Class<T> clazz){
        if(object != null && clazz.isInstance(object)) {
            return clazz.cast(object);
        }
        return null;
    }

    /**
     * 数组转换类型
     * @param objects 对象数组
     * @param index 索引
     * @param clazz 类型
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object[] objects, int index , Class<T> clazz){
        return cast(objects[index], clazz);
    }

    public static String trunc(String str, int len, String afterPrefix) {
        if (StringUtils.isNotBlank(str) && str.length() > len) {
            return str.substring(0, len).concat(afterPrefix);
        }
        return str;
    }

    /**
     * 提取中文
     * @param str
     * @return
     */
    public static List<String> pickChs(String str) {
        List<String> strList = new ArrayList<>();
        //使用正则表达式
        Pattern pattern = Pattern.compile("([\u4E00-\u9FA5]{2,})");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            strList.add(matcher.group());
        }
        return strList;
    }

    /**
     * 提取英文
     * @param str
     * @return
     */
    public static List<String> pickEng(String str) {
        List<String> strList = new ArrayList<>();
        //使用正则表达式
        Pattern pattern = Pattern.compile("([A-Za-z]{2,})");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            strList.add(matcher.group());
        }
        return strList;
    }

    /**
     * 去除首尾指定字符
     * @param str   字符串
     * @param element   指定字符
     * @return
     */
    public static String trimLast(String str, String element, String defaultString){
        int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
        return str;
    }
}
