package com.clover.customer.controller;

import com.clover.common.beans.ApiCallResult;
import com.clover.common.beans.ConditionBean;
import com.clover.common.beans.PageBean;
import com.clover.common.util.CriteriaMapUtils;
import com.clover.common.util.StringUtils;
import com.clover.customer.beans.domain.CrmCustomerBean;
import com.clover.customer.beans.domain.CrmCustomerFeatureBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.beans.vo.CustomerVo;
import com.clover.customer.beans.vto.CustomerBean;
import com.clover.customer.beans.vto.CustomerCategoryFeaturesBean;
import com.clover.customer.beans.vto.CustomerOverviewVto;
import com.clover.customer.service.*;
import io.swagger.annotations.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.CredentialsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
@RequestMapping("/customer")
@Api(tags = {"Customer Search"}, description = "Customer Search APIs")
public class CustomerSearchController {
    protected Logger logger = LoggerFactory.getLogger(CustomerSearchController.class);

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerSearchService customerSearchService;

    @RequestMapping(value={"/search"}, method= RequestMethod.GET)
    @ApiOperation(value = "Search Customer API", response = CustomerOverviewVto.class,responseContainer =
            "List")
    public ResponseEntity<ApiCallResult> search(@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                                                @ApiParam(value = "column name")
                                                @RequestParam(value = "sortColumn", required = false, defaultValue =
                                                        "customerId") String sortColumn,
                                                @ApiParam(value = "desc or asc,default value is desc")
                                                @RequestParam(value = "sortType", required = false, defaultValue =
                                                        "desc") String sortType,
                                                @ApiParam(value = "like this - customerName::like::clover" +
                                                        ".pang$type::=::AI")
                                                @RequestParam(value = "conditionsStr", required = false, defaultValue = "") String conditionsStr){
        logger.info("invoke: " + "/Customer/search");
        ApiCallResult result = new ApiCallResult();
        try{
            List<ConditionBean> conditions = CriteriaMapUtils.commonConditionsGenerate(conditionsStr);
            PageBean<CustomerOverviewVto> customerOverviewVtoPageBean = customerSearchService.page(pageSize, pageNumber,
                    sortColumn, sortType,conditions);
            result.setContent(customerOverviewVtoPageBean);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("search Customer failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
