package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.api.utils.List2TreeNodeUtil;
import cn.icepear.dandelion.upm.biz.mapper.SysDeptMapper;
import cn.icepear.dandelion.upm.biz.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	@CacheEvict(value =  {"dept_trees","dept_list"}, allEntries = true)
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
	@CacheEvict(value = {"dept_trees","dept_list"}, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	//todo 删除部门对应的账号也要失效
	public Boolean removeDeptById(Long id) {
		List<SysDept> syslist = baseMapper.sonListDepts(id);

		//级联删除部门
		List<Long> idList = new ArrayList<>();
		for (SysDept sysDept : syslist) {
			idList.add(sysDept.getDeptId());
		}

		if (CollUtil.isNotEmpty(idList)) {
			List<SysDept> removeList = idList.stream().map( i -> {
				SysDept s = new SysDept();
				s.setDelFlag(1);
				s.setDeptId(i);
				return s;
			}).collect(Collectors.toList());
			return this.updateBatchById(removeList);
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
	@CacheEvict(value =  {"dept_trees","dept_list"}, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateDeptById(SysDept sysDept) {
		//更新部门
		return this.updateById(sysDept);
	}

	@Override
	public List<SysDept> getParentDeptList(Long deptId) {
		return baseMapper.parentListDepts(deptId, 0);
	}

	/**
	 * 获取机构信息
	 * @param deptId
	 * @return
	 */
	@Override
	public SysDept getSysDeptById(Long deptId) {
		return baseMapper.selectById(deptId);
	}

	/**
	 * 查询全部部门树
	 *
	 * @return 树
	 */
	@Override
	@Cacheable(value = "dept_trees", key = "0+'_tree'")
	public List<DeptTree> getDeptTree() {

		List<DeptTree> list = baseMapper.listDeptsTrees();
		return list;
	}

	/**
	 * 查询全部部门树
	 *
	 * @return 树
	 */
	@Override
	@Cacheable(value = "dept_list", key = "0+'_treelist'")
	public List<SysDept> getSysDeptList() {

		List<SysDept> list = baseMapper.listDeptsTreesList();

		return list;
	}

	/**
	 * 查询用户部门 树
	 *
	 * @return
	 */
	@Override
	@Cacheable(value = "dept_trees", key = "#deptId+'_son_tree'")
	public List<DeptTree> getCurrentUserDeptTrees(Long deptId) {
		List<SysDept> list = baseMapper.sonListDepts(deptId);
		if(list.size() != 0){
			List<DeptTree> treeNode = list.stream().map(dept -> SysDept.sysDept2DeptTree(dept)).collect(Collectors.toList());
			return  (List<DeptTree>) List2TreeNodeUtil.rebuildList2Tree(treeNode);
		}else {
			List<DeptTree> nullTree = new ArrayList<>();
			return nullTree;
		}
	}

	/**
	 * 查询用户部门 数组
	 *
	 * @return
	 */
	@Override
	@Cacheable(value = "dept_list", key = "#deptId+'_son_treelist'")
	public List<SysDept> getCurrentUserSysDeptList(Long deptId) {
		List<SysDept> list = baseMapper.sonListDepts(deptId);
		if(list.size() != 0){
			return  list;
		}else {
			List<SysDept> nullTree = new ArrayList<>();
			return nullTree;
		}
	}

}
