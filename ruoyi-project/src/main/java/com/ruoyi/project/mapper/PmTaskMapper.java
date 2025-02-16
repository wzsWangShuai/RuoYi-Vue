package com.ruoyi.project.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.PmTask;

/**
 * 任务管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface PmTaskMapper 
{
    /**
     * 查询任务管理
     * 
     * @param taskId 任务管理主键
     * @return 任务管理
     */
    public PmTask selectPmTaskByTaskId(Long taskId);

    public PmTask selectPmTaskByTaskName(String taskName);

    /**
     * 查询任务管理
     *
     * @param taskIds 任务管理主键集
     * @return 任务管理
     */
    public List<PmTask> selectPmTaskByTaskIds(Long[] taskIds);

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
     * 删除任务管理
     * 
     * @param taskId 任务管理主键
     * @return 结果
     */
    public int deletePmTaskByTaskId(Long taskId);

    /**
     * 批量删除任务管理
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePmTaskByTaskIds(Long[] taskIds);

    public Long selectPmTaskCountByProjId(PmTask pmTask);

    public List<Map<String, Object>> selectMonthlyTaskCompletion(Map<String, Object> params);
}
