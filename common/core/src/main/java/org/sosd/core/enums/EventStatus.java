package org.sosd.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    /**
     * 未审核
     */
    UNAUDIT("0", "未审核"),
    /**
     * 通过
     */
    PASS("3", "可用"),
    /**
     * 未通过
     */
    UNPASS("2","不可用");

    private final String code;
    private final String info;
}
