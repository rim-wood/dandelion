package cn.icepear.dandelion.upm.api.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rim-wood
 * @description 日志对外实体
 * @date Created on 2019/10/21.
 */
@Data
public class SysLogVO implements Serializable {
    /**
     * 日志标题
     */
    private String title;
}
