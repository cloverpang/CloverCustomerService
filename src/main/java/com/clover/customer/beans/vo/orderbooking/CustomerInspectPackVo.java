package com.clover.customer.beans.vo.orderbooking;

import com.clover.customer.annotation.CustomerFeatureMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by clover on 2017/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerInspectPackVo {
    public CustomerInspectPackVo() {
    }

    @CustomerFeatureMapping(fieldName = "default_finished_quantity_for_psi",categoryCode="customerOrderBooking")
    private int defaultFinishedQuantityForPsi;

    @CustomerFeatureMapping(fieldName = "default_finished_quantity_for_pm",categoryCode="customerOrderBooking")
    private int defaultFinishedQuantityForPm;

    @CustomerFeatureMapping(fieldName = "default_finished_quantity_for_ipc",categoryCode="customerOrderBooking")
    private int defaultFinishedQuantityForIpc;

    @CustomerFeatureMapping(fieldName = "default_finished_quantity_for_clc",categoryCode="customerOrderBooking")
    private int defaultFinishedQuantityForClc;

    @CustomerFeatureMapping(fieldName = "default_finished_quantity_for_dupro",categoryCode="customerOrderBooking")
    private int defaultFinishedQuantityForDupro;

    public int getDefaultFinishedQuantityForPsi() {
        return defaultFinishedQuantityForPsi;
    }

    public void setDefaultFinishedQuantityForPsi(int defaultFinishedQuantityForPsi) {
        this.defaultFinishedQuantityForPsi = defaultFinishedQuantityForPsi;
    }

    public int getDefaultFinishedQuantityForPm() {
        return defaultFinishedQuantityForPm;
    }

    public void setDefaultFinishedQuantityForPm(int defaultFinishedQuantityForPm) {
        this.defaultFinishedQuantityForPm = defaultFinishedQuantityForPm;
    }

    public int getDefaultFinishedQuantityForIpc() {
        return defaultFinishedQuantityForIpc;
    }

    public void setDefaultFinishedQuantityForIpc(int defaultFinishedQuantityForIpc) {
        this.defaultFinishedQuantityForIpc = defaultFinishedQuantityForIpc;
    }

    public int getDefaultFinishedQuantityForClc() {
        return defaultFinishedQuantityForClc;
    }

    public void setDefaultFinishedQuantityForClc(int defaultFinishedQuantityForClc) {
        this.defaultFinishedQuantityForClc = defaultFinishedQuantityForClc;
    }

    public int getDefaultFinishedQuantityForDupro() {
        return defaultFinishedQuantityForDupro;
    }

    public void setDefaultFinishedQuantityForDupro(int defaultFinishedQuantityForDupro) {
        this.defaultFinishedQuantityForDupro = defaultFinishedQuantityForDupro;
    }
}
