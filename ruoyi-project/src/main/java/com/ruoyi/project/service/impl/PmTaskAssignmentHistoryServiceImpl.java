package com.ruoyi.project.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmTaskAssignmentHistoryMapper;
import com.ruoyi.project.domain.PmTaskAssignmentHistory;
import com.ruoyi.project.service.IPmTaskAssignmentHistoryService;

/**
 * 任务分配历史Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@Service
public class PmTaskAssignmentHistoryServiceImpl implements IPmTaskAssignmentHistoryService 
{
    @Autowired
    private PmTaskAssignmentHistoryMapper pmTaskAssignmentHistoryMapper;

    /**
     * 查询任务分配历史
     * 
     * @param historyId 任务分配历史主键
     * @return 任务分配历史
     */
    @Override
    public PmTaskAssignmentHistory selectPmTaskAssignmentHistoryByHistoryId(Long historyId)
    {
        return pmTaskAssignmentHistoryMapper.selectPmTaskAssignmentHistoryByHistoryId(historyId);
    }

    /**
     * 查询任务分配历史列表
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 任务分配历史
     */
    @Override
    public List<PmTaskAssignmentHistory> selectPmTaskAssignmentHistoryList(PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        return pmTaskAssignmentHistoryMapper.selectPmTaskAssignmentHistoryList(pmTaskAssignmentHistory);
    }

    /**
     * 新增任务分配历史
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 结果
     */
    @Override
    public int insertPmTaskAssignmentHistory(PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        return pmTaskAssignmentHistoryMapper.insertPmTaskAssignmentHistory(pmTaskAssignmentHistory);
    }

    /**
     * 修改任务分配历史
     * 
     * @param pmTaskAssignmentHistory 任务分配历史
     * @return 结果
     */
    @Override
    public int updatePmTaskAssignmentHistory(PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        return pmTaskAssignmentHistoryMapper.updatePmTaskAssignmentHistory(pmTaskAssignmentHistory);
    }

    /**
     * 批量删除任务分配历史
     * 
     * @param historyIds 需要删除的任务分配历史主键
     * @return 结果
     */
    @Override
    public int deletePmTaskAssignmentHistoryByHistoryIds(Long[] historyIds)
    {
        return pmTaskAssignmentHistoryMapper.deletePmTaskAssignmentHistoryByHistoryIds(historyIds);
    }

    /**
     * 删除任务分配历史信息
     * 
     * @param historyId 任务分配历史主键
     * @return 结果
     */
    @Override
    public int deletePmTaskAssignmentHistoryByHistoryId(Long historyId)
    {
        return pmTaskAssignmentHistoryMapper.deletePmTaskAssignmentHistoryByHistoryId(historyId);
    }
}
