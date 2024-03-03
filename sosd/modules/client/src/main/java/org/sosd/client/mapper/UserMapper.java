package org.sosd.client.mapper;

import org.sosd.client.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-02-21 21:43:54
* @Entity com.example.client.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




