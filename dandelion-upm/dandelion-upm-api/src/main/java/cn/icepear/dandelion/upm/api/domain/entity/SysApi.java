package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author rim-wood
 * @description 平台接口API实体
 * @date Created on 2020/5/23.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysApi extends Model<SysApi> {
    /**
     * 接口ID
     */
    @TableId(value = "api_id", type = IdType.ID_WORKER)
    private Long apiId;

    /**
     * 接口分类
     */
    private String apiName;

    /**
     * 接口分类
     */
    private String apiCategory;

    /**
     * 资源描述
     */
    private String apiDesc;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 接口路径
     */
    private String path;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    /**
     * 安全认证:0-否 1-是 默认:1
     */
    private Integer isAuth;

    /**
     * 是否公开访问: 0-内部的 1-公开的
     */
    private Integer isOpen;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
