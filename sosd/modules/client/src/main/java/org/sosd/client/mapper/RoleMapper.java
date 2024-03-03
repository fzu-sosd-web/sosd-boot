package org.sosd.client.mapper;

import org.sosd.client.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【role(角色信息表)】的数据库操作Mapper
* @createDate 2024-02-14 17:11:06
* @Entity com/client.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




