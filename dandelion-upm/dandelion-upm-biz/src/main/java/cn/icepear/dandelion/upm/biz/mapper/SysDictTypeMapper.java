package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    List<SysDictType> selectAll();
}
