package com.clover.customer.service;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vto.CustomerCategoryFeaturesBean;

import java.util.List;

/**
 * Created by clover on 2017/10/12.
 */
public interface CustomerLegacyService {
    CustomerBean getCustomer(String customerId) throws Exception;

    CrmCustomerFeatureBean getCustomerFeature(String customerId,String customerFeatureId) throws Exception;

    boolean saveCustomerFeature(String customerId,CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception;

    CustomerCategoryFeaturesBean getCustomerCategoryFeatures(String customerId,String categoryId) throws Exception;

    boolean saveCustomerFeatures(String customerId,List<CrmCustomerFeatureBean> crmCustomerFeatures) throws Exception;

    boolean saveCustomerCategoryFeatures(String customerId,CustomerCategoryFeaturesBean customerCategoryFeaturesBean) throws
            Exception;
}
