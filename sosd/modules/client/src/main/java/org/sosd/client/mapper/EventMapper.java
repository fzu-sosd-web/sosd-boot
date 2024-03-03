package org.sosd.client.mapper;

import org.sosd.client.domain.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【event】的数据库操作Mapper
* @createDate 2024-02-19 11:35:31
* @Entity com.example.client.domain.Event
*/
@Mapper
public interface EventMapper extends BaseMapper<Event> {

}




