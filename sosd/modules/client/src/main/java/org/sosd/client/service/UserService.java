package org.sosd.client.service;

import org.sosd.client.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86187
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-02-14 17:11:06
*/
public interface UserService extends IService<User> {
    void register(String username,String password,String phone,String code);
}
