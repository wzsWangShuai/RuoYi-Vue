package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.PmTaskAssignmentHistory;

/**
 * 任务分配历史Service接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface IPmTaskAssignmentHistoryService 
{
    /**
     * 查询任务分配历史
     * 
     * @param historyId 任务分配历史主键
     * @return 任务分配历史
     */
    public PmTaskAssignmentHistory selectPmTaskAssignmentHistoryByHistoryId(Long historyId);

    /**
     * 查询任务分配历史列表
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 任务分配历史集合
     */
    public List<PmTaskAssignmentHistory> selectPmTaskAssignmentHistoryList(PmTaskAssignmentHistory pmTaskAssignmentHistory);

    /**
     * 新增任务分配历史
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 结果
     */
    public int insertPmTaskAssignmentHistory(PmTaskAssignmentHistory pmTaskAssignmentHistory);

    /**
     * 修改任务分配历史
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 结果
     */
    public int updatePmTaskAssignmentHistory(PmTaskAssignmentHistory pmTaskAssignmentHistory);

    /**
     * 批量删除任务分配历史
     * 
     * @param historyIds 需要删除的任务分配历史主键集合
     * @return 结果
     */
    public int deletePmTaskAssignmentHistoryByHistoryIds(Long[] historyIds);

    /**
     * 删除任务分配历史信息
     * 
     * @param historyId 任务分配历史主键
     * @return 结果
     */
    public int deletePmTaskAssignmentHistoryByHistoryId(Long historyId);
}
