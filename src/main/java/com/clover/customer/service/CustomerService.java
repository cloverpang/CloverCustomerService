package com.clover.customer.service;

import com.clover.customer.beans.domain.CrmCustomerBean;

/**
 * Created by clover on 2017/10/6.
 */
public interface CustomerService {
    CrmCustomerBean get(String id);

    boolean add(CrmCustomerBean crmCustomerBean) throws Exception;

    boolean update(CrmCustomerBean crmCustomerBean) throws Exception;

    boolean insert(CrmCustomerBean crmCustomerBean) throws Exception;

    boolean delete(String id) throws Exception;
}
