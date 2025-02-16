package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务分配历史对象 pm_task_assignment_history
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
public class PmTaskAssignmentHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 历史记录ID（唯一标识） */
    private Long historyId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    /** 被分配人ID */
    @Excel(name = "被分配人ID")
    private Long assignedTo;

    /** 分配人 */
    @Excel(name = "分配人")
    private String assignedBy;

    /** 分配时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date assignedTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setHistoryId(Long historyId) 
    {
        this.historyId = historyId;
    }

    public Long getHistoryId() 
    {
        return historyId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setAssignedTo(Long assignedTo) 
    {
        this.assignedTo = assignedTo;
    }

    public Long getAssignedTo() 
    {
        return assignedTo;
    }
    public void setAssignedBy(String assignedBy) 
    {
        this.assignedBy = assignedBy;
    }

    public String getAssignedBy() 
    {
        return assignedBy;
    }
    public void setAssignedTime(Date assignedTime) 
    {
        this.assignedTime = assignedTime;
    }

    public Date getAssignedTime() 
    {
        return assignedTime;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("historyId", getHistoryId())
            .append("taskId", getTaskId())
            .append("assignedTo", getAssignedTo())
            .append("assignedBy", getAssignedBy())
            .append("assignedTime", getAssignedTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
