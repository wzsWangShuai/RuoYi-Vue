package com.ruoyi.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.core.domain.entity.PmMilestone;
import com.ruoyi.common.core.domain.entity.PmProject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.domain.PmMilestoneTask;
import com.ruoyi.project.domain.PmTaskAssignmentHistory;
import com.ruoyi.project.domain.PmTaskDependency;
import com.ruoyi.project.domain.vo.ProgressData;
import com.ruoyi.project.domain.vo.ProjectProgress;
import com.ruoyi.project.mapper.PmMilestoneTaskMapper;
import com.ruoyi.project.mapper.PmProjectMapper;
import com.ruoyi.project.mapper.PmTaskAssignmentHistoryMapper;
import com.ruoyi.project.mapper.PmTaskDependencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmTaskMapper;
import com.ruoyi.common.core.domain.entity.PmTask;
import com.ruoyi.project.service.IPmTaskService;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.SystemProperties.getUserName;

/**
 * 任务管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@Service
public class PmTaskServiceImpl implements IPmTaskService 
{
    @Autowired
    private PmTaskMapper pmTaskMapper;
    @Autowired
    private PmMilestoneTaskMapper pmMilestoneTaskMapper;
    @Autowired
    private PmTaskDependencyMapper pmTaskDependencyMapper;
    @Autowired
    private PmTaskAssignmentHistoryMapper pmTaskAssignmentHistoryMapper;
    @Autowired
    private PmProjectMapper pmProjectMapper;

    /**
     * 查询任务管理
     * 
     * @param taskId 任务管理主键
     * @return 任务管理
     */
    @Override
    public PmTask selectPmTaskByTaskId(Long taskId)
    {
        return pmTaskMapper.selectPmTaskByTaskId(taskId);
    }

    /**
     * 查询任务管理列表
     * 
     * @param pmTask 任务管理
     * @return 任务管理
     */
    @Override
    public List<PmTask> selectPmTaskList(PmTask pmTask)
    {
        List<PmTask> list = pmTaskMapper.selectPmTaskList(pmTask);
        for (PmTask task : list) {
            PmTaskDependency pmTaskDependency = new PmTaskDependency();
            pmTaskDependency = pmTaskDependencyMapper.selectPmTaskDependencyByTaskId(task.getTaskId());
            if (pmTaskDependency != null) {
                task.setDependencyId(pmTaskDependency.getDependencyId());
            }
        }
        if (pmTask.getMilestoneId() != null) {
            PmMilestoneTask pmMilestoneTask = new PmMilestoneTask();
            pmMilestoneTask.setMilestoneId(pmTask.getMilestoneId());
            List<PmMilestoneTask> pmMilestoneTaskList = pmMilestoneTaskMapper.selectPmMilestoneTaskList(pmMilestoneTask);
            filterPmTaskList(list, pmMilestoneTaskList);
        }
        return list;
    }

    public void filterPmTaskList(List<PmTask> pmTaskList, List<PmMilestoneTask> pmMilestoneTaskList) {

        // 提取 pmMilestoneTaskList 中的所有 taskId
        Set<Long> taskIds = new HashSet<>();
        for (PmMilestoneTask milestoneTask : pmMilestoneTaskList) {
            taskIds.add(milestoneTask.getTaskId());
        }

        // 过滤 pmTaskList，仅保留那些 taskId 存在于 taskIds 集合中的任务
        List<PmTask> filteredTaskList = new ArrayList<>();
        for (PmTask task : pmTaskList) {
            if (taskIds.contains(task.getTaskId())) {
                filteredTaskList.add(task);
            }
        }

        // 将过滤后的列表赋值回 pmTaskList
        pmTaskList.clear();
        pmTaskList.addAll(filteredTaskList);
    }

    /**
     * 新增任务管理
     * 
     * @param pmTask 任务管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPmTask(PmTask pmTask)
    {
        pmTask.setCreateTime(DateUtils.getNowDate());
        pmTask.setUpdateTime(DateUtils.getNowDate());
        pmTask.setCreateBy(getUserName());
        int result = pmTaskMapper.insertPmTask(pmTask);
        if (result > 0) {
            // 添加依赖关系
            if (pmTask.getDependencyId() != null && pmTask.getDependencyId() != 0) {
                PmTaskDependency pmTaskDependency = new PmTaskDependency();
                pmTaskDependency.setTaskId(pmTask.getTaskId());
                pmTaskDependency.setDependencyId(pmTask.getDependencyId());
                int count = pmTaskDependencyMapper.insertPmTaskDependency(pmTaskDependency);
                if (count <= 0) {
                    throw new RuntimeException("Failed to insert PmTaskDependency with taskId: " + pmTaskDependency);
                }
                if (!StringUtils.equals(pmTask.getStatus(),"0")) {
                    Set<Long> taskIdSet = new HashSet<>();
                    taskIdSet.add(pmTaskDependency.getDependencyId());
                    List<PmTask> pmTaskList = pmTaskMapper.selectPmTaskByTaskIds(taskIdSet.toArray(new Long[taskIdSet.size()]));
                    for (PmTask task : pmTaskList) {
                        String status = pmTask.getStatus();
                        status = StringUtils.equals(task.getStatus(), "3") ? status : "2";
                        pmTask.setStatus(status);
                        pmTaskMapper.updatePmTask(pmTask);
                    }
                }
            }
            pmTask = pmTaskMapper.selectPmTaskByTaskName(pmTask.getTaskName());
            // 添加历史记录
            PmTaskAssignmentHistory pmTaskAssignmentHistory = new PmTaskAssignmentHistory();
            pmTaskAssignmentHistory.setTaskId(pmTask.getTaskId());
            pmTaskAssignmentHistory.setAssignedTo(pmTask.getOwnerId());
            pmTaskAssignmentHistory.setAssignedBy(pmTask.getCreateBy());
            pmTaskAssignmentHistory.setAssignedTime(DateUtils.getNowDate());
            int count1 = pmTaskAssignmentHistoryMapper.insertPmTaskAssignmentHistory(pmTaskAssignmentHistory);
            if (count1 <= 0) {
                throw new RuntimeException("Failed to insert PmTaskAssignmentHistory with taskId: " + pmTaskAssignmentHistory);
            }

        }

        return result;
    }

    /**
     * 修改任务管理
     * 
     * @param pmTask 任务管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePmTask(PmTask pmTask)
    {
        pmTask.setUpdateTime(DateUtils.getNowDate());
        String status = pmTask.getStatus();
        // 处理前置任务不完成，后续任务不能开启
        if (!StringUtils.equals(status,"0")) {
            PmTaskDependency pmTaskDependency = new PmTaskDependency();
            pmTaskDependency.setTaskId(pmTask.getTaskId());
            List<PmTaskDependency> pmTaskDependencyList = pmTaskDependencyMapper.selectPmTaskDependencyList(pmTaskDependency);
            if (pmTaskDependencyList.size() > 0) {
                Set<Long> taskIdSet = new HashSet<>();
                for (PmTaskDependency taskDependency : pmTaskDependencyList) {
                    taskIdSet.add(taskDependency.getDependencyId());
                }
                List<PmTask> pmTaskList = pmTaskMapper.selectPmTaskByTaskIds(taskIdSet.toArray(new Long[taskIdSet.size()]));
                for (PmTask task : pmTaskList) {
                    status = StringUtils.equals(task.getStatus(), "3") ? status : "2";
                    pmTask.setStatus(status);
                }
            } else if (pmTask.getDependencyId() != null) {
                pmTaskDependency.setDependencyId(pmTask.getDependencyId());
                int result = pmTaskDependencyMapper.insertPmTaskDependency(pmTaskDependency);
                if (result <= 0) {
                    throw new RuntimeException("Failed to insert PmTaskDependency with taskId: " + pmTaskDependency);
                }
            }
        }
        PmTask pmTask1 = new PmTask();
        pmTask1 = pmTaskMapper.selectPmTaskByTaskId(pmTask.getTaskId());
        // 处理历史记录
        if (pmTask.getOwnerId() !=null && pmTask1.getOwnerId() != pmTask.getOwnerId()) {
            PmTaskAssignmentHistory pmTaskAssignmentHistory = new PmTaskAssignmentHistory();
            pmTaskAssignmentHistory.setTaskId(pmTask.getTaskId());
            pmTaskAssignmentHistory.setAssignedTo(pmTask.getOwnerId());
            pmTaskAssignmentHistory.setAssignedBy(pmTask.getCreateBy());
            pmTaskAssignmentHistory.setAssignedTime(DateUtils.getNowDate());
            int result = pmTaskAssignmentHistoryMapper.insertPmTaskAssignmentHistory(pmTaskAssignmentHistory);
            if (result <= 0) {
                throw new RuntimeException("Failed to insert PmTaskAssignmentHistory with taskId: " + pmTaskAssignmentHistory);
            }
        }
        // 处理依赖关系
        if (pmTask.getDependencyId() != null && pmTask.getDependencyId() != 0) {
            PmTaskDependency pmTaskDependency = new PmTaskDependency();
            pmTaskDependency.setTaskId(pmTask.getTaskId());
            List<PmTaskDependency> pmTaskDependencyList = pmTaskDependencyMapper.selectPmTaskDependencyList(pmTaskDependency);

            if (pmTaskDependencyList.size() > 0){
                PmTaskDependency pmTaskDependency1 = pmTaskDependencyList.get(0);
                if (pmTaskDependency1.getDependencyId() != pmTask.getDependencyId()) {
                    pmTaskDependency.setDependencyId(pmTask.getDependencyId());
                    int count = pmTaskDependencyMapper.updatePmTaskDependency(pmTaskDependency);
                    if (count <= 0) {
                        throw new RuntimeException("Failed to update PmTaskDependency with taskId: " + pmTaskDependency);
                    }
                }
            }

        }
        return pmTaskMapper.updatePmTask(pmTask);
    }

    /**
     * 批量删除任务管理
     * 
     * @param taskIds 需要删除的任务管理主键
     * @return 结果
     */
    @Override
    public int deletePmTaskByTaskIds(Long[] taskIds)
    {
        return pmTaskMapper.deletePmTaskByTaskIds(taskIds);
    }

    /**
     * 删除任务管理信息
     * 
     * @param taskId 任务管理主键
     * @return 结果
     */
    @Override
    public int deletePmTaskByTaskId(Long taskId)
    {
        return pmTaskMapper.deletePmTaskByTaskId(taskId);
    }

    @Override
    public List<ProjectProgress> getProjectProgress(Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        List<Map<String, Object>> results = pmTaskMapper.selectMonthlyTaskCompletion(params);

        Map<Long, ProjectProgress> projectProgressMap = new HashMap<>();

        for (Map<String, Object> result : results) {
            Long projId = (Long) result.get("proj_id");
            String month = (String) result.get("month");
            int completedTasks = ((Number) result.get("completed_tasks")).intValue();

            ProjectProgress projectProgress = projectProgressMap.get(projId);
            if (projectProgress == null) {
                projectProgress = new ProjectProgress();
                PmProject pmProject = pmProjectMapper.selectPmProjectByProjId(projId);
                projectProgress.setProjectName(pmProject.getProjName());
                projectProgress.setData(new ArrayList<>());
                projectProgressMap.put(projId, projectProgress);
            }

            ProgressData progressData = new ProgressData();
            progressData.setWeek(month);
            progressData.setProgress(completedTasks);

            projectProgress.getData().add(progressData);
        }

        return new ArrayList<>(projectProgressMap.values());
    }
}
