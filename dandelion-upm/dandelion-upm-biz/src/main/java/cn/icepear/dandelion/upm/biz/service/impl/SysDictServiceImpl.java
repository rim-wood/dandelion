package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.upm.api.domain.entity.SysDict;
import cn.icepear.dandelion.upm.biz.mapper.SysDictMapper;
import cn.icepear.dandelion.upm.biz.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rim-wood
 * @description 字典管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    @Cacheable(value = "sys_dict_sub", key = "#typeCode+'-dict_list'")
    public List<SysDict> listDict(String typeCode) {
        return this.list(new QueryWrapper<SysDict>().eq( StringUtils.isNotBlank(typeCode), "type_code", typeCode).eq("del_flag", 0));
    }

    @Override
    @CacheEvict(value = {"sys:dict_type", "sys_dict_sub"}, allEntries = true)
    public boolean updateDictById(SysDict sysDict) {
        return this.updateById(sysDict);
    }

    @Override
    @CacheEvict(value = {"sys:dict_type", "sys_dict_sub"}, allEntries = true)
    public boolean saveDict(SysDict sysDict) {
        return this.save(sysDict);
    }
}
