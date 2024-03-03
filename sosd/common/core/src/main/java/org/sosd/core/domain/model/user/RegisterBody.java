package org.sosd.core.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户注册对象
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码不能为空")
    private String phone;


    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;


}