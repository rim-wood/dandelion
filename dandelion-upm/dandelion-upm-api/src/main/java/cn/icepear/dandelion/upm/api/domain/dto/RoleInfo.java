package cn.icepear.dandelion.upm.api.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author rim-wood
 * @description 角色信息
 * @date Created on 2019/6/19.
 */
@Data
public class RoleInfo implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleCode;
}
