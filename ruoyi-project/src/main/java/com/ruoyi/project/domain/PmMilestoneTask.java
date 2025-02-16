package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 里程碑-任务关联对象 pm_milestone_task
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
public class PmMilestoneTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 里程碑ID */
    @Excel(name = "里程碑ID")
    private Long milestoneId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMilestoneId(Long milestoneId) 
    {
        this.milestoneId = milestoneId;
    }

    public Long getMilestoneId() 
    {
        return milestoneId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("milestoneId", getMilestoneId())
            .append("taskId", getTaskId())
            .toString();
    }
}
