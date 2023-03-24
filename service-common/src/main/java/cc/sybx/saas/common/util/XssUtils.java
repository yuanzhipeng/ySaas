package cc.sybx.saas.common.util;

import java.util.Objects;

public class XssUtils {
    /**
     * 转义 < >
     * @param value
     * @return
     */
    public static String replaceXss(String value){
        if(value != null && value.length() > 0){
            return value.replaceAll("<", "&lt;").replace(">", "&gt;");
        }else {
            return value;
        }
    }

    /**
     * 替换LIKE通配符(暂支持mysql,其他数据库需要测试后才可使用)
     * @param value
     * @return
     */
    public static String replaceLikeWildcard(String value){
        return value.replaceAll("\\\\","\\\\\\\\").replaceAll("_","\\\\_").replaceAll("%","\\\\%");
    }

    /**
     * 替换ES的LIKE通配符(暂支持es,其他数据库需要测试后才可使用)
     * @param value
     * @return
     */
    public static String replaceEsLikeWildcard(String value){
        return value.replaceAll("\\\\","\\\\\\\\")
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\[","\\\\[")
                .replaceAll("]","\\]")
                .replaceAll("\\{","\\\\{")
                .replaceAll("}","\\}")
                .replaceAll("\\?","\\\\?")
                .replaceAll("\\*","\\\\*");
    }


    /**
     * LIKE通配符（仅支持mongoDB）
     * @param str
     * @return
     */
    public static String mongodbLike(String str) {
        return String.format("^.*%s.*$", Objects.toString(str).trim());
    }
}
