package com.clover.customer.beans.vto;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by clover on 2017/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCategoryFeaturesBean {
    private String customerName;
    private String featureCategoryId;
    private String featureCategoryName;

    private List<CrmCustomerFeatureBean> customerCategoryFeatures;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFeatureCategoryId() {
        return featureCategoryId;
    }

    public void setFeatureCategoryId(String featureCategoryId) {
        this.featureCategoryId = featureCategoryId;
    }

    public String getFeatureCategoryName() {
        return featureCategoryName;
    }

    public void setFeatureCategoryName(String featureCategoryName) {
        this.featureCategoryName = featureCategoryName;
    }

    public List<CrmCustomerFeatureBean> getCustomerCategoryFeatures() {
        return customerCategoryFeatures;
    }

    public void setCustomerCategoryFeatures(List<CrmCustomerFeatureBean> customerCategoryFeatures) {
        this.customerCategoryFeatures = customerCategoryFeatures;
    }
}
