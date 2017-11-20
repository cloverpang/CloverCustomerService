package com.clover.customer.service.impl;

import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vto.CustomerCategoryFeaturesBean;
import com.clover.customer.dao.mybatis.mapper.CustomerFeatureMapper;
import com.clover.customer.dao.mybatis.mapper.CustomerMapper;
import com.clover.customer.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("customerLegacyService")
public class CustomerLegacyServiceImpl implements CustomerLegacyService{
    protected Logger logger = LoggerFactory.getLogger(CustomerLegacyServiceImpl.class);

    @Autowired
    private CustomerFeatureMapper customerFeatureMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerFeatureService customerFeatureService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FeatureCategoryService featureCategoryService;

    @Autowired
    private FeatureService featureService;

    @Override
    public CustomerBean getCustomer(String customerId) throws Exception {
        try{
            CustomerBean customerBean = new CustomerBean();
            CrmCustomerBean crmCustomerBean = customerService.get(customerId);

            if(null != crmCustomerBean){
                customerBean.setCustomerId(crmCustomerBean.getId());
                customerBean.setCustomerName(crmCustomerBean.getCustomerName());

                List<CrmCustomerFeatureBean> crmCustomerFeatureBeanList = customerFeatureService.getByCustomerId(customerId);
                List<CrmFeatureBean> allFeatures = featureService.getAll();
                List<CrmCustomerFeatureBean> customerFullFeatures = getCustomerFullFeatures(customerId, allFeatures,
                        crmCustomerFeatureBeanList);
                customerBean.setCustomerFeatures(customerFullFeatures);

                Map<String,List<CrmCustomerFeatureBean>> customerAllCategoryFeatures = new HashMap<String,
                        List<CrmCustomerFeatureBean>>();
                List<CrmFeatureCategoryBean> categoryList = featureCategoryService.getAll();
                if(CollectionUtils.isNotEmpty(categoryList)){
                    for(CrmFeatureCategoryBean category : categoryList){
                        List<CrmCustomerFeatureBean> categoryFeatures = getCustomerCategoryFeatures(allFeatures,
                                customerFullFeatures,category.getId(),customerId);
                        customerAllCategoryFeatures.put(category.getCustomerFeatureCategory(),categoryFeatures);
                    }
                }
                customerBean.setCustomerCategoryFeatures(customerAllCategoryFeatures);
            }

            return customerBean;
        }catch (Exception e){
            logger.error("failed on get customer : " + e.getMessage());
            throw new Exception("Failed on get customer : " + e.getMessage());
        }
    }

    @Override
    public CrmCustomerFeatureBean getCustomerFeature(String customerId, String customerFeatureId) throws Exception {
        return customerFeatureService.getByCustomerIdAndFeatureId(customerId,customerFeatureId);
    }

    @Override
    public boolean saveCustomerFeature(String customerId, CrmCustomerFeatureBean crmCustomerFeatureBean) throws Exception {
        try{
            if(StringUtils.isEmpty(crmCustomerFeatureBean.getCustomerId())){
                crmCustomerFeatureBean.setCustomerId(customerId);
            }

            CrmCustomerFeatureBean originCustomerFeature = customerFeatureService.getByCustomerIdAndFeatureId
                    (customerId,crmCustomerFeatureBean.getCustomerFeatureId());
            boolean result = false;
            if(null != originCustomerFeature){
                result = customerFeatureService.update(crmCustomerFeatureBean);
            }else{
                result = customerFeatureService.add(crmCustomerFeatureBean);
            }

            return result;
        }catch(Exception e){
            logger.error("failed on save Customer Feature " + crmCustomerFeatureBean.getCustomerId() +
                    crmCustomerFeatureBean.getCustomerFeatureName() + "  : "
                    + e.getMessage());
            throw new Exception("failed on save Customer Feature : " + e.getMessage());
        }
    }

