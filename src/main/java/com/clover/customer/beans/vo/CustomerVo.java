package com.clover.customer.beans.vo;

import com.clover.customer.annotation.CustomerFeatureCategoryMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by clover on 2017/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerVo {
    private String customerId;
    private String customerName;

    @CustomerFeatureCategoryMapping(categoryName = "Customer Overview",categoryCode = "customerOview")
    private CustomerOverviewVo customerOverviewVo;

    @CustomerFeatureCategoryMapping(categoryName = "Customer Order Booking",categoryCode = "customerOrderBooking")
    private CustomerOrderBookingVo customerOrderBookingVo;

    public CustomerVo() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerOverviewVo getCustomerOverviewVo() {
        return customerOverviewVo;
    }

    public void setCustomerOverviewVo(CustomerOverviewVo customerOverviewVo) {
        this.customerOverviewVo = customerOverviewVo;
    }

    public CustomerOrderBookingVo getCustomerOrderBookingVo() {
        return customerOrderBookingVo;
    }

    public void setCustomerOrderBookingVo(CustomerOrderBookingVo customerOrderBookingVo) {
        this.customerOrderBookingVo = customerOrderBookingVo;
    }
}
