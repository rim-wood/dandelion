package cn.icepear.dandelion.upm.biz.mapper;


import cn.icepear.dandelion.upm.api.domain.entity.SysOauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rim-wood
 * @description OAuth2 client 管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysOauthClientDetailsMapper extends BaseMapper<SysOauthClientDetails> {

}
