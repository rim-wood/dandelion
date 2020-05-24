package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rim-wood
 * @description 角色对应的功能实体
 * @date Created on 2020/5/23.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleFunction extends Model<SysRoleFunction> {
    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long roleId;

    private Long functionId;

    public SysRoleFunction() {
    }

    public SysRoleFunction(Long roleId, Long functionId) {
        this.roleId = roleId;
        this.functionId = functionId;
    }

}
