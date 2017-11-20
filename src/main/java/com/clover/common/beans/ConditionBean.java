package com.clover.common.beans;

/**
 * Created by clover on 2017/11/1.
 */
public class ConditionBean {
    private String conditionName;
    private String conditionExpression;
    private String conditionValue;

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    @Override
    public String toString() {
        return "ConditionBean{" +
                "conditionName='" + conditionName + '\'' +
                ", conditionExpression='" + conditionExpression + '\'' +
                ", conditionValue='" + conditionValue + '\'' +
                '}';
    }
}
