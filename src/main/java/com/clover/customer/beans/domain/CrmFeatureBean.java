package com.clover.customer.beans.domain;

/**
 * Created by clover on 2017/10/9.
 */
public class CrmFeatureBean {
    private String id;
    private String featureCode;
    private String featureName;
    private String featureCategoryId;
    private String featureCategoryName;
    private String featureType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeatureCategoryId() {
        return featureCategoryId;
    }

    public void setFeatureCategoryId(String featureCategoryId) {
        this.featureCategoryId = featureCategoryId;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureCategoryName() {
        return featureCategoryName;
    }

    public void setFeatureCategoryName(String featureCategoryName) {
        this.featureCategoryName = featureCategoryName;
    }
}
