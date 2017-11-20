package com.clover.customer.beans.vto;

import com.clover.customer.annotation.CustomerFeatureMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by clover on 2017/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOverviewVto {
    private String customerId;

    @CustomerFeatureMapping(fieldName = "customer_name")
    private String customerName;
    @CustomerFeatureMapping(fieldName = "customer_type")
    private String type;
    @CustomerFeatureMapping(fieldName = "customer_bu")
    private String bu;
    @CustomerFeatureMapping(fieldName = "customer_regDate")
    private String regDate;
    @CustomerFeatureMapping(fieldName = "customer_sic")
    private String sic;
    @CustomerFeatureMapping(fieldName = "customer_country")
    private String country;

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
