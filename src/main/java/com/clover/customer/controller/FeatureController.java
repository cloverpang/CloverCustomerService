package com.clover.customer.controller;

import com.clover.common.beans.PageBean;
import com.clover.customer.beans.domain.CrmFeatureBean;
import com.clover.customer.service.FeatureService;
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
import com.clover.common.beans.ApiCallResult;

import java.util.List;

@RestController
@SpringBootApplication
@RequestMapping("/Feature")
@Api(tags = {"Feature"}, description = "Feature APIs")
public class FeatureController {
    protected Logger logger = LoggerFactory.getLogger(FeatureController.class);

    @Autowired
    private FeatureService featureService;

    @ApiOperation(value="get feature ", notes="",response = CrmFeatureBean.class)
    @RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
    public ResponseEntity<ApiCallResult> get(@PathVariable("id") String id){
        logger.info("invoke: " + "/Feature/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            CrmFeatureBean crmFeatureBean = featureService.get(id);
            if (null != crmFeatureBean) {
                result.setContent(crmFeatureBean);
            } else {
                result.setMessage("can not found out this feature ");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get feature  by id failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="update feature ", notes="",response = boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crmFeatureBean", value = "", required = true, dataType = "CrmFeatureBean")
    })
    @RequestMapping(value={""}, method= RequestMethod.PUT)
    public ResponseEntity<ApiCallResult> update(@RequestBody CrmFeatureBean crmFeatureBean){
        logger.info("invoke: " + "/Feature/");
        ApiCallResult result = new ApiCallResult();
        try{
            CrmFeatureBean crmFeature = featureService.get(crmFeatureBean.getId());
            if (null != crmFeature) {
                boolean update = featureService.update(crmFeatureBean);
                if(update){
                    result.setContent(true);
                }else {
                    result.setContent(false);
                    result.setMessage("can not update this feature ");
                }
            } else {
                result.setContent(false);
                result.setMessage("can not found out old feature");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("update feature  failed : " + e.getStackTrace());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="add feature ", notes="",response = boolean.class)
    @ApiImplicitParam(name = "crmFeatureBean", value = "", required = true, dataType = "CrmFeatureBean")
    @RequestMapping(value={"/add"}, method= RequestMethod.POST)
    public ResponseEntity<ApiCallResult> add(@RequestBody CrmFeatureBean
                                                     crmFeatureBean){
        logger.info("invoke: " + "/Feature");
        ApiCallResult result = new ApiCallResult();
        try{
            boolean add = featureService.add(crmFeatureBean);
            if (add) {
                result.setContent(true);
            } else {
                result.setContent(false);
                result.setMessage("can not save feature ");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("add feature  failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value="update feature ", notes="",response = boolean.class)
    @RequestMapping(value={"/{id}"}, method= RequestMethod.DELETE)
    public ResponseEntity<ApiCallResult> delete(@PathVariable("id") String id){
        logger.info("invoke: " + "/Feature/" + id);
        ApiCallResult result = new ApiCallResult();
        try{
            boolean delete = featureService.delete(id);
            if(delete){
                result.setContent(true);
            }else {
                result.setContent(false);
                result.setMessage("can not delete this feature ");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("delete feature  failed : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/all"}, method= RequestMethod.GET)
    @ApiOperation(value = "Get all Feature API", response = CrmFeatureBean.class,responseContainer = "List")
    public ResponseEntity<ApiCallResult> getAll(){
        logger.info("invoke: " + "/Feature/all");
        ApiCallResult result = new ApiCallResult();
        try{
            List<CrmFeatureBean> crmFeatureBeanList = featureService.getAll();
            result.setContent(crmFeatureBeanList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get all Feature failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/search"}, method= RequestMethod.GET)
    @ApiOperation(value = "Search Feature API", response = CrmFeatureBean.class,responseContainer =
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
        logger.info("invoke: " + "/Feature/search");
        ApiCallResult result = new ApiCallResult();
        try{
            PageBean<CrmFeatureBean> crmFeatureBeanPageBean = featureService.page(startDate,
                    endDate,
                    keyword, pageSize, pageNumber, sortColumn, sortType);
            result.setContent(crmFeatureBeanPageBean);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("search Feature failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/category"}, method= RequestMethod.GET)
    @ApiOperation(value = "Get one category feature API", response = CrmFeatureBean.class,responseContainer = "List")
    public ResponseEntity<ApiCallResult> getByCategory(@ApiParam(value = "should be feature categoryId or categoryName'")
                                                       @RequestParam(value = "category", required = true,
                                                               defaultValue = "") String category){
        logger.info("invoke: " + "/Feature/category?category=" + category);
        ApiCallResult result = new ApiCallResult();
        try{
            List<CrmFeatureBean> crmFeatureBeanList = featureService.getByCategory(category);
            result.setContent(crmFeatureBeanList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.setMessage("get by category Feature failed : " + ExceptionUtils.getFullStackTrace(e));
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
