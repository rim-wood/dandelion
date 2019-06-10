package cn.icepear.dandelion.common.security.component.error;

import cn.hutool.http.HttpStatus;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author rim-wood
 * @description 自定义Oauth2授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * @date Created on 2019/6/2.
 */
@Slf4j
@Component
@AllArgsConstructor
public class DandelionOAuth2AccessDeniedHandler extends OAuth2AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException authException) throws IOException, ServletException {
        log.debug("授权失败，禁止访问 {}", httpServletRequest.getRequestURI());
        httpServletResponse.setCharacterEncoding(CommonConstants.UTF8);
        httpServletResponse.setContentType(CommonConstants.CONTENT_TYPE);
        R<String> result = new R<>(new AccessDeniedException("授权失败，禁止访问"));
        httpServletResponse.setStatus(HttpStatus.HTTP_FORBIDDEN);
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
