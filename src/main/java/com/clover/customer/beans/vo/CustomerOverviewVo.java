package com.clover.customer.beans.vo;

import com.clover.customer.annotation.CustomerFeatureCategoryMapping;
import com.clover.customer.annotation.CustomerFeatureMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOverviewVo {

    public CustomerOverviewVo() {
    }

    @CustomerFeatureMapping(fieldName = "customer_name",categoryCode="customerOview")
    private String customerName;
    @CustomerFeatureMapping(fieldName = "customer_type",categoryCode="customerOview")
    private String type;
    @CustomerFeatureMapping(fieldName = "customer_bu",categoryCode="customerOview")
    private String bu;
    @CustomerFeatureMapping(fieldName = "customer_regDate",categoryCode="customerOview")
    private String regDate;
    @CustomerFeatureMapping(fieldName = "customer_sic",categoryCode="customerOview")
    private String sic;
    @CustomerFeatureMapping(fieldName = "customer_country",categoryCode="customerOview")
    private String country;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSic() {
        return sic;
    }

    public void setSic(String sic) {
        this.sic = sic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