    @Override
    public CustomerCategoryFeaturesBean getCustomerCategoryFeatures(String customerId, String categoryId) throws Exception {
        try{
            CustomerCategoryFeaturesBean customerCategoryFeature = new CustomerCategoryFeaturesBean();
            CrmFeatureCategoryBean crmFeatureCategory = featureCategoryService.get(categoryId);
            if(null != crmFeatureCategory){
                customerCategoryFeature.setFeatureCategoryId(crmFeatureCategory.getId());
                customerCategoryFeature.setFeatureCategoryName(crmFeatureCategory.getCustomerFeatureCategory());

                CrmCustomerBean crmCustomer = customerService.get(customerId);
                customerCategoryFeature.setCustomerName(crmCustomer.getCustomerName());

                List<CrmCustomerFeatureBean> crmCustomerFeatureBeanList = customerFeatureService.getByCustomerId(customerId);
                List<CrmFeatureBean> allFeatures = featureService.getAll();
                List<CrmCustomerFeatureBean> customerFullFeatures = getCustomerFullFeatures(customerId, allFeatures,
                        crmCustomerFeatureBeanList);

                List<CrmCustomerFeatureBean> categoryFeatures = getCustomerCategoryFeatures(allFeatures,
                        customerFullFeatures,categoryId,customerId);
                customerCategoryFeature.setCustomerCategoryFeatures(categoryFeatures);
            }
            return customerCategoryFeature;
        }catch (Exception e){
            logger.error("failed on get Customer category Feature : " + e.getMessage());
            throw new Exception("failed on get Customer category Feature : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean saveCustomerFeatures(String customerId,List<CrmCustomerFeatureBean> crmCustomerFeatures) throws Exception {
        try{
            boolean result = true;
            if(CollectionUtils.isNotEmpty(crmCustomerFeatures)){
                for(CrmCustomerFeatureBean customerFeature : crmCustomerFeatures){
                    result = saveCustomerFeature(customerId,customerFeature);
                }
            }
            return result;
        }catch (Exception e){
            logger.error("failed on save Customer Features : " + e.getMessage());
            throw new Exception("failed on save Customer Features : " + e.getMessage());
        }
    }

    @Override
    public boolean saveCustomerCategoryFeatures(String customerId, CustomerCategoryFeaturesBean customerCategoryFeaturesBean) throws Exception {
        try{
            boolean result = true;
            result = saveCustomerFeatures(customerId,customerCategoryFeaturesBean.getCustomerCategoryFeatures());
            return result;
        }catch (Exception e){
            logger.error("failed on save Customer Category Features : " + e.getMessage());
            throw new Exception("failed on save Customer Category Features : " + e.getMessage());
        }
    }

    public List<CrmCustomerFeatureBean> getCustomerFullFeatures(String customerId) throws Exception{

        List<CrmCustomerFeatureBean> crmCustomerFeatureBeanList = customerFeatureService.getByCustomerId(customerId);
        List<CrmFeatureBean> allFeatures = featureService.getAll();
        return getCustomerFullFeatures(customerId,allFeatures,crmCustomerFeatureBeanList);
    }

    public List<CrmCustomerFeatureBean> getCustomerFullFeatures(String customerId,List<CrmFeatureBean> allFeatures,
                                                                List<CrmCustomerFeatureBean> crmCustomerFeatureBeanList) throws Exception{
        List<CrmCustomerFeatureBean> customerFullFeatures = new ArrayList<CrmCustomerFeatureBean>();
        if(CollectionUtils.isNotEmpty(allFeatures)){
            for(CrmFeatureBean feature : allFeatures){
                CrmCustomerFeatureBean customerFeature = new CrmCustomerFeatureBean();
                customerFeature.setCustomerId(customerId);
                customerFeature.setCustomerFeatureId(feature.getId());
                customerFeature.setCustomerFeatureName(feature.getFeatureName());
                if(CollectionUtils.isNotEmpty(crmCustomerFeatureBeanList)){
                    for(CrmCustomerFeatureBean crmCustomerFeature : crmCustomerFeatureBeanList){
                        if(crmCustomerFeature.getCustomerFeatureId().equals(feature.getId())){
                            customerFeature.setCustomerFeatureValue(crmCustomerFeature.getCustomerFeatureValue());
                            customerFeature.setId(crmCustomerFeature.getId());

                            continue;
                        }
                    }
                }
                customerFullFeatures.add(customerFeature);
            }
        }

        return customerFullFeatures;
    }

    public List<CrmCustomerFeatureBean> getCustomerCategoryFeatures(List<CrmFeatureBean> allFeatures,
                                                                    List<CrmCustomerFeatureBean> customerFullFeatures,
                                                                    String categoryId,String customerId) throws Exception{
        List<CrmCustomerFeatureBean> customerCategoryFeatures = new ArrayList<CrmCustomerFeatureBean>();

        List<CrmFeatureBean> categoryFeatures = featureService.getByCategoryId(categoryId, allFeatures);

        if(CollectionUtils.isNotEmpty(categoryFeatures)){
            for(CrmFeatureBean feature : categoryFeatures){
                CrmCustomerFeatureBean customerFeature = new CrmCustomerFeatureBean();
                customerFeature.setCustomerId(customerId);
                customerFeature.setCustomerFeatureId(feature.getId());
                customerFeature.setCustomerFeatureName(feature.getFeatureName());
                for(CrmCustomerFeatureBean crmCustomerFeature : customerFullFeatures){
                    if(crmCustomerFeature.getCustomerFeatureId().equals(feature.getId())){
                        customerFeature.setCustomerFeatureValue(crmCustomerFeature.getCustomerFeatureValue());
                        customerFeature.setId(crmCustomerFeature.getId());

                        continue;
                    }
                }
                customerCategoryFeatures.add(customerFeature);
            }
        }
        return customerCategoryFeatures;
    }
}
