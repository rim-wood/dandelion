package cn.icepear.dandelion.upm.biz.check;

import lombok.AllArgsConstructor;
import lombok.Data;


public interface AbstractCheck {

    public CheckResult check();

    @Data
    @AllArgsConstructor
    class CheckResult {
        private String msg;
        private boolean flag;
    }
}
