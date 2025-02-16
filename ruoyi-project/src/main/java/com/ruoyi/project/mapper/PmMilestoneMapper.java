package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.PmMilestone;

/**
 * 里程碑管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface PmMilestoneMapper 
{
    /**
     * 查询里程碑管理
     * 
     * @param milestoneId 里程碑管理主键
     * @return 里程碑管理
     */
    public PmMilestone selectPmMilestoneByMilestoneId(Long milestoneId);

    /**
     * 查询里程碑管理列表
     * 
     * @param pmMilestone 里程碑管理
     * @return 里程碑管理集合
     */
    public List<PmMilestone> selectPmMilestoneList(PmMilestone pmMilestone);

    /**
     * 新增里程碑管理
     * 
     * @param pmMilestone 里程碑管理
     * @return 结果
     */
    public int insertPmMilestone(PmMilestone pmMilestone);

    /**
     * 修改里程碑管理
     * 
     * @param pmMilestone 里程碑管理
     * @return 结果
     */
    public int updatePmMilestone(PmMilestone pmMilestone);

    /**
     * 删除里程碑管理
     * 
     * @param milestoneId 里程碑管理主键
     * @return 结果
     */
    public int deletePmMilestoneByMilestoneId(Long milestoneId);

    /**
     * 批量删除里程碑管理
     * 
     * @param milestoneIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePmMilestoneByMilestoneIds(Long[] milestoneIds);
}
