package com.clover.customer.service;

import com.clover.common.beans.PageBean;
import com.clover.customer.beans.domain.CrmFeatureBean;

import java.util.List;

/**
 * Created by clover on 2017/10/9.
 */
public interface FeatureService {
    CrmFeatureBean get(String id);

    CrmFeatureBean getByCode(String code);

    boolean add(CrmFeatureBean crmFeatureBean) throws Exception;

    boolean update(CrmFeatureBean crmFeatureBean) throws Exception;

    boolean insert(CrmFeatureBean crmFeatureBean) throws Exception;

    boolean delete(String id) throws Exception;

    List<CrmFeatureBean> getAll() throws Exception;

    List<CrmFeatureBean> search(String startDate, String endDate, String keyWord, int pageSize, int pageNo,
                                        String sortColumn,String sortType) throws Exception;

    List<CrmFeatureBean> getByCategory(String category) throws Exception;

    PageBean<CrmFeatureBean> page(String startDate, String endDate, String keyWord, int pageSize, int pageNo,
                                          String sortColumn,String sortType) throws Exception;

    List<CrmFeatureBean> getByCategoryId(String categoryId,List<CrmFeatureBean> allFeatures) throws Exception;
}
