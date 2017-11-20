package com.clover.customer.service.impl;

import com.clover.common.util.CriteriaMapUtils;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.dao.mybatis.mapper.FeatureCategoryMapper;
import com.clover.customer.service.FeatureCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import com.clover.common.beans.PageBean;

/**
 * Created by clover on 2017/10/9.
 */
@Component
@Service
public class FeatureCategoryServiceImpl implements FeatureCategoryService{
    protected Logger logger = LoggerFactory.getLogger(FeatureCategoryServiceImpl.class);

    @Autowired
    private FeatureCategoryMapper featureCategoryMapper;

    @Override
    public CrmFeatureCategoryBean get(String id) {
        try{
            logger.info("get feature category : " + id);
            return featureCategoryMapper.get(id);
        }catch (Exception e){
            logger.error("failed on get feature category : " + e.getMessage());
            return null;
        }
    }

    @Override
    public CrmFeatureCategoryBean getByCode(String code) {
        try{
            logger.info("get feature category : " + code);
            return featureCategoryMapper.getByCode(code);
        }catch (Exception e){
            logger.error("failed on get feature category : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean add(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception{
        try{
            List<CrmFeatureCategoryBean> originFeatureCategories = search(null,null,crmFeatureCategoryBean
                    .getCustomerFeatureCategory(),10,10,null,null);

            if(!CollectionUtils.isEmpty(originFeatureCategories)){
                logger.error("The same name category have existing!");
                throw new Exception("the same name category have existing!");
            }

            if (StringUtils.isEmpty(crmFeatureCategoryBean.getId())){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmFeatureCategoryBean.setId(uuid);
            }

            featureCategoryMapper.insert(crmFeatureCategoryBean);
            return true;
        }catch (Exception e){
            logger.error("faild on add new feature category : " + e.getMessage());
            throw new Exception("Add feature category exception : " + e.getMessage());
        }
    }

    @Override
    public boolean update(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception{
        try{
            featureCategoryMapper.update(crmFeatureCategoryBean);
            return true;
        }catch (Exception e){
            throw new Exception("Update feature category exception : " + e.getMessage());
        }
    }

    @Override
    public boolean insert(CrmFeatureCategoryBean crmFeatureCategoryBean) throws Exception{
        try{
            if(null == crmFeatureCategoryBean.getId()){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmFeatureCategoryBean.setId(uuid);
            }
            featureCategoryMapper.insert(crmFeatureCategoryBean);
            return true;
        }catch (Exception e){
            throw new Exception("Insert feature category exception : " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) throws Exception{
        try{
            CrmFeatureCategoryBean crmFeatureCategory = get(id);
            if(null != crmFeatureCategory){
                featureCategoryMapper.delete(id);
                return true;
            }else{
                throw new Exception("Can not found out old category!");
            }
        }catch (Exception e){
            throw new Exception("Delete feature category exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureCategoryBean> getAll() throws Exception{
        try{
            return featureCategoryMapper.searchAll();
        }catch (Exception e){
            throw new Exception("Get all feature category exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureCategoryBean> search(String startDate, String endDate, String keyWord, int pageSize, int
            pageNo, String sortColumn, String sortType) throws Exception{
        try{
            Map<String, Object> criteriaMap = CriteriaMapUtils.commonCriteriaMapGenerate(startDate,endDate,keyWord,
                    pageSize,pageNo,sortColumn,sortType);

            return featureCategoryMapper.search(criteriaMap);
        }catch (Exception e){
            throw new Exception("Search feature category exception : " + e.getMessage());
        }
    }

    @Override
    public PageBean<CrmFeatureCategoryBean> page(String startDate, String endDate, String keyWord, int pageSize, int
            pageNo, String sortColumn, String sortType) throws Exception {
        try{
            Map<String, Object> criteriaMap = CriteriaMapUtils.commonCriteriaMapGenerate(startDate,endDate,keyWord,
                    pageSize,pageNo,sortColumn,sortType);

            PageBean<CrmFeatureCategoryBean> featureCategoryBeanPageBean = new PageBean<CrmFeatureCategoryBean>();
            int totalRecords = featureCategoryMapper.count(criteriaMap);

            featureCategoryBeanPageBean.setTotalSize(totalRecords);
            featureCategoryBeanPageBean.setPageNo(pageNo);
            int numOfPages = Double.valueOf(Math.ceil((1.0 * totalRecords) / pageSize)).intValue();
            featureCategoryBeanPageBean.setTotalPageNum(numOfPages);

            List<CrmFeatureCategoryBean> crmFeatureCategoryBeans = featureCategoryMapper.search(criteriaMap);
            if(CollectionUtils.isEmpty(crmFeatureCategoryBeans)){
                crmFeatureCategoryBeans = Collections.emptyList();
            }

            featureCategoryBeanPageBean.setPageItems(crmFeatureCategoryBeans);
            return featureCategoryBeanPageBean;
        }catch (Exception e){
            throw new Exception("Search feature category exception : " + e.getMessage());
        }
    }


}
