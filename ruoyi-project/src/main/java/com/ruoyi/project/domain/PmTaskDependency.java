package com.ruoyi.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务依赖关系对象 pm_task_dependency
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
public class PmTaskDependency extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 当前任务ID */
    @Excel(name = "当前任务ID")
    private Long taskId;

    /** 依赖的任务ID */
    @Excel(name = "依赖的任务ID")
    private Long dependencyId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setDependencyId(Long dependencyId) 
    {
        this.dependencyId = dependencyId;
    }

    public Long getDependencyId() 
    {
        return dependencyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("dependencyId", getDependencyId())
            .toString();
    }
}
