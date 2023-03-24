package cc.sybx.saas.common.util;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {

    /**
     * 是否是手机号码
     *
     * @param phone 手机号码
     * @return
     */
    public static boolean isPhone(String phone) {
        return StringUtils.defaultString(phone, StringUtils.EMPTY).matches("^134[0-8]\\d{7}$|^13[^4]\\d{8}$|^14[5-9]\\d{8}$|^15[^4]\\d{8}$|^16[6]\\d{8}$|^17[0-8]\\d{8}$|^18[\\d]{9}$|^19[1,5,8,9]\\d{8}$");
    }
}
