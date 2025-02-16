package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.PmTaskDependency;

/**
 * 任务依赖关系Service接口
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
public interface IPmTaskDependencyService 
{
    /**
     * 查询任务依赖关系
     * 
     * @param id 任务依赖关系主键
     * @return 任务依赖关系
     */
    public PmTaskDependency selectPmTaskDependencyById(Long id);

    /**
     * 查询任务依赖关系列表
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 任务依赖关系集合
     */
    public List<PmTaskDependency> selectPmTaskDependencyList(PmTaskDependency pmTaskDependency);

    /**
     * 新增任务依赖关系
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 结果
     */
    public int insertPmTaskDependency(PmTaskDependency pmTaskDependency);

    /**
     * 修改任务依赖关系
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 结果
     */
    public int updatePmTaskDependency(PmTaskDependency pmTaskDependency);

    /**
     * 批量删除任务依赖关系
     * 
     * @param ids 需要删除的任务依赖关系主键集合
     * @return 结果
     */
    public int deletePmTaskDependencyByIds(Long[] ids);

    /**
     * 删除任务依赖关系信息
     * 
     * @param id 任务依赖关系主键
     * @return 结果
     */
    public int deletePmTaskDependencyById(Long id);
}
