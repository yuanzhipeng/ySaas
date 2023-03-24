package cc.sybx.saas.filter;

import cc.sybx.saas.common.validation.HTMLEscapeUtil;
import cc.sybx.saas.common.validation.ValidationPattern;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    private int paramNameSize = 150;

    private int paramValueSize = 10240;

    private byte[] requestBody = null;

    private String[] excludeFields; //

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest, String[] excludeFields) {
        super(servletRequest);
        this.excludeFields = excludeFields;

        //缓存请求body
        try {
            requestBody = StreamUtils.copyToByteArray(servletRequest.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest, String[] excludeFields, Integer pns, Integer pvs) {
        super(servletRequest);
        this.excludeFields = excludeFields;
        if (null != pns) {
            this.paramNameSize = pns.intValue();
        }

        if (null != pvs) {
            this.paramValueSize = pvs.intValue();
        }

        //缓存请求body
        try {
            requestBody = StreamUtils.copyToByteArray(servletRequest.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public String getContextPath() {
        String path = super.getContextPath();
        // Return empty String for the ROOT context
        if (path == null || "".equals(path.trim())) {
            return "";
        }

        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP context path: " + path, path, "HTTPContextPath", 300, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad ContextPath", e);
        }
        return clean;
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        if (cookies == null) {
            return new Cookie[0];
        }

        List<Cookie> newCookies = new ArrayList<>();
        for (Cookie c : cookies) {
            // build a new clean cookie
            try {
                // get data from original cookie
                String name = ValidationPattern.getValidInput("Cookie name: " + c.getName(), c.getName(), "HTTPCookieName", 150, true);
                String value = ValidationPattern.getValidInput("Cookie value: " + c.getValue(), c.getValue(), "HTTPCookieValue", 1000, true);
                int maxAge = c.getMaxAge();
                String domain = c.getDomain();
                String path = c.getPath();

                Cookie n = new Cookie(name, value);
                n.setMaxAge(maxAge);

                if (domain != null) {
                    n.setDomain(ValidationPattern.getValidInput("Cookie domain: " + domain, domain, "HTTPHeaderValue", 200, false));
                }
                if (path != null) {
                    n.setPath(ValidationPattern.getValidInput("Cookie path: " + path, path, "HTTPHeaderValue", 200, false));
                }
                newCookies.add(n);
            } catch (ValidationException e) {
                logger.warn("Skipping bad cookie: {}={}", c.getName(), c.getValue(), e);
            }
        }
        return newCookies.toArray(new Cookie[newCookies.size()]);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP header value: " + value, value, "HTTPHeaderValue", 500, true);
        } catch (ValidationException e) {
            logger.warn("Skipping bad Header", e);
        }
        return clean;
    }

    @Override
    public Enumeration getHeaderNames() {
        Vector<String> v = new Vector<>();
        Enumeration en = super.getHeaderNames();
        while (en.hasMoreElements()) {
            try {
                String name = (String) en.nextElement();
                String clean = ValidationPattern.getValidInput("HTTP header name: " + name, name, "HTTPHeaderName", 150, true);
                v.add(clean);
            } catch (ValidationException e) {
                logger.warn("Skipping bad HeaderNames.i", e);
            }
        }
        return v.elements();
    }

    @Override
    public Enumeration getHeaders(String name) {
        Vector<String> v = new Vector<>();
        Enumeration en = super.getHeaders(name);
        while (en.hasMoreElements()) {
            try {
                String value = (String) en.nextElement();
                String clean = value;
                if(!ArrayUtils.contains(excludeFields, name)){
                    clean = ValidationPattern.getValidInput("HTTP header value (" + name + "): " + value, value, "HTTPHeaderValue", 500, true);
                }
                v.add(HTMLEscapeUtil.escape(clean));
            } catch (ValidationException e) {
                logger.warn("Skipping bad Headers.i", e);
            }
        }
        return v.elements();
    }

    @Override
    public String getParameter(String name) {
        String orig = super.getParameter(name);
        String clean = orig;
        try {
            if(!ArrayUtils.contains(excludeFields, name)){
                clean = ValidationPattern.getValidInput("HTTP parameter name: " + name, orig, "HTTPParameterValue", paramValueSize, true);
                clean=ValidationPattern.stripXSS(clean);
            }

        } catch (ValidationException e) {
            logger.warn("Skipping bad parameter", e);
        }
        return clean;
    }

    @Override
    public Map getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> cleanMap = new HashMap<>();
        for (Object o : map.entrySet()) {
            try {
                Map.Entry e = (Map.Entry) o;
                String name = (String) e.getKey();
                if (ArrayUtils.contains(excludeFields, name)) {
                    cleanMap.put(name, (String[]) e.getValue());
                    continue;
                }
                String cleanName = ValidationPattern.getValidInput("HTTP parameter name: " + name, name, "HTTPParameterName", paramNameSize, true);

                String[] value = (String[]) e.getValue();
                String[] cleanValues = new String[value.length];
                for (int j = 0; j < value.length; j++) {
                    String cleanValue = ValidationPattern.getValidInput("HTTP parameter value: " + value[j], value[j], "HTTPParameterValue", paramValueSize, true);
                    cleanValues[j] = ValidationPattern.stripXSS(cleanValue);
                }
                cleanMap.put(cleanName, cleanValues);
            } catch (ValidationException e) {
                logger.warn("Skipping bad ParameterMap.i", e);
            }
        }
        return cleanMap;
    }

    @Override
    public Enumeration getParameterNames() {
        Vector<String> v = new Vector<>();
        Enumeration en = super.getParameterNames();
        while (en.hasMoreElements()) {
            try {
                String name = (String) en.nextElement();
                String clean = ValidationPattern.getValidInput("HTTP parameter name: " + name, name, "HTTPParameterName", paramNameSize, true);
                v.add(clean);
            } catch (ValidationException e) {
                logger.warn("Skipping bad ParameterNames.i", e);
            }
        }
        return v.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        if (ArrayUtils.contains(excludeFields, name)) {
            return super.getParameterValues(name);
        }

        String[] values = super.getParameterValues(name);
        List<String> newValues;

        if (values == null) {
            return null;
        }
        newValues = new ArrayList<>();
        for (String value : values) {
            try {
                String cleanValue = ValidationPattern.getValidInput("HTTP parameter value: " + value, value, "HTTPParameterValue", paramValueSize, true);
                //cleanValue=HTMLEscapeUtil.escape(cleanValue);
                newValues.add(ValidationPattern.stripXSS(cleanValue));
            } catch (ValidationException e) {
                logger.warn("Skipping bad ParameterValues.i", e);
            }
        }
        return newValues.toArray(new String[newValues.size()]);
    }

    @Override
    public String getPathInfo() {
        String path = super.getPathInfo();
        if (path == null) {
            return null;
        }
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP path: " + path, path, "HTTPPath", 150, true);
        } catch (ValidationException e) {
            logger.warn("Skipping bad PathInfo", e);
        }
        return clean;
    }

    @Override
    public String getQueryString() {
        String query = super.getQueryString();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP query string: " + query, query, "HTTPQueryString", 2000, true);
        } catch (ValidationException e) {
            logger.warn("Skipping bad QueryString", e);
        }
        return clean;
    }

    @Override
    public String getRequestedSessionId() {
        String id = super.getRequestedSessionId();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("Requested cookie: " + id, id, "HTTPJSESSIONID", 50, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad RequestedSessionId", e);
        }
        return clean;
    }

    @Override
    public String getRequestURI() {
        String uri = super.getRequestURI();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP URI: " + uri, uri, "HTTPURI", 2000, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad RequestURI", e);
        }
        return clean;
    }

    @Override
    public StringBuffer getRequestURL() {
        String url = super.getRequestURL().toString();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP URL: " + url, url, "HTTPURL", 2000, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad RequestURL", e);
        }
        return new StringBuffer(clean);
    }

    @Override
    public String getScheme() {
        String scheme = super.getScheme();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP scheme: " + scheme, scheme, "HTTPScheme", 10, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad Scheme", e);
        }
        return clean;
    }

    @Override
    public String getServerName() {
        String name = super.getServerName();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP server name: " + name, name, "HTTPServerName", 100, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad ServerName", e);
        }
        return clean;
    }

    @Override
    public int getServerPort() {
        int port = super.getServerPort();
        if (port < 0 || port > 0xFFFF) {
            logger.warn(String.format("HTTP server port out of range: {}", port));
            port = 0;
        }
        return port;
    }

    @Override
    public String getServletPath() {
        String path = super.getServletPath();
        String clean = "";
        try {
            clean = ValidationPattern.getValidInput("HTTP servlet path: " + path, path, "HTTPServletPath", 800, false);
        } catch (ValidationException e) {
            logger.warn("Skipping bad ServletPath", e);
        }
        return clean;
    }

    /**
     * 重写 getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() {
        if(requestBody == null){
            requestBody= new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    /**
     * 重写 getReader()
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
