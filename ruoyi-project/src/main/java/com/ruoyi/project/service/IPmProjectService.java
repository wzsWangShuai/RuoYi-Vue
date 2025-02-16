package com.ruoyi.project.service;

import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.PmProject;

/**
 * 项目管理Service接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface IPmProjectService 
{
    /**
     * 查询项目管理
     * 
     * @param projId 项目管理主键
     * @return 项目管理
     */
    public PmProject selectPmProjectByProjId(Long projId);

    /**
     * 查询项目管理列表
     * 
     * @param pmProject 项目管理
     * @return 项目管理集合
     */
    public List<PmProject> selectPmProjectList(PmProject pmProject);

    /**
     * 新增项目管理
     * 
     * @param pmProject 项目管理
     * @return 结果
     */
    public int insertPmProject(PmProject pmProject);

    /**
     * 修改项目管理
     * 
     * @param pmProject 项目管理
     * @return 结果
     */
    public int updatePmProject(PmProject pmProject);

    /**
     * 批量删除项目管理
     * 
     * @param projIds 需要删除的项目管理主键集合
     * @return 结果
     */
    public int deletePmProjectByProjIds(Long[] projIds);

    /**
     * 删除项目管理信息
     * 
     * @param projId 项目管理主键
     * @return 结果
     */
    public int deletePmProjectByProjId(Long projId);

    /**
     * 查询项目树
     *
     * @return 项目管理集合
     */
    public List<TreeSelect> selectPmProjectTree();

    /**
     * 查询项目里程碑树
     *
     * @return 项目管理集合
     */
    public List<TreeSelect> selectPmProjectMilestoneTree();

    /**
     * 构建前端所需要下拉树结构
     *
     * @param pmProjects 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildProjTreeSelect(List<PmProject> pmProjects);

    /**
     * 导入项目数据
     * @param pmProjectList
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    public String importProject(List<PmProject> pmProjectList, Boolean isUpdateSupport, String operName);

}
