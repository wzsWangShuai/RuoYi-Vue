package com.ruoyi.project.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.core.domain.entity.PmTask;
import com.ruoyi.project.domain.vo.ProjectProgress;

/**
 * 任务管理Service接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface IPmTaskService 
{
    /**
     * 查询任务管理
     * 
     * @param taskId 任务管理主键
     * @return 任务管理
     */
    public PmTask selectPmTaskByTaskId(Long taskId);

    /**
     * 查询任务管理列表
     * 
     * @param pmTask 任务管理
     * @return 任务管理集合
     */
    public List<PmTask> selectPmTaskList(PmTask pmTask);

    /**
     * 新增任务管理
     * 
     * @param pmTask 任务管理
     * @return 结果
     */
    public int insertPmTask(PmTask pmTask);

    /**
     * 修改任务管理
     * 
     * @param pmTask 任务管理
     * @return 结果
     */
    public int updatePmTask(PmTask pmTask);

    /**
     * 批量删除任务管理
     * 
     * @param taskIds 需要删除的任务管理主键集合
     * @return 结果
     */
    public int deletePmTaskByTaskIds(Long[] taskIds);

    /**
     * 删除任务管理信息
     * 
     * @param taskId 任务管理主键
     * @return 结果
     */
    public int deletePmTaskByTaskId(Long taskId);

    public List<ProjectProgress> getProjectProgress(Date startDate, Date endDate);
}
