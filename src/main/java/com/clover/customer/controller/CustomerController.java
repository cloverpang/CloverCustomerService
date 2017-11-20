package com.clover.customer.controller;

import com.clover.common.beans.ApiCallResult;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vto.CustomerCategoryFeaturesBean;
import com.clover.customer.service.CustomerLegacyService;
import com.clover.customer.service.CustomerService;
import com.clover.customer.service.FeatureService;
import io.swagger.annotations.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@RequestMapping("/customer")
@Api(tags = {"Customer"}, description = "Customer APIs")
public class CustomerController {
    protected Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    @Qualifier("customerLegacyService")
    private CustomerLegacyService customerLegacyService;

    @Autowired
    @Qualifier("customerService")
    private CustomerService customerService;

    @ApiOperation(value="get customer basic", notes="",response = CrmCustomerBean.class)
    @RequestMapping(value={"/basic/{id}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getBasicCustomer(@PathVariable("id") String id){
        logger.info("invoke: " + "/Customer/basic/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            CrmCustomerBean crmCustomer = customerService.get(id);
            if(null != crmCustomer){
                result.setContent(crmCustomer);
            }else{
                result.setMessage("can not found out this customer ");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer by id failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="get customer", notes="",response = CustomerBean.class)
    @RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getFullCustomer(@PathVariable("id") String id){
        logger.info("invoke: " + "/Customer/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            CustomerBean customer = customerLegacyService.getCustomer(id);
            result.setContent(customer);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer by id failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="update customer name ", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crmCustomerBean", value = "", required = true, dataType = "CrmCustomerBean")
    })
    @RequestMapping(value={""}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> update(@RequestBody CrmCustomerBean crmCustomerBean){
        logger.info("invoke: " + "/Customer/");
        ApiCallResult result = new ApiCallResult();
        try{
            CrmCustomerBean crmCustomer = customerService.get(crmCustomerBean.getId());
            if (null != crmCustomer) {
                boolean update = customerService.update(crmCustomerBean);
                if(update){
                    result.setContent(true);
                }else {
                    result.setContent(false);
                    result.setMessage("can not update this customer ");
                }
            } else {
                result.setContent(false);
                result.setMessage("can not found out old customer");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("update customer  failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value="add customer basic ", notes="",response = boolean.class)
    @ApiImplicitParam(name = "crmCustomerBean", value = "", required = true, dataType = "CrmCustomerBean")
    @RequestMapping(value={"/add"}, method= RequestMethod.POST)
    public ResponseEntity<ApiCallResult> add(@RequestBody CrmCustomerBean
                                                     crmCustomerBean){
        logger.info("invoke: " + "/Customer");
        ApiCallResult result = new ApiCallResult();
        try{
            boolean add = customerService.add(crmCustomerBean);
            if (add) {
                result.setContent(true);
            } else {
                result.setContent(false);
                result.setMessage("can not save customer ");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("add customer  failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value="get customer category features", notes="",response = CustomerCategoryFeaturesBean.class)
    @RequestMapping(value={"/categoryFeatures/{customerId}/{categoryId}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getCustomerCategoryFeatures(@PathVariable("customerId") String customerId,
                                                                     @PathVariable("categoryId") String categoryId){
        logger.info("invoke: " + "/Customer/categoryFeatures/" + customerId + "/" + categoryId);
        ApiCallResult result = new ApiCallResult();
        try{
            CustomerCategoryFeaturesBean customerCategoryFeature = customerLegacyService.getCustomerCategoryFeatures
                    (customerId, categoryId);
            result.setContent(customerCategoryFeature);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer category features failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="save customer category features ", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerCategoryFeaturesBean", value = "", required = true, dataType = "CustomerCategoryFeaturesBean")
    })
    @RequestMapping(value={"/categoryFeatures/{customerId}"}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> saveCategoryFeatures(@PathVariable("customerId") String customerId,
                                                              @RequestBody CustomerCategoryFeaturesBean customerCategoryFeaturesBean){
        logger.info("invoke: " + "/Customer/categoryFeatures/" + customerId);
        ApiCallResult result = new ApiCallResult();
        try{
            boolean update = customerLegacyService.saveCustomerCategoryFeatures(customerId,
                    customerCategoryFeaturesBean);
            if(update){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not save this customer category features");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("save customer category features failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="save customer feature ", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crmCustomerFeatureBean", value = "", required = true, dataType = "CrmCustomerFeatureBean")
    })
    @RequestMapping(value={"/customerFeature/{customerId}"}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> saveCustomerFeature(@PathVariable("customerId") String customerId,
                                                              @RequestBody CrmCustomerFeatureBean crmCustomerFeatureBean){
        logger.info("invoke: " + "/Customer/customerFeature/" + customerId);
        ApiCallResult result = new ApiCallResult();
        try{
            boolean update = customerLegacyService.saveCustomerFeature(customerId, crmCustomerFeatureBean);
            if(update){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not save this customer feature");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("save customer feature failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
