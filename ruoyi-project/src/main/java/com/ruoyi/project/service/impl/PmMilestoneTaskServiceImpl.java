package com.ruoyi.project.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmMilestoneTaskMapper;
import com.ruoyi.project.domain.PmMilestoneTask;
import com.ruoyi.project.service.IPmMilestoneTaskService;

/**
 * 里程碑-任务关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
@Service
public class PmMilestoneTaskServiceImpl implements IPmMilestoneTaskService 
{
    @Autowired
    private PmMilestoneTaskMapper pmMilestoneTaskMapper;

    /**
     * 查询里程碑-任务关联
     * 
     * @param id 里程碑-任务关联主键
     * @return 里程碑-任务关联
     */
    @Override
    public PmMilestoneTask selectPmMilestoneTaskById(Long id)
    {
        return pmMilestoneTaskMapper.selectPmMilestoneTaskById(id);
    }

    /**
     * 查询里程碑-任务关联列表
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 里程碑-任务关联
     */
    @Override
    public List<PmMilestoneTask> selectPmMilestoneTaskList(PmMilestoneTask pmMilestoneTask)
    {
        return pmMilestoneTaskMapper.selectPmMilestoneTaskList(pmMilestoneTask);
    }

    /**
     * 新增里程碑-任务关联
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 结果
     */
    @Override
    public int insertPmMilestoneTask(PmMilestoneTask pmMilestoneTask)
    {
        return pmMilestoneTaskMapper.insertPmMilestoneTask(pmMilestoneTask);
    }

    /**
     * 修改里程碑-任务关联
     * 
     * @param pmMilestoneTask 里程碑-任务关联
     * @return 结果
     */
    @Override
    public int updatePmMilestoneTask(PmMilestoneTask pmMilestoneTask)
    {
        return pmMilestoneTaskMapper.updatePmMilestoneTask(pmMilestoneTask);
    }

    /**
     * 批量删除里程碑-任务关联
     * 
     * @param ids 需要删除的里程碑-任务关联主键
     * @return 结果
     */
    @Override
    public int deletePmMilestoneTaskByIds(Long[] ids)
    {
        return pmMilestoneTaskMapper.deletePmMilestoneTaskByIds(ids);
    }

    /**
     * 删除里程碑-任务关联信息
     * 
     * @param id 里程碑-任务关联主键
     * @return 结果
     */
    @Override
    public int deletePmMilestoneTaskById(Long id)
    {
        return pmMilestoneTaskMapper.deletePmMilestoneTaskById(id);
    }
}
