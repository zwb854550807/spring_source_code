package com.zengwb.bs.entity;

import java.time.LocalDate;
import java.util.List;


public class ApplyClassesEntity {

    private String planId;

    private LocalDate startData;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public LocalDate getStartData() {
        return startData;
    }

    public void setStartData(LocalDate startData) {
        this.startData = startData;
    }

    public LocalDate getEndData() {
        return endData;
    }

    public void setEndData(LocalDate endData) {
        this.endData = endData;
    }

    public List<String> getAmsUserIds() {
        return amsUserIds;
    }

    public void setAmsUserIds(List<String> amsUserIds) {
        this.amsUserIds = amsUserIds;
    }

    private LocalDate endData;

    private List<String> amsUserIds;

}
