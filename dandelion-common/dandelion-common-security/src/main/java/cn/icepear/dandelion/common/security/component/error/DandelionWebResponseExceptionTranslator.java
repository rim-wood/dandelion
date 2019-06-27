package cn.icepear.dandelion.common.security.component.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @author rim-wood
 * @description 异常处理,重写 oauth2 默认实现
 * @date Created on 2019/6/3.
 */
@Component
@Slf4j
public class DandelionWebResponseExceptionTranslator  implements WebResponseExceptionTranslator<OAuth2Exception> {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        //尝试从堆栈跟踪中提取Spring Security Exception
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        //处理 OAuth2Exception 异常
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        //处理 AuthenticationException 异常
        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        //处理 InsufficientAuthenticationException 异常
        ase = (InsufficientAuthenticationException) throwableAnalyzer
                .getFirstThrowableOfType(InsufficientAuthenticationException.class, causeChain);
        if (ase instanceof InsufficientAuthenticationException) {
            return handleOAuth2Exception(new UnauthorizedException(ase.getMessage(), ase));
        }

        //处理 AccessDeniedException 异常
        ase = (AccessDeniedException) throwableAnalyzer
                .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        //处理 HttpRequestMethodNotSupportedException 异常
        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
                HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
        }

        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        //构建错误返回头
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        //如果状态码为401或者访问的scope权限不足，则在返回头中也添加错误信息
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        //构建为统一的返回形式R，但是ResponseEntity的泛型必须为OAuth2Exception，所以必须通过自定义一个OAuth2Exception。格式跟R相同
        return new ResponseEntity<OAuth2Exception>(msgToCN(e), headers, HttpStatus.valueOf(status));

    }

    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        this.throwableAnalyzer = throwableAnalyzer;
    }

    private DandelionOAuth2Exception msgToCN(OAuth2Exception e){
        String errorCode = e.getOAuth2ErrorCode();
        log.debug("DandelionWebResponseExceptionTranslator exception info is {}",e.getMessage());
        switch (errorCode){
            case OAuth2Exception.INVALID_CLIENT:
                return new DandelionOAuth2Exception("无效的客户端凭证", e.getOAuth2ErrorCode());
            case OAuth2Exception.UNAUTHORIZED_CLIENT:
                return new DandelionOAuth2Exception("客户端未被授权使用此方法请求授权码", e.getOAuth2ErrorCode());
            case OAuth2Exception.INVALID_GRANT:
                return new DandelionOAuth2Exception(e.getMessage()!=""?e.getMessage():"授权类型无效或非法不允许", e.getOAuth2ErrorCode());
            case OAuth2Exception.INVALID_SCOPE:
                return new DandelionOAuth2Exception("请求的scope无效或超出应用许可范围", e.getOAuth2ErrorCode());
            case OAuth2Exception.INVALID_TOKEN:
                return new DandelionOAuth2Exception("访问令牌无效或已过期!", e.getOAuth2ErrorCode());
            case OAuth2Exception.INVALID_REQUEST:
                return new DandelionOAuth2Exception("请求参数错误或为空", e.getOAuth2ErrorCode());
            case OAuth2Exception.REDIRECT_URI_MISMATCH:
                return new DandelionOAuth2Exception("重定向地址不匹配", e.getOAuth2ErrorCode());
            case OAuth2Exception.UNSUPPORTED_GRANT_TYPE:
                return new DandelionOAuth2Exception("授权许可类型不被支持,参考(password,refresh_token,authorization_code,client_credentials)", e.getOAuth2ErrorCode());
            case OAuth2Exception.UNSUPPORTED_RESPONSE_TYPE:
                return new DandelionOAuth2Exception("授权服务器不支持使用此方法获得授权码", e.getOAuth2ErrorCode());
            case OAuth2Exception.ACCESS_DENIED:
                return new DandelionOAuth2Exception("资源所有者或授权服务器拒绝该请求", e.getOAuth2ErrorCode());
            case "unauthorized":
                return new DandelionOAuth2Exception(e.getMessage()!=""?e.getMessage():"授权失败", e.getOAuth2ErrorCode());
            default:
                return new DandelionOAuth2Exception(e.getMessage(), e.getOAuth2ErrorCode());
        }
    }

    @SuppressWarnings("serial")
    private static class ForbiddenException extends OAuth2Exception {

        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        @Override
        public int getHttpErrorCode() {
            return 403;
        }

    }

    @SuppressWarnings("serial")
    private static class ServerErrorException extends OAuth2Exception {

        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        @Override
        public int getHttpErrorCode() {
            return 500;
        }

    }

    @SuppressWarnings("serial")
    private static class UnauthorizedException extends OAuth2Exception {

        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return 401;
        }

    }

    @SuppressWarnings("serial")
    private static class MethodNotAllowed extends OAuth2Exception {

        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        @Override
        public int getHttpErrorCode() {
            return 405;
        }

    }
}
