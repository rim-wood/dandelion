package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.upm.api.domain.entity.SysDictType;
import cn.icepear.dandelion.upm.biz.mapper.SysDictTypeMapper;
import cn.icepear.dandelion.upm.biz.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzh
 * @description 字典表类型主表service实现
 * @date Created on 2019-08-21.
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    @Cacheable(value = "sys:dict_type", key = "0+'-dict_list'")
    public List<SysDictType> listDictType() {
        return this.list();
    }

    @Override
    @CacheEvict(value = {"sys:dict_type", "sys_dict_sub"} , allEntries = true)
    public boolean updateDictTypeById(SysDictType sysDictType) {
        return this.updateById(sysDictType);
    }

    @Override
    @CacheEvict(value = {"sys:dict_type", "sys_dict_sub"}, allEntries = true)
    public boolean saveDictType(SysDictType sysDictType) {
        return this.save(sysDictType);
    }

    @Override
    @Cacheable(value = "sys:dict_type", key = "0+'-all_list'")
    public List<SysDictType> selectAll(){
        return sysDictTypeMapper.selectAll();
    }
}
