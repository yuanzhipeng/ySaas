package cc.sybx.saas.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@Slf4j
public final class HttpUtil {

    private static final String UNKNOWN = "unknown";

    public static final String LOCAL_ADDRESS = "127.0.0.1";

    /**
     * 获取当前HTTP请求对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前HTTP请求对象
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取当前Scheme
     * 如127.0.0.1
     *
     * @return
     */
    public static String getServerName() {
        return getRequest().getServerName();
    }

    /**
     * 获取当前Scheme（含端口号）
     * 如http://127.0.0.1:80
     *
     * @return
     */
    public static String getBasePath() {
        HttpServletRequest request = getRequest();
        return MessageFormat.format("{0}://{1}:{2}", request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()));
    }

    /**
     * 获取当前项目名
     *
     * @return
     */
    public static String getProjectName() {
        HttpServletRequest request = getRequest();
        return request.getContextPath();
    }

    /**
     * 获取当前项目路径
     *
     * @return
     */
    public static String getProjectRealPath() {
        HttpServletRequest request = getRequest();
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取客户端ip
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = getRequest();

        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(',') != -1) {
            ip = ip.substring(ip.lastIndexOf(',') + 1, ip.length()).trim();
        }
        return ip;
    }
}
