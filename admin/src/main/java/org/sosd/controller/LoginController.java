package org.sosd.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import org.sosd.client.service.UserService;
import org.sosd.core.domain.model.user.RegisterBody;
import org.sosd.domain.LoginVo;
import org.sosd.service.IAuthStrategy;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sosd.core.constant.Captcha;
import org.sosd.core.domain.R;
import org.sosd.core.domain.model.user.LoginBody;
import org.sosd.core.util.JsonUtils;
import org.sosd.core.util.ValidatorUtils;
import org.sosd.redis.util.RedisUtils;
import org.sosd.sms.util.AliyunSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;

@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;
    private final AliyunSmsUtil AliyunSmsUtil;


    // 发送短信验证码的接口
    @GetMapping("/sms/code")
    public R<String> smsCode(@NotBlank(message = "手机号不能为空") String phonenumber) throws Exception {
        // 校验工具
        ValidatorUtils.validate(phonenumber);
        String key = Captcha.CAPTCHA_CODE_KEY + phonenumber;
        String code = RandomUtil.randomNumbers(4);
        // 验证码放入缓存（1分钟）
        RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Captcha.CAPTCHA_EXPIRATION));
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        boolean b = AliyunSmsUtil.sendMsg(phonenumber, map);
        if (!b) {
            log.error("验证码短信发送异常");
            return R.fail();
        }
        log.info("验证码为：{}",code);
        return R.ok("验证码已发送，注意接收，2分钟内有效");
    }

    // 登录界面
    @PostMapping("/login")
    public R<LoginVo> login(@Validated @RequestBody String body) throws Exception {
        // 转为loginbody查找登入类型
        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        // 校验传参
        ValidatorUtils.validate(loginBody);
        // 获取登录类型后选择不同登录方法
        String grantType = loginBody.getGrantType();
        LoginVo login = IAuthStrategy.login(body, grantType);
        return R.ok(login);
    }

    // 注册
    @PostMapping("/register")
    public R<String> register(@Validated @RequestBody RegisterBody registerBody) {
        // 校验传参
        ValidatorUtils.validate(registerBody);
        String phone = registerBody.getPhone();
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        // 使用工具类对密码进行加密
        password = BCrypt.hashpw(password);
        String code = registerBody.getCode();
        userService.register(username,password,phone,code);

        return R.ok();
    }
    // 找回密码
    @PostMapping("/findbackpw")
    public R<String> findBackPw(){
        return null;
    }

    // 退出登录
    @PostMapping("/logout")
    public R<Void> logout() {
        StpUtil.logout();
        return R.ok("退出成功");
    }
}
