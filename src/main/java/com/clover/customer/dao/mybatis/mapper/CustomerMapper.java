package com.clover.customer.dao.mybatis.mapper;

import com.clover.customer.beans.domain.CrmCustomerBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/10/12.
 */
@Component
@Mapper
public interface CustomerMapper {
    CrmCustomerBean get(String id);

    void insert(CrmCustomerBean crmCustomerBean);

    void update(CrmCustomerBean crmCustomerBean);

    void delete(String id);

    List<CrmCustomerBean> search(Map<String, Object> param);

    int count(Map<String, Object> param);
}
