package cn.icepear.dandelion.upm.api.remote;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.dto.UserInfo;

/**
 * @author rim-wood
 * @description 内部用户服务调用
 * @date Created on 2019-04-24.
 */
public interface RemoteUserService {
    R<UserInfo> info(String username, String from);
}
