package cn.icepear.dandelion.common.security.component.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rim-wood
 * @description 自定义 AuthenticationException 异常处理 可以根据 AuthenticationException 不同细化异常处理
 * @date Created on 2019/6/4.
 */
@Component
public class DandelionAuthExceptionEntryPoint extends AbstractOAuth2SecurityExceptionHandler implements AuthenticationEntryPoint {
    private String typeName = OAuth2AccessToken.BEARER_TYPE;

    private String realmName = "oauth";

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        super.setExceptionTranslator(new DandelionWebResponseExceptionTranslator());
        doHandle(request, response, authException);
    }

    @Override
    protected ResponseEntity<?> enhanceResponse(ResponseEntity<?> response, Exception exception) {
        HttpHeaders headers = response.getHeaders();
        String existing = null;
        if (headers.containsKey("WWW-Authenticate")) {
            existing = extractTypePrefix(headers.getFirst("WWW-Authenticate"));
        }
        StringBuilder builder = new StringBuilder();
        builder.append(typeName+" ");
        builder.append("realm=\"" + realmName + "\"");
        if (existing!=null) {
            builder.append(", "+existing);
        }
        HttpHeaders update = new HttpHeaders();
        update.putAll(response.getHeaders());
        update.set("WWW-Authenticate", builder.toString());
        return new ResponseEntity<Object>(response.getBody(), update, response.getStatusCode());
    }

    private String extractTypePrefix(String header) {
        String existing = header;
        String[] tokens = existing.split(" +");
        if (tokens.length > 1 && !tokens[0].endsWith(",")) {
            existing = StringUtils.arrayToDelimitedString(tokens, " ").substring(existing.indexOf(" ") + 1);
        }
        return existing;
    }
}
