package cc.sybx.saas.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter extends OncePerRequestFilter {

    private Integer paramNameSize;

    private Integer paramValueSize;

    private String excludeFieldsName;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.addHeader("Access-Control-Allow-Headers", "authorization,content-type,x-requested-with,systemId," +
                    "Platform,reqId");
            response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Max-Age", "1800");
            response.addHeader("Allow", "Allow:GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            response.addHeader("Vary", "Origin");
            response.setHeader("X-Application-Context", "application:8080");
            response.setContentLength(0);
            return;
        }
        if (StringUtils.isNotBlank(excludeFieldsName)) {
            filterChain.doFilter(new XssHttpServletRequestWrapper(request, StringUtils.split(excludeFieldsName, ','),paramNameSize,paramValueSize), response);
        } else {
            filterChain.doFilter(new XssHttpServletRequestWrapper(request, null,paramNameSize,paramValueSize), response);
        }
    }

    @Override
    public void destroy() {
        this.excludeFieldsName = null;
    }


    public void setParamValueSize(Integer paramValueSize) {
        this.paramValueSize = paramValueSize;
    }

    public void setParamNameSize(Integer paramNameSize) {
        this.paramNameSize = paramNameSize;
    }

    public void setExcludeFieldsName(String excludeFieldsName) {
        this.excludeFieldsName = excludeFieldsName;
    }
}
