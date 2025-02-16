package com.ruoyi.project.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmTaskDependencyMapper;
import com.ruoyi.project.domain.PmTaskDependency;
import com.ruoyi.project.service.IPmTaskDependencyService;

/**
 * 任务依赖关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-15
 */
@Service
public class PmTaskDependencyServiceImpl implements IPmTaskDependencyService 
{
    @Autowired
    private PmTaskDependencyMapper pmTaskDependencyMapper;

    /**
     * 查询任务依赖关系
     * 
     * @param id 任务依赖关系主键
     * @return 任务依赖关系
     */
    @Override
    public PmTaskDependency selectPmTaskDependencyById(Long id)
    {
        return pmTaskDependencyMapper.selectPmTaskDependencyById(id);
    }

    /**
     * 查询任务依赖关系列表
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 任务依赖关系
     */
    @Override
    public List<PmTaskDependency> selectPmTaskDependencyList(PmTaskDependency pmTaskDependency)
    {
        return pmTaskDependencyMapper.selectPmTaskDependencyList(pmTaskDependency);
    }

    /**
     * 新增任务依赖关系
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 结果
     */
    @Override
    public int insertPmTaskDependency(PmTaskDependency pmTaskDependency)
    {
        return pmTaskDependencyMapper.insertPmTaskDependency(pmTaskDependency);
    }

    /**
     * 修改任务依赖关系
     * 
     * @param pmTaskDependency 任务依赖关系
     * @return 结果
     */
    @Override
    public int updatePmTaskDependency(PmTaskDependency pmTaskDependency)
    {
        return pmTaskDependencyMapper.updatePmTaskDependency(pmTaskDependency);
    }

    /**
     * 批量删除任务依赖关系
     * 
     * @param ids 需要删除的任务依赖关系主键
     * @return 结果
     */
    @Override
    public int deletePmTaskDependencyByIds(Long[] ids)
    {
        return pmTaskDependencyMapper.deletePmTaskDependencyByIds(ids);
    }

    /**
     * 删除任务依赖关系信息
     * 
     * @param id 任务依赖关系主键
     * @return 结果
     */
    @Override
    public int deletePmTaskDependencyById(Long id)
    {
        return pmTaskDependencyMapper.deletePmTaskDependencyById(id);
    }
}
