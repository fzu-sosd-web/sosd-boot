package org.sosd.core.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode()
public class AuthIdCardBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 实名
     */
    @NotBlank
    @Pattern(regexp = "^[\\u4e00-\\u9fa5·]{2,15}$"
    ,message = "名字格式错误")
    private String realName;

    /**
     * 身份证号
     */
    @NotBlank
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)"
    ,message = "身份证格式错误")
    private String idNumber;

    /**
     * 用户id
     */
    private Long userId;
}
