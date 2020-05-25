package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.upm.api.domain.entity.SysApi;
import cn.icepear.dandelion.upm.biz.mapper.SysApiMapper;
import cn.icepear.dandelion.upm.biz.service.SysApiService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rim-wood
 * @description
 * @date Created on 2020/5/25.
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements SysApiService {

    @Override
    public IPage<SysApi> findListPage(Page page) {
        return null;
    }

    @Override
    public List<SysApi> findAllList(String serviceId) {
        return null;
    }
}
