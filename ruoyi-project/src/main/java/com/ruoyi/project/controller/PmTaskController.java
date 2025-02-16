package com.ruoyi.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.PmTask;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.service.IPmProjectService;
import com.ruoyi.project.service.IPmTaskService;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ruoyi.project.service.impl.QwenService.callQwen;

/**
 * 任务管理Controller
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@RestController
@RequestMapping("/project/task")
public class PmTaskController extends BaseController
{
    @Autowired
    private IPmTaskService pmTaskService;
    @Autowired
    private IPmProjectService pmProjectService;

    @Value("${qwen.api-key}")
    private String qwenApiKey;

    @Value("${qwen.api-url}")
    private String qwenApiUrl;

    /**
     * 查询任务管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:task:list')")
    @GetMapping("/list")
    public AjaxResult list(PmTask pmTask)
    {
        List<PmTask> list = pmTaskService.selectPmTaskList(pmTask);
        return success(list);
    }

    /**
     * 导出任务管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:task:export')")
    @Log(title = "任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmTask pmTask)
    {
        List<PmTask> list = pmTaskService.selectPmTaskList(pmTask);
        ExcelUtil<PmTask> util = new ExcelUtil<PmTask>(PmTask.class);
        util.exportExcel(response, list, "任务管理数据");
    }

    /**
     * 获取任务管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(pmTaskService.selectPmTaskByTaskId(taskId));
    }

    /**
     * 新增任务管理
     */
    @PreAuthorize("@ss.hasPermi('project:task:add')")
    @Log(title = "任务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmTask pmTask)
    {
        return toAjax(pmTaskService.insertPmTask(pmTask));
    }

    /**
     * 修改任务管理
     */
    @PreAuthorize("@ss.hasPermi('project:task:edit')")
    @Log(title = "任务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmTask pmTask)
    {
        return toAjax(pmTaskService.updatePmTask(pmTask));
    }

    /**
     * 删除任务管理
     */
    @PreAuthorize("@ss.hasPermi('project:task:remove')")
    @Log(title = "任务管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(pmTaskService.deletePmTaskByTaskIds(taskIds));
    }

    /**
     * 获取项目里程碑树列表
     */
    @PreAuthorize("@ss.hasPermi('project:task:list')")
    @GetMapping("/projMilestoneTree")
    public AjaxResult projMilestoneTree()
    {
        return success(pmProjectService.selectPmProjectMilestoneTree());
    }

    /**
     * 获取项目树列表
     */
    @PreAuthorize("@ss.hasPermi('project:task:list')")
    @GetMapping("/projTree")
    public AjaxResult projTree()
    {
        return success(pmProjectService.selectPmProjectTree());
    }

    /**
     * 获取项目任务完成趋势图数据
     */
    @PreAuthorize("@ss.hasPermi('project:task:list')")
    @GetMapping("/getProjectProgress")
    public AjaxResult getProjectProgress()
    {
        // 当天时间
        Date endDate = DateUtils.getNowDate();
        Date startDate = DateUtils.getDateBefore(endDate, 30);
        return success(pmTaskService.getProjectProgress(startDate, endDate));
    }


    @PreAuthorize("@ss.hasPermi('project:task:generate-description')")
    @PostMapping("/generateDescription")
    public AjaxResult generateTaskDescription(@RequestBody Map<String, String> request) {
        String taskName = request.get("taskName");

        if (taskName == null || taskName.isEmpty()) {
            return AjaxResult.error("任务名称不能为空");
        }

        try {
            String description = callQwen(taskName);
            return AjaxResult.success(Map.of("description", description));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "生成任务描述失败");
        }
    }

}
