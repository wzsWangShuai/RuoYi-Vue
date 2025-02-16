package com.ruoyi.project.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.project.domain.PmMilestoneTask;
import com.ruoyi.project.service.IPmMilestoneTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 里程碑-任务关联Controller
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
@RestController
@RequestMapping("/project/milestoneTask")
public class PmMilestoneTaskController extends BaseController
{
    @Autowired
    private IPmMilestoneTaskService pmMilestoneTaskService;

    /**
     * 查询里程碑-任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmMilestoneTask pmMilestoneTask)
    {
        startPage();
        List<PmMilestoneTask> list = pmMilestoneTaskService.selectPmMilestoneTaskList(pmMilestoneTask);
        return getDataTable(list);
    }

    /**
     * 导出里程碑-任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:export')")
    @Log(title = "里程碑-任务关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmMilestoneTask pmMilestoneTask)
    {
        List<PmMilestoneTask> list = pmMilestoneTaskService.selectPmMilestoneTaskList(pmMilestoneTask);
        ExcelUtil<PmMilestoneTask> util = new ExcelUtil<PmMilestoneTask>(PmMilestoneTask.class);
        util.exportExcel(response, list, "里程碑-任务关联数据");
    }

    /**
     * 获取里程碑-任务关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pmMilestoneTaskService.selectPmMilestoneTaskById(id));
    }

    /**
     * 新增里程碑-任务关联
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:add')")
    @Log(title = "里程碑-任务关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmMilestoneTask pmMilestoneTask)
    {
        return toAjax(pmMilestoneTaskService.insertPmMilestoneTask(pmMilestoneTask));
    }

    /**
     * 修改里程碑-任务关联
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:edit')")
    @Log(title = "里程碑-任务关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmMilestoneTask pmMilestoneTask)
    {
        return toAjax(pmMilestoneTaskService.updatePmMilestoneTask(pmMilestoneTask));
    }

    /**
     * 删除里程碑-任务关联
     */
    @PreAuthorize("@ss.hasPermi('project:milestoneTask:remove')")
    @Log(title = "里程碑-任务关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pmMilestoneTaskService.deletePmMilestoneTaskByIds(ids));
    }
}
