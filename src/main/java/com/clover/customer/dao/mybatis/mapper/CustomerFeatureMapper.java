package com.clover.customer.dao.mybatis.mapper;

import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/10/12.
 */
@Component
@Mapper
public interface CustomerFeatureMapper {
    CrmCustomerFeatureBean get(String id);

    void insert(CrmCustomerFeatureBean crmCustomerFeatureBean);

    void update(CrmCustomerFeatureBean crmCustomerFeatureBean);

    void delete(String id);

    void deleteByCustomerId(String customerId);

    List<CrmCustomerFeatureBean> search(Map<String, Object> param);

    int count(Map<String, Object> param);
}
