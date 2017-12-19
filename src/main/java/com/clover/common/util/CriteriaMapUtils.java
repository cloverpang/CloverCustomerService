package com.clover.common.util;

import com.clover.common.beans.ConditionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/10/11.
 */
public class CriteriaMapUtils {
    public static Map<String, Object> commonCriteriaMapGenerate(String startDate, String endDate, String keyWord, int pageSize, int
            pageNo, String sortColumn, String sortType){
        Map<String, Object> criteriaMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(startDate)){
            criteriaMap.put("startDate", startDate);
        }
        if(StringUtils.isNotEmpty(endDate)){
            criteriaMap.put("endDate", endDate);
        }
        if(StringUtils.isNotEmpty(keyWord)){
            criteriaMap.put("keyWord", keyWord);
        }

        if(StringUtils.isNotEmpty(sortColumn)){
            criteriaMap.put("sortColumn", sortColumn);
        }
        if(StringUtils.isNotEmpty(sortType)){
            criteriaMap.put("sortType", sortType);
        }

        criteriaMap.put("pageSize", pageSize);
        criteriaMap.put("pageNo", pageNo);

        pageSize = (pageSize <= 0 ? 25 : pageSize);
        int start = (pageNo - 1) * pageSize;
        int end = start + pageSize - 1;
        criteriaMap.put("startRowNumber", start);
        criteriaMap.put("endRowNumber", end);

        return criteriaMap;
    }

    public static List<ConditionBean> commonConditionsGenerate(String conditionStr){
        List<ConditionBean> conditions = new ArrayList<ConditionBean>();
        try{
            if(StringUtils.isEmpty(conditionStr)){
                return conditions;
            }

            String[] conditionArray = conditionStr.split("\\$");
            for(String oneConditionStr : conditionArray){
                String[] condition = oneConditionStr.split("::");
                if(condition.length == 3){
                    try{
                        ConditionBean conditionBean = new ConditionBean();
                        conditionBean.setConditionName(condition[0]);
                        conditionBean.setConditionExpression(condition[1]);
                        conditionBean.setConditionValue(condition[2]);
                        conditions.add(conditionBean);
                        //System.out.println(conditionBean.toString());
                    }catch (Exception e){

                    }
                }
            }
        }catch (Exception e){

        }

        return conditions;
    }
}
