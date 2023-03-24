package cc.sybx.ms.jwt;

import cc.sybx.ms.util.Utils;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.security.SignatureException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtInterceptor implements WebRequestInterceptor {
    private static final String DEFAULT_JWT_HEADER_KEY = "Authorization";
    private static final String DEFAULT_JWT_DEADER_START = "Bearer ";
    private static final String JSON_WEB_TOKEN = "JSON_WEB_TOKEN:";

    private String jwtSecretKey;
    private String jwtHeaderKey;
    private String jwtHeaderPrefix;
    private JSONObject excludedRestUlrMap;
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * 路径比较器
     * @param request the current web request
     * @throws Exception
     */
    private static AntPathMatcher antPathMather = new AntPathMatcher();

    public JwtInterceptor(String jwtSecretKey, String jwtHeaderKey, String jwtHeaderPrefix, String excludedRestUrls,
                          RedisTemplate<String, ?> redisTemplate){
        this.jwtSecretKey = jwtSecretKey;
        this.jwtHeaderKey = Utils.hasText(jwtHeaderKey) ? jwtHeaderKey : DEFAULT_JWT_HEADER_KEY;
        this.jwtHeaderPrefix = Utils.hasText(jwtHeaderPrefix) ? jwtHeaderPrefix : DEFAULT_JWT_DEADER_START;
        this.excludedRestUlrMap = JSONObject.parseObject(excludedRestUrls);
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
        if(request instanceof ServletWebRequest){
            ServletWebRequest webRequest = (ServletWebRequest) request;
            String servletPath = webRequest.getRequest().getServletPath();
            HttpMethod httpMethod = webRequest.getHttpMethod();

            if(HttpMethod.OPTIONS.equals(webRequest.getHttpMethod())){
                return;
            }

            if(excludedRestUlrMap != null && !excludedRestUlrMap.isEmpty()){
                List<String> list = excludedRestUlrMap.keySet().stream().filter(excludedRestUlr ->
                        antPathMather.match(excludedRestUlr, servletPath)).collect(Collectors.toList());

                if(list != null && !list.isEmpty()){
                    for(String restUrl : list){
                        String reqMethodStr = excludedRestUlrMap.getString(restUrl);
                        if(reqMethodStr != null){
                            if(reqMethodStr.contains(httpMethod.name())){
                                return;
                            }
                        }
                    }
                }
            }

            final String authHeader = request.getHeader(this.jwtHeaderKey);
            String token;

            // 优先从header中获取jwt 如果不存在就去 url的末尾查询是否存在jwt编码
            if(authHeader != null && authHeader.startsWith(this.jwtHeaderPrefix)){
                token = authHeader.substring(jwtHeaderPrefix.length());
            } else {
                token = this.parseExportUrlToken(servletPath);
                if(token == null){
                    log.error("JwtInterceptor preHeader out ['{} Missing jwtToken']", servletPath);
                    throw new SignatureException("Missing jwtToken");
                }
            }

            if(redisTemplate.hasKey(JSON_WEB_TOKEN.concat(token))){
                try{
                    final Claims claims = Jwts.parser().setSigningKey(jwtHeaderKey).parseClaimsJws(token).getBody();
                    webRequest.setAttribute("claims", claims, RequestAttributes.SCOPE_REQUEST);
                    if(log.isDebugEnabled()){
                        log.debug("JwtInterceptor preHeader out ['Authorization success']");
                    }
                } catch (ExpiredJwtException e) {
                    log.info("JwtInterceptor preHeader out ['{} Expired jwtToken'], exMsg:{}", servletPath, e.getMessage());
                    throw new SignatureException("Expired jwtToken");
                } catch (Exception e) {
                    log.info("JwtInterceptor preHeader out ['{} Invalid jwtToken'], exMsg:{}", servletPath, e.getMessage());
                    throw new SignatureException("Invalid jwtToken");
                }
            } else {
                log.info("JwtInterceptor preHandle out ['{} Expired jwtToken'], exMsg:{}", servletPath, "Expired jwtToken.");
                throw new io.jsonwebtoken.SignatureException("Expired jwtToken.");
            }
        }
    }

    private String parseExportUrlToken(String servletPath) {
        try{
            String[] paths = servletPath.split("/");
            String encrypted = paths[paths.length - 1];
            String decrypted = new String(Base64.getDecoder().decode(encrypted.getBytes()));
            JSONObject tokenJsonObject = JSONObject.parseObject(decrypted);
            return tokenJsonObject.getString("token");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
