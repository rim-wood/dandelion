package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 字典管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysDictService extends IService<SysDict> {

    List<SysDict> listDict(String typeCode);

    boolean updateDictById(SysDict sysDict);

    boolean saveDict(SysDict sysDict);
}
