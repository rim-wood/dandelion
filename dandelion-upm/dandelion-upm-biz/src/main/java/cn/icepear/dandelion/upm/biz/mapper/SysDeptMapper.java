package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

 /**
 * @author rim-wood
 * @description 部门管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
 @Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 *
	 * @return 数据列表
	 */
	List<SysDept> listDepts();
}
