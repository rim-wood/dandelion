package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.lock.redis.RedisLock;
import cn.icepear.dandelion.common.log.annotation.SysLog;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.dto.UserDTO;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.domain.vo.UserVO;
import cn.icepear.dandelion.upm.biz.service.SysDeptService;
import cn.icepear.dandelion.upm.biz.service.SysRoleService;
import cn.icepear.dandelion.upm.biz.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rim-wood
 * @description 用户相关API
 * @date Created on 2019-05-29.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/register")
    @SysLog("用户注册")
    @RedisLock(name = "register",keys = {"#clientIp"})
    public R register(@RequestBody UserDTO userDTO, @RequestHeader("clientIp") String clientIp){
        SysUser exist = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name",userDTO.getUserName()));
        if(exist==null){
            if (userDTO.getDeptId() != null){
                List<SysDept> parentDeptList =  sysDeptService.getParentDeptList(userDTO.getDeptId());
                if(parentDeptList.isEmpty()){
                    return R.error(1001,"该部门不存在",null);
                }
                userDTO.setGrandparentDeptId(parentDeptList.get(0).getDeptId());
            }else{
                userDTO.setDeptId(userDTO.getGrandparentDeptId());
            }
            SysUser result = sysUserService.registUser(userDTO);
            if(result!=null) {
                return R.<SysUser>builder().code(CommonConstants.SUCCESS).msg("注册成功，等待审核").data(result).build();
            }
            return R.error(1003,"注册失败",null);
        }
        return R.error(1002,"该用户已存在",null);
    }

    @PostMapping("/add")
    @PreAuthorize("@mse.hasPermission('sys:user:add')")
    @SysLog("新增用户")
    public R addUser(@RequestBody UserDTO userDTO){
        SysUser exist = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name",userDTO.getUserName()));
        if(exist==null) {
            boolean result = sysUserService.saveUser(userDTO);
            if (result) {
                return R.<UserDTO>builder().code(CommonConstants.SUCCESS).msg("添加成功").data(userDTO).build();
            }
            return R.error(1002,"新增失败",null);
        }
        return R.error(1001,"该用户已存在",null);
    }

    @PutMapping("/update")
    @PreAuthorize("@mse.hasPermission('sys:user:update')")
    @SysLog("更新用户信息")
    public R updateUser(@RequestBody UserDTO userDTO, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if(!isAdmin){
            DandelionUser user = SecurityUtils.getUser();
            List<SysRole> sysRoles=  sysRoleService.getAllList(user.getGrandparentDeptId());
            List<Integer> role= new ArrayList<>();
            sysRoles.forEach(sysRole -> {
                role.add(sysRole.getRoleId());
            });
            if(role.containsAll(userDTO.getRole())){
                return R.error(1001,"更新失败", null);
            }
        }

        SysUser exist = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name",userDTO.getUserName()).eq("id",userDTO.getId()));
        if(exist!=null){
            boolean result = sysUserService.updateUser(userDTO);
            if (result) {
                return R.<UserDTO>builder().code(CommonConstants.SUCCESS).msg("更新成功").data(userDTO).build();
            }
            return R.error(1001,"更新失败",null);
        }
        return R.error(1000,"用户不存在",null);
    }

    @GetMapping("/info")
    public R getUserInfo(){
        DandelionUser user = SecurityUtils.getUser();
        UserVO userVO = sysUserService.getUserVoByUserName(user.getUsername());
        return new R<UserVO>(userVO);
    }

    @PutMapping("/info")
    @SysLog("更新用户自身信息")
    public R updateUserInfo(@RequestBody UserDTO userDTO){
        DandelionUser userSelf = SecurityUtils.getUser();
        userDTO.setUserName(userSelf.getUsername());
        boolean result = sysUserService.updateUserInfo(userDTO);
        if (result) {
            return R.<UserDTO>builder().code(CommonConstants.SUCCESS).msg("更新成功").data(userDTO).build();
        }
        return R.error(1001,"更新失败",null);
    }

    @GetMapping("/page")
    @PreAuthorize("@mse.hasPermission('sys:user:page')")
    public R getUserList(Page page, String userName,Integer deptId,@JudgeUserRole boolean isAdmin){
        //判断是否是超级管理员，查看所有数据
        if(!isAdmin){
            DandelionUser curUser = SecurityUtils.getUser();
            if(deptId!=null){
                // 判断查找的机构编号是否是本用户所属的机构列表里面包含的机构，否则没有权限查看
                List<SysDept> parentDeptList = sysDeptService.getParentDeptList(deptId);
                if(parentDeptList.size()>0&&curUser.getGrandparentDeptId().equals(parentDeptList.get(0).getDeptId())){
                    IPage<List<UserVO>> rs = sysUserService.getUserWithRolePage(page,userName,deptId);
                    return new R<>("查询成功",rs);
                }
                return R.error(1001,"没有权限查看本机构用户信息",null);
            }
            //如果没有传入要查找的机构编号，默认查用户所属的最高级机构编号下面的所有用户
            IPage<List<UserVO>> rs = sysUserService.getUserWithRolePage(page,userName,curUser.getGrandparentDeptId());
            return  new R<>("查询成功",rs);
        }
        IPage<List<UserVO>> rs = sysUserService.getUserWithRolePage(page, userName, deptId);
        return new R<>("查询成功", rs);
    }

    @GetMapping("/info/{userId}")
    @PreAuthorize("@mse.hasPermission('sys:user:info')")
    public R<UserVO> getUserInfo(@PathVariable int userId,@JudgeUserRole boolean isAdmin){
        DandelionUser curUser = SecurityUtils.getUser();
        UserVO user = sysUserService.getUserVoById(userId);
        if(user!=null){
           if(!isAdmin){
               if(curUser.getGrandparentDeptId() != user.getGrandparentDeptId()){
                   return R.error(1001,"该用户不是本机构的成员，不能查看",null);
               }
               return new R<>(user);
           }
           return new R<>(user);
        }
        return R.error(1002,"该用户不存在",null);
    }

}
