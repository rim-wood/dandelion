package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lzh
 * @description 字典管理service接口
 * @date Created on 2019-08-21.
 */
public interface SysDictTypeService  extends IService<SysDictType> {

    List<SysDictType> listDictType();

    boolean updateDictTypeById(SysDictType sysDictType);

    boolean saveDictType(SysDictType sysDictType);

    List<SysDictType> selectAll();
}
