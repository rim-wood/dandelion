package cn.icepear.dandelion.common.security.component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author rim-wood
 * @description 自定义的权限校验器
 * @date Created on 2019/6/28.
 */
@Slf4j
@Component("mse")
public class MySecurityExpression {
    /**
     * 判断接口是否有xx:xx权限(具有aa:bb权限的，默认具有aa:bb:cc,aa:bb:dd或者其他，或者采用aa:bb:all这种形式也具有aa:bb:cc等权限)
     * @param permission 权限
     * @return {boolean}
     */
    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String allStr = ":all";
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> {
                    if(x.equals(permission)) return true;
                    else {boolean result = false;
                        int allIndex = x.indexOf(allStr);
                        if(allIndex!=-1){
                            x = x.substring(0, allIndex);
                        }
                        result = PatternMatchUtils.simpleMatch(x+":*", permission);
                        return result;
                    }
                });
    }
}
