package cn.icepear.dandelion.upm.biz.check;

import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.biz.service.SysDeptService;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDeptCheck implements AbstractCheck {

    private Long deptId;

    private SysDeptService sysDeptService;

    private DandelionUser dandelionUser;

    public UserDeptCheck(Long deptId, SysDeptService sysDeptService) {
        this.deptId = deptId;
        this.sysDeptService = sysDeptService;
        this.dandelionUser = SecurityUtils.getUser();
    }

    public UserDeptCheck() {
    }

    @Override
    public CheckResult check() {
        if(deptId == 0){
            return new CheckResult("不存在0的机构id" , false);
        }
        List<SysDept> highestSysdept = sysDeptService.getParentDeptList(deptId);
        if(highestSysdept.size() == 0){
            return new CheckResult("该用户的机构已经被删除" , false);
        }
        List<SysDept> grandSysDeptS = highestSysdept.stream().filter(sysDept ->
            sysDept.getParentId() == 0
        ).collect(Collectors.toList());

        if(grandSysDeptS.size() == 0){
            return new CheckResult("该用户的机构字典父级被删除" , false);
        }
        SysDept grandSysDept = grandSysDeptS.get(0);
        if(grandSysDept.getDeptId().equals(dandelionUser.getGrandparentDeptId()) ){
            return new CheckResult("该用户可以修改子级的父级机构" , true);
        }else {
            return new CheckResult("该用户不可修改其他机构" , false);
        }
    }
}
