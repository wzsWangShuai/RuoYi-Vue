package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 里程碑管理对象 pm_milestone
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public class PmMilestone extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 里程碑ID（唯一标识） */
    private Long milestoneId;

    /** 所属项目ID */
    @Excel(name = "所属项目ID")
    private Long projId;

    /** 里程碑名称 */
    @Excel(name = "里程碑名称")
    private String milestoneName;

    /** 里程碑描述 */
    @Excel(name = "里程碑描述")
    private String description;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private Long[] taskIds;

    private List<PmTask> children = new ArrayList<>();

    public void setMilestoneId(Long milestoneId) 
    {
        this.milestoneId = milestoneId;
    }

    public Long getMilestoneId() 
    {
        return milestoneId;
    }
    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setMilestoneName(String milestoneName) 
    {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneName() 
    {
        return milestoneName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setDeadline(Date deadline) 
    {
        this.deadline = deadline;
    }

    public Date getDeadline() 
    {
        return deadline;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long[] taskIds) {
        this.taskIds = taskIds;
    }

    @Override
    public List<PmTask> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = (List<PmTask>) children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("milestoneId", getMilestoneId())
            .append("projId", getProjId())
            .append("milestoneName", getMilestoneName())
            .append("description", getDescription())
            .append("deadline", getDeadline())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
