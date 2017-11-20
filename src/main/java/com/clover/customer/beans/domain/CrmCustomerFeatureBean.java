package com.clover.customer.beans.domain;

/**
 * Created by clover on 2017/10/9.
 */
public class CrmCustomerFeatureBean {
    private String id;
    private String customerId;
    private String customerFeatureId;
    private String customerFeatureName;
    private String customerFeatureValue;

    public String getCustomerFeatureValue() {
        return customerFeatureValue;
    }

    public void setCustomerFeatureValue(String customerFeatureValue) {
        this.customerFeatureValue = customerFeatureValue;
    }

    public String getCustomerFeatureId() {
        return customerFeatureId;
    }

    public void setCustomerFeatureId(String customerFeatureId) {
        this.customerFeatureId = customerFeatureId;
    }

    public String getCustomerFeatureName() {
        return customerFeatureName;
    }

    public void setCustomerFeatureName(String customerFeatureName) {
        this.customerFeatureName = customerFeatureName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
