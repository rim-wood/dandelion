package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.entity.SysDict;
import cn.icepear.dandelion.upm.api.domain.entity.SysDictType;
import cn.icepear.dandelion.upm.biz.service.SysDictService;
import cn.icepear.dandelion.upm.biz.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rim-wood
 * @description 机构部门管理
 * @date Created on 2019-07-29.
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典表类型主表
     * @return
     */
    @GetMapping("/list")
    public R getDictTypePage(){
        List<SysDictType> rs = sysDictTypeService.listDictType();
        return new R<>("查询成功",rs);
    }

    /**
     * 查询详细表
     */
    @GetMapping("/sub/list")
    public R getDictPage(@RequestParam("typeCode")String typeCode){
        List<SysDict> rs = sysDictService.listDict(typeCode);
        return new R<>("查询成功",rs);
    }

    /**
     * 修改字典主表
     *
     * @param sysDictType
     * @return
     */
    @PostMapping("update")
    @PreAuthorize("@mse.hasPermission('sys:dict:update')")
    public R<SysDictType> updateDictType(@RequestBody @Valid SysDictType sysDictType){
        sysDictType.setUpdateTime(LocalDateTime.now());
        SysDictType exist = sysDictTypeService.getOne(new QueryWrapper<SysDictType>().eq("type_code", sysDictType.getTypeCode()).ne("id", sysDictType.getId()));  //校验字典typeCode唯一性
        if(exist != null){
            return new R<>(CommonConstants.FAIL,"已经存在"+ sysDictType.getTypeCode() + "字典",sysDictType);
        }
        //-------------------------------------------查询修改前的type_code并修改详细表关联数据------------------------------------//
        SysDictType var1 = sysDictTypeService.getById(sysDictType.getId());
        sysDictService.update(new UpdateWrapper<SysDict>().set("type_code",sysDictType.getTypeCode()).eq("type_code", var1.getTypeCode()));
        //-------------------------------------------查询修改前的type_code并修改详细表关联数据------------------------------------//
        boolean flag = sysDictTypeService.updateDictTypeById(sysDictType);

        return flag?new R<>(CommonConstants.SUCCESS,"修改成功",sysDictType):new R<>(CommonConstants.FAIL,"修改失败",sysDictType);
    }

    @PostMapping("/sub/update")
    @PreAuthorize("@mse.hasPermission('sys:dict:update')")
    public R<SysDict> updateDict(@RequestBody @Valid SysDict sysDict){
        sysDict.setUpdateTime(LocalDateTime.now());
        boolean flag = sysDictService.updateDictById(sysDict);

        return flag?new R<>(CommonConstants.SUCCESS,"修改成功",sysDict):new R<>(CommonConstants.FAIL,"修改失败",sysDict);
    }

    /**
     * 添加详细表
     *
     * @param sysDict
     * @return
     */
    @PostMapping("sub/add")
    @PreAuthorize("@mse.hasPermission('sys:dict:add')")
    public R<SysDict> subDictAdd(@RequestBody @Valid SysDict sysDict){
        sysDict.setCreateTime(LocalDateTime.now());
        sysDict.setDelFlag("0");
        boolean flag = sysDictService.saveDict(sysDict);

        return flag?new R<>(CommonConstants.SUCCESS,"添加成功",sysDict):new R<>(CommonConstants.FAIL,"添加失败",sysDict);
    }

    /**
     * 添加字典表类型主表
     *
     * @param sysDictType
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("@mse.hasPermission('sys:dict:add')")
    public R<SysDictType> dictAdd(@RequestBody @Valid SysDictType sysDictType){
        SysDictType exist = sysDictTypeService.getOne(new QueryWrapper<SysDictType>().eq("type_code", sysDictType.getTypeCode()));  //校验字典typeCode唯一性
        if(exist != null){
            return new R<>(CommonConstants.FAIL,"已经存在"+ sysDictType.getTypeCode() + "字典",sysDictType);
        }
        sysDictType.setCreateTime(LocalDateTime.now());
        boolean flag = sysDictTypeService.saveDictType(sysDictType);

        return flag? new R<>(CommonConstants.SUCCESS,"添加成功",sysDictType):new R<>(CommonConstants.FAIL,"添加失败",sysDictType);
    }

    @GetMapping("/alllist")
    public R dictAllList(){
        List<SysDictType> rs = sysDictTypeService.selectAll();
        return new R<>("查询成功",rs);
    }
}
