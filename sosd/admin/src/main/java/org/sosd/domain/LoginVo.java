package org.sosd.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginVo {
    /**
     * 授权令牌，成功登录时返回
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 登录状态：成功、账户错误、密码错误
     */
    private String status;

    /**
     * 附加消息，可用于提供更详细的错误信息
     */
    private String message;

    // 构造方法，用于成功登录
    public LoginVo(String accessToken) {
        this.accessToken = accessToken;
        this.status = "成功";
    }

    public LoginVo() {

    }

    // 静态工厂方法，用于错误情况
    public static LoginVo withError(String status, String message) {
        LoginVo loginVo = new LoginVo();
        loginVo.setStatus(status);
        loginVo.setMessage(message);
        return loginVo;
    }
}
