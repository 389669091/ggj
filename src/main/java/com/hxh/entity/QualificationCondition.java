package com.hxh.entity;

/*
* 封装Qualification查询条件对象
* */
public class QualificationCondition {

    private String startDate;//开始申请时间
    private String endDate;//结束时间
    private String type;//资质类型
    private String check;//审核状态

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "QualificationCondition{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", check='" + check + '\'' +
                '}';
    }
}
