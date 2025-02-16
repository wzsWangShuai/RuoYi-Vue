package com.ruoyi.project.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.PmTask;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.core.domain.entity.PmMilestone;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.project.mapper.PmMilestoneMapper;
import com.ruoyi.project.mapper.PmTaskMapper;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.PmProjectMapper;
import com.ruoyi.common.core.domain.entity.PmProject;
import com.ruoyi.project.service.IPmProjectService;

import static org.apache.commons.lang3.SystemProperties.getUserName;

/**
 * 项目管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@Service
public class PmProjectServiceImpl implements IPmProjectService
{
    private static final Logger log = LoggerFactory.getLogger(PmProjectServiceImpl.class);

    @Autowired
    private PmProjectMapper pmProjectMapper;
    @Autowired
    private PmMilestoneMapper pmMilestoneMapper;
    @Autowired
    private PmTaskMapper pmTaskMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询项目管理
     * 
     * @param projId 项目管理主键
     * @return 项目管理
     */
    @Override
    public PmProject selectPmProjectByProjId(Long projId)
    {
        return pmProjectMapper.selectPmProjectByProjId(projId);
    }

    /**
     * 查询项目管理列表
     * 
     * @param pmProject 项目管理
     * @return 项目管理
     */
    @Override
    public List<PmProject> selectPmProjectList(PmProject pmProject)
    {
        List<PmProject> pmProjectList = pmProjectMapper.selectPmProjectList(pmProject);
        for (PmProject pmProject1 : pmProjectList) {
            PmTask pmTask = new PmTask();
            pmTask.setProjId(pmProject1.getProjId());
            Long totalTasks = pmTaskMapper.selectPmTaskCountByProjId(pmTask);
            pmProject1.setTotalTasks(totalTasks);
        }

        return pmProjectList;
    }

    /**
     * 新增项目管理
     * 
     * @param pmProject 项目管理
     * @return 结果
     */
    @Override
    public int insertPmProject(PmProject pmProject)
    {
        pmProject.setCreateTime(DateUtils.getNowDate());
        pmProject.setCreateBy(getUserName());
        return pmProjectMapper.insertPmProject(pmProject);
    }

    /**
     * 修改项目管理
     * 
     * @param pmProject 项目管理
     * @return 结果
     */
    @Override
    public int updatePmProject(PmProject pmProject)
    {
        pmProject.setUpdateTime(DateUtils.getNowDate());
        pmProject.setUpdateBy(getUserName());
        return pmProjectMapper.updatePmProject(pmProject);
    }

    /**
     * 批量删除项目管理
     * 
     * @param projIds 需要删除的项目管理主键
     * @return 结果
     */
    @Override
    public int deletePmProjectByProjIds(Long[] projIds)
    {
        return pmProjectMapper.deletePmProjectByProjIds(projIds);
    }

    /**
     * 删除项目管理信息
     * 
     * @param projId 项目管理主键
     * @return 结果
     */
    @Override
    public int deletePmProjectByProjId(Long projId)
    {
        return pmProjectMapper.deletePmProjectByProjId(projId);
    }

    /**
     * 查询项目树
     *
     * @return 项目管理集合
     */
    @Override
    public List<TreeSelect> selectPmProjectTree()
    {
        List<PmProject> pmProjectList = pmProjectMapper.selectPmProjectList(new PmProject());
        return buildProjTreeSelect(pmProjectList);
    }

    /**
     * 查询项目里程碑树
     *
     * @return 项目管理集合
     */
    @Override
    public List<TreeSelect> selectPmProjectMilestoneTree()
    {
        List<PmProject> pmProjectList = pmProjectMapper.selectPmProjectList(new PmProject());
        List<PmMilestone> pmMilestoneList = pmMilestoneMapper.selectPmMilestoneList(new PmMilestone());
        for (PmProject pmProject : pmProjectList) {
            for (PmMilestone pmMilestone : pmMilestoneList) {
                if (pmProject.getProjId().equals(pmMilestone.getProjId())) {
                    pmProject.getChildren().add(pmMilestone);
                }
            }
        }
        return buildProjTreeSelect(pmProjectList);
    }
    /**
     * 构建前端所需要下拉树结构
     *
     * @param pmProjects 项目列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildProjTreeSelect(List<PmProject> pmProjects)
    {
        return pmProjects.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 导入项目数据
     * @param pmProjectList
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    @Override
    public String importProject(List<PmProject> pmProjectList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(pmProjectList) || pmProjectList.size() == 0) {
            throw new ServiceException("导入项目数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (PmProject project : pmProjectList) {
            try {
                // 验证是否存在这个项目
                PmProject p = pmProjectMapper.selectPmProjectByName(project.getProjName());
                if (StringUtils.isNull(p)) {
                    BeanValidators.validateWithException(validator, project);
                    // 校验开始时间和结束时间是否符合时间顺序
                    // 空值检查
                    if (project.getStartTime() == null || project.getEndTime() == null) {
                        throw new IllegalArgumentException("Start time or end time cannot be null");
                    }
                    Instant startTime = project.getStartTime().toInstant();
                    Instant endTime = project.getEndTime().toInstant();
                    if (DateUtils.isDateBefore(startTime, endTime)) {
                        project.setCreateBy(operName);
                        pmProjectMapper.insertPmProject(project);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、项目 " + project.getProjName() + " 导入成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、项目 " + project.getProjName() + " 时间不符时间逻辑");
                    }
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, project);
                    Instant startTime = project.getStartTime().toInstant();
                    Instant endTime = project.getEndTime().toInstant();
                    if (DateUtils.isDateBefore(startTime, endTime)) {
                        project.setProjId(p.getProjId());
                        project.setUpdateBy(operName);
                        pmProjectMapper.updatePmProject(project);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、项目 " + project.getProjName() + " 更新成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、项目 " + project.getProjName() + " 时间不符时间逻辑");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、项目 " + project.getProjName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目 " + project.getProjName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }

}
