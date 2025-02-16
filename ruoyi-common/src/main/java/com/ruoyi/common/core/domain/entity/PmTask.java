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
 * 任务管理对象 pm_task
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public class PmTask extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID（唯一标识） */
    private Long taskId;

    /** 所属项目ID */
    private Long projId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String description;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long ownerId;

    /** 优先级（0低 1中 2高） */
    @Excel(name = "优先级", readConverterExp = "0=低,1=中,2=高")
    private String priority;

    /** 状态（0未开始 1进行中 2阻塞 3已完成） */
    @Excel(name = "状态", readConverterExp = "0=未开始,1=进行中,2=阻塞,3=已完成")
    private String status;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartDate;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndDate;

    /** 实际完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualEndDate;

    /** 预估工时（小时） */
    @Excel(name = "预估工时", readConverterExp = "小=时")
    private Long estimatedHours;

    /** 实际工时（小时） */
    @Excel(name = "实际工时", readConverterExp = "小=时")
    private Long actualHours;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @Excel(name = "任务进度")
    private int taskSchedule;

    private Long milestoneId;

    private Long dependencyId;

    private List<PmTask> children = new ArrayList<PmTask>();

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setPriority(String priority) 
    {
        this.priority = priority;
    }

    public String getPriority() 
    {
        return priority;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setPlanStartDate(Date planStartDate) 
    {
        this.planStartDate = planStartDate;
    }

    public Date getPlanStartDate() 
    {
        return planStartDate;
    }
    public void setPlanEndDate(Date planEndDate) 
    {
        this.planEndDate = planEndDate;
    }

    public Date getPlanEndDate() 
    {
        return planEndDate;
    }
    public void setActualEndDate(Date actualEndDate) 
    {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualEndDate() 
    {
        return actualEndDate;
    }
    public void setEstimatedHours(Long estimatedHours) 
    {
        this.estimatedHours = estimatedHours;
    }

    public Long getEstimatedHours() 
    {
        return estimatedHours;
    }
    public void setActualHours(Long actualHours) 
    {
        this.actualHours = actualHours;
    }

    public Long getActualHours() 
    {
        return actualHours;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public int getTaskSchedule() {
        return taskSchedule;
    }

    public void setTaskSchedule(int taskSchedule) {
        this.taskSchedule = taskSchedule;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(Long dependencyId) {
        this.dependencyId = dependencyId;
    }

    public List<PmTask> getChildren() {
        return children;
    }
    public void setChildren(List<?> children) {
        this.children = (List<PmTask>) children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("parentId", getParentId())
            .append("projId", getProjId())
            .append("taskName", getTaskName())
            .append("description", getDescription())
            .append("ownerId", getOwnerId())
            .append("priority", getPriority())
            .append("status", getStatus())
            .append("planStartDate", getPlanStartDate())
            .append("planEndDate", getPlanEndDate())
            .append("actualEndDate", getActualEndDate())
            .append("estimatedHours", getEstimatedHours())
            .append("actualHours", getActualHours())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
