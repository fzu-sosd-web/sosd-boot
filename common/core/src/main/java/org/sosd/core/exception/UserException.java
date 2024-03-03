package org.sosd.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误码对应的参数
     */
    private Object[] args;
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String code, Object... args) {
        super(String.format(code, args)); // 格式化消息
        this.code = code;
        this.args = args;
    }
}
