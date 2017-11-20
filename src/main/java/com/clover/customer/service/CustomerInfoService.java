package com.clover.customer.service;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vo.CustomerVo;
import com.clover.customer.beans.vo.CustomerOverviewVo;

import java.util.List;
import java.util.Map;

public interface CustomerInfoService {
    CustomerVo getCustomer(String customerId) throws Exception;

    boolean saveCustomer(CustomerVo customerVo) throws Exception;

    Map<String,String> getCustomerFeatures(String customerId) throws Exception;

    Map<String,String> getCustomerFeatures(String customerId,String featureNames) throws Exception;

    boolean saveCustomerFeatures(String customerId,Map<String,String> customerFeatures) throws Exception;
}
