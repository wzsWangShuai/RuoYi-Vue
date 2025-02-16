package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.PmProject;

/**
 * 项目管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface PmProjectMapper 
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
     * 删除项目管理
     * 
     * @param projId 项目管理主键
     * @return 结果
     */
    public int deletePmProjectByProjId(Long projId);

    /**
     * 批量删除项目管理
     * 
     * @param projIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePmProjectByProjIds(Long[] projIds);

    /**
     * 根据项目名查询项目
     * @param projName
     * @return
     */
    public PmProject selectPmProjectByName(String projName);
}
