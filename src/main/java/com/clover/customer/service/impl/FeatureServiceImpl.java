package com.clover.customer.service.impl;

import com.clover.common.util.CriteriaMapUtils;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.dao.mybatis.mapper.FeatureCategoryMapper;
import com.clover.customer.dao.mybatis.mapper.FeatureMapper;
import com.clover.customer.service.FeatureService;
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
public class FeatureServiceImpl implements FeatureService{
    protected Logger logger = LoggerFactory.getLogger(FeatureServiceImpl.class);
    @Autowired
    private FeatureMapper featureMapper;

    @Override
    public CrmFeatureBean get(String id) {
        try{
            logger.info("get feature : " + id);
            return featureMapper.get(id);
        }catch (Exception e){
            logger.error("failed on get feature : " + e.getMessage());
            return null;
        }
    }

    @Override
    public CrmFeatureBean getByCode(String code) {
        try{
            logger.info("get feature : " + code);
            return featureMapper.getByCode(code);
        }catch (Exception e){
            logger.error("failed on get feature : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean add(CrmFeatureBean crmFeatureBean) throws Exception {
        try{
            List<CrmFeatureBean> originFeatures = search(null,null,crmFeatureBean
                    .getFeatureCode(),10,10,null,null);

            if(!CollectionUtils.isEmpty(originFeatures)){
                logger.error("The same name feature have existing!");
                throw new Exception("the same name feature have existing!");
            }

            if (StringUtils.isEmpty(crmFeatureBean.getId())){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmFeatureBean.setId(uuid);
            }

            featureMapper.insert(crmFeatureBean);
            return true;
        }catch (Exception e){
            logger.error("faild on add new feature : " + e.getMessage());
            throw new Exception("Add feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean update(CrmFeatureBean crmFeatureBean) throws Exception {
        try{
            featureMapper.update(crmFeatureBean);
            return true;
        }catch (Exception e){
            throw new Exception("Update feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean insert(CrmFeatureBean crmFeatureBean) throws Exception {
        try{
            if(null == crmFeatureBean.getId()){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmFeatureBean.setId(uuid);
            }
            featureMapper.insert(crmFeatureBean);
            return true;
        }catch (Exception e){
            throw new Exception("Insert feature exception : " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try{
            CrmFeatureBean crmFeature = get(id);
            if(null != crmFeature){
                featureMapper.delete(id);
                return true;
            }else{
                throw new Exception("Can not found out old feature!");
            }
        }catch (Exception e){
            throw new Exception("Delete feature exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureBean> getAll() throws Exception {
        try{
            return featureMapper.searchAll();
        }catch (Exception e){
            throw new Exception("Get all feature exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureBean> search(String startDate, String endDate, String keyWord, int pageSize, int pageNo,
                                       String sortColumn, String sortType) throws Exception {
        try{
            Map<String, Object> criteriaMap = CriteriaMapUtils.commonCriteriaMapGenerate(startDate,endDate,keyWord,
                    pageSize,pageNo,sortColumn,sortType);

            return featureMapper.search(criteriaMap);
        }catch (Exception e){
            throw new Exception("Search feature exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureBean> getByCategory(String category) throws Exception {
        try{
            Map<String, Object> criteriaMap = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(category)){
                criteriaMap.put("category", category);
            }

            return featureMapper.getByCategory(criteriaMap);
        }catch (Exception e){
            throw new Exception("get feature by category exception : " + e.getMessage());
        }
    }

    @Override
    public PageBean<CrmFeatureBean> page(String startDate, String endDate, String keyWord, int pageSize, int pageNo, String sortColumn, String sortType) throws Exception {
        try{
            Map<String, Object> criteriaMap = CriteriaMapUtils.commonCriteriaMapGenerate(startDate,endDate,keyWord,
                    pageSize,pageNo,sortColumn,sortType);

            PageBean<CrmFeatureBean> featureCategoryBeanPageBean = new PageBean<CrmFeatureBean>();
            int totalRecords = featureMapper.count(criteriaMap);

            featureCategoryBeanPageBean.setTotalSize(totalRecords);
            featureCategoryBeanPageBean.setPageNo(pageNo);
            int numOfPages = Double.valueOf(Math.ceil((1.0 * totalRecords) / pageSize)).intValue();
            featureCategoryBeanPageBean.setTotalPageNum(numOfPages);

            List<CrmFeatureBean> crmFeatureBeans = featureMapper.search(criteriaMap);
            if(CollectionUtils.isEmpty(crmFeatureBeans)){
                crmFeatureBeans = Collections.emptyList();
            }

            featureCategoryBeanPageBean.setPageItems(crmFeatureBeans);
            return featureCategoryBeanPageBean;
        }catch (Exception e){
            throw new Exception("Search page feature exception : " + e.getMessage());
        }
    }

    @Override
    public List<CrmFeatureBean> getByCategoryId(String categoryId, List<CrmFeatureBean> allFeatures) throws Exception {
        List<CrmFeatureBean> categoryFeatures = new ArrayList<CrmFeatureBean>();
        if(CollectionUtils.isNotEmpty(allFeatures)){
            for(CrmFeatureBean feature : allFeatures){
                if(feature.getFeatureCategoryId().equals(categoryId)){
                    categoryFeatures.add(feature);
                    continue;
                }
            }
        }
        return categoryFeatures;
    }
}
