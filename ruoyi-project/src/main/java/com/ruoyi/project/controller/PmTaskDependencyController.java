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
import com.ruoyi.project.domain.PmTaskDependency;
import com.ruoyi.project.service.IPmTaskDependencyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务依赖关系Controller
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
@RestController
@RequestMapping("/project/taskDependency")
public class PmTaskDependencyController extends BaseController
{
    @Autowired
    private IPmTaskDependencyService pmTaskDependencyService;

    /**
     * 查询任务依赖关系列表
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmTaskDependency pmTaskDependency)
    {
        startPage();
        List<PmTaskDependency> list = pmTaskDependencyService.selectPmTaskDependencyList(pmTaskDependency);
        return getDataTable(list);
    }

    /**
     * 导出任务依赖关系列表
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:export')")
    @Log(title = "任务依赖关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmTaskDependency pmTaskDependency)
    {
        List<PmTaskDependency> list = pmTaskDependencyService.selectPmTaskDependencyList(pmTaskDependency);
        ExcelUtil<PmTaskDependency> util = new ExcelUtil<PmTaskDependency>(PmTaskDependency.class);
        util.exportExcel(response, list, "任务依赖关系数据");
    }

    /**
     * 获取任务依赖关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pmTaskDependencyService.selectPmTaskDependencyById(id));
    }

    /**
     * 新增任务依赖关系
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:add')")
    @Log(title = "任务依赖关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmTaskDependency pmTaskDependency)
    {
        return toAjax(pmTaskDependencyService.insertPmTaskDependency(pmTaskDependency));
    }

    /**
     * 修改任务依赖关系
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:edit')")
    @Log(title = "任务依赖关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmTaskDependency pmTaskDependency)
    {
        return toAjax(pmTaskDependencyService.updatePmTaskDependency(pmTaskDependency));
    }

    /**
     * 删除任务依赖关系
     */
    @PreAuthorize("@ss.hasPermi('project:taskDependency:remove')")
    @Log(title = "任务依赖关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pmTaskDependencyService.deletePmTaskDependencyByIds(ids));
    }
}
