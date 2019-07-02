package cn.icepear.dandelion.common.security.component.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author rim-wood
 * @description 自定义 OAuth2 异常
 * @date Created on 2019/6/3.
 */
@JsonSerialize(using = DandelionOAuth2ExceptionSerializer.class)
public class DandelionOAuth2Exception extends OAuth2Exception{
    @Getter
    @Setter
    private String errorCode;

    public DandelionOAuth2Exception(String msg) {
        super(msg);
    }

    public DandelionOAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
