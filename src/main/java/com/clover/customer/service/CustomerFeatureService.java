package com.clover.customer.service;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;

import java.util.List;

/**
 * Created by clover on 2017/10/12.
 */
public interface CustomerFeatureService {
    CrmCustomerFeatureBean get(String id);

    boolean add(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception;

    boolean update(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception;

    boolean insert(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception;

    boolean delete(String id) throws Exception;

    boolean deleteByCustomerId(String customerId) throws Exception;

    List<CrmCustomerFeatureBean> getByCustomerId(String customerId) throws Exception;

    List<CrmCustomerFeatureBean> getByCustomerIdAndCategoryId(String customerId,String categoryId) throws Exception;

    CrmCustomerFeatureBean getByCustomerIdAndFeatureId(String customerId,String customerFeatureId) throws Exception;
}
