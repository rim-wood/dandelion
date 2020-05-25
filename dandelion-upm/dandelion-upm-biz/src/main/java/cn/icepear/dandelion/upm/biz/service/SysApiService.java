package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.entity.SysApi;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 接口管理service
 * @date Created on 2020/5/25.
 */
public interface SysApiService extends IService<SysApi> {
    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    IPage<SysApi> findListPage(Page page);

    /**
     * 根据微服务ID查询该服务的接口列表
     *
     * @return
     */
    List<SysApi> findAllList(String serviceId);


}
