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
 * @description 菜单对应的功能实体
 * @date Created on 2020/5/23.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFunction extends Model<SysFunction> {
    /**
     * 功能ID
     */
    @TableId(value = "function_id", type = IdType.ID_WORKER)
    private Long functionId;

    /**
     * 所属菜单ID
     */
    private Long menuId;

    /**
     * 对应的接口ID
     */
    private Long apiId;

    /**
     * 功能权限代码
     */
    private String functionCode;

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 功能描述
     */
    private String functionDesc;

    /**
     * 排序优先级
     */
    private Integer priority;
    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;
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
