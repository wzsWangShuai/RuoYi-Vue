package com.ruoyi.project.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.core.domain.entity.PmTask;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.PmMilestoneTask;
import com.ruoyi.project.mapper.PmMilestoneTaskMapper;
import com.ruoyi.project.mapper.PmTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmMilestoneMapper;
import com.ruoyi.common.core.domain.entity.PmMilestone;
import com.ruoyi.project.service.IPmMilestoneService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 里程碑管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@Service
public class PmMilestoneServiceImpl implements IPmMilestoneService 
{
    @Autowired
    private PmMilestoneMapper pmMilestoneMapper;
    @Autowired
    private PmTaskMapper pmTaskMapper;
    @Autowired
    private PmMilestoneTaskMapper pmMilestoneTaskMapper;

    /**
     * 查询里程碑管理
     * 
     * @param milestoneId 里程碑管理主键
     * @return 里程碑管理
     */
    @Override
    public PmMilestone selectPmMilestoneByMilestoneId(Long milestoneId)
    {
        PmMilestone pmMilestone = pmMilestoneMapper.selectPmMilestoneByMilestoneId(milestoneId);
        PmMilestoneTask pmMilestoneTask = new PmMilestoneTask();
        pmMilestoneTask.setMilestoneId(milestoneId);
        List<PmMilestoneTask> pmMilestoneTaskList = pmMilestoneTaskMapper.selectPmMilestoneTaskList(pmMilestoneTask);
        Long[] taskIds = pmMilestoneTaskList.stream().map(PmMilestoneTask::getTaskId).toArray(Long[]::new);
        pmMilestone.setTaskIds(taskIds);
        return pmMilestone;
    }

    /**
     * 查询里程碑管理列表
     * 
     * @param pmMilestone 里程碑管理
     * @return 里程碑管理
     */
    @Override
    public List<PmMilestone> selectPmMilestoneList(PmMilestone pmMilestone)
    {
        List<PmMilestone> list = pmMilestoneMapper.selectPmMilestoneList(pmMilestone);
        List<PmTask> pmTaskList = pmTaskMapper.selectPmTaskList(new PmTask());
        List<PmMilestoneTask> pmMilestoneTaskList = pmMilestoneTaskMapper.selectPmMilestoneTaskList(new PmMilestoneTask());
        populateMilestoneChildren(list, pmTaskList, pmMilestoneTaskList);
        return list;
    }
    public void populateMilestoneChildren(List<PmMilestone> list, List<PmTask> pmTaskList, List<PmMilestoneTask> pmMilestoneTaskList) {
        // 创建一个映射，将 milestoneId 映射到 PmMilestone 对象
        Map<Long, PmMilestone> milestoneMap = new HashMap<>();
        for (PmMilestone milestone : list) {
            milestone.setParentId(0l);
            milestoneMap.put(milestone.getMilestoneId(), milestone);
        }

        // 创建一个映射，将 taskId 映射到 PmTask 对象
        Map<Long, PmTask> taskMap = new HashMap<>();
        for (PmTask task : pmTaskList) {
            taskMap.put(task.getTaskId(), task);
        }

        // 遍历 pmMilestoneTaskList，将任务添加到对应的里程碑的 children 列表中
        for (PmMilestoneTask milestoneTask : pmMilestoneTaskList) {
            Long milestoneId = milestoneTask.getMilestoneId();
            Long taskId = milestoneTask.getTaskId();

            PmMilestone milestone = milestoneMap.get(milestoneId);
            PmTask task = taskMap.get(taskId);

            if (milestone != null && task != null) {
                milestone.getChildren().add(task);
                Long[] newArray = appendToLongArray(milestone.getTaskIds(), taskId);
                milestone.setTaskIds(newArray);
            }
        }
    }
    /**
     * 将一个 Long 元素拼接到 Long[] 数组末尾
     *
     * @param array 原始数组
     * @param element 要添加的元素
     * @return 新的数组
     */
    public static Long[] appendToLongArray(Long[] array, Long element) {
        if (array == null) {
            // 如果原始数组为 null，则创建一个新的长度为 1 的数组并添加元素
            return new Long[]{element};
        }

        // 创建一个长度为原数组长度 + 1 的新数组
        Long[] newArray = Arrays.copyOf(array, array.length + 1);
        // 将新元素添加到新数组的末尾
        newArray[array.length] = element;
        return newArray;
    }

