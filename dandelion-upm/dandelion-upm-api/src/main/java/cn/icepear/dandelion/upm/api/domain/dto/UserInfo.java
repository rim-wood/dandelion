package cn.icepear.dandelion.upm.api.domain.dto;

import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rim-wood
 * @description 用户信息
 * @date Created on 2019-04-24.
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private SysUser sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Integer[] roles;
}
