package cn.icepear.dandelion.authorization.endpoint;

import cn.hutool.core.util.StrUtil;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.core.utils.RedisUtils;
import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rim-wood
 * @description 自定义token端点
 * @date Created on 2019/10/31.
 */
@Controller
public class DandelionTokenEndpoint {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/login")
    public String login(){
        return "forward:/login.html";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code){

        return "forward:/index.html";
    }

    @PostMapping("/oauth/logout")
    @ResponseBody
    public R<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader){
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if(accessToken!=null) {
            Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();
            redisUtils.delete(SecurityConstants.USER_DETAILS_KEY + ":" + additionalInfo.get("userName"));
            redisUtils.delete("menu_details:" + additionalInfo.get("userName") + "_menuTreeList");
            tokenStore.removeAccessToken(accessToken);
            return R.<Boolean>builder()
                    .code(CommonConstants.SUCCESS)
                    .msg("退出成功")
                    .data(Boolean.TRUE)
                    .build();
        }
        return R.<Boolean>builder()
                .code(CommonConstants.FAIL)
                .msg("用户未登录")
                .data(Boolean.FALSE)
                .build();
    }
}
