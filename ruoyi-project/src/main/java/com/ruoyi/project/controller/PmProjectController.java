package com.ruoyi.project.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.project.service.impl.PmProjectServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.PmProject;
import com.ruoyi.project.service.IPmProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目管理Controller
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@RestController
@RequestMapping("/project/project")
public class PmProjectController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(PmProjectController.class);
    @Autowired
    private IPmProjectService pmProjectService;

    /**
     * 查询项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmProject pmProject)
    {
        startPage();
        List<PmProject> list = pmProjectService.selectPmProjectList(pmProject);
        return getDataTable(list);
    }

    /**
     * 导出项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:project:export')")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmProject pmProject)
    {
        List<PmProject> list = pmProjectService.selectPmProjectList(pmProject);
        ExcelUtil<PmProject> util = new ExcelUtil<PmProject>(PmProject.class);
        util.exportExcel(response, list, "项目管理数据");
    }

    /**
     * 获取项目管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:project:query')")
    @GetMapping(value = "/{projId}")
    public AjaxResult getInfo(@PathVariable("projId") Long projId)
    {
        return success(pmProjectService.selectPmProjectByProjId(projId));
    }

    /**
     * 新增项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmProject pmProject)
    {
        return toAjax(pmProjectService.insertPmProject(pmProject));
    }

    /**
     * 修改项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmProject pmProject)
    {
        return toAjax(pmProjectService.updatePmProject(pmProject));
    }

    /**
     * 删除项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projIds}")
    public AjaxResult remove(@PathVariable Long[] projIds)
    {
        return toAjax(pmProjectService.deletePmProjectByProjIds(projIds));
    }

    /**
     * 导入项目数据
     */
    @PreAuthorize("@ss.hasPermi('project:project:import')")
    @Log(title = "项目管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, @RequestParam(value = "updateSupport", required = false, defaultValue = "0")Boolean isUpdateSupport) {
        try {
            ExcelUtil<PmProject> util = new ExcelUtil<>(PmProject.class);
            List<PmProject> pmProjectList = util.importExcel(file.getInputStream());
            String message = pmProjectService.importProject(pmProjectList, isUpdateSupport, getUsername());
            return AjaxResult.success(message);
        } catch (Exception e) {
            log.error("导入项目数据失败", e);
            return AjaxResult.error(e.getMessage());
        }
    }
    /**
     * 导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<PmProject> util = new ExcelUtil<PmProject>(PmProject.class);
        util.importTemplateExcel(response, "项目数据");
    }
}
