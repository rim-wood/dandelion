package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rim-wood
 * @description 用户角色实体类
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;

}
