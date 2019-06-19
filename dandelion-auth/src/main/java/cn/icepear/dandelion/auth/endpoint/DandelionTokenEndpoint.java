package cn.icepear.dandelion.auth.endpoint;

import cn.icepear.dandelion.common.core.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author rim-wood
 * @description 自定义tokenEndpoint实现，把token返回结果封装成R形式
 * @date Created on 2019/6/19.
 */
@RestController
public class DandelionTokenEndpoint {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/oauth/token", method=RequestMethod.GET)
    public R<OAuth2AccessToken> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> res = tokenEndpoint.getAccessToken(principal,parameters);
        return new R<OAuth2AccessToken>("success",res.getBody());
    }

    @RequestMapping(value = "/oauth/token", method=RequestMethod.POST)
    public R<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> res = tokenEndpoint.postAccessToken(principal,parameters);
        return new R<OAuth2AccessToken>("success",res.getBody());
    }
}
