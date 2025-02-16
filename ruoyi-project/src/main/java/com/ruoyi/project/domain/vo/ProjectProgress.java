package com.ruoyi.project.domain.vo;

import java.util.List;

public class ProjectProgress {
    private String projectName;
    private List<ProgressData> data;

    // Getters and setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProgressData> getData() {
        return data;
    }

    public void setData(List<ProgressData> data) {
        this.data = data;
    }
}