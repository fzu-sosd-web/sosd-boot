package org.sosd.core.domain.model.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class AuditBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需求id
     */
    @NotBlank(message = "主键不能为空")
    private Long id;

    /**
     * 审核人
     */
    private String auditManager;

    /**
     * 是否通过
     */
    @NotBlank(message = "主键不能为空")
    private Boolean ifPass;

    /**
     * 失败原因
     */
    private String failReason;



}
