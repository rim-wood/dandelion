package cn.icepear.dandelion.job.task;


import com.stpass.cityplatform.hoslink.daas.domain.entity.DeptScopeStatus;
import com.stpass.cityplatform.hoslink.daas.domain.entity.DaasDeptScope;
import com.stpass.cityplatform.hoslink.daas.remote.RemoteScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 定期检查用户服务状态
 */
@Component("sysDeptScopeTask")
@Slf4j
public class SysDeptScopeTask {

    @Autowired
    private RemoteScopeService remoteScopeService;

    public void deptScopeStatus(){
        List<DaasDeptScope> sysDeptScopeList = remoteScopeService.scopeAllList();
        sysDeptScopeList.forEach(sysDeptScope -> {
            if (!sysDeptScope.getExpireDate().plus(1, ChronoUnit.DAYS).isAfter(LocalDate.now())){
                sysDeptScope.setStatus(DeptScopeStatus.EXPIRED);
                remoteScopeService.updateStatus(sysDeptScope);
            }
        });
    }
}
