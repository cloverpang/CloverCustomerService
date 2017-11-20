package com.clover.customer.service.impl;

import com.clover.common.util.StringUtils;
import com.clover.customer.annotation.CustomerFeatureCategoryMapping;
import com.clover.customer.annotation.CustomerFeatureMapping;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.beans.vo.CustomerVo;
import com.clover.customer.service.*;
import com.clover.customer.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Service
public class SettingServiceImpl implements SettingService{
    protected Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);

    @Autowired
    @Qualifier("customerLegacyService")
    private CustomerLegacyService customerLegacyService;

    @Autowired
    @Qualifier("customerService")
    private CustomerService customerService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureCategoryService featureCategoryService;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void featureSetting() throws Exception {
        try{
            System.out.println("start to config setting!");
            Class<?> c = CustomerVo.class;
            categorySettingForObject(c);
            featureSettingForObject(c);
            System.out.println("end of config setting!");
        }catch (Exception e){
            throw new Exception("failed in featureSetting : - " + e.getMessage());
        }
    }

    private void categorySettingForObject(Class<?> c) throws Exception{
        try{
            for (Field f : c.getDeclaredFields()){
                Annotation ano = f.getAnnotation(CustomerFeatureCategoryMapping.class);
                if(ano != null){
                    String categoryName = ((CustomerFeatureCategoryMapping) ano).categoryName();
                    String categoryCode = ((CustomerFeatureCategoryMapping) ano).categoryCode();

                    String categoryId = "";
                    CrmFeatureCategoryBean crmFeatureCategory = new CrmFeatureCategoryBean();
                    crmFeatureCategory.setCustomerFeatureCategory(categoryName);
                    crmFeatureCategory.setCustomerFeatureCategoryCode(categoryCode);
                    CrmFeatureCategoryBean currentCategoryExisting = featureCategoryService.getByCode(categoryCode);
                    if(null != currentCategoryExisting && StringUtils.isNotEmpty(currentCategoryExisting
                            .getCustomerFeatureCategoryCode())){
                        categoryId = currentCategoryExisting.getId();
                        crmFeatureCategory.setId(categoryId);
                        featureCategoryService.update(crmFeatureCategory);
                    }else{
                        categoryId = IDUtils.generateID();
                        crmFeatureCategory.setId(categoryId);
                        featureCategoryService.insert(crmFeatureCategory);
                        System.out.println("insert new category -- " + crmFeatureCategory.getCustomerFeatureCategoryCode());
                        //categoryId = "";
                    }
                }else{
                    Class<?> subClass  = f.getType();
                    //String className = subClass.getName();
                    featureSettingForObject(subClass);
                }
            }
        }catch (Exception e){
            throw new Exception("failed in categorySettingForObject : " + c.getName() + " - " + e.getMessage());
        }
    }

    private void featureSettingForObject(Class<?> c) throws Exception{
        try{
            for (Field f : c.getDeclaredFields()){
                Annotation ano = f.getAnnotation(CustomerFeatureMapping.class);
                if(ano != null){
                    String featureName = ((CustomerFeatureMapping) ano).fieldName();
                    String featureCode = ((CustomerFeatureMapping) ano).fieldName();
                    String categoryCode = ((CustomerFeatureMapping) ano).categoryCode();

                    CrmFeatureBean crmFeature = new CrmFeatureBean();

                    CrmFeatureBean currentFeatureExisting = featureService.getByCode(featureCode);
                    CrmFeatureCategoryBean currentCategoryExisting = featureCategoryService.getByCode(categoryCode);

                    String categoryId = "";
                    if(null != currentCategoryExisting && StringUtils.isNotEmpty(currentCategoryExisting
                            .getCustomerFeatureCategoryCode())){
                        categoryId = currentCategoryExisting.getId();
                    }

                    crmFeature.setFeatureCategoryId(categoryId);
                    crmFeature.setFeatureName(featureName);
                    crmFeature.setFeatureCode(featureCode);

                    String targetField = ((CustomerFeatureMapping) ano).targetField();
                    if(StringUtils.isEmpty(targetField)){
                        targetField = f.getName();
                    }
                    Method getMethod = c.getMethod(
                            "get" + StringUtils.toUpperCaseFirstOne(targetField), null);
                    Class<?> t = getMethod.getReturnType();
                    crmFeature.setFeatureType(t.getSimpleName());

                    if(null != currentFeatureExisting && StringUtils.isNotEmpty(currentFeatureExisting
                            .getFeatureCode())){
                        crmFeature.setId(currentFeatureExisting.getId());
                        featureService.update(crmFeature);
                    }else{
                        crmFeature.setId(IDUtils.generateID());
                        featureService.insert(crmFeature);
                    }
                }else{
                    Class<?> subClass  = f.getType();
                    //String className = subClass.getName();
                    featureSettingForObject(subClass);
                }
            }
        }catch (Exception e){
            throw new Exception("failed in featureSettingForObject : " + c.getName() + " - " + e.getMessage());
        }
    }
}
