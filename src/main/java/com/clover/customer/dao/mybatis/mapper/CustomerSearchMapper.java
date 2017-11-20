package com.clover.customer.dao.mybatis.mapper;

import com.clover.customer.beans.vto.CustomerOverviewVto;
import com.clover.customer.beans.vo.CustomerOverviewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by clover on 2017/10/27.
 */
@Component
@Mapper
public interface CustomerSearchMapper {
    List<CustomerOverviewVto> search(Map<String, Object> param);

    int count(Map<String, Object> param);

    List<CustomerOverviewVto> searchAll(Map<String, Object> param);

    int countAll(Map<String, Object> param);
}
