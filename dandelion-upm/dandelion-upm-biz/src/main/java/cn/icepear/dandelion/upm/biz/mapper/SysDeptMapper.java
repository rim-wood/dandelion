package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

 /**
 * @author rim-wood
 * @description 部门管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
 @Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 所有的机构部门列表
	 *
	 * @return 数据列表
	 */
	List<DeptTree> listDeptsTrees();

	 /**
	  * 所有的机构部门列表
	  *
	  * @return 数据列表
	  */
	 List<SysDept> listDeptsTreesList();

	 /**
	  * 查询父级机构列表
	  *
	  * @return 部门父级数据列表
	  */
 	List<SysDept> parentListDepts(@Param("deptId") Integer deptId, @Param("delFlag") Integer delFlag);


	 /**
	  * 查询子级机构列表
	  *
	  * @param
	  * @return 部门父级数据列表
	  */
	 List<SysDept> sonListDepts(Integer deptId);
}
