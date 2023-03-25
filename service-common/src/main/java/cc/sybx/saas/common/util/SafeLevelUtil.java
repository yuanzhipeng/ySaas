package cc.sybx.saas.common.util;

import java.util.regex.Pattern;

/**
 * 判断密码的安全度
 * Created by CHENLI on 2017/8/17.
 */
public final class SafeLevelUtil {

    //密码安全等级：20危险 40低、60中、80高
    private static final int DANGER = 20;
    private static final int LOW = 40;
    private static final int MEDIUM = 60;
    private static final int HIGH = 80;


    /**
     * 判断密码的安全度
     * 1. 密码只由数字组成，且少于7位——危险，能量条填充20%
     * 2. 密码只由数字、小写字母、大写字母当中的一种组成——弱，能量条填充40%
     * 3. 密码由数字、小写字母、大写字母当中的两种组成——中度安全，能量条填充60%
     * 4. 密码由数字、小写字母、大写字母当中的三种以上组成——比较安全，能量条填充80%
     *
     * @param passWord
     * @return
     */
    public static int getSafeLevel(String passWord) {
        String danger = "^(\\d{1,7}+)?$";
        String low = "^(\\d{7,16}+|[a-z]{7,16}+|[A-Z]{7,16}+)$";
        String medium = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{7,16}+$|(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]{7,16}+$";
        String high = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{7,16}+$";
        Pattern p = Pattern.compile(high);

        if (p.matcher(passWord).matches()) {
            return HIGH;
        } else {
            p = Pattern.compile(medium);
            if (p.matcher(passWord).matches()) {
                return MEDIUM;
            } else {
                p = Pattern.compile(low);
                if (p.matcher(passWord).matches()) {
                    return LOW;
                } else {
                    p = Pattern.compile(danger);
                    if (p.matcher(passWord).matches()) {
                        return DANGER;
                    }
                    return DANGER;
                }
            }
        }
    }
}
