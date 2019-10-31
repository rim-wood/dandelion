package cn.icepear.dandelion.upm.api.domain.vo;

import cn.icepear.dandelion.upm.api.domain.dto.MenuTree;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rim-wood
 * @description 系统菜单树（列表）
 * @date Created on 2019/10/21.
 */
@Data
public class SystemToMenuVo extends MenuTree implements Serializable {
    private Integer systemId;
    private String sysLabel;
    private List<MenuTree> menuTrees;
}
