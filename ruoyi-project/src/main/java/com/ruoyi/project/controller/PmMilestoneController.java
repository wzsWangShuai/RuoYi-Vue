package com.ruoyi.project.controller;

import java.util.List;

import com.ruoyi.project.domain.PmMilestoneTask;
import com.ruoyi.project.service.IPmMilestoneTaskService;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.PmMilestone;
import com.ruoyi.project.service.IPmMilestoneService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 里程碑管理Controller
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@RestController
@RequestMapping("/project/milestone")
public class PmMilestoneController extends BaseController
{
    @Autowired
    private IPmMilestoneService pmMilestoneService;
    @Autowired
    private IPmMilestoneTaskService pmMilestoneTaskService;

    /**
     * 查询里程碑管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:list')")
    @GetMapping("/list")
    public AjaxResult list(PmMilestone pmMilestone)
    {
        List<PmMilestone> list = pmMilestoneService.selectPmMilestoneList(pmMilestone);
        return success(list);
    }

    /**
     * 导出里程碑管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:export')")
    @Log(title = "里程碑管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmMilestone pmMilestone)
    {
        List<PmMilestone> list = pmMilestoneService.selectPmMilestoneList(pmMilestone);
        ExcelUtil<PmMilestone> util = new ExcelUtil<PmMilestone>(PmMilestone.class);
        util.exportExcel(response, list, "里程碑管理数据");
    }

    /**
     * 获取里程碑管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:query')")
    @GetMapping(value = "/{milestoneId}")
    public AjaxResult getInfo(@PathVariable("milestoneId") Long milestoneId)
    {
        return success(pmMilestoneService.selectPmMilestoneByMilestoneId(milestoneId));
    }

    /**
     * 新增里程碑管理
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:add')")
    @Log(title = "里程碑管理", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody PmMilestone pmMilestone)
    {
        Long[] taskIds = pmMilestone.getTaskIds();
        int result = pmMilestoneService.insertPmMilestone(pmMilestone);
        if (result <= 0) {
            throw new RuntimeException("Failed to insert PmMilestone");
        }
        List<PmMilestone> milestones = pmMilestoneService.selectPmMilestoneList(pmMilestone);
        for (Long taskId : taskIds) {
            PmMilestoneTask pmMilestoneTask = new PmMilestoneTask();
            if (Collections.isEmpty(milestones)) {
                throw new RuntimeException("Failed to insert PmMilestoneTask with taskId: " + taskId);
            }
            pmMilestoneTask.setMilestoneId(milestones.get(0).getMilestoneId());
            pmMilestoneTask.setTaskId(taskId);
            int count = pmMilestoneTaskService.insertPmMilestoneTask(pmMilestoneTask);
            if (count <= 0) {
                throw new RuntimeException("Failed to insert PmMilestoneTask with taskId: " + taskId);
            }
        }

        return toAjax(result);
    }

    /**
     * 修改里程碑管理
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:edit')")
    @Log(title = "里程碑管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmMilestone pmMilestone)
    {
        return toAjax(pmMilestoneService.updatePmMilestone(pmMilestone));
    }

    /**
     * 删除里程碑管理
     */
    @PreAuthorize("@ss.hasPermi('project:milestone:remove')")
    @Log(title = "里程碑管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{milestoneIds}")
    public AjaxResult remove(@PathVariable Long[] milestoneIds)
    {
        return toAjax(pmMilestoneService.deletePmMilestoneByMilestoneIds(milestoneIds));
    }
}
