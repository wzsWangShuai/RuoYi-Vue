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
import com.ruoyi.project.domain.PmTaskAssignmentHistory;
import com.ruoyi.project.service.IPmTaskAssignmentHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务分配历史Controller
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@RestController
@RequestMapping("/project/task/history")
public class PmTaskAssignmentHistoryController extends BaseController
{
    @Autowired
    private IPmTaskAssignmentHistoryService pmTaskAssignmentHistoryService;

    /**
     * 查询任务分配历史列表
     */
    @PreAuthorize("@ss.hasPermi('project:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        startPage();
        List<PmTaskAssignmentHistory> list = pmTaskAssignmentHistoryService.selectPmTaskAssignmentHistoryList(pmTaskAssignmentHistory);
        return getDataTable(list);
    }

    /**
     * 导出任务分配历史列表
     */
    @PreAuthorize("@ss.hasPermi('project:history:export')")
    @Log(title = "任务分配历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        List<PmTaskAssignmentHistory> list = pmTaskAssignmentHistoryService.selectPmTaskAssignmentHistoryList(pmTaskAssignmentHistory);
        ExcelUtil<PmTaskAssignmentHistory> util = new ExcelUtil<PmTaskAssignmentHistory>(PmTaskAssignmentHistory.class);
        util.exportExcel(response, list, "任务分配历史数据");
    }

    /**
     * 获取任务分配历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:history:query')")
    @GetMapping(value = "/{historyId}")
    public AjaxResult getInfo(@PathVariable("historyId") Long historyId)
    {
        return success(pmTaskAssignmentHistoryService.selectPmTaskAssignmentHistoryByHistoryId(historyId));
    }

    /**
     * 新增任务分配历史
     */
    @PreAuthorize("@ss.hasPermi('project:history:add')")
    @Log(title = "任务分配历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        return toAjax(pmTaskAssignmentHistoryService.insertPmTaskAssignmentHistory(pmTaskAssignmentHistory));
    }

    /**
     * 修改任务分配历史
     */
    @PreAuthorize("@ss.hasPermi('project:history:edit')")
    @Log(title = "任务分配历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmTaskAssignmentHistory pmTaskAssignmentHistory)
    {
        return toAjax(pmTaskAssignmentHistoryService.updatePmTaskAssignmentHistory(pmTaskAssignmentHistory));
    }

    /**
     * 删除任务分配历史
     */
    @PreAuthorize("@ss.hasPermi('project:history:remove')")
    @Log(title = "任务分配历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{historyIds}")
    public AjaxResult remove(@PathVariable Long[] historyIds)
    {
        return toAjax(pmTaskAssignmentHistoryService.deletePmTaskAssignmentHistoryByHistoryIds(historyIds));
    }
}
