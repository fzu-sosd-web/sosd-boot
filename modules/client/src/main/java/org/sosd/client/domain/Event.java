package org.sosd.client.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName event
 */
@TableName(value ="event")
@Data
public class Event implements Serializable {
    /**
     * 需求id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 需求名称
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 执行时间
     */
    private Date executionTime;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 发布状态默认为’0‘（未审核0，不可用2，可用3，已完成）
     */
    private String status;

    /**
     * 未删除0，已删除1
     */
    private String delFlag;

    /**
     * 奖励时间币的个数
     */
    private Integer reward;

    /**
     * 创立者id
     */
    private Long createBy;

    /**
     * 当前报名人数
     */
    private Integer curentNum;

    /**
     * 审核失败原因
     */
    private String defaultReason;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Event other = (Event) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getNeedNum() == null ? other.getNeedNum() == null : this.getNeedNum().equals(other.getNeedNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getExecutionTime() == null ? other.getExecutionTime() == null : this.getExecutionTime().equals(other.getExecutionTime()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getReward() == null ? other.getReward() == null : this.getReward().equals(other.getReward()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCurentNum() == null ? other.getCurentNum() == null : this.getCurentNum().equals(other.getCurentNum()))
            && (this.getDefaultReason() == null ? other.getDefaultReason() == null : this.getDefaultReason().equals(other.getDefaultReason()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getNeedNum() == null) ? 0 : getNeedNum().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getExecutionTime() == null) ? 0 : getExecutionTime().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getReward() == null) ? 0 : getReward().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCurentNum() == null) ? 0 : getCurentNum().hashCode());
        result = prime * result + ((getDefaultReason() == null) ? 0 : getDefaultReason().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", needNum=").append(needNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", executionTime=").append(executionTime);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", reward=").append(reward);
        sb.append(", createBy=").append(createBy);
        sb.append(", curentNum=").append(curentNum);
        sb.append(", defaultReason=").append(defaultReason);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}