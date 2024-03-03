package org.sosd.core.domain.model.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CommentBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空")
    @Size(max = 127,message = "留言不能超过100字")
    private String content;

    /**
     * 发布者id
     */
    @NotBlank(message = "发布者id不能为空")
    private Integer createBy;



}