    /**
     * 新增里程碑管理
     * 
     * @param pmMilestone 里程碑管理
     * @return 结果
     */
    @Override
    public int insertPmMilestone(PmMilestone pmMilestone)
    {
        pmMilestone.setCreateTime(DateUtils.getNowDate());
        return pmMilestoneMapper.insertPmMilestone(pmMilestone);
    }

    /**
     * 修改里程碑管理
     * 
     * @param pmMilestone 里程碑管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePmMilestone(PmMilestone pmMilestone)
    {
        Long[] taskIds = pmMilestone.getTaskIds();
        PmMilestoneTask pmMilestoneTask = new PmMilestoneTask();
        pmMilestoneTask.setMilestoneId(pmMilestone.getMilestoneId());
        List<PmMilestoneTask> pmMilestoneTaskList = pmMilestoneTaskMapper.selectPmMilestoneTaskList(pmMilestoneTask);
        Long[] pmMilestoneTaskIds = new Long[pmMilestoneTaskList.size()];
        for (int i = 0; i < pmMilestoneTaskList.size(); i++) {
            pmMilestoneTaskIds[i] = pmMilestoneTaskList.get(i).getTaskId();
        }

        List<Long> addIds = new ArrayList<>(Arrays.stream(taskIds).toList());
        addIds.removeAll(Arrays.asList(pmMilestoneTaskIds));
        List<Long> deleteIds = new ArrayList<>(Arrays.stream(pmMilestoneTaskIds).toList());
        deleteIds.removeAll(Arrays.asList(taskIds));
        int result = 0;
        if (addIds.size() != 0) {
            for (Long taskId : addIds) {
                PmMilestoneTask pmMilestoneTask1 = new PmMilestoneTask();
                pmMilestoneTask1.setMilestoneId(pmMilestone.getMilestoneId());
                pmMilestoneTask1.setTaskId(taskId);
                result = pmMilestoneTaskMapper.insertPmMilestoneTask(pmMilestoneTask1);
                if (result <= 0) {
                    throw new RuntimeException("Failed to update PmMilestoneTask with taskId: " + taskId);
                }
            }
        }

        Long[] ids = new Long[deleteIds.size()];
        if (ids.length != 0) {
            for (int i = 0; i < deleteIds.size(); i++) {
                Long taskId = deleteIds.get(i);
                for (PmMilestoneTask milestoneTask : pmMilestoneTaskList) {
                    if (milestoneTask.getTaskId().equals(taskId)) {
                        ids[i] = milestoneTask.getId();
                    }
                }
            }
            result = pmMilestoneTaskMapper.deletePmMilestoneTaskByIds(ids);
            if (result <= 0) {
                throw new RuntimeException("Failed to update PmMilestoneTask with taskId: " + deleteIds);
            }
        }
        result = pmMilestoneMapper.updatePmMilestone(pmMilestone);
        return result;
    }

    /**
     * 批量删除里程碑管理
     * 
     * @param milestoneIds 需要删除的里程碑管理主键
     * @return 结果
     */
    @Override
    public int deletePmMilestoneByMilestoneIds(Long[] milestoneIds)
    {
        return pmMilestoneMapper.deletePmMilestoneByMilestoneIds(milestoneIds);
    }

    /**
     * 删除里程碑管理信息
     * 
     * @param milestoneId 里程碑管理主键
     * @return 结果
     */
    @Override
    public int deletePmMilestoneByMilestoneId(Long milestoneId)
    {
        return pmMilestoneMapper.deletePmMilestoneByMilestoneId(milestoneId);
    }
}
