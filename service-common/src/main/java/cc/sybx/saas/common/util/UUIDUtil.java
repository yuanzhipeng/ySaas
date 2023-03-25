package cc.sybx.saas.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UUIDUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDUtil.class);
    private static UIDFactory uuid = null;

    static {
        try {
            uuid = UIDFactory.getInstance("UUID");
        } catch (Exception unsex) {
            LOGGER.info("Init UIDFactory Failed", unsex);
        }
    }

    /**
     * Constructor for the UUIDGener object
     */
    private UUIDUtil() {
    }

    /**
     * 获取uuid字符
     *
     * @author lihe 2013-7-4 下午5:31:09
     * @return
     * @see
     * @since
     */
    public static String getUUID() {
        return uuid.getNextUID();
    }
}
