package org.sosd.service;


import cn.hutool.extra.spring.SpringUtil;
import org.sosd.domain.LoginVo;

/**
 * 授权策略
 */
public interface IAuthStrategy {

    String BASE_NAME = "Auth";

    /**
     * 登录
     */
    static LoginVo login(String body, String grantType) throws Exception {
        // 授权类型
        String beanName = grantType + BASE_NAME;
        if (!SpringUtil.getBeanFactory().containsBean(beanName)) {
            throw new Exception("授权类型不正确!");
        }
        IAuthStrategy instance = SpringUtil.getBean(beanName);
        return instance.login(body);
    }

    /**
     * 登录
     */
    LoginVo login(String body);

}
