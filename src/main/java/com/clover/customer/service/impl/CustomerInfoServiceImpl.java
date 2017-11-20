package com.clover.customer.service.impl;

import com.clover.common.consts.Consts;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.vo.CustomerOrderBookingVo;
import com.clover.customer.beans.vo.CustomerOverviewVo;
import com.clover.customer.beans.vo.CustomerVo;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.service.*;
import com.clover.customer.util.CustomerFieldUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by clover on 2017/10/13.
 */
@Component
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService{
    protected Logger logger = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

    @Autowired
    CustomerLegacyService customerLegacyService;

    @Autowired
    CustomerService customerService;

    @Autowired
    FeatureService featureService;

    @Autowired
    CustomerFeatureService customerFeatureService;

    @Override
    public CustomerVo getCustomer(String customerId) throws Exception {
        try{
            CustomerBean customerBean = customerLegacyService.getCustomer(customerId);
            CustomerVo customerVo = new CustomerVo();

            if(null != customerBean && StringUtils.isNotEmpty(customerBean.getCustomerId())){
                customerVo.setCustomerId(customerBean.getCustomerId());
                customerVo.setCustomerName(customerBean.getCustomerName());
                CustomerFieldUtils.setCustomer(customerVo,customerBean.getCustomerFeatures());
            }
            return customerVo;
        }catch (Exception e){
            throw new Exception("Get customer failed : " + e.getMessage());
        }
    }

    @Override
    public boolean saveCustomer(CustomerVo customerVo) throws Exception {
        try{
            String customerId = "";
            if(StringUtils.isNotEmpty(customerVo.getCustomerId())){
                CrmCustomerBean crmCustomer = customerService.get(customerVo.getCustomerId());
                if(null != crmCustomer){
                    customerId = crmCustomer.getId();
                }else{
                    throw new Exception("The customer is not correct customer!");
                }
            }else{
                CrmCustomerBean newCrmCustomer = new CrmCustomerBean();
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                newCrmCustomer.setId(uuid);
                newCrmCustomer.setCustomerName(customerVo.getCustomerName());
                boolean addNewCustomer = customerService.add(newCrmCustomer);
                customerId = uuid;
                if(!addNewCustomer){
                    throw new Exception("Add new customer field!");
                }
            }

            List<CrmFeatureBean> allFeatures = featureService.getAll();
            List<CrmCustomerFeatureBean> customerFeatures = new ArrayList<CrmCustomerFeatureBean>();

            CustomerFieldUtils.mappingCustomerFeatures(customerId, allFeatures, customerFeatures, customerVo);

            return customerLegacyService.saveCustomerFeatures(customerId,customerFeatures);
        }catch (Exception e){
            throw new Exception("Save customer (full object) failed : " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getCustomerFeatures(String customerId) throws Exception {
        try{
            Map<String,String> customerFeatures = new HashMap<String,String>();
            //CustomerBean customer = customerLegacyService.getCustomer(customerId);
            List<CrmCustomerFeatureBean> customerFeatureBeanList = customerFeatureService.getByCustomerId(customerId);
            if(CollectionUtils.isNotEmpty(customerFeatureBeanList)){
                for(CrmCustomerFeatureBean feature : customerFeatureBeanList){
                    customerFeatures.put(feature.getCustomerFeatureName(),feature.getCustomerFeatureValue());
                }
            }
            return customerFeatures;
        }catch (Exception e){
            throw new Exception("Get CustomerFeatures failed : " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getCustomerFeatures(String customerId, String featureNames) throws Exception {
        try{
            if(StringUtils.isEmpty(featureNames)){
                throw new Exception("Get CustomerFeatures by features name failed : featureNames is empty");
            }

            Map<String,String> customerFeatures = new HashMap<String,String>();
            //CustomerBean customer = customerLegacyService.getCustomer(customerId);

            String[] featureArray = featureNames.split(Consts.COMMA);

            List<CrmCustomerFeatureBean> customerFeatureBeanList = customerFeatureService.getByCustomerId(customerId);
            if(CollectionUtils.isNotEmpty(customerFeatureBeanList)){
                for(CrmCustomerFeatureBean feature : customerFeatureBeanList){
                    for(String featureName : featureArray){
                        if(featureName.equals(feature.getCustomerFeatureName())){
                            customerFeatures.put(feature.getCustomerFeatureName(),feature.getCustomerFeatureValue());
                            break;
                        }
                    }
                }
            }

            return customerFeatures;
        }catch (Exception e){
            throw new Exception("Get CustomerFeatures by features name failed : " + e.getMessage());
        }
    }

    @Override
    public boolean saveCustomerFeatures(String customerId, Map<String, String> customerFeatures) throws Exception {
       try{
           if(null == customerFeatures || customerFeatures.size() == 0){
               throw new Exception("Save CustomerFeatures failed : customerFeatures is null!");
           }

           boolean result = false;
           List<CrmFeatureBean> allFeatures = featureService.getAll();
           List<CrmCustomerFeatureBean> customerFeatureList = mappingCustomerFeatures(customerId,customerFeatures,allFeatures);
           result = customerLegacyService.saveCustomerFeatures(customerId,customerFeatureList);

           return result;
       }catch (Exception e){
           throw new Exception("Save CustomerFeatures failed : " + e.getMessage());
       }
    }

    private List<CrmCustomerFeatureBean> mappingCustomerFeatures(String customerId,Map<String, String>
            customerFeatures,List<CrmFeatureBean> allFeatures) throws Exception{
        if(null == customerFeatures || customerFeatures.size() == 0){
            throw new Exception("Save CustomerFeatures failed : customerFeatures is null!");
        }

        List<CrmCustomerFeatureBean> customerFeatureList = new ArrayList<CrmCustomerFeatureBean>();
        Iterator<Map.Entry<String, String>> entries = customerFeatures.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            for(CrmFeatureBean featureBean : allFeatures){
                if(featureBean.getFeatureCode().equals(entry.getKey())){
                    CrmCustomerFeatureBean crmCustomerFeatureBean = new CrmCustomerFeatureBean();
                    crmCustomerFeatureBean.setCustomerId(customerId);
                    crmCustomerFeatureBean.setCustomerFeatureId(featureBean.getId());
                    crmCustomerFeatureBean.setCustomerFeatureValue(entry.getValue());
                    crmCustomerFeatureBean.setCustomerFeatureName(featureBean.getFeatureName());
                    customerFeatureList.add(crmCustomerFeatureBean);
                    break;
                }
            }
        }

        return customerFeatureList;
    }
}
