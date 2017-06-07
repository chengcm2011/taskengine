package com.application.b.model;

import com.cheng.lang.model.SuperModel;
import com.cheng.lang.model.UFBoolean;

/**
 *
 */
public class PaymentModel extends SuperModel {
    private String pkPayment;
    private int rentid;
    private String rentContractCode;
    private Double commission;
    private Double deposit;
    private Double monthRent;
    private String collectionCycleCode;
    private int paymentid;
    private Double oughtPaymentPrice;

    private UFBoolean isfinish;
    private int dr;
    private String ts;

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public String getPKFieldName() {
        return "pkPayment";
    }

    @Override
    public String getTableName() {
        return "tmp_payment";
    }

    public String getPkPayment() {
        return pkPayment;
    }

    public void setPkPayment(String pkPayment) {
        this.pkPayment = pkPayment;
    }

    public int getRentid() {
        return rentid;
    }

    public void setRentid(int rentid) {
        this.rentid = rentid;
    }

    public String getRentContractCode() {
        return rentContractCode;
    }

    public void setRentContractCode(String rentContractCode) {
        this.rentContractCode = rentContractCode;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(Double monthRent) {
        this.monthRent = monthRent;
    }

    public String getCollectionCycleCode() {
        return collectionCycleCode;
    }

    public void setCollectionCycleCode(String collectionCycleCode) {
        this.collectionCycleCode = collectionCycleCode;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    public Double getOughtPaymentPrice() {
        return oughtPaymentPrice;
    }

    public void setOughtPaymentPrice(Double oughtPaymentPrice) {
        this.oughtPaymentPrice = oughtPaymentPrice;
    }

    public UFBoolean getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(UFBoolean isfinish) {
        this.isfinish = isfinish;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "pkPayment='" + pkPayment + '\'' +
                ", rentid=" + rentid +
                ", rentContractCode='" + rentContractCode + '\'' +
                ", commission=" + commission +
                ", deposit=" + deposit +
                ", monthRent=" + monthRent +
                ", collectionCycleCode='" + collectionCycleCode + '\'' +
                ", paymentid=" + paymentid +
                ", oughtPaymentPrice=" + oughtPaymentPrice +
                ", isfinish=" + isfinish +
                ", dr=" + dr +
                ", ts='" + ts + '\'' +
                '}';
    }
}
