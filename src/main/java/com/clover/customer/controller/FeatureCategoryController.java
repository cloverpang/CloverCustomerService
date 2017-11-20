package com.clover.customer.controller;


import com.clover.common.beans.PageBean;
import com.clover.customer.beans.domain.CrmFeatureCategoryBean;
import com.clover.customer.service.FeatureCategoryService;
import io.swagger.annotations.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clover.common.beans.ApiCallResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@SpringBootApplication
@RequestMapping("/FeatureCategory")
@Api(tags = {"FeatureCategory"}, description = "FeatureCategory APIs")
public class FeatureCategoryController {
    protected Logger logger = LoggerFactory.getLogger(FeatureCategoryController.class);

    @Autowired
    private FeatureCategoryService featureCategoryService;

    @ApiOperation(value="get feature category", notes="",response = CrmFeatureCategoryBean.class)
    @RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> get(@PathVariable("id") String id){
        logger.info("invoke: " + "/FeatureCategory/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            CrmFeatureCategoryBean crmFeatureCategoryBean = featureCategoryService.get(id);
            if (null != crmFeatureCategoryBean) {
                result.setContent(crmFeatureCategoryBean);
            } else {
                result.setMessage("can not found out this feature category");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get feature category by id failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="update feature category", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crmFeatureCategoryBean", value = "", required = true, dataType = "CrmFeatureCategoryBean")
    })
    @RequestMapping(value={""}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> update(@RequestBody CrmFeatureCategoryBean crmFeatureCategoryBean){
        logger.info("invoke: " + "/FeatureCategory/");
        ApiCallResult result = new ApiCallResult();
        try{
            CrmFeatureCategoryBean crmFeatureCategory = featureCategoryService.get(crmFeatureCategoryBean.getId());
            if (null != crmFeatureCategory) {
                boolean update = featureCategoryService.update(crmFeatureCategoryBean);
                if(update){
                    result.setContent(true);
                }else {
                    result.setContent(false);
                    result.setMessage("can not update this feature category");
                }
            } else {
                result.setContent(false);
                result.setMessage("can not found out old category");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("update feature category failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="add feature category", notes="",response = boolean.class)
    @ApiImplicitParam(name = "crmFeatureCategoryBean", value = "", required = true, dataType = "CrmFeatureCategoryBean")
    @RequestMapping(value={"/add"}, method= RequestMethod.POST)
    public ResponseEntity<ApiCallResult> add(@RequestBody CrmFeatureCategoryBean
            crmFeatureCategoryBean){
        logger.info("invoke: " + "/FeatureCategory");
        ApiCallResult result = new ApiCallResult();
        try{
            boolean add = featureCategoryService.add(crmFeatureCategoryBean);
            if (add) {
                result.setContent(true);
            } else {
                result.setContent(false);
                result.setMessage("can not save feature category");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("add feature category failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value="update feature category", notes="",response = boolean.class)
    @RequestMapping(value={"/{id}"}, method= RequestMethod.DELETE)
    public ResponseEntity<ApiCallResult> delete(@PathVariable("id") String id){
        logger.info("invoke: " + "/FeatureCategory/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            boolean delete = featureCategoryService.delete(id);
            if(delete){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not delete this feature category");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("delete feature category failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/all"}, method= RequestMethod.GET)
    @ApiOperation(value = "Get all FeatureCategory API", response = CrmFeatureCategoryBean.class,responseContainer = "List")
    public ResponseEntity<ApiCallResult> getAll(){
        logger.info("invoke: " + "/FeatureCategory/all");
        ApiCallResult result = new ApiCallResult();
        try{
            List<CrmFeatureCategoryBean> crmFeatureCategoryBeanList = featureCategoryService.getAll();
            result.setContent(crmFeatureCategoryBeanList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get all FeatureCategory failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/search"}, method= RequestMethod.GET)
    @ApiOperation(value = "Search FeatureCategory API", response = CrmFeatureCategoryBean.class,responseContainer =
            "List")
    public ResponseEntity<ApiCallResult> search(@ApiParam(value = "should be format like '2017-03-05'")
                                                @RequestParam(value = "start", required = false, defaultValue = "") String startDate,
                                                @ApiParam(value = "should be format like '2017-03-08'")
                                                @RequestParam(value = "end", required = false, defaultValue = "") String endDate,
                                                @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                                                @ApiParam(value = "column name")
                                                @RequestParam(value = "sortColumn", required = false, defaultValue = "id") String sortColumn,
                                                @ApiParam(value = "desc or asc,default value is desc")
                                                @RequestParam(value = "sortType", required = false, defaultValue = "desc") String sortType){
        logger.info("invoke: " + "/FeatureCategory/search");
        ApiCallResult result = new ApiCallResult();
        try{
            PageBean<CrmFeatureCategoryBean> crmFeatureCategoryBeanPageBean = featureCategoryService.page(startDate,
                    endDate,
                    keyword, pageSize, pageNumber, sortColumn, sortType);
            result.setContent(crmFeatureCategoryBeanPageBean);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("search FeatureCategory failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
