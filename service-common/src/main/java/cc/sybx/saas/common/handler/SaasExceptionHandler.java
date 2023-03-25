package cc.sybx.saas.common.handler;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.util.CommonErrorCode;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class SaasExceptionHandler {
    @Resource
    private MessageSource messageSource;
    @Value("#{'${cors.allowedOrigins:*}'.split(',')}")
    private List<String> allowedOrigins;
    private static final String LOGGER_FORMAT = "操作执行异常：异常编码{},异常信息：{},堆栈信息：{}";


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SaasRuntimeException.class)
    public BaseResponse SaasRuntimeException(SaasRuntimeException saasEx) {

        Throwable cause = saasEx.getCause();
        String msg = "";
        if (cause != null) {
            msg = cause.getMessage();
        }

        String errorCode = saasEx.getErrorCode();
        if (StringUtils.isNotEmpty(errorCode)) {
            if (CommonErrorCode.SPECIFIED.equals(errorCode) && saasEx.getParams() != null && saasEx.getParams().length > 0) {
                msg = Objects.toString(saasEx.getParams()[0]);
            } else {
                msg = this.getMessage(errorCode, saasEx.getParams());
            }

            // 如果异常码在本系统中有对应异常信息，以异常码对应的提示信息为准
            if (!errorCode.equals(msg)) {
                saasEx.setResult(msg);
            }

            if (StringUtils.isNotBlank(saasEx.getResult()) && !"fail".equals(saasEx.getResult())) {
                log.error(LOGGER_FORMAT, saasEx.getErrorCode(), saasEx.getResult(), saasEx);
                return BaseResponse.info(errorCode, saasEx.getResult(), saasEx.getData());
            }
            //2、如果有异常码，以异常码对应的提示信息为准
//            msg = this.getMessage(errorCode, ex.getParams());
        } else if (StringUtils.isEmpty(msg)) {
            //3、异常码为空 & msg为空，提示系统异常
            msg = this.getMessage(CommonErrorCode.FAILED, saasEx.getParams());
        }

        if (StringUtils.isEmpty(errorCode)) {
            errorCode = CommonErrorCode.FAILED;
        }
        log.error(LOGGER_FORMAT, errorCode, msg, saasEx);

        return new BaseResponse(errorCode, saasEx.getParams());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse validationExceptionHandle(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return new BaseResponse(CommonErrorCode.PARAMETER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse constraintViolationExceptionHandle(ConstraintViolationException ex) {
        final StringBuilder sb = new StringBuilder();
        ex.getConstraintViolations().forEach(
                i -> sb
                        .append(i.getRootBeanClass().getName())
                        .append('.')
                        .append(i.getPropertyPath())
                        .append(i.getMessage()).append("\r\n")
        );
        log.error("{}", sb);
        return new BaseResponse(CommonErrorCode.PARAMETER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse illegalStateExceptionHandle(IllegalStateException ex) {
        log.error("{}", ex.getMessage());
        return new BaseResponse(CommonErrorCode.PARAMETER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse defaultExceptionHandler(Throwable ex) throws Exception {
        log.error(LOGGER_FORMAT, "", ex.getMessage(), ex);
        if (ex.getCause() instanceof GenericJDBCException) {
            if (1366 == ((GenericJDBCException) ex.getCause()).getSQLException().getErrorCode()) {
                return new BaseResponse(CommonErrorCode.ILLEGAL_CHARACTER);
            }
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return new BaseResponse(CommonErrorCode.PARAMETER_ERROR);
        }
        return BaseResponse.FAILED();
    }

    /**
     * jwt异常处理
     *
     * @param sx
     * @return
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse jwtExceptionHandler(SignatureException sx, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msg = sx.getMessage();
        response.setStatus(200);
        response.addHeader("Access-Control-Allow-Origin", Joiner.on(",").join(allowedOrigins));
        response.addHeader("Access-Control-Allow-Headers", "authorization,content-type,x-requested-with");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Max-Age", "1800");
        response.addHeader("Allow", "Allow:GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.addHeader("Vary", "Origin");
        if ("Invalid jwtToken.".equals(msg) || "Expired jwtToken.".equals(msg) || "Missing jwtToken.".equals(msg)) {
            return new BaseResponse("K-000015");
        } else {
            return new BaseResponse(CommonErrorCode.FAILED);
        }
    }

    /**
     * 获取错误码描述
     *
     * @param code   错误码
     * @param params 输出替换参数
     * @return 错误信息
     */
    protected String getMessage(String code, Object[] params) {
        try {
            /**
             * 默认 获取Locale.CHINA 配置的i18n 如果需要自动转语言 传递相应参数即可
             * 后去顺序  传递 Locale.CHINA 的情况下
             * 1. 优先获取 messages_en_CN
             * 2. 1不存在时获取  messages
             */
            return messageSource.getMessage(code, params, Locale.CHINA);
        } catch (NoSuchMessageException e) {
            return code;
        }
    }
}