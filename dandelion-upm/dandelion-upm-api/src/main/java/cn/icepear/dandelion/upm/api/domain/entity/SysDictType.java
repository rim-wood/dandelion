package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rim-wood
 * @description 字典表类型主表
 * @date Created on 2019/10/21.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends Model<SysDict> {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典类型标识代码
     */
    @NotBlank(message = "字典类型标识代码不能为空")
    private String typeCode;
    /**
     * 字典类型描述
     */
    private String description;
    /**
     * 字典类型（0-系统类，1-业务类）
     */
    @NotNull(message = "字典类型不能为空")
    private Integer type;
    /**
     * 描述
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<SysDict> sysDicts;
}

