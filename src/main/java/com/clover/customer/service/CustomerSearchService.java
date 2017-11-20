package com.clover.customer.service;

import com.clover.common.beans.ConditionBean;
import com.clover.common.beans.PageBean;
import com.clover.customer.beans.vto.CustomerOverviewVto;

import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/11/1.
 */
public interface CustomerSearchService {
    List<CustomerOverviewVto> getAll() throws Exception;

    PageBean<CustomerOverviewVto> page(int pageSize, int pageNo,
                                  String sortColumn,String sortType,List<ConditionBean> conditions) throws Exception;
}
