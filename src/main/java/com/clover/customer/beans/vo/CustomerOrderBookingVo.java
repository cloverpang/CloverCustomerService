package com.clover.customer.beans.vo;

import com.clover.customer.annotation.CustomerFeatureCategoryMapping;
import com.clover.customer.beans.vo.orderbooking.CustomerAqlSampleSettingVo;
import com.clover.customer.beans.vo.orderbooking.CustomerInspectPackVo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by clover on 2017/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOrderBookingVo {
    public CustomerOrderBookingVo() {
    }

    public CustomerInspectPackVo customerInspectPackVo;

    public CustomerAqlSampleSettingVo customerAqlSampleSettingVo;

    public CustomerInspectPackVo getCustomerInspectPackVo() {
        return customerInspectPackVo;
    }

    public void setCustomerInspectPackVo(CustomerInspectPackVo customerInspectPackVo) {
        this.customerInspectPackVo = customerInspectPackVo;
    }

    public CustomerAqlSampleSettingVo getCustomerAqlSampleSettingVo() {
        return customerAqlSampleSettingVo;
    }

    public void setCustomerAqlSampleSettingVo(CustomerAqlSampleSettingVo customerAqlSampleSettingVo) {
        this.customerAqlSampleSettingVo = customerAqlSampleSettingVo;
    }
}
