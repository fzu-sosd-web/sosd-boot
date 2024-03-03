package org.sosd.client.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.sosd.client.domain.User;
import org.sosd.client.service.UserService;
import org.sosd.client.mapper.UserMapper;
import org.sosd.core.constant.Captcha;
import org.sosd.core.enums.UserStatus;
import org.sosd.core.exception.UserException;
import org.sosd.redis.util.RedisUtils;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-02-14 17:11:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Override
    // TODO 修改为传入账号，用户名先设置随机
    public void register(String username, String password,String phone,String code) {
        User one = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhonenumber, phone)
        );
        String vaildcode = RedisUtils.getCacheObject(Captcha.CAPTCHA_CODE_KEY + phone);
        if (!vaildcode.equals(code)){
            throw new UserException("验证码错误");
        }
        else if (!ObjectUtil.isEmpty(one)){
            throw new UserException("该手机号已被注册:{}",phone);
        }
        else if (UserStatus.DISABLE.getCode().equals(one.getStatus())){
            throw new UserException("该用户已被停用:{}",phone);
        }
        User newsuser = new User();
        newsuser.setPassword(password);
        newsuser.setPhonenumber(password);
        newsuser.setUserName(username);
        try {
            save(newsuser);
        }catch (UserException e){
            throw new UserException("保存失败");
        }
    }
}




