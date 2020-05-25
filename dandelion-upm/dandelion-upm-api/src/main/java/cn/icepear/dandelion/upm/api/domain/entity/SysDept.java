package cn.icepear.dandelion.upm.api.domain.entity;

import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


/**
 * @author rim-wood
 * @description 部门实体
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;
	/**
	 * 部门ID
	 */
	@TableId(value = "dept_id", type = IdType.ID_WORKER)
	private Long deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	private String deptName;

	/**
	 * 部门父级部门Id
	 */
	private Long parentId;

	/**
	 * 部门类型
	 */
	private String deptType;

	/**
	 * 排序
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	private String updator;

	/**
	 * 是否删除  1：已删除  0：正常
	 */
	@TableLogic
	private int delFlag;

	public static DeptTree sysDept2DeptTree(SysDept sysDept){
		DeptTree deptTree = new DeptTree();
		deptTree.setDeptName(sysDept.getDeptName());
		deptTree.setDeptType(sysDept.getDeptType());
		deptTree.setDelFlag(sysDept.getDelFlag());
		deptTree.setSortOrder(sysDept.getSortOrder());
		deptTree.setId(sysDept.getDeptId());
		deptTree.setParentId(sysDept.getParentId());
		return deptTree;
	}
}
