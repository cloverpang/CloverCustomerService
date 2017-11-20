package com.clover.customer.util;

import com.clover.common.util.StringUtils;
import com.clover.customer.annotation.CustomerFeatureMapping;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.beans.vo.CustomerVo;
import org.apache.commons.collections.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clover on 2017/10/16.
 */
public class CustomerFieldUtils {

    @SuppressWarnings("rawtypes")
    public static void setCustomer(Object customerObject, List<CrmCustomerFeatureBean> customerFeatures){
        Class<?> c = customerObject.getClass();

        System.out.println("clover setCustomer : " + c.toString());
        for (Field f : c.getDeclaredFields()){
            Annotation ano = f.getAnnotation(CustomerFeatureMapping.class);

            if(ano != null){
                String fieldName = ((CustomerFeatureMapping) ano).fieldName();

                String targetField = ((CustomerFeatureMapping) ano).targetField();
                if(StringUtils.isEmpty(targetField)){
                    targetField = f.getName();
                }

                if(StringUtils.isNotEmpty(fieldName)){
                    System.out.println("clover fieldName : " + fieldName);
                    setCustomerOneField(fieldName,targetField,customerObject,customerFeatures);
                }
            }else{
                Class<?> subClass  = f.getType();
                String className = subClass.getName();

                System.out.println("clover sub className : " + className);

                if(className.startsWith("com.clover")){
                    try{
                        Method getMethod = c.getMethod(
                                "get" + StringUtils.toUpperCaseFirstOne(f.getName()),null);
                        Object subCustomerObject = getMethod.invoke(customerObject,null);

                        if(null == subCustomerObject){
                            System.out.println("subCustomerObject is null ");
                            Class<?> t = getMethod.getReturnType();

                            subCustomerObject = t.newInstance();

                            Method setMethod = c.getMethod(
                                    "set" + StringUtils.toUpperCaseFirstOne(f.getName()), t);

                            System.out.println("setMethod : " + setMethod.getName());

                            setMethod.invoke(customerObject,subCustomerObject);
                            System.out.println("tt subCustomerObject : " + subCustomerObject.getClass().toString());

                        }

                        if(null != subCustomerObject){
                            System.out.println("clover subCustomerObject : " + subCustomerObject.getClass().toString());
                            setCustomer(subCustomerObject,customerFeatures);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void setCustomerOneField(String customerFieldName,String fieldName,Object customerField,
                                           List<CrmCustomerFeatureBean> customerFeatures){
        if(CollectionUtils.isEmpty(customerFeatures)){
            return;
        }

        try{
            for(CrmCustomerFeatureBean feature : customerFeatures){
                if(feature.getCustomerFeatureName().equals(customerFieldName)){
                    Class<?> clazz = customerField.getClass();
                    String fieldValue = feature.getCustomerFeatureValue();

                    System.out.println("clover " + customerFieldName + " -- fieldValue : " + fieldValue);

                    setField(fieldName,clazz,customerField,fieldValue);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setField(String fieldName,Class<?> clazz,Object customerField,String fieldValue){
        try{
            Method getMethod = clazz.getMethod(
                    "get" + StringUtils.toUpperCaseFirstOne(fieldName), null);

            Class<?> t = getMethod.getReturnType();

            Method setMethod = clazz.getMethod(
                    "set" + StringUtils.toUpperCaseFirstOne(fieldName), t);

            if(t.getSimpleName().equalsIgnoreCase("String")){
                System.out.println("clover -- set field : " + customerField);
                setMethod.invoke(customerField, fieldValue);
            }

            if(t.getSimpleName().equalsIgnoreCase("Int")){
                System.out.println("clover -- set field : " + customerField);
                int i = Integer.parseInt(fieldValue);
                setMethod.invoke(customerField, i);
            }

            if(t.getSimpleName().equalsIgnoreCase("Integer")){
                Integer i = Integer.parseInt(fieldValue);
                setMethod.invoke(customerField, i);
            }

            if(t.getSimpleName().equalsIgnoreCase("Boolean")){
                boolean i = StringUtils.isTrue(fieldValue);
                setMethod.invoke(customerField, i);
            }

        }catch (Exception e){

        }
    }

    public static void mappingCustomerFeatures(String customerId,List<CrmFeatureBean> allFeatures,
                                               List<CrmCustomerFeatureBean> customerFeatures,Object customerObject){
        if(CollectionUtils.isEmpty(allFeatures)){
            return;
        }

        Class<?> c = customerObject.getClass();

        for (Field f : c.getDeclaredFields()){
            Annotation ano = f.getAnnotation(CustomerFeatureMapping.class);

            if(ano != null){
                String fieldName = ((CustomerFeatureMapping) ano).fieldName();

                String targetField = ((CustomerFeatureMapping) ano).targetField();
                if(StringUtils.isEmpty(targetField)){
                    targetField = f.getName();
                }

                if(StringUtils.isNotEmpty(fieldName)){
                    System.out.println("clover fieldName : " + fieldName);
                    mappingCustomerOneFeature(customerId, fieldName, targetField, customerObject, customerFeatures,
                            allFeatures);
                }
            }else{
                Class<?> subClass  = f.getType();
                String className = subClass.getName();

                System.out.println("clover sub className : " + className);

                if(className.startsWith("com.clover")){
                    try{
                        Method getMethod = c.getMethod(
                                "get" + StringUtils.toUpperCaseFirstOne(f.getName()),null);
                        Object subCustomerObject = getMethod.invoke(customerObject,null);

                        if(null == subCustomerObject){
                            System.out.println("subCustomerObject is null ");
                            Class<?> t = getMethod.getReturnType();

                            subCustomerObject = t.newInstance();

                            Method setMethod = c.getMethod(
                                    "set" + StringUtils.toUpperCaseFirstOne(f.getName()), t);

                            System.out.println("setMethod : " + setMethod.getName());

                            setMethod.invoke(customerObject,subCustomerObject);
                            System.out.println("tt subCustomerObject : " + subCustomerObject.getClass().toString());

                        }

                        if(null != subCustomerObject){
                            System.out.println("clover subCustomerObject : " + subCustomerObject.getClass().toString());
                            mappingCustomerFeatures(customerId, allFeatures, customerFeatures, subCustomerObject);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void mappingCustomerOneFeature(String customerId,String customerFieldName,String fieldName,Object
            customerField, List<CrmCustomerFeatureBean> customerFeatures,List<CrmFeatureBean> allFeatures){
        String fieldValue = "";
        try{
            Class<?> clazz = customerField.getClass();
            Method getMethod = clazz.getMethod(
                    "get" + StringUtils.toUpperCaseFirstOne(fieldName), null);
            Object fieldObject = getMethod.invoke(customerField);
            if(null != fieldObject){
                fieldValue = fieldObject.toString();
            }
        }catch (Exception e){
            System.out.println("get fieldObject value failed!" + e.getMessage());
        }

        for(CrmFeatureBean feature : allFeatures){
            if(feature.getFeatureCode().equals(customerFieldName)){
                CrmCustomerFeatureBean crmCustomerFeature = new CrmCustomerFeatureBean();
                crmCustomerFeature.setCustomerId(customerId);
                crmCustomerFeature.setCustomerFeatureName(feature.getFeatureCode());
                crmCustomerFeature.setCustomerFeatureId(feature.getId());
                crmCustomerFeature.setCustomerFeatureValue(fieldValue);
                customerFeatures.add(crmCustomerFeature);
                break;
            }
        }
    }
}
