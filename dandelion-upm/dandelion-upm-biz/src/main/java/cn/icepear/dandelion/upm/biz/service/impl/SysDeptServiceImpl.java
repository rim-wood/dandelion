package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.biz.mapper.SysDeptMapper;
import cn.icepear.dandelion.upm.biz.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rim-wood
 * @description 部门管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	/**
	 * 添加信息部门
	 *
	 * @param dept 部门
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveDept(SysDept dept) {
		return this.save(dept);
	}


	/**
	 * 删除部门
	 *
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	@Override
	public Boolean removeDeptById(Integer id) {
		//级联删除部门 todo 查询子部门
		List<Integer> idList = new ArrayList<>();

		if (CollUtil.isNotEmpty(idList)) {
			return this.removeByIds(idList);
		}
		return false;
	}

	/**
	 * 更新部门
	 *
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	@Override
	public Boolean updateDeptById(SysDept sysDept) {
		//更新部门
		return this.updateById(sysDept);
	}

	/**
	 * 查询全部部门树
	 *
	 * @return 树
	 */
	@Override
	public List<DeptTree> listDeptTrees() {
		//todo
		return null;
	}

	/**
	 * 查询用户部门树
	 *
	 * @return
	 */
	@Override
	public List<DeptTree> listCurrentUserDeptTrees() {
		//todo
		return null;
	}

}
