package org.sosd.client.mapper;

import org.sosd.client.domain.Participations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【participations(参与者表)】的数据库操作Mapper
* @createDate 2024-02-19 11:53:02
* @Entity com.example.client.domain.Participations
*/
@Mapper
public interface ParticipationsMapper extends BaseMapper<Participations> {

}




