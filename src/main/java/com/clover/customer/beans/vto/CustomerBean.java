package com.clover.customer.beans.vto;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerBean {
    private String customerId;
    private String customerName;

    private List<CrmCustomerFeatureBean> customerFeatures;
    private Map<String,List<CrmCustomerFeatureBean>> customerCategoryFeatures;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CrmCustomerFeatureBean> getCustomerFeatures() {
        return customerFeatures;
    }

    public void setCustomerFeatures(List<CrmCustomerFeatureBean> customerFeatures) {
        this.customerFeatures = customerFeatures;
    }

    public Map<String, List<CrmCustomerFeatureBean>> getCustomerCategoryFeatures() {
        return customerCategoryFeatures;
    }

    public void setCustomerCategoryFeatures(Map<String, List<CrmCustomerFeatureBean>> customerCategoryFeatures) {
        this.customerCategoryFeatures = customerCategoryFeatures;
    }
}
