package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目管理对象 pm_project
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public class PmProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID（唯一标识） */
    private Long projId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projName;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String description;

    /** 负责人 */
    @Excel(name = "负责人")
    private String leader;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 项目状态（0未开始 1进行中 2已延期 3已完成） */
    @Excel(name = "项目状态", readConverterExp = "0=未开始,1=进行中,2=已延期,3=已完成")
    private String projectStatus;

    /** 项目状态（0未开始 1进行中 2已延期 3已完成） */
    @Excel(name = "延期原因")
    private String delayReason;

    /** 总任务数 */
    private Long totalTasks;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private List<PmMilestone> children = new ArrayList<>();

    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setProjName(String projName) 
    {
        this.projName = projName;
    }

    public String getProjName() 
    {
        return projName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setLeader(String leader) 
    {
        this.leader = leader;
    }

    public String getLeader() 
    {
        return leader;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setProjectStatus(String projectStatus) 
    {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() 
    {
        return projectStatus;
    }
    public void setTotalTasks(Long totalTasks) 
    {
        this.totalTasks = totalTasks;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public Long getTotalTasks()
    {
        return totalTasks;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public List<PmMilestone> getChildren()
    {
        return children;
    }
    public void setChildren(List<PmMilestone> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projId", getProjId())
            .append("projName", getProjName())
            .append("description", getDescription())
            .append("leader", getLeader())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("projectStatus", getProjectStatus())
            .append("totalTasks", getTotalTasks())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
