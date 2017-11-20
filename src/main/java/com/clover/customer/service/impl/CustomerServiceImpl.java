package com.clover.customer.service.impl;

import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.dao.mybatis.mapper.CustomerFeatureMapper;
import com.clover.customer.dao.mybatis.mapper.CustomerMapper;
import com.clover.customer.service.CustomerFeatureService;
import com.clover.customer.service.CustomerService;
import com.clover.customer.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by clover on 2017/10/12.
 */
@Component
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    protected Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerFeatureMapper customerFeatureMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerFeatureService customerFeatureService;
    
    @Override
    public CrmCustomerBean get(String id) {
        try{
            return customerMapper.get(id);
        }catch (Exception e){
            logger.error("failed on get customer : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean add(CrmCustomerBean crmCustomerBean) throws Exception {
        try{
            if (StringUtils.isEmpty(crmCustomerBean.getId())){
                crmCustomerBean.setId(IDUtils.generateID());
            }

            customerMapper.insert(crmCustomerBean);
            return true;
        }catch (Exception e){
            logger.error("faild on add new customer : " + e.getMessage());
            throw new Exception("Add customer exception : " + e.getMessage());
        }
    }

    @Override
    public boolean update(CrmCustomerBean crmCustomerBean) throws Exception {
        try{
            customerMapper.update(crmCustomerBean);
            return true;
        }catch (Exception e){
            throw new Exception("Update customer exception : " + e.getMessage());
        }
    }

    @Override
    public boolean insert(CrmCustomerBean crmCustomerBean) throws Exception {
        try{
            if (StringUtils.isEmpty(crmCustomerBean.getId())){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                crmCustomerBean.setId(uuid);
            }

            customerMapper.insert(crmCustomerBean);
            return true;
        }catch (Exception e){
            logger.error("faild on add new customer : " + e.getMessage());
            throw new Exception("Add customer exception : " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try{
            CrmCustomerBean crmCustomerBean = get(id);
            if(null != crmCustomerBean){
                customerMapper.delete(id);
                customerFeatureService.deleteByCustomerId(crmCustomerBean.getId());
                return true;
            }else{
                throw new Exception("Can not found out old customer!");
            }
        }catch (Exception e){
            throw new Exception("Delete customer exception : " + e.getMessage());
        }
    }
}
