package org.sosd.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EventException extends RuntimeException{
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
    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(String code, Object... args) {
        super(String.format(code, args)); // 格式化消息
        this.code = code;
        this.args = args;
    }
}
