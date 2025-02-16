package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.PmMilestoneTask;

/**
 * 里程碑-任务关联Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
public interface PmMilestoneTaskMapper 
{
    /**
     * 查询里程碑-任务关联
     * 
     * @param id 里程碑-任务关联主键
     * @return 里程碑-任务关联
     */
    public PmMilestoneTask selectPmMilestoneTaskById(Long id);

    /**
     * 查询里程碑-任务关联列表
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 里程碑-任务关联集合
     */
    public List<PmMilestoneTask> selectPmMilestoneTaskList(PmMilestoneTask pmMilestoneTask);

    /**
     * 新增里程碑-任务关联
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 结果
     */
    public int insertPmMilestoneTask(PmMilestoneTask pmMilestoneTask);

    /**
     * 修改里程碑-任务关联
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 结果
     */
    public int updatePmMilestoneTask(PmMilestoneTask pmMilestoneTask);

    /**
     * 删除里程碑-任务关联
     * 
     * @param id 里程碑-任务关联主键
     * @return 结果
     */
    public int deletePmMilestoneTaskById(Long id);

    /**
     * 批量删除里程碑-任务关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePmMilestoneTaskByIds(Long[] ids);
}
