package com.clover.customer.service.impl;

import com.clover.common.beans.ConditionBean;
import com.clover.common.beans.PageBean;
import com.clover.common.util.StringUtils;
import com.clover.customer.annotation.CustomerFeatureMapping;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.vto.CustomerOverviewVto;
import com.clover.customer.dao.mybatis.mapper.CustomerSearchMapper;
import com.clover.customer.service.CustomerSearchService;
import com.clover.customer.service.FeatureService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/11/1.
 */
@Component
@Service("customerSearchService")
public class CustomerSearchServiceImpl implements CustomerSearchService{
    protected Logger logger = LoggerFactory.getLogger(CustomerSearchServiceImpl.class);

    @Autowired
    private CustomerSearchMapper customerSearchMapper;

    @Autowired
    private FeatureService featureService;

    @Override
    public List<CustomerOverviewVto> getAll() throws Exception{
        return null;
    }

    @Override
    public PageBean<CustomerOverviewVto> page(int pageSize, int pageNo, String sortColumn, String sortType,
                                              List<ConditionBean> conditions) throws Exception {
        try{
            Map<String, Object> criteriaMap = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(sortColumn)){
                criteriaMap.put("sortColumn", sortColumn);
            }
            if(StringUtils.isNotEmpty(sortType)){
                criteriaMap.put("sortType", sortType);
            }
            criteriaMap.put("pageSize", pageSize);
            criteriaMap.put("pageNo", pageNo);

            pageSize = (pageSize <= 0 ? 25 : pageSize);
            int start = (pageNo - 1) * pageSize;
            int end = start + pageSize - 1;
            criteriaMap.put("startRowNumber", start);
            criteriaMap.put("endRowNumber", end);

            //criteriaMap.put("returnObjectType",CustomerOverviewVto.class.getName());

            List<CrmFeatureBean> allFeatures = featureService.getAll();

            Map<String, Object> features = new HashMap<String, Object>();

            generateFeaturesMap(features, allFeatures, CustomerOverviewVto.class);


            criteriaMap.put("features",features);
            if(null != conditions && conditions.size() > 0){
                criteriaMap.put("conditions",conditions);
            }

            PageBean<CustomerOverviewVto> customerOverviewVtoPageBean = new PageBean<CustomerOverviewVto>();
            int totalRecords = customerSearchMapper.count(criteriaMap);
            customerOverviewVtoPageBean.setTotalSize(totalRecords);
            int numOfPages = Double.valueOf(Math.ceil((1.0 * totalRecords) / pageSize)).intValue();
            customerOverviewVtoPageBean.setTotalPageNum(numOfPages);

            List<CustomerOverviewVto> customerOverviewVtos = customerSearchMapper.search(criteriaMap);
            if(CollectionUtils.isEmpty(customerOverviewVtos)){
                customerOverviewVtos = Collections.emptyList();
            }

            customerOverviewVtoPageBean.setPageItems(customerOverviewVtos);
            return customerOverviewVtoPageBean;
        }catch (Exception e){
            throw new Exception("Search customer page function exception : " + e.getMessage());
        }
    }

    public void generateFeaturesMap(Map<String, Object> features,List<CrmFeatureBean> allFeatures,Class<?> c){
        for (Field f : c.getDeclaredFields()){
            Annotation ano = f.getAnnotation(CustomerFeatureMapping.class);

            if(ano != null){
                String fieldName = ((CustomerFeatureMapping) ano).fieldName();
                String targetField = ((CustomerFeatureMapping) ano).targetField();
                if(StringUtils.isEmpty(targetField)){
                    targetField = f.getName();
                }
                String featureId = fieldName;
                for(CrmFeatureBean feature : allFeatures){
                    if(feature.getFeatureCode().equals(fieldName)){
                        featureId = feature.getId();
                        break;
                    }
                }
                //System.out.println(targetField + ":" + featureId);
                features.put(targetField,featureId);
            }
        }
    }
}
