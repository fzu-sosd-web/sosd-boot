package org.sosd.client.mapper;

import org.sosd.client.domain.Follows;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【follows】的数据库操作Mapper
* @createDate 2024-02-19 11:40:15
* @Entity com.example.client.domain.Follows
*/
@Mapper
public interface FollowsMapper extends BaseMapper<Follows> {

}




