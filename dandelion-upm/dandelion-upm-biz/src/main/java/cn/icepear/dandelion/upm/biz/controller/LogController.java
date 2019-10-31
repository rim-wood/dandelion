package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.entity.SysLog;
import cn.icepear.dandelion.upm.api.domain.vo.SysLogVO;
import cn.icepear.dandelion.upm.biz.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author rim-wood
 * @description 系统日志API
 * @date Created on 2019-08-08.
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 日志列表分页查询
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@mse.hasPermission('sys:log:page')")
    public R getLogPage(Page page, SysLogVO sysLogVO){
        IPage<SysLog> rs = sysLogService.page(page, new QueryWrapper<SysLog>().like(StringUtils.isNotBlank(sysLogVO.getTitle()),"title",sysLogVO.getTitle()));
        return new R<>("查询成功",rs);
    }

    /**
     * 删除日志
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@mse.hasPermission('sys:log:del')")
    public R removeById(@PathVariable Long id) {
        boolean rs = sysLogService.removeById(id);
        if(rs){
            return new R<>("删除成功",null);
        }
        return R.error(1000,"删除失败",null);
    }
}
