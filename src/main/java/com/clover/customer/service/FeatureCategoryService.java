package com.clover.customer.service;

import com.clover.common.beans.PageBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;

import java.util.List;

/**
 * Created by clover on 2017/10/9.
 */
public interface FeatureCategoryService {
    CrmFeatureCategoryBean get(String id);

    CrmFeatureCategoryBean getByCode(String code);

    boolean add(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception;

    boolean update(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception;

    boolean insert(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception;

    boolean delete(String id) throws Exception;

    List<CrmFeatureCategoryBean> getAll() throws Exception;

    List<CrmFeatureCategoryBean> search(String startDate, String endDate, String keyWord, int pageSize, int pageNo,
                                        String sortColumn,String sortType) throws Exception;

    PageBean<CrmFeatureCategoryBean> page(String startDate, String endDate, String keyWord, int pageSize, int pageNo,
                                          String sortColumn,String sortType) throws Exception;
}
