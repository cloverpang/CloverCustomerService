package com.clover.customer.beans.vo.orderbooking;

import com.clover.customer.annotation.CustomerFeatureMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by clover on 2017/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAqlSampleSettingVo {
    public CustomerAqlSampleSettingVo() {
    }

    @CustomerFeatureMapping(fieldName = "allow_client_change_aql_online",categoryCode="customerOrderBooking")
    private boolean allowClientChangeAQLOnline;

    @CustomerFeatureMapping(fieldName = "customer_sample_level",categoryCode="customerOrderBooking")
    private String customerSampleLevel;

    public boolean getAllowClientChangeAQLOnline() {
        return allowClientChangeAQLOnline;
    }

    public void setAllowClientChangeAQLOnline(boolean allowClientChangeAQLOnline) {
        this.allowClientChangeAQLOnline = allowClientChangeAQLOnline;
    }

    public String getCustomerSampleLevel() {
        return customerSampleLevel;
    }

    public void setCustomerSampleLevel(String customerSampleLevel) {
        this.customerSampleLevel = customerSampleLevel;
    }
}
