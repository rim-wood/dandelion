package cn.icepear.dandelion.upm.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rim-wood
 * @description 角色对外实体
 * @date Created on 2019/10/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVO implements Serializable {
    private Long roleId;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private String deptId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updator;

    /**
     * 删除识别(0-正常，1-删除)
     */
    private Integer delFlag;

    /**
     * 是否为系统默认 （1-是 0-不是）
     */
    private Integer sysDefault;

    /**
     * 拥有菜单权限列表
     */
    private List<Long> menuIdList;

    /**
     * 拥有功能权限列表
     */
    private List<Long> functionIdList;
}

