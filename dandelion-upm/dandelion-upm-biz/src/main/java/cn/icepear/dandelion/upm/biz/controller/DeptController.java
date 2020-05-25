package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.biz.check.AbstractCheck;
import cn.icepear.dandelion.upm.biz.check.UserDeptCheck;
import cn.icepear.dandelion.upm.biz.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rim-wood
 * @description 机构部门管理
 * @date Created on 2019-07-29.
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 获取所有的部门树结构 不需要权限控制
     * @return
     */
    @GetMapping("/allTree")
    public R<List<DeptTree>> getAllDeptTree(){
        List<DeptTree> deptTrees = sysDeptService.getDeptTree();
        return new R<>(CommonConstants.SUCCESS,"查询成功",deptTrees);
    }

    /**
     * 获取所有的部门 数组结构 不需要权限控制
     * @return
     */
    @GetMapping("/allList")
    public R<List<SysDept>> getAllDeptList(){
        List<SysDept> deptTrees = sysDeptService.getSysDeptList();
        return new R<>(CommonConstants.SUCCESS,"查询成功",deptTrees);
    }

    /**
     * 根据本机构ID获取子部门 树结构
     * @return
     */
    @GetMapping("/tree")
    @PreAuthorize("@mse.hasPermission('sys:dept:tree')")
    public R<List<DeptTree>> getDeptTreeById(){
        DandelionUser dandelionUser = SecurityUtils.getUser();
        List<DeptTree> deptTree = sysDeptService.getCurrentUserDeptTrees(dandelionUser.getGrandparentDeptId());
        if(deptTree.size() != 0){
            return new R<>(CommonConstants.SUCCESS,"查询成功",deptTree);
        }else{
            return R.error(CommonConstants.FAIL,"未查询到子部门树", null );
        }
    }

    /**
     * 根据本机构ID获取子部门 数组结构
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@mse.hasPermission('sys:dept:list')")
    public R<List<SysDept>> getSysDeptById(){
        DandelionUser dandelionUser = SecurityUtils.getUser();
        List<SysDept> deptTree = sysDeptService.getCurrentUserSysDeptList(dandelionUser.getGrandparentDeptId());
        if(deptTree.size() != 0){
            return new R<>(CommonConstants.SUCCESS,"查询成功",deptTree);
        }else{
            return R.error(CommonConstants.FAIL,"未查询到子部门树", null );
        }
    }

    /**
     * 根据deptId获取机构信息
     * @return
     */
    @GetMapping("/info/{deptId}")
    @PreAuthorize("@mse.hasPermission('sys:dept:info')")
    public R<SysDept> getSysDeptById(@PathVariable Long deptId,@JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if(!isAdmin){
            UserDeptCheck userDeptCheck = new UserDeptCheck(deptId, sysDeptService);
            AbstractCheck.CheckResult check = userDeptCheck.check();

            if(!check.isFlag()){
                return new R<>(CommonConstants.FAIL,check.getMsg(),null);
            }
        }
        SysDept sysDept = sysDeptService.getSysDeptById(deptId);
        if(sysDept != null){
            return new R<>(CommonConstants.SUCCESS,"查询成功",sysDept);
        }else {
            return R.error(CommonConstants.FAIL,"未查询到数据", null );
        }

    }

    /**
     * 添加机构部门
     */
    @PostMapping("/add")
    @PreAuthorize("@mse.hasPermission('sys:dept:add')")
    public R<SysDept> addSysDept(@RequestBody SysDept sysDept,@JudgeUserRole(role = "ADMIN") boolean isAdmin){

        if(!isAdmin){
            UserDeptCheck userDeptCheck = new UserDeptCheck(sysDept.getParentId(), sysDeptService);
            AbstractCheck.CheckResult check = userDeptCheck.check();

            if(!check.isFlag()){
                return new R<>(CommonConstants.FAIL,check.getMsg(),null);
            }
        }

        DandelionUser dandelionUser = SecurityUtils.getUser();
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setDelFlag(0);
        sysDept.setCreator(dandelionUser.getUsername());
        //dept_name parent_id 唯一约束
        SysDept exits = sysDeptService.getOne(new QueryWrapper<SysDept>().eq("dept_name", sysDept.getDeptName()).eq("parent_id", sysDept.getParentId()));
        boolean  flag = false;
        if(exits != null && exits.getDelFlag() == 1){ //原来的被删掉了
            sysDept.setDeptId(exits.getDeptId());
            flag = sysDeptService.updateDeptById(sysDept);
        }else if(exits != null && exits.getDelFlag() == 0){
            return new R(CommonConstants.FAIL,"添加失败",sysDept.getDeptName()+"已经存在");
        }else {
            flag = sysDeptService.saveDept(sysDept);
        }
        if(flag){
            return new R<>(CommonConstants.SUCCESS,"添加成功",sysDept);
        }else {
            return new R<>(CommonConstants.FAIL,"添加失败",sysDept);
        }
    }

    /**
     * 修改机构部门
     */
    @PostMapping("/update")
    @PreAuthorize("@mse.hasPermission('sys:dept:update')")
    public R<SysDept> updateSysDept(@RequestBody SysDept sysDept ,@JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if(sysDept.getParentId() == null){
            return new R<>(CommonConstants.FAIL,"parentId不能为空",null);
        }
        if(!isAdmin){
            UserDeptCheck userDeptCheck = new UserDeptCheck(sysDept.getParentId(), sysDeptService);
            AbstractCheck.CheckResult check = userDeptCheck.check();

            if(!check.isFlag()){
                return new R<>(CommonConstants.FAIL,check.getMsg(),null);
            }
        }

        sysDept.setUpdateTime(LocalDateTime.now());
        DandelionUser dandelionUser = SecurityUtils.getUser();
        sysDept.setUpdator(dandelionUser.getUsername());
        boolean flag = false;
        if(sysDept.getDelFlag() == 1){//当要删除部门时
            flag = sysDeptService.removeDeptById(sysDept.getDeptId());
        }else {
            flag = sysDeptService.updateDeptById(sysDept);
        }
        if(flag){
            return new R<>(CommonConstants.SUCCESS,"修改成功",sysDept);
        }else {
            return new R<>(CommonConstants.FAIL,"修改失败",sysDept);
        }
    }


}
