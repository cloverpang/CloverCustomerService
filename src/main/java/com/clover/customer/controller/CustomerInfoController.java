package com.clover.customer.controller;

import com.clover.common.beans.ApiCallResult;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.beans.vo.CustomerVo;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vto.CustomerCategoryFeaturesBean;
import com.clover.customer.service.CustomerInfoService;
import com.clover.customer.service.CustomerLegacyService;
import com.clover.customer.service.CustomerService;
import com.clover.customer.service.FeatureService;
import io.swagger.annotations.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@SpringBootApplication
@RequestMapping("/customer")
@Api(tags = {"Customer Info"}, description = "Customer Info APIs")
public class CustomerInfoController {
    protected Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    @Autowired
    private CustomerInfoService customerInfoService;

    @ApiOperation(value="get customer all info", notes="",response = CustomerVo.class)
    @RequestMapping(value={"/info/{id}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getCustomer(@PathVariable("id") String id){
        logger.info("invoke: " + "/customer/info/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            CustomerVo customerVo = customerInfoService.getCustomer(id);
            if(StringUtils.isNotEmpty(customerVo.getCustomerId())){
                result.setContent(customerVo);
            }else{
                result.setMessage("can not found out this customer ");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer vo by id failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="save customer vo", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerVo", value = "", required = true, dataType = "CustomerVo")
    })
    @RequestMapping(value={"/info"}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> saveCustomerVo(@RequestBody CustomerVo customerVo){
        logger.info("invoke: " + "/customer/info");
        ApiCallResult result = new ApiCallResult();
        try{
            boolean update = customerInfoService.saveCustomer(customerVo);
            if(update){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not save this customer vo");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("save customer vo failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="get customer features", notes="",response = Map.class)
    @RequestMapping(value={"/info/{id}/features"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getCustomerFeatures(@PathVariable("id") String id){
        logger.info("invoke: " + "/customer/info/" + id + "/features");
        ApiCallResult result = new ApiCallResult();
        try{
            Map<String, String> customerFeatures = customerInfoService.getCustomerFeatures(id);
            if(null != customerFeatures && customerFeatures.size() > 0){
                result.setContent(customerFeatures);
            }else{
                result.setMessage("can not found out this customer features ");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer features by id failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="get customer features by feature names", notes="",response = Map.class)
    @RequestMapping(value={"/info/{id}/features/names"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> getCustomerFeaturesByNames(@PathVariable("id") String id,
                                                                    @ApiParam(value = "'")
                                                                    @RequestParam(value = "featureNames", required = true, defaultValue = "") String featureNames){
        logger.info("invoke: " + "/customer/info/" + id + "/features/names?featureNames=" + featureNames);
        ApiCallResult result = new ApiCallResult();
        try{
            Map<String, String> customerFeatures = customerInfoService.getCustomerFeatures(id,featureNames);
            if(null != customerFeatures && customerFeatures.size() > 0){
                result.setContent(customerFeatures);
            }else{
                result.setMessage("can not found out this customer features ");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get customer features by id and names failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="save customer features", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerFeatures", value = "", required = true, dataType = "Map")
    })
    @RequestMapping(value={"/info/{id}/features"}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> saveCustomerFeatures(@PathVariable("id") String id,@RequestBody Map<String, String> customerFeatures){
        logger.info("invoke: " + "/customer/info/"+id + "/features");
        ApiCallResult result = new ApiCallResult();
        try{
            boolean update = customerInfoService.saveCustomerFeatures(id, customerFeatures);
            if(update){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not update this customer features");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("update customer features failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
