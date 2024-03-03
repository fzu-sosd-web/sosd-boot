package org.sosd.core.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsLoginBody extends LoginBody {
    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}")
    private String phone;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}")
    private String code;
}
