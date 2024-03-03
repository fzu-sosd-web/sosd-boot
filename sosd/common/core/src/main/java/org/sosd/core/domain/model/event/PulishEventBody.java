package org.sosd.core.domain.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class PulishEventBody {

    /**
     * 需求名字
     */
    private String name;

    /**
     * 需求描述
     */
    private String description;

    /**
     * 需求人数
     */
    private Integer needNum;

    /**
     * 地址信息
     */
    @NotBlank(message = "地址信息不能为空")
    private String address;


    /**
     * 奖励时间币的个数
     */
    private Integer reward;

    /**
     * 执行时间
     */
    @NotNull(message = "执行时间不能为空")
    @Pattern(regexp = "yyyy-MM-dd HH:mm:ss",message = "时间格式错误")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executionTime;

    /**
     * 创立者id
     */
    @NotNull(message = "创立者不能为空")
    private Long createBy;

}
