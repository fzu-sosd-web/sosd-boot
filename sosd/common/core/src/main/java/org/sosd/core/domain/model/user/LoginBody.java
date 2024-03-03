package org.sosd.core.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 授权类型
     */
    @NotBlank(message = "授权类型不能为空")
    private String grantType;
    /**
     * 唯一标识
     */
//    private String uuid;

}