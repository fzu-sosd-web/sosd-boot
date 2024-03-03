package org.sosd.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.sosd.client.domain.Role;
import org.sosd.client.service.RoleService;
import org.sosd.client.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【role(角色信息表)】的数据库操作Service实现
* @createDate 2024-02-14 17:11:06
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

}




