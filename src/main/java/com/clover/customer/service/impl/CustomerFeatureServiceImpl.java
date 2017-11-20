package com.clover.customer.service.impl;

import com.clover.common.util.CriteriaMapUtils;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.dao.mybatis.mapper.CustomerFeatureMapper;
import com.clover.customer.service.CustomerFeatureService;
import com.clover.customer.service.FeatureCategoryService;
import com.clover.customer.service.FeatureService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class CustomerFeatureServiceImpl implements CustomerFeatureService{
    protected Logger logger = LoggerFactory.getLogger(CustomerFeatureServiceImpl.class);

    @Autowired
    private CustomerFeatureMapper customerFeatureMapper;

    @Autowired
    private FeatureCategoryService featureCategoryService;

    @Autowired
    private FeatureService featureService;

    @Override
    public CrmCustomerFeatureBean get(String id) {
        try{
            return customerFeatureMapper.get(id);
        }catch (Exception e){
            logger.error("failed on get customer feture : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean add(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception {
        try{
            if (StringUtils.isEmpty(crmCustomerFeatureBean.getId())){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmCustomerFeatureBean.setId(uuid);
            }

            customerFeatureMapper.insert(crmCustomerFeatureBean);
            return true;
        }catch (Exception e){
            logger.error("faild on add new customer feature : " + e.getMessage());
            throw new Exception("Add customer feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean update(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception {
        try{
            customerFeatureMapper.update(crmCustomerFeatureBean);
            return true;
        }catch (Exception e){
            throw new Exception("Update customer feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean insert(CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception {
        try{
            if (StringUtils.isEmpty(crmCustomerFeatureBean.getId())){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmCustomerFeatureBean.setId(uuid);
            }

            customerFeatureMapper.insert(crmCustomerFeatureBean);
            return true;
        }catch (Exception e){
            logger.error("faild on insert new customer feature : " + e.getMessage());
            throw new Exception("Insert customer feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try{
            CrmCustomerFeatureBean crmCustomerFeatureBean = get(id);
            if(null != crmCustomerFeatureBean){
                customerFeatureMapper.delete(id);
                return true;
            }else{
                throw new Exception("Can not found out old customer feature!");
            }
        }catch (Exception e){
            throw new Exception("Delete customer feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean deleteByCustomerId(String customerId) throws Exception {
        try{
            customerFeatureMapper.deleteByCustomerId(customerId);
            return true;
        }catch (Exception e){
            throw new Exception("Delete customer feature by customerId exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmCustomerFeatureBean> getByCustomerId(String customerId) throws Exception {
        try{
            Map<String, Object> criteriaMap = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(customerId)){
                criteriaMap.put("customerId", customerId);
            }

            return customerFeatureMapper.search(criteriaMap);
        }catch (Exception e){
            throw new Exception("get customer feature by customerId exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmCustomerFeatureBean> getByCustomerIdAndCategoryId(String customerId, String categoryId) throws Exception {
        try{
            List<CrmCustomerFeatureBean> crmCustomerFeatureBeanList = getByCustomerId(customerId);
            //List<CrmFeatureCategoryBean> crmFeatureCategoryBeans = featureCategoryService.getAll();
            List<CrmFeatureBean> crmFeatureBeans = featureService.getByCategory(categoryId);

            List<CrmCustomerFeatureBean> customerFeatureBeans = new ArrayList<CrmCustomerFeatureBean>();

            if(CollectionUtils.isNotEmpty(crmCustomerFeatureBeanList) && CollectionUtils.isNotEmpty(customerFeatureBeans)){
                for(CrmFeatureBean feature : crmFeatureBeans){
                    for(CrmCustomerFeatureBean customerFeature : crmCustomerFeatureBeanList){
                        if(customerFeature.getCustomerFeatureId().equals(feature.getId())){
                            customerFeatureBeans.add(customerFeature);
                            continue;
                        }
                    }
                }
            }

            return customerFeatureBeans;
        }catch (Exception e){
            throw new Exception("get customer feature by customerId exception : " + e.getMessage());
        }
    }

    @Override
    public CrmCustomerFeatureBean getByCustomerIdAndFeatureId(String customerId, String customerFeatureId) throws Exception {
        try{
            Map<String, Object> criteriaMap = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(customerId)){
                criteriaMap.put("customerId", customerId);
            }
            if(StringUtils.isNotEmpty(customerFeatureId)){
                criteriaMap.put("customerFeatureId", customerFeatureId);
            }

            List<CrmCustomerFeatureBean> featureBeans = customerFeatureMapper.search(criteriaMap);
            if(CollectionUtils.isNotEmpty(featureBeans)){
                return featureBeans.get(0);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new Exception("get customer feature by customerId exception : " + e.getMessage());
        }
    }
}
