package cn.icepear.dandelion.common.security.component.resolver;

import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.NoSuchElementException;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author rimwood
 * @date 2017-03-23 22:02
 */
@Component
public class JudgeUserRoleHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(boolean.class) && parameter.hasParameterAnnotation(JudgeUserRole.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        JudgeUserRole judgeUserRole = parameter.getParameterAnnotation(JudgeUserRole.class);
        String role = judgeUserRole.role();
        //获取用户
        DandelionUser user = SecurityUtils.getUser();
        try {
            user.getRoles().stream().filter(roleInfo -> role.equals(roleInfo.getRoleCode())).findFirst().get();
            return true;
        }catch (NoSuchElementException e) {
            return false;
        }
    }
}
