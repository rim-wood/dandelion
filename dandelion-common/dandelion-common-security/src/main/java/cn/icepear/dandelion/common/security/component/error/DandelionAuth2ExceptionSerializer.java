package cn.icepear.dandelion.common.security.component.error;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 * @author rim-wood
 * @description 返回异常时，需要统一返回跟R一致的对象，所以需要序列化转化 OAuth2Exception
 * @date Created on 2019/6/3.
 */
public class DandelionAuth2ExceptionSerializer extends StdSerializer<DandelionOAuth2Exception> {
    public DandelionAuth2ExceptionSerializer() {
        super(DandelionOAuth2Exception.class);
    }

    @Override
    @SneakyThrows
    public void serialize(DandelionOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
        gen.writeStartObject();
        gen.writeObjectField("code", CommonConstants.FAIL);
        gen.writeStringField("msg", value.getMessage());
        gen.writeStringField("data", value.getErrorCode());
        gen.writeEndObject();
    }
}
