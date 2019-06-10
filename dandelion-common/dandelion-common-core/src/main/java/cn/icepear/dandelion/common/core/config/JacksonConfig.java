package cn.icepear.dandelion.common.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * @author romwood
 * @date 2019-04-01
 */
@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper getObjectMapper(){

        ObjectMapper objectMapper = new ObjectMapper();

        /** 通过该方法对mapper对象进行设置，所有序列化的对象都将按该规则进行序列化
         * Include.Include.ALWAYS 默认
         * Include.NON_DEFAULT 属性为默认值不序列化
         * Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
         * Include.NON_NULL 属性为NULL 不序列化
         */
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 反序列化时，匹配实体类时忽略掉匹配不到的字符，让反序列化时不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段保留，将null值转为""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider)
                    throws IOException
            {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
