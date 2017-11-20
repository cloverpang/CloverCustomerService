package com.clover.customer.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import com.clover.customer.beans.domain.*;

/**
 * Component注解不添加也没事，只是不加service那边引入LearnMapper会有错误提示，但不影响
 */
@Component
@Mapper
public interface FeatureCategoryMapper {
    CrmFeatureCategoryBean get(String id);

    CrmFeatureCategoryBean getByCode(String code);

    void insert(CrmFeatureCategoryBean crmFeatureCategoryBean);

    void update(CrmFeatureCategoryBean crmFeatureCategoryBean);

    void delete(String id);

    List<CrmFeatureCategoryBean> search(Map<String, Object> param);

    int count(Map<String, Object> param);

    List<CrmFeatureCategoryBean> searchAll();

    int countAll();
}
