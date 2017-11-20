package com.clover.customer.beans.domain;

/**
 * Created by clover on 2017/10/9.
 */
public class CrmFeatureCategoryBean {
    private String id;
    private String customerFeatureCategory;
    private String customerFeatureCategoryCode;

    public String getCustomerFeatureCategory() {
        return customerFeatureCategory;
    }

    public void setCustomerFeatureCategory(String customerFeatureCategory) {
        this.customerFeatureCategory = customerFeatureCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerFeatureCategoryCode() {
        return customerFeatureCategoryCode;
    }

    public void setCustomerFeatureCategoryCode(String customerFeatureCategoryCode) {
        this.customerFeatureCategoryCode = customerFeatureCategoryCode;
    }
}
